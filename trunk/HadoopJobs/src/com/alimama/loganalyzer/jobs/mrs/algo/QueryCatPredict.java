package com.alimama.loganalyzer.jobs.mrs.algo;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import com.alimama.loganalyzer.jobs.mrs.util.KQConst;

// 根据查询串来进行类目预测
public class QueryCatPredict {
	
	private String indexPath = null;
	private IndexWriter indexWriter = null;
	private IndexSearcher indexSearcher = null;
	private Analyzer analyzer = null;
	
	public QueryCatPredict(String _indexPath, Analyzer _analyzer) {
		indexPath = _indexPath;
		analyzer = _analyzer;
	}
	
	public QueryCatPredict(String _indexPath, String tws_conf_path) {
		this.indexPath = _indexPath;

		TwsAnalyzer twsAnalyzer = new TwsAnalyzer(tws_conf_path);
		analyzer = twsAnalyzer;
	}

	public boolean prepareIndex() {
		try {
			indexWriter = new IndexWriter(FSDirectory.open(new File(indexPath)), analyzer, true, IndexWriter.MaxFieldLength.LIMITED);
			indexWriter.setRAMBufferSizeMB(100);
			return true;
		} catch (Throwable e) {
			System.err.println("QueryCatPredict prepare error:" + e);
			e.printStackTrace();
		}
		return false;
	}
	
	public void addDocument(String keywords, String clickNum, String catId) throws CorruptIndexException, IOException {
		Document doc = new Document();

		Field field1 = new Field(KQConst.QUERY_FIELD, keywords, Field.Store.YES, Field.Index.ANALYZED);
		doc.add(field1);
		Field field2 = new Field(KQConst.CLICK_FIELD, clickNum, Field.Store.YES, Field.Index.NO);
		doc.add(field2);
		Field field3 = new Field(KQConst.CAT_FIELD, catId, Field.Store.YES, Field.Index.NO);
		doc.add(field3);

		indexWriter.addDocument(doc);
	}
	
	public void endIndex() {
		if (indexWriter != null) {
			try {
				indexWriter.setUseCompoundFile(true);				
				indexWriter.optimize();
				indexWriter.close();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean prepareSearch() {
		try {
			indexSearcher = new IndexSearcher(FSDirectory.open(new File(indexPath)), true);
			return true;
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 根据查询词返回最匹配类目
	 * 
	 * @param queryWord
	 * @return
	 */
	public int search(String queryWord) {
		try {
			System.out.println("Query 关键词:" + queryWord);
		    QueryParser parser = new QueryParser(Version.LUCENE_29, KQConst.QUERY_FIELD, analyzer);
		    Query query = parser.parse(queryWord);

		    TopDocs topDocs = indexSearcher.search(query, 10);
		    ScoreDoc[] hits = topDocs.scoreDocs;
		    if (hits.length <=0){
				return KQConst.NOT_EXIST_INT;
		    }
		    String bestCatId = "0";
		    int maxClickNum = 0;
		    for (int i = 0; i < hits.length; i++) {
		         Document d = indexSearcher.doc(hits[i].doc);
		         
		         String queryStr = d.getField(KQConst.QUERY_FIELD).stringValue();
		         String catIdStr = d.getField(KQConst.CAT_FIELD).stringValue();
		         String clickNumStr = d.getField(KQConst.CLICK_FIELD).stringValue();

		         System.out.println("Query:" + queryStr);
		         System.out.println("Cat:" + catIdStr);
		         System.out.println("Click:" + clickNumStr);
		         
		         if (Integer.parseInt(clickNumStr) > maxClickNum){
		        	 bestCatId = d.getField(KQConst.CAT_FIELD).stringValue();
		        	 maxClickNum = Integer.parseInt(clickNumStr);
		         }
	    	}			
			return Integer.parseInt(bestCatId);
		} catch (Throwable e) {
			e.printStackTrace();
			return KQConst.NOT_EXIST_INT;
		} 
	}
	
	public void endSearch() {
		try {			
			indexSearcher.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		if(args.length != 3) {
			System.out.println("usage: QueryCatPredict <lucene_path> <tws_conf_path> <query>");
			return;
		}		
		QueryCatPredict generator = new QueryCatPredict(args[0], args[1]);
		generator.prepareSearch();
		System.out.println("最终预测结果：" + generator.search(args[2]));
		generator.endSearch();
	}

	
}
