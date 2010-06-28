package com.alimama.loganalyzer.jobs.mrs.algo.lucene3;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

import com.alimama.loganalyzer.jobs.mrs.util.KQConst;

/**
 * 根据查询串来进行类目预测
 *  
 * @author 明风
 * @date 2010-05-03
 *
 */
public class Lucene3QueryCatPredictor {
	
	private String indexPath = null;
	private IndexSearcher indexSearcher = null;
	
	public Lucene3QueryCatPredictor(String _indexPath) {
		this.indexPath = _indexPath;
	}
	
	/**
	 * 初始化indexSearcher, 准备查询
	 */
	public void initialize() {
		try {
			indexSearcher = new IndexSearcher(FSDirectory.open(new File(indexPath)), true);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 查询query词对应的cat，根据点击数排名最高
	 * @param queryStr
	 * @return
	 */
	public int search(String queryStr) {		
		try {
			Query query = new TermQuery(new Term(KQConst.QUERY_FIELD, queryStr));   
			TopDocs rs = indexSearcher.search(query, null, 10);
			System.out.println(rs.totalHits);
			if (rs.totalHits <= 0){
				return KQConst.NOT_EXIST_INT; 
			}
			
			Document firstHit = indexSearcher.doc(rs.scoreDocs[0].doc);
			String catIdStr = firstHit.getField(KQConst.CAT_FIELD).name();
			
			int catId = Integer.parseInt(catIdStr);
			return catId;
		} catch (Throwable e) {
			e.printStackTrace();
			return KQConst.NOT_EXIST_INT;
		}
	}
	
	/**
	 *	结束 
	 */
	public void unitialize() {
		try {
			indexSearcher.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
