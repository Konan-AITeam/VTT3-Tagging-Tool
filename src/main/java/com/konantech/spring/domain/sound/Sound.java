
package com.konantech.spring.domain.sound;

import lombok.Data;

import java.util.List;

@Data
public class Sound {
    public String file_name;
    public String registed_name;
    public List<SoundResult> sound_results = null;

    @Data
    public static class SoundResult {
        public String start_time;
        public String end_time;
        public String sound_type;
    }

}
