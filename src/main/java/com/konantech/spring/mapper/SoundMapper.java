package com.konantech.spring.mapper;

import com.konantech.spring.domain.sound.Sound;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Mapper
@Repository
public interface SoundMapper {
    List<Map> getSoundList(Map map);

    int putSoundItem(Map map);

    int setSoundItem(Map map);

    int deleteSoundItems(int Soundid);

    Sound getSoundJson(Map param);

    List<Sound.SoundResult> getSoundResultsJson(Map param);
}
