package com.qzy.service;

import com.qzy.entity.TrxInfo;

import java.util.List;
import java.util.Map;

public interface RestClientService {
    List<TrxInfo> getTrxInfoByLogical(Map<String,String> params);
}
