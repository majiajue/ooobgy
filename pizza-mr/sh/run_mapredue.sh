输入路径：
/group/taobao/taobao/dw/stb/20110718/pizza_path/
/group/taobao/taobao/dw/result/20110718/PathAnalysis/

输出路径：
/group/taobao/taobao/dw/result/20110718/pizza/invert_list
/group/taobao/taobao/dw/result/20110718/pizza/invert_list 

开发环境：
hadoop jar  -libjars /home/mingfeng/pizza/json.jar /home/mingfeng/pizza/path_analysis.jar com.taobao.dw.pizza.path_analysis.mr.InvertedListBuilder /group/taobao/taobao/dw/stb/20110718/pizza_path/ /group/tbdev/mingfeng/pizza/invert_list/pt=20110711000000 

hadoop jar  -libjars /home/mingfeng/pizza/json.jar /home/mingfeng/pizza//path_analysis.jar com.taobao.dw.pizza.path_analysis.mr.PathMatcher /group/taobao/taobao/dw/result/20110718/PathAnalysis/ /group/tbdev/mingfeng/pizza/result/20110718/pizza/path_match_nodes -inverted.list /group/tbdev/mingfeng/pizza/invert_list/pt=20110711000000 

hadoop jar  -libjars /home/mingfeng/pizza/json.jar /home/mingfeng/pizza/path_analysis.jar com.taobao.dw.pizza.path_analysis.mr.PathMatcherDiagnose /group/taobao/taobao/dw/fact/20110809/PathAnalysis/ /group/tbdev/mingfeng/pizza/result/20110809/pizza/path_match_dignose -inverted.list /group/tbdev/mingfeng/pizza/invert_list/pt=20110809000000

xiaolong.zxl开发环境：
hadoop jar  -libjars /home/zhouxiaolong.pt/pizza/lib/json.jar /home/zhouxiaolong.pt/pizza/dist/path_analysis.jar com.taobao.dw.pizza.path_analysis.mr.InvertedListBuilder /group/taobao/taobao/dw/stb/20110718/pizza_path/ /group/tbdev/mingfeng/pizza/invert_list/pt=20110711000000 

hadoop jar  -libjars /home/zhouxiaolong.pt/pizza/lib/json.jar /home/zhouxiaolong.pt/pizza/dist/path_analysis.jar com.taobao.dw.pizza.path_analysis.mr.PathMatcher /group/taobao/taobao/dw/result/20110718/PathAnalysis/ /group/tbdev/mingfeng/pizza/result/20110718/pizza/path_match_nodes -inverted.list /group/tbdev/mingfeng/pizza/invert_list/pt=20110711000000 

hadoop jar  -libjars /home/zhouxiaolong.pt/pizza/lib/json.jar /home/zhouxiaolong.pt/pizza/dist/path_analysis.jar com.taobao.dw.pizza.path_analysis.mr.PathMatcherDiagnose /group/taobao/taobao/dw/fact/20110809/PathAnalysis/ /group/tbdev/mingfeng/pizza/result/20110809/pizza/path_match_dignose -inverted.list /group/tbdev/mingfeng/pizza/invert_list/pt=20110809000000

hadoop jar  -libjars /home/zhouxiaolong.pt/pizza/lib/json.jar /home/zhouxiaolong.pt/pizza/dist/path_analysis.jar com.taobao.dw.pizza.path_analysis.mr.PathParser /group/taobao/taobao/dw/result/20110718/PathAnalysis/ /group/tbdev/mingfeng/pizza/result/20110718/pizza/user_paths -inverted.list /group/tbdev/mingfeng/pizza/invert_list/pt=20110711000000 


QA环境：

hadoop jar  -libjars /home/taobao/dw_hive/pizza/jar/json.jar /home/taobao/dw_hive/pizza/jar/path_analysis.jar com.taobao.dw.pizza.path_analysis.mr.InvertedListBuilder /group/taobao/taobao/dw/stb/20110718/pizza_path/ /group/taobao/taobao/dw/result/20110718/pizza/invert_list

hadoop jar  -libjars /home/taobao/dw_hive/pizza/json.jar /home/taobao/dw_hive/pizza/path_analysis.jar com.taobao.dw.pizza.path_analysis.mr.PathMatcher /group/taobao/taobao/dw/result/20110718/PathAnalysis /group/taobao/taobao/dw/result/20110718/pizza/path_match_nodes/ -inverted.list /group/taobao/taobao/dw/result/20110718/pizza/invert_list


预发环境：
hadoop jar  -libjars /home/taobao/dw_hive/pizza/jar/json.jar /home/taobao/dw_hive/pizza/jar/path_analysis.jar com.taobao.dw.pizza.path_analysis.mr.InvertedListBuilder /group/taobao/taobao/dw/stb/20110718/pizza_path/ /group/tbpre/taobao/dw/result/20110718/pizza/invert_list

hadoop jar  -libjars /home/taobao/dw_hive/pizza/jar/json.jar /home/taobao/dw_hive/pizza/jar/path_analysis.jar com.taobao.dw.pizza.path_analysis.mr.PathMatcher /group/taobao/taobao/dw/result/20110718/PathAnalysis /group/tbpre/taobao/dw/result/20110718/pizza/path_match_nodes/ -inverted.list /group/tbpre/taobao/dw/result/20110718/pizza/invert_list


生产环境：
hadoop jar  -libjars /home/taobao/dw_hive/pizza/jar/json.jar /home/taobao/dw_hive/pizza/jar/path_analysis.jar com.taobao.dw.pizza.path_analysis.mr.InvertedListBuilder /group/taobao/taobao/dw/stb/20110718/pizza_path/ /group/taobao/taobao/dw/result/20110718/pizza/invert_list

hadoop jar  -libjars /home/taobao/dw_hive/pizza/json.jar /home/taobao/dw_hive/pizza/path_analysis.jar com.taobao.dw.pizza.path_analysis.mr.PathMatcher /group/taobao/taobao/dw/result/20110718/PathAnalysis /group/taobao/taobao/dw/result/20110718/pizza/path_match_nodes/ -inverted.list /group/taobao/taobao/dw/result/20110718/pizza/invert_list

taobaohadoop.sh jar  -libjars /home/taobao/dw_hive/pizza/jar/json.jar /home/taobao/dw_hive/pizza/jar/path_analysis.jar com.taobao.dw.pizza.path_analysis.mr.PathMatcherDiagnose /group/taobao/taobao/dw/fact/20110809/PathAnalysis /group/taobao/taobao/dw/fact/20110809/pizza/path_match_diagnose/ -inverted.list /group/taobao/taobao/dw/result/20110809/pizza/invert_list