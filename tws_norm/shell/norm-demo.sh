#!/bin/sh

export LD_LIBRARY_PATH="./":$LD_LIBRARY_PATH

java -cp ./tws-norm.jar com.taobao.dw.comm.tws.TwsNorm "$1"
