package com.konantech.spring.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import com.konantech.spring.domain.vtt.SectionVo;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;


public class FileReadTest {

    @Test
    public void FileReadTest() throws IOException {
        //최종 완성될 JSONObject 선언(전체)
        JSONObject jsonObject = new JSONObject();
        String folderPath = "proxyshot\\demo\\proxyshot\\2018\\04\\19\\608\\";

        //정보 입력
        /*
        //person의 JSON정보를 담을 Array 선언
        JSONArray personArray = new JSONArray();

        //person의 한명 정보가 들어갈 JSONObject 선언
        JSONObject personInfo = new JSONObject();
        personInfo.put("face", "Rachal");
        personInfo.put("age", "25");
        personInfo.put("gender", "남자");
        personInfo.put("nickname", "남궁민수");
        //Array에 입력
        personArray.add(personInfo);

        personInfo = new JSONObject();
        personInfo.put("name", "전지현");
        personInfo.put("age", "21");
        personInfo.put("gender", "여자");
        personInfo.put("nickname", "예니콜");
        personArray.add(personInfo);

        //전체의 JSONObject에 사람이란 name으로 JSON의 정보로 구성된 Array의 value를 입력
        jsonObject.put("persons", personArray);
        */

        JSONArray sectionInfoArray = new JSONArray();

        //sectionInfoArray 정보가 들어갈 JSONObject 선언
        JSONObject sectionInfo = new JSONObject();

        for (int i = 0; i <5 ; i++) {
            sectionInfo = new JSONObject();
            String sectionTime = "00:00~ 00:" + i + "0";
            String sectionId = "S0000"+i;
            sectionInfo.put("sectionIndex", i);
            sectionInfo.put("sectionId",sectionId );
            sectionInfo.put("section_time", sectionTime);
            sectionInfo.put("folderPath", folderPath+sectionId);
            sectionInfoArray.add(sectionInfo);
        }
        jsonObject.put("section_list", sectionInfoArray);

        //JSONObject를 String 객체에 할당
        String jsonInfo = jsonObject.toJSONString();

        System.out.print(jsonInfo);

        ObjectMapper mapper = new ObjectMapper();
        MapType type = mapper.getTypeFactory().constructMapType(
                Map.class, String.class, Object.class);
        Map<String, Object> data = mapper.readValue(jsonInfo, type);

        String systemPath = "Z:\\";

        String strImgExt = "jpg|jpeg|png|gif|bmp"; //허용할 이미지타입

        JSONArray getList=(JSONArray)jsonObject.get("section_list");
        System.out.println("getList :"+( ((JSONObject)getList.get(0)).get("folderPath")) );

        for (int i = 0; i < getList.size(); i++) {
            System.out.println("folderPathIndex :" + (((JSONObject) getList.get(i)).get("folderPath")));
            folderPath = systemPath + ((JSONObject) getList.get(i)).get("folderPath").toString();

            File dirFile = new File(folderPath);
            File[] fileList = dirFile.listFiles();

            if (fileList != null) {
                for (File tempFile : fileList) {
                    if (tempFile.isFile()) {
                        String tempPath = tempFile.getParent();
                        String ext = tempFile.getName().substring(tempFile.getName().lastIndexOf(".") + 1);
                        if (strImgExt.contains(ext.toLowerCase())) {
                            System.out.println("문자열 있음!");
                        } else {
                            System.out.println("문자열 없음!");
                        }

                        System.out.println("Path :" + tempPath);
                        System.out.println("FileName :" + tempFile);
                        /*** Do something withd tempPath and temp FileName ^^; ***/
                    }
                }
            }
        }


    }

    @Test
    public void FileReadTest2() throws IOException {
        String systemPath = "Z:\\proxyshot";
        String imgFilePath = "Z:\\proxyshot\\2018\\04\\30\\624_1\\S00000\\";
        String strImgExt = "jpg|jpeg|png|gif|bmp"; //허용할 이미지타입

        ArrayList<SectionVo> sectionShotList = new ArrayList<>();
        SectionVo sectionVo;


        File dirFile = new File(imgFilePath);
        File[] fileList = dirFile.listFiles();

        if (fileList != null) {
            for (File tempFile : fileList) {
                if (tempFile.isFile()) {
                    String tempPath = tempFile.getParent();
                    String ext = tempFile.getName().substring(tempFile.getName().lastIndexOf(".") + 1);
                    int Idx = tempFile.getName().lastIndexOf(".");
                    String fileName = tempFile.getName().substring(0, Idx);

                    if (strImgExt.contains(ext.toLowerCase())) {
                        System.out.println("이미지 있음!");
                        System.out.println("Path :" + imgFilePath);
                        System.out.println("fileName :" + fileName);
                        sectionVo = new SectionVo();
                        sectionVo.setSectionId(tempFile.getName());
                        sectionVo.setSectionPath(imgFilePath);
                        sectionShotList.add(sectionVo);
                    } else {
                        System.out.println("이미지 없음!");
                        System.out.println("FileName :" + tempFile);
                    }
                }
            }
        }
    }
}
