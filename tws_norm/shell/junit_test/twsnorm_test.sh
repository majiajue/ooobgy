#!/bin/sh

#sh ../../jni/build.sh

export LD_LIBRARY_PATH="../../jni/":$LD_LIBRARY_PATH

java -classpath ../../lib/junit-4.8.2.jar:../../dist/tws-norm.jar junit.textui.TestRunner com.taobao.dw.comm.tws.TwsNorm_Test