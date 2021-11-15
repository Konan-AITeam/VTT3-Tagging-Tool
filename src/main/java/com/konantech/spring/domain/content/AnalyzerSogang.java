package com.konantech.spring.domain.content;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

@Data
public class AnalyzerSogang {


    private ArrayList<FaceInfo> faceData;
    private ArrayList<ObjectInfo> objectData;
    private ArrayList<PlaceInfo> placeData;

    @Data
    public class FaceInfo {
        String location;
        Info info;
    }

    @Data
    public class ObjectInfo {
        String location;
        Info info;
    }

    @Data
    public class PlaceInfo {
        String location;
        Info info;
    }

    @Data
    public class Info {
        String objectName;
        double accuracy;
    }

    public AnalyzerSogang(String jsonString) {

//        int origin = 1280;
//        int target = 200;
//        double rate = (double) target / (double) origin;
//        double rate = 1D;

        try {
            ObjectMapper mapper = new ObjectMapper();
            HashMap<String, Object> map = mapper.readValue(jsonString, new HashMap<String, Object>().getClass());

            // face
            JSONArray jsonArrayFace = JSONArray.fromObject(map.get("faceData"));
            faceData = new ArrayList<>();
            for(Object obj : jsonArrayFace) {
                JSONArray jsonArray = (JSONArray) obj;
                Object obj1 = jsonArray.get(1);
                if ( obj1 instanceof JSONObject) {
                    JSONArray rect = (JSONArray) jsonArray.get(0);
                    JSONObject name = (JSONObject) jsonArray.get(1);
                    faceData.add(getFaceInfo(rect, name));
                } else if ( obj1 instanceof JSONArray ) {
                    for (Object obj2 : (JSONArray) obj1) {
                        JSONArray rect = (JSONArray) ((JSONArray) obj1).get(0);
                        JSONObject name = (JSONObject) ((JSONArray) obj1).get(1);
                        faceData.add(getFaceInfo(rect, name));
                    }
                }
            }

            // object
            JSONArray jsonArrayObject = JSONArray.fromObject(map.get("objectData"));
            objectData = new ArrayList<>();
            for(Object obj : jsonArrayObject) {
                JSONArray jsonArray = (JSONArray) obj;
                Object obj1 = jsonArray.get(1);
                if ( obj1 instanceof JSONObject ) {
                    JSONArray rect = (JSONArray) jsonArray.get(0);
                    JSONObject name = (JSONObject) jsonArray.get(1);
                    objectData.add(getObjectInfo(rect, name));
                } else if ( obj1 instanceof JSONArray ) {
                    for (Object obj2 : (JSONArray) obj1) {
                        JSONArray rect = (JSONArray) ((JSONArray) obj1).get(0);
                        JSONObject name = (JSONObject) ((JSONArray) obj1).get(1);
                        objectData.add(getObjectInfo(rect, name));
                    }
                }
            }

            // place
            JSONArray jsonArrayPlace = JSONArray.fromObject(map.get("placeData"));
            placeData = new ArrayList<>();
            for(Object obj : jsonArrayPlace) {
                JSONArray jsonArray = (JSONArray) obj;
                Object obj1 = jsonArray.get(1);
                if ( obj1 instanceof JSONObject ) {
                    JSONArray rect = (JSONArray) jsonArray.get(0);
                    JSONObject name = (JSONObject) jsonArray.get(1);
                    placeData.add(getPlaceInfo(rect, name));
                } else if ( obj1 instanceof JSONArray ) {
                    for (Object obj2 : (JSONArray) obj1) {
                        JSONArray rect = (JSONArray) ((JSONArray) obj1).get(0);
                        JSONObject name = (JSONObject) ((JSONArray) obj1).get(1);
                        placeData.add(getPlaceInfo(rect, name));
                    }
                }
            }

        } catch (Exception ignore) {
            ignore.printStackTrace();
            //ignore
        }
    }

    private FaceInfo getFaceInfo(JSONArray rect, JSONObject name ) {
        List<Double> box = new ArrayList<>();
        for (double n : (List<Integer>) rect) {
            box.add(Double.parseDouble(String.format("%.02f", n )));
        }
        Info i = new Info();
        for(Iterator<String> itr = name.keys(); itr.hasNext(); ) {
            i.setObjectName(itr.next());
            i.setAccuracy(Double.parseDouble(String.format("%.02f", MapUtils.getDouble(name,i.getObjectName()))));
        }
        FaceInfo faceInfo = new FaceInfo();
        faceInfo.setInfo(i);
        faceInfo.setLocation(StringUtils.join(box," "));
        return faceInfo;
    }

    private ObjectInfo getObjectInfo(JSONArray rect, JSONObject name  ) {
        ObjectInfo objectInfo = new ObjectInfo();
        List<Double> box = new ArrayList<>();
        for (double n : (List<Integer>) rect) {
            box.add(Double.parseDouble(String.format("%.02f", n)));
        }
        objectInfo.setLocation(StringUtils.join(box," "));

        for(Iterator<String> itr = name.keys(); itr.hasNext(); ) {
            Info i = new Info();
            i.setObjectName(itr.next());
            i.setAccuracy(Double.parseDouble(String.format("%.02f", MapUtils.getDouble(name,i.getObjectName()))));
            objectInfo.setInfo(i);
        }
        return objectInfo;
    }

    private PlaceInfo getPlaceInfo(JSONArray rect, JSONObject name  ) {
        PlaceInfo placeInfo = new PlaceInfo();
        for(Iterator<String> itr = name.keys(); itr.hasNext(); ) {
            Info i = new Info();
            i.setObjectName(itr.next());
            i.setAccuracy(Double.parseDouble(String.format("%.02f", MapUtils.getDouble(name,i.getObjectName()))));
            placeInfo.setInfo(i);
        }
        return placeInfo;
    }
}
