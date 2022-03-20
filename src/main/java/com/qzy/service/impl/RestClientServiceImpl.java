package com.qzy.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qzy.entity.TrxInfo;
import com.qzy.service.RestClientService;
import com.qzy.utils.LogicalToESQueryString;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class RestClientServiceImpl implements RestClientService {

    @Autowired
    private RestClient restClient;

    public static final Logger LOGGER = LoggerFactory.getLogger(RestClientServiceImpl.class);

    @Override
    public List<TrxInfo> getTrxInfoByLogical(Map<String, String> params) {
        String logical = params.get("logical");
        String queryString = LogicalToESQueryString.queryStringForRestClient(logical);
        LOGGER.info("queryString ==> " + queryString);

        List<TrxInfo> trxInfos = new ArrayList<>();
        HttpEntity entity = new NStringEntity(queryString, ContentType.APPLICATION_JSON);
        try {
            Response response = restClient.performRequest("POST", "test-charlie/_search", Collections.emptyMap(), entity);
            trxInfos = parseTrxInfo(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return trxInfos;
    }

    private List<TrxInfo> parseTrxInfo(Response response) {
        List<TrxInfo> trxInfos = new ArrayList<>();
        JSONObject jsonObject = null;

        try {
            jsonObject = JSON.parseObject(EntityUtils.toString(response.getEntity()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject hits = (JSONObject) jsonObject.get("hits");
        JSONArray jsonArray = hits.getJSONArray("hits");
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject object = (JSONObject) jsonArray.get(i);
            JSONObject source = (JSONObject) object.get("_source");
            TrxInfo trxInfo = JSON.parseObject(source.toString(), TrxInfo.class);
            trxInfos.add(trxInfo);
        }
        return trxInfos;
    }
}
