package com.konantech.spring.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.konantech.spring.domain.content.ContentField;
import com.konantech.spring.domain.content.ContentQuery;
import com.konantech.spring.domain.visual.Visual;
import com.konantech.spring.domain.vtt.FrameImage;
import com.konantech.spring.domain.vtt.RepImgVo;
import com.konantech.spring.mapper.ContentMapper;
import com.konantech.spring.mapper.VisualMapper;
import com.konantech.spring.service.ContentService;
import com.konantech.spring.service.VisualService;
import com.konantech.spring.util.MapUtil;
import com.konantech.spring.util.RequestUtils;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.security.Principal;
import java.util.*;

@Service("visualService")
public class VisualServiceImpl implements VisualService {


    @Autowired
    private VisualMapper visualMapper;

    @Autowired
    private ContentMapper contentMapper;

    @Value("${darc.proxyShotFolder}")
    private String proxyShotFolder; //application.yml  관련 설정.

    @Value("${darc.shotServerUrl}")
    private String shotServerUrl; //application.yml  관련 설정.

    private static Logger log = LoggerFactory.getLogger(VisualServiceImpl.class);

    @Override
    public int getPutRepreImg(Map map) throws Exception {
        return visualMapper.getPutRepreImg(map);
    }

    @Override
    public int getCntRepreImg(Map map) throws Exception {
        return visualMapper.getCntRepreImg(map);
    }

    @Override
    public RepImgVo getSelectRepImg(Map map) throws Exception {
        return visualMapper.getSelectRepImg(map);
    }

    @Override
    public RepImgVo getSelectPrevRepImg(Map map) throws Exception {
        return visualMapper.getSelectPrevRepImg(map);
    }

//    @Override
//    public List<RepImgVo> getSelectRepImg(Map map) throws Exception {
//        List<RepImgVo> repImgList = visualMapper.getSelectRepImg(map);
//
//        log.debug("VisualServiceImpl repImgList ===> " + repImgList);
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonInStr ="";
//        ClientJson clientJson = null;
//        for(RepImgVo vo : repImgList){
//            String getJsonStr = vo.getRepJson();
//            if(!Boolean.valueOf(vo.getSavedChk())){
//                clientJson = new ClientJson();
//                //서강대 json=>client.json 파싱
//                ConvertObj convertObj = mapper.readValue(getJsonStr, ConvertObj.class);
//                String cap = convertObj.getCap();
//                for (ConvertObj.Result result : convertObj.getResults()) {
//                    for (ConvertObj.Result.Module_result module_result : result.getModule_result()) {
//                        ConvertObj.Result.Module_result.Label label = module_result.getLabel().get(0);
//
//                        //위치
//                        ClientJson.Rect rect = new ClientJson.Rect();
//                        rect.setX(module_result.getPosition().getX());
//                        rect.setY(module_result.getPosition().getY());
//                        rect.setW(module_result.getPosition().getW());
//                        rect.setH(module_result.getPosition().getH());
//
//                        if (result.getModule_name().equals("object")) {
//                            if (label.getDescription().equals("person")) {
//                                ClientJson.PersonFull personFull = new ClientJson.PersonFull();
//                                personFull.setFull_rect(rect);
//                                personFull.setScore(label.getScore());
//                                clientJson.getPersonFull().add(personFull);
//                            } else {
//                                ClientJson.Object object = new ClientJson.Object();
//                                object.setObject_rect(rect);
//                                object.setObject_name(label.getDescription());
//                                object.setScore(label.getScore());
//                                clientJson.getObject().add(object);
//                            }
//                        } else if (result.getModule_name().equals("friends.face")) {
//                            ClientJson.Person person = new ClientJson.Person();
//                            person.setRect(rect);
//                            person.setPerson_name(label.getDescription());
//                            person.setScore(label.getScore());
//                            clientJson.getPerson().add(person);
//                        } else if (result.getModule_name().equals("friends.place")) {
//                            ClientJson.Place place = new ClientJson.Place();
//                            place.setPlace(label.getDescription());
//                            place.setScore(label.getScore());
//                            clientJson.getPlaces().add(place);
//                        }
//                    }
//                }
//                clientJson.setCap(cap);
//            }else{
//                //visual json=>client.json 파싱
//                List<Map<String,Object>> visual = mapper.readValue(getJsonStr,  ArrayList.class);
//                for(Map mVisualResult : visual) {
//
//                    getJsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mVisualResult);
//                    Visual.VisualResult visualResult = mapper.readValue(getJsonStr,  Visual.VisualResult.class);
//
//                    if(!visualResult.getPeriod_frame_num().equals(vo.getRepImgSeq())){
//                        continue;
//                    }
//                    clientJson = new ClientJson();
//
//                    clientJson.setRelate_between_obj(visualResult.getRelate_between_obj());
//                    clientJson.setEmotional_behavior(visualResult.getEmotional_behavior());
//
//                    ClientJson.Place place = new ClientJson.Place();
//                    place.setPerson_name(visualResult.getRelated_person());
//                    place.setPlace(visualResult.getPlace());
//                    place.setSpot(visualResult.getSpot());
//                    place.setScore(visualResult.getScore());
//                    clientJson.getPlaces().add(place);
//
//                    //persons
//                    for (Map<String, List<Visual.VisualResult.Person>> personMap : visualResult.getPerson()) {
//                        for (String name : personMap.keySet()) {
//
//                            Visual.VisualResult.Person person = personMap.get(name).get(0);
//
//                            //인물
//                            ClientJson.Person clientPerson = new ClientJson.Person();
//                            clientPerson.setPerson_name(name);
//                            clientPerson.setBehavior(person.getBehavior());
//                            clientPerson.setEmotion(person.getEmotion());
//                            clientPerson.setScore(person.getScore());
//                            //위치
//                            Visual.VisualResult.Rect mFace = person.getFace_rect();
//                            if(mFace!=null) {
//                                ClientJson.Rect rect = new ClientJson.Rect();
//                                rect.setX(mFace.getMin_x());
//                                rect.setY(mFace.getMin_y());
//                                rect.setMax_x(mFace.getMax_x());
//                                rect.setMax_y(mFace.getMax_y());
//                                clientPerson.setRect(rect);
//                            }
//                            clientJson.getPerson().add(clientPerson);
//
//                            //인물 전체
//                            //위치
//                            Visual.VisualResult.Rect mFull = person.getFull_rect();
//                            if(mFull!=null) {
//                                ClientJson.PersonFull clientPersonFull = new ClientJson.PersonFull();
//                                clientPersonFull.setPerson_name(name);
//                                ClientJson.Rect rect = new ClientJson.Rect();
//                                rect.setX(mFull.getMin_x());
//                                rect.setY(mFull.getMin_y());
//                                rect.setMax_x(mFull.getMax_x());
//                                rect.setMax_y(mFull.getMax_y());
//                                clientPersonFull.setFull_rect(rect);
//                                clientPersonFull.setScore(person.getScore());
//                                clientJson.getPersonFull().add(clientPersonFull);
//                            }
//
//                            for (Visual.VisualResult.Person.RelatedObject relObject : person.getRelated_object()) {
//
//                                //객체
//                                ClientJson.Object object = new ClientJson.Object();
//                                object.setObject_name(relObject.getObject_name());
//                                object.setPerson_name(name);
//                                object.setPredicate(relObject.getPredicate());
//                                object.setScore(relObject.getScore());
//                                //위치
//                                Visual.VisualResult.Rect mObject = relObject.getObject_rect();
//                                if(mObject!=null) {
//                                    ClientJson.Rect rect = new ClientJson.Rect();
//                                    rect.setX(mObject.getMin_x());
//                                    rect.setY(mObject.getMin_y());
//                                    rect.setMax_x(mObject.getMax_x());
//                                    rect.setMax_y(mObject.getMax_y());
//                                    object.setObject_rect(rect);
//                                }
//                                clientJson.getObject().add(object);
//                            }
//                        }
//                    }
//                    //object
//                    for (Visual.VisualResult.VisualObject visualObject : visualResult.getObject()) {
//
//                        if(!visualObject.getObject_name().equals("person")) {
//                            //객체
//                            ClientJson.Object object = new ClientJson.Object();
//                            object.setObject_name(visualObject.getObject_name());
//                            object.setScore(visualObject.getScore());
//                            //위치
//                            Visual.VisualResult.Rect mRect = visualObject.getObject_rect();
//                            if (mRect != null) {
//                                ClientJson.Rect rect = new ClientJson.Rect();
//                                rect.setX(mRect.getMin_x());
//                                rect.setY(mRect.getMin_y());
//                                rect.setMax_x(mRect.getMax_x());
//                                rect.setMax_y(mRect.getMax_y());
//                                object.setObject_rect(rect);
//                            }
//                            clientJson.getObject().add(object);
//                        }else{
//                            //인물 전체
//                            ClientJson.PersonFull clientPersonFull = new ClientJson.PersonFull();
//                            clientPersonFull.setScore(visualObject.getScore());
//                            //위치
//                            Visual.VisualResult.Rect mRect = visualObject.getObject_rect();
//                            if (mRect != null) {
//                                ClientJson.Rect rect = new ClientJson.Rect();
//                                rect.setX(mRect.getMin_x());
//                                rect.setY(mRect.getMin_y());
//                                rect.setMax_x(mRect.getMax_x());
//                                rect.setMax_y(mRect.getMax_y());
//                                clientPersonFull.setFull_rect(rect);
//                            }
//                            clientJson.getPersonFull().add(clientPersonFull);
//                        }
//                    }
//                }
//            }
//            jsonInStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(clientJson);
//            vo.setRepJson(jsonInStr);
//        }
//        return repImgList;
//    }

    @Override
    public int getPutMetaInfo(Map paramMap, List list) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String repImgSeq = (String) paramMap.get("repImgSeq");
        String jsonInString = "";

        LinkedHashMap<String, Object> metaInfoList = getSelectMetaInfo(paramMap);
        if (metaInfoList != null && metaInfoList.get("vtt_meta_json") != null) {
            String jsonString = metaInfoList.get("vtt_meta_json").toString();
            List<Map> visualPojo = mapper.readValue(jsonString, ArrayList.class);
            boolean newVisualInfo = true;
            Object temp = null;
            for (Map mVisual : visualPojo) {
                String oldVisualJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mVisual);
                Visual.VisualResult oldVIsualResult = mapper.readValue(oldVisualJson, Visual.VisualResult.class);
                list.add(oldVIsualResult);
                if (repImgSeq.equals(oldVIsualResult.getPeriod_frame_num())) {
                    newVisualInfo = false;
                    temp = oldVIsualResult;
                }
            }
            if (!newVisualInfo) {
                list.remove(temp);
            }
        }
        Collections.sort(list, (o1, o2) -> {
            String so1 = ((Visual.VisualResult) o1).getPeriod_frame_num();
            String so2 = ((Visual.VisualResult) o2).getPeriod_frame_num();
            return so1.compareTo(so2);
        });
        jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(list);
        paramMap.put("vtt_meta_json", jsonInString);
        return visualMapper.getPutMetaInfo(paramMap);
    }

    @Override
    public int getPutMetaInfo(Map paramMap) throws Exception {
        return visualMapper.getPutMetaInfo(paramMap);
    }

    @Override
    public LinkedHashMap<String, Object> getSelectMetaInfo(Map<String, Object> map) throws Exception {
        return visualMapper.getSelectMetaInfo(map);
    }

    @Override
    public LinkedHashMap<String, Object> getSelectFrameImgInfo(Map<String, Object> map) throws Exception {
        return visualMapper.getSelectFrameImgInfo(map);
    }

    @Override
    public LinkedHashMap<String, Object> getSelectPrevMetaInfo(Map<String, Object> map) throws Exception {
        return visualMapper.getSelectPrevMetaInfo(map);
    }

    @Override
    public Map getRepImgInfo(Map map) throws Exception {
        return visualMapper.getRepImgInfo(map);
    }

    @Override
    public int getPutSecInfo(Map<String, Object> map) throws Exception {
        return visualMapper.getPutSecInfo(map);
    }

    @Override
    public List<LinkedHashMap<String, Object>> getSecInfo(Map<String, Object> map) throws Exception {
        return visualMapper.getSecInfo(map);
    }

//    @Override
//    public String getJsonData(Map<String, Object> map) throws Exception {
//        List<String> metaData = visualMapper.getJsonData(map);
//        ObjectMapper mapper = new ObjectMapper();
//        List<Visual.VisualResult> list = new ArrayList<>();
//        for(String json : metaData){
//
//            List<Map> visualResults = mapper.readValue(json, ArrayList.class);
//            for(Map mVisualResult : visualResults){
//                String sVisualJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mVisualResult);
//                Visual.VisualResult visualResult = mapper.readValue(sVisualJson, Visual.VisualResult.class);
//                list.add(visualResult);
//            }
//        }
//        ContentQuery param = new ContentQuery();
//        param.setIdx(Integer.parseInt((String)map.get("idx")));
//        ContentField video = contentMapper.getContentItem(param);
//        Visual result = new Visual();
//        result.setFile_name(video.getOrifilename());
//        result.setRegisted_name(video.getTitle());
//        result.setVisual_results(list);
//        String json = mapper.writeValueAsString(result);
//        return json;
//    }

    @Override
    public String getJsonData(Map<String, Object> map) throws Exception {
/** 2019.09.30 getJsonData -> getNewJsonData 변경
 List<Map> shotInfoList = visualMapper.getMetaShotInfo(map);
 ObjectMapper mapper = new ObjectMapper();
 List<Object> visualDatalist = new ArrayList<>();

 //대표어 매핑 정의 JSON
 JSONObject repJson = (JSONObject)JSONSerializer.toJSON("{\"cake\" : \"cakes\",\"chair\" : \"chair(stool)\",\"baby_bed\" : \"bed\",\"computer_keyboard\" : \"computer\",\"computer_mouse\" : \"computer\",\"cup\" : \"glass\",\"cup or mug\" : \"glass\",\"filling_cabinet\" : \"cabinet\",\"milk_can\" : \"can\",\"cards\" : \"card\",\"donut\" : \"donuts\",\"strapless_dress\" : \"dress\",\"electric_fan\" : \"fan\",\"paper\" : \"paper(report)\",\"newspaper\" : \"paper(report)\",\"cell_phone\" : \"phone\",\"swimming_trunks\" : \"swimsuit\",\"dining_table\" : \"table\",\"tv_or_monitor\" : \"TV\",\"vacuum\" : \"vacuum cleaner\",\"washer\" : \"washing machine\",\"pie\" : \"tart\",\"sandwich\" : \"sandwitch\",\"wineglass\" : \"glass\",\"magazine\" : \"book\"}");

 for(Map shotInfoMap: shotInfoList){
 map.put("shotid", shotInfoMap.get("shotid"));

 List<String> metaData = visualMapper.getShotMetaJsonData(map);
 List<Object> list = new ArrayList<>();

 for(String json : metaData){
 //배열이 아닌 경우 배열로 인식되게 변경
 if(!json.startsWith("[")){
 json = "["+json+"]";
 }

 List<Map> visualResults = mapper.readValue(json, ArrayList.class);

 for(Map mVisualResult : visualResults){

 //PERSON_INFO FORMAT 변경
 ArrayList<Map> persons = (ArrayList<Map>) mVisualResult.get("persons");
 ArrayList<Map> newPersons = new ArrayList<>();
 for(Map person : persons){

 HashMap<String, String> person_info = (HashMap<String, String>) person.get("person_info");

 Object emotion = person_info.get("emotion");

 if(emotion instanceof Map){

 HashMap<String, Object> emotionMap = (HashMap<String, Object>) emotion;
 person_info.replace("emotion", "");

 for(String em : emotionMap.keySet()) {

 if((Integer)emotionMap.get(em) > 0){
 person_info.put("emotion", em);
 }
 }
 }

 if(!person_info.containsKey("face_rect_score") || !person_info.containsKey("full_rect_score")){
 person_info.put("face_rect_score", "");
 person_info.put("full_rect_score", "");
 }

 person.replace("person_info", person_info);

 newPersons.add(person);
 }

 mVisualResult.replace("persons", newPersons);
 mVisualResult.remove("image_id");
 mVisualResult.remove("image_descs");

 String sVisualJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mVisualResult);

 Set key = repJson.keySet();
 Iterator<String> iter = key.iterator();

 //대표어로 교체
 while(iter.hasNext()){
 String repKey = iter.next();
 sVisualJson = sVisualJson.replace("\""+repKey+"\"", "\""+repJson.getString(repKey)+"\"");
 }

 JSONObject vJson = (JSONObject)JSONSerializer.toJSON(sVisualJson.replace("null", "''"));
 list.add(vJson);
 }
 }

 shotInfoMap.put("image_info", list);

 LinkedHashMap<String, Object> shotDataMap = new LinkedHashMap<>();

 shotDataMap.put("shot_id", shotInfoMap.get("shot_id"));
 shotDataMap.put("start_time", shotInfoMap.get("start_time"));
 shotDataMap.put("end_time", shotInfoMap.get("end_time"));
 shotDataMap.put("image_info", shotInfoMap.get("image_info"));

 visualDatalist.add(shotDataMap);

 }

 ContentQuery param = new ContentQuery();
 param.setIdx(Integer.parseInt((String)map.get("idx")));
 ContentField video = contentMapper.getContentItem(param);

 LinkedHashMap<String, Object> visualMap = new LinkedHashMap<>();

 visualMap.put("file_name", video.getOrifilename());
 visualMap.put("registed_name", video.getTitle());
 visualMap.put("visual_results", visualDatalist);

 String json = mapper.writeValueAsString(visualMap);

 2019.09.30 getJsonData -> getNewJsonData 변경
 */
        String json = "";
        return json;
    }

    @Override
    public String getNewJsonData(Map<String, Object> map) throws Exception {

        List<Map> shotInfoList = visualMapper.getMetaShotInfo(map);
        ObjectMapper mapper = new ObjectMapper();
        List<Object> visualDatalist = new ArrayList<>();

        //대표어 매핑 정의 JSON
        JSONObject repJson = (JSONObject) JSONSerializer.toJSON("{\"cake\" : \"cakes\",\"chair\" : \"chair(stool)\",\"baby_bed\" : \"bed\",\"computer_keyboard\" : \"computer\",\"computer_mouse\" : \"computer\",\"cup\" : \"glass\",\"cup or mug\" : \"glass\",\"filling_cabinet\" : \"cabinet\",\"milk_can\" : \"can\",\"cards\" : \"card\",\"donut\" : \"donuts\",\"strapless_dress\" : \"dress\",\"electric_fan\" : \"fan\",\"paper\" : \"paper(report)\",\"newspaper\" : \"paper(report)\",\"cell_phone\" : \"phone\",\"swimming_trunks\" : \"swimsuit\",\"dining_table\" : \"table\",\"tv_or_monitor\" : \"TV\",\"vacuum\" : \"vacuum cleaner\",\"washer\" : \"washing machine\",\"pie\" : \"tart\",\"sandwich\" : \"sandwitch\",\"wineglass\" : \"glass\",\"magazine\" : \"book\"}");

        for (Map shotInfoMap : shotInfoList) {
            map.put("shotid", shotInfoMap.get("shotid"));

            List<String> metaData = visualMapper.getShotMetaJsonData(map);
            String vidInfo = visualMapper.getVidInfo(map);
            List<Object> list = new ArrayList<>();

            for (String json : metaData) {
                //배열이 아닌 경우 배열로 인식되게 변경
                if (!json.startsWith("[")) {
                    json = "[" + json + "]";
                }

                List<Map> visualResults = mapper.readValue(json, ArrayList.class);

                for (Map mVisualResult : visualResults) {

                    //frame_id FORMAT 변경
                    String frame_id = (String) mVisualResult.get("frame_id");
                    frame_id = vidInfo + "_" + frame_id;
                    mVisualResult.replace("frame_id", frame_id);

                    //PERSON_INFO FORMAT 변경
                    ArrayList<Map> persons = (ArrayList<Map>) mVisualResult.get("persons");
                    ArrayList<Map> newPersons = new ArrayList<>();
                    for (Map person : persons) {

                        HashMap<String, String> person_info = (HashMap<String, String>) person.get("person_info");

                        Object emotion = person_info.get("emotion");

                        if (emotion instanceof Map) {

                            HashMap<String, Object> emotionMap = (HashMap<String, Object>) emotion;
                            person_info.replace("emotion", "");

                            for (String em : emotionMap.keySet()) {

                                if ((Integer) emotionMap.get(em) > 0) {
                                    person_info.put("emotion", em);
                                }
                            }
                        }

                        if (!person_info.containsKey("face_rect_score") || !person_info.containsKey("full_rect_score")) {
                            person_info.put("face_rect_score", "");
                            person_info.put("full_rect_score", "");
                        }

                        LinkedHashMap<String, String> person_info_new = new LinkedHashMap<>();
                        person_info_new.put("face_rect", person_info.get("face_rect"));
                        person_info_new.put("full_rect", person_info.get("full_rect"));
                        person_info_new.put("behavior", person_info.get("behavior"));
                        person_info_new.put("predicate", person_info.get("predicate"));
                        person_info_new.put("emotion", person_info.get("emotion"));
                        person_info_new.put("face_rect_score", person_info.get("face_rect_score"));
                        person_info_new.put("full_rect_score", person_info.get("full_rect_score"));

                        person.replace("person_info", person_info_new);

                        ArrayList<Map> related_objects_list = (ArrayList<Map>) person.get("related_objects");

                        for (Map item_list : related_objects_list) {
                            item_list.remove("score");
                        }

                        newPersons.add(person);
                    }

                    mVisualResult.replace("persons", newPersons);
                    mVisualResult.remove("image_id");
                    mVisualResult.remove("image_descs");

                    String sVisualJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mVisualResult);

                    Set key = repJson.keySet();
                    Iterator<String> iter = key.iterator();

                    //대표어로 교체
                    while (iter.hasNext()) {
                        String repKey = iter.next();
                        sVisualJson = sVisualJson.replace("\"" + repKey + "\"", "\"" + repJson.getString(repKey) + "\"");
                    }

                    JSONObject vJson = (JSONObject) JSONSerializer.toJSON(sVisualJson.replace("null", "''"));
                    list.add(vJson);
                }
            }

            shotInfoMap.put("image_info", list);

            LinkedHashMap<String, Object> shotDataMap = new LinkedHashMap<>();
            boolean isShotId = MapUtils.getBooleanValue(map, "include_shot_id");
            if (isShotId) {
                shotDataMap.put("shot_id", shotInfoMap.get("shotid"));
            }
            shotDataMap.put("start_time", shotInfoMap.get("start_time"));
            shotDataMap.put("end_time", shotInfoMap.get("end_time"));
            shotDataMap.put("vid", vidInfo);
            shotDataMap.put("image_info", shotInfoMap.get("image_info"));

            visualDatalist.add(shotDataMap);

        }

        ContentQuery param = new ContentQuery();
        param.setIdx(Integer.parseInt((String) map.get("idx")));
        ContentField video = contentMapper.getContentItem(param);

        LinkedHashMap<String, Object> visualMap = new LinkedHashMap<>();

        visualMap.put("file_name", video.getOrifilename());
//        visualMap.put("registed_name", video.getTitle());
        visualMap.put("visual_results", visualDatalist);

        String json = mapper.writeValueAsString(visualMap);

        return json;
    }

    @Override
    public String getShotJsonData(Map<String, Object> map) throws Exception {

        List<LinkedHashMap> shotInfoList = visualMapper.getShotList(map);
        for (int i = 0; i < shotInfoList.size(); i++) {
            LinkedHashMap target_array = shotInfoList.get(i);
            String base_shot_id = (String) target_array.get("shot_id");
            String[] words = base_shot_id.split("_");
            int shot_num = Integer.parseInt(words[1]);
            int change_shot_num = shot_num+1;
            String number = "SHOT_"+String.format("%010d", change_shot_num);
            target_array.put("shot_id", number);
            shotInfoList.set(i, target_array);
        }
        ObjectMapper mapper = new ObjectMapper();
        List<Object> visualDatalist = new ArrayList<>();

        ContentQuery param = new ContentQuery();
        param.setIdx(Integer.parseInt((String) map.get("idx")));
        ContentField video = contentMapper.getContentItem(param);

        HashMap<String, Object> visualMap = new HashMap<>();

        visualMap.put("file_name", video.getOrifilename());
        visualMap.put("registed_name", video.getTitle());
        visualMap.put("shot_results", shotInfoList);

        String json = mapper.writeValueAsString(visualMap);

        return json;
    }


    @Override
    public int deleteRepImg(Map<String, Object> map) throws Exception {
        int result = -1;
        visualMapper.deleteRepImg(map);
        LinkedHashMap<String, Object> metaInfo = visualMapper.getSelectMetaInfo(map);
        String json = (String) metaInfo.get("vtt_meta_json");

        return result;
    }

    @Override
    public List<Map> getWorkerList(Map<String, Object> param) throws Exception {
        return visualMapper.getWorkerList(param);
    }

    /* lee.jaewook */
    public ArrayList<FrameImage> getFrameImageList(HttpServletRequest request, Principal principal) throws Exception {
        Map param = RequestUtils.getParameterMap(request);

        String shotid = RequestUtils.getParameter(request, "shotid", "");
        String shotname = RequestUtils.getParameter(request, "shotname");
        String assetfilepath = RequestUtils.getParameter(request, "assetfilepath", "");
        String assetfilename = RequestUtils.getParameter(request, "assetfilename", "");

        log.debug("assetfilepath - 1 ==> " + assetfilepath);
        assetfilepath = assetfilepath.replace("mnt/disk02/darc4data/proxyshot", proxyShotFolder);

        int startframeindex = RequestUtils.getParameterInt(request, "startframeindex", 0);
        int endframeindex = RequestUtils.getParameterInt(request, "endframeindex", 0);
        int curPage = RequestUtils.getParameterInt(request, "curPage", 0);
        int pageCnt = RequestUtils.getParameterInt(request, "pageCnt", 0);

        int frame_cut = RequestUtils.getParameterInt(request, "frame_cut", 7);

        log.debug("shotid ==> " + shotid);
        log.debug("assetfilepath - 3 ==> " + assetfilepath);
        log.debug("assetfilename ==> " + assetfilename);
        log.debug("frame_cut ==> " + frame_cut);
        log.debug("shotname ==> " + shotname);
        if (ContentService.FRAMES_PER_SECOND == 0) {
            frame_cut = 1;
            endframeindex ++;
        }

        ArrayList<Integer> idx_list = new ArrayList<Integer>();

        int s_idx = startframeindex == 1 ? 0 : startframeindex;
        int e_idx = endframeindex;
        int calcPageCnt = pageCnt;

        //첫페이지가 아니면 전페이지 마지막 이미지 정보 추가
        if (curPage > 1) {
            pageCnt = 11;
            calcPageCnt = 10;
        }

        for (int i = 0; i < pageCnt; i++) {
            int step = (frame_cut / 2);
            int new_idx = (s_idx + step) + ((curPage - 1) * calcPageCnt * frame_cut) + (i * frame_cut);

            if (curPage > 1) {
                new_idx = new_idx - frame_cut;
            }
            /*해당 샷의 마지막 장 추가 */
            if (new_idx >= e_idx) {
                //new_idx = e_idx;
                //idx_list.add(new_idx);

                break;
            }
            idx_list.add(new_idx);
        }
        if (idx_list.size() < 1) {
            idx_list.add((s_idx + e_idx) / 2);
        }
        log.debug("idx_list ==> [" + idx_list.toString() + "]");

        /* lee.jaewook */
        param.put("userid", principal.getName());
        List<Map> frameList = visualMapper.getFrameImageInfo(param);

        String imgFilePath = proxyShotFolder + "/" + assetfilepath;
        String strImgExt = "jpg|jpeg|png|gif|bmp"; //허용할 이미지타입

        log.debug("imgFilePath ==> " + imgFilePath);

//        ArrayList<SectionVo> sectionShotList = new ArrayList<>();
//        SectionVo sectionVo;
        ArrayList<FrameImage> sectionShotList = new ArrayList<>();
        ArrayList<String> filepatharry = new ArrayList<String>();


        for (int i = 0; i < idx_list.size(); i++) {
            String fileIdx = Integer.toString(idx_list.get(i));
            String fullPath = imgFilePath + "/" + "IMAGE_" + StringUtils.leftPad(fileIdx, 10, "0") + ".jpg";

            filepatharry.add(fullPath);
        }

        log.debug("filepatharry ==> [" + filepatharry.toString() + "]");

        File[] fileList = new File[filepatharry.size()];
        for (int i = 0; i < filepatharry.size(); i++) {
            File file1 = new File(filepatharry.get(i));
            fileList[i] = file1;
        }


        log.debug("fileSize =>[" + fileList.length + "]");

        if (fileList != null) {
            //파일명 정렬렬
            Arrays.sort(fileList, (Comparator<Object>) (o1, o2) -> {
                String s1 = ((File) o1).getName();
                String s2 = ((File) o2).getName();

                return s1.compareTo(s2);
            });

            for (File tempFile : fileList) {
                log.debug("tempFile => [" + tempFile + "]");
                log.debug("tempFile.isFile() => [" + tempFile.isFile() + "]");

                if (tempFile.isFile()) {
                    String tempPath = tempFile.getParent();
                    String ext = tempFile.getName().substring(tempFile.getName().lastIndexOf(".") + 1);
                    int Idx = tempFile.getName().lastIndexOf(".");

                    log.debug("fileStempPathize =>[" + tempPath + "]");
                    log.debug("ext =>[" + ext + "]");
                    log.debug("Idx =>[" + Idx + "]");

                    String fileId = tempFile.getName().substring(0, Idx);
                    log.debug("fileId =>[" + fileId + "]");
                    if (strImgExt.contains(ext.toLowerCase())) {
                        log.info("이미지 있음!");
                        log.info("Path :" + imgFilePath);
                        log.info("fileName :" + tempFile.getName());

                        FrameImage frameImage = new FrameImage();
                        frameImage.setVideoid(MapUtils.getIntValue(param, "videoid"));
                        frameImage.setSceneid(MapUtils.getIntValue(param, "sceneid"));
                        frameImage.setShotid(MapUtils.getIntValue(param, "shotid"));
                        frameImage.setAssetfilename(tempFile.getName());
                        frameImage.setAssetfilepath(assetfilepath.replace("/Volumes/ikon/darc4data/proxyshot", ""));
                        for (Map map : frameList) {
                            log.debug("map ===> " + map);
                            log.debug("tempFile.getName().equals(MapUtils.getString(map,\"assetfilename\")) ===> " + tempFile.getName().equals(MapUtils.getString(map, "assetfilename")));
                            log.debug("tempFile.getName() ===> " + tempFile.getName());
                            log.debug("MapUtils.getString(map,\"assetfilename\") ===> " + MapUtils.getString(map, "assetfilename"));

                            if (tempFile.getName().equals(MapUtils.getString(map, "assetfilename"))) {
                                frameImage.setUserid(MapUtils.getString(map, "userid"));
                                frameImage.setFrameimgid(MapUtils.getIntValue(map, "frameimgid", -1));
                                frameImage.setVtt_meta_idx(MapUtils.getIntValue(map, "vtt_meta_idx", -1));
                                frameImage.setSavechk(MapUtils.getString(map, "savechk"));

                                break;
                            }
                        }

                        if (fileList[0].getName().equals(tempFile.getName()) && curPage > 1) {
                            frameImage.setHiddenchk("true");
                        } else {
                            frameImage.setHiddenchk("false");
                        }

                        sectionShotList.add(frameImage);

                    } else {
                        log.info("이미지 없음!");
                        log.info("FileName :" + tempFile);
                    }
                }
            }
        }
        log.debug("getFrameImageList sectionShotList ===> " + sectionShotList);

        return sectionShotList;
    }


    public int getPutMetaInfo2(HttpServletRequest request, Principal principal) throws Exception {
        Map reqMap = RequestUtils.getParameterMap(request);
        int vtt_meta_idx = Integer.parseInt(MapUtil.getParameter(reqMap, "vtt_meta_idx", ""));

        reqMap.put("vtt_meta_json", MapUtil.getParameter(reqMap, "metaData", ""));
        reqMap.put("userid", principal.getName());

        log.debug("getPutMetaInfo2 - vtt_meta_idx ===> " + vtt_meta_idx);
        log.debug("getPutMetaInfo2 - reqMap ===> " + reqMap);

        if (vtt_meta_idx < 1) {
            log.debug("getPutMetaInfo2 if - vtt_meta_idx ===> " + vtt_meta_idx);
            return visualMapper.getPutMetaInfo2(reqMap);    // error
        } else {
            log.debug("getPutMetaInfo2 else - vtt_meta_idx ===> " + vtt_meta_idx);
            return visualMapper.getPutUpdateMetaInfo2(reqMap);
        }

    }

    @Override
    public LinkedHashMap<String, Object> getSelectMetaInfo(HttpServletRequest request, Principal principal) throws Exception {
        Map map = RequestUtils.getParameterMap(request);
        map.put("userid", principal.getName());

        return visualMapper.getSelectMetaInfo(map);
    }
}
