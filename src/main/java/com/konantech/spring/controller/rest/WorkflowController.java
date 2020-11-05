package com.konantech.spring.controller.rest;


import com.konantech.spring.domain.response.MapResponse;
import com.konantech.spring.domain.workflow.*;
import com.konantech.spring.entities.ErrorCode;
import com.konantech.spring.exception.ApiException;
import com.konantech.spring.exception.FieldException;
import com.konantech.spring.response.ObjectResponse;
import com.konantech.spring.service.WorkflowService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
public class WorkflowController {

    @Autowired
    WorkflowService workflowService;

    private static final String CHECKIN = "{</br>" +
            " \"compservername\": \"transcoder\",</br>" +
            " \"channel\": 0,</br>" +
            " \"pool\": 0,</br>" +
            " \"jobnames\": [\"transcoding\"],</br>" +
            " \"port\" : 9999,</br>" +
            " \"clienturl\" : \"http://0.0.0.0:9999\"</br>" +
            "}</br>";
    @ApiOperation(value = "컨포넌트 체크인", notes = CHECKIN )
    @RequestMapping(value = "/v2/compservers", method = {RequestMethod.POST})
    public ResponseEntity<?> checkin(@Valid @RequestBody CheckInRequest checkInRequest, BindingResult result , HttpServletRequest request) throws Exception {
        if (result.hasErrors()) {
            throw new FieldException(result);
        }
        try {
            Map<String, Object> response = new LinkedHashMap<>();
            response.put("compserverId", workflowService.checkin(request, checkInRequest));
            return new ObjectResponse(response);
        } catch (Exception e) {
            throw new ApiException(ErrorCode.ERR_7010_COMPSERVER_ERROR, "checkout 오류 입니다 ( " + e.getMessage() + " )");
        }
    }

    @ApiOperation(value = "콤포넌트 체크아웃", notes = "체크인한 콤포넌트서버 ID값 입력" )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "compserverid", value = "콤포넌트서버ID", required = true, dataType = "int", paramType = "path", defaultValue = "3")
    })
    @RequestMapping(value = "/v2/compservers/{compserverid}", method = {RequestMethod.DELETE})
    public ResponseEntity<?> checkout(@PathVariable int compserverid) throws Exception {
        try {
            Map<String, Object> response = new LinkedHashMap<>();
            response.put("compserverId", workflowService.checkout(compserverid));
            return new ObjectResponse(response);
        } catch (Exception e) {
            throw new ApiException(ErrorCode.ERR_7010_COMPSERVER_ERROR, "checkout 오류 입니다 ( " + e.getMessage() + " )");
        }
    }

    private static final String reportProgress = "{</br>" +
            "        \"jobid\":123456,</br>" +
            "            \"progres\": 50,</br>" +
            "            \"exvalue\": \"120fps\"</br>" +
            "    }</br>";
    @ApiOperation(value = "진행률 보고", notes = reportProgress )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "compserverid", value = "콤포넌트서버ID", required = true, dataType = "int", paramType = "path", defaultValue = "3")
    })
    @RequestMapping(value = "/v2/compservers/{compserverid}/reportProgress", method = {RequestMethod.PUT})
    public ResponseEntity<?> reportProgress(@PathVariable int compserverid, @Valid @RequestBody ReportProgressRequest request, BindingResult result, HttpSession httpSession) throws Exception {
        if (result.hasErrors()) {
            throw new FieldException(result);
        }
        try {
            System.out.println("reportProgress >>> ");
            System.out.println(request.toString());
            workflowService.reportProgress(httpSession, compserverid, request );
            MapResponse mapResponse = new MapResponse();
            mapResponse.setResult("success");
            return new ObjectResponse(mapResponse);
        } catch (Exception e) {
            throw new ApiException(ErrorCode.ERR_7010_COMPSERVER_ERROR, "reportProgress 오류 입니다 ( " + e.getMessage() + " )");
        }
    }

    private static final String reportResult = "{</br>" +
            "        \"jobid\":123456,</br>" +
            "            \"result\": \"success\",</br>" +
            "            \"message\": \"\"</br>" +
            "    }</br>";
    @ApiOperation(value = "작업 결과 보고", notes = reportResult )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "compserverid", value = "콤포넌트서버ID", required = true, dataType = "int", paramType = "path", defaultValue = "3")
    })
    @RequestMapping(value = "/v2/compservers/{compserverid}/reportResult", method = {RequestMethod.PUT})
    public ResponseEntity<?> reportResult(@PathVariable int compserverid, @Valid @RequestBody ReportResultRequest request, BindingResult result, HttpSession httpSession) throws Exception {
        if (result.hasErrors()) {
            throw new FieldException(result);
        }
        try {
            System.out.println("reportResult >>> ");
            System.out.println(request.toString());
            workflowService.reportResult(httpSession, compserverid, request );
            MapResponse mapResponse = new MapResponse();
            mapResponse.setResult("success");
            return new ObjectResponse(mapResponse);
        } catch (Exception e) {
            throw new ApiException(ErrorCode.ERR_7010_COMPSERVER_ERROR, "reportResult 오류 입니다 ( " + e.getMessage() + " )");
        }

    }















    @ApiOperation(value = "워크플로우 검색", notes = "" )
    @RequestMapping(value = "/v2/workflows", method = {RequestMethod.GET})
    public ResponseEntity<?> getWorkflows(HttpServletRequest request) throws Exception {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("list", "작업중..");
        return ResponseEntity.ok(response);
    }

    private static final String createWorkflow = "{<br/>" +
            " \"workflowname\":\"ingest\",<br/>" +
            " \"assetname\":\"ast_br_video\",<br/>" +
            " \"assetid\":310091,<br/>" +
            " \"subtype\":0<br/>" +
            "}<br/>";
    @ApiOperation(value = "워크플로우 실행", notes = createWorkflow )
    @RequestMapping(value = "/v2/workflows", method = {RequestMethod.POST})
    public ResponseEntity<?> createWorkflow(@Valid @RequestBody WorkflowRequest request, BindingResult result, HttpSession httpSession ) throws Exception {
        if (result.hasErrors()) {
            throw new FieldException(result);
        }
        try {
            List<Integer> workflowIds = workflowService.createWorkflowHis(
                    httpSession,
                    request.getWorkflowname(),
                    request.getAssetname(),
                    request.getAssetid(),
                    request.getSubtype());

            Map<String, Object> response = new LinkedHashMap<>();
            response.put("workflowIds", workflowIds );

            MapResponse mapResponse = new MapResponse();
            mapResponse.setResult("success");
            mapResponse.setMessage(response);
            return new ObjectResponse(mapResponse);

        } catch (Exception e) {
            throw new ApiException(ErrorCode.ERR_7010_COMPSERVER_ERROR, "workflows 오류 입니다 ( " + e.getMessage() + " )");
        }
    }







}
