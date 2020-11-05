package com.konantech.spring.domain.vtt;

import lombok.Data;

@Data
public class MetaInfoVo {
    int vttMetaIdx;
    int representImgIdx;
    int idx;
    int userId;
    String vttMetaJson;
    String regTime;
    String udtTime;
}
