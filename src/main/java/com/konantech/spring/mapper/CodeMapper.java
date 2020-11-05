package com.konantech.spring.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface CodeMapper {

    List<Map<String, Object>> getCodeMap(String codeId) throws Exception;
}
