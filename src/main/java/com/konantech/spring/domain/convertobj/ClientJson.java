package com.konantech.spring.domain.convertobj;

import lombok.Data;
import org.apache.commons.lang.StringUtils;

import java.util.*;

@Data
public class ClientJson {
    private List<Person> person = new LinkedList<>();
    private List<PersonFull> personFull = new LinkedList<>();
    private List<Object> object = new LinkedList<>();
    private List<Place> places = new LinkedList<>();
    private String relate_between_obj;
    private String emotional_behavior;
    private String cap;

    @Data
    public static class Person {
        private String person_name;
        private Rect rect = new Rect();
        private String emotion;
        private String behavior;
        public String score;
    }

    @Data
    public static class PersonFull {
        private String person_name;
        private Rect full_rect = new Rect();
        public String score;
    }

    @Data
    public static class Object {
        private String person_name;
        private String object_name;
        private Rect object_rect = new Rect();
        private String predicate;
        public String score;
    }

    @Data
    public static class Place {
        private String person_name;
        private String place;
        private String spot;
        public String score;
    }


    @Data
    public static class Rect{
        private String x;
        private String y;
        private String max_x;
        private String max_y;
        private String w;
        private String h;


        public void setX(String x){
            if(StringUtils.isNumeric(x) && StringUtils.isNumeric(this.max_x))
                this.w = (Integer.parseInt(this.max_x) - Integer.parseInt(x))+"";
            this.x = x;
        }

        public void setMax_x(String max_x){
            if(StringUtils.isNumeric(this.x) && StringUtils.isNumeric(max_x))
                this.w = (Integer.parseInt(max_x) - Integer.parseInt(this.x))+"";
            else this.w = max_x;
            this.max_x = max_x;
        }

        public void setY(String y){
            if(StringUtils.isNumeric(this.max_y) && StringUtils.isNumeric(y))
                this.h = (Integer.parseInt(this.max_y) - Integer.parseInt(y))+"";
            this.y = y;
        }

        public void setMax_y(String max_y){
            if(StringUtils.isNumeric(this.y) && StringUtils.isNumeric(max_y))
                this.h = (Integer.parseInt(max_y) - Integer.parseInt(this.y))+"";
            else this.h = max_y;
            this.max_y = max_y;
        }

        public void setW(String w){
            if(StringUtils.isNumeric(this.x) && StringUtils.isNumeric(w))
                this.max_x = (Integer.parseInt(w) + Integer.parseInt(this.x))+"";
            else this.max_x = w;
            this.w=w;
        }

        public void setH(String h){
            if(StringUtils.isNumeric(this.y) && StringUtils.isNumeric(h))
                this.max_y = (Integer.parseInt(h) + Integer.parseInt(this.y))+"";
            else this.max_y = h;
            this.h=h;
        }
    }
}
