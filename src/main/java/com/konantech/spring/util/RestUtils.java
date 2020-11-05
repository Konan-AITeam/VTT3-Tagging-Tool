package com.konantech.spring.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@Component
public class RestUtils {

    public static URI makeURI(String path) {
        URI uri = UriComponentsBuilder.fromHttpUrl(path).build().toUri();
        return uri;
    }

    public static ResponseEntity makeRestTemplate(URI uri, HttpMethod httpMethod, Map<String, Object> restResponse) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        for( String key : restResponse.keySet() ){
            params.add(key, restResponse.get(key));
        }

        HttpEntity<Map> httpEntity = new HttpEntity<>(restResponse, headers);
        ResponseEntity<Map> responseEntity = null;
        try {
            HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<MultiValueMap<String, Object>>(params, headers);
            responseEntity = restTemplate.exchange(uri, httpMethod, httpEntity, Map.class);
        } catch (HttpStatusCodeException e) {
            MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap();
            Map<String, String> map = JSONUtils.jsonStringToMap(e.getResponseBodyAsString());
            for (String key : map.keySet()) {
                multiValueMap.add(key, map.get(key));
            }
            responseEntity = new ResponseEntity(multiValueMap, e.getStatusCode());
        } catch (RestClientException e) {
            e.getStackTrace();
        }

        return responseEntity;
    }

}