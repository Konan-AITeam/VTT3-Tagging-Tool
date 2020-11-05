package com.konantech.spring.service;

import com.konantech.spring.domain.sound.Sound;
import com.konantech.spring.mapper.SoundMapper;
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
public class SoundService {

    @Autowired
    private SoundMapper soundMapper;

    public List<Map> getSoundList(HttpServletRequest request, Principal principal) {
        Map param = RequestUtils.getParameterMap(request);
        param.put("userid", principal.getName());
        return soundMapper.getSoundList(param);
    }

    public int putSoundList(HttpServletRequest request, Principal principal) throws Exception {
        String videoid = RequestUtils.getParameter(request, "idx");
        String[] soundid = RequestUtils.getParameterValues(request, "soundid");
        String[] starttimecode = RequestUtils.getParameterValues(request, "starttimecode");
        String[] endtimecode = RequestUtils.getParameterValues(request, "endtimecode");
        String[] soundtype = RequestUtils.getParameterValues(request, "soundtype");
        String[] delflag = RequestUtils.getParameterValues(request, "delflag");
        int result = 0;
        for (int i = 0; i < soundid.length; i++) {
            HashMap<String, String> param = new HashMap<>();
            param.put("soundid", soundid[i]);
            param.put("videoid", videoid);
            param.put("starttimecode", starttimecode[i]);
            param.put("endtimecode", endtimecode[i]);
            param.put("soundtype", soundtype[i]);
            param.put("userid", principal.getName());
            if (delflag[i].equals("true")) {
                if (!soundid[i].equals(""))
                    result += soundMapper.deleteSoundItems(Integer.parseInt(soundid[i]));
            } else {
                if (soundid[i].equals(""))
                    result += soundMapper.putSoundItem(param);
                else
                    result += soundMapper.setSoundItem(param);
            }
        }
        return result;
    }

    public String getJsonData(Map paramMap) throws Exception {

        Sound soundData = soundMapper.getSoundJson(paramMap);
        List<Sound.SoundResult> soundResultList = soundMapper.getSoundResultsJson(paramMap);
        soundData.setSound_results(soundResultList);

        String resultJson = JSONUtils.jsonStringFromObject(soundData);

        return resultJson;
    }


}
