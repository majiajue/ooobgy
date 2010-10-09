#!/bin/bash

export jobconf_file=RecordBase.xml

if [ $# -eq 1 ];
then
    export jobconf_file=$1
fi

date_filter=0
if [ $# -eq 2 ];
then
    export date_filter=$2
fi

class_root=edu.zju.cs.ooobgy.mr
laucher=$class_root.common.HadoopLauncher
job_class=$class_root.jobs.DateFilter
jar_path=../dist/record_base_mr.jar
hadoop_cmd=hadoop

out_path=/group/taobao/ooobgy/call_record/date_filter/$date_filter
$hadoop_cmd fs -rmr $out_path
$hadoop_cmd jar $jar_path $laucher $job_class $jobconf_file date_filter=$date_filter
