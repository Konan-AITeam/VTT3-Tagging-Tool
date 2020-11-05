package com.konantech.spring.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface CodeService {
    List<Map<String,Object>> getCodeMap(String codeId) throws Exception;
}
