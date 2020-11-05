package com.konantech.spring.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.konantech.spring.CommonTests;
import com.konantech.spring.domain.content.ContentQuery;
import com.konantech.spring.domain.storyboard.ShotTB;
import com.konantech.spring.mapper.StoryboardMapper;
import com.konantech.spring.util.JSONUtils;
import org.apache.commons.io.FilenameUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.util.*;


public class MamExServiceTest extends CommonTests {

    @Autowired
    MamExService mamExService;

    @Autowired
    StoryboardMapper storyboardMapper;

    @Value("${darc.proxyShotFolder}")
    public String proxyShotFolder;

    @Test
    public void readJson() throws Exception {
        ContentQuery param = new ContentQuery();
        param.setIdx(735);
        List<ShotTB> shotList = storyboardMapper.getShotList(param);
        Map upParam = new HashMap();
        ObjectMapper mapper = new ObjectMapper();
        for(ShotTB shot : shotList){
            String assetFilePath = shot.getAssetfilepath();
            String assetFileName = shot.getAssetfilename();
            assetFileName = assetFileName.substring(0,assetFileName.indexOf("."));
            File dir = new File(proxyShotFolder+"/"+assetFilePath+assetFileName);
            if(dir.exists()&&dir.isDirectory()){
                File[] jsonFils = dir.listFiles((file, name) -> name.toLowerCase().endsWith(".json"));
                Arrays.sort(jsonFils);
                System.out.println(dir.getName());
                if(jsonFils != null && jsonFils.length>0) {
                    Arrays.sort(jsonFils);
                    List json = JSONUtils.jsonFileToList(jsonFils[0]);
                    String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
                    upParam.put("videoid",shot.getVideoid());
                    upParam.put("shotid",shot.getShotid());
                    upParam.put("object",jsonString);
                    System.out.println(jsonFils[0].getName()+"/"+jsonString);
                    storyboardMapper.updateShotItem(upParam);
                }
            }

        }
    }

}