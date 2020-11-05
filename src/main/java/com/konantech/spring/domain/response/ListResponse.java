package com.konantech.spring.domain.response;

import com.konantech.spring.domain.content.AssetField;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ListResponse<T> {

    private List<AssetField> filed;
    private List<T> list;
    private int limit;
    private int offset;
    private int total;

}
