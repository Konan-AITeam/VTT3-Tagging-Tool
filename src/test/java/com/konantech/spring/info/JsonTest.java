package com.konantech.spring.info;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.konantech.spring.domain.convertobj.*;
import com.konantech.spring.domain.visual.*;
import com.konantech.spring.domain.vtt.SectionVo;
import com.konantech.spring.domain.workflow.CompJobParamProperty;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JsonTest {


    @Test
    public void test1() throws IOException {

        String jsonString = "{\"paramlist\": [{ \"type\": \"mainkey\", \"field\": \"idx\" }] }";
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, Object> map = mapper.readValue(jsonString, new HashMap<String, Object>().getClass());
        String json = mapper.writeValueAsString(map.get("paramlist"));
        CollectionType javaType = mapper.getTypeFactory().constructCollectionType(List.class, CompJobParamProperty.class);
        List<CompJobParamProperty> list = mapper.readValue(json, javaType);
        System.out.println(list);
        for (CompJobParamProperty l : list) {
            System.out.println(l);
        }

    }

    @Test
    public void test2() throws IOException {

        String jsonString = "{\"paramlist\": [{ \"type\": \"mainkey\", \"field\": \"idx\" }] }";
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, Object> map = mapper.readValue(jsonString, new HashMap<String, Object>().getClass());
        String json = mapper.writeValueAsString(map.get("paramlist"));
        List<CompJobParamProperty> list = mapper.readValue(json, new TypeReference<List<CompJobParamProperty>>() {
        });
        System.out.println(list);
        for (CompJobParamProperty l : list) {
            System.out.println(l);
        }

    }


    @Test
    public void test3() throws Exception {

        String jsonString = "{\"streams\": [{\"refs\": 1, \"tags\": {\"language\": \"und\", \"handler_name\": \"ISO Media file produced by Google Inc.\", \"creation_time\": \"2017-02-20T05:06:19.000000Z\"}, \"index\": 0, \"level\": 31, \"width\": 1280, \"height\": 720, \"is_avc\": \"true\", \"pix_fmt\": \"yuv420p\", \"profile\": \"Main\", \"bit_rate\": \"1681574\", \"duration\": \"239.989744\", \"codec_tag\": \"0x31637661\", \"nb_frames\": \"5754\", \"start_pts\": 0, \"time_base\": \"1/90000\", \"codec_name\": \"h264\", \"codec_type\": \"video\", \"start_time\": \"0.000000\", \"coded_width\": 1280, \"color_range\": \"tv\", \"color_space\": \"bt709\", \"disposition\": {\"dub\": 0, \"forced\": 0, \"lyrics\": 0, \"comment\": 0, \"default\": 1, \"karaoke\": 0, \"original\": 0, \"attached_pic\": 0, \"clean_effects\": 0, \"visual_impaired\": 0, \"hearing_impaired\": 0, \"timed_thumbnails\": 0}, \"duration_ts\": 21599077, \"coded_height\": 720, \"has_b_frames\": 1, \"r_frame_rate\": \"24000/1001\", \"avg_frame_rate\": \"517860000/21599077\", \"color_transfer\": \"bt709\", \"chroma_location\": \"left\", \"codec_long_name\": \"H.264 / AVC / MPEG-4 AVC / MPEG-4 part 10\", \"codec_time_base\": \"21599077/1035720000\", \"color_primaries\": \"bt709\", \"nal_length_size\": \"4\", \"codec_tag_string\": \"avc1\", \"bits_per_raw_sample\": \"8\", \"sample_aspect_ratio\": \"1:1\", \"display_aspect_ratio\": \"16:9\"}, {\"tags\": {\"language\": \"und\", \"handler_name\": \"ISO Media file produced by Google Inc.\", \"creation_time\": \"2017-02-20T05:06:19.000000Z\"}, \"index\": 1, \"profile\": \"LC\", \"bit_rate\": \"125595\", \"channels\": 2, \"duration\": \"240.001451\", \"codec_tag\": \"0x6134706d\", \"nb_frames\": \"10336\", \"start_pts\": 0, \"time_base\": \"1/44100\", \"codec_name\": \"aac\", \"codec_type\": \"audio\", \"sample_fmt\": \"fltp\", \"start_time\": \"0.000000\", \"disposition\": {\"dub\": 0, \"forced\": 0, \"lyrics\": 0, \"comment\": 0, \"default\": 1, \"karaoke\": 0, \"original\": 0, \"attached_pic\": 0, \"clean_effects\": 0, \"visual_impaired\": 0, \"hearing_impaired\": 0, \"timed_thumbnails\": 0}, \"duration_ts\": 10584064, \"sample_rate\": \"44100\", \"r_frame_rate\": \"0/0\", \"avg_frame_rate\": \"0/0\", \"channel_layout\": \"stereo\", \"bits_per_sample\": 0, \"codec_long_name\": \"AAC (Advanced Audio Coding)\", \"codec_time_base\": \"1/44100\", \"codec_tag_string\": \"mp4a\"}]}";
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, Object> map = mapper.readValue(jsonString, new HashMap<String, Object>().getClass());
        JSONArray jsonArray = JSONArray.fromObject(map.get("streams"));
        if (jsonArray != null && jsonArray.size() > 0) {
            JSONObject o1 = (JSONObject) jsonArray.get(0);
            int width = (int) o1.get("width");
            int height = (int) o1.get("height");
            System.out.println(width);
            System.out.println(height);
        }
    }


    @Test
    public void test4() throws Exception {
        String jsonFilePath ="Z:\\proxyshot\\2018\\04\\30\\624_1\\S00000\\S00000.json";
        String jsonFilePath2= "http://10.10.30.21:7070/darc4/proxyshot/2018/04/30/624_1/S00000/S00000.json";
        File jsonFile =new File(jsonFilePath2);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readValue(jsonFile, JsonNode.class);
        System.out.println(jsonNode);
        System.out.println("x: " + jsonNode.get(0).get("results").get(0).get("module_result").get(0).get("position").get("x"));
        System.out.println("y: " + jsonNode.get(0).get("results").get(0).get("module_result").get(0).get("position").get("y"));
        System.out.println("w: " + jsonNode.get(0).get("results").get(0).get("module_result").get(0).get("position").get("w"));
        System.out.println("h: " + jsonNode.get(0).get("results").get(0).get("module_result").get(0).get("position").get("h"));


    }

    @Test
    public void test5()throws Exception{
        String jsonFilePath ="Z:\\proxyshot\\2018\\04\\30\\624_1\\S00000\\S00000.json";
        File jsonFile =new File(jsonFilePath);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readValue(jsonFile, JsonNode.class);
        System.out.println(jsonNode.get(0).get("results"));
        System.out.println("size :" + jsonNode.get(0).get("results").size());

        String strImgExt = "jpg|jpeg|png|gif|bmp"; //허용할 이미지타입

        ArrayList<SectionVo> sectionShotList= new ArrayList<>();
        SectionVo sectionVo;

       /* for (int i = 0; i < getList.size(); i++) {
            System.out.println("folderPathIndex :" + (((JSONObject) getList.get(i)).get("folderPath")));
            jsonFilePath = systemPath + ((JSONObject) getList.get(i)).get("folderPath").toString();
            String tmpPath = ((JSONObject) getList.get(i)).get("folderPath").toString();

            File dirFile = new File(folderPath);
            File[] fileList = dirFile.listFiles();

            if (fileList != null) {
                for (File tempFile : fileList) {
                    if (tempFile.isFile()) {
                        String tempPath = tempFile.getParent();
                        String ext = tempFile.getName().substring(tempFile.getName().lastIndexOf(".") + 1);
                        int Idx = tempFile.getName().lastIndexOf(".");
                        String fileName = tempFile.getName().substring(0, Idx );

                        if (strImgExt.contains(ext.toLowerCase())) {
                            System.out.println("문자열 있음!");
                            System.out.println("Path :" + tmpPath);
                            System.out.println("fileName :" + fileName);
                            sectionVo = new SectionVo();
                            sectionVo.setSectionId(tempFile.getName());
                            sectionVo.setSectionPath(tmpPath);
                            sectionShotList.add(sectionVo);
                        } else {
                            System.out.println("문자열 없음!");
                            System.out.println("FileName :" + tempFile);
                        }
                    }
                }
            }
        }*/
    }

    @Test
    public void test6()throws Exception{
        /*VisualObject visualObject = new VisualObject();
        List<VisualObject> visualObjects = new ArrayList<>();
        visualObjects.add(visualObject);

       *//* Chendler person = new Chendler();
        List<Chendler> chendlers = new ArrayList<>();
        person.setChendler(chendlers);

        List<Chendler> persons = new ArrayList<>();

        persons.add(person);

        VisualResult visualResult = new VisualResult();
        visualResult.setVisualObjects(visualObjects);
        visualResult.setPersons(persons);
        List<VisualResult> visualResults = new ArrayList<>();
        visualResults.add(visualResult);
*//*
        Visual visual = new Visual();
       // visual.setVisualResults(visualResults);*/
    }

    @Test
    public void test7()throws Exception{
        /*String jsonString = "{ \"fileName\" : \"test\", \"registedName\" : \"OV201700310079.avi\", \"visualResults\" : [ { \"image\" : \"F0000070\", \"periodNum\" : \"1\", \"periodFrameNum\" : \"1\", \"startTime\" : \"00:00:00;00\", \"endTime\" : \"00:00:31;0\", \"place\" : \"living_room\", \"spot\" : \"\", \"relatedPerson\" : \"\", \"persons\" : [ { \"personName\" : \"Phoebe\", \"faceRect\" : { \"x\" : \"530\", \"y\" : \"44\", \"w\" : \"35\", \"h\" : \"47\" }, \"fullRect\" : { \"x\" : \"636\", \"y\" : \"197\", \"w\" : \"227\", \"h\" : \"264\" }, \"behavior\" : \"\", \"emotion\" : \"\", \"relatedObject\" : [ ] }, { \"personName\" : \"Joey\", \"faceRect\" : { \"x\" : \"755\", \"y\" : \"235\", \"w\" : \"44\", \"h\" : \"53\" }, \"fullRect\" : null, \"behavior\" : \"\", \"emotion\" : \"\", \"relatedObject\" : [ ] }, { \"personName\" : \"Ross\", \"faceRect\" : { \"x\" : \"214\", \"y\" : \"94\", \"w\" : \"26\", \"h\" : \"32\" }, \"fullRect\" : null, \"behavior\" : \"\", \"emotion\" : \"\", \"relatedObject\" : [ ] }, { \"personName\" : \"Monica\", \"faceRect\" : { \"x\" : \"324\", \"y\" : \"162\", \"w\" : \"32\", \"h\" : \"41\" }, \"fullRect\" : null, \"behavior\" : \"\", \"emotion\" : \"\", \"relatedObject\" : [ ] } ], \"visualObjects\" : [ { \"objectName\" : \"Phoebe\", \"objectRect\" : { \"x\" : \"636\", \"y\" : \"197\", \"w\" : \"227\", \"h\" : \"264\" } }, { \"objectName\" : \"person\", \"objectRect\" : { \"x\" : \"730\", \"y\" : \"232\", \"w\" : \"420\", \"h\" : \"433\" } }, { \"objectName\" : \"person\", \"objectRect\" : { \"x\" : \"320\", \"y\" : \"137\", \"w\" : \"140\", \"h\" : \"155\" } }, { \"objectName\" : \"bookshelf\", \"objectRect\" : { \"x\" : \"338\", \"y\" : \"5\", \"w\" : \"249\", \"h\" : \"138\" } }, { \"objectName\" : \"chair\", \"objectRect\" : { \"x\" : \"976\", \"y\" : \"404\", \"w\" : \"224\", \"h\" : \"279\" } }, { \"objectName\" : \"sofa\", \"objectRect\" : { \"x\" : \"197\", \"y\" : \"286\", \"w\" : \"522\", \"h\" : \"294\" } }, { \"objectName\" : \"sofa\", \"objectRect\" : { \"x\" : \"265\", \"y\" : \"276\", \"w\" : \"929\", \"h\" : \"414\" } }, { \"objectName\" : \"sofa\", \"objectRect\" : { \"x\" : \"16\", \"y\" : \"501\", \"w\" : \"503\", \"h\" : \"215\" } }, { \"objectName\" : \"table\", \"objectRect\" : { \"x\" : \"0\", \"y\" : \"350\", \"w\" : \"224\", \"h\" : \"218\" } }, { \"objectName\" : \"table\", \"objectRect\" : { \"x\" : \"594\", \"y\" : \"187\", \"w\" : \"149\", \"h\" : \"96\" } }, { \"objectName\" : \"table\", \"objectRect\" : { \"x\" : \"84\", \"y\" : \"259\", \"w\" : \"282\", \"h\" : \"108\" } }, { \"objectName\" : \"table\", \"objectRect\" : { \"x\" : \"470\", \"y\" : \"468\", \"w\" : \"548\", \"h\" : \"250\" } } ] } ] }";
        ObjectMapper mapper = new ObjectMapper();
        //JsonNode jsonNode = mapper.readValue(jsonString, JsonNode.class);
        Visual visualPojo = mapper.readValue(jsonString, Visual.class);
        ConvertObj convertObj = new ConvertObj();
        List<Result> resultList = new ArrayList<>();
        Result result = new Result();
        List<Module_result> module_resultList = new ArrayList<>();

        for (int i = 0; i <visualPojo.getVisual_results().size() ; i++) {
            for (int j = 0; j <visualPojo.getVisual_results().get(i).getPerson().size() ; j++) {
                Label label = new Label();
                Position position = new Position();
                List<Label> labelList = new ArrayList<>();
                Module_result module_result = new Module_result();

                label.setDescription(visualPojo.getVisual_results().get(i).getPerson().get(j).getPersonName());
                label.setBehavior(visualPojo.getVisual_results().get(i).getPerson().get(j).getBehavior());
                label.setEmotion(visualPojo.getVisual_results().get(i).getPerson().get(j).getEmotion());

                for (int k = 0; k < visualPojo.getVisual_results().get(i).getPerson().get(j).getRelated_object().size(); k++) {
                    label.setPredIcate(visualPojo.getVisual_results().get(i).getPerson().get(j).getRelated_object().get(k).getPredIcate());
                }
                position.setMin_x(visualPojo.getVisual_results().get(i).getPerson().get(j).getFace_rect().getMin_x());
                position.setMin_y(visualPojo.getVisual_results().get(i).getPerson().get(j).getFace_rect().getMin_y());
                position.setMax_y(visualPojo.getVisual_results().get(i).getPerson().get(j).getFace_rect().getMax_y());
                position.setMax_x(visualPojo.getVisual_results().get(i).getPerson().get(j).getFace_rect().getMax_x());

                labelList.add(label);
                module_result.setPosition(position);
                module_result.setLabel(labelList);
                module_resultList.add(module_result);
            }
        }
        result.setModule_name("friends.face");
        result.setModule_result(module_resultList);
        resultList.add(result);

        result = new Result();
        module_resultList = new ArrayList<>();

        for (int i = 0; i <visualPojo.getVisual_results().size() ; i++) {
            for (int j = 0; j < visualPojo.getVisual_results().get(i).getPerson().size(); j++) {
                for (int k = 0; k < visualPojo.getVisual_results().get(i).getPerson().get(j).getRelated_object().size(); k++) {
                    visualPojo.getVisual_results().get(i).getPerson().get(j).getRelated_object().get(k).getObject_name();
                    visualPojo.getVisual_results().get(i).getPerson().get(j).getRelated_object().get(k).getObject_rect().getMin_x();
                    visualPojo.getVisual_results().get(i).getPerson().get(j).getRelated_object().get(k).getObject_rect().getMin_y();
                    visualPojo.getVisual_results().get(i).getPerson().get(j).getRelated_object().get(k).getObject_rect().getMax_x();
                    visualPojo.getVisual_results().get(i).getPerson().get(j).getRelated_object().get(k).getObject_rect().getMax_y();
                    visualPojo.getVisual_results().get(i).getPerson().get(j).getRelated_object().get(k).getPredIcate();
                }
            }
        }


        for (int i = 0; i <visualPojo.getVisual_results().size() ; i++) {
            *//*for (int j = 0; j <visualPojo.getVisual_results().get(i).getPerson().size() ; j++) {
                Label label = new Label();
                Position position = new Position();
                List<Label> labelList = new ArrayList<>();
                Module_result module_result = new Module_result();
                if(visualPojo.getVisual_results().get(i).getPerson().get(j).getFull_rect() != null){
                    label.setDescription(visualPojo.getVisual_results().get(i).getPerson().get(j).getPersonName());
                    position.setMin_x(visualPojo.getVisual_results().get(i).getPerson().get(j).getFull_rect().getMin_x());
                    position.setMin_y(visualPojo.getVisual_results().get(i).getPerson().get(j).getFull_rect().getMin_y());
                    position.setMax_x(visualPojo.getVisual_results().get(i).getPerson().get(j).getFull_rect().getMax_x());
                    position.setMax_y(visualPojo.getVisual_results().get(i).getPerson().get(j).getFull_rect().getMax_y());
                    labelList.add(label);
                    module_result.setPosition(position);
                    module_result.setLabel(labelList);
                    module_resultList.add(module_result);
                }
            }*//*
            for (int j = 0; j <visualPojo.getVisual_results().get(i).getObject().size() ; j++) {
                Label label = new Label();
                Position position = new Position();
                List<Label> labelList = new ArrayList<>();
                Module_result module_result = new Module_result();
                label.setDescription(visualPojo.getVisual_results().get(i).getObject().get(j).getObject_name());
                position.setMin_x(visualPojo.getVisual_results().get(i).getObject().get(j).getObject_rect().getMin_x());
                position.setMin_y(visualPojo.getVisual_results().get(i).getObject().get(j).getObject_rect().getMin_y());
                position.setMax_x(visualPojo.getVisual_results().get(i).getObject().get(j).getObject_rect().getMax_x());
                position.setMax_y(visualPojo.getVisual_results().get(i).getObject().get(j).getObject_rect().getMax_y());
                labelList.add(label);
                module_result.setPosition(position);
                module_result.setLabel(labelList);
                module_resultList.add(module_result);
            }

            result.setModule_name("object");
            result.setModule_result(module_resultList);
        }
        resultList.add(result);

        result = new Result();
        module_resultList = new ArrayList<>();

        for (int i = 0; i <visualPojo.getVisual_results().size() ; i++) {
            Label label = new Label();
            Position position = new Position();
            List<Label> labelList = new ArrayList<>();
            Module_result module_result = new Module_result();

            label.setDescription( visualPojo.getVisual_results().get(i).getPlace());
            position.setMin_x("0");
            position.setMin_y("0");
            position.setMax_x("0");
            position.setMax_y("0");

            labelList.add(label);
            module_result.setPosition(position);
            module_result.setLabel(labelList);
            module_resultList.add(module_result);
        }

        result.setModule_name("friends.place");
        result.setModule_result(module_resultList);

        resultList.add(result);
        convertObj.setResults(resultList);


        String jsonStr2 = "{ \"image\" : \"http://10.10.18.183:8000/media/20180719/075405852245.jpg\", \"modules\" : \"friends\", \"token\" : 794, \"uploaded_date\" : \"2018-07-19T16:54:05.853056+09:00\", \"updated_date\" : \"2018-07-19T16:54:20.918260+09:00\", \"results\" : [ { \"module_name\" : \"friends.face\", \"module_result\" : [ { \"position\" : { \"y\" : 45.0, \"h\" : 47.0, \"w\" : 36.0, \"x\" : 531.0 }, \"label\" : [ { \"score\" : 0.8053488007539318, \"description\" : \"Phoebe\" }, { \"score\" : 0.09144275684842211, \"description\" : \"Rachel\" }, { \"score\" : 0.0567509368504981, \"description\" : \"Chandler\" }, { \"score\" : 0.020477722527108253, \"description\" : \"Monica\" }, { \"score\" : 0.014366044061593466, \"description\" : \"Joey\" } ] }, { \"position\" : { \"y\" : 236.0, \"h\" : 53.0, \"w\" : 45.0, \"x\" : 756.0 }, \"label\" : [ { \"score\" : 0.43254197821528884, \"description\" : \"Joey\" }, { \"score\" : 0.42309431337131104, \"description\" : \"Chandler\" }, { \"score\" : 0.054699865903254395, \"description\" : \"Monica\" }, { \"score\" : 0.04690245843384481, \"description\" : \"Rachel\" }, { \"score\" : 0.03276296994291983, \"description\" : \"Phoebe\" } ] }, { \"position\" : { \"y\" : 95.0, \"h\" : 32.0, \"w\" : 27.0, \"x\" : 214.0 }, \"label\" : [ { \"score\" : 0.3323653568168584, \"description\" : \"Ross\" }, { \"score\" : 0.3053644228926922, \"description\" : \"Joey\" }, { \"score\" : 0.14589356688094535, \"description\" : \"Monica\" }, { \"score\" : 0.10109753484690255, \"description\" : \"Chandler\" }, { \"score\" : 0.09554722814613846, \"description\" : \"Rachel\" } ] }, { \"position\" : { \"y\" : 163.0, \"h\" : 42.0, \"w\" : 32.0, \"x\" : 325.0 }, \"label\" : [ { \"score\" : 0.5957687758388809, \"description\" : \"Monica\" }, { \"score\" : 0.2380156735909849, \"description\" : \"Joey\" }, { \"score\" : 0.10735390563285553, \"description\" : \"Rachel\" }, { \"score\" : 0.0375068387031223, \"description\" : \"Chandler\" }, { \"score\" : 0.01121417494539982, \"description\" : \"Ross\" } ] } ] }, { \"module_name\" : \"friends.place\", \"module_result\" : [ { \"position\" : { \"y\" : 0.0, \"h\" : 720.0, \"w\" : 1280.0, \"x\" : 0.0 }, \"label\" : [ { \"score\" : 0.9618486166000366, \"description\" : \"living_room\" }, { \"score\" : 0.019586870446801186, \"description\" : \"coffee_shop\" }, { \"score\" : 0.006697223521769047, \"description\" : \"restaurant\" }, { \"score\" : 0.004953708034008741, \"description\" : \"bedroom\" }, { \"score\" : 0.00308360462076962, \"description\" : \"hospital_room\" } ] } ] }, { \"module_name\" : \"object\", \"module_result\" : [ { \"position\" : { \"y\" : 5.027, \"h\" : 138.379, \"w\" : 248.157, \"x\" : 338.262 }, \"label\" : [ { \"score\" : 0.576, \"description\" : \"bookshelf\" } ] }, { \"position\" : { \"y\" : 405.072, \"h\" : 279.813, \"w\" : 225.339, \"x\" : 977.383 }, \"label\" : [ { \"score\" : 0.392, \"description\" : \"chair\" } ] }, { \"position\" : { \"y\" : 197.039, \"h\" : 263.589, \"w\" : 227.976, \"x\" : 636.757 }, \"label\" : [ { \"score\" : 0.57, \"description\" : \"person\" } ] }, { \"position\" : { \"y\" : 231.97, \"h\" : 434.226, \"w\" : 420.435, \"x\" : 730.239 }, \"label\" : [ { \"score\" : 0.567, \"description\" : \"person\" } ] }, { \"position\" : { \"y\" : 137.412, \"h\" : 154.693, \"w\" : 139.729, \"x\" : 320.094 }, \"label\" : [ { \"score\" : 0.305, \"description\" : \"person\" } ] }, { \"position\" : { \"y\" : 286.446, \"h\" : 293.835, \"w\" : 522.91, \"x\" : 196.738 }, \"label\" : [ { \"score\" : 0.985, \"description\" : \"sofa\" } ] }, { \"position\" : { \"y\" : 276.143, \"h\" : 414.45, \"w\" : 929.017, \"x\" : 265.292 }, \"label\" : [ { \"score\" : 0.586, \"description\" : \"sofa\" } ] }, { \"position\" : { \"y\" : 500.941, \"h\" : 215.824, \"w\" : 502.355, \"x\" : 15.329 }, \"label\" : [ { \"score\" : 0.352, \"description\" : \"sofa\" } ] }, { \"position\" : { \"y\" : 350.47, \"h\" : 217.967, \"w\" : 224.915, \"x\" : 0.0 }, \"label\" : [ { \"score\" : 0.89, \"description\" : \"table\" } ] }, { \"position\" : { \"y\" : 187.452, \"h\" : 96.154, \"w\" : 149.679, \"x\" : 593.228 }, \"label\" : [ { \"score\" : 0.514, \"description\" : \"table\" } ] }, { \"position\" : { \"y\" : 259.046, \"h\" : 108.452, \"w\" : 282.464, \"x\" : 84.399 }, \"label\" : [ { \"score\" : 0.472, \"description\" : \"table\" } ] }, { \"position\" : { \"y\" : 468.553, \"h\" : 250.447, \"w\" : 548.404, \"x\" : 470.293 }, \"label\" : [ { \"score\" : 0.325, \"description\" : \"table\" } ] } ] } ] }";
        ConvertObj visualPojo2 = mapper.readValue(jsonStr2, ConvertObj.class);*/
    }

    @Test
    public void test8()throws Exception {
        /*for (int n = 0; n <; n++) {
            for (int l = 0; l <; l++) {
                for (int k = 0; k < visualPojo.getVisual_results().get(n).getPerson().get(l).getRelated_object().size(); k++) {
                    String relObj = visualPojo.getVisual_results().get(n).getPerson().get(l).getRelated_object().get(k).getObject_name();
                    String relX = visualPojo.getVisual_results().get(n).getPerson().get(l).getRelated_object().get(k).getObject_rect().getMin_x();
                    String relY = visualPojo.getVisual_results().get(n).getPerson().get(l).getRelated_object().get(k).getObject_rect().getMin_y();
                    String relW = visualPojo.getVisual_results().get(n).getPerson().get(l).getRelated_object().get(k).getObject_rect().getMax_x();
                    String relH = visualPojo.getVisual_results().get(n).getPerson().get(l).getRelated_object().get(k).getObject_rect().getMax_y();

                    if (relObj.equals(visualPojo.getVisual_results().get(n).getObject().get(l).getObject_name())
                            && relX.equals(visualPojo.getVisual_results().get(n).getObject().get(l).getObject_rect().getMin_x())
                            && relY.equals(visualPojo.getVisual_results().get(n).getObject().get(l).getObject_rect().getMin_y())
                            && relW.equals(visualPojo.getVisual_results().get(n).getObject().get(l).getObject_rect().getMax_x())
                            && relH.equals(visualPojo.getVisual_results().get(n).getObject().get(l).getObject_rect().getMax_y())
                            ) {
                        label.setPredIcate(visualPojo.getVisual_results().get(i).getPerson().get(j).getRelated_object().get(k).getPredIcate());
                    }
                }
            }
        }*/
    }

}
