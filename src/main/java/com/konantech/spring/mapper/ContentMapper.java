package com.konantech.spring.mapper;

import com.konantech.spring.domain.content.ContentField;
import com.konantech.spring.domain.content.ContentQuery;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Mapper
@Repository
public interface ContentMapper {

    int getContentCount(ContentQuery param);

    List<Map<String, Object>> getContentList(ContentQuery param);

    List<Map<String, Object>> getAutoTaggingContentList(Map<String, Object> param);

    ContentField getContentItem(ContentQuery param);

    int putContentItem(Map<String, Object> request);

    int updateContentItem(Map<String, Object> request);

    int deleteContentItem(int idx);

    int deleteMetaInfo(int idx);

    int deleteSectionInfo(int idx);

    int deleteRepImg(int idx);

    int insertRepImg(int idx);

    List<Map<String, Object>> getRepImgPath(int idx);

    List<Map<String, Object>> getFileUserList(Map<String, Object> request);

    int checkActiveRun(String idx);

    int updateActiveRun(String idx);

    List<Map<String, Object>> getFrameImgTb(int idx);
}
