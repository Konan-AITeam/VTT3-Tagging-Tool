package com.konantech.spring.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Mapper
@Repository
public interface CustomQueryMapper {

    Map<String, Object> selectItem(String query);

    List<Map<String, Object>> selectList(String query);

}
