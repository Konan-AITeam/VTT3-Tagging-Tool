<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../includes/taglib.jsp" %>
<c:import url="../includes/header.jsp"/>
<!-- top navigation -->
<div class="top_nav">
    <div class="nav_menu nav_menu-j">
        <nav>
            <div class="col-md-5 col-8 align-self-center">
                <div class="title-txt text-themecolor">
                    CONTENT
                </div>
                <ol class="breadcrumb">
                    <li class="breadcrumb-item">
                        <a href="javascript:void(0)"> 콘텐츠</a>
                    </li>
                    <li class="breadcrumb-item active">

                        스토리 보드
                    </li>

                </ol>
            </div>
        </nav>
    </div>
</div>
<!-- /top navigation -->


<!-- page content -->
<div class="container" role="main">
    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="col-12">
                    <div id="ribbon">
                <span class="ribbon-button-alignment pull-right" style="margin-right:25px">
                    <span class="left" style="margin-top: -5px;">
                    <form class="smart-form" id="search_form" onsubmit="return false;">
                        <section>
                            <label class="input">
                                <i class="icon-append fa fa-close txt-color-customSky" id="btn_eraser"></i>
                                <i class="icon-append fa fa-search txt-color-customSky" id="btn_search"></i>
                                <input type="text" placeholder="Search" id="keyword" onkeypress='if(event.keyCode == 13) { $("#btn_search").click(); }'>
                            </label>
                        </section>
                    </form>
                    </span>

                    <div class="btn-group btn_menu" id="btn_detect" style="padding-left:15px;font-size:12px;">
                        <input type="radio" name="btn_show_target" value="object"/> 사물
                        <input type="radio" name="btn_show_target" value="place"/> 장소
                        <input type="radio" name="btn_show_target" value="face"/> 인물
                        <input type="radio" name="btn_show_target" value=""/> 없음
                    </div>
                </span>
                    </div>
                </div>

                <div class="x_content superbox">
                    <article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                        <c:forEach var="shot" items="${listResponse.list}" varStatus="i">

                            <c:set var="thumbnail" value="${ shotServerUrl }/${ shot.assetfilepath }${ shot.assetfilename }"/>

                            <div class="storyboard_data superbox-list">
                                <input type="hidden" name="sequence" value="${ i.count }" />
                                <input type="hidden" name="shotid" value="${ shot.shotid }" />
                                <input type="hidden" name="videoid" value="${ shot.videoid }" />
                                <input type="hidden" name="starttimecode" value="${ shot.starttimecode }" />
                                <input type="hidden" name="thumbnail" value="${ thumbnail }" />
                                <input type="hidden" name="content" value="<c:out value="${ shot.content }" escapeXml="true" />" />
                                <input type="hidden" name="object" value="<c:out value="${ shot.object }" escapeXml="true" />" />
                                <img src="${ thumbnail }" data-img="${thumbnail}" class="superbox-img center">
                                <span class="timecode"><c:out value="${ shot.starttimecode }"/></span>

                                <c:set var="detectLocation1" value=""/>
                                <c:set var="detectAccuracy1" value=""/>
                                <c:set var="detectObject1" value=""/>

                                <c:set var="detectLocation2" value=""/>
                                <c:set var="detectAccuracy2" value=""/>
                                <c:set var="detectObject2" value=""/>

                                <c:set var="detectLocation3" value=""/>
                                <c:set var="detectAccuracy3" value=""/>
                                <c:set var="detectObject3" value=""/>

                                <c:set var="detectInfo" value="${shot.detect.objectData}"/>
                                <c:forEach var="detect" items="${detectInfo}" varStatus="j">
                                    <c:set var="detectLocation1" value="${j.first ? '' : detectLocation1}${j.first ? '' : ','}${detect.location}" />
                                    <c:set var="detectAccuracy1" value="${j.first ? '' : detectAccuracy1}${j.first ? '' : ','}${detect.info.accuracy}" />
                                    <c:set var="detectObject1" value="${j.first ? '' : detectObject1}${j.first ? '' : ','}${detect.info.objectName}" />
                                </c:forEach>
                                <input type="hidden" name="oLocation" value="${ detectLocation1 }"/>
                                <input type="hidden" name="oAccuracy" value="${ detectAccuracy1 }"/>
                                <input type="hidden" name="oObject" value="${ detectObject1 }"/>


                                <c:set var="detectInfo" value="${shot.detect.placeData}"/>
                                <c:forEach var="detect" items="${detectInfo}" varStatus="j">
                                    <c:set var="detectLocation2" value="${j.first ? '' : detectLocation2}${j.first ? '' : ','}${detect.location}" />
                                    <c:set var="detectAccuracy2" value="${j.first ? '' : detectAccuracy2}${j.first ? '' : ','}${detect.info.accuracy}" />
                                    <c:set var="detectObject2" value="${j.first ? '' : detectObject2}${j.first ? '' : ','}${detect.info.objectName}" />
                                </c:forEach>
                                <input type="hidden" name="pLocation" value="${ detectLocation2 }"/>
                                <input type="hidden" name="pAccuracy" value="${ detectAccuracy2 }"/>
                                <input type="hidden" name="pObject" value="${ detectObject2 }"/>


                                <c:set var="detectInfo" value="${shot.detect.faceData}"/>
                                <c:forEach var="detect" items="${detectInfo}" varStatus="j">
                                    <c:set var="detectLocation3" value="${j.first ? '' : detectLocation3}${j.first ? '' : ','}${detect.location}" />
                                    <c:set var="detectAccuracy3" value="${j.first ? '' : detectAccuracy3}${j.first ? '' : ','}${detect.info.accuracy}" />
                                    <c:set var="detectObject3" value="${j.first ? '' : detectObject3}${j.first ? '' : ','}${detect.info.objectName}" />
                                </c:forEach>
                                <input type="hidden" name="fLocation" value="${ detectLocation3 }"/>
                                <input type="hidden" name="fAccuracy" value="${ detectAccuracy3 }"/>
                                <input type="hidden" name="fObject" value="${ detectObject3 }"/>


                            </div>

                        </c:forEach>
                    </article>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- /page content -->

</div>
<!--/main_container  -->
</div>
<!--/container body  -->

<c:import url="../includes/script.jsp"/>
<script>
    function resize_thumbnail() {
        var layer_width = $(".superbox article").width() - 6;
        var box_width = 210;
        var col = parseInt(layer_width / box_width);
        var ch = (layer_width - (box_width * col)) / col;
        $( ".superbox-list" ).css( "margin-right", ch + "px" );
    }

    $(document).resize(function() {
        resize_thumbnail();
    });

    resize_thumbnail();

    var shot_width = parseFloat("${itemResponse.item.shotWidth}");

    function svgDraw(keyword) {

        $(".superbox-show").hide();

        var detectType = $("input:radio[name=btn_show_target]:checked").val();
        keyword = keyword.toLowerCase();

        var colors1 = ["#F5B7B1", "#D7BDE2", "#A9CCE3", "#A3E4D7", "#A9DFBF","#F9E79F", "#F5CBA7" ];
        var colors2 = ["#CD6155", "#9B59B6", "#2980B9", "#1ABC9C", "#27AE60","#F1C40F", "#E67E22" ];

        $(".superbox-list").each( function() {
            var target = $(this);

            var detectLocation = "";
            var detectAccuracy = "";
            var detectObject = "";

            if(detectType == "object") {
                detectLocation = $(this).find("input[name=oLocation]").val();
                detectAccuracy = $(this).find("input[name=oAccuracy]").val();
                detectObject = $(this).find("input[name=oObject]").val();

            } else if(detectType == "face") {
                detectLocation = $(this).find("input[name=fLocation]").val();
                detectAccuracy = $(this).find("input[name=fAccuracy]").val();
                detectObject = $(this).find("input[name=fObject]").val();
            } else if(detectType == "place") {
                detectLocation = $(this).find("input[name=pLocation]").val();
                detectAccuracy = $(this).find("input[name=pAccuracy]").val();
                detectObject = $(this).find("input[name=pObject]").val();
            }

            var arrLocation = detectLocation.split(',');
            var arrAccuracy = detectAccuracy.split(',');
            var arrObject = detectObject.split(',');

            target.svg();
            var svg = target.svg("get");
            svg.clear();

            var rate = 200.0 / shot_width;

            var count = 0;
            if(detectType == "place") {
                $.each(arrObject, function(index, val) {
                    var found = true;
                    if (keyword != null && keyword.length > 0) {
                        if(arrObject[index].toLowerCase().indexOf(keyword) > -1) {
                            found = true;
                        } else {
                            found = false;
                        }
                    }
                    if(val != null && val != '' && val.length != 0 && found) {
                        var color = colors2[index % colors2.length];
                        svg.text(5, 15 + (count * 15), val + " [" + arrAccuracy[index] + "]",{id: 'Text', fontFamily: 'Nanum Gothic', fontSize: 14, fill: color, 'font-weight': 'bold' });
                        count++;
                    }

                });
            } else {
                $.each(arrLocation, function(index, val) {
                    var found = true;
                    if (keyword != null && keyword.length > 0) {
                        if(arrObject[index].toLowerCase().indexOf(keyword) > -1) {
                            found = true;
                        } else {
                            found = false;
                        }
                    }
                    if(val != null && val != '' && val.length != 0 && found) {
                        var arr  = val.split(' ');
                        var color1 = colors1[index % colors1.length];
                        var color2 = colors2[index % colors2.length];
                        svg.rect(parseFloat(arr[0]) * rate, parseFloat(arr[1]) * rate, parseFloat(arr[2]) * rate, parseFloat(arr[3]) * rate , { fill: 'none', stroke: color1, strokeWidth: 2 });
                        svg.text(parseFloat(arr[0]) * rate, parseFloat(arr[1]) * rate + 12, arrObject[index] + " [" + arrAccuracy[index] + "]",{id: 'Text', fontFamily: 'Nanum Gothic', fontSize: 14, fill: color2, 'font-weight': 'bold' });


                    }
                });
            }

        });
    }

    $("#btn_search").on("click", function() {
        var keyword = $("#keyword").val();
        svgDraw(keyword);
        $(".superbox-show").hide();
    });

    $(document).ready(function() {
        $('.superbox').SuperBox();


        $("#btn_detect input:radio[name=btn_show_target]").change(function() {
            var keyword = $("#keyword").val();
            svgDraw(keyword);
        });

        $("input:radio[name=btn_show_target]:first").attr("checked", true);
        var keyword = $("#keyword").val();
        svgDraw(keyword);

    });

</script>
<c:import url="../includes/footer.jsp"/>