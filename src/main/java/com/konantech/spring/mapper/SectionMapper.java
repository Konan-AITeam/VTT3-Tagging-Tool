package com.konantech.spring.mapper;

import com.konantech.spring.domain.section.Qa;
import com.konantech.spring.domain.section.Section;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Mapper
@Repository
public interface SectionMapper {
    List<Map> getSectionList(Map map);

    List<Map> getSectionOfSceneList(Map map);

    int putSectionItem(Map map);

    int setSectionItem(Map map);

    int deleteSectionItems(int sectionid);

    List<Map> getDepictionList(Map param);

    int putDepictionItem(Map map);

    int setDepictionItem(Map map);

    List<Map> getQuestionList(Map param);

    int putQuestionItem(Map map);

    int setQuestionItem(Map map);

    List<Map> getShotQuestionList(Map param);

    int putShotQuestionItem(Map map);

    int setShotQuestionItem(Map map);

    List<Map> getRelationList(Map param);

    int putRelationItem(Map map);

    int setRelationItem(Map map);

    int deleteRelationItems(int relationid);

    Section getSectionJson(Map param);

    List<Section.QaResult> getQaResultsJson(Map param);

    List<Section.QaResult.RelatedPeriodInfo> getRelationJson(Map param);

    List<Section.QaResult.DescriptionInfo> getDepictionJson(Map param);

    List<Section.QaResult.Qa> getQaJson(Map param);

    //2019.09.20 추가
    List<Qa.QaResultInfo> getSectionInfoJson(Map param);

    Integer[] getShotContained(Map param);

    List<Qa.QaResult.QnaInfo> getSectionInfoQa(Map param);

    List<Qa.QaResult.QnaInfo> getShotInfoQa(Map param);

}
