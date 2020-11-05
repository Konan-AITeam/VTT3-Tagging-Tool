package com.konantech.spring.mapper;

import com.konantech.spring.domain.subtitle.Subtitle;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Mapper
@Repository
public interface SubtitleMapper {
    List<Map> getSubtitleList(Map map);

    int putSubtitleItem(Map map);

    int setSubtitleItem(Map map);

    int deleteSubtitleItems(int Subtitleid);

    Subtitle getSubtitleJson(Map param);

    List<Subtitle.SubtitleResult> getSubtitleResultsJson(Map param);
}
