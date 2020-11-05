package com.konantech.spring.domain.content;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class VideoFile {

    private String title;
    private String content;
    private String orifilename;
    private String filepath;
    private String remoteAddr;
    private MultipartFile file;
    private String uuid;
    private int chunks;
    private int chunk;

}
