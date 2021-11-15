package com.konantech.spring.service;

import com.konantech.spring.domain.content.ContentQuery;
import com.konantech.spring.domain.section.Qa;
import com.konantech.spring.domain.section.Section;
import com.konantech.spring.domain.storyboard.ShotTB;
import com.konantech.spring.mapper.ContentMapper;
import com.konantech.spring.mapper.SectionMapper;
import com.konantech.spring.util.JSONUtils;
import com.konantech.spring.util.RequestUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class SectionService {

    @Autowired
    private SectionMapper sectionMapper;

    @Autowired
    private ContentMapper contentMapper;

    public List<Map> getSectionList(HttpServletRequest request, Principal principal) {
        String idx = RequestUtils.getParameter(request, "idx");
        String qaSearchWord = RequestUtils.getParameter(request, "qaSearchWord");
        HashMap<String, String> param = new HashMap<>();
        param.put("userid", principal.getName());
        param.put("idx", idx);
        param.put("qaSearchWord", qaSearchWord);


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


    public List<Map> getQaChkSectionList(HttpServletRequest request, Principal principal) {
        String idx = RequestUtils.getParameter(request, "idx");
        String qaSearchWord = RequestUtils.getParameter(request, "qaSearchWord");
        HashMap<String, String> param = new HashMap<>();
        param.put("userid", principal.getName());
        param.put("idx", idx);
        param.put("qaSearchWord", qaSearchWord);
        if(request.isUserInRole("ROLE_ADMIN")){
            param.put("roleadmin", "admin");
        }else{
            param.put("roleadmin", "user");
        }
        List<Map> list = sectionMapper.getQaChkSectionList(param);

        list.forEach(m -> {
            float rate = Float.parseFloat(RequestUtils.getParameter(request, "rate", "1 "));
            float startframeindex = MapUtils.getFloatValue(m, "startframeindex");
            float endframeindex = MapUtils.getFloatValue(m, "endframeindex");
            m.put("startframeindex", startframeindex / rate);
            m.put("endframeindex", endframeindex / rate);
        });

        return list;
    }

    public int putQaSection(HttpServletRequest request, Principal principal) throws Exception {

        String videoid = RequestUtils.getParameter(request, "idx");
        String[] sectionid = RequestUtils.getParameterValues(request, "sectionid");
        String[] startshotid = RequestUtils.getParameterValues(request, "startshotid");
        String[] endshotid = RequestUtils.getParameterValues(request, "endshotid");
        String[] sectionname = RequestUtils.getParameterValues(request, "sectionname");
        String[] delflag = RequestUtils.getParameterValues(request, "delflag");
        int result = 0;
        for (int i = 0; i < sectionid.length; i++) {
            HashMap<String, String> param = new HashMap<>();
            param.put("sectionid", sectionid[i]);
            param.put("videoid", videoid);
            param.put("startshotid", startshotid[i]);
            param.put("endshotid", endshotid[i]);
            param.put("sectionname", sectionname[i]);
            param.put("userid", principal.getName());
            if (delflag[i].equals("true")) {
                if (!sectionid[i].equals(""))
                    result += sectionMapper.deleteSectionItems(Integer.parseInt(sectionid[i]));
            } else {
                if (sectionid[i].equals(""))
                    result += sectionMapper.putSectionItem(param);
                else
                    result += sectionMapper.setSectionItem(param);
            }
        }
        return result;
    }

    public List<Map> getDepictionList(HttpServletRequest request, Principal principal) {
        String sectionid = RequestUtils.getParameter(request, "sectionid");
        HashMap<String, String> param = new HashMap<>();
        param.put("userid", principal.getName());
        param.put("sectionid", sectionid);
        return sectionMapper.getDepictionList(param);
    }

    public int putDepiction(HttpServletRequest request, Principal principal) throws Exception {
        String sectionid = RequestUtils.getParameter(request, "sectionid");
        String[] depictionid = RequestUtils.getParameterValues(request, "depictionid");
        String[] depiction = RequestUtils.getParameterValues(request, "depiction");
        int result = 0;
        for (int i = 0; i < depictionid.length; i++) {
            HashMap<String, String> param = new HashMap<>();
            param.put("depictionid", depictionid[i]);
            param.put("sectionid", sectionid);
            param.put("depiction", depiction[i]);
            param.put("userid", principal.getName());
            if (depictionid[i].equals(""))
                result += sectionMapper.putDepictionItem(param);
            else
                result += sectionMapper.setDepictionItem(param);
        }
        return result;
    }

    public List<Map> getQuestionList(HttpServletRequest request, Principal principal) {
        String sectionid = RequestUtils.getParameter(request, "sectionid");
        String workerId = RequestUtils.getParameter(request, "workerId");
        HashMap<String, String> param = new HashMap<>();
        if(workerId == null || "".equals(workerId)){
            param.put("userid", principal.getName());
        }else{
            param.put("userid", workerId);
        }
        param.put("sectionid", sectionid);
        return sectionMapper.getQuestionList(param);
    }
    public List<Map> getQaChkQuestionList(HttpServletRequest request, Principal principal) {
        String sectionid = RequestUtils.getParameter(request, "sectionid");
        String workerId = RequestUtils.getParameter(request, "workerId");
        String questionId = RequestUtils.getParameter(request, "questionId");
        HashMap<String, String> param = new HashMap<>();
        if(workerId == null || "".equals(workerId)){
            param.put("userid", principal.getName());
        }else{
            param.put("userid", workerId);
        }
        param.put("sectionid", sectionid);
        param.put("questionid", questionId);
        return sectionMapper.getQaChkQuestionList(param);
    }

    public int putQuestionList(HttpServletRequest request, Principal principal) throws Exception {
        String sectionid = RequestUtils.getParameter(request, "sectionid");
        String[] questionid = RequestUtils.getParameterValues(request, "questionid");
        String[] questiontype = RequestUtils.getParameterValues(request, "questiontype");
        String[] question = RequestUtils.getParameterValues(request, "question");
        String[] answer = RequestUtils.getParameterValues(request, "answer");
        String[] wrong_answer1 = RequestUtils.getParameterValues(request, "wrong_answer1");
        String[] wrong_answer2 = RequestUtils.getParameterValues(request, "wrong_answer2");
        String[] wrong_answer3 = RequestUtils.getParameterValues(request, "wrong_answer3");
        String[] wrong_answer4 = RequestUtils.getParameterValues(request, "wrong_answer4");
        int result = 0;
        for (int i = 0; i < questionid.length; i++) {
            HashMap<String, String> param = new HashMap<>();
            param.put("questionid", questionid[i]);
            param.put("questiontype", questiontype[i]);
            param.put("sectionid", sectionid);
            param.put("question", question[i]);
            param.put("answer", answer[i]);
            param.put("wrong_answer1", wrong_answer1[i]);
            param.put("wrong_answer2", wrong_answer2[i]);
            param.put("wrong_answer3", wrong_answer3[i]);
            param.put("wrong_answer4", wrong_answer4[i]);
            param.put("userid", principal.getName());
                if (questionid[i].equals(""))
                    result += sectionMapper.putQuestionItem(param);
                else
                    result += sectionMapper.setQuestionItem(param);

        }
        return result;
    }

    public List<Map> getShotQuestionList(HttpServletRequest request, Principal principal) {
        String shotid = RequestUtils.getParameter(request, "shotid");
        String workerId = RequestUtils.getParameter(request, "workerId");
        HashMap<String, String> param = new HashMap<>();
        if(workerId == null || "".equals(workerId)){
            param.put("userid", principal.getName());
        }else{
            param.put("userid", workerId);
        }
        param.put("shotid", shotid);
        return sectionMapper.getShotQuestionList(param);
    }

    public List<Map> getWorkerList(HttpServletRequest request) {
        String shotid = RequestUtils.getParameter(request, "shotid");
        String sectionid = RequestUtils.getParameter(request, "sectionid");
        HashMap<String, String> param = new HashMap<>();
        param.put("shotid", shotid);
        param.put("sectionid", sectionid);
        return sectionMapper.getWorkerList(param);
    }

    public int putShotQuestionList(HttpServletRequest request, Principal principal) throws Exception {
        String shotid = RequestUtils.getParameter(request, "shotid");
        String[] questionid = RequestUtils.getParameterValues(request, "questionid");
        String[] questiontype = RequestUtils.getParameterValues(request, "questiontype");
        String[] question = RequestUtils.getParameterValues(request, "question");
        String[] answer = RequestUtils.getParameterValues(request, "answer");
        String[] wrong_answer1 = RequestUtils.getParameterValues(request, "wrong_answer1");
        String[] wrong_answer2 = RequestUtils.getParameterValues(request, "wrong_answer2");
        String[] wrong_answer3 = RequestUtils.getParameterValues(request, "wrong_answer3");
        String[] wrong_answer4 = RequestUtils.getParameterValues(request, "wrong_answer4");
        int result = 0;
        for (int i = 0; i < questionid.length; i++) {
            HashMap<String, String> param = new HashMap<>();
                param.put("questionid", questionid[i]);
                param.put("questiontype", questiontype[i]);
                param.put("shotid", shotid);
                param.put("question", question[i]);
                param.put("answer", answer[i]);
                param.put("wrong_answer1", wrong_answer1[i]);
                param.put("wrong_answer2", wrong_answer2[i]);
                param.put("wrong_answer3", wrong_answer3[i]);
                param.put("wrong_answer4", wrong_answer4[i]);
                param.put("userid", principal.getName());
                if (questionid[i].equals(""))
                    result += sectionMapper.putShotQuestionItem(param);
                else
                    result += sectionMapper.setShotQuestionItem(param);

        }
        return result;
    }

    public List<Map> getRelationList(HttpServletRequest request, Principal principal) {
        String videoid = RequestUtils.getParameter(request, "idx");
        HashMap<String, String> param = new HashMap<>();
        param.put("userid", principal.getName());
        param.put("videoid", videoid);
        return sectionMapper.getRelationList(param);
    }

    public int putRelationItem(HttpServletRequest request, Principal principal) throws Exception {
        String videoid = RequestUtils.getParameter(request, "idx");
        String[] relationid = RequestUtils.getParameterValues(request, "relationid");
        String[] subject_sectionid = RequestUtils.getParameterValues(request, "subject_sectionid");
        String[] object_sectionid = RequestUtils.getParameterValues(request, "object_sectionid");
        String[] relationcode = RequestUtils.getParameterValues(request, "relationcode");
        String[] delflag = RequestUtils.getParameterValues(request, "delflag");
        int result = 0;
        for (int i = 0; i < relationid.length; i++) {
            HashMap<String, String> param = new HashMap<>();
            param.put("relationid", relationid[i]);
            param.put("videoid", videoid);
            param.put("subject_sectionid", subject_sectionid[i]);
            param.put("object_sectionid", object_sectionid[i]);
            param.put("relationcode", relationcode[i]);
            param.put("userid", principal.getName());
            if (delflag[i].equals("true")) {
                if (!relationid[i].equals(""))
                    result += sectionMapper.deleteRelationItems(Integer.parseInt(relationid[i]));
            } else {
                if (relationid[i].equals(""))
                    result += sectionMapper.putRelationItem(param);
                else
                    result += sectionMapper.setRelationItem(param);
            }
        }
        return result;
    }


    public String getJsonData(Map paramMap) throws Exception {

        Section sectionData = sectionMapper.getSectionJson(paramMap);

        List<Section.QaResult> qaResultList = sectionMapper.getQaResultsJson(paramMap);
        for (Section.QaResult qaResult : qaResultList) {

            paramMap.put("sectionid", qaResult.getPeriod_num());

            List<Section.QaResult.RelatedPeriodInfo> relationList = sectionMapper.getRelationJson(paramMap);
            qaResult.setRelated_period_info(relationList);

            List<Section.QaResult.DescriptionInfo> descriptionList = sectionMapper.getDepictionJson(paramMap);
            qaResult.setDescription_info(descriptionList);

            List<Section.QaResult.Qa> qaList = sectionMapper.getQaJson(paramMap);
            qaResult.setQa_info(qaList);


        }
        sectionData.setQa_results(qaResultList);

        String resultJson = JSONUtils.jsonStringFromObject(sectionData);

        return resultJson;
    }

    public String getJsonDataQa(Map paramMap) throws Exception {

        Section sectionData = sectionMapper.getSectionJson(paramMap);
        Qa qaData = new Qa();
        qaData.setFile_name(sectionData.getFile_name());

        List<Qa.QaResult> sectionDataList = new ArrayList<>();
        List<Qa.QaResultInfo> sectionInfoList = sectionMapper.getSectionInfoJson(paramMap);
        for (Qa.QaResultInfo qaInfo : sectionInfoList) {
            Qa.QaResult qr = new Qa.QaResult();
            qr.setVid(qaInfo.getVid());
            qr.setVideoType(qaInfo.getVideoType());
            qr.setDescription(qaInfo.getDescription());

            paramMap.put("sectionid", qaInfo.getPeriod_num());
            paramMap.put("videoid", qaInfo.getVideoid());
            paramMap.put("shotid", qaInfo.getShotId());

            List<Qa.QaResult.QnaInfo> sectionInfoQa = new ArrayList<>();
            List<Qa.QaResult.QnaResult> sectionInfoQaResut = new ArrayList<>();

            if (qaInfo.getIdx() == 1) {
                Integer[] shotContained = sectionMapper.getShotContained(paramMap);
                qr.setShot_contained(shotContained);
                sectionInfoQa = sectionMapper.getSectionInfoQa(paramMap);
            } else if (qaInfo.getIdx() == 2) {
                Integer[] shotContained = {qaInfo.getShot_contained()};
                qr.setShot_contained(shotContained);
                sectionInfoQa = sectionMapper.getShotInfoQa(paramMap);
            }

            for (Qa.QaResult.QnaInfo qna : sectionInfoQa) {

                Qa.QaResult.QnaResult qans = new Qa.QaResult.QnaResult();
               if(qna.getQue() != null && !qna.getQue().equals("")) {
                    if (qaInfo.getIdx() == 2) {
                        String qid = "99" + qna.getQid();
                        qans.setQid(Integer.parseInt(qid));
                    } else {
                        qans.setQid(qna.getQid());
                    }
                    String qa_level = "";
                    if(qna.getQa_level()==5){
                        qa_level = "kb";
                    }else{
                        qa_level = String.valueOf(qna.getQa_level());
                    }
                    qans.setQa_level(qa_level);
                    qans.setQ_level_mem(qna.getQ_level_mem());
                    qans.setQ_level_logic(qna.getQ_level_logic());
                    qans.setQue(qna.getQue());
                    qans.setTrue_ans(qna.getTrue_ans());

                    String[] falseAns = {qna.getWrong_answer1(), qna.getWrong_answer2(), qna.getWrong_answer3(), qna.wrong_answer4};
                    qans.setFalse_ans(falseAns);

                    sectionInfoQaResut.add(qans);
               }
            }
            qr.setQa(sectionInfoQaResut);

            System.out.println(qr.toString());

            sectionDataList.add(qr);
        }
        qaData.setQa_results(sectionDataList);
        String resultJson = JSONUtils.jsonStringFromObject(qaData);

        return resultJson;
    }

    public List<ShotTB> getQaChkSectionOfSceneList(ContentQuery n) {
        return sectionMapper.getQaChkSectionOfSceneList(n);
    }
    /*public List<Map> getWorkerList(HttpServletRequest request) throws Exception {
        String sectionid = RequestUtils.getParameter(request, "sectionid");
        HashMap<String, String> param = new HashMap<>();
        param.put("sectionid", sectionid);
        return sectionMapper.getWorkerList(param);
    }*/
}
