package com.alimama.loganalyzer.jobs.mrs.algo.lucene3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.FSDirectory;

import com.alimama.loganalyzer.jobs.mrs.algo.Normalizer;
import com.alimama.loganalyzer.jobs.mrs.algo.TwsAnalyzer;
import com.alimama.loganalyzer.jobs.mrs.util.KQConst;

/**
 * 从keycat_v3文件生成Lucene 3索引
 * 
 * @author 明风
 * @date 2010-05-03
 */

public class Lucene3IndexBuilder {
	private String document_path;
	private String lucene_path;
	
	private String tws_conf_path;
	private String normalizer_conf_path;
	
	private Normalizer normalizer = null;
	private TwsAnalyzer twsAnalyzer = null;

	IndexWriter indexWriter = null;
	
	/**
	 * 构造器
	 * 
	 * @param _document_path	源文件路径
	 * @param _lucene_path		生成Lucene文件路径
	 * @param _tws_conf_path	分词器配置路径
	 * @param _normalizer_conf_path	归一化器配置路径
	 */
	public Lucene3IndexBuilder(String _document_path, String _lucene_path, String _tws_conf_path, String _normalizer_conf_path) {
		document_path = _document_path;
		lucene_path = _lucene_path;
		
		tws_conf_path = _tws_conf_path;
		normalizer_conf_path = _normalizer_conf_path;
	}

	/**
	 * 初始化
	 */
	public void initialize() {
		if (normalizer == null) {
			normalizer = new Normalizer();
			normalizer.initialize(normalizer_conf_path);
		}

		if (twsAnalyzer == null) {
			twsAnalyzer = new TwsAnalyzer(tws_conf_path);
		}
	}

	/**
	 * 
	 */
	public void unitialize() {
		normalizer = null;
		twsAnalyzer = null;
	}

	/**
	 * 主接口
	 */
	public void buildIndex() {
		prepareIndex();		
		build();		
		endIndex();

	}

	/**
	 * 创建索引
	 */
	private void build() {
		String line = null;
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(document_path), KQConst.UTF_8_ENCODING));
			
			while ((line = reader.readLine()) != null) {
				String[] items = line.split(KQConst.SPLIT);
				
				String query = items[0];
				String clickNum = items[1];
				String catId = items[2];

				addDocument(normalizer.normalize(query), clickNum, catId);
			}
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 开始准备创建索引，删除原有的索引文件
	 * 
	 */
	public void prepareIndex() {
		File luceneFile = new File(lucene_path);
		if (luceneFile.exists()) {
			deleteFile(luceneFile);
		}
		try {
			indexWriter = new IndexWriter(FSDirectory.open(luceneFile), twsAnalyzer, true, IndexWriter.MaxFieldLength.LIMITED);
		} catch (Throwable e) {
			System.err.println("Lucene3IndexBuilder prepare index error:" + e);
			e.printStackTrace();
		}
	}
	

	/**
	 * 完成创建索引的过程，优化并写入文件
	 */
	public void endIndex() {
		if (indexWriter != null) {
			try {
				indexWriter.optimize();
				indexWriter.close();
			} catch (Throwable e) {
				System.err.println("Lucene3IndexBuilder close index error:" + e);
				e.printStackTrace();
			}
		}
	}	

	/**
	 * 添加一个索引文档
	 * 
	 * @param query	查询词
	 * @param click	点击数
	 * @param cat	类目
	 * 
	 * @throws CorruptIndexException
	 * @throws IOException
	 */
	private void addDocument(String query, String click, String cat) throws CorruptIndexException, IOException {
		 Document doc = new Document(); 
		 
		 doc.add(new Field(KQConst.QUERY_FIELD, query, Field.Store.YES, Field.Index.ANALYZED));
		 doc.add(new Field(KQConst.CLICK_FIELD, click, Field.Store.YES, Field.Index.NOT_ANALYZED));
		 doc.add(new Field(KQConst.CAT_FIELD, cat, Field.Store.YES, Field.Index.NOT_ANALYZED));
		 
		 indexWriter.addDocument(doc);
	}

	/**
	 * 删除文件
	 * 
	 * @param file
	 */
	private void deleteFile(File file) {
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File subFile : files) {
				deleteFile(subFile);
			}
		}
		file.delete();
	}
	
	public static void main(String[] args) {
		if (args.length != 4) {
			System.out.println("usage: Lucene3IndexBuilder <src_path> <dest_path> <tws_conf_path> <normalizer_conf_path>");
			return;
		}

		System.out.println(args);
		
		Lucene3IndexBuilder builder = new Lucene3IndexBuilder(args[0], args[1], args[2], args[3]);
		
		builder.initialize();
		builder.buildIndex();
		builder.unitialize();
	}



}
