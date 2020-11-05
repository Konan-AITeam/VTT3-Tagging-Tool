package com.konantech.spring.service;

import com.konantech.spring.mapper.ContentMapper;
import com.konantech.spring.mapper.SectionMapper;
import com.konantech.spring.mapper.SoundMapper;
import com.konantech.spring.mapper.SubtitleMapper;
import com.konantech.spring.util.RequestUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class PopupService {

    @Autowired
    private SubtitleMapper subtitleMapper;

    @Autowired
    private SoundMapper soundMapper;

    @Autowired
    private SectionMapper sectionMapper;

    public List<Map> getSubtitleList(HttpServletRequest request, Principal principal) {
        Map param = RequestUtils.getParameterMap(request);
        param.put("userid", "konan");
        return subtitleMapper.getSubtitleList(param);
    }

    public List<Map> getSoundList(HttpServletRequest request, Principal principal) {
        Map param = RequestUtils.getParameterMap(request);
        param.put("userid", "konan");
        return soundMapper.getSoundList(param);
    }

    public List<Map> getSectionList(HttpServletRequest request, Principal principal) {
        String idx = RequestUtils.getParameter(request, "idx");
        HashMap<String, String> param = new HashMap<>();
        param.put("userid", "konan");
        param.put("idx", idx);

        List<Map> list = sectionMapper.getSectionList(param);

        list.forEach(m -> {
            float rate = Float.parseFloat(RequestUtils.getParameter(request, "rate", "1 "));
            float startframeindex = MapUtils.getFloatValue(m, "startframeindex");
            float endframeindex = MapUtils.getFloatValue(m, "endframeindex");
            m.put("startframeindex", startframeindex / rate);
            m.put("endframeindex", endframeindex / rate);
        });

        return list;
    }

    public List<Map> getQuestionList(HttpServletRequest request, Principal principal) {
        String sectionid = RequestUtils.getParameter(request, "sectionid");
        HashMap<String, String> param = new HashMap<>();
        param.put("userid", "konan");
        param.put("sectionid", sectionid);
        return sectionMapper.getQuestionList(param);
    }

    public List<Map> getShotQuestionList(HttpServletRequest request, Principal principal) {
        String shotid = RequestUtils.getParameter(request, "shotid");
        HashMap<String, String> param = new HashMap<>();
        param.put("userid", "konan");
        param.put("shotid", shotid);
        return sectionMapper.getShotQuestionList(param);
    }
}
