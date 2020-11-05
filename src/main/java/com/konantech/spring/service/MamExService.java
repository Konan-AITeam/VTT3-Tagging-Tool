package com.konantech.spring.service;

import com.konantech.spring.core.RedisRepository;
import com.konantech.spring.domain.content.ContentField;
import com.konantech.spring.domain.content.ContentQuery;
import com.konantech.spring.domain.storyboard.ShotTB;
import com.konantech.spring.mapper.ContentMapper;
import com.konantech.spring.util.JSONUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.Normalizer;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class MamExService {

    @Value("${darc.videoFolder}")
    public String videoFolder;

    @Value("${darc.proxyShotFolder}")
    public String proxyShotFolder;

    @Autowired
    RedisRepository redisRepository;

    @Autowired
    private ContentService contentService;


    @Autowired
    private StoryboardService storyboardService;

    @Autowired
    ContentMapper contentMapper;


    private static Logger logger = LoggerFactory.getLogger(MamExService.class);

    // 임시!
    public String ex_update_jobresult(String request) throws Exception {
        try {
            Map<String, Object> param = JSONUtils.jsonStringToMap(request);
            Map assetinfo = (HashMap) param.get("assetinfo");
            String type = (String) assetinfo.get("type");
            if(StringUtils.equals(type,"shot")) {
                Map shotinfo = (HashMap) assetinfo.get("shotinfo");
                return shot(shotinfo);
            } else if(StringUtils.equals(type,"qualitycheck")) {
                System.out.println(param);
            }

        } catch (Exception e) {
            throw new Exception(e.getMessage(), e);
        }
        return "SUCCESS";
    }


    public String shot(Map shotinfo) throws Exception {

        String filepath = MapUtils.getString(shotinfo, "filepath");
        filepath = Normalizer.normalize(filepath, Normalizer.Form.NFC);

        int idx = MapUtils.getIntValue(shotinfo,"assetid");
        ContentQuery query = new ContentQuery();
        query.setIdx(idx);
        ContentField asset = contentService.getContentItem(query);

        // reset
        if(StringUtils.equals(MapUtils.getString(shotinfo,"sequencetype"),"start")||
                (StringUtils.equals(MapUtils.getString(shotinfo,"sequencetype"),"end")&&
                        MapUtils.getIntValue(shotinfo,"startframeindex")==0)) {
            contentMapper.deleteMetaInfo(idx);
            contentMapper.deleteRepImg(idx);
            storyboardService.deleteShotItems(idx);
        }

        // shot insert
        if (asset != null) {
            ShotTB shotTB = new ShotTB();
            shotTB.setVideoid(idx);
            shotTB.setContent(filepath);
            shotTB.setStarttimecode(MapUtils.getString(shotinfo, "starttimecode"));
            shotTB.setStartframeindex(MapUtils.getIntValue(shotinfo, "startframeindex"));
            shotTB.setEndtimecode(MapUtils.getString(shotinfo, "endtimecode"));
            shotTB.setEndframeindex(MapUtils.getIntValue(shotinfo, "endframeindex"));
            shotTB.setAssetfilepath(FilenameUtils.getPath(filepath));
            shotTB.setAssetfilename(FilenameUtils.getName(filepath));
            Object object = shotinfo.get("object");
            storyboardService.putShotItem(shotTB);
        }

        if(StringUtils.equals(MapUtils.getString(shotinfo,"sequencetype"),"end")) {
            //end, status update
            LinkedHashMap<String, Object> param = new LinkedHashMap<>();
            param.put("idx", idx);
            param.put("catalogstatus", 3000);
            contentService.updateContentItem(param);
        }
        return "success";
    }


    public String callMamEx(String trName, String request) throws Exception {

        if(StringUtils.equals(trName,"ex_update_jobresult")) {
            return ex_update_jobresult(request);
        }

        String response = "";

        // ToDo Throw error : no exist mamex

        return response;
    }


    public String callMamEx( String request) throws Exception {

        return ex_update_jobresult(request);
    }
}


