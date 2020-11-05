<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../includes/taglib.jsp" %>
<!-- #MAIN PANEL -->
<ul class="nav nav-tabs bar_tabs" id="myTab" role="tablist">

</ul>
<div class="tab-content" id="nav-tabContent">

</div>

<script>
    $(document).ready(function(){
        //var metaJsonMap = $('#metaJsonMap').val();
        var metaJsonMap = $("#represent-img-nav").find("[name='repJson']").val();
        try{
            var metaJsonArr = $.parseJSON(metaJsonMap);
            var savechk = $("#represent-img-nav").find("[name='savechk']").val();

            var obj1 = $('#myTab');
            var obj2 = $('#nav-tabContent');


            if(savechk=='true'){
                if(metaJsonArr.persons){
                    var li1 = $('<li>',{'class':'active'}).appendTo(obj1);
                    var a1 = $('<a>',{'class':'nav-item nav-link active', id:'nav-tab-person-0'})
                        .attr({'data-toggle':'tab', 'href': '#nav-person-0', role: 'tab', 'area-controls': 'nav-person-0','aria-selected':true}).html(metaJsonArr.image_id===undefined?metaJsonArr.frame_id:metaJsonArr.image_id+' / 인물').appendTo(li1);

                    var div1 = $('<div>',{'class':'tab-pane fade active in', id:'nav-person-0'}).attr({role: 'tabpanel', 'aria-labelledby': 'nav-person-0'}).appendTo(obj2);
                    var table1 = $('<table>', {'class':'table table-striped'}).appendTo(div1);
                    var thead1 = $('<thead>').appendTo(table1);
                    var tr1 = $('<tr>').appendTo(thead1);

                    var th_list = ['이름','얼굴좌표','전체좌표','행동','감정', '서술어', '관련객체'];
                    for(var i=0;i<th_list.length;i++){
                        var th = $('<th>').html(th_list[i]).attr({scope: 'col'}).appendTo(tr1);
                    }


                    var tbody1 = $('<tbody>').appendTo(table1);
                    for(var i=0;i<metaJsonArr.persons.length;i++){
                        var tr = $('<tr>').appendTo(tbody1);
                        var td1 = $('<td>').html(metaJsonArr.persons[i].person_id).appendTo(tr);

                        var face_rect = metaJsonArr.persons[i].person_info.face_rect;
                        var full_rect = metaJsonArr.persons[i].person_info.full_rect;

                        var face_rect_str = face_rect.min_x+','+face_rect.min_y+','+face_rect.max_x+','+face_rect.max_y;
                        var full_rect_str = full_rect.min_x+','+full_rect.min_y+','+full_rect.max_x+','+full_rect.max_y;

                        var td2 = $('<td>').html(face_rect_str).appendTo(tr);
                        var td3 = $('<td>').html(full_rect_str).appendTo(tr);
                        var td4 = $('<td>').html(metaJsonArr.persons[i].person_info.behavior).appendTo(tr);
                        var td5 = $('<td>').appendTo(tr);

                        var emotion_arr = metaJsonArr.persons[i].person_info.emotion;
                        //var emotion = arrayFaceData[i].person_info.emotion;

                        var emotion = '';
                        for(var a in emotion_arr){
                            if(emotion_arr[a] == 10){
                                emotion = a;
                                break;
                            }
                        }

                        // if(emotion == ''){
                        //     emotion = emotion_arr;
                        // }

                        td5.html(emotion);

                        // for(var a in metaJsonArr.persons[i].person_info.emotion){
                        //     if(metaJsonArr.persons[i].person_info.emotion[a] == 10){
                        //         td5.html(a);
                        //     }
                        // }

                        var td4 = $('<td>').html(metaJsonArr.persons[i].person_info.predicate).appendTo(tr);    // 서술어

                        var related_objects_list = '';
                        for(var j=0;j<metaJsonArr.persons[i].related_objects.length;j++){
                            if(j > 0){
                                related_objects_list += " || ";
                            }
                            related_objects_list += metaJsonArr.persons[i].related_objects[j].related_object_id;
                        }
                        var td7 = $('<td>').html(related_objects_list).appendTo(tr);
                    }

                }

                if(metaJsonArr.objects){
                    var li1 = $('<li>').appendTo(obj1);
                    var a1 = $('<a>',{'class':'nav-item nav-link', id:'nav-tab-obj-0'})
                        .attr({'data-toggle':'tab', 'href': '#nav-obj-0', role: 'tab', 'area-controls': 'nav-obj-0','aria-selected':true}).html(metaJsonArr.image_id===undefined?metaJsonArr.frame_id:metaJsonArr.image_id+' / 객체').appendTo(li1);

                    var div1 = $('<div>',{'class':'tab-pane fade', id:'nav-obj-0'}).attr({role: 'tabpanel', 'aria-labelledby': 'nav-obj-0'}).appendTo(obj2);
                    var table1 = $('<table>', {'class':'table table-striped'}).appendTo(div1);
                    var thead1 = $('<thead>').appendTo(table1);
                    var tr1 = $('<tr>').appendTo(thead1);

                    var th_list = ['객체명','좌표','구분'];
                    for(var i=0;i<th_list.length;i++){
                        var th = $('<th>').html(th_list[i]).attr({scope: 'col'}).appendTo(tr1);
                    }

                    var tbody1 = $('<tbody>').appendTo(table1);

                    for(var i=0;i<metaJsonArr.persons.length;i++){
                        for(var j=0;j<metaJsonArr.persons[i].related_objects.length;j++){
                            if(j > 0){
                                related_objects_list += " || ";
                            }
                            related_objects_list += metaJsonArr.persons[i].related_objects[j].related_object_id;

                            var tr = $('<tr>').appendTo(tbody1);
                            var td1 = $('<td>').html(metaJsonArr.persons[i].related_objects[j].related_object_id).appendTo(tr);

                            var related_object_rect = metaJsonArr.persons[i].related_objects[j].related_object_rect;
                            var related_object_rect_str = related_object_rect.min_x+','+related_object_rect.min_y+','+related_object_rect.max_x+','+related_object_rect.max_y;

                            var td2 = $('<td>').html(related_object_rect_str).appendTo(tr);
                            var td3 = $('<td>').html('(관련객체)').appendTo(tr);
                        }

                    }
                    for(var i=0;i<metaJsonArr.objects.length;i++){
                        var tr = $('<tr>').appendTo(tbody1);
                        var td1 = $('<td>').html(metaJsonArr.objects[i].object_id).appendTo(tr);

                        var object_rect = metaJsonArr.objects[i].object_rect;
                        var object_rect_str = object_rect.min_x+','+object_rect.min_y+','+object_rect.max_x+','+object_rect.max_y;


                        var td2 = $('<td>').html(object_rect_str).appendTo(tr);
                        var td3 = $('<td>').html('(일반객체)').appendTo(tr);
                    }
                }
            }
        }
        catch (e) {

        }

    });
</script>