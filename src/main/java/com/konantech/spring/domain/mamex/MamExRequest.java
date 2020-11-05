package com.konantech.spring.domain.mamex;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
public class MamExRequest implements Serializable {

    @NotNull(message = "pool 를 입력하세요")
    private int pool;

    @NotEmpty(message = "trnames 를 입력하세요")
    private List<String> trnames;

    @NotEmpty(message = "uri 를 입력하세요")
    private String uri;

    public MamExRequest() { }
}