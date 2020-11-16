package com.konantech.spring.controller.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.konantech.spring.domain.content.ContentField;
import com.konantech.spring.domain.content.ContentQuery;
import com.konantech.spring.domain.storyboard.ShotTB;
import com.konantech.spring.domain.vtt.FrameImage;
import com.konantech.spring.domain.vtt.RepImgVo;
import com.konantech.spring.service.*;
import com.konantech.spring.util.RequestUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.security.Principal;
import java.util.*;


@Controller
public class VisualController {

    @Autowired
    RestService restService; //레스트 서비스

    @Autowired
    private CodeService codeService; //공통코드 서비스

    @Autowired
    private ContentService contentService; //(기존)

    @Autowired
    private VisualService visualService; //(신규) VTTM(메타정보 관리) 관련 서비스

    @Autowired
    private StoryboardService storyboardService;


    @Value("${darc.shotServerUrl}")
    private String shotServerUrl; //application.yml  관련 설정.

    @Value("${darc.proxyShotFolder}")
    private String proxyShotFolder; //application.yml  관련 설정.

    @Value("${darc.videoServerUrl}")
    private String videoServerUrl; //application.yml  관련 설정.

    private static Logger log = LoggerFactory.getLogger(VisualController.class);


    @RequestMapping(value = "/visual/main", method = RequestMethod.GET)
    public String main(ModelMap modelMap, HttpServletRequest request) throws Exception {

        Map<String, Object> queryMap = RequestUtils.getParameterMap(request);
        modelMap.addAttribute("queryMap", queryMap);

        int idx = RequestUtils.getParameterInt(request, "idx", 0);
        //TODO idx 를 통한 폴더 검색
        //파일 생성 후 idx 정보 획득 경로 협의 필요.

        //TODO JSON 파일 load 및 파일내역 파싱
        // json 파일 생성 방식 협의 필요.

        List<Map<String, Object>> codeMap01 = codeService.getCodeMap("V0103");
        List<Map<String, Object>> codeMap02 = codeService.getCodeMap("V0102");
        List<Map<String, Object>> codeMap03 = codeService.getCodeMap("V0105");
        List<Map<String, Object>> codeMap04 = codeService.getCodeMap("V0108");
        List<Map<String, Object>> codeMap05 = codeService.getCodeMap("V0106");
        List<Map<String, Object>> codeMap06 = codeService.getCodeMap("O0101");
        List<Map<String, Object>> codeMap07 = codeService.getCodeMap("D0101");
        List<Map<String, Object>> codeMap08 = codeService.getCodeMap("V0107");
        List<Map<String, Object>> codeMap09 = codeService.getCodeMap("V0110");
        List<Map<String, Object>> codeMap10 = codeService.getCodeMap("V0111");
        List<Map<String, Object>> codeMap11 = codeService.getCodeMap("V1001");  // 서술어

        ContentQuery contentQuery = new ContentQuery();
        contentQuery.setIdx(idx);
        ContentField contentField = contentService.getContentItem(contentQuery);

        modelMap.addAttribute("codeMap01", codeMap01);
        modelMap.addAttribute("codeMap02", codeMap02);
        modelMap.addAttribute("codeMap03", codeMap03);
        modelMap.addAttribute("codeMap04", codeMap04);
        modelMap.addAttribute("codeMap05", codeMap05);
        modelMap.addAttribute("codeMap06", codeMap06);
        modelMap.addAttribute("codeMap07", codeMap07);
        modelMap.addAttribute("codeMap08", codeMap08);
        modelMap.addAttribute("codeMap09", codeMap09);
        modelMap.addAttribute("codeMap10", codeMap10);
        modelMap.addAttribute("codeMap11", codeMap11);
        modelMap.addAttribute("shotServerUrl", shotServerUrl.replace("210.217.95.183", request.getServerName()));

        modelMap.addAttribute("idx", idx);
        modelMap.addAttribute("contentField", contentField);


        return "visual/visual_info_edit";
    }


    /*
     * 샷 구간 리스트 호출
     */
    @RequestMapping(value = "/visual/getSectionList")
    public String getSectionList(Model model, HttpServletRequest request) throws Exception {
        Map param = RequestUtils.getParameterMap(request);
        ContentQuery query1 = new ContentQuery();
        query1.setIdx(RequestUtils.getParameterInt(request, "videoid"));

        List<ShotTB> list = storyboardService.getImgEditShotList(query1);

        ContentQuery query2 = new ContentQuery();
        query2.setIdx(RequestUtils.getParameterInt(request, "videoid"));
        ContentField contentField = contentService.getContentItem(query2);

        model.addAttribute("sectionList", list);
        model.addAttribute("param", param);
        model.addAttribute("itemDetail", contentField);
        return "visual/visual_section_list";
    }

    /*
     * 구간별 샷 리스트 호출
     */
    @RequestMapping(value = "/visual/getSectionShotList")
    public String getSectionShotList(Model model, HttpServletRequest request, Principal principal) throws Exception {
        List<FrameImage> sectionShotList = visualService.getFrameImageList(request, principal);
        log.debug("getSectionShotList - sectionShotList ===> " + sectionShotList);
        log.debug("getSectionShotList - shotServerUrl ===> " + shotServerUrl);
        if(!sectionShotList.isEmpty()){
            log.debug("getSectionShotList - URL ===> " + shotServerUrl + sectionShotList.get(0).getAssetfilepath() + "/" + sectionShotList.get(0).getAssetfilename());
        }

        model.addAttribute("sectionShotList", sectionShotList);
        model.addAttribute("shotServerUrl", shotServerUrl);

        return "visual/visual_section_shot_list";
    }

    /*
     *  구간별 샷 메타json read 및 결과 return
     */
    @RequestMapping(value = "/visual/getMetaJson")
    @ResponseBody
    public Map getMetaJson(Model model, HttpServletRequest request) throws Exception {
        String sectionPath = RequestUtils.getParameter(request, "sectionPath", "");
        String shotFileName = RequestUtils.getParameter(request, "shotFileName", "");
        String filePath = proxyShotFolder + "/" + sectionPath + "/" + shotFileName;
        Map result = restService.getImgMetaInfo(filePath);

        return result;
    }


    /*
     *  시각정보 대표 이미지 호출
     */
    @RequestMapping(value = "/visual/getRepreImage")
    public String getRepreImage(Model model, HttpServletRequest request, Principal principal) throws Exception {
        Map map = RequestUtils.getParameterMap(request);
        map.put("userid", principal.getName());

        String assetfilepath = (String) map.get("assetfilepath");
        String assetfilepathorigin = (String) map.get("assetfilepathorigin");
        log.debug("getRepreImage assetfilepath ===> " + assetfilepath);
        log.debug("map ===> " + map);

        assetfilepath = assetfilepath.replace("mnt/disk02/darc4data/proxyshot/", "");
        assetfilepath = assetfilepath.replace("/Volumes/ikon/darc4data/proxyshot/", "");
        assetfilepathorigin = assetfilepathorigin.replace("mnt/disk02/darc4data/proxyshot/", "");

        map.remove("assetfilepath");
        map.remove("assetfilepathorigin");
        map.put("assetfilepath", assetfilepath);
        map.put("assetfilepathorigin", assetfilepathorigin);

        log.debug("visualMapper.getSelectRepImg(map)");
        RepImgVo repImg = visualService.getSelectRepImg(map);

        if(request.isUserInRole("ROLE_ADMIN")){
            List userList = visualService.getWorkerList(map);
            model.addAttribute("userList", userList);
        }

        model.addAttribute("repImg", repImg);
        model.addAttribute("param", map);
        model.addAttribute("shotServerUrl", shotServerUrl);
        model.addAttribute("assetfilepath", assetfilepath);

        log.debug("model ===> " + model);
        log.debug("assetfilepath ===> " + assetfilepath);

        return "visual/visual_data_img";
    }

    /*
     *  이전 시각정보 대표 이미지 호출
     */
    @RequestMapping(value = "/visual/getPrevRepreImage")
    public String getPrevRepreImage(Model model, HttpServletRequest request, Principal principal) throws Exception {
        Map map = RequestUtils.getParameterMap(request);
        map.put("userid", principal.getName());

        String otheruserid = (String)map.get("otheruserid");
        if(otheruserid == null || "".equals(otheruserid)){
            map.put("selectuserid", principal.getName());
        }else{
            map.put("selectuserid", otheruserid);
        }

        String assetfilepath = (String) map.get("assetfilepath");
        String assetfilepathorigin = (String) map.get("assetfilepathorigin");
        log.debug("getRepreImage assetfilepath ===> " + assetfilepath);
        log.debug("map ===> " + map);

        assetfilepath = assetfilepath.replace("mnt/disk02/darc4data/proxyshot/", "");
        assetfilepath = assetfilepath.replace("/Volumes/ikon/darc4data/proxyshot/", "");
        assetfilepathorigin = assetfilepathorigin.replace("mnt/disk02/darc4data/proxyshot/", "");

        map.remove("assetfilepath");
        map.remove("assetfilepathorigin");
        map.put("assetfilepath", assetfilepath);
        map.put("assetfilepathorigin", assetfilepathorigin);

        log.debug("visualMapper.getSelectRepImg(map)");
        RepImgVo repImg = visualService.getSelectPrevRepImg(map);

        if (request.isUserInRole("ROLE_ADMIN")) {
            List userList = visualService.getWorkerList(map);
            model.addAttribute("userList", userList);
        }

        model.addAttribute("repImg", repImg);
        model.addAttribute("param", map);
        model.addAttribute("shotServerUrl", shotServerUrl);
        model.addAttribute("assetfilepath", assetfilepath);

        log.debug("model ===> " + model);
        log.debug("assetfilepath ===> " + assetfilepath);

        return "visual/visual_data_img";
    }

    /*
     *  시각정보 편집 화면 호출
     */
    @RequestMapping(value = "/visual/getMetaEdit")
    public String getMetaEdit(Model model, @RequestParam(value = "idx", defaultValue = "") String idx) throws Exception {
        return "visual/visual_meta_edit_list";
    }

    /*
     *  오토태깅
     */
    @RequestMapping(value = "/visual/getPutRepreImg")
    @ResponseBody
    public Map getPutRepreImg(HttpServletRequest request, Principal principal) throws Exception {
        Map<String, String> param = RequestUtils.getParameterMap(request);
        ObjectMapper mapper = new ObjectMapper();
        Map resultMap = new LinkedHashMap();

        String assetfilepath = MapUtils.getString(param, "assetfilepath");
        String filePath = proxyShotFolder + "/" + assetfilepath + "/" + MapUtils.getString(param, "assetimgfilename");
        String jsonStr = "";

        log.debug("getPutRepreImg param ==> " + param);
        log.debug("getPutRepreImg filePath ==> " + filePath);

        try {
            jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(restService.getImgMetaInfo(filePath));

        } catch (Exception e) {
            resultMap.put("success", false);
            resultMap.put("message", e.getMessage());
            return resultMap;
        }

        param.put("autojson", jsonStr);

        log.debug("jsonStr ===> " + jsonStr);
        log.debug("param ===> " + param);

        int result = visualService.getPutRepreImg(param);
        if (result > 0) {
            resultMap.put("success", true);

            resultMap.put("message", "저장되었습니다.");
        } else {
            resultMap.put("success", false);
            resultMap.put("message", "저장 실패하였습니다.");
        }
        return resultMap;
    }


    /*
     *  오토태깅
     */
    @RequestMapping(value = "/visual/getPutBulkRepreImg")
    @ResponseBody
    public Map getPutBulkRepreImg(HttpServletRequest request, Principal principal) throws Exception {
        Map<String, Object> param = RequestUtils.getParameterMap(request);
        Map resultMap = new LinkedHashMap();
        StringBuffer errorMsg = new StringBuffer();
        int taggingCnt = 0;
        int errorCnt = 0;

        List<Map<String, Object>> contentList = contentService.getAutoTaggingContentList(param);

        for (Map map : contentList) {
            ContentQuery query1 = new ContentQuery();
            query1.setIdx(MapUtils.getInteger(map, "idx"));
            List<ShotTB> shotlist = storyboardService.getShotList(query1);

            for (ShotTB shotData : shotlist) {
                Map tagParam = new LinkedHashMap();
                tagParam.put("videoid", shotData.getVideoid());
                tagParam.put("shotname", shotData.getAssetfilename());
                tagParam.put("shotid", shotData.getShotid());
                tagParam.put("assetfilename", shotData.getAssetfilename());
                tagParam.put("assetfilepathorigin", shotData.getAssetfilepath());
                tagParam.put("assetfilepath", shotData.getAssetfilepath() + shotData.getAssetfilename());
                tagParam.put("startframeindex", shotData.getStartframeindex());
                tagParam.put("endframeindex", shotData.getEndframeindex());

                ObjectMapper mapper = new ObjectMapper();

                int frameIdx = shotData.getStartframeindex();
                int endFrameIdx = shotData.getEndframeindex();
                boolean isLoop = true;

                File files[] = new File(proxyShotFolder + File.separator + MapUtils.getString(tagParam, "assetfilepath")+File.separator).listFiles();

                if(files.length > 0){
                    for(File f : files){
                        tagParam.put("assetimgfilename", f.getName());
                        frameIdx += shotData.getStep();

                        LinkedHashMap<String, Object> metaInfoList = visualService.getSelectFrameImgInfo(tagParam);

                        if (metaInfoList != null && metaInfoList.size() > 0) {
                            log.debug("debug : 기 태깅 이미지!!");
                        } else {

                            tagParam.put("frameimgid", 0);
                            String assetfilepath = MapUtils.getString(tagParam, "assetfilepath");
                            String filePath = proxyShotFolder + "/" + assetfilepath + "/" + MapUtils.getString(tagParam, "assetimgfilename");
                            String jsonStr = "";

                            try {
                                jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(restService.getImgMetaInfo(filePath));
                                taggingCnt++;

                            } catch (Exception e) {
                                errorMsg.append(tagParam.get("videoid").toString() + " / " + tagParam.get("shotid").toString() + " / " + tagParam.get("assetimgfilename").toString() + " ERROR : " + e.getMessage());
                                errorMsg.append("\\n");
                                errorCnt++;
                                log.debug("error : " + e.getMessage());
                                continue;
                            }

                            tagParam.put("autojson", jsonStr);
                            visualService.getPutRepreImg(tagParam);
                        }
                    }
                }
            }


        }

        resultMap.put("errorMsg", errorMsg.toString());
        resultMap.put("taggingCnt", taggingCnt);
        resultMap.put("errorCnt", errorCnt);
        return resultMap;
    }

    /*
     *  메타 데이터 저장. - 서강대 response json을 meta_info_json 으로 변경.
     */
    @RequestMapping(value = "/visual/getPutMetaInfo")
    public String getPutMetaInfo(Model model, HttpServletRequest request, Principal principal) throws Exception {
        int resultCnt = visualService.getPutMetaInfo2(request, principal);
        log.debug("resultCnt ===> " + resultCnt);
        log.debug("request ===> " + request);
        log.debug(RequestUtils.getParameter(request, "metaData"));

        Map resultMap = new LinkedHashMap();
        if (resultCnt > 0) {
            LinkedHashMap<String, Object> metaInfoList = visualService.getSelectMetaInfo(request, principal);
            String jsonString = metaInfoList.get("vtt_meta_json").toString();

            log.debug("getPutMetaInfo - metaInfoList ===> " + metaInfoList);
            log.debug("getPutMetaInfo - jsonString ===> " + jsonString);

            resultMap.put("success", true);
            resultMap.put("message", "저장되었습니다.");

            model.addAttribute("resultMap", resultMap);
            model.addAttribute("metaJsonMap", jsonString);

        } else {
            resultMap.put("success", false);
            resultMap.put("message", "저장 실패하였습니다.");
            model.addAttribute("resultMap", resultMap);
            model.addAttribute("metaJsonMap", null);
        }

        return "visual/visual_meta_info_list";
    }


    /*
     *  메타 데이터 저장. - 서강대 response json을 meta_info_json 으로 변경. (vtt 기존)
     */
//    @RequestMapping(value = "/visual/getPutMetaInfo")
//    public String getPutMetaInfo(Model model, @RequestParam Map<String, Object> reqMap, Principal principal) throws Exception {
//
//        String faceForm = MapUtil.getParameter(reqMap,"faceForm","");
//        String personAllForm = MapUtil.getParameter(reqMap,"personAllForm","");
//        String objForm = MapUtil.getParameter(reqMap,"objForm","");
//        String placeForm = MapUtil.getParameter(reqMap,"placeForm","");
//        String qaForm = MapUtil.getParameter(reqMap,"qaForm","");
//        String representImgIdx = MapUtil.getParameter(reqMap,"repIdx","");
//        String repFileId = MapUtil.getParameter(reqMap,"repFileId","");
//        String repVideoId = MapUtil.getParameter(reqMap,"repVideoId","");
//        String repImgSeq = MapUtil.getParameter(reqMap,"repImgSeq","");
//        String repSectionId = MapUtil.getParameter(reqMap,"repSectionId","");
//        String repShotid = MapUtil.getParameter(reqMap,"repShotid","");
//        String userid = principal.getName();
//
//        ObjectMapper mapper = new ObjectMapper();
//        JSONArray jsonArr1 = mapper.readValue(faceForm, JSONArray.class);
//        JSONArray jsonArr2 = mapper.readValue(personAllForm, JSONArray.class);
//        JSONArray jsonArr3 = mapper.readValue(objForm, JSONArray.class);
//        JSONArray jsonArr4 = mapper.readValue(placeForm, JSONArray.class);
//        JSONArray jsonArr5 = mapper.readValue(qaForm, JSONArray.class);
//
//        List<Map<String,List<Visual.VisualResult.Person>>> personMapList = new ArrayList<>();
//        List<Visual.VisualResult.VisualObject> objectList = new ArrayList<>();
//        String jsonInString = "{}";
//
//        int idx = Integer.parseInt(reqMap.get("repVideoId").toString());
//
//        Map repMap = new HashMap();
//        repMap.put("repVideoId",repVideoId);
//        repMap.put("representImgIdx",representImgIdx);
//
//        //Vtt  정보 호출.
//        Map mapInfo = visualService.getRepImgInfo(repMap);
//        String title =MapUtil.getParameter(mapInfo,"title","");
//        String orifilename =MapUtil.getParameter(mapInfo,"orifilename","");
//        String starttimecode =MapUtil.getParameter(mapInfo,"starttimecode","");
//        String endtimecode =MapUtil.getParameter(mapInfo,"endtimecode","");
//
//        Visual.VisualResult visualResult = new Visual.VisualResult();
//        HashMap<String, List<Visual.VisualResult.Person>> map = new HashMap<>();
//
//        //인물
//        for (int i = 0; i < jsonArr1.size(); i++) {
//            String faceCoordinate = jsonArr1.getJSONObject(i).get("faceCoordinate").toString();
//            String faceName = jsonArr1.getJSONObject(i).get("faceName").toString();
//            String faceAction = "";
//            if( jsonArr1.getJSONObject(i).get("faceAction") != null){
//                faceAction = jsonArr1.getJSONObject(i).get("faceAction").toString();
//            }
//            String faceEmotion = "";
//            if( jsonArr1.getJSONObject(i).get("faceEmotion") != null){
//                faceEmotion = jsonArr1.getJSONObject(i).get("faceEmotion").toString();
//            }
//            String faceScore = "";
//            if( jsonArr1.getJSONObject(i).get("score") != null){
//                faceScore = jsonArr1.getJSONObject(i).get("score").toString();
//            }
//
//            if (!"".equals(faceCoordinate)) {
//                Visual.VisualResult.Person person = new Visual.VisualResult.Person();
//                Visual.VisualResult.Rect faceRect = new Visual.VisualResult.Rect();
//                person.setBehavior(faceAction);
//                person.setEmotion(faceEmotion);
//                person.setScore(faceScore);
//                String faceRectArr[] = faceCoordinate.split(",");
//                faceRect.setMin_x(faceRectArr[0]);
//                faceRect.setMin_y(faceRectArr[1]);
//                faceRect.setMax_x((Integer.parseInt(faceRectArr[2]) + Integer.parseInt(faceRectArr[0]))+"");
//                faceRect.setMax_y((Integer.parseInt(faceRectArr[3]) + Integer.parseInt(faceRectArr[1]))+"");
//                person.setFace_rect(faceRect);
//                ArrayList<Visual.VisualResult.Person> personList = new ArrayList<>();
//                personList.add(person);
//                map.put(faceName,personList);
//            }
//        }
//
//        //인물전체
//        for (int i = 0; i < jsonArr2.size(); i++) {
//            //위치
//            String personAllCoordinate = jsonArr2.getJSONObject(i).get("personAllCoordinate").toString();
//            String personAllRectArr[] = personAllCoordinate.split(",");
//            Visual.VisualResult.Rect fullRect = new Visual.VisualResult.Rect();
//            fullRect.setMin_x(personAllRectArr[0]);
//            fullRect.setMin_y(personAllRectArr[1]);
//            fullRect.setMax_x((Integer.parseInt(personAllRectArr[2]) + Integer.parseInt(personAllRectArr[0]))+"");
//            fullRect.setMax_y((Integer.parseInt(personAllRectArr[3]) + Integer.parseInt(personAllRectArr[1]))+"");
//
//            String score = "";
//            if( jsonArr2.getJSONObject(i).get("score") != null){
//                score = jsonArr2.getJSONObject(i).get("score").toString();
//            }
//            String personAllName ="";
//            //인물전체
//            if(jsonArr2.getJSONObject(i).get("personAllName") != null){
//                personAllName = jsonArr2.getJSONObject(i).get("personAllName").toString();
//                List<Visual.VisualResult.Person> personList = map.get(personAllName);
//                if(personList==null||personList.size()==0){
//                    Visual.VisualResult.Person person = new Visual.VisualResult.Person();
//                    personList = new ArrayList<>();
//                    personList.add(person);
//                }
//                personList.get(0).setFull_rect(fullRect);
//                personList.get(0).setScore(score);
//
//                map.put(personAllName,personList);
//            }else{//객체
//                Visual.VisualResult.VisualObject object = new Visual.VisualResult.VisualObject();
//                object.setObject_name("person");
//                object.setObject_rect(fullRect);
//                object.setScore(score);
//                objectList.add(object);
//            }
//        }
//
//        //객체
//        for (int i = 0; i < jsonArr3.size(); i++) {
//            Visual.VisualResult.Rect tmpPobjRect = new Visual.VisualResult.Rect();
//            String objCoordinate = jsonArr3.getJSONObject(i).get("objCoordinate").toString();
//            String objCoordinateRectArr[] = objCoordinate.split(",");
//            tmpPobjRect.setMin_x(objCoordinateRectArr[0]);
//            tmpPobjRect.setMin_y(objCoordinateRectArr[1]);
//            tmpPobjRect.setMax_x((Integer.parseInt(objCoordinateRectArr[2]) + Integer.parseInt(objCoordinateRectArr[0]))+"");
//            tmpPobjRect.setMax_y((Integer.parseInt(objCoordinateRectArr[3]) + Integer.parseInt(objCoordinateRectArr[1]))+"");
//            String relatedObjName = "";
//            if(jsonArr3.getJSONObject(i).get("relatedObj") != null){
//                relatedObjName = jsonArr3.getJSONObject(i).get("relatedObj").toString();
//            }
//            String score = "";
//            if( jsonArr3.getJSONObject(i).get("score") != null){
//                score = jsonArr3.getJSONObject(i).get("score").toString();
//            }
//            if(jsonArr3.getJSONObject(i).get("personName") != null) {
//                String personName = jsonArr3.getJSONObject(i).get("personName").toString();
//                String description = null;
//                if(jsonArr3.getJSONObject(i).get("description") != null)
//                    description = jsonArr3.getJSONObject(i).get("description").toString();
//
//                Visual.VisualResult.Person.RelatedObject relObject = new Visual.VisualResult.Person.RelatedObject();
//                relObject.setObject_name(relatedObjName);
//                relObject.setObject_rect(tmpPobjRect);
//                relObject.setPredicate(description);
//                relObject.setScore(score);
//
//                List<Visual.VisualResult.Person> personList =  map.get(personName);
//                List<Visual.VisualResult.Person.RelatedObject> relobjectList =  personList.get(0).getRelated_object();
//                relobjectList.add(relObject);
//                personList.get(0).setRelated_object(relobjectList);
//                map.put(personName,personList);
//            }else{
//                Visual.VisualResult.VisualObject object = new Visual.VisualResult.VisualObject();
//                object.setObject_name(relatedObjName);
//                object.setObject_rect(tmpPobjRect);
//                object.setScore(score);
//                objectList.add(object);
//            }
//        }
//
//        String place ="";
//        String placeDetail ="";
//        String relatedPeople = "";
//        String emotionAct = "";
//        String relationObj = "";
//        String score = "";
//
//        if(jsonArr4.size() >0){
//            if(jsonArr4.getJSONObject(0).get("place") != null) {
//                place = jsonArr4.getJSONObject(0).get("place").toString();
//            }
//            if(jsonArr4.getJSONObject(0).get("placeDetail") != null) {
//                placeDetail = jsonArr4.getJSONObject(0).get("placeDetail").toString();
//            }
//            if(jsonArr4.getJSONObject(0).get("relatedPeople") != null) {
//                relatedPeople = jsonArr4.getJSONObject(0).get("relatedPeople").toString();
//            }
//
//            if( jsonArr4.getJSONObject(0).get("score") != null){
//                score = jsonArr4.getJSONObject(0).get("score").toString();
//            }
//        }
//
//        if(jsonArr5.size() >0){
//            if(jsonArr5.getJSONObject(0).get("emotional_behavior") != null) {
//                emotionAct = jsonArr5.getJSONObject(0).get("emotional_behavior").toString();
//            }
//            if(jsonArr5.getJSONObject(0).get("relate_between_obj") != null) {
//                relationObj = jsonArr5.getJSONObject(0).get("relate_between_obj").toString();
//            }
//        }
//
//        personMapList.add(map);
//        visualResult.setPerson(personMapList);
//        visualResult.setObject(objectList);
//        visualResult.setPeriod_frame_num(repImgSeq);
//        visualResult.setPeriod_num(repSectionId);
//        visualResult.setImage(repFileId);
//        visualResult.setPlace(place);
//        visualResult.setSpot(placeDetail);
//        visualResult.setRelated_person(relatedPeople);
//        visualResult.setStart_time(starttimecode);//구간샷이미지 시작시간
//        visualResult.setEnd_time(endtimecode);//구간샷이미지 종료시간
//        visualResult.setEmotional_behavior(emotionAct);
//        visualResult.setRelate_between_obj(relationObj);
//        visualResult.setScore(score);
//
//
//        ArrayList<Visual.VisualResult> list = new ArrayList<>();
//        list.add(visualResult);
//
//        Map<String, Object> paramMap = new HashMap();
//        paramMap.put("shotId", repShotid);
//        paramMap.put("idx",reqMap.get("repVideoId").toString());
//        paramMap.put("userId", userid);
//        paramMap.put("repImgSeq", repImgSeq);
//        int resultCnt = visualService.getPutMetaInfo(paramMap, list);
//
//        Map resultMap = new LinkedHashMap();
//        if (resultCnt > 0) {
//            LinkedHashMap<String,Object> metaInfoList = visualService.getSelectMetaInfo(paramMap);
//            String jsonString = metaInfoList.get("vtt_meta_json").toString();
//            List<Map> metaJsonMap = mapper.readValue(jsonString, ArrayList.class);
//
//            resultMap.put("success", true);
//            resultMap.put("message", "저장되었습니다.");
//            model.addAttribute("resultMap",resultMap);
//            model.addAttribute("metaJsonMap",metaJsonMap);
//        } else {
//            resultMap.put("success", false);
//            resultMap.put("message", "저장 실패하였습니다.");
//            model.addAttribute("resultMap",resultMap);
//            model.addAttribute("metaJsonMap",null);
//        }
//        return "visual/visual_meta_info_list";
//    }

    /*
     *  대표이미지 삭제
     */
    @RequestMapping(value = "/visual/deleteRepImg")
    public String deleteRepImg(Model model, @RequestParam Map<String, Object> reqMap, Principal principal) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("shotId", reqMap.get("shotId").toString());
        paramMap.put("idx", reqMap.get("idx").toString());
        paramMap.put("repImgSeq", reqMap.get("repImgSeq").toString());
        int resultCnt = visualService.deleteRepImg(paramMap);
        Map resultMap = new LinkedHashMap();
        if (resultCnt > 0) {
            LinkedHashMap<String, Object> metaInfoList = visualService.getSelectMetaInfo(paramMap);
            String jsonString = metaInfoList.get("vtt_meta_json").toString();
            List<Map> metaJsonMap = mapper.readValue(jsonString, ArrayList.class);

            resultMap.put("success", true);
            resultMap.put("message", "삭제되었습니다.");
            model.addAttribute("resultMap", resultMap);
            model.addAttribute("metaJsonMap", metaJsonMap);
        } else {
            resultMap.put("success", false);
            resultMap.put("message", "삭제 실패하였습니다.");
            model.addAttribute("resultMap", resultMap);
            model.addAttribute("metaJsonMap", null);
        }
        return "visual/visual_meta_info_list";
    }

    /*
     *  시각정보 편집 화면 호출
     */
    @RequestMapping(value = "/visual/getMetaInfo")
    public String getMetaInfo(Model model, HttpServletRequest request, Principal principal) throws Exception {
        Map<String, Object> paramMap = RequestUtils.getParameterMap(request);
        paramMap.put("userid", principal.getName());
        LinkedHashMap<String, Object> metaInfoList = visualService.getSelectMetaInfo(paramMap);

        String jsonString = "";
        List<Map> metaJsonMap = null;
        if (metaInfoList != null && metaInfoList.size() > 0) {
            jsonString = metaInfoList.get("vtt_meta_json").toString();
            //metaJsonMap = (List<Map>)JSONUtils.jsonStringToObject(jsonString, ArrayList.class);

        }

        model.addAttribute("metaJsonMap", jsonString);
        return "visual/visual_meta_info_list";
    }

    /*
     *  이전 시각정보 편집 화면 호출
     */
    @RequestMapping(value = "/visual/getPrevMetaInfo")
    public String getPrevMetaInfo(Model model, HttpServletRequest request, Principal principal) throws Exception {
        Map<String, Object> paramMap = RequestUtils.getParameterMap(request);
        paramMap.put("userid", principal.getName());

        String otheruserid = (String)paramMap.get("otheruserid");
        if(otheruserid == null || "".equals(otheruserid)){
            paramMap.put("selectuserid", principal.getName());
        }else{
            paramMap.put("selectuserid", otheruserid);
        }

        LinkedHashMap<String, Object> metaInfoList = visualService.getSelectPrevMetaInfo(paramMap);

        String jsonString = "";
        List<Map> metaJsonMap = null;
        if (metaInfoList != null && metaInfoList.size() > 0) {
            jsonString = metaInfoList.get("vtt_meta_json").toString();
            //metaJsonMap = (List<Map>)JSONUtils.jsonStringToObject(jsonString, ArrayList.class);

        }

        model.addAttribute("metaJsonMap", jsonString);
        return "visual/visual_meta_info_list";
    }
    /*
    @RequestMapping(value = "/visual/getMetaInfo")
    public String getMetaInfo(Model model, HttpServletRequest request, Principal principal) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> paramMap = RequestUtils.getParameterMap(request);
        paramMap.put("userId", principal.getName());
        LinkedHashMap<String,Object> metaInfoList = visualService.getSelectMetaInfo(paramMap);
        String jsonString = "";
        List<Map>  metaJsonMap = null;
        if(metaInfoList != null && metaInfoList.size()>0) {
            jsonString = metaInfoList.get("vtt_meta_json").toString();
            metaJsonMap = mapper.readValue(jsonString, ArrayList.class);
        }
        model.addAttribute("metaJsonMap",metaJsonMap);
        return "visual/visual_meta_info_list";
    }
    */

    @RequestMapping(value = "/visual/updateShot")
    @ResponseBody
    public String updateShot(HttpServletRequest request) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        Map param = RequestUtils.getParameterMap(request);
        int result = storyboardService.updateShotItem(param);
        resultMap.put("result", result);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(resultMap);
        return json;
    }
}
