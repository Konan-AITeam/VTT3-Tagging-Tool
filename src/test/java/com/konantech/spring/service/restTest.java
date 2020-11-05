package com.konantech.spring.service;

import org.junit.Test;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

public class restTest {
    @Test
    public void restTest() throws IOException {
        RestTemplate restTemplate = new RestTemplate();

        String url ="http://10.10.18.183:8000/analyzer/";

        HttpHeaders headers = new HttpHeaders();

        //헤더 내임, 값 셋팅.
        String headerName = "";
        String headerValue = "";
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        headers.add(headerName, headerValue);

        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();

        Resource resource = new FileSystemResource("Z:\\proxyshot\\demo\\proxyshot\\2018\\04\\19\\608\\S00000.jpg");
        parameters.add("Content-Type", "multipart/form-data");
        parameters.add("image", resource);
        parameters.add("modules", "friends");

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(parameters, headers);

        ResponseEntity<Map> result = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Map.class);


        System.out.println("result :" + result.getBody());



    }
}
