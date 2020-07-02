package com.natsumes.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class HttpUtils {

    private static Logger logger = LoggerFactory.getLogger(HttpUtils.class);    //日志记录


    public static String getJson(String url, MultiValueMap<String, String> params) {

        HttpHeaders headers = new HttpHeaders();
        //  请勿轻易改变此提交方式，大部分的情况下，提交方式都是表单提交
        headers.setContentType(MediaType.APPLICATION_JSON);
        return client(url, HttpMethod.GET, headers, params);
    }

    public static String postJson(String url, MultiValueMap<String, String> params) {
        HttpHeaders headers = new HttpHeaders();
        //  请勿轻易改变此提交方式，大部分的情况下，提交方式都是表单提交
        headers.setContentType(MediaType.APPLICATION_JSON);
        return client(url, HttpMethod.POST, headers, params);
    }

    private static String client(String url, HttpMethod method, HttpHeaders headers, MultiValueMap<String, String> params) {
        RestTemplate client = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
        //  执行HTTP请求
        ResponseEntity<String> response = client.exchange(url, method, requestEntity, String.class);
        return response.getBody();
    }

}
