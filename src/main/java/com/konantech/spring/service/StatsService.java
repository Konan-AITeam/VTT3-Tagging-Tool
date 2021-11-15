package com.konantech.spring.service;

import com.konantech.spring.domain.sound.Sound;
import com.konantech.spring.mapper.SoundMapper;
import com.konantech.spring.mapper.StatsMapper;
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
public class StatsService {

    @Autowired
    private StatsMapper statsMapper;

    public List<Map> getStatsViewInfoList(HttpServletRequest request,Principal principal) {
        Map param = RequestUtils.getParameterMap(request);
        List<Map> result = null;
        String statstype = RequestUtils.getParameter(request, "statstype");
        String statsitem = RequestUtils.getParameter(request, "statsitem");
        if(!request.isUserInRole("ROLE_ADMIN")){
            param.put("workerid", principal.getName());
        }else{
            param.put("workerid", RequestUtils.getParameter(request, "workerId"));
        }
        switch (statsitem) {
            case "place":
                result = statsMapper.getStatsViewInfoListPlace(param);
                break;
            case "behavior":
                result = statsMapper.getStatsViewInfoListBehavior(param);
                break;
            case "predicate":
                result = statsMapper.getStatsViewInfoListPredicate(param);
                break;
            case "emotion":
                result = statsMapper.getStatsViewInfoListEmotion(param);
                break;
            case "person":
                result = statsMapper.getStatsViewInfoListPerson(param);
                break;
            case "object":
                result = statsMapper.getStatsViewInfoListObject(param);
                break;
            case "related object":
                result = statsMapper.getStatsViewInfoListRelatedObject(param);
                break;
        }

        return result;
    }

    public List<Map> getStatsQaInfoListQaInterrogativeShot(Map param) {
        return statsMapper.getStatsQaInfoListQaInterrogativeShot(param);
    }
    public List<Map> getStatsQaInfoListQaInterrogativeScene(Map param) {
        return statsMapper.getStatsQaInfoListQaInterrogativeScene(param);
    }
    public List<Map> getStatsQaInfoListQaInterrogativeChart(Map param) {
        return statsMapper.getStatsQaInfoListQaInterrogativeChart(param);
    }
    public List<Map> getStatsQaInfoListQaTotalShot(Map param) {
        return statsMapper.getStatsQaInfoListQaTotalShot(param);
    }
    public List<Map> getStatsQaInfoListQaTotalScene(Map param) {
        return statsMapper.getStatsQaInfoListQaTotalScene(param);
    }


    public int getCountStatsViewInfoList(HttpServletRequest request,Principal principal) {
        Map param = RequestUtils.getParameterMap(request);
        int totalcnt = 0;
        String statstype = RequestUtils.getParameter(request, "statstype");
        String statsitem = RequestUtils.getParameter(request, "statsitem");
        if(!request.isUserInRole("ROLE_ADMIN")){
            param.put("workerid", principal.getName());
        }else{
            param.put("workerid", RequestUtils.getParameter(request, "workerId"));
        }
        switch (statsitem) {
            case "place":
                totalcnt = statsMapper.getCountStatsViewInfoListPlace(param);
                break;
            case "behavior":
                totalcnt = statsMapper.getCountStatsViewInfoListBehavior(param);
                break;
            case "predicate":
                totalcnt = statsMapper.getCountStatsViewInfoListPredicate(param);
                break;
            case "emotion":
                totalcnt = statsMapper.getCountStatsViewInfoListEmotion(param);
                break;
            case "person":
                totalcnt = statsMapper.getCountStatsViewInfoListPerson(param);
                break;
            case "object":
                totalcnt = statsMapper.getCountStatsViewInfoListObject(param);
                break;
            case "related object":
                totalcnt = statsMapper.getCountStatsViewInfoListRelatedObject(param);
                break;
        }
        return totalcnt;
    }

    public List<Map> getWorkerList(HttpServletRequest request) {
        Map param = RequestUtils.getParameterMap(request);
        List<Map> result = null;
        String statstype = RequestUtils.getParameter(request, "statstype");
        String statsitem = RequestUtils.getParameter(request, "statsitem");
        if(statstype.equals("viewInfo")){
            switch (statsitem) {
                case "place":
                    result = statsMapper.getWorkerListPlace(param);
                    break;
                case "behavior":
                    result = statsMapper.getWorkerListBehavior(param);
                    break;
                case "predicate":
                    result = statsMapper.getWorkerListPredicate(param);
                    break;
                case "emotion":
                    result = statsMapper.getWorkerListEmotion(param);
                    break;
                case "person":
                    result = statsMapper.getWorkerListPerson(param);
                    break;
                case "object":
                    result = statsMapper.getWorkerListObject(param);
                    break;
                case "related object":
                    result = statsMapper.getWorkerListRelatedObject(param);
                    break;
            }
        }else if(statstype.equals("qaInfo")) {
            if(statsitem.equals("'qaInterrogative'")){
                result = statsMapper.getWorkerListQaInterrogative(param);
            }else{
                result = statsMapper.getWorkerListQaTotal(param);
            }
        }

        return result;
    }
/*

    public String getJsonData(Map paramMap) throws Exception {

        Sound soundData = soundMapper.getSoundJson(paramMap);
        List<Sound.SoundResult> soundResultList = soundMapper.getSoundResultsJson(paramMap);
        soundData.setSound_results(soundResultList);

        String resultJson = JSONUtils.jsonStringFromObject(soundData);

        return resultJson;
    }
*/


}
