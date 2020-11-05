package com.konantech.spring.domain.content;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ResultOGQ {

    private String jsonPath;
    private List<Map<String, Object>> imgList;

}