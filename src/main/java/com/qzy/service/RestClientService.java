package com.qzy.service;

import com.qzy.entity.TrxInfo;

import java.util.List;
import java.util.Map;
/**
 * @author: Charlie Qzy
 * @date: 2022/3/21
 * @description:
 * @version: 1.0.0
 */
public interface RestClientService {
    List<TrxInfo> getTrxInfoByLogical(Map<String,String> params);
}
