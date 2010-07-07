add file ../jni/libiconv.so.2;
add file ../jni/libNormalizer.so;
add file ../jni/libTwsTokenization.so;
add file ../zip/tws;
add file ../zip/normalize;
add jars ../dist/tws-norm.jar;

create temporary function normalizer as 'com.taobao.dw.common.udf.NormalizerUdf';