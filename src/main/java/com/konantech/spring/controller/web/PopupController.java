package com.konantech.spring.controller.web;

import com.konantech.spring.domain.content.ContentField;
import com.konantech.spring.domain.content.ContentQuery;
import com.konantech.spring.domain.storyboard.ShotTB;
import com.konantech.spring.service.*;
import com.konantech.spring.util.JSONUtils;
import com.konantech.spring.util.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
public class PopupController {

    @Autowired
    private CodeService codeService; //공통코드 서비스

    @Autowired
    private ContentService contentService; //(기존)

    @Autowired
    private PopupService popupService;

    @Autowired
    private StoryboardService storyboardService;

    @Value("${darc.videoServerUrl}")
    private String videoServerUrl; //application.yml  관련 설정.

    @RequestMapping(value = "/popup/subtitle/edit", method = RequestMethod.GET)
    public String getSecInfoMainSubtitle(ModelMap modelMap, HttpServletRequest request) throws Exception {

        int idx = 1106;
        //TODO idx 를 통한 폴더 검색
        //파일 생성 후 idx 정보 획득 경로 협의 필요.

        //TODO JSON 파일 load 및 파일내역 파싱
        // json 파일 생성 방식 협의 필요.
        ContentQuery contentQuery = new ContentQuery();
        contentQuery.setIdx(idx);
        ContentField contentField = contentService.getContentItem(contentQuery);

        modelMap.addAttribute("idx", idx);
        modelMap.addAttribute("videoServerUrl", videoServerUrl);
        modelMap.addAttribute("contentField", contentField);

        return "popup/pop_subtitle_edit";
    }

    /*
     * 자막 리스트 호출
     */
    @RequestMapping(value = "/popup/subtitle/subtitleList")
    public String getQaSectionListSubtitle(Model model, HttpServletRequest request, Principal principal) throws Exception {
        Map<String,String> param = RequestUtils.getParameterMap(request);

        //등장인물코드
        List personCode = codeService.getCodeMap("V0103");
        List<Map> list = popupService.getSubtitleList(request, principal);
        model.addAttribute("subtitleList", list);
        model.addAttribute("personCode", personCode);
        model.addAttribute("param", param);

        return "popup/pop_subtitle_list";
    }

    @RequestMapping(value = "/popup/sound/edit", method = RequestMethod.GET)
    public String getSecInfoMainSound(ModelMap modelMap, HttpServletRequest request) throws Exception {

        int idx = 1106;
        //TODO idx 를 통한 폴더 검색
        //파일 생성 후 idx 정보 획득 경로 협의 필요.

        //TODO JSON 파일 load 및 파일내역 파싱
        // json 파일 생성 방식 협의 필요.
        ContentQuery contentQuery = new ContentQuery();
        contentQuery.setIdx(idx);
        ContentField contentField = contentService.getContentItem(contentQuery);

        modelMap.addAttribute("idx", idx);
        modelMap.addAttribute("videoServerUrl", videoServerUrl);
        modelMap.addAttribute("contentField", contentField);

        return "popup/pop_sound_edit";
    }

    /*
     * 사운드 리스트 호출
     */
    @RequestMapping(value = "/popup/sound/soundList")
    public String getQaSectionListSound(Model model, HttpServletRequest request, Principal principal) throws Exception {
        Map<String,String> param = RequestUtils.getParameterMap(request);

        //사운드 타입코드
        List soundType = codeService.getCodeMap("S0101");
        List<Map> list = popupService.getSoundList(request, principal);
        model.addAttribute("soundList", list);
        model.addAttribute("soundType", soundType);
        model.addAttribute("param", param);

        return "popup/pop_sound_list";
    }

    @RequestMapping(value = "/popup/section/edit", method = RequestMethod.GET)
    public String getSecInfoMain(ModelMap modelMap, HttpServletRequest request, Principal principal) throws Exception {

        int idx = 1106;
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
        modelMap.addAttribute("contentField", contentField);

        return "popup/pop_section_qa_edit";
    }

    /*
     * 구간 리스트 호출
     */
    @RequestMapping(value = "/popup/section/getQaSectionList")
    public String getQaSectionList(Model model, HttpServletRequest request, Principal principal) throws Exception {
        Map<String, String> param = RequestUtils.getParameterMap(request);
        List<Map> list = popupService.getSectionList(request, principal);
        model.addAttribute("qaSectionList", list);
        model.addAttribute("param", param);

        return "popup/_pop_section_qa_list";
    }

    @RequestMapping(value = "/popup/section/getSectionOfSceneList")
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
        return "popup/pop_section_qa_section_list";
    }

    /*
     * QA 리스트 호출
     */
    @RequestMapping(value = "/popup/section/getQuestionList")
    public String getQuestionList(Model model, HttpServletRequest request, Principal principal) throws Exception {
        Map<String, String> param = RequestUtils.getParameterMap(request);
        List<Map> list = popupService.getQuestionList(request, principal);
        model.addAttribute("questionList", list);
        model.addAttribute("param", param);

        return "popup/pop_section_question_list";
    }

    /*
     * QA 리스트 호출
     */
    @RequestMapping(value = "/popup/section/getShotQuestionList")
    public String getShotQuestionList(Model model, HttpServletRequest request, Principal principal) throws Exception {
        Map<String, String> param = RequestUtils.getParameterMap(request);
        List<Map> list = popupService.getShotQuestionList(request, principal);
        model.addAttribute("questionList", list);
        model.addAttribute("param", param);

        return "popup/pop_section_shot_question_list";
    }
}
