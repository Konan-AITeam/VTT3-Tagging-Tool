package com.konantech.spring.controller.web;

import com.konantech.spring.domain.content.ContentField;
import com.konantech.spring.domain.content.ContentQuery;
import com.konantech.spring.service.CodeService;
import com.konantech.spring.service.ContentService;
import com.konantech.spring.service.SoundService;
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
public class SoundController {

    @Autowired
    private CodeService codeService; //공통코드 서비스

    @Autowired
    private ContentService contentService; //(기존)

    @Autowired
    private SoundService soundService; //공통코드 서비스

    @Value("${darc.videoServerUrl}")
    private String videoServerUrl; //application.yml  관련 설정.

    private static Logger log = LoggerFactory.getLogger(SoundController.class);

    @RequestMapping(value = "/sound/edit", method = RequestMethod.GET)
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

        return "sound/sound_edit";
    }

    /*
     * 사운드 리스트 호출
     */
    @RequestMapping(value = "/sound/soundList")
    public String getQaSectionList(Model model, HttpServletRequest request, Principal principal) throws Exception {
        Map<String,String> param = RequestUtils.getParameterMap(request);

        //사운드 타입코드
        List soundType = codeService.getCodeMap("S0101");
        List<Map> list = soundService.getSoundList(request, principal);
        model.addAttribute("soundList", list);
        model.addAttribute("soundType", soundType);
        model.addAttribute("param", param);

        return "sound/sound_list";
    }



    /*
     *  사운드 정보 저장
     */
    @RequestMapping(value = "/sound/putSoundList", method = RequestMethod.POST)
    public String  putSoundList(Model model, HttpServletRequest request, Principal principal) throws Exception {
        Map<String, Object> paramMap = RequestUtils.getParameterMap(request);

        int resultCnt = soundService.putSoundList(request, principal);

        if(resultCnt >0){
            //사운드 타입코드
            List soundType = codeService.getCodeMap("S0101");
            List<Map> qaSectionList = soundService.getSoundList(request,principal);
            model.addAttribute("soundList",qaSectionList);
            model.addAttribute("soundType", soundType);
            model.addAttribute("param",paramMap);
            model.addAttribute("success",true);
        }else{
            model.addAttribute("success",false);
        }
        return "sound/sound_list";
    }
}
