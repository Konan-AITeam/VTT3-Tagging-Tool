package com.konantech.spring.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.konantech.spring.domain.content.ContentField;
import com.konantech.spring.domain.content.ContentQuery;
import com.konantech.spring.domain.storyboard.ShotTB;
import com.konantech.spring.domain.workflow.*;
import com.konantech.spring.mapper.ContentMapper;
import com.konantech.spring.mapper.StoryboardMapper;
import com.konantech.spring.mapper.WorkflowMapper;
import com.konantech.spring.util.JSONUtils;
import com.konantech.spring.util.RequestUtils;
import com.konantech.spring.util.RestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.*;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class WorkflowService {
    private static Logger log = LoggerFactory.getLogger(WorkflowService.class);

    @Autowired
    private WorkflowMapper workflowMapper;

    @Autowired
    StoryboardMapper storyboardMapper;

    @Autowired
    ContentMapper contentMapper;

    @Value("${darc.proxyShotFolder}")
    public String proxyShotFolder;

    @Value("${darc.videoFolder}")
    public String videoFolder;

    public int checkin(HttpServletRequest request, CheckInRequest checkInRequest) throws Exception {

        String clientip = checkInRequest.getClientip();
        String clienturl = checkInRequest.getClienturl();
        int port = checkInRequest.getPort();
        List<String> jobNames = checkInRequest.getJobnames();

        if(StringUtils.isEmpty(clientip)) {
            clientip = RequestUtils.getRemoteAddr(request);
            checkInRequest.setClientip(clientip);
        }

        if(StringUtils.isEmpty(clienturl)) {
            clienturl = "http://" + clientip + ":" + port;
            checkInRequest.setClienturl(clienturl);
        }

        if( workflowMapper.getCompServerCount(checkInRequest) > 0) {
            throw new Exception("콤포넌트서버가 이미 존재합니다. 체크아웃후 진행하세요");
        }
        workflowMapper.putCompServer(checkInRequest);
        int compserverid = checkInRequest.getCompserverid();
        for(String jobName : jobNames) {
            CompJobTB compJob = workflowMapper.getCompJob(jobName);
            if(compJob != null) {
                workflowMapper.putCompServerJob(compserverid, jobName);
            }
        }
        return compserverid;
    }

    public int checkout(int compserverid) throws Exception {
        CompServerTB compServer = workflowMapper.getCompServerByID(compserverid);
        if (compServer == null) {
            throw new Exception("콤포넌트서버가 없습니다");
        }
        workflowMapper.delCompServerJob(compserverid);
        workflowMapper.delCompServer(compserverid);
        return compserverid;
    }

    public List<Integer> createWorkflowHis(HttpSession httpSession, String workflowName, String assetName, int assetID, int subtype) throws IOException {

        List<Integer> result = new ArrayList<Integer>();

        WorkFlowOrderTB param = new WorkFlowOrderTB();
        param.setWorkflowname(workflowName);
        param.setSubtype(subtype);
        List<WorkFlowOrderTB> results = workflowMapper.getWorkFlowOrder(param);
        if (results != null) {

            WorkflowHisTB workflowHisTB;
            int joborder = 0;
            for(WorkFlowOrderTB workFlowOrderTB : results) {

                String jobName = workFlowOrderTB.getJobname();
                workflowHisTB = new WorkflowHisTB();
                workflowHisTB.setWorkflowname(workflowName);
                workflowHisTB.setSubtype(subtype);
                workflowHisTB.setCurrjob(jobName);
                workflowHisTB.setMaintable(assetName);
                workflowHisTB.setMainassetid(assetID);
                workflowHisTB.setReftable("");
                workflowHisTB.setRefassetid(0);

                workflowMapper.putWorkflowHis(workflowHisTB);
                int workflowid = workflowHisTB.getWorkflowid();

                // paramList
                Map<String, Object> paramListMap = getParamList(workflowid, jobName);
                String paramListString = JSONUtils.jsonStringFromObject(paramListMap);

                // paramList
                Map<String, Object> jobPropertyMap = getJobProperty(jobName, paramListMap);
                String jobContentString = JSONUtils.jsonStringFromObject(jobPropertyMap);


                int pool = workFlowOrderTB.getPool();
                int priority = workFlowOrderTB.getPriority();
                CompJobQueueTB compJobQueueTB = new CompJobQueueTB();
                compJobQueueTB.setWorkflowid(workflowid);
                compJobQueueTB.setJobname(jobName);
                compJobQueueTB.setPool(pool);
                compJobQueueTB.setPriority(priority);
                compJobQueueTB.setJoborder(joborder);
                compJobQueueTB.setParamlist(paramListString);
                compJobQueueTB.setJobcontents(jobContentString);

                workflowMapper.putCompJobQueue(compJobQueueTB);

                result.add(workflowid);
                joborder++;
            }

        }
        return result;
    }

    private Map<String, Object> getParamList(int workflowid, String jobName) throws IOException {

        WorkflowHisTB workflowHisTB = workflowMapper.selectWorkflowByID(workflowid);
        CompJobTB compJob = workflowMapper.getCompJob(jobName);
        String str = compJob.getParamProperty();

        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, Object> map = mapper.readValue(str, new HashMap<String, Object>().getClass());
        String json = mapper.writeValueAsString(map.get("paramlist"));
        List<CompJobParamProperty> list = mapper.readValue(json, new TypeReference<List<CompJobParamProperty>>() { });
        Map<String, Object> resultMap = new HashMap<>();

        for( CompJobParamProperty prop : list ) {
            if (StringUtils.equals(prop.getType(), "mainkey")) {
                resultMap.put(prop.getField(), workflowHisTB.getMainassetid());
            } else if (StringUtils.equals(prop.getType(), "refkey")) {
                resultMap.put(prop.getField(), workflowHisTB.getRefassetid());
            } else if (StringUtils.equals(prop.getType(), "direct")) {
                resultMap.put(prop.getField(), prop.getValue());
            } else if (StringUtils.equals(prop.getType(), "other")) {
                String query = prop.getOtherQuery(workflowHisTB);
                resultMap.put(prop.getField(), workflowMapper.selectCustomInt(query));
            }
        }
        return resultMap;
    }

    private Map<String, Object> getJobProperty( String jobName, Map<String, Object> paramListMap) throws IOException {

        CompJobTB compJob = workflowMapper.getCompJob(jobName);
        String str = compJob.getJobProperty();

        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, Object> map = mapper.readValue(str, new HashMap<String, Object>().getClass());
        String json = mapper.writeValueAsString(map.get("select"));
        List<CompJobJobPropertySelect> jobPropertySelect = mapper.readValue(json, new TypeReference<List<CompJobJobPropertySelect>>() { });

        json = mapper.writeValueAsString(map.get("status"));
        List<CompJobJobPropertyUpdate> jobPropertyStatus = mapper.readValue(json, new TypeReference<List<CompJobJobPropertyUpdate>>() { });

        json = mapper.writeValueAsString(map.get("start"));
        List<CompJobJobPropertyUpdate> jobPropertyStart = mapper.readValue(json, new TypeReference<List<CompJobJobPropertyUpdate>>() { });

        json = mapper.writeValueAsString(map.get("end"));
        List<CompJobJobPropertyUpdate> jobPropertyEnd = mapper.readValue(json, new TypeReference<List<CompJobJobPropertyUpdate>>() { });

        Map<String, Object> jobPropertyMap = new HashMap<>();
        List<Map<String, Object>> jobInfoList = new ArrayList<>();
        jobPropertyMap.put("jobinfos", jobInfoList);
        Map<String, Object> jobInfo = new HashMap<>();
        jobInfoList.add(jobInfo);

        Map<String, Object> resultMap = new HashMap<>();
        for( CompJobJobPropertySelect select : jobPropertySelect ) {
              jobInfo.put(select.getAlias(), workflowMapper.selectCustomObject(select.getSelectQuery(paramListMap)));
//            if(select.isSingleObject()) {
//                jobInfo.put(select.getAlias(), workflowMapper.selectCustomObject(select.getSelectQuery(paramListMap)));
//            } else {
//                jobInfo.put(select.getAlias(), workflowMapper.selectCustomObjectList(select.getSelectQuery(paramListMap)));
//            }
        }
        return jobPropertyMap;
    }

    public void updateStatusProc(CompJobQueueTB compJobQueueTB, CompServerTB compServerTB) {
        compJobQueueTB.setStatusEnum(STATUS.PROC);
        compServerTB.setStatusEnum(STATUS.PROC);

        workflowMapper.updateCompjobAllocStatus(compJobQueueTB);
        workflowMapper.updateCompserverAllocStatus(compServerTB);
    }

    // 여기부터 다시봐야함..
    public void allocJob(CompJobQueueTB compJobQueueTB, CompServerTB compServerTB) throws Exception {

        WorkflowHisTB workflowHisTB = workflowMapper.selectWorkflowByID(compJobQueueTB.getWorkflowid());
        if (workflowHisTB == null) {
            throw new Exception("실행할 워크플로우가 없습니다");
        }
        DArcWorkflowSchema workflowSchema = new DArcWorkflowSchema(workflowMapper, workflowHisTB.getWorkflowname(), workflowHisTB.getSubtype());
        DArcCompserverSchema compserverSchema = new DArcCompserverSchema(workflowMapper, compServerTB.getCompservername());

        if (callCompserver(workflowHisTB, compJobQueueTB, compServerTB)) {
            workflowSchema.setCompjobStatusStart(workflowHisTB, compJobQueueTB, compServerTB);
            compserverSchema.setCompserverStatus(STATUS.WORKING, compServerTB);
        }
        else {
            compJobQueueTB.setStatusEnum(STATUS.NONE);
            compServerTB.setStatusEnum(STATUS.ERROR);
            workflowMapper.updateCompjobAllocStatus(compJobQueueTB);
            workflowMapper.updateCompserverAllocStatus(compServerTB);
        }
    }

    public int reportProgress(HttpSession httpSession, int compserverId, ReportProgressRequest request) throws Exception {

        System.out.println("== reportProgress == ");
        System.out.println(ReflectionToStringBuilder.toString(request, ToStringStyle.MULTI_LINE_STYLE));

        int jobID = request.getJobid();
        String progressExValue = request.getExvalue();
        int progress = request.getProgress();

        CompJobQueueTB compJobQueueTB = workflowMapper.selectJobByID(jobID);
        if (compJobQueueTB == null) {
            throw new Exception("compjob 데이타가 존재하지 않습니다");
        }
        if (compJobQueueTB.getStatusEnum() != STATUS.WORKING ) {
            throw new Exception("Working 상태가 아닙니다");
        }
        if (compJobQueueTB.getCompserverid() != compserverId) {
            throw new Exception("compserverId 가 다릅니다");
        }
        WorkflowHisTB workflowHisTB = workflowMapper.selectWorkflowByID(compJobQueueTB.getWorkflowid());
        if (workflowHisTB == null ) {
            throw new Exception("workflow 데이타가 존재하지 않습니다");
        }


        DArcWorkflowSchema workflowSchema = new DArcWorkflowSchema(workflowMapper, workflowHisTB.getWorkflowname(), workflowHisTB.getSubtype());
        workflowSchema.reportProgress(workflowHisTB, compJobQueueTB, progress, progressExValue);

        return workflowMapper.updateCompserverTimeStamp(compserverId);
    }



    public int reportResult(HttpSession httpSession, int compserverId, ReportResultRequest request ) throws Exception {

        System.out.println("== reportResult == ");
        System.out.println(ReflectionToStringBuilder.toString(request, ToStringStyle.MULTI_LINE_STYLE));

        int jobId = request.getJobid();
        String message = request.getMessage();
        STATUS status = STATUS.valueOf(StringUtils.upperCase(request.getResult()));

        CompJobQueueTB compJobQueueTB = workflowMapper.selectJobByID(jobId);
        CompServerTB compServerTB = workflowMapper.getCompServerByID(compserverId);

        if (compJobQueueTB == null) {
            throw new Exception("compjob 데이타가 존재하지 않습니다");
        }
        if (compServerTB == null) {
            throw new Exception("compserver 데이타가 존재하지 않습니다");
        }
//        if (compJobQueueTB.getStatusEnum() != STATUS.WORKING) {
//            throw new Exception("Working 상태가 아닙니다");
//        }
        WorkflowHisTB workflowHisTB = workflowMapper.selectWorkflowByID(compJobQueueTB.getWorkflowid());
        if (workflowHisTB == null) {
            throw new Exception("workflow 데이타가 존재하지 않습니다");
        }
        if (status == STATUS.SUCCESS) {
            ObjectMapper mapper = new ObjectMapper();
            CompJobQueueTB compJob = workflowMapper.selectJobByID(jobId);
            try {
                Map param = mapper.readValue(compJob.getParamlist(), HashMap.class);
                int videoid = (int) param.get("idx");
                readJson(videoid);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        DArcWorkflowSchema workflowSchema = new DArcWorkflowSchema(workflowMapper, workflowHisTB.getWorkflowname(), workflowHisTB.getSubtype());
        workflowSchema.reportResult(httpSession, compJobQueueTB, workflowHisTB, status, message);



        DArcCompserverSchema compserverSchema = new DArcCompserverSchema(workflowMapper, compServerTB.getCompservername());
        compserverSchema.setCompserverStatus(status, compServerTB);
        return 1;
    }













    boolean callCompserver(WorkflowHisTB workflowDataTb, CompJobQueueTB compjobDatTb, CompServerTB compserverDatTb) throws Exception {
        CompJobTB compjobCnfTb = workflowMapper.getCompJob(compjobDatTb.getJobname());

        try {
            Map<String, Object> jobPropertyMap = JSONUtils.jsonStringToMap(compjobDatTb.getJobcontents());
            jobPropertyMap.put("workflowid", workflowDataTb.getWorkflowid());
            jobPropertyMap.put("workflowname", workflowDataTb.getWorkflowname());
            jobPropertyMap.put("jobid", compjobDatTb.getJobid());
            jobPropertyMap.put("jobname", compjobDatTb.getJobname());
            jobPropertyMap.put("jobtype", compjobCnfTb.getJobType());

            Map<String, Object> mamExMap = new HashMap<>();
            mamExMap.put("start", compjobCnfTb.getStartExName());
            mamExMap.put("progress", compjobCnfTb.getProgressExName());
            mamExMap.put("end", compjobCnfTb.getEndExName());
//            mamExMap.put("start", "startCompjob");
//            mamExMap.put("progress", "progress");
//            mamExMap.put("end", "startCompjob");
//            mamExMap.put("pool", 0);
            jobPropertyMap.put("mamex", mamExMap);

            String jobProperty = JSONUtils.jsonStringFromObjectLowerCase(jobPropertyMap);
            log.debug(jobProperty);

            String host = compserverDatTb.getClienturl();
            if (StringUtils.isEmpty(host)) {
                host = String.format("http://%s:%d", compserverDatTb.getClientip(), compserverDatTb.getPort());
            }
            String url = String.format("%s/v2/compservers/%d/jobs", host, compserverDatTb.getCompserverid());

            System.out.println("--------- ossbaord --------------");
            System.out.println(url);
            System.out.println(jobProperty);

            URI uri = RestUtils.makeURI( url );
            ResponseEntity response = RestUtils.makeRestTemplate(uri, HttpMethod.POST, jobPropertyMap);
            if (response != null && response.getStatusCodeValue() != 200) {
                System.out.println("error_statuscode = " + response.getStatusCode());
                System.out.println("error_body = " + response.getBody());
                throw new Exception("콤포넌트 호출에 오류가 발생했습니다( " + url + " )");
            }
            System.out.println("statuscode = " + response.getStatusCode());
            System.out.println("body = " + response.getBody());

        } catch (IOException e) {
            return false;
        }

        return true;
    }



    public void readJson(int videoid) throws Exception {
        ContentQuery param = new ContentQuery();
        param.setIdx(videoid);
        List<ShotTB> shotList = storyboardMapper.getShotList(param);
        Map upParam = new HashMap();
        ObjectMapper mapper = new ObjectMapper();
        for(ShotTB shot : shotList){
            String assetFilePath = shot.getAssetfilepath();
            String assetFileName = shot.getAssetfilename();
            assetFileName = assetFileName.substring(0,assetFileName.indexOf("."));
            File dir = new File(proxyShotFolder+"/"+assetFilePath+assetFileName);
            if(dir.exists()&&dir.isDirectory()){
                File[] jsonFils = dir.listFiles((file, name) -> name.toLowerCase().endsWith(".json"));
                Arrays.sort(jsonFils);
                System.out.println(dir.getName());
                if(jsonFils != null && jsonFils.length>0) {
                    Arrays.sort(jsonFils);
                    List json = JSONUtils.jsonFileToList(jsonFils[0]);
                    String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
                    upParam.put("videoid",shot.getVideoid());
                    upParam.put("shotid",shot.getShotid());
                    upParam.put("object",jsonString);
                    System.out.println(jsonFils[0].getName()+"/"+jsonString);
                    storyboardMapper.updateShotItem(upParam);
                }
            }

        }
    }

    public void putCatalogQue(int idx, String status, String error_msg, int progress){
        ContentQuery param = new ContentQuery();
        param.setIdx(idx);

        String callback_url = "";
        String set_video_filepath = videoFolder;
        String set_proxy_filepath = proxyShotFolder;

        log.debug("set_video_filepath ===> " + set_video_filepath);
        log.debug("set_proxy_filepath ===> " + set_proxy_filepath);

        set_video_filepath = set_video_filepath.replace(videoFolder, "/mnt/disk02/darc4data/video");
        set_proxy_filepath = set_proxy_filepath.replace(proxyShotFolder,"/mnt/disk02/darc4data/proxyshot");

        log.debug("set_video_filepath ===> " + set_video_filepath);
        log.debug("set_proxy_filepath ===> " + set_proxy_filepath);

        ContentField field = contentMapper.getContentItem(param);
        HashMap<String,Object> paramMap = new HashMap<>();
        paramMap.put("videoid",idx);
        paramMap.put("catalog_path",set_proxy_filepath + File.separator + field.getAssetfilepath());
        paramMap.put("video_full_path",set_video_filepath + File.separator + field.getAssetfilepath() + File.separator + field.getAssetfilename());
        paramMap.put("callback_url", "callback/"+callback_url);
        paramMap.put("status", status);
        paramMap.put("cancle_yn", "N");
        paramMap.put("error_msg", error_msg);
        paramMap.put("progress", progress);
        workflowMapper.putCatalogQue(paramMap);
    }
}
