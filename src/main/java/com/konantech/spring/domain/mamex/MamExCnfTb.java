package com.konantech.spring.domain.mamex;

import lombok.Data;

/**
 * Created by seheung on 2017. 4. 24..
 */
@Data
public class MamExCnfTb {
    int mamexid;
    String trname;
    String hostname;
    int port;
    int pool;
    String uri;
    long timestamp;
    boolean fail;
    int delflag;
}
