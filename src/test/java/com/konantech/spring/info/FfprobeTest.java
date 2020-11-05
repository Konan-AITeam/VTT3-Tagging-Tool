package com.konantech.spring.info;

import com.konantech.spring.util.FFmpegUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class FfprobeTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private FFmpegUtil fFmpegUtil;

    @Test
    public void ffprobeTest() {

        String query = "ffprobe|-print_format|json|-show_streams|/data/tmp/1.mp4";
        String r = fFmpegUtil.getExecString(query);
        System.out.println(r);

    }



}
