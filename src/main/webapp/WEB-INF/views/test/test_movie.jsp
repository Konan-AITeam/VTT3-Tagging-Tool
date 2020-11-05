<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../includes/taglib.jsp" %>
<c:import url="../includes/header.jsp"/>
<c:import url="../includes/script.jsp"/>
<div class="container-fluid">
    <%--row : ribbon--%>
    <div class="row">
        <div class="col-12">
            <!-- RIBBON -->
            <span class="item"></span>
            <span class="ribbon-button-alignment pull-right" style="margin-right:25px">
            <span id="btn_edit_toggle" class="btn btn-ribbon hidden-xs" data-target=".multi-collapse" data-toggle="collapse" aria-expanded="true" aria-controls=".multi-collapse"><i class="fa fa-file-picture-o "></i> 시각정보 편집(확대/축소)</span>
            <span id="btn_delete" class="btn btn-ribbon hidden-xs" data-title="add"><i class="fa fa-trash-o txt-color-customRed"></i> 삭제</span>
            <span id="btn_storyboard" class="btn btn-ribbon hidden-xs" data-title="add"><i class="fa fa-picture-o txt-color-orange"></i> 스토리보드</span>
            <span id="btn_play" class="btn btn-ribbon hidden-xs" data-title="add"><i class="fa fa-youtube-play txt-color-orange"></i> 객체인식재생</span>
            </span>
        </div>
    </div>
    <div class="row">
        <div id="sectionList" class="collapse multi-collapse show col-2">
            <div class="card">
                <div class="card-body">
                    <h6 class="card-title">구간 리스트</h6>
                    <div class="" style="height:600px; overflow-y: scroll;" id="selectList">
                        <table class="table table-hover">
                            <thead>
                            <tr>
                                <th scope="col">
                                    <small>구간</small>
                                </th>
                                <th scope="col">
                                    <small>구간범위</small>
                                </th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="result" items="${sectionList}" varStatus="i">
                                <tr>
                                    <td>
                                        <small><a href="javascript:getSectionShotList('${result.videoid}','${result.shotid}','<c:out value="${result.assetfilepath}"/>', '<c:out value="${result.assetfilename}"/>');"><c:out value="${i.count}"/>구간</a></small>
                                    </td>
                                    <td>
                                        <small>${result.starttimecode}~${result.endtimecode}</small>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <div id="sectionList" class="collapse multi-collapse show col-2">
            <div class="card">
                <div class="card-body">
                    <h6 class="card-title">구간 리스트</h6>
                    <div class="" style="height:600px; overflow-y: scroll;" id="selectList">
                        <table class="table table-hover">
                            <thead>
                            <tr>
                                <th scope="col">
                                    <small>구간</small>
                                </th>
                                <th scope="col">
                                    <small>구간범위</small>
                                </th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="result" items="${sectionList}" varStatus="i">
                                <tr>
                                    <td>
                                        <small><a href="javascript:getSectionShotList('${result.videoid}','${result.shotid}','<c:out value="${result.assetfilepath}"/>', '<c:out value="${result.assetfilename}"/>');"><c:out value="${i.count}"/>구간</a></small>
                                    </td>
                                    <td>
                                        <small>${result.starttimecode}~${result.endtimecode}</small>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <div id="list2" class="collapse multi-collapse show col-8">
            <div class="card">
                <div class="card-body">
                    <h6 class="card-title">구간 리스트</h6>
                    <div class="videobox" style="height:600px;">
                        <video id="videojs" class="video-js vjs-default-skin vjs-big-play-centered" controls preload="auto" data-setup='{ "playbackRates" : [0.5, 1.0, 1.5, 2.0] }' style="width: 100%; height: 100%;"
                               poster src = "http://10.10.30.21:7070/darc4/video/2018/07/30/638/OV201800000638.mp4"></video>
                        <div class="select-video">원본</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    $(document).ready(function() {
        playerSet();
    });

    var myPlayer =  videojs('#videojs');

    function playerSet(){
        // videojs
        //player.init("videojs", "video", "${ videoServerUrl }/${ itemResponse.item.assetfilepath }/${ itemResponse.item.assetfilename}", "");
        player.init("videojs", "video", "http://10.10.30.21:7070/darc4/video/2018/07/30/638/OV201800000638.mp4", "");
        videojs("videojs").on('timeupdate',getTimeBiz);
    }


    function getTimeBiz() {
            console.log("myPlayer.currentTime()", time2Code('video',videojs("videojs").currentTime(),29.97));
    }
</script>
<c:import url="../includes/footer.jsp"/>

