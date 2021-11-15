
package com.konantech.spring.domain.section;

import lombok.Data;

import java.util.List;

@Data
public class Qa {
    public String file_name;
    public List<QaResult> qa_results = null;

    @Data
    public static class QaResultInfo {
        public String vid = null;
        public String videoType;
        public String videoid;
        public String description;
        public String period_num;
        public String shotId;
        public Integer shot_contained;
        public int idx;


    }

    @Data
    public static class QaResult {
        public String vid = null;
        public String videoType;
        public Integer[] shot_contained = null;
        public String description;
        public List<QnaResult> qa = null;

        @Data
        public static class QnaInfo {
            public int qid;
            public int qa_level;
            public int q_level_mem;
            public int q_level_logic;
            public String que;
            public String true_ans;
            public String wrong_answer1;
            public String wrong_answer2;
            public String wrong_answer3;
            public String wrong_answer4;
        }

        @Data
        public static class QnaResult {
            public int qid;
            public String qa_level;
            public int q_level_mem;
            public int q_level_logic;
            public String que;
            public String true_ans;
            public String[] false_ans;

        }

        @Data
        public static class FalseAns {
            public String false_ans;
        }
    }
}
