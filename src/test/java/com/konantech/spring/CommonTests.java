package com.konantech.spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.konantech.spring.domain.content.ContentQuery;
import com.konantech.spring.domain.storyboard.ShotTB;
import com.konantech.spring.domain.visual.Visual;
import com.konantech.spring.exception.NotFoundException;
import com.konantech.spring.mapper.ContentMapper;
import com.konantech.spring.mapper.StoryboardMapper;
import com.konantech.spring.mapper.VisualMapper;
import com.konantech.spring.service.VisualService;
import com.konantech.spring.service.WorkflowService;
import com.konantech.spring.util.FFmpegUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommonTests {

    private Logger log = LoggerFactory.getLogger(this.getClass());
    public MockHttpServletRequest req = new MockHttpServletRequest();

    @Autowired
    private VisualMapper visualMapper;

    @Autowired
    private VisualService visualService; //(신규) VTTM(메타정보 관리) 관련 서비스
    @Autowired
    private ContentMapper contentMapper;
    @Autowired
    StoryboardMapper storyboardMapper;


    @Autowired
    private WorkflowService workflowService;

    @Autowired
    private FFmpegUtil fFmpegUtil;

    @Value("${darc.videoFolder}")
    public String videoFolder;

    @Value("${darc.volumewin}")
    public String volumewin;

    @Value("${darc.volumeetc}")
    public String volumeetc;
    @Test
    public void putFileData() throws Exception{

        SimpleDateFormat yyyy = new SimpleDateFormat("yyyy");
        SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyy/MM/dd");
        Map update = new LinkedHashMap<String, Object>();
        String base = "/Volumes/konan/OGQ/cc0 license videos";

        File root = new File(base);
        if(root.isDirectory()){
            File[] files = root.listFiles();
            for(File file : files){
                String ext = FilenameUtils.getExtension(file.getName());
                Map req = new LinkedHashMap<String, Object>();
                req.put("title", file.getName());
                req.put("content", file.getName());
                contentMapper.putContentItem(req);
                int idx = MapUtils.getIntValue(req,"idx");
                String objectid = String.format("OV%s%08d", yyyy.format(new Date()), idx);

                String assetfilepath = yyyyMMdd.format(new Date()) + "/" + idx;
                String assetfilename = objectid + "." + ext;
                String filepath = FilenameUtils.normalize(videoFolder + "/" + assetfilepath + "/" + assetfilename);
                File descFile = new File(filepath);

                if (descFile.isDirectory()) {
                    throw new NotFoundException("파일명을 확인 하세요 ( " + descFile.getAbsolutePath() + " )");
                }
                if (descFile.exists()) {
                    throw new NotFoundException("대상 파일이 존재 합니다 ( " + descFile.getAbsolutePath() + " )");
                }
                File dir = descFile.getParentFile();
                if (!dir.exists()) {
                    if (!dir.mkdirs()) {
                        throw new NotFoundException("Permission denied, ( " + dir.getAbsolutePath() + " )");
                    }
                }
                /* file move */
                FileUtils.copyFile(file, descFile);

                String mediainfo = fFmpegUtil.getMediaInfo(filepath);
                update.put("idx", idx);
                update.put("objectid", objectid);
                update.put("assetfilepath", assetfilepath);
                update.put("assetfilename", assetfilename);
                update.put("assetfilesize", FileUtils.sizeOf(new File(filepath)));
                update.put("orifilename", file.getName());
                update.put("mediainfo", mediainfo);

                update.put("volumewin", volumewin);
                update.put("volumeetc", volumeetc);

                contentMapper.updateContentItem(update);

                //업로드 완료된 파일 카탈로깅 등록
                workflowService.createWorkflowHis(
                        null,
                        "ca",
                        "VTTM_video_tb",
                        idx,
                        0);
            }
        }
    }

    @Test
    public void jsonPasing() throws Exception{
        String[] aFilePath = {
                "s01_ep07_tag2_visual_Final_for_merge.json"
                ,"s01_ep08_tag2_visual_Final_for_merge.json"
                ,"s01_ep09_tag2_visual_Final_for_merge.json"
                ,"s01_ep10_tag2_visual_Final_for_merge.json"
        };
        String[] aVideoId = {
                "658"
                ,"659"
                ,"660"
                ,"661"
        };
        for(int i = 0 ; i<aFilePath.length; i++) {
            String base = "/Volumes/konan/VTT/2차년도/02. 메타데이터 관련/VTT3_2차년도_메타데이터태깅/20180824_메타데이터_1차배포/VTT3_2차년도_메타데이터1차_배포_20180824_visual수정/";
            File jsonFile = new File(base+aFilePath[i]);
            ObjectMapper mapper = new ObjectMapper();
            Visual visual = mapper.readValue(jsonFile, Visual.class);
            ContentQuery vo = new ContentQuery();
            vo.setIdx(Integer.parseInt(aVideoId[i]));
            List<ShotTB> shotList = storyboardMapper.getShotList(vo);
            List<Visual.VisualResult> list = new ArrayList<>();
            for (Visual.VisualResult visualResult : visual.getVisual_results()) {
                List<Map<String, List<Visual.VisualResult.Person>>> oldPerson = visualResult.getPerson();
                List<Map<String, List<Visual.VisualResult.Person>>> newPerson = new ArrayList<>();
                Map<String, List<Visual.VisualResult.Person>> mPerson = new HashMap<>();
                newPerson.add(mPerson);
                for (String key : oldPerson.get(0).keySet()) {
                    List<Visual.VisualResult.Person> person = oldPerson.get(0).get(key);
                    String mKey = key.substring(0, 1).toUpperCase() + key.substring(1);
                    newPerson.get(0).put(mKey, person);
                }
                visualResult.setPerson(newPerson);

                if(list.size()!=0&&!list.get(0).getPeriod_num().equals(visualResult.getPeriod_num())){
                    String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(list);
                    String shotId = "";
                    for(ShotTB shot : shotList){
                        if(shot.getAssetfilename().startsWith(list.get(0).getPeriod_num())){
                            shotId=shot.getShotid()+"";
                            break;
                        }
                    }

                    Map<String, String> paramMap = new HashMap<>();
                    paramMap.put("idx", aVideoId[i]);
                    paramMap.put("shotId", shotId);
                    paramMap.put("userId", "konan");
                    paramMap.put("vttMetaJson", json);
                    visualService.getPutMetaInfo(paramMap);
                    list.clear();
                }else if(visual.getVisual_results().indexOf(visualResult)+1==visual.getVisual_results().size()){
                    visualResult.setPeriod_frame_num((list.size()+1)+"");
                    list.add(visualResult);

                    String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(list);
                    String shotId = "";
                    for(ShotTB shot : shotList){
                        if(shot.getAssetfilename().startsWith(list.get(0).getPeriod_num())){
                            shotId=shot.getShotid()+"";
                            break;
                        }
                    }

                    HashMap<String, String> paramMap = new HashMap<>();
                    paramMap.put("idx", aVideoId[i]);
                    paramMap.put("shotId", shotId);
                    paramMap.put("userId", "konan");
                    paramMap.put("vttMetaJson", json);
                        visualService.getPutMetaInfo(paramMap);

                }
                visualResult.setPeriod_frame_num((list.size()+1)+"");
                list.add(visualResult);

            }
            contentMapper.deleteRepImg(Integer.parseInt(aVideoId[i]));
            contentMapper.insertRepImg(Integer.parseInt(aVideoId[i]));
        }

    }


    @Test
    public void getImgQaInfo() throws Exception {
        String imgPath = "/Volumes/konan/darc4data/proxyshot/2018/09/05/658/S00001/F0000110.jpg";
        RestTemplate restTemplate = new RestTemplate();

        String url ="http://10.10.18.193:3333/cap";

        HttpHeaders headers = new HttpHeaders();

        //헤더 내임, 값 셋팅.
        String headerName = "";
        String headerValue = "";
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        headers.add(headerName, headerValue);

        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();

        Resource resource = new FileSystemResource(imgPath);
        parameters.add("Content-Type", "multipart/form-data");
        parameters.add("image", resource);
        parameters.add("modules", "friends");

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(parameters, headers);

        ResponseEntity<LinkedHashMap> result = restTemplate.exchange(url, HttpMethod.POST, requestEntity, LinkedHashMap.class);

        System.out.println("result :" +result.getBody());
    }
}

