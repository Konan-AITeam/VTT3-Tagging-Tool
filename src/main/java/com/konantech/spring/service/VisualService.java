package com.konantech.spring.service;

import com.konantech.spring.domain.vtt.FrameImage;
import com.konantech.spring.domain.vtt.RepImgVo;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public interface VisualService {
    int getPutRepreImg(Map param) throws Exception;

    int getCntRepreImg(Map map) throws Exception;

    //    List<RepImgVo> getSelectRepImg(Map map) throws Exception;
    RepImgVo getSelectRepImg(Map map) throws Exception;

    RepImgVo getSelectPrevRepImg(Map map) throws Exception;

    int getPutMetaInfo(Map map, List list) throws Exception;

    int getPutMetaInfo(Map map) throws Exception;

    LinkedHashMap<String, Object> getSelectMetaInfo(Map<String, Object> map) throws Exception;

    LinkedHashMap<String, Object> getSelectPrevMetaInfo(Map<String, Object> map) throws Exception;

    LinkedHashMap<String, Object> getSelectFrameImgInfo(Map<String, Object> map) throws Exception;

    Map getRepImgInfo(Map repMap) throws Exception;

    int getPutSecInfo(Map<String, Object> pramMap) throws Exception;

    List<LinkedHashMap<String, Object>> getSecInfo(Map<String, Object> pramMap) throws Exception;

    String getJsonData(Map<String, Object> map) throws Exception;

    String getNewJsonData(Map<String, Object> map) throws Exception;

    String getShotJsonData(Map<String, Object> map) throws Exception;

    int deleteRepImg(Map<String, Object> map) throws Exception;

    List<Map> getWorkerList(Map<String, Object> pramMap) throws Exception;

    /* lee.jaewook */
    ArrayList<FrameImage> getFrameImageList(HttpServletRequest request, Principal principal) throws Exception;

    LinkedHashMap<String, Object> getSelectMetaInfo(HttpServletRequest request, Principal principal) throws Exception;

    int getPutMetaInfo2(HttpServletRequest request, Principal principal) throws Exception;
}
