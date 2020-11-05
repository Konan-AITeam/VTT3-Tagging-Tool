package com.konantech.spring.mapper;

import com.konantech.spring.domain.content.ContentQuery;
import com.konantech.spring.domain.storyboard.ShotTB;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Mapper
@Repository
public interface StoryboardMapper {

    int getShotCount(ContentQuery param);

    List<ShotTB> getShotList(ContentQuery param);

    List<ShotTB> getImgEditShotList(ContentQuery param);

    List<ShotTB> getSectionOfSceneList(ContentQuery param);

    int putShotItem(ShotTB shotTB);

    int deleteShotItems(int videoid);

    int updateShotItem(Map param);

    List<ShotTB> getVisualJsonList(Map param);

}
