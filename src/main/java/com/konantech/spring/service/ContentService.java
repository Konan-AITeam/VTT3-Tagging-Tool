package com.konantech.spring.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.konantech.spring.controller.web.ContentController;
import com.konantech.spring.domain.content.ContentField;
import com.konantech.spring.domain.content.ContentQuery;
import com.konantech.spring.domain.content.VideoFile;
import com.konantech.spring.domain.response.ItemResponse;
import com.konantech.spring.domain.storyboard.ShotTB;
import com.konantech.spring.domain.workflow.WorkflowRequest;
import com.konantech.spring.exception.NotFoundException;
import com.konantech.spring.mapper.ContentMapper;
import com.konantech.spring.mapper.CustomQueryMapper;
import com.konantech.spring.mapper.StoryboardMapper;
import com.konantech.spring.mapper.WorkflowMapper;
import com.konantech.spring.util.FFmpegUtil;
import com.konantech.spring.util.RequestUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ContentService {
    public static final Integer FRAMES_PER_SECOND = 3;// 3(기존)-> 0 (풀프레임)

    @Autowired
    private ContentMapper contentMapper;

    @Autowired
    private CustomQueryMapper customQueryMapper;

    @Autowired
    private WorkflowService workflowService;

    @Autowired
    WorkflowMapper workflowMapper;

    @Autowired
    private StoryboardMapper storyboardMapper;

    @Autowired
    private StoryboardService storyboardService;

    @Autowired
    private VisualService visualService;

    @Autowired
    private RestService restService;

    @Autowired
    private FFmpegUtil fFmpegUtil;

    @Value("${cmd.ffprobe}")
    private String ffprobeCmd;

    @Value("${darc.videoFolder}")
    public String videoFolder;

    @Value("${darc.volumewin}")
    public String volumewin;

    @Value("${darc.volumeetc}")
    public String volumeetc;

    @Value("${darc.shotServerUrl}")
    public String shotServerUrl;

    @Value("${darc.proxyShotFolder}")
    public String proxyShotFolder;

    private static Logger log = LoggerFactory.getLogger(ContentController.class);

    public int getContentCount(ContentQuery param) {
        return contentMapper.getContentCount(param);
    }

    public List<Map<String, Object>> getContentList(ContentQuery n) {
        return contentMapper.getContentList(n);
    }

    public List<Map<String, Object>> getAutoTaggingContentList(Map<String, Object> param) {
        return contentMapper.getAutoTaggingContentList(param);
    }

    public ContentField getContentItem(ContentQuery n) throws Exception {

        ContentField item = contentMapper.getContentItem(n);
        String mediainfo = item.getMediainfo();
        if (mediainfo != null) {
            ObjectMapper mapper = new ObjectMapper();
            HashMap<String, Object> map = mapper.readValue(mediainfo, new HashMap<String, Object>().getClass());
            JSONArray jsonArray = JSONArray.fromObject(map.get("streams"));
            if (jsonArray != null && jsonArray.size() > 0) {
                for (int pos = 0; pos < jsonArray.size(); pos++) {
                    JSONObject o1 = (JSONObject) jsonArray.get(pos);
                    int width = MapUtils.getIntValue(o1, "width");
                    int height = MapUtils.getIntValue(o1, "height");
                    if (width > 0) {
                        JSONObject tags = (JSONObject) o1.get("tags");
                        String ratate = null;
                        if (tags != null) {
                            ratate = MapUtils.getString(tags, "rotate");
                        }
                        if (ratate != null && Integer.parseInt(ratate) == 90) {
                            item.setWidth(height);
                            item.setHeight(width);
                        } else {
                            item.setWidth(width);
                            item.setHeight(height);
                        }
                        break;
                    }
                }
            }
        }
        return item;
    }

    public int putContentItem(Map<String, Object> request) {
        return contentMapper.putContentItem(request);
    }

    public int updateContentItem(Map<String, Object> request) {
        return contentMapper.updateContentItem(request);
    }


    public ItemResponse<ContentField> upload(VideoFile videoFile, HttpSession httpSession) throws Exception {
        File uFile = multipartFile(videoFile);
        ContentQuery query = new ContentQuery();
        ItemResponse<ContentField> itemResponse = new ItemResponse<>();

        /* 업로드 마지막일때 */
        if (videoFile.getChunks() == 0 || videoFile.getChunks() == videoFile.getChunk() + 1) {


            SimpleDateFormat yyyy = new SimpleDateFormat("yyyy");
            SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyy/MM/dd");

            String title = videoFile.getTitle();
            String content = videoFile.getContent();
            MultipartFile file = videoFile.getFile();

            Map update = new LinkedHashMap<String, Object>();

            Map req = new LinkedHashMap<String, Object>();
            req.put("title", title);
            req.put("content", content);
            this.putContentItem(req);
            int idx = MapUtils.getIntValue(req, "idx");
            String objectid = String.format("OV%s%08d", yyyy.format(new Date()), idx);

            try {
                if (file != null) {
                    String ext = FilenameUtils.getExtension(file.getOriginalFilename());
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
                    FileUtils.moveFile(uFile, descFile);

                    if (StringUtils.isEmpty(videoFile.getOrifilename())) {
                        videoFile.setOrifilename(file.getOriginalFilename());
                    }
                    String mediainfo = fFmpegUtil.getMediaInfo(filepath);
                    update.put("idx", idx);
                    update.put("objectid", objectid);
                    update.put("assetfilepath", assetfilepath);
                    update.put("assetfilename", assetfilename);
                    update.put("assetfilesize", FileUtils.sizeOf(new File(filepath)));
                    update.put("orifilename", videoFile.getOrifilename());
                    update.put("mediainfo", mediainfo);

                } else {
                    update.put("idx", idx);
                    update.put("objectid", objectid);
                    update.put("assetfilepath", videoFile.getFilepath());
                    update.put("genrepath", "/");
                    update.put("orifilename", videoFile.getOrifilename());

                }

                update.put("volumewin", volumewin);
                update.put("volumeetc", volumeetc);
                if("OGQ".equals(req.get("content"))){
                    update.put("display", false);
                }

                this.updateContentItem(update);

                //업로드 완료된 파일 카탈로깅 등록 OGQ
                if("OGQ".equals(req.get("content"))){

                    Map param = new HashedMap();
                    param.put("status", 1);
                    param.put("progress", 30);
                    param.put("startFlag", "Y");
                    param.put("error_msg", "error msg!!");

                    this.queryResetTemp1(param, "catalog", idx, httpSession);
                }

            } catch (IOException e) {
                throw new Exception(e.getMessage(), e);
            }

            /*workflowService.createWorkflowHis(httpSession,"ca","VTTM_video_tb",idx,0);*/
            query.setIdx(idx);
            itemResponse.setItem(this.getContentItem(query));
        }
        return itemResponse;
    }

    public void deleteContent(HttpServletRequest req, String ids) throws Exception {

        String[] idx_split = StringUtils.split(ids, "|");
        int idx;
        for (String s : idx_split) {
            idx = Integer.parseInt(s);
            contentMapper.deleteMetaInfo(idx);
            contentMapper.deleteSectionInfo(idx);
            contentMapper.deleteRepImg(idx);
            contentMapper.deleteContentItem(idx);
        }
    }

    public void retry(HttpServletRequest req, String cname, String ids) throws Exception {

        String[] idx_split = StringUtils.split(ids, "|");
        if (StringUtils.equals(cname, "catalog") || StringUtils.equals(cname, "transcoding")) {
            int idx;
            for (String s : idx_split) {
                idx = Integer.parseInt(s);
                queryResetTemp1(req, cname, idx);
            }

        } else {
            throw new Exception("catalog 또는 transcoding 이 아닙니다");
        }
    }


    /* 임시 */
    public void queryResetTemp1(HttpServletRequest req, String cname, int idx) throws Exception {

        String error_msg = RequestUtils.getParameter(req, "error_msg");
        int progress = RequestUtils.getParameterInt(req, "progress");
        int statusParam = RequestUtils.getParameterInt(req, "status");
        String startFlag = RequestUtils.getParameter(req, "startFlag");

        Map param = new HashedMap();
        param.put("error_msg", error_msg);
        param.put("progress", progress);
        param.put("status", statusParam);
        param.put("startFlag", startFlag);

        this.queryResetTemp1(param, cname, idx, req.getSession());

    }

    public void queryResetTemp1(Map<String, Object> paramMap, String cname, int idx, HttpSession httpSession) throws Exception {

        if (StringUtils.equals(cname, "catalog")) {

            String error_msg =  MapUtils.getString(paramMap, "error_msg");
            int progress = MapUtils.getIntValue(paramMap, "progress");
            int statusParam = MapUtils.getIntValue(paramMap, "status");
            String startFlag = MapUtils.getString(paramMap, "startFlag");

            String[] statusList = {"wait", "progress", "done", "fail"};
            String status = statusList[statusParam];

            ContentQuery param = new ContentQuery();
            param.setIdx(idx);
            ContentField field = contentMapper.getContentItem(param);

            // make json
            if (startFlag.equals("Y")) {
                String makerjsonFile = videoFolder + File.separator + field.getAssetfilepath() + File.separator + field.getAssetfilename();
                makerjsonFile = makerjsonFile.replace(".mp4", ".json");
                String tempPath = videoFolder + File.separator + field.getAssetfilepath();
                log.debug("tempPath ==> " + tempPath);

                File dir = new File(tempPath);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                log.debug("pass dir");


                JSONObject json = new JSONObject();

                File newJsonFile = new File(makerjsonFile);
                FileOutputStream fos = new FileOutputStream(newJsonFile);
                HashMap jsonMap = new HashMap();
                json.put("videoid", idx);
                json.put("fps", FRAMES_PER_SECOND);
                List<Map> newJsonDataList = new ArrayList<>();
                json.put("scene", newJsonDataList);


                log.debug("json ==> " + json.toString());
                fos.write(json.toString().getBytes());
                fos.flush();
                fos.close();
                log.debug("json success==> ");
            }


            if (progress == 100 && statusParam == 2) {
                // json read
                String jsonFile = videoFolder + File.separator + field.getAssetfilepath() + File.separator + field.getAssetfilename();
                jsonFile = jsonFile.replace(".mp4", "_result.json");
                log.debug("result jsonFile ==> " + jsonFile);

                FileInputStream fileStream = null;
                File file = null;
                int i = 0;
                StringBuffer sb = new StringBuffer();
                try {
                    file = new File(jsonFile);
                    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));

                    fileStream = new FileInputStream(jsonFile);
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line);
                    }
                    br.close();

                    String jsonStr = sb.toString();
                    jsonStr = jsonStr.replace("?", "");
                    JSONObject jsonData = JSONObject.fromObject(jsonStr);

                    log.debug("jsonStr ==> [" + jsonStr + "]");
                    log.debug("jsonData ==> " + jsonData.toString());

                    int videoid = jsonData.getInt("videoid");
                    int fps = jsonData.getInt("fps");
                    int step = jsonData.getInt("step");
                    JSONArray jsonScene = jsonData.getJSONArray("scene");

                    log.debug("jsonScene ==> " + jsonScene.toString());

                    for (int j = 0; j < jsonScene.size(); j++) {
                        JSONObject jo1 = jsonScene.getJSONObject(j);

                        int sceneid = jo1.getInt("sceneid");
                        int scene_startframeindex = jo1.getInt("startframeindex");
                        String scene_starttimecode = jo1.getString("starttimecode");
                        int scene_endframeindex = jo1.getInt("endframeindex");
                        String scene_endtimecode = jo1.getString("endtimecode");

                        log.debug("j ==> " + j);
                        log.debug("jo1 ==> " + jo1);
                        log.debug("sceneid ==> " + sceneid);
                        log.debug("scene_startframeindex ==> " + scene_startframeindex);
                        log.debug("scene_starttimecode ==> " + scene_starttimecode);
                        log.debug("scene_endframeindex ==> " + scene_endframeindex);
                        log.debug("scene_endtimecode ==> " + scene_endtimecode);

                        JSONArray jsonShot = jo1.getJSONArray("shot");
                        log.debug("jsonShot ==> " + jsonShot.toString());

                        for (int k = 0; k < jsonShot.size(); k++) {
                            JSONObject jo2 = jsonShot.getJSONObject(k);

                            String sequencetype = jo2.getString("sequencetype");
                            String filepath = jo2.getString("filepath");
                            int shot_startframeindex = jo2.getInt("startframeindex");
                            int shot_endframeindex = jo2.getInt("endframeindex");
                            String shot_starttimecode = jo2.getString("starttimecode");
                            String shot_endtimecode = jo2.getString("endtimecode");

                            log.debug("k ==> " + k);
                            log.debug("k filepath ==> " + filepath);
                            log.debug("k filepath.replace ==> " + filepath.replace("/mnt/disk02/darc4data/proxyshot/", ""));
                            log.debug("k FilenameUtils filepath ==> " + FilenameUtils.getPath(filepath));

                            ShotTB shotTB = new ShotTB();
                            shotTB.setVideoid(idx);
                            shotTB.setContent(filepath);
                            shotTB.setStarttimecode(shot_starttimecode);
                            shotTB.setStartframeindex(shot_startframeindex);
                            shotTB.setEndtimecode(shot_endtimecode);
                            shotTB.setEndframeindex(shot_endframeindex);
                            shotTB.setAssetfilepath(FilenameUtils.getPath(filepath.replace("/mnt/disk02/darc4data/proxyshot/", "")));
                            shotTB.setAssetfilename(FilenameUtils.getName(filepath));
                            shotTB.setStep(step);

                            log.debug("shotTB ==> " + shotTB.toString());

                            //Object object = shotinfo.get("object");
                            storyboardService.putShotItem(shotTB);
                        }

                    }

                    if("OGQ".equals(field.getContent())){
                        this.ogqAutoTagging(idx);
                    }

                    /* TODO:업데이트 로직이 코드값과 맞지 않는 부분 수정 필요, 시간 표기 수정 필요 */
                    LinkedHashMap<String, Object> param1 = new LinkedHashMap<>();
                    param1.put("idx", idx);
                    param1.put("catalogstatus", "3000");
                    contentMapper.updateContentItem(param1);
                } catch (Exception e) {
                    LinkedHashMap<String, Object> param1 = new LinkedHashMap<>();
                    param1.put("idx", idx);
                    param1.put("catalogstatus", "4000");
                    contentMapper.updateContentItem(param1);
                    throw new Exception("파일 입출력 에러!!");
                } finally {
                    try {
                        fileStream.close();
                    } catch (Exception e) {
                        throw new Exception("파일 스트림 닫기 실패!!");
                    }
                }

            }

            // 임시1
            WorkflowRequest request = new WorkflowRequest();
            request.setWorkflowname("ca");
            request.setAssetname("VTTM_video_tb");
            request.setAssetid(idx);
            request.setSubtype(0);
            List<Integer> workflowIds = workflowService.createWorkflowHis(
                    httpSession,
                    request.getWorkflowname(),
                    request.getAssetname(),
                    request.getAssetid(),
                    request.getSubtype()
            );

            //catalogQue 등록
            workflowService.putCatalogQue(idx, status, error_msg, progress);

        }
    }

    private File multipartFile(VideoFile uploadFile) throws Exception {
        SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyy/MM/dd");
        String baseFolder = FilenameUtils.normalize(videoFolder + File.separator + yyyyMMdd.format(new Date()) + File.separator + uploadFile.getUuid());
        File base = new File(baseFolder);
        File uParent = base.getParentFile();

        log.debug("baseFolder ==> " + baseFolder);

        if (!uParent.exists()) {
            if (!uParent.mkdirs()) {
                throw new NotFoundException("Permission denied, ( " + uParent.getAbsolutePath() + " )");
            }
        }

        MultipartFile multipartFile = uploadFile.getFile();
        if (multipartFile != null && !multipartFile.isEmpty()) {
            long fileSize = multipartFile.getSize();
            if (fileSize > 0) {
                InputStream inputStream = null;
                OutputStream outputStream = null;
                try {
                    inputStream = multipartFile.getInputStream();
                    outputStream = new FileOutputStream(base, (uploadFile.getChunk() == 0) ? false : true);
                    byte[] buffer = new byte[8192];
                    int readBytes;
                    while ((readBytes = inputStream.read(buffer, 0, 8192)) != -1) {
                        outputStream.write(buffer, 0, readBytes);
                    }
                } catch (Exception ex) {
                    throw ex;
                } finally {
                    if (outputStream != null) {
                        outputStream.close();
                    }
                    if (inputStream != null) {
                        inputStream.close();
                    }
                }
            }
        }

        return base;
    }

    public List<Map<String, Object>> getRepImgFile(int idx) {
        List<Map<String, Object>> list = contentMapper.getRepImgPath(idx);

        list.forEach(m -> {
            String path = MapUtils.getString(m, "img_url");
            m.put("path", proxyShotFolder + "/" + path);
        });

        return list;
    }

    public List<Map<String, Object>> getFileUserList(Map<String, Object> param) {
        return contentMapper.getFileUserList(param);
    }

    public int checkActiveRun(String idx){
        return contentMapper.checkActiveRun(idx);
    }

    public int updateActiveRun(String idx){
        return contentMapper.updateActiveRun(idx);
    }

    private void ogqAutoTagging(int idx) throws Exception{

        int taggingCnt = 0;
        int errorCnt = 0;
        StringBuffer errorMsg = new StringBuffer();

        ContentQuery query1 = new ContentQuery();
        query1.setIdx(idx);
        List<ShotTB> shotList = storyboardService.getShotList(query1);

        for (ShotTB shotData : shotList) {
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

    public String getResultJson(int idx) {

        ContentQuery param = new ContentQuery();
        param.setIdx(idx);
        ContentField field = contentMapper.getContentItem(param);

        String jsonPath = videoFolder + File.separator + field.getAssetfilepath() + File.separator + field.getAssetfilename();
        jsonPath = jsonPath.replace(".mp4", "_result.json");

        return jsonPath;
    }

    public List<Map<String, Object>> getFrameImgTb(int idx){
        return contentMapper.getFrameImgTb(idx);
    }
}
