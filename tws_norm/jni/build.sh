#!/bin/bash

rm -f libTwsTokenization.so
rm -f libNormalizer.so

echo "build tws lib ..."
g++34 -g -Wall -shared -o libTwsTokenization.so -fPIC -I/usr/local/include/tws -I/usr/java/jdk1.6.0_07/include -I/usr/java/jdk1.6.0_07/include/linux -I. -L/usr/local/lib -L/home/a/lib64 com_taobao_dw_comm_tws_TwsTokenization.cpp -ldl -ltws

echo "build normalization lib ..."
g++34 -g -Wall -shared -o libNormalizer.so -fPIC -I/home/a/include/phoenix/normalize -I/usr/java/jdk1.6.0_07/include -I/usr/java/jdk1.6.0_07/include/linux -I. -L/usr/local/lib -L/home/a/lib64/ com_taobao_dw_comm_tws_Normalizer.cpp /home/a/lib64/libnormalize.a /usr/local/lib/libtws.a /home/a/lib64/libiconv.so -ldl
