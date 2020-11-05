package com.konantech.spring.domain.workflow;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.konantech.spring.mapper.WorkflowMapper;
import com.konantech.spring.util.JSONUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import static com.sun.jmx.mbeanserver.Util.cast;

public class DArcCompjobSchema {
    WorkflowMapper workflowMapper;
    String jobName;
    String startExName;
    String progressExName;
    String endExName;
    String jobType;
    List<DArcCompjobParam> paramPropertyList;
    List<DArcCompjobPropertySelect> selectPropertyList;
    List<DArcCompjobPropertyUpdate> statusPropertyList;
    List<DArcCompjobPropertyUpdate> startPropertyList;
    List<DArcCompjobPropertyUpdate> endPropertyList;
    int pool;
    int priority;
    int joborder;


    public String getJobName() {
        return jobName;
    }

    public int getPool() {
        return pool;
    }

    public int getPriority() {
        return priority;
    }

    public String getStartExName() {
        return startExName;
    }

    public String getProgressExName() {
        return progressExName;
    }

    public String getEndExName() {
        return endExName;
    }

    public String getJobType() {
        return jobType;
    }

    public List<DArcCompjobParam> getParamPropertyList() {
        return paramPropertyList;
    }

    public DArcCompjobSchema(WorkflowMapper workflowMapper, String jobName, int pool, int priority, int joborder) {
        this.workflowMapper = workflowMapper;
        this.jobName = jobName;
        this.pool = pool;
        this.priority = priority;
        this.joborder = joborder;
        paramPropertyList = new ArrayList<>();
        selectPropertyList = new ArrayList<>();
        statusPropertyList = new ArrayList<>();
        startPropertyList = new ArrayList<>();
        endPropertyList = new ArrayList<>();

        initCompjob();
    }

    void initCompjob() {
        CompJobTB compjobCnfTb = workflowMapper.getCompJob(jobName);


        startExName = compjobCnfTb.getStartExName();
        progressExName = compjobCnfTb.getProgressExName();
        endExName = compjobCnfTb.getEndExName();
        jobType = compjobCnfTb.getJobType();

        initParamPropertyList(compjobCnfTb.getParamProperty());
        initJobProperty(compjobCnfTb.getJobProperty());
    }

    void initJobProperty(String jobProperty) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> properties = JSONUtils.jsonStringToMap(jobProperty);

            List<Object> itemList = null;
            if (properties.containsKey("select")) {
                itemList = (List<Object>) properties.get("select");
                for (Object obj : itemList) {
                    Map<String, Object> item = (Map<String, Object>) obj;
                    DArcCompjobPropertySelect param = new DArcCompjobPropertySelect();
                    param.setTable((String) item.get("table"));
                    param.setAlias((String) item.get("alias"));
                    param.setSelect((String) item.get("select"));
                    param.setWhere((List<String>) item.get("where"));
                    selectPropertyList.add(param);
                }
            }

            if (properties.containsKey("status")) {
                itemList = (List<Object>) properties.get("status");
                for (Object obj : itemList) {
                    Map<String, Object> item = (Map<String, Object>) obj;
                    DArcCompjobPropertyUpdate param = new DArcCompjobPropertyUpdate();
                    param.setTable((String) item.get("table"));
                    param.setUpdate((String) item.get("update"));
                    param.setWhere((List<String>) item.get("where"));
                    statusPropertyList.add(param);
                }
            }

            if (properties.containsKey("start")) {
                itemList = (List<Object>) properties.get("start");
                for (Object obj : itemList) {
                    Map<String, Object> item = (Map<String, Object>) obj;
                    DArcCompjobPropertyUpdate param = new DArcCompjobPropertyUpdate();
                    param.setTable((String) item.get("table"));
                    param.setUpdate((String) item.get("update"));
                    param.setWhere((List<String>) item.get("where"));
                    startPropertyList.add(param);
                }
            }

            if (properties.containsKey("end")) {
                itemList = (List<Object>) properties.get("end");
                for (Object obj : itemList) {
                    Map<String, Object> item = (Map<String, Object>) obj;
                    DArcCompjobPropertyUpdate param = new DArcCompjobPropertyUpdate();
                    param.setTable((String) item.get("table"));
                    param.setUpdate((String) item.get("update"));
                    param.setWhere((List<String>) item.get("where"));
                    endPropertyList.add(param);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void initParamPropertyList(String paramProperty) {
        try {

            ObjectMapper mapper = new ObjectMapper();
            HashMap<String, Object> map = mapper.readValue(paramProperty, new HashMap<String, Object>().getClass());
            JSONArray jsonArray = JSONArray.fromObject(map.get("paramlist"));
            for(Object obj : jsonArray) {
                Map<String, Object> param = (Map<String, Object>) JSONUtils.jsonStringToObject(obj.toString(), HashMap.class);
                paramPropertyList.add(new DArcCompjobParam(param));
            }
//            List<Object> objects = cast(JSONUtils.jsonStringToMap(paramProperty).get("paramlist"));
//            for(Object object : objects) {
//                Map<String, Object> param = cast (object);
//                paramPropertyList.add(new DArcCompjobParam(param));
//            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 작업 생성 관련 메소드

    public int CreateCompjob(int workflowID) {
        CompJobQueueTB compjobDatTb = new CompJobQueueTB();
        Map<String, Object> paramMap = getParamList(workflowID);
        Map<String, Object> jobPropertyMap = getJobProperty(paramMap);

        compjobDatTb.setWorkflowid(workflowID);
        compjobDatTb.setJobname(jobName);
        compjobDatTb.setPool(pool);
        compjobDatTb.setPriority(priority);
        compjobDatTb.setJoborder(joborder);
        try {
            compjobDatTb.setParamlist(JSONUtils.jsonStringFromObjectLowerCase(paramMap));
            compjobDatTb.setJobcontents(JSONUtils.jsonStringFromObjectLowerCase(jobPropertyMap));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        workflowMapper.putCompJobQueue(compjobDatTb);
        updateAssetJobStatus(compjobDatTb, STATUS.READY, false);

        return compjobDatTb.getJobid();
    }

    Map<String, Object> getParamList(int workflowID) {

        WorkflowHisTB workflowDataTb = workflowMapper.selectWorkflowByID(workflowID);

        HashMap<String, Object> paramMap = new HashMap<>();
        for (DArcCompjobParam property : paramPropertyList) {
            if ("mainkey".equals(property.getType())) {
                paramMap.put(property.getField(), workflowDataTb.getMainassetid());
            } else if ("refkey".equals(property.getType())) {
                paramMap.put(property.getField(), workflowDataTb.getRefassetid());
            } else if ("direct".equals(property.getType())) {
                paramMap.put(property.getField(), property.getValue());
            } else if ("other".equals(property.getType())) {
                String query = property.getOtherQuery(workflowDataTb);
                paramMap.put(property.getField(), workflowMapper.selectCustomInt(query));
            }
        }
        //return paramMap.toString();
        return paramMap;
    }

    Map<String, Object>  getJobProperty(Map<String, Object> paramMap) {
        Map<String, Object> jobPropertyMap = new HashMap<>();
        //jobProperties.put("jobtype", jobType);

        List<Map<String, Object>> jobInfoList = new ArrayList<>();
        jobPropertyMap.put("jobinfos", jobInfoList);
        Map<String, Object> jobInfo = new HashMap<>();
        jobInfoList.add(jobInfo);

        for (DArcCompjobPropertySelect select : selectPropertyList) {
            if (select.isSingleObject()) {
                jobInfo.put(select.getAlias(), workflowMapper.selectCustomObject(select.getSelectQuery(paramMap)));
            }
            else {
                jobInfo.put(select.getAlias(), workflowMapper.selectCustomObjectList(select.getSelectQuery(paramMap)));
            }
        }
        return jobPropertyMap;
    }

    // 작업 진행 상태 업데이트 관련 메소드

    public void setCompjobStatusStart(CompJobQueueTB compjobDatTb, CompServerTB compserverDatTb) {
        compjobDatTb.setStatusEnum(STATUS.WORKING);
        compjobDatTb.setCompserverid(compserverDatTb.getCompserverid());
        compjobDatTb.setChannel(compserverDatTb.getChannel());
        compjobDatTb.setCompserverip(compserverDatTb.getClientip());

        workflowMapper.updateCompjobStart(compjobDatTb);

       updateAssetJobStatus(compjobDatTb, STATUS.WORKING, true);
    }

    public void reportProgress(CompJobQueueTB compjobDatTb, int progress, String progressExValue) {
        compjobDatTb.setStatusEnum(STATUS.WORKING);
        compjobDatTb.setProgress(progress);
        compjobDatTb.setProgressex(progressExValue);
        workflowMapper.updateCompjobProgress(compjobDatTb);

        updateAssetJobStatus(compjobDatTb, STATUS.WORKING, false);
    }

    public void reportResult(CompJobQueueTB compjobDatTb, STATUS status, String message) {
        compjobDatTb.setStatusEnum(status);
        compjobDatTb.setFailreason(message);
        if (status == STATUS.SUCCESS) {
            compjobDatTb.setProgress(100);
        } else if (status == STATUS.POSTPONE || status == STATUS.RETRY) {
            compjobDatTb.setFailcount(compjobDatTb.getFailcount() + 1);
        }
        workflowMapper.updateCompjobResult(compjobDatTb);
        if (status == STATUS.SUCCESS || status == STATUS.FAIL) {
//            workflowMapper.insertToDelCompjob(compjobDatTb.getJobid());
            workflowMapper.deleteCompjob(compjobDatTb.getJobid());
        }
        updateAssetJobStatus(compjobDatTb, status, false);
    }

    void updateAssetJobStatus(CompJobQueueTB compjobDatTb, STATUS status, boolean isStart) {
        Map<String, Object> paramMap = null;
        try {
            paramMap = JSONUtils.jsonStringToMap(compjobDatTb.getParamlist());
            if (isStart) {
                for(DArcCompjobPropertyUpdate startProp : startPropertyList) {
                    workflowMapper.updateCustom(startProp.getUpdateTimeQuery(paramMap));
                }
            }
            if (compjobDatTb.getStatusEnum() == STATUS.SUCCESS || compjobDatTb.getStatusEnum() == STATUS.FAIL) {
                for(DArcCompjobPropertyUpdate endProp : endPropertyList) {
                    workflowMapper.updateCustom(endProp.getUpdateTimeInitQuery(paramMap));
                }
            }
            for(DArcCompjobPropertyUpdate statusProp : statusPropertyList) {
                workflowMapper.updateCustom(statusProp.getUpdateStatusQuery(status, compjobDatTb.getProgress(), paramMap));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setCompjobStatus(STATUS status, CompJobQueueTB compjobDatTb, String failReason) {

    }
    
}
