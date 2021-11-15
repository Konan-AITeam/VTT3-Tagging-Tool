package com.konantech.spring.controller.web;

import com.konantech.spring.domain.content.ContentField;
import com.konantech.spring.domain.content.ContentQuery;
import com.konantech.spring.service.CodeService;
import com.konantech.spring.service.ContentService;
import com.konantech.spring.service.SubtitleService;
import com.konantech.spring.util.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class SubtitleController {

    @Autowired
    private CodeService codeService; //공통코드 서비스

    @Autowired
    private ContentService contentService; //(기존)

    @Autowired
    private SubtitleService subtitleService;

    @Value("${darc.videoServerUrl}")
    private String videoServerUrl; //application.yml  관련 설정.

    private static Logger log = LoggerFactory.getLogger(SubtitleController.class);

    @RequestMapping(value = "/subtitle/edit", method = RequestMethod.GET)
    public String getSecInfoMain(ModelMap modelMap, HttpServletRequest request) throws Exception {

        int idx = RequestUtils.getParameterInt(request, "idx", 0);
        //TODO idx 를 통한 폴더 검색
        //파일 생성 후 idx 정보 획득 경로 협의 필요.

        //TODO JSON 파일 load 및 파일내역 파싱
        // json 파일 생성 방식 협의 필요.
        ContentQuery contentQuery = new ContentQuery();
        contentQuery.setIdx(idx);
        ContentField contentField =contentService.getContentItem(contentQuery);

        modelMap.addAttribute("idx", idx);
        modelMap.addAttribute("videoServerUrl", videoServerUrl);
        modelMap.addAttribute("contentField", contentField);

        return "subtitle/subtitle_edit";
    }

    /*
     * 자막 리스트 호출
     */
    @RequestMapping(value = "/subtitle/subtitleList")
    public String getQaSectionList(Model model, HttpServletRequest request, Principal principal) throws Exception {
        Map<String,String> param = RequestUtils.getParameterMap(request);

        //등장인물코드
        List personCode = codeService.getCodeMap("V0103");
        List<Map> list = subtitleService.getSubtitleList(request, principal);
        model.addAttribute("subtitleList", list);
        model.addAttribute("personCode", personCode);
        model.addAttribute("param", param);

        return "subtitle/subtitle_list";
    }

    /*
     *  사운드 정보 저장
     */
    @RequestMapping(value = "/subtitle/putSubtitleList", method = RequestMethod.POST)
    public String  putSubtitleList(Model model, HttpServletRequest request, Principal principal) throws Exception {
        Map<String, Object> paramMap = RequestUtils.getParameterMap(request);

        int resultCnt = subtitleService.putSubtitleList(request, principal);

        if(resultCnt >0){
            //등장인물코드
            List personCode = codeService.getCodeMap("V0103");
            List<Map> list = subtitleService.getSubtitleList(request,principal);
            model.addAttribute("subtitleList",list);
            model.addAttribute("personCode", personCode);
            model.addAttribute("param",paramMap);
            model.addAttribute("success",true);
        }else{
            model.addAttribute("success",false);
        }
        return "subtitle/subtitle_list";
    }
}
