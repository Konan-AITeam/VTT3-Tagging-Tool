package com.konantech.spring.mapper;

import com.konantech.spring.domain.sound.Sound;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Mapper
@Repository
public interface StatsMapper {
    List<Map> getStatsViewInfoListPlace(Map map);
    List<Map> getStatsViewInfoListBehavior(Map map);
    List<Map> getStatsViewInfoListPredicate(Map map);
    List<Map> getStatsViewInfoListEmotion(Map map);
    List<Map> getStatsViewInfoListPerson(Map map);
    List<Map> getStatsViewInfoListObject(Map map);
    List<Map> getStatsViewInfoListRelatedObject(Map map);
    List<Map> getStatsQaInfoListQaTotalShot(Map map);
    List<Map> getStatsQaInfoListQaTotalScene(Map map);
    List<Map> getStatsQaInfoListQaInterrogativeShot(Map map);
    List<Map> getStatsQaInfoListQaInterrogativeScene(Map map);
    List<Map> getStatsQaInfoListQaInterrogativeChart(Map map);

    int getCountStatsViewInfoListPlace(Map map);
    int getCountStatsViewInfoListBehavior(Map map);
    int getCountStatsViewInfoListPredicate(Map map);
    int getCountStatsViewInfoListEmotion(Map map);
    int getCountStatsViewInfoListPerson(Map map);
    int getCountStatsViewInfoListObject(Map map);
    int getCountStatsViewInfoListRelatedObject(Map map);

    List<Map> getWorkerListPlace(Map map);
    List<Map> getWorkerListBehavior(Map map);
    List<Map> getWorkerListPredicate(Map map);
    List<Map> getWorkerListEmotion(Map map);
    List<Map> getWorkerListPerson(Map map);
    List<Map> getWorkerListObject(Map map);
    List<Map> getWorkerListRelatedObject(Map map);
    List<Map> getWorkerListQaInterrogative(Map map);
    List<Map> getWorkerListQaTotal(Map map);
}
