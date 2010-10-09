package edu.zju.cs.ooobgy.mr.common;

import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.Reducer;

/**
 * @Author: 周晓龙
 * @created: 2010-7-21
 **/

/**
 * Hadoop MapReduce作业的公共接口，
 * 所有需要使用Launcher启动的作业必须实现该接口
 */
public interface HadoopJob {
	/**
	 * 获取要使用的Mapper类
	 * @return Mapper
	 */
	@SuppressWarnings("unchecked")
	public Class<? extends Mapper> getMapper();
	
	/**
	 * 获取要使用的Reducer类
	 * @return Reducer
	 */
	@SuppressWarnings("unchecked")
	public Class<? extends Reducer> getReducer();
	
	/**
	 * MapReduce作业程序内部的一些特有config，优先级小于配置文件的配置
	 * @param conf
	 */
	public void configJob(JobConf conf);
}
