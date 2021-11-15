package com.konantech.spring.controller.web;

import com.konantech.spring.domain.content.ContentField;
import com.konantech.spring.domain.content.ContentQuery;
import com.konantech.spring.domain.storyboard.ShotTB;
import com.konantech.spring.service.*;
import com.konantech.spring.util.JSONUtils;
import com.konantech.spring.util.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;
import java.util.Map;


@Controller
public class SectionController {

    @Autowired
    RestService restService; //레스트 서비스

    @Autowired
    private CodeService codeService; //공통코드 서비스

    @Autowired
    private ContentService contentService; //(기존)

    @Autowired
    private VisualService visualService; //(신규) VTTM(메타정보 관리) 관련 서비스

    @Autowired
    private SectionService sectionService;

    @Autowired
    private StoryboardService storyboardService;

    @Value("${darc.proxyShotFolder}")
    private String proxyShotFolder; //application.yml  관련 설정.

    @Value("${darc.videoServerUrl}")
    private String videoServerUrl; //application.yml  관련 설정.

    private static Logger log = LoggerFactory.getLogger(SectionController.class);

    @RequestMapping(value = "/section/{type}", method = RequestMethod.GET)
    public String getSecInfoMain(@PathVariable String type, ModelMap modelMap, HttpServletRequest request, Principal principal) throws Exception {

        int idx = RequestUtils.getParameterInt(request, "idx", 0);
        Boolean qachk = RequestUtils.getParameterBool(request, "qachk", false);
        //TODO idx 를 통한 폴더 검색
        //파일 생성 후 idx 정보 획득 경로 협의 필요.

        //TODO JSON 파일 load 및 파일내역 파싱
        // json 파일 생성 방식 협의 필요.

        ContentQuery contentQuery = new ContentQuery();
        contentQuery.setIdx(idx);
        ContentField contentField = contentService.getContentItem(contentQuery);

        String mediaInfo = contentField.getMediainfo();
        Map mediaMap = JSONUtils.jsonStringToMap(mediaInfo);
        List list = (List) mediaMap.get("streams");
        Map map = (Map) list.get(0);
        String r_frame_rate = (String) map.get("r_frame_rate");
        float frate = Float.parseFloat(r_frame_rate.split("/")[0]) / Float.parseFloat(r_frame_rate.split("/")[1]);

        modelMap.addAttribute("videoServerUrl", videoServerUrl);
        modelMap.addAttribute("idx", idx);
        modelMap.addAttribute("rate", frate);
        modelMap.addAttribute("qachk", qachk);
        modelMap.addAttribute("contentField", contentField);

        return "section/section_" + type + "_edit";
    }

    /*
     * 구간 리스트 호출
     */
    @RequestMapping(value = "/section/getQaSectionList")
    public String getQaSectionList(Model model, HttpServletRequest request, Principal principal) throws Exception {
        Map<String, String> param = RequestUtils.getParameterMap(request);
        List<Map> list = sectionService.getSectionList(request, principal);
        model.addAttribute("qaSectionList", list);
        model.addAttribute("param", param);

        return "section/_section_qa_list";
    }

    /*
     * QA 검증용 구간 리스트 호출
     */
    @RequestMapping(value = "/section/getQaChkSectionList")
    public String getQaChkSectionList(Model model, HttpServletRequest request, Principal principal) throws Exception {
        Map<String, String> param = RequestUtils.getParameterMap(request);

        List<Map> list = sectionService.getQaChkSectionList(request, principal);
        model.addAttribute("qaSectionList", list);
        model.addAttribute("param", param);

        return "section/_section_qachk_list";
    }

    /*
     *  구간정보 저장
     */
    @RequestMapping(value = "/section/putQaSectionList", method = RequestMethod.POST)
    public String getPutSecInfo(Model model, HttpServletRequest request, Principal principal) throws Exception {
        Map<String, Object> paramMap = RequestUtils.getParameterMap(request);

        int resultCnt = sectionService.putQaSection(request, principal);

        if (resultCnt > 0) {
            List<Map> qaSectionList = sectionService.getSectionList(request, principal);
            model.addAttribute("qaSectionList", qaSectionList);
            model.addAttribute("param", paramMap);
            model.addAttribute("success", true);
        } else {
            model.addAttribute("success", false);
        }
        return "section/_section_qa_list";
    }

    /*
     * 묘사 리스트 호출
     */
    @RequestMapping(value = "/section/getDepictionList")
    public String getDepictionList(Model model, HttpServletRequest request, Principal principal) throws Exception {
        Map<String, String> param = RequestUtils.getParameterMap(request);
        List<Map> list = sectionService.getDepictionList(request, principal);
        model.addAttribute("depictionList", list);
        model.addAttribute("param", param);

        return "section/section_depiction_list";
    }

    /*
     *  묘사정보 저장
     */
    @RequestMapping(value = "/section/putDepictionList", method = RequestMethod.POST)
    public String putDepictionList(Model model, HttpServletRequest request, Principal principal) throws Exception {
        Map<String, Object> paramMap = RequestUtils.getParameterMap(request);

        int resultCnt = sectionService.putDepiction(request, principal);

        if (resultCnt > 0) {
            List<Map> qaSectionList = sectionService.getDepictionList(request, principal);
            model.addAttribute("depictionList", qaSectionList);
            model.addAttribute("param", paramMap);
            model.addAttribute("success", true);
        } else {
            model.addAttribute("success", false);
        }
        return "section/section_depiction_list";
    }

    /*
     * QA 리스트 호출
     */
    @RequestMapping(value = "/section/getQuestionList")
    public String getQuestionList(Model model, HttpServletRequest request, Principal principal) throws Exception {
        Map<String, String> param = RequestUtils.getParameterMap(request);
        List<Map> list = sectionService.getQuestionList(request, principal);
        String workerId = RequestUtils.getParameter(request, "workerId");
        if(workerId == null || "".equals(workerId)){
            workerId = principal.getName();
        }
        model.addAttribute("workerId", workerId);
        if(request.isUserInRole("ROLE_ADMIN")){
            List userList = sectionService.getWorkerList(request);
            model.addAttribute("userList", userList);
        }
        model.addAttribute("questionList", list);
        model.addAttribute("param", param);

        return "section/section_question_list";
    }
    /*
     * QA 리스트 호출
     */
    @RequestMapping(value = "/section/getQaChkQuestionList")
    public String getQaChkQuestionList(Model model, HttpServletRequest request, Principal principal) throws Exception {
        Map<String, String> param = RequestUtils.getParameterMap(request);
        List<Map> list = sectionService.getQuestionList(request, principal);
        String workerId = RequestUtils.getParameter(request, "workerId");
        String questionid = RequestUtils.getParameter(request, "questionId");
        if(workerId == null || "".equals(workerId)){
            workerId = principal.getName();
        }
        model.addAttribute("workerId", workerId);
        if(request.isUserInRole("ROLE_ADMIN")){
            List userList = sectionService.getWorkerList(request);
            model.addAttribute("userList", userList);
        }
        model.addAttribute("questionid", questionid);
        model.addAttribute("questionList", list);
        model.addAttribute("param", param);

        return "section/section_qachk_question_list";
    }

    /*
     *  QA정보 저장
     */
    @RequestMapping(value = "/section/putQuestionList", method = RequestMethod.POST)
    public String putQuestionList(Model model, HttpServletRequest request, Principal principal) throws Exception {
        Map<String, Object> paramMap = RequestUtils.getParameterMap(request);

        int resultCnt = sectionService.putQuestionList(request, principal);

        if (resultCnt > 0) {
            List<Map> qaSectionList = sectionService.getQuestionList(request, principal);
            model.addAttribute("questionList", qaSectionList);
            model.addAttribute("param", paramMap);
            model.addAttribute("success", true);
        } else {
            model.addAttribute("success", false);
        }
        return "section/section_question_list";
    }

    /*
     * QA 리스트 호출
     */
    @RequestMapping(value = "/section/getQaChkShotQuestionList")
    public String getQaChkShotQuestionList(Model model, HttpServletRequest request, Principal principal) throws Exception {
        Map<String, String> param = RequestUtils.getParameterMap(request);
        List<Map> list = sectionService.getShotQuestionList(request, principal);

        String workerId = RequestUtils.getParameter(request, "workerId");
        String questionid = RequestUtils.getParameter(request, "questionId");
        if(workerId == null || "".equals(workerId)){
            workerId = principal.getName();
        }
        model.addAttribute("workerId", workerId);
        if(request.isUserInRole("ROLE_ADMIN")){
            List userList = sectionService.getWorkerList(request);
            model.addAttribute("userList", userList);
        }
        model.addAttribute("questionList", list);
        model.addAttribute("param", param);
        model.addAttribute("questionid", questionid);
        return "section/section_qachk_shot_question_list";
    }

    /*
     * QA 리스트 호출
     */
    @RequestMapping(value = "/section/getShotQuestionList")
    public String getShotQuestionList(Model model, HttpServletRequest request, Principal principal) throws Exception {
        Map<String, String> param = RequestUtils.getParameterMap(request);
        List<Map> list = sectionService.getShotQuestionList(request, principal);

        String workerId = RequestUtils.getParameter(request, "workerId");
        if(workerId == null || "".equals(workerId)){
            workerId = principal.getName();
        }
        model.addAttribute("workerId", workerId);
        if(request.isUserInRole("ROLE_ADMIN")){
            List userList = sectionService.getWorkerList(request);
            model.addAttribute("userList", userList);
        }
        model.addAttribute("questionList", list);
        model.addAttribute("param", param);
        return "section/section_shot_question_list";
    }

    @RequestMapping(value = "/section/putShotQuestionList", method = RequestMethod.POST)
    public String putShotQuestionList(Model model, HttpServletRequest request, Principal principal) throws Exception {
        Map<String, Object> paramMap = RequestUtils.getParameterMap(request);

        int resultCnt = sectionService.putShotQuestionList(request, principal);

        if (resultCnt > 0) {
            List<Map> qaSectionList = sectionService.getShotQuestionList(request, principal);
            model.addAttribute("questionList", qaSectionList);
            model.addAttribute("param", paramMap);
            model.addAttribute("success", true);
        } else {
            model.addAttribute("success", false);
        }
        return "section/section_shot_question_list";
    }

    /*
     * 인과/의도 리스트 호출
     */
    @RequestMapping(value = "/section/getRelationList")
    public String getRelationList(Model model, HttpServletRequest request, Principal principal) throws Exception {
        Map<String, String> param = RequestUtils.getParameterMap(request);
        List<Map> list = sectionService.getRelationList(request, principal);
        List<Map> sectionlist = sectionService.getSectionList(request, principal);
        //관계타입
        List relationCode = codeService.getCodeMap("D0102");
        model.addAttribute("relationList", list);
        model.addAttribute("qaSectionList", sectionlist);
        model.addAttribute("relationCode", relationCode);
        model.addAttribute("param", param);

        return "section/section_relation_list";
    }

    /*
     *  인과/의도 저장
     */
    @RequestMapping(value = "/section/putRelationList", method = RequestMethod.POST)
    public String putRelateionList(Model model, HttpServletRequest request, Principal principal) throws Exception {
        Map<String, Object> paramMap = RequestUtils.getParameterMap(request);

        int resultCnt = sectionService.putRelationItem(request, principal);

        if (resultCnt > 0) {
            List<Map> sectionlist = sectionService.getSectionList(request, principal);
            List<Map> list = sectionService.getRelationList(request, principal);
            //관계타입
            List relationCode = codeService.getCodeMap("D0102");
            model.addAttribute("relationList", list);
            model.addAttribute("qaSectionList", sectionlist);
            model.addAttribute("relationCode", relationCode);
            model.addAttribute("param", paramMap);
            model.addAttribute("success", true);
        } else {
            model.addAttribute("success", false);
        }
        return "section/section_relation_list";
    }
    @RequestMapping(value = "/section/getSectionOfSceneList")
    public String getSectionOfSceneList(Model model, HttpServletRequest request) throws Exception {
        Map param = RequestUtils.getParameterMap(request);
        ContentQuery query1 = new ContentQuery();
        query1.setIdx(RequestUtils.getParameterInt(request, "videoid"));
        query1.setSectionid(RequestUtils.getParameterInt(request, "sectionid"));
        List<ShotTB> list = storyboardService.getSectionOfSceneList(query1);

        ContentQuery query2 = new ContentQuery();
        query2.setIdx(RequestUtils.getParameterInt(request, "videoid"));
        ContentField contentField = contentService.getContentItem(query2);

        model.addAttribute("sectionList", list);
        model.addAttribute("param", param);
        model.addAttribute("itemDetail", contentField);
        return "section/section_qa_section_list";
    }

    @RequestMapping(value = "/section/getQaChkSectionOfSceneList")
    public String getQaChkSectionOfSceneList(Model model, HttpServletRequest request,  Principal principal) throws Exception {
        Map param = RequestUtils.getParameterMap(request);
        ContentQuery query1 = new ContentQuery();
        query1.setIdx(RequestUtils.getParameterInt(request, "videoid"));
        query1.setQaSearchWord(RequestUtils.getParameter(request, "qaSearchWord"));
        if(request.isUserInRole("ROLE_ADMIN")){
            query1.setRoleadmin("admin");
        }else{
            query1.setRoleadmin("user");
        }
        query1.setUserid(principal.getName());

        List<ShotTB> list = sectionService.getQaChkSectionOfSceneList(query1);

        ContentQuery query2 = new ContentQuery();
        query2.setIdx(RequestUtils.getParameterInt(request, "videoid"));
        ContentField contentField = contentService.getContentItem(query2);

        model.addAttribute("sectionList", list);
        model.addAttribute("param", param);
        model.addAttribute("itemDetail", contentField);
        return "section/section_qachk_section_list";
    }

    /*
     * 샷 구간 리스트 호출
     */
    @RequestMapping(value = "/section/getSectionList")
    public String getSectionList(Model model, HttpServletRequest request) throws Exception {
        Map param = RequestUtils.getParameterMap(request);
        ContentQuery query1 = new ContentQuery();
        query1.setIdx(RequestUtils.getParameterInt(request, "videoid"));
        List<ShotTB> list = storyboardService.getShotList(query1);

        ContentQuery query2 = new ContentQuery();
        query2.setIdx(RequestUtils.getParameterInt(request, "videoid"));
        ContentField contentField = contentService.getContentItem(query2);

        model.addAttribute("sectionList", list);
        model.addAttribute("param", param);
        model.addAttribute("itemDetail", contentField);
        return "section/section_section_list";
    }

    /* 샷 단축키 */
    @RequestMapping(value = "/section/video/hotkey", method = RequestMethod.GET)
    public String shotHotkey() throws Exception {
        return "section/_section_video_hotkey";
    }

    /* 팝업 - 가이드 라인 Scene */
    @RequestMapping(value = "/section/guide/scene", method = RequestMethod.GET)
    public String guideScene() throws Exception {
        return "section/_section_scene_qa_guide";
    }

    /* 팝업 - 가이드 라인  Shot */
    @RequestMapping(value = "/section/guide/shot", method = RequestMethod.GET)
    public String guideShot() throws Exception {
        return "section/_section_shot_qa_guide";
    }
}
