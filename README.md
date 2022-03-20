# logical-exp-to-esquery
Javacc 7.0.10
Elasticsearch 6.4.3
QueryString query

# 使用es templet
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
