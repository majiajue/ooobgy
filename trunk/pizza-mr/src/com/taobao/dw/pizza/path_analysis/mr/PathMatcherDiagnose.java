package com.taobao.dw.pizza.path_analysis.mr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.SequenceFile.CompressionType;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.compress.GzipCodec;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.RecordWriter;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.SequenceFileOutputFormat;
import org.apache.hadoop.mapred.TextOutputFormat;
import org.apache.hadoop.mapred.lib.MultipleOutputFormat;
import org.apache.hadoop.util.Progressable;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.json.JSONException;
import org.json.JSONObject;

import com.sun.corba.se.impl.interceptors.PINoOpHandlerImpl;
import com.taobao.dw.pizza.path_analysis.core.PizzaConst;
import com.taobao.dw.pizza.path_analysis.core.algo.InvertedListReader;
import com.taobao.dw.pizza.path_analysis.core.algo.PizzaJSON;
import com.taobao.dw.pizza.path_analysis.core.algo.PathTraceUtils;
import com.taobao.dw.pizza.path_analysis.core.pojo.AtomPath;
import com.taobao.dw.pizza.path_analysis.core.pojo.AtomTrace;
import com.taobao.dw.pizza.path_analysis.core.pojo.CompositeTrace;
import com.taobao.dw.pizza.path_analysis.core.pojo.InvertedList;

/**
 * 路径匹配
 * 
 * 剥离节点切分部分，为了更好的调试和解释，方便查找问题
 * 
 * @created 2011-08-10
 * 
 * @author 明风
 * @deprecated 2011-9-11底层逻辑已改变，若需使用请从{@link PathMatcher}修改过来
 */
public class PathMatcherDiagnose extends Configured implements Tool {
	InvertedList invertedList;

	public static class PmMapper extends MapReduceBase implements
			Mapper<LongWritable, Text, Text, Text> {
		final InvertedList invertedList = new InvertedList();
		Text key = new Text();
		Text value = new Text();

		Map<String, JSONObject> nodeFeatureAttrs;
		String[] rawTraces;
		Map<String, JSONObject> nodeIdsAttrs;
		
		@Override
		public void configure(JobConf job) {
			super.configure(job);
			try {
				readFromHdfs(job);
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
			System.out.println("InvertedList:" + this.invertedList);
		}

		private void read(FileSystem fs, Path path) throws IOException {
			FileStatus[] fileStatus = fs.listStatus(path);
			for (FileStatus status : fileStatus) {
				Path catPath = status.getPath();
				BufferedReader fis;
				FSDataInputStream in = fs.open(catPath);
				fis = new BufferedReader(new InputStreamReader(in, "UTF-8"));
				InvertedListReader ilr = new InvertedListReader();
				this.invertedList.addAll(ilr.readInvertedList(fis));
				fis.close();
			}
			
			//System.out.println("Inverted List: " + invertedList);

		}

		private void readFromHdfs(JobConf jobConf) throws IOException {
			FileSystem fs = FileSystem.get(jobConf);
			try {
				String invertedListPath = jobConf.get("inverted.list");
				if (invertedListPath == null || invertedListPath.length() <= 0) {
					throw new IOException("Input path is empty");
				}

				Path inputPath = new Path(invertedListPath);
				read(fs, inputPath);
			} catch (IOException e) {
				e.printStackTrace();
				throw e;
			}
		}

		@Override
		public void map(LongWritable key, Text value,
				OutputCollector<Text, Text> output, Reporter reporter)
				throws IOException {
			try {
				nodeFeatureAttrs = PizzaJSON.parseUserRoute(value.toString());
				rawTraces = PathTraceUtils.extractTraces(nodeFeatureAttrs.keySet());
				nodeIdsAttrs = PathTraceUtils.rebuildNodeAttrs(nodeFeatureAttrs);
			} catch (Throwable e) {
				System.err.println("Map 1:" + e);
				reporter.incrCounter("PathCounters", "WrongJson", 1);
				return;
			}


			if (nodeFeatureAttrs.size() == 0 || rawTraces.length == 0){
				return;
			}

			//System.out.println("NodeFeatureAttrs:" +nodeFeatureAttrs.keySet());
			//System.out.println("RawTraces:" + Arrays.asList(rawTraces));

			for (String rawTrace : rawTraces) {
				try {
					 //单个首节点，特殊处理，rawTrace将会是一个首节点，因为有可能匹配多个首节点，所以还是可能会有多个。
					if (nodeFeatureAttrs.size() == 1) {
						List<AtomTrace> ats = PathTraceUtils.findAtsWithHeadNode(rawTrace, this.invertedList);
						//outputAtomTrace(ats, nodeIdsAttrs, output);
					} else {
						// 拆分用户轨迹为原子轨迹
						List<AtomTrace> ats = PathTraceUtils.splitTrace(rawTrace);
						if (ats.size() <= 0)
							return;
						//System.out.println("Original At:" + ats);

						// 从构造好的倒排表中，将原子轨迹对应的路径id和下一节点id，填充上
						for (AtomTrace at : ats) {
							invertedList.match(at);
						}
						//System.out.println("Matched At:" + ats);

						// 合并原子轨迹为组合轨迹
						List<CompositeTrace> cts = PathTraceUtils.mergeTrace(ats);

						if (cts.size() > 0)
							outputCompositeTraces(rawTrace, cts, nodeIdsAttrs, output, reporter);
						else
							outputAtomTraces(rawTrace, ats, nodeIdsAttrs, output, reporter);
					}
				} catch (Throwable t) {
					System.err.println("Map 2" + t);
					t.printStackTrace();
				}
			}
		}

		/**
		 *  输出原子轨迹起始于首节点，单独将首节点所有可以匹配路径输出一次，输出逻辑同单个首节点
		 * @param rawTrace 
		 * 
		 * @param ats
		 * @param nodeIdsAttrs2
		 * @param output
		 * @param reporter 
		 * 
		 * @throws IOException 
		 * @throws JSONException 
		 */
		private void outputAtomTraces(String rawTrace, List<AtomTrace> ats, Map<String, JSONObject> nodeAttrs, OutputCollector<Text, Text> output, Reporter reporter) throws JSONException, IOException {
			reporter.incrCounter("PathCounters", "WrongCompositeTraces", 1);
			String uid = nodeAttrs.values().iterator().next().getString("uid");
			key.set(uid);
			for (AtomTrace at : ats) {
					StringBuilder sb1 = new StringBuilder();

					sb1.append("Wrong").append(PizzaConst.SPLIT);
					sb1.append(rawTrace).append(PizzaConst.SPLIT);
					sb1.append(at.toString());

					value.set(sb1.toString());
					
					output.collect(key,  value);
			}
		}



		/**
		 * 
		 * @param cts
		 * 				   样例：
		 * 							 86+88-91
		 * 									{-662107825=(86+88-91):-662107825,13,86,1, 
		 * 									 -1442201463=(86+88-91):-1442201463,91,86,7, 
		 * 									 -1898552851=(86+88-91):-1898552851,141,86,3}
		 * @param nodeMap
		 * 				   样例：
		 * 						  	{52={"uid":"107188702","isUser":"1","nodeFeature":"52","logTime":"20110619111834","mid":"5618641004102355756","isRefer":"1","url":"http://s.taobao.com/search?q=防晒衣 长袖 透明&keyword=&commend=all&ssid=s5-e&search_type=item&atype=&tracelog=&sourceId=tb.index"}, 
		 * 						     40={"uid":"107188702","isUser":"1","nodeFeature":"40","logTime":"20110619112150","mid":"5618641004102355756","isRefer":"0","url":"http://item.taobao.com/item.htm?id=9519056136"}}
		 * @param output
		 * 
		 * @throws JSONException 
		 * @throws IOException 
		 */
		
		String[] concernKeys = new String[]{"nodeFeature", "isUser", "mid"};
		
		private void outputCompositeTraces(String rawTrace, List<CompositeTrace> cts, Map<String, JSONObject> nodeAttrs, OutputCollector<Text, Text> output, Reporter reporter) throws JSONException, IOException {
			if (cts == null || cts.size() <=0 || nodeAttrs.size() <=0){
				//System.err.println("Invalid CompositeTrace or NodeMap. Nothing will be output!");
				reporter.incrCounter("PathCounters", "WrongCompositeTraces", 1);
				return;
			}
			reporter.incrCounter("PathCounters", "RightCompositeTraces", 1);
			//System.out.println("Composite Traces: " + cts);
			
			String uid = nodeAttrs.values().iterator().next().getString("uid");
			key.set(uid);
			for (CompositeTrace ct : cts) {
				//for (CompositePath cp : ct.cps.values()) {
					StringBuilder sb1 = new StringBuilder();

					sb1.append("Right").append(PizzaConst.SPLIT);
					sb1.append(rawTrace).append(PizzaConst.SPLIT);
					sb1.append(ct.toString());

					value.set(sb1.toString());
					
					output.collect(key, value);
				//}
			}
		}

	}

	/**
	 * 
	 */
	public static class PmReducer extends MapReduceBase implements
			Reducer<Text, Text, LongWritable, Text> {
		private LongWritable l = new LongWritable();
		private Set<String> vs= new HashSet<String> ();
		private Text result = new Text();
		@Override
		public void reduce(Text key, Iterator<Text> values,
				OutputCollector<LongWritable, Text> output, Reporter reporter)
				throws IOException {
			while( values.hasNext()){
				vs.add(values.next().toString());
			}
			for (String v:vs){
				result.set(key + PizzaConst.SPLIT + v);
				output.collect(l, result);
			}
			vs.clear();
		}
	}
	
	public static class ReportOutFormat<K extends WritableComparable<?>, V extends Writable>
			extends MultipleOutputFormat<K, V> {

		private SequenceFileOutputFormat<K, V> thesequenceOutputFormat = null;

		protected RecordWriter<K, V> getBaseRecordWriter(FileSystem fs, 	JobConf job, String name, Progressable arg3) throws IOException {
			if (thesequenceOutputFormat == null) {
				thesequenceOutputFormat = new SequenceFileOutputFormat<K, V>();
			}
			return thesequenceOutputFormat.getRecordWriter(fs, job, name, arg3);
		}

		protected String generateFileNameForKeyValue(K key, V value, String name) {
			String tag = "";
			String v = value.toString();
			if (v.contains("Wrong")){
				tag = "Wrong";
			}else{
				tag = "Right";
			}
			
			if (!tag.equals("")) {
				return tag + "/" + name;
			} else {
				return name;
			}
		}
	}

	@Override
	public int run(String[] args) throws Exception {
		System.out.println(Arrays.asList(args));
		if (args.length < 2) {
			System.err.println("Usage: InvertedListBuilder <in> <out>");
			System.exit(2);
		}

		String invertedList = "";
		for (int i = 0; i < args.length; ++i) {
			if ("-inverted.list".equals(args[i])) {
				invertedList = args[i + 1];
			}
		}

		try {
			JobConf pizzaJob = (JobConf) getConf();

			pizzaJob.set("inverted.list", invertedList);

			pizzaJob.setJobName("PathMatcher");

			pizzaJob.setJarByClass(PathMatcherDiagnose.class);

			pizzaJob.setMapperClass(PmMapper.class);
			//pizzaJob.setNumReduceTasks(0);
			pizzaJob.setReducerClass(PmReducer.class);

			pizzaJob.setMapOutputKeyClass(Text.class);
			pizzaJob.setMapOutputValueClass(Text.class);

			pizzaJob.setOutputKeyClass(LongWritable.class);
			pizzaJob.setOutputValueClass(Text.class);

			
			FileInputFormat.addInputPath(pizzaJob, new Path(args[0]));

			FileSystem fstm = FileSystem.get(pizzaJob);
			Path outDir = new Path(args[1]);
			fstm.delete(outDir, true);
			
			pizzaJob.setOutputFormat(ReportOutFormat.class);
			
			SequenceFileOutputFormat.setCompressOutput(pizzaJob, true);
			SequenceFileOutputFormat.setOutputCompressionType(pizzaJob,	CompressionType.BLOCK);
			SequenceFileOutputFormat.getOutputCompressorClass(pizzaJob,	GzipCodec.class);
			
			TextOutputFormat.setOutputPath(pizzaJob, new Path(args[1]));
			
			JobClient.runJob(pizzaJob);

			return 0;
		} catch (Throwable t) {
			t.printStackTrace();
			return 2;
		}
	}

	public static void main(String[] args) throws Exception {
		int res = ToolRunner.run(new JobConf(), new PathMatcherDiagnose(), args);
		System.exit(res);
	}
}
