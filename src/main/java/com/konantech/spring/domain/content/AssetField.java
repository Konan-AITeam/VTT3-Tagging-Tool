package com.konantech.spring.domain.content;

import lombok.Data;

import java.io.Serializable;

@Data
public class AssetField implements Serializable {

    private String fieldName;
    private String fieldType;
    private String fieldCaption;
    private int isReadonly;
    private String defaultValue;

}