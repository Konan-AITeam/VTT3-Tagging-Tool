package com.konantech.spring.service;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class RestService {
    public LinkedHashMap getImgMetaInfo(String imgPath) throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        String url = "http://10.10.18.183:8000/analyzer/";

        HttpHeaders headers = new HttpHeaders();

        //헤더 내임, 값 셋팅.
        String headerName = "";
        String headerValue = "";
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        headers.add(headerName, headerValue);

        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();

        Resource resource = new FileSystemResource(imgPath);
        parameters.add("Content-Type", "multipart/form-data");
        parameters.add("image", resource);
        parameters.add("modules", "missoh.face, object");

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(parameters, headers);

        ResponseEntity<LinkedHashMap> result = restTemplate.exchange(url, HttpMethod.POST, requestEntity, LinkedHashMap.class);

        LinkedHashMap mResult = result.getBody();

        return mResult;
    }


    public LinkedHashMap getImgQaInfo(String imgPath) throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        String url = "http://10.10.18.193:3333/cap";

        HttpHeaders headers = new HttpHeaders();

        //헤더 내임, 값 셋팅.
        String headerName = "";
        String headerValue = "";
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        headers.add(headerName, headerValue);

        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();

        Resource resource = new FileSystemResource(imgPath);
        parameters.add("Content-Type", "multipart/form-data");
        parameters.add("image", resource);
        parameters.add("modules", "friends");

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(parameters, headers);

        ResponseEntity<LinkedHashMap> result = restTemplate.exchange(url, HttpMethod.POST, requestEntity, LinkedHashMap.class);

        //System.out.println("result :" +result.getBody());
        return result.getBody();
    }

    public LinkedHashMap checkTypingError(String text) throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        String url = "http://10.10.18.231:8008/typo/check";

        HttpHeaders headers = new HttpHeaders();

        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();

        parameters.add("Content-Type", "application/json");
        parameters.add("text", text);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(parameters, headers);

        ResponseEntity<LinkedHashMap> result = restTemplate.exchange(url, HttpMethod.POST, requestEntity, LinkedHashMap.class);

        LinkedHashMap mResult = result.getBody();

        return mResult;
    }

    public LinkedHashMap postDicInfo(String words) throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        String url = "http://10.10.18.231:8008/typo/word";

        HttpHeaders headers = new HttpHeaders();

        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();

        parameters.add("Content-Type", "application/json");
        parameters.add("words", words);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(parameters, headers);

        ResponseEntity<LinkedHashMap> result = restTemplate.exchange(url, HttpMethod.POST, requestEntity, LinkedHashMap.class);

        LinkedHashMap mResult = result.getBody();

        return mResult;
    }

    public LinkedHashMap getDicInfo() throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        String url = "http://10.10.18.231:8008/typo/words";

        HttpHeaders headers = new HttpHeaders();

        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();

        parameters.add("Content-Type", "application/json");

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(parameters, headers);

        ResponseEntity<LinkedHashMap> result = restTemplate.exchange(url, HttpMethod.GET, requestEntity, LinkedHashMap.class);

        LinkedHashMap mResult = result.getBody();

        return mResult;
    }
}
