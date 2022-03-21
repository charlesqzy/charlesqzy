package com.qzy.controller;
import com.qzy.entity.TrxInfo;
import com.qzy.service.RestClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
/**
 * @author: Charlie Qzy
 * @date: 2022/3/21
 * @description:
 * @version: 1.0.0
 */
@RestController
@RequestMapping("/rest")
public class RestClientController {

    @Autowired
    private RestClientService restClientService;

    @GetMapping("/hello")
    public String Hello() {
        return "Hello Javacc and ES!";
    }

    @PostMapping("/logical")
    public List<TrxInfo> getTrxInfoByLogical(@RequestBody Map<String, String> params) {
        return restClientService.getTrxInfoByLogical(params);
    }

}
