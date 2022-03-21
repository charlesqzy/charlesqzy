# logical-exp-to-esquery
使用Javacc 将逻辑表达式转化为Elasticsearch query_string，并查询结果  

javacc 7.0.10  

elasticsearch 6.4.3  

kibana 6.4.3  


# 注意
javacc 从官网https://javacc.github.io/javacc/#download 下载Source (zip)后  

解压后的文件夹中，缺失target/javacc.jar  

因此需要手动下载javacc-7.0.10.jar 放入target/,并重命名为javacc.jar  


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
