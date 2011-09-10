package com.taobao.dw.pizza.path_analysis.mr;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import com.taobao.dw.pizza.path_analysis.core.PizzaConst;
import com.taobao.dw.pizza.path_analysis.core.algo.PizzaJSON;
import com.taobao.dw.pizza.path_analysis.core.pojo.AtomPath;

/**
 * 倒排表构造
 * 
 * 输入格式为：
 * 					PathId^ANodeId1(+/-)NodeId2(+/-)NodeId3... 
 * 输出格式为：
 * 					NodeId1-NodeId2^AP1,P2,P3...^AEscapeNode1,EscapeNode2...
 * 
 * @author 明风
 * @created on 2011-06-21
 * 
 */
public class InvertedListBuilder extends Configured implements Tool {

	public static class IlbMapper extends MapReduceBase implements
			Mapper<LongWritable, Text, Text, Text> {
		private static final String NO_BRANCH_PATH = "1";
		Text outputKey = new Text();
		Text outputValue = new Text();
		

		@Override
		/**
		 * 将路径拆分为节点组合（nodeId1+-nodeId2)，以节点组合为key输出，路径id,下一节点id,为value
		 */
		
		public void map(LongWritable key, Text value,
				OutputCollector<Text, Text> output, Reporter reporter)
				throws IOException {
			String line = value.toString();
			String[] values = line.split(PizzaConst.TAB_SPLIT);
			//System.out.println("List:" + Arrays.asList(values));
			
			if(values.length > 9){
				String pathLevel = values[9];
				//只处理没有分叉的路径
				if (!pathLevel.equals(NO_BRANCH_PATH)){
					return;
				}
				
				if(!values[8].startsWith("{\"PATH_KEY\""))
					return;
				
				List<AtomPath> aps = PizzaJSON.parsePathExp(values[8]); 
					
				for (AtomPath ap: aps) {
					outputKey.set(ap.getPathKey());
					outputValue.set(ap.getPathAttr());
					output.collect(outputKey, outputValue);
				}
			}
		}

	}


	public static class IlbReducer extends MapReduceBase implements
			Reducer<Text, Text, Text, Text> {
		Text outputKey = new Text();
		Text outputValue = new Text();

		StringBuilder pathAttrs = new StringBuilder();
		Set<String> valueSet= new HashSet<String>(50);

		@Override
		/**
		 * 接收节点组合（nodeId1-nodeId2)，以及后续路径和下一节点，将路径id和下一节点id，拼接组合后输出
		 */
		public void reduce(Text key, Iterator<Text> values,
				OutputCollector<Text, Text> output, Reporter reporter)
				throws IOException {
			while (values.hasNext()) {
				valueSet.add(values.next().toString());				
			}
			
			Iterator<String> ivs = valueSet.iterator();
			while (ivs.hasNext()){
				pathAttrs.append(ivs.next());
				if (ivs.hasNext())
					pathAttrs.append(PizzaConst.SECOND_SPLIT);
			}

			outputKey.set(key.toString() + PizzaConst.SPLIT + pathAttrs.toString());
			output.collect(outputKey, null);
			
			valueSet.clear();
			pathAttrs.setLength(0);
		}

	}
	

	@Override
	public int run(String[] args) throws Exception {
		 if (args.length < 2) {
	            System.err.println("Usage: InvertedListBuilder <in> <out>");
	            System.exit(2);
	        }

	        try {
				JobConf pizzaJob = (JobConf) getConf();

				pizzaJob.setJobName("InvertedListBuilder");

				pizzaJob.setJarByClass(InvertedListBuilder.class);
				
				pizzaJob.setMapperClass(IlbMapper.class);
				pizzaJob.setReducerClass(IlbReducer.class);
				
				pizzaJob.setMapOutputKeyClass(Text.class);
				pizzaJob.setMapOutputValueClass(Text.class);

				pizzaJob.setOutputKeyClass(Text.class);
				pizzaJob.setOutputValueClass(IntWritable.class);

				FileInputFormat.addInputPath(pizzaJob, new Path(args[0]));
				
				FileSystem fstm = FileSystem.get(pizzaJob);
				Path outDir = new Path(args[1]);
				fstm.delete(outDir, true);
				
				FileOutputFormat.setOutputPath(pizzaJob, new Path(args[1]));

				JobClient.runJob(pizzaJob);
   
				return 0;
			} catch (Throwable t) {
				t.printStackTrace();
				return 2;
			}
	}

    public static void main(String[] args) throws Exception {
        int res = ToolRunner.run(new JobConf(), new InvertedListBuilder(), args);
        System.exit(res);
    }

}
