package com.qzy.utils;

import com.qzy.javacc.es.ESQueryTransformer;

public class LogicalToESQueryString {

    public static String queryStringForRestClient(String input) {
        String prefix = "{\"query\":{\"query_string\":{\"query\":\"";
        String end = "\"}}}";
        String queryString = ESQueryTransformer.logicalToQueryString(input);
        return prefix + queryString + end;
    }

}
