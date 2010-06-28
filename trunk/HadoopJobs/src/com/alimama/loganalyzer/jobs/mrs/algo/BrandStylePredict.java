package com.alimama.loganalyzer.jobs.mrs.algo;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class BrandStylePredict {
	private final static String split = "\u0001";

	private String catPath;
	private String brandsPath;
	private String stylesPath;
	TwsTokenization tokenization;
	private Normalizer normalizer;
	private java.util.Map<String, java.util.Map<String, String>> brandsToIds = null;
	private java.util.Map<String, java.util.Map<String, String>> typesToIds = null;
	private java.util.Map<String, java.util.Set<String>> wordToCats = null;
	private java.util.Map<String, java.util.Set<String>> brandsToCats = null;
	private java.util.Map<String, String> catParents = null;

	public class BrandStyle {
		public String brandId;
		public String styleId;
		public double weight;

		public BrandStyle(String _brandId, String _styleId, double _weight) {
			brandId = _brandId;
			styleId = _styleId;
			weight = _weight;
		}
	}

	public BrandStylePredict(String _catPath, String _brandsPath,
			String _stylesPath, TwsTokenization _tokenization,
			Normalizer _normalizer) {
		catPath = _catPath;
		brandsPath = _brandsPath;
		stylesPath = _stylesPath;
		tokenization = _tokenization;
		normalizer = _normalizer;
	}

	public void initialize() {
		readCatParents();
		//System.out.println("catParents");
		//System.out.println(catParents);
		readBrands();
		//System.out.println("Brands");
		//System.out.println(brandsToIds);
		readTypes();
		//System.out.println("types");
		//System.out.println(typesToIds);
		readCatWord();
		//System.out.println("catWords");
		//System.out.println(wordToCats);
		//System.out.println("brandsToCats");
		//System.out.println(brandsToCats);
		
	}

	private void addBrandCat(String brand, String cat) {
		if (brandsToCats.containsKey(brand)) {
			java.util.Set<String> cats = brandsToCats.get(brand);
			if (!cats.contains(cat)) {
				cats.add(cat);
			}
		} else {
			java.util.Set<String> cats = new java.util.HashSet<String>();
			cats.add(cat);
			brandsToCats.put(brand, cats);
		}
	}
	
	private void readBrands() {
		if (brandsToIds == null) {
			try {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(new FileInputStream(brandsPath),
								"utf-8"));
				brandsToIds = new java.util.HashMap<String, java.util.Map<String, String>>();
				brandsToCats = new java.util.HashMap<String, java.util.Set<String>>();

				String line = reader.readLine();
				while (line != null && !line.isEmpty()) {
					String[] items = line.split(split);
					if (items.length != 5) {
						continue;
					}

					String name = items[4];
					String cat = items[0];
					String id = items[3];

					addBrand(name, cat, id);
					if (name.indexOf("/") > 0) {
						String[] subNames = name.split("/");
						for (String subName : subNames) {
							addBrand(subName, cat, id);
						}
					} else {
						addBrand(name, cat, id);
					}
					
					addBrandCat(id, cat);

					line = reader.readLine();
				}
				reader.close();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void readTypes() {
		if (typesToIds == null) {
			try {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(new FileInputStream(stylesPath),
								"utf-8"));
				typesToIds = new java.util.HashMap<String, java.util.Map<String, String>>();

				String line = reader.readLine();
				while (line != null && !line.isEmpty()) {
					String[] items = line.split(split);
					String name = items[5];
					String brand = items[1];
					String id = items[4];
					addType(name, brand, id);

					line = reader.readLine();
				}
				reader.close();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void readCatParents() {
		if (catParents == null) {
			try {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(new FileInputStream(catPath),
								"utf-8"));
				catParents = new java.util.HashMap<String, String>();

				String line = reader.readLine();
				while (line != null && !line.isEmpty()) {
					String[] items = line.split(split);
					String level = items[0];
					String id = items[1];

					if (!id.equalsIgnoreCase("0") && !id.equalsIgnoreCase("-1")) {
						// Add parent
						try {
							int levelNumber = Integer.parseInt(level);
							if (levelNumber > 1) {
								StringBuilder builder = new StringBuilder();
								builder.append("_");
								for (int k = 3; k < 2 * levelNumber + 1; k += 2){
									builder.append(items[k]);
									builder.append("_");
								}
								catParents.put(id, builder.toString());
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

					line = reader.readLine();
				}
				reader.close();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void readCatWord() {
		if (wordToCats == null) {
			try {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(new FileInputStream(catPath),
								"utf-8"));
				wordToCats = new java.util.HashMap<String, java.util.Set<String>>();

				String line = reader.readLine();
				while (line != null && !line.isEmpty()) {
					String[] items = line.split(split);
					String id = items[1];
					String name = items[2];

					if (!id.equals("0") && !id.equals("-1")) {
						// Add cat name
						if (!name.isEmpty()) {
							if (name.indexOf("/") > 0) {
								String[] subNames = name.split("/");
								for (String subName : subNames) {
									addCatWord(subName, id);
								}
							} else {
								addCatWord(name, id);
							}
						}
					}
					line = reader.readLine();
				}
				reader.close();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private void addCatWord(String name, String cat) {
		tokenization.segment(name);
		List<String> words = tokenization.getKeyWords();
		for (String word : words) {
			String word_normalized = normalizer.normalize(word);
			if (word_normalized.isEmpty()) {
				continue;
			}

			if (wordToCats.containsKey(word_normalized)) {
				java.util.Set<String> cats = wordToCats.get(word_normalized);
				if (!cats.contains(cat)) {
					cats.add(cat);
				}
			} else {
				java.util.Set<String> cats = new java.util.HashSet<String>();
				cats.add(cat);
				wordToCats.put(word_normalized, cats);
			}
		}
	}

	private void addBrand(java.util.Map<String, java.util.Map<String, String>> brandsToCatToId, String brand, String cat, String id) {
		if (brandsToCatToId.containsKey(brand)) {
			java.util.Map<String, String> catToId = brandsToCatToId.get(brand);
			if (!catToId.containsKey(cat)) {
				catToId.put(cat, id);
			}
		} else {
			java.util.Map<String, String> catToId = new java.util.HashMap<String, String>();
			catToId.put(cat, id);
			brandsToCatToId.put(brand, catToId);
		}
	}
	
	private void addBrand(String name, String cat, String id) {
		String name_normalized = normalizer.normalize(name);
		if (name_normalized.isEmpty()) {
			return;
		}
		addBrand(brandsToIds, name_normalized, cat, id);
	}

	private void addType(java.util.Map<String, java.util.Map<String, String>> typesToBrandToId, String type, String brand, String id) {
		if (typesToBrandToId.containsKey(type)) {
			java.util.Map<String, String> brandToId = typesToBrandToId.get(type);
			if (!brandToId.containsKey(brand)) {
				brandToId.put(brand, id);
			}
		} else {
			java.util.Map<String, String> brandToId = new java.util.HashMap<String, String>();
			brandToId.put(brand, id);
			typesToBrandToId.put(type, brandToId);
		}
	}
	
	private void addType(String name, String brand, String id) {
		String name_normalized = normalizer.normalize(name);
		if (name_normalized.isEmpty()) {
			return;
		}
		addType(typesToIds, name_normalized, brand, id);
	}
	
	private List<String> getBrandId(String brand, String cat) {
		if (brandsToIds.containsKey(brand)) {
			java.util.List<String> ids = new java.util.LinkedList<String>();
			java.util.Map<String, String> cats = brandsToIds.get(brand);
			Iterator<java.util.Map.Entry<String, String>> it = cats.entrySet().iterator();
			while(it.hasNext()) {
				java.util.Map.Entry<String, String> catId = it.next();
				if (!ids.contains(catId.getValue())) {
					if (isRelatedCat(catId.getKey(), cat)) {
						ids.add(catId.getValue());
					}
				}
			}
			if (!ids.isEmpty()) {
				return ids;
			}
		}
		return null;
	}

	private List<String> getAllBrandIds(String cat, List<String> brands,
			List<String> realBrands) {
		List<String> allBrandIds = new LinkedList<String>();
		for (String brand : brands) {
			List<String> brandIds = getBrandId(brand, cat);
			if (brandIds != null) {
				allBrandIds.addAll(brandIds);
				realBrands.add(brand);
			}
		}
		return allBrandIds;
	}
	
	private boolean isBrandById(String brand, String cat) {
		if (brandsToCats.containsKey(brand)) {
			Set<String> cats = brandsToCats.get(brand);
			return isRelatedCats(cat, cats.iterator());
		}
		
		return false;
	}
	
	private List<String> getBrandStyleId(String style, String cat) {
		if (typesToIds.containsKey(style)) {
			java.util.Map<String, String> ids = new java.util.HashMap<String, String>();
			java.util.List<String> brandStyleIds = new LinkedList<String>();
			java.util.Map<String, String> brands = typesToIds.get(style);
			Iterator<java.util.Map.Entry<String, String>> it = brands.entrySet().iterator();
			while(it.hasNext()) {
				java.util.Map.Entry<String, String> brandStyle = it.next();
				if (!ids.containsKey(brandStyle.getKey())) {
					if (isBrandById(brandStyle.getKey(), cat)) {
						ids.put(brandStyle.getKey(), brandStyle.getValue());
						brandStyleIds.add(brandStyle.getKey());
						brandStyleIds.add(brandStyle.getValue());
					}
				}
			}
			if (!brandStyleIds.isEmpty()) {
				return brandStyleIds;
			}
		}
		return null;
	}

	private List<String> getAllBrandStyleIds(String cat, List<String> styles,
			List<String> realStyles) {
		List<String> allBrandStyleIds = new LinkedList<String>();

		for (String style : styles) {
			List<String> brandStyleIds = getBrandStyleId(style, cat);
			if (brandStyleIds != null) {
				allBrandStyleIds.addAll(brandStyleIds);
				realStyles.add(style);
			}
		}
		return allBrandStyleIds;
	}

	public List<BrandStyle> predict(String cat, List<String> brands,
			List<String> styles, List<String> realBrands,
			List<String> realStyles) {
		List<BrandStyle> brandStyles = new LinkedList<BrandStyle>();
		List<String> allBrandStyleIds = new LinkedList<String>();

		if (!brands.isEmpty() || !styles.isEmpty()) {
			// Step 1) Calculate all brand style
			if (styles.isEmpty()) {
				// We only refer the brandIds  没有型号词，从品牌词进行推测
				List<String> allBrandIds = getAllBrandIds(cat, brands,
						realBrands);
				for (String brandId : allBrandIds) {
					allBrandStyleIds.add(brandId);
					allBrandStyleIds.add("0");
				}
			} else if (brands.isEmpty()) {
				// We only refer from styles  没有品牌词，从型号词进行推测
				allBrandStyleIds = getAllBrandStyleIds(cat, styles, realStyles);
			} else {
				// We refer to the  从品牌词和型号词一起推测
				List<String> allBrandIds = getAllBrandIds(cat, brands,
						realBrands);		
				
				List<String> allBrandStyleIdsTemp = getAllBrandStyleIds(cat,
						styles, realStyles);

				Iterator<String> it = allBrandStyleIdsTemp.iterator();
				while (it.hasNext()) {
					String brandId = it.next();
					String styleId = it.next();

					if (allBrandIds.contains(brandId)) {
						allBrandStyleIds.add(brandId);
						allBrandStyleIds.add(styleId);
					}
				}

				if (allBrandStyleIds.isEmpty()) {
					allBrandStyleIds = allBrandStyleIdsTemp;

					for (String brandId : allBrandIds) {
						allBrandStyleIds.add(brandId);
						allBrandStyleIds.add("0");
					}
				}
			}

			// Step 2) Construct the output brand-style-id
			Iterator<String> it = allBrandStyleIds.iterator();
			while (it.hasNext()) {
				String brandId = it.next();
				String styleId = it.next();

				boolean exist = false;
				for (BrandStyle brandStyle : brandStyles) {
					if (brandStyle.brandId.equals(brandId)
							&& brandStyle.styleId.equals(styleId)) {
						brandStyle.weight++;
						exist = true;
						break;
					}
				}
				if (!exist) {
					brandStyles.add(new BrandStyle(brandId, styleId, 1));
				}
			}
		}

		if (allBrandStyleIds.isEmpty()) {
			brandStyles.add(new BrandStyle("0", "0", 1));
		} else {
			int count = allBrandStyleIds.size() / 2;
			for (BrandStyle brandStyle : brandStyles) {
				brandStyle.weight /= count;
			}
		}

		return brandStyles;
	}

	public boolean isAncestorCat(String cat, String catAncestor) {
		if (catAncestor.equals(cat)) {
			return true;
		} else {
			if (catParents.containsKey(cat)) {
				String catParent = catParents.get(cat);
				StringBuilder builder = new StringBuilder();
				builder.append("_");
				builder.append(catAncestor);
				builder.append("_");
				if (catParent.indexOf(builder.toString()) >= 0) {
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean isRelatedCat(String cat1, String cat2) {
		if (isAncestorCat(cat1, cat2) || isAncestorCat(cat2, cat1)) {
			return true;
		}
		return false;
	}

	private boolean isRelatedCats(String cat, Iterator<String> cats) {
		while(cats.hasNext()) {
			if (isRelatedCat(cat, cats.next())) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean isCatWord(String word, String cat) {
		if (wordToCats.containsKey(word)) {
			java.util.Set<String> cats = wordToCats.get(word);
			return isRelatedCats(cat, cats.iterator());
		}
		return false;
	}
	
	public boolean isCatSynonymous(String word, String cat) {
		return false;
	}
}
