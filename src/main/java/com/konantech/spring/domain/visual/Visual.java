
package com.konantech.spring.domain.visual;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Data
public class Visual {

    public String file_name;
    public String registed_name;
    public List<VisualResult> visual_results = new LinkedList<>();

    @Data
    public static class VisualResult {

        public String image;
        public String period_num;
        public String period_frame_num;
        public String start_time;
        public String end_time;
        public String place;
        public String spot;
        public String related_person;
        public String emotional_behavior;
        public String relate_between_obj;
        public String score;
        public List<Map<String,List<Person>>> person = new LinkedList<>();
        public List<VisualObject> object = new LinkedList<>();

        @Data
        public static class Person {
            public Rect face_rect;
            public Rect full_rect;
            public String behavior;
            public String emotion;
            public String score;
            public List<RelatedObject> related_object = new LinkedList<>();

            @Data
            public static class RelatedObject {

                public String object_name;
                public String predicate;
                public String score;
                public Rect object_rect;

            }

        }

        @Data
        public static class VisualObject {
            public String object_name;
            public String score;
            public Rect object_rect;

        }

        @Data
        public static class Rect {
            public String min_x;
            public String min_y;
            public String max_x;
            public String max_y;
        }

    }

}

