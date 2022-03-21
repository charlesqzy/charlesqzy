# logical-exp-to-esquery
使用Javacc 将逻辑表达式转化为Elasticsearch query_string，并查询结果  

javacc 7.0.10  

elasticsearch 6.4.3  

kibana 6.4.3  


# 注意
javacc 从官网https://javacc.github.io/javacc/#download 下载Source (zip)后  

解压后的文件夹中，缺失target/javacc.jar，运行javacc命令是，报“无法找到主类”错误。  

需要手动下载javacc-7.0.10.jar 放入target/,并重命名为javacc.jar  

# 启动ES
下载elasticsearch6.4.3 在windows中解压，进入bin/，运行elasticsearch.bat  
启动成功后，访问``` http://localhost:9200/```，出现以下内容：  
```
{
  "name" : "dtAgOwK",
  "cluster_name" : "chuck-application",
  "cluster_uuid" : "6TRpLUWAQJe7tA04PK6gzQ",
  "version" : {
    "number" : "6.4.3",
    "build_flavor" : "default",
    "build_type" : "zip",
    "build_hash" : "fe40335",
    "build_date" : "2018-10-30T23:17:19.084789Z",
    "build_snapshot" : false,
    "lucene_version" : "7.4.0",
    "minimum_wire_compatibility_version" : "5.6.0",
    "minimum_index_compatibility_version" : "5.0.0"
  },
  "tagline" : "You Know, for Search"
}
```
# 启动kibana
下载kibana 6.4.3，解压后，进入bin/,运行kibana.bat  
进入 ```http://localhost:5601/```，进入Dev Tools  

# 使用kibana 创建index template
```
POST _template/test-charlie-template
{
  "template": "test-charlie-*",
  "order": 0,
  "settings": {
    "number_of_shards": 1,
    "number_of_replicas": 1
  },
  "aliases": {
    "test-charlie": {}
  },
  "mappings": {
    "trx-info": {
      "dynamic": "strict",
      "_all": {
        "enabled": false
      },
      "_source": {
        "enabled": true
      },
      "properties": {
        "id": {
          "type": "keyword"
        },
        "city": {
          "type": "keyword"
        },
        "trx_amt": {
          "type": "double"
        },
        "trx_cnt": {
          "type": "integer"
        },
        "trx_desc": {
          "type": "text"
        }
      }
    }
  }
}
```
# 使用kibana 插入一些数据
```
POST test-charlie-1/trx-info
{
  "id":"trx-00001",
  "city":"上海",
  "trx_amt":89032.45,
  "trx_cnt":90332,
  "trx_desc":"上海1月份交易数据"
}

POST test-charlie-2/trx-info
{
  "id":"trx-00002",
  "city":"上海",
  "trx_amt":99033.67,
  "trx_cnt":100332,
  "trx_desc":"上海2月份交易数据"
}

POST test-charlie-1/trx-info
{
  "id":"trx-00003",
  "city":"苏州",
  "trx_amt":8848.48,
  "trx_cnt":88332,
  "trx_desc":"苏州测试数据"
}

POST test-charlie-1/trx-info
{
  "id":"trx-00004",
  "city":"常州",
  "trx_amt":7898
}
```
# 启动服务
# Postman Post请求
```
{
	"logical":"trx_amt between 1000 and 100000 "
}
```
日志输出query string ：  
```{"query":{"query_string":{"query":"(trx_amt:[1000 TO 100000])"}}}```
```
[
    {
        "id": "trx-00001",
        "city": "上海",
        "trxAmt": 89032.45,
        "trxCnt": 90332,
        "trxDesc": "上海1月份交易数据"
    },
    {
        "id": "trx-00004",
        "city": "常州",
        "trxAmt": 7898,
        "trxCnt": null,
        "trxDesc": null
    },
    {
        "id": "trx-00003",
        "city": "苏州",
        "trxAmt": 8848.48,
        "trxCnt": 88332,
        "trxDesc": "苏州测试数据"
    },
    {
        "id": "trx-00002",
        "city": "上海",
        "trxAmt": 99033.67,
        "trxCnt": 100332,
        "trxDesc": "上海2月份交易数据"
    }
]
```

# Post 请求2
```
{
	"logical":"trx_amt between 1000 and 100000 and city not in (\"上海\",\"常州\")"
}
```
日志输出 query string ```{"query":{"query_string":{"query":"(trx_amt:[1000 TO 100000]) AND NOT city : (\"上海\",\"常州\")"}}}```  
查询结果  
```
[
    {
        "id": "trx-00003",
        "city": "苏州",
        "trxAmt": 8848.48,
        "trxCnt": 88332,
        "trxDesc": "苏州测试数据"
    }
]
```
