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
                        객체인식 재생
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
                <div id="ribbon">
                    <span >
                        <span id="elapsed_time" class="btn btn-ribbon hidden-xs">Press play for HTML5</span>
                        <span id="currentTime" class="btn btn-ribbon hidden-xs">currentTime</span>
                        <span class="left" style="padding-right:10px;">
                            <form class="smart-form" id="search_form" onsubmit="return false;" >
                                <section>
                                    <label class="input">
                                        <i class="icon-append fa fa-close txt-color-customSky" id="btn_eraser"></i>
                                        <input type="text" placeholder="Search" id="keyword" >
                                    </label>
                                </section>
                            </form>
                        </span>
                    </span>
                </div>

                <div class="x_content" id = "content">
                    <div class="row">
                        <div class="col-xs-6 col-sm-6 col-md-6 col-lg-6 videobox">
                            <video id="videojs" class="video-js vjs-default-skin vjs-big-play-centered" controls preload="auto" data-setup='{ "playbackRates" : [0.5, 1.0, 1.5, 2.0] }' style="width: 100%; height: 100%;"></video>
                            <div class="select-video">원본</div>
                        </div>
                        <div class="col-xs-6 col-sm-6 col-md-6 col-lg-6 videobox" >
                            <div class="videobox-layout">
                                <canvas id="output1"></canvas>
                            </div>
                            <div class="select-video">얼굴</div>
                        </div>
                    </div>

                    <div class="row" style="padding-top:10px;">
                        <div class="col-xs-6 col-sm-6 col-md-6 col-lg-6 videobox" >
                            <div class="videobox-layout">
                                <canvas id="output2"></canvas>
                            </div>
                            <div class="select-video">사물</div>
                        </div>
                        <div class="col-xs-6 col-sm-6 col-md-6 col-lg-6 videobox" >
                            <div class="videobox-layout">
                                <canvas id="output3"></canvas>
                            </div>
                            <div class="select-video">장소</div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<c:import url="../includes/script.jsp"/>
<script>

    var colors1 = ["#F5B7B1", "#D7BDE2", "#A9CCE3", "#A3E4D7", "#A9DFBF","#F9E79F", "#F5CBA7" ];
    var colors2 = ["#F5B7B1", "#D7BDE2", "#A9CCE3", "#A3E4D7", "#A9DFBF","#F9E79F", "#F5CBA7" ];
    // // var colors2 = ["red","#CD6155", "#9B59B6", "#2980B9", "#1ABC9C", "#27AE60","#F1C40F", "#E67E22" ];

    var detectObject = {};
    var detectPlace = {};
    var detectFace= {};

    var objectKeys = [];

    //get detect data
    detectData();

    for (var key in detectObject) {
        objectKeys.push(key);
    }
    objectKeys.sort();
    objectKeys.reverse();

    var origin_width = parseFloat("${itemResponse.item.width}");
    var origin_height = parseFloat("${itemResponse.item.height}");
    var shot_width = parseFloat("${itemResponse.item.shotWidth}");

    $(window).resize(function() {
        resizeVideo();
    });

    // video resize
    resizeVideo();

    // videojs
    player.init("videojs", "video", "${ videoServerUrl }/${ itemResponse.item.assetfilepath }/${ itemResponse.item.assetfilename}", "");

    // Setup Video
    var video = document.querySelector('video');
    var time_dump = document.getElementById("elapsed_time");


    var $jCanvas1 = $('#output1');
    var $jCanvas2 = $('#output2');
    var $jCanvas3 = $('#output3');

    var ctx1 = $jCanvas1[0].getContext("2d");
    var ctx2 = $jCanvas2[0].getContext("2d");
    var ctx3 = $jCanvas3[0].getContext("2d");


    function html5glasses() {

        var keyword = $("#keyword").val().toLowerCase();

        // Start the clock
        var elapsed_time = (new Date()).getTime();

        // Draw the video to canvas
        ctx1.drawImage(video, 0, 0, origin_width, origin_height, 0, 0, $jCanvas1.width(), $jCanvas1.height());
        ctx2.drawImage(video, 0, 0, origin_width, origin_height, 0, 0, $jCanvas2.width(), $jCanvas2.height());
        ctx3.drawImage(video, 0, 0, origin_width, origin_height, 0, 0, $jCanvas3.width(), $jCanvas3.height());

        // Stop the clock
        time_dump.innerHTML = "Process time : " + ((new Date()).getTime() - elapsed_time).toString() + "ms";

        var currentTime = $("#currentTime").text();
        var sel = "00:00:00;00";

        for (var i = 0; i < objectKeys.length; i++) {
            var k = objectKeys[i];
            var sec = code2frame(k, 29.97);
            if(sec <= currentTime) {
                sel = k;
                break;spdlqj
            }
        }

        var ch = currentTime - sec;
        if(ch > 5) {
            return;
        }

        var arr, accuracy, objectName, color1, color2,  X, Y, WIDTH, HEIGHT;
        var rate = $jCanvas1.width() / shot_width;

        var fontSize = "14pt";
        var fontFamily = "AppleSDGothicNeo-Regular,'Malgun Gothic','맑은 고딕',dotum,'돋움',sans-serif";

        // face
        var arrLocation = detectFace[sel][0].split(',');
        var arrAccuracy = detectFace[sel][1].split(',');
        var arrObject = detectFace[sel][2].split(',');
        $.each(arrLocation, function(index, val) {
            color1 = colors1[index % colors1.length];
            color2 = colors2[index % colors2.length];

            accuracy = arrAccuracy[index];
            objectName = arrObject[index];

            if (objectName.toLowerCase().indexOf(keyword) == -1) {
                return true;
            }

            arr  = val.split(' ');
            X = parseFloat(arr[0]) * rate;
            Y = parseFloat(arr[1]) * rate;
            WIDTH = parseFloat(arr[2]) * rate;
            HEIGHT = parseFloat(arr[3]) * rate;

            $jCanvas1.drawRect({
                strokeStyle: color1,
                strokeWidth: 2,
                x: X , y: Y,
                fromCenter: false,
                width: WIDTH ,
                height: HEIGHT
            });

            $jCanvas1.drawText({
                fillStyle: color1,
                strokeStyle: color2,
                strokeWidth: 2,
                shadowColor: '#000',
                shadowBlur: 5,
                x: X + 3, y: Y + 3,
                fontSize: fontSize ,
                fontFamily: fontFamily,
                fromCenter: false,
                text: objectName + " [ " +  accuracy + " ]"
            });
        });

        // object
        var arrLocation = detectObject[sel][0].split(',');
        var arrAccuracy = detectObject[sel][1].split(',');
        var arrObject = detectObject[sel][2].split(',');
        $.each(arrLocation, function(index, val) {


            color1 = colors1[index % colors1.length];
            color2 = colors2[index % colors2.length];

            arr  = val.split(' ');
            accuracy = arrAccuracy[index];
            objectName = arrObject[index];

            if (objectName.toLowerCase().indexOf(keyword) == -1) {
                return true;
            }

            X = parseFloat(arr[0]) * rate;
            Y = parseFloat(arr[1]) * rate;
            WIDTH = parseFloat(arr[2]) * rate;
            HEIGHT = parseFloat(arr[3]) * rate;

            $jCanvas2.drawRect({
                strokeStyle: color1,
                strokeWidth: 2,
                x: X , y: Y,
                fromCenter: false,
                width: WIDTH ,
                height: HEIGHT
            });


            $jCanvas2.drawText({
                fillStyle: color1,
                strokeStyle: color2,
                strokeWidth: 2,
                shadowColor: '#000',
                shadowBlur: 5,
                x: X + 3, y: Y + 3,
                fontSize: fontSize,
                fontFamily: fontFamily,
                fromCenter: false,
                text: objectName + " [ " +  accuracy + " ]"
            });
        });

        // place
        var arrLocation = detectPlace[sel][0].split(',');
        var arrAccuracy = detectPlace[sel][1].split(',');
        var arrObject = detectPlace[sel][2].split(',');
        var count = 0;
        $.each(arrObject, function(index, val) {
            color1 = colors1[index % colors1.length];
            color2 = colors2[index % colors2.length];
            objectName = val;

            if (objectName.toLowerCase().indexOf(keyword) == -1) {
                return true;
            }

            accuracy = arrAccuracy[index];
            $jCanvas3.drawText({
                fillStyle: colors1[5],
                strokeStyle: colors2[5],
                strokeWidth: 2,
                shadowColor: '#000',
                shadowBlur: 5,
                x: 10, y: 10 + (count * 20.0),
                fontSize: '20pt' ,
                fontFamily: fontFamily,
                fromCenter: false,
                maxWidth: 300,
                text: objectName + " [ " +  accuracy + " ]"
            });
            count++;
        });


    }

    /* Events */
    var vidInterval;
    video.addEventListener('play', function() {
        vidInterval = setInterval(html5glasses,50);
    });

    video.addEventListener('ended', function() {
        clearInterval(vidInterval);
        time_dump.innerHTML = "finished";
    });

    function detectData() {

        <c:import url="detect.jsp"/>

    }


    function calculateCover(frame, sides) {
        var ratio = sides[1] / sides[0],
            cover = {
                width: frame.width,
                height: Math.ceil(frame.width * ratio)
            };
        console.log("ratio : "+ratio);
        console.log("Math : "+Math.ceil(frame.width * ratio));
        console.log("video w: "+cover.width);
        console.log("video h: "+cover.height);
        if (cover.height >= frame.height) {
            cover.height = frame.height;
            cover.width = Math.ceil(frame.height / ratio);
        }
        console.log("video w: "+cover.width);
        console.log("video h: "+cover.height);
        return cover;
    }

    function resizeVideo() {
        var content_width = $("#content").width() - 30;
        var content_height = $(window).height() - 250;
        console.log("content_width : "+$("#content").width());
        console.log("content_height : "+$(window).height());
        var w = content_width / 2;
        var h = content_height / 2;
        console.log("video w: "+w);
        console.log("video h: "+h);
        var calcSize = calculateCover({width: w, height: h}, [origin_width,origin_height]);
        $(".videobox").css("height",h);
        $(".videobox-layout").css("height",h);
        $("canvas").attr("width",calcSize.width ).attr("height",calcSize.height );
        $(".select-video").css("margin-top",h * -1);
        console.log("calcSize w: "+calcSize.width);
        console.log("calcSize h: "+calcSize.height);
    }


    $(document).ready(function() {
        //지우기
        $("#btn_eraser").on("click", function() {
            $("#keyword").val("");
        });
    });


</script>
<c:import url="../includes/footer.jsp"/>