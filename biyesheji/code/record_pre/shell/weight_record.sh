#!/bin/bash

if [ $# -eq 1 ];
then
    export jobconf_file=$1
fi

class_root=edu.zju.cs.ooobgy.mr
laucher=$class_root.common.HadoopLauncher
job_class=$class_root.jobs.WeightRecord
jar_path=../dist/record_base_mr.jar
hadoop_cmd=hadoop

out_path=/group/taobao/ooobgy/call_record/weight
$hadoop_cmd fs -rmr $out_path
$hadoop_cmd jar $jar_path $laucher $job_class $jobconf_file
