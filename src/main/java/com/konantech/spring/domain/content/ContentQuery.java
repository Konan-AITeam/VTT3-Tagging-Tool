package com.konantech.spring.domain.content;

import lombok.Data;

@Data
public class ContentQuery {

    private int idx;
    private int sectionid;
    private String qaSearchWord;
    private String roleadmin;
    private String userid;

    private int limit;
    private int offset;
}
