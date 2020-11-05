
package com.konantech.spring.domain.section;

import lombok.Data;

import java.util.List;

@Data
public class Section {
    public String file_name;
    public String registed_name;
    public List<QaResult> qa_results = null;

    @Data
    public static class QaResult {
        public String visual_period_num = null;
        public String period_num;
        public String start_time;
        public String end_time;
        public List<DescriptionInfo> description_info = null;
        public List<Qa> qa_info = null;
        public List<RelatedPeriodInfo> related_period_info = null;

        @Data
        public static class DescriptionInfo {
            public String description;

        }
        @Data
        public static class Qa {
            public String question;
            public String answer;
            public String wrong_answer1;
            public String wrong_answer2;
            public String wrong_answer3;
            public String wrong_answer4;

        }
        @Data
        public static class RelatedPeriodInfo {
            public String related_period;
            public String related_type;
        }


    }
}
