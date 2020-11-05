package com.konantech.spring.controller.rest;

import com.konantech.spring.domain.content.ContentField;
import com.konantech.spring.domain.content.ContentQuery;
import com.konantech.spring.domain.content.ResultOGQ;
import com.konantech.spring.domain.content.VideoFile;
import com.konantech.spring.domain.response.ItemResponse;
import com.konantech.spring.domain.response.ListResponse;
import com.konantech.spring.domain.storyboard.ShotTB;
import com.konantech.spring.response.ObjectResponse;
import com.konantech.spring.service.ContentService;
import com.konantech.spring.service.StoryboardService;
import com.konantech.spring.util.RequestUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RestController
public class ContentRestController {

    @Autowired
    private ContentService contentService;

    @Autowired
    private StoryboardService storyboardService;


    private static final String UPLOAD_INPUT = "</br>" +
            "  \"title\": \"제목입니다\"</br>" +
            "  \"content\": \"내용입니다\"</br>" +
            "  \"orifilename\": \"원본이름.mp4\"</br>" +
            "  \"file\": \"첨부파일선택\"</br>" +
            "</br>";

    @ApiOperation(value = "비디오추가", notes = UPLOAD_INPUT)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title", value = "제목", required = true, dataType = "String", paramType = "query", defaultValue = "제목입니다"),
            @ApiImplicitParam(name = "content", value = "내용", required = false, dataType = "String", paramType = "query", defaultValue = "내용입니다"),
            @ApiImplicitParam(name = "orifilename", value = "원본파일명", required = false, dataType = "String", paramType = "query", defaultValue = "원본이름.mp4"),
            @ApiImplicitParam(name = "file", value = "첨부파일", required = false, dataType = "file", paramType = "formData")
    })
    @RequestMapping(value = "/v2/upload", method = RequestMethod.POST)
    public ObjectResponse upload(
            @ApiIgnore HttpServletRequest httpServletRequest,
            @RequestParam String title,
            @RequestParam String content,
            @RequestParam String orifilename,
            @RequestParam("file") MultipartFile file,
            HttpSession httpSession) throws Exception {

        if (StringUtils.isEmpty(title)) {
            title = orifilename;
        }
        VideoFile videoFile = new VideoFile();
        videoFile.setTitle(title);
        videoFile.setContent(content);
        videoFile.setOrifilename(orifilename);
        videoFile.setFile(file);
        videoFile.setRemoteAddr(RequestUtils.getRemoteAddr(httpServletRequest));
        return new ObjectResponse<>(contentService.upload(videoFile, httpSession));
    }


    private static final String ASSET_INPUT = "</br>" +
            "  \"title\": \"제목입니다\"</br>" +
            "  \"content\": \"내용입니다\"</br>" +
            "  \"orifilename\": \"원본이름.mp4\"</br>" +
            "  \"filepath\": \"/data/test.mp4\"</br>" +
            "</br>";

    @ApiOperation(value = "비디오추가", notes = ASSET_INPUT)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title", value = "제목", required = true, dataType = "String", paramType = "query", defaultValue = "제목입니다"),
            @ApiImplicitParam(name = "content", value = "내용", required = false, dataType = "int", paramType = "query", defaultValue = "내용입니다"),
            @ApiImplicitParam(name = "orifilename", value = "원본파일명", required = false, dataType = "String", paramType = "query", defaultValue = "원본이름.mp4"),
            @ApiImplicitParam(name = "filepath", value = "서버 절대경로", required = false, dataType = "String", paramType = "query", defaultValue = "/data/test.mp4")
    })
    @RequestMapping(value = "/v2/insert", method = RequestMethod.PUT, produces = {"application/json"})
    public ObjectResponse insert(
            @ApiIgnore HttpServletRequest httpServletRequest,
            @RequestParam String title,
            @RequestParam String content,
            @RequestParam String orifilename,
            @RequestParam String filepath,
            HttpSession httpSession) throws Exception {

        VideoFile videoFile = new VideoFile();
        videoFile.setTitle(title);
        videoFile.setContent(content);
        videoFile.setOrifilename(orifilename);
        videoFile.setFilepath(filepath);
        videoFile.setRemoteAddr(RequestUtils.getRemoteAddr(httpServletRequest));
        return new ObjectResponse<>(contentService.upload(videoFile, httpSession));
    }


    @ApiOperation(value = "비디오상세", notes = "상세조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "idx", value = "IDX", required = false, dataType = "int", paramType = "path", defaultValue = "1")
    })

    @RequestMapping(value = "/v2/content/{idx}", method = RequestMethod.GET)
    public ObjectResponse getContent(@PathVariable int idx) throws Exception {

        ContentQuery query = new ContentQuery();
        query.setIdx(idx);
        ContentField item = contentService.getContentItem(query);

        ItemResponse itemResponse = new ItemResponse<ContentField>();
        itemResponse.setItem(item);
        return new ObjectResponse<>(itemResponse);
    }

    @RequestMapping(value = "/v2/shots/{idx}", method = RequestMethod.GET)
    public ObjectResponse getShots(@PathVariable int idx) throws Exception {

        ContentQuery query = new ContentQuery();
        query.setIdx(idx);

        List<Map<String, Object>> list = contentService.getFrameImgTb(idx);
        String jsonPath = contentService.getResultJson(idx);

        ResultOGQ ro = new ResultOGQ();
        ro.setJsonPath(jsonPath);
        ro.setImgList(list);

        ItemResponse ItemResponse = new ItemResponse<ResultOGQ>();
        ItemResponse.setItem(ro);
        return new ObjectResponse<>(ItemResponse);
    }

/*    @RequestMapping(value = "/v2/shots/{idx}", method = RequestMethod.GET)
    public ObjectResponse getShots(@PathVariable int idx) throws Exception {

        ContentQuery query = new ContentQuery();
        query.setIdx(idx);
        List<ShotTB> list = storyboardService.getShotList(query);

        ListResponse listResponse = new ListResponse<List<Map<String, Object>>>();
        listResponse.setList(list);
        return new ObjectResponse<>(listResponse);
    }*/

    @RequestMapping(value = "/v2/retry/{cname}/{idx}", method = RequestMethod.GET)
    public ObjectResponse contentRetry(HttpServletRequest request, @PathVariable String cname, @PathVariable String idx) throws Exception {
        contentService.retry(request, cname, idx);
        ItemResponse itemResponse = new ItemResponse<ContentField>();
        return new ObjectResponse<>(itemResponse);
    }

}
