package com.konantech.spring.service;

import com.konantech.spring.mapper.DownloadMapper;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class DownloadService {
    @Value("${darc.proxyShotFolder}")
    public String proxyShotFolder;

    @Autowired
    private DownloadMapper downloadMapper;

    public List<Map<String, Object>> getSelectWorkedImage(Map map) throws Exception {
        List<Map<String, Object>> list = downloadMapper.getSelectImage(map);

        list.forEach(m -> {
            String path = MapUtils.getString(m, "img_url");
            m.put("path", proxyShotFolder + "/" + path);
        });

        return list;
    }
}
