package com.qzy.utils;

import com.qzy.javacc.es.ESQueryTransformer;
/**
 * @author: Charlie Qzy
 * @date: 2022/3/21
 * @description:
 * @version: 1.0.0
 */
public class LogicalToESQueryString {

    public static String queryStringForRestClient(String input) {
        String prefix = "{\"query\":{\"query_string\":{\"query\":\"";
        String end = "\"}}}";
        String queryString = ESQueryTransformer.logicalToQueryString(input);
        return prefix + queryString + end;
    }

}
