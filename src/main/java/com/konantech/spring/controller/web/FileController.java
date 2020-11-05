package com.konantech.spring.controller.web;

import com.konantech.spring.service.*;
import com.konantech.spring.util.JSONUtils;
import com.konantech.spring.util.RequestUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class FileController {

    @Autowired
    VisualService visualService;

    @Autowired
    SectionService sectionService;

    @Autowired
    ContentService contentService;

    @Autowired
    SoundService soundService;

    @Autowired
    SubtitleService subtitleService;

    @Autowired
    FileService fileService;

    @Autowired
    DownloadService downloadService;

    @RequestMapping(value = "/xmlFileDown", method = RequestMethod.GET)
    public void xmlFileDown(@RequestParam(value = "fileId", defaultValue = "file.xml") String fileId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");

        //파일 업로드된 경로
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // 루트 엘리먼트
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("company");
            doc.appendChild(rootElement);

            // staff 엘리먼트
            Element staff = doc.createElement("Staff");
            rootElement.appendChild(staff);

            // 속성값 정의
            Attr attr = doc.createAttribute("id");
            attr.setValue("1");
            staff.setAttributeNode(attr);

            // 속성값을 정의하는 더 쉬운 방법
            // staff.setAttribute("id", "1");

            // firstname 엘리먼트
            Element firstname = doc.createElement("firstname");
            firstname.appendChild(doc.createTextNode("Gildong"));
            staff.appendChild(firstname);

            // lastname 엘리먼트
            Element lastname = doc.createElement("lastname");
            lastname.appendChild(doc.createTextNode("Hong"));
            staff.appendChild(lastname);

            // nickname 엘리먼트
            Element nickname = doc.createElement("nickname");
            nickname.appendChild(doc.createTextNode("Mr.Hong"));
            staff.appendChild(nickname);

            // salary 엘리먼트
            Element salary = doc.createElement("salary");
            salary.appendChild(doc.createTextNode("100000"));
            staff.appendChild(salary);

            // XML 파일로 쓰기
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF - 8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);

            String fileName = "file.xml";
            // 파일 객체 생성

            //실제 내보낼 파일명
            String oriFileName = "file.xml";


            File file = new File(fileName);
            // true 지정시 파일의 기존 내용에 이어서 작성
            FileWriter fw = new FileWriter(file, true);

            // 파일안에 문자열 쓰기
            //fw.write(txt);
            fw.flush();

            // 객체 닫기
            fw.close();


            //실제 내보낼 파일명
            InputStream in = null;
            OutputStream os = null;
            boolean skip = false;
            String client = "";

            //파일을 읽어 스트림에 담기
            try {
                in = new FileInputStream(file);
            } catch (FileNotFoundException fe) {
                skip = true;
            }

            client = request.getHeader("User-Agent");

            //파일 다운로드 헤더 지정
            response.reset();
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Description", "JSP Generated Data");

            if (!skip) {
                // IE
                if (client.indexOf("MSIE") != -1) {
                    response.setHeader("Content-Disposition", "attachment; filename=\""
                            + java.net.URLEncoder.encode(oriFileName, "UTF-8").replaceAll("\\+", "\\ ") + "\"");
                    // IE 11 이상.
                } else if (client.indexOf("Trident") != -1) {
                    response.setHeader("Content-Disposition", "attachment; filename=\""
                            + java.net.URLEncoder.encode(oriFileName, "UTF-8").replaceAll("\\+", "\\ ") + "\"");
                } else {
                    // 한글 파일명 처리
                    response.setHeader("Content-Disposition",
                            "attachment; filename=\"" + new String(oriFileName.getBytes("UTF-8"), "ISO8859_1") + "\"");
                    response.setHeader("Content-Type", "application/octet-stream; charset=utf-8");
                }
                response.setHeader("Content-Length", "" + file.length());
                os = response.getOutputStream();
                byte b[] = new byte[(int) file.length()];
                int leng = 0;
                while ((leng = in.read(b)) > 0) {
                    os.write(b, 0, leng);
                }
            } else {
                response.setContentType("text/html;charset=UTF-8");
                System.out.println("<script language='javascript'>alert('파일을 찾을 수 없습니다');history.back();</script>");
            }
            in.close();
            os.close();
        } catch (Exception e) {
            System.out.println("ERROR : " + e.getMessage());
        }
    }

    /*
    @ResponseBody
    @RequestMapping(value = "/jsonFileDown", method = RequestMethod.GET)
    public void jsonFileDown(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map paramMap = RequestUtils.getParameterMap(request);
        String visualJson = visualService.getJsonData(paramMap);
        String qaJson = sectionService.getJsonData(paramMap);
        String soundJson = soundService.getJsonData(paramMap);
        List<Map> fileList = new ArrayList<>();
        fileList.add(fileService.createJsonFileStream(paramMap,"visual",visualJson));
        fileList.add(fileService.createJsonFileStream(paramMap,"qa",qaJson));
        fileList.add(fileService.createJsonFileStream(paramMap,"sound",soundJson));

        fileService.downloadZip(response,"json", fileList);
    }
    */

    @ResponseBody
    @RequestMapping(value = "/downloadJsonFile", method = RequestMethod.GET)
    public void downloadJsonFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map param = RequestUtils.getParameterMap(request);
        String chkDown = (String) param.get("chkDown");
        param.put("include_shot_id", false);

        List<Map> userList = contentService.getFileUserList(param);
        List<Map> fileList = new ArrayList<>();

        userList.forEach(userData -> {
            param.replace("userid", userData.get("user_id"));
            try {
                if (userData.get("info_type").equals("visual") && chkDown.contains("visual")) {
                    fileList.add(fileService.createJsonFileStream(param, "visual", visualService.getNewJsonData(param)));
                } else if (userData.get("info_type").equals("sound") && chkDown.contains("sound")) {
                    fileList.add(fileService.createJsonFileStream(param, "sound", soundService.getJsonData(param)));
                } else if (userData.get("info_type").equals("section") && chkDown.contains("section")) {
                    fileList.add(fileService.createJsonFileStream(param, "qa", sectionService.getJsonDataQa(param)));
                } else if (userData.get("info_type").equals("subtitle") && chkDown.contains("subtitle")) {
                    fileList.add(fileService.createJsonFileStream(param, "subtitle", subtitleService.getJsonData(param)));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        fileList.add(fileService.createJsonFileStream(RequestUtils.getParameterMap(request), "shotinfo", visualService.getShotJsonData(RequestUtils.getParameterMap(request))));

        fileService.downloadZip(response, "json", fileList);
    }

    @ResponseBody
    @RequestMapping(value = "/downloadWorkedImgFile", method = RequestMethod.GET)
    public void downloadWorkedImgFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map param = RequestUtils.getParameterMap(request);
        param.put("include_shot_id", true);

        List<Map> userList = contentService.getFileUserList(param);
        List<Map> fileList = new ArrayList<>();

        userList.forEach(userData -> {
            String userId = (String) userData.get("user_id");
            param.replace("userid", userId);

            if (userData.get("info_type").equals("visual")) {
                Map<String, Object> properties = null;
                try {
                    properties = JSONUtils.jsonStringToMap(visualService.getNewJsonData(param));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (properties.containsKey("visual_results")) {
                    List<Object> itemList = (List<Object>) properties.get("visual_results");

                    itemList.forEach(obj -> {
                        Map reqParam = new HashMap();
                        reqParam.put("userid", param.get("userid"));
                        reqParam.put("idx", Integer.parseInt((String) param.get("idx")));
                        reqParam.put("shotid", ((Map<String, Object>) obj).get("shot_id"));

                        List<Map<String, Object>> list = null;
                        try {
                            list = downloadService.getSelectWorkedImage(reqParam);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        List<Object> imageInfoList = (List<Object>) ((Map<String, Object>) obj).get("image_info");

                        for (Object objImageInfo : imageInfoList) {
                            String[] arrayFrameId = StringUtils.split(MapUtils.getString((Map<String, Object>) objImageInfo, "frame_id"), "_");
                            String key = arrayFrameId[arrayFrameId.length - 2] + "_" + arrayFrameId[arrayFrameId.length - 1];
                            Map<String, Object> imageInfo = getImageMapFromKey(list, key);
                            if (MapUtils.isNotEmpty(imageInfo)) {
                                List<String> strings = new ArrayList<String>();

                                strings.add(arrayFrameId[0]);
                                strings.add(arrayFrameId[0] + "_" + arrayFrameId[1] + "_0000");
                                strings.add(arrayFrameId[0] + "_" + arrayFrameId[1] + "_" + arrayFrameId[2]);
                                strings.add(MapUtils.getString(imageInfo, "img_file_name") + ".jpg");
                                try {
                                    Map imageMap = fileService.createImgFileStream(MapUtils.getString(imageInfo, "path"), StringUtils.join(strings, File.separator));
                                    if (this.checkedUniqueMap(fileList, (String) imageMap.get("name"))) {
                                        fileList.add(imageMap);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                }
            }
        });

        fileService.downloadZip(response, "img", fileList);
    }

    private boolean checkedUniqueMap(List<Map> list, String key) {
        try {
            list
                    .stream()
                    .filter(map -> map != null)
                    .filter(map -> map.containsValue(key))
                    .findFirst()
                    .get();
        } catch (Exception e) {
            return true;
        }

        return false;
    }


    private Map<String, Object> getImageMapFromKey(List<Map<String, Object>> list, String key) {
        Map<String, Object> result = list
                .stream()
                .filter(map -> map != null)
                .filter(map -> StringUtils.equals(MapUtils.getString(map, "img_file_name"), key))
                .findFirst()
                .get();
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/imgFileDown", method = RequestMethod.GET)
    public void imgFileDown(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map paramMap = RequestUtils.getParameterMap(request);
        List<Map<String, Object>> list = contentService.getRepImgFile(Integer.parseInt((String) paramMap.get("idx")));
        List<Map> fileList = new ArrayList<>();

        list.forEach(map -> {
            String path = MapUtils.getString(map, "path");
            String[] arrayPath = path.split(File.separator);
            String fileName = arrayPath[arrayPath.length - 2] + File.separator + arrayPath[arrayPath.length - 1];
            try {
                fileList.add(fileService.createImgFileStream(path, fileName));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        fileService.downloadZip(response, "img", fileList);
    }

}
