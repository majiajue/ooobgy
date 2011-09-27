1.作业要求中的部分不重复说明，以下说明一些不同的地方
2.工具最后打包为一个jar，放在./dist/commons_utility.jar
3."./gen"是自动生成的代码模板
4.ant all执行所有任务，ant默认只执行到打包./dist/commons_utility.jar
5.代码的编码为UTF-8, javadoc的编码为GBK，（可在build.xml中看到）
6.由于时间和精力的限制，单元测试没有覆盖所有的共有方法。
    重点测试了com.topcoder.commons.utils.ParameterCheckUtility
    和com.topcoder.commons.utils.ValidationUtility
    两个类的代码覆盖率为98%，总代码覆盖率为66%
7.javadoc任务中的一个warning是由于javadoc处理枚举类型时的固有问题，暂时没想到好的解决办法。可以考虑不用枚举来实现
8.工具的javadoc注释沿用了生成的注释，没有做太大的整理，只是加了作者签名和做了些小调整，格式上没有整理，所以美观上稍欠不足
9.一方面由于模块代码逻辑比较简单，一方面由于时间和精力的限制，代码注释加得比较少，一些关键实现还是加了注释了
