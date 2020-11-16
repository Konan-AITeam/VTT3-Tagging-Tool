package com.konantech.spring.service;

import com.konantech.spring.domain.subtitle.Subtitle;
import com.konantech.spring.mapper.SubtitleMapper;
import com.konantech.spring.util.JSONUtils;
import com.konantech.spring.util.RequestUtils;
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
public class SubtitleService {

    @Autowired
    private SubtitleMapper subtitleMapper;

    public List<Map> getSubtitleList(HttpServletRequest request, Principal principal) {
        Map param = RequestUtils.getParameterMap(request);
        param.put("userid", principal.getName());
        return subtitleMapper.getSubtitleList(param);
    }

    public int putSubtitleList(HttpServletRequest request, Principal principal) throws Exception {

        String videoid = RequestUtils.getParameter(request, "idx");
        String[] subtitleid = RequestUtils.getParameterValues(request, "subtitleid");
        String[] starttimecode = RequestUtils.getParameterValues(request, "starttimecode");
        String[] endtimecode = RequestUtils.getParameterValues(request, "endtimecode");
        String[] person = RequestUtils.getParameterValues(request, "person");
        String[] subtitle = RequestUtils.getParameterValues(request, "subtitle");
        String[] delflag = RequestUtils.getParameterValues(request, "delflag");
        int result = 0;

        for (int i = 0; i < subtitleid.length; i++) {
            HashMap<String, String> param = new HashMap<>();
            param.put("subtitleid", subtitleid[i]);
            param.put("videoid", videoid);
            param.put("starttimecode", starttimecode[i]);
            param.put("endtimecode", endtimecode[i]);
            param.put("person", person[i]);
            param.put("subtitle", subtitle[i]);
            param.put("userid", principal.getName());
            if (delflag[i].equals("true")) {
                if (!subtitleid[i].equals(""))
                    result += subtitleMapper.deleteSubtitleItems(Integer.parseInt(subtitleid[i]));
            } else {
                if (subtitleid[i].equals(""))
                    result += subtitleMapper.putSubtitleItem(param);
                else
                    result += subtitleMapper.setSubtitleItem(param);
            }
        }
        return result;
    }

    public String getJsonData(Map paramMap) throws Exception {

        Subtitle subtitleData = new Subtitle();
        List<Subtitle.SubtitleResult> subtitleResultList = subtitleMapper.getSubtitleResultsJson(paramMap);
        subtitleData.setScript(subtitleResultList);

        String resultJson = JSONUtils.jsonStringFromObject(subtitleData);

        return resultJson;
    }

}
