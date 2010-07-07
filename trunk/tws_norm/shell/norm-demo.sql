add file ./libiconv.so.2;
add file ./libNormalizer.so;
add file ./libTwsTokenization.so;
add file ./tws;
add file ./normalize;
add jars ./tws-norm.jar;

create temporary function uniform as 'com.taobao.dw.common.udf.UniformUdf';

select
  ds, uniform(query)
from
  dm_fact_query_h_v2
where
  ds=20100106;