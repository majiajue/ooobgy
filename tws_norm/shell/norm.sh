#!/bin/sh

export LD_LIBRARY_PATH="../jni/":$LD_LIBRARY_PATH

java -cp ../dist/tws_norm.jar com.taobao.dw.comm.tws.TwsNorm "$1"
