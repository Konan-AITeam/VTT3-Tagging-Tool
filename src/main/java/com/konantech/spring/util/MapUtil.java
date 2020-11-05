package com.konantech.spring.util;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;

public class MapUtil {
    private MapUtil() {
    }

    public static String getParameter(Map map, String name, String defaultValue) {
        String value ="";
        if(map.get(name) != null){
            value = map.get(name).toString();
        }
        return StringUtils.isEmpty(value) ? defaultValue : value;
    }
}
