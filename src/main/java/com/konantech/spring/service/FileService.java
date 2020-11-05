package com.konantech.spring.service;

import net.sf.json.JSONObject;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class FileService {

    @Value("${darc.tempFolder}")
    public String tempFolder;

    public Map createJsonFileStream(Map paramMap, String type, String json) throws Exception{
        HashMap<String,Object> result = new HashMap<>();
        String fileName =type+"_"+paramMap.get("userid") + "_" + paramMap.get("idx") + ".json";

        JSONObject jsonObject = JSONObject.fromObject(json);
        json = jsonObject.toString(4);

        byte[] contentByte = json.getBytes();

        result.put("name", fileName);
        result.put("content",contentByte);
        return result;
    }

    public Map createImgFileStream(String path, String fileName) throws Exception{
        HashMap<String,Object> result = new HashMap<>();
        File file = new File(path);
        if(!file.exists()){
            return null;
        }

        byte[] contentByte = FileUtils.readFileToByteArray(file);

        result.put("name", fileName);
        result.put("content",contentByte);
        return result;
    }

    public void downloadZip(HttpServletResponse response, String type, List<Map> list) throws Exception{
        FileOutputStream fos = null;
        ZipOutputStream zipOut = null;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd_HHmm");
        String today = simpleDateFormat.format(new Date());
        String zipFileName;
        if(list.size() == 0) {
            zipFileName = type+ "_" + today+"_empty_file"+".zip";
        } else {
            zipFileName = type+ "_" + today+".zip";
        }
        File zip = new File(tempFolder+File.separator+zipFileName);
        fos = new FileOutputStream(zip);
        zipOut = new ZipOutputStream(new BufferedOutputStream(fos));

        for (Map data : list){
            ZipEntry ze = new ZipEntry(MapUtils.getString(data,"name"));
            zipOut.putNextEntry(ze);
            byte[] tmp = (byte[])data.get("content");
            zipOut.write(tmp);
            zipOut.flush();
        }
        zipOut.close();

        String headerResponse = "attachment;filename=";
        headerResponse = headerResponse.concat(zipFileName);
        response.setContentType("application/json");
        response.setHeader("Set-Cookie", "fileDownload=true; path=/");
        response.addHeader("Content-disposition", headerResponse);
        ServletOutputStream out = response.getOutputStream();

        FileInputStream zfis = new FileInputStream(zip);
        byte[] tmp = new byte[4*1024];
        int size = 0;
        while((size = zfis.read(tmp)) != -1){
            out.write(tmp, 0, size);
        }
        out.flush();
        out.close();
        if(zip.exists()) zip.delete();
    }
}
