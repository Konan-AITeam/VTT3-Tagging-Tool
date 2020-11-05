
package com.konantech.spring.domain.convertobj;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
public class ConvertObj {

    public String image;
    public String modules;
    public String token;
    public String uploaded_date;
    public String updated_date;
    public String cap;
    public List<Result> results = new LinkedList<>();

    @Data
    public static class Result {
        public String module_name;
        public List<Module_result> module_result = null;

        @Data
        public static class Module_result {
            public Position position;
            public List<Label> label = null;

            @Data
            public static class Position {

                public String y;
                public String h;
                public String w;
                public String x;

            }

            @Data
            public static class Label {
                public String score;
                public String description;
                public String behavior;
                public String emotion;
                public String predIcate;
                public String spot;
                public String relPerson;

            }


        }


    }


}
