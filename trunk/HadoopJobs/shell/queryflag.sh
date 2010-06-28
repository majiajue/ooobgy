#!/bin/sh

#sh ../jni/build1.sh

export LD_LIBRARY_PATH="./jni/":$LD_LIBRARY_PATH

java -cp ./dist/keywords.jar com.alimama.loganalyzer.jobs.mrs.algo.query.QueryFlagJudger "$1" $2 
