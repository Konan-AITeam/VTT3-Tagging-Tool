package com.konantech.spring.domain.content;

import lombok.Data;

import java.util.Date;

@Data
public class ContentField {
    private int idx;
    private String objectid;
    private String title;
    private String content;
    private String orifilename;
    private String assetfilename;
    private String assetfilepath;
    private int assetfilesize;
    private Date transcodingendtime;
    private Date transcodingstarttime;
    private int transcodingstatus;
    private Date catalogendtime;
    private Date catalogstarttime;
    private int catalogstatus;
    private Date createtime;
    private int createuser;
    private String mediainfo;
    private String genre;
    private boolean delflag;


    //add
    private int width;
    private int height;
    private int shotWidth;
    private int shotHeight;

}
