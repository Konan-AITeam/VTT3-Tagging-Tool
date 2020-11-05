package com.konantech.spring.service.impl;

import com.konantech.spring.mapper.CodeMapper;
import com.konantech.spring.service.CodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("codeService")
public class CodeServiceImpl implements CodeService {

    @Autowired
    private CodeMapper codeMapper;

    @Override
    public List<Map<String, Object>> getCodeMap(String codeId) throws Exception {
        return codeMapper.getCodeMap(codeId);
    }
}
