package com.konantech.spring.domain.response;

import com.konantech.spring.domain.content.AssetField;
import lombok.Data;

import java.util.List;

@Data
public class ItemResponse<T> {

    private List<AssetField> filed;
    private T item;

}
