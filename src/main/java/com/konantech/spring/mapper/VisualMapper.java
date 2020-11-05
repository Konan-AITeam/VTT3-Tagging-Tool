package com.konantech.spring.mapper;

import com.konantech.spring.domain.vtt.RepImgVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface VisualMapper {

    int getPutRepreImg(Map param) throws Exception;

    int getCntRepreImg(Map map) throws Exception;

    //    List<RepImgVo> getSelectRepImg(Map map) throws Exception;
    RepImgVo getSelectRepImg(Map map) throws Exception;

    RepImgVo getSelectPrevRepImg(Map map) throws Exception;

    int getPutMetaInfo(Map map) throws Exception;

    LinkedHashMap<String, Object> getSelectMetaInfo(Map<String, Object> map) throws Exception;

    LinkedHashMap<String, Object> getSelectPrevMetaInfo(Map<String, Object> map) throws Exception;

    LinkedHashMap<String, Object> getSelectFrameImgInfo(Map<String, Object> map) throws Exception;

    Map getRepImgInfo(Map map) throws Exception;

    int getPutSecInfo(Map<String, Object> map) throws Exception;

    List<LinkedHashMap<String, Object>> getSecInfo(Map<String, Object> map) throws Exception;

    List<String> getJsonData(Map map) throws Exception;

    int deleteRepImg(Map map) throws Exception;

    List<Map> getWorkerList(Map map) throws Exception;

    /* lee.jaewook */
    List<Map> getFrameImageInfo(Map map) throws Exception;

    int getPutMetaInfo2(Map map) throws Exception;

    int getPutUpdateMetaInfo2(Map map) throws Exception;

    List<String> getShotMetaJsonData(Map map) throws Exception;

    List<Map> getMetaShotInfo(Map map) throws Exception;

    List<LinkedHashMap> getShotList(Map map) throws Exception;

    String getVidInfo(Map map) throws Exception;
}
