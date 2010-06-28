#!/bin/bash

rm -f libAliWSNew.so
rm -f libAliWSTokenization.so
rm -f libTwsTokenization.so
rm -f libNormalizer.so

echo "build AliWS101 lib ..."
g++ -g -Wall -shared -o libAliWS101.so -fPIC -I/usr/local/include/AliWS -I/usr/java/jdk1.6.0_07/include -I/usr/java/jdk1.6.0_07/include/linux -I. -L/usr/local/lib -L/home/a/lib64 com_alimama_loganalyzer_jobs_mrs_algo_AliWS101.cpp -ldl -lAliWS

echo "build AliWS086 lib ..."
g++34 -g -Wall -shared -o libAliWS086.so -fPIC -I/usr/local/include/AliWS -I/usr/java/jdk1.6.0_07/include -I/usr/java/jdk1.6.0_07/include/linux -I. -L/usr/local/lib -L/home/a/lib64 com_alimama_loganalyzer_jobs_mrs_algo_AliWS086.cpp -ldl -lAliWS

echo "build tws lib ..."
g++34 -g -Wall -shared -o libTwsTokenization.so -fPIC -I/usr/local/include/tws -I/usr/java/jdk1.6.0_07/include -I/usr/java/jdk1.6.0_07/include/linux -I. -L/usr/local/lib -L/home/a/lib64 com_alimama_loganalyzer_jobs_mrs_algo_TwsTokenization.cpp -ldl -ltws

echo "build normalization lib ..."
g++34 -g -Wall -shared -o libNormalizer.so -fPIC -I/home/a/include/phoenix/normalize -I/usr/java/jdk1.6.0_07/include -I/usr/java/jdk1.6.0_07/include/linux -I. -L/usr/local/lib -L/home/a/lib64/ com_alimama_loganalyzer_jobs_mrs_algo_Normalizer.cpp /home/a/lib64/libnormalize.a /usr/local/lib/libtws.a /home/a/lib64/libiconv.so -ldl
