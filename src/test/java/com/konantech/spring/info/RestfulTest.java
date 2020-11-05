package com.konantech.spring.info;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriUtils;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Map;

public class RestfulTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void insert() {

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://127.0.0.1:8080/v2/insert";
        URI uri = URI.create(url);

        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//        headers.setContentType(MediaType.APPLICATION_JSON);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("title", "제목입니다");
        map.add("content", "내용입니다");
        map.add("orifilename", "원본이름.mp4");
        map.add("filepath", "/data/test.mp4");

        HttpEntity<?> request = new HttpEntity<>(map, headers);
        ResponseEntity<?> responseEntity = restTemplate.exchange(uri, HttpMethod.PUT, request, Map.class);
        System.out.println(responseEntity);

    }

    @Test
    public void upload() throws Exception {

        InputStream is = getClass().getResourceAsStream("/sample/1.avi");
        byte[] data = IOUtils.toByteArray(is);
//        Path path = Paths.get("/data/tmp/1.1");
//        byte[] data = Files.readAllBytes(path);
        ByteArrayResource resource = new ByteArrayResource(data){
            @Override
            public String getFilename() throws IllegalStateException {
                return "1.avi";
            }
        };
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://127.0.0.1:8080/v2/upload";
        URI uri = URI.create(url);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap map = new LinkedMultiValueMap<String, Object>();
        map.add("title", "제목입니다");
        map.add("content", "내용입니다");
        map.add("orifilename", "원본이름.mp4");
        map.add("file", resource);

        HttpEntity<?> request = new HttpEntity<>(map, headers);
        ResponseEntity<?> responseEntity = restTemplate.exchange(uri, HttpMethod.POST, request, Map.class);
        System.out.println(responseEntity);
    }

    @Test
    public void urlUplaod() throws Exception {

        int idx = 118;
        String tempFolder = "/Volumes/konan/darc4data/temp";
        String source = "https://s3.ap-northeast-2.amazonaws.com/vtt-ogq/movie_attach/59_길재형 요청.mp4";

        Path fn = Paths.get(source);
        URL url = new URL(UriUtils.encodePath(source,"UTF-8"));
        BufferedInputStream bis = new BufferedInputStream(url.openStream());
        String ext = FilenameUtils.getExtension(source);

        String target = FilenameUtils.normalize(tempFolder + "/" + idx + "." + ext);
        File fileTarget = new File(target);
        if (!fileTarget.exists()) {
            fileTarget.delete();
        }

        FileOutputStream fis = new FileOutputStream(target);
        byte[] buffer = new byte[8192];
        int count;
        while ((count = bis.read(buffer, 0, 8192)) != -1) {
            fis.write(buffer, 0, count);
        }
        fis.close();
        bis.close();

        InputStream is = new FileInputStream(target);
        byte[] data = IOUtils.toByteArray(is);
        ByteArrayResource resource = new ByteArrayResource(data){
            @Override
            public String getFilename() throws IllegalStateException {
                return fn.getFileName().toString();
            }
        };
        RestTemplate restTemplate = new RestTemplate();
        String serverUrl = "http://127.0.0.1:8080/v2/upload";
        URI uri = URI.create(serverUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap map = new LinkedMultiValueMap<String, Object>();
        map.add("title", "제목입니다");
        map.add("content", "내용입니다");
        map.add("orifilename", fn.getFileName().toString());
        map.add("file", resource);

        HttpEntity<?> request = new HttpEntity<>(map, headers);
        ResponseEntity<?> responseEntity = restTemplate.exchange(uri, HttpMethod.POST, request, Map.class);
        System.out.println(responseEntity);

        if (!fileTarget.exists()) {
            fileTarget.delete();
        }

    }

}
