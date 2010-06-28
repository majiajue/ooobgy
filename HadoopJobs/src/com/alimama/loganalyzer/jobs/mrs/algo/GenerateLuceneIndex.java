package com.alimama.loganalyzer.jobs.mrs.algo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import com.alimama.loganalyzer.jobs.mrs.util.KQConst;


/**
 * 从keycat_v3文件生成Lucene索引
 * 
 * @author 明风
 * @date 2010-05-04
 * 
 */
public class GenerateLuceneIndex {
	private String document_path;
	private String lucene_path;
	private String tws_conf_path;
	private String normalizer_conf_path;
	
	private TwsTokenization tokenization = null;
	private Normalizer normalizer = null;
	private TwsAnalyzer twsAnalyzer = null;
	
	public GenerateLuceneIndex(String _document_path, String _lucene_path, String _tws_conf_path, String _normalizer_conf_path) {
		document_path = _document_path;
		lucene_path = _lucene_path;
		tws_conf_path = _tws_conf_path;
		normalizer_conf_path = _normalizer_conf_path;
	}
	
	public void initialize() {
		if (tokenization == null) {
			tokenization = new TwsTokenization();
			tokenization.initialize(tws_conf_path);
		}
		
		if (normalizer == null) {
			normalizer = new Normalizer();
			normalizer.initialize(normalizer_conf_path);
		}
		
		if (twsAnalyzer == null) {
			twsAnalyzer = new TwsAnalyzer(tws_conf_path);
		}		
	}
	
	public void unitialize() {
		tokenization.unitialize();
	}
	
	private void deleteFile(File file) {
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File subFile: files) {
				deleteFile(subFile);
			}
		}
		file.delete();
	}
	
	private void buildIndexFiles() {
		// Step 1) Delete old files
		File indexFile = new File(lucene_path);
		if (indexFile.exists()) {
			deleteFile(indexFile);
		}
		
		// Step 2) Create index files
		QueryCatPredict predict = new QueryCatPredict(lucene_path, twsAnalyzer);
		if (predict.prepareIndex()) {
			try {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(new FileInputStream(document_path), "utf-8"));
				String line = reader.readLine();
				while (line != null) {
					String[] terms = line.split(KQConst.SPLIT);
					String query = terms[0];
					String clickNum = terms[1];
					String catId = terms[2];
					String queryNormalized = normalizer.normalize(query);
					predict.addDocument(queryNormalized, clickNum, catId);
					line = reader.readLine();
				}
				reader.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			predict.endIndex();
		}
	}
	
	public static void main(String[] args) {
		if(args.length != 4) {
			System.out.println("usage: GenerateLuceneIndex <src_path> <dest_path> <tws_conf_path> <normalizer_conf_path>");
			return;
		}		
		GenerateLuceneIndex generator = new GenerateLuceneIndex(args[0], args[1], args[2], args[3]);
		generator.initialize();
		generator.buildIndexFiles();
		generator.unitialize();
	}
}
