package com.konantech.spring.controller.rest;

import com.konantech.spring.entities.ErrorCode;
import com.konantech.spring.exception.ApiException;
import com.konantech.spring.response.MapResponse;
import com.konantech.spring.response.ObjectResponse;
import com.konantech.spring.service.MamExService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MamExController {

    @Autowired
    MamExService mamexService;


    private static final String callRestShot = "{  <br/>" +
            "   \"assetinfo\":{  <br/>" +
            "      \"type\":\"shot\",<br/>" +
            "      \"shotinfo\":{  <br/>" +
            "         \"assetid\":112,<br/>" +
            "         \"sequencetype\":\"start\",<br/>" +
            "         \"starttimecode\":\"00:00:10:11\",<br/>" +
            "         \"endtimecode\":\"00:00:11:11\",<br/>" +
            "         \"startframeindex\":111,<br/>" +
            "         \"endframeindex\":122,<br/>" +
            "         \"filepath\":\"/data/2010/12/23/11111.jpg\"<br/>" +
            "      }<br/>" +
            "   }<br/>" +
            "}";

    @ApiOperation(value = "mamEx 호출", notes = callRestShot)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "trname", value = "trname", required = true, dataType = "string", paramType = "path", defaultValue = "ex_update_jobresult"),
            @ApiImplicitParam(name = "pool", value = "pool", required = true, dataType = "string", paramType = "path", defaultValue = "1")
    })
    @RequestMapping(value = "/v2/shot", method = {RequestMethod.PUT})
    public ResponseEntity<?> callMamEx(@RequestBody String request) throws Exception {
        try {
            mamexService.callMamEx(request);

            MapResponse mapResponse = new MapResponse();
            mapResponse.setResult("success");
            return new ObjectResponse(mapResponse);

        } catch (Exception e) {
            throw new ApiException(ErrorCode.ERR_7010_COMPSERVER_ERROR, "callMamEx 오류 입니다 ( " + e.getMessage() + " )");
        }
    }

    private static final String callMamEx = "{  <br/>" +
            "   \"workinfo\":{  <br/>" +
            "   },<br/>" +
            "   \"assetinfo\":{  <br/>" +
            "      \"type\":\"shot\",<br/>" +
            "      \"shotinfo\":{  <br/>" +
            "         \"assetid\":112,<br/>" +
            "         \"sequencetype\":\"start\",<br/>" +
            "         \"starttimecode\":\"00:00:10:11\",<br/>" +
            "         \"endtimecode\":\"00:00:11:11\",<br/>" +
            "         \"keypositiontimecode\":\"00:00:10:25\",<br/>" +
            "         \"startframeindex\":111,<br/>" +
            "         \"endframeindex\":122,<br/>" +
            "         \"keypositionframeindex\":1117,<br/>" +
            "         \"facerect\":[  <br/>" +
            "            \"11 12 13 14\",<br/>" +
            "            \"11 12 13 14\",<br/>" +
            "            \"11 12 13 14\"<br/>" +
            "         ],<br/>" +
            "         \"storage\":\"X\",<br/>" +
            "         \"filepath\":\"/data/2010/12/23/11111.jpg\"<br/>" +
            "      }<br/>" +
            "   }<br/>" +
            "}";

    @ApiOperation(value = "mamEx 호출", notes = callMamEx)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "trname", value = "trname", required = true, dataType = "string", paramType = "path", defaultValue = "ex_update_jobresult"),
            @ApiImplicitParam(name = "pool", value = "pool", required = true, dataType = "string", paramType = "path", defaultValue = "1")
    })
    @RequestMapping(value = "/v2/mamEx/{trname}/{pool}", method = {RequestMethod.PUT})
    public ResponseEntity<?> callMamEx(@PathVariable String trname, @PathVariable int pool, @RequestBody String request) throws Exception {
        try {
            mamexService.callMamEx(trname, request);

            MapResponse mapResponse = new MapResponse();
            mapResponse.setResult("success");
            return new ObjectResponse(mapResponse);

        } catch (Exception e) {
            throw new ApiException(ErrorCode.ERR_7010_COMPSERVER_ERROR, "callMamEx 오류 입니다 ( " + e.getMessage() + " )");
        }
    }

}
