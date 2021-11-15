package com.konantech.spring.controller.web;

import com.konantech.spring.service.CodeService;
import com.konantech.spring.service.ContentService;
import com.konantech.spring.service.SectionService;
import com.konantech.spring.service.StatsService;
import com.konantech.spring.util.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class StatsController {

    @Autowired
    private CodeService codeService; //공통코드 서비스

    @Autowired
    private ContentService contentService; //(기존)

    @Autowired
    private StatsService statsService; //공통코드 서비스

    @Autowired
    private SectionService sectionService;

    private static Logger log = LoggerFactory.getLogger(StatsController.class);

    @RequestMapping(value = "/stats", method = RequestMethod.GET)
    public String getStatsMain(ModelMap modelMap, HttpServletRequest request) throws Exception {

        int idx = RequestUtils.getParameterInt(request, "idx", 0);

        /*ContentQuery contentQuery = new ContentQuery();
        contentQuery.setIdx(idx);
        ContentField contentField =contentService.getContentItem(contentQuery);
*/
        modelMap.addAttribute("idx", idx);
        /*modelMap.addAttribute("contentField", contentField);*/

        return "stats/stats_edit";
    }

    @RequestMapping(value = "/stats/getStatsViewInfoList")
    public String getStatsViewInfoList(Model model, HttpServletRequest request, Principal principal) throws Exception {
        Map<String, String> param = RequestUtils.getParameterMap(request);
        String workerId = RequestUtils.getParameter(request, "workerId");

        model.addAttribute("workerId", workerId);
        if(request.isUserInRole("ROLE_ADMIN")) {
            List userList = statsService.getWorkerList(request);
            model.addAttribute("userList", userList);
        }
        int totalcnt = statsService.getCountStatsViewInfoList(request, principal);
        List<Map> list = statsService.getStatsViewInfoList(request, principal);

        model.addAttribute("totalcnt", totalcnt);
        model.addAttribute("statsList", list);
        model.addAttribute("param", param);

        return "stats/_stats_view_data";
    }

    @RequestMapping(value = "/stats/getStatsQaInfoList")
    public String getStatsQaInfoList(Model model, HttpServletRequest request, Principal principal) throws Exception {
        Map<String, String> param = RequestUtils.getParameterMap(request);
        String workerId = RequestUtils.getParameter(request, "workerId");

        if(request.isUserInRole("ROLE_ADMIN")) {
            List userList = statsService.getWorkerList(request);
            model.addAttribute("userList", userList);
        }else{
            workerId =  principal.getName();
        }

        param.put("workerid", workerId);
        model.addAttribute("workerId", workerId);

        String statsitem = RequestUtils.getParameter(request, "statsitem");
        List<Map> shotStatsList = null;
        List<Map> sceneStatsList = null;
        List<Map> chartStatsList = null;

        String _page = "";
        if(statsitem.equals("qaInterrogative")){
            _page = "stats/_stats_qa_interrogative_data";
            shotStatsList = statsService.getStatsQaInfoListQaInterrogativeShot(param);
            sceneStatsList = statsService.getStatsQaInfoListQaInterrogativeScene(param);
            chartStatsList = statsService.getStatsQaInfoListQaInterrogativeChart(param);
        }else{
            _page = "stats/_stats_qa_total_data";
            shotStatsList = statsService.getStatsQaInfoListQaTotalShot(param);
            sceneStatsList = statsService.getStatsQaInfoListQaTotalScene(param);
        }
        model.addAttribute("shotStatsList", shotStatsList);
        model.addAttribute("sceneStatsList", sceneStatsList);
        model.addAttribute("chartStatsList", chartStatsList);
        model.addAttribute("param", param);
        return _page;
    }
}
