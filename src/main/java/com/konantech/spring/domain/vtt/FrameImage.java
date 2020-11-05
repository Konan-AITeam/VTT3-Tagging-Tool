package com.konantech.spring.domain.vtt;

import lombok.Data;

@Data
public class FrameImage {
    int videoid;
    int sceneid;
    int shotid;
    int frameimgid;
    int vtt_meta_idx;
    String assetfilename;
    String assetfilepath;
    String autojson;
    String vtt_meta_json;
    String savechk;
    String userid;
    String hiddenchk;
}
