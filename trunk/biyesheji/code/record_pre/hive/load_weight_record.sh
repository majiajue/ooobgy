#!/bin/sh
yday=`date -d -1days +%Y%m%d`

if [ "$#" != "0" ]
then
   yday=$1
fi
echo "yday: $yday"

hive << EOF
alter table weight_record drop partition(ds=$yday);
alter table weight_record add partition(ds=$yday) location '/group/taobao/ooobgy/call_record/weight';
EOF
