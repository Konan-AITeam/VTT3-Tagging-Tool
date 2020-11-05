package com.konantech.spring.domain.vtt;

import lombok.Data;

@Data
public class RepImgVo {

//    String repIdx;
//    String repJson;
//    String repFileId;
//    String repImgSeq;
//    String repImgUrl;
//    String regTime;
//    String repVideoId;
//    String repSectionId;
//    String savedChk;

    int videoid;
    int sceneid;
    int shotid;
    int frameimgid;
    int vtt_meta_idx;
    String repjson;
    String savechk;
    String assetfilepath;
    String assetfilename;
    String registed_time;

}
