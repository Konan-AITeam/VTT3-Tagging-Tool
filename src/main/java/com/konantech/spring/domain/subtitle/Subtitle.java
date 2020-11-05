
package com.konantech.spring.domain.subtitle;

import lombok.Data;

import java.util.List;

@Data
public class Subtitle {
    public List<SubtitleResult> script = null;

    @Data
    public static class SubtitleResult {
        public String st;
        public String et;
        public String speaker;
        public String utter;
    }

}
