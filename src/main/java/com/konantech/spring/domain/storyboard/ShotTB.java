package com.konantech.spring.domain.storyboard;

import com.konantech.spring.domain.content.AnalyzerSogang;
import lombok.Data;

import java.io.Serializable;

@Data
public class ShotTB implements Serializable {

    private int shotid;
    private int videoid;
    private int step;
    private String content;
    private String starttimecode;
    private int startframeindex;
    private String endtimecode;
    private int endframeindex;
    private String assetfilename;
    private String assetfilepath;
    private String object;
    private boolean delflag;
    private int startno;
    private double activescore;

    //add
    private AnalyzerSogang detect;
}