package com.qzy.controller;

import com.qzy.entity.TrxInfo;
import com.qzy.service.RestClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
