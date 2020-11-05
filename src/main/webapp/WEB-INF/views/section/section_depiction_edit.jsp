<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../includes/taglib.jsp" %>
<c:import url="../includes/header.jsp"/>
<c:import url="./_section_header.jsp"/>

<!-- page content -->
<div class="container section" role="main">

    <!-- 카드 컨텐츠 시작 -->
    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12 btn-group">
            <div class="x_panel">
                <!--
                <button class="btn btn-success btn-move" id="info">구간 편집</button>
                <button class="btn btn-success btn-move" id="depiction">구간 묘사</button>
                <button class="btn btn-success btn-move" id="qa">QA</button>
                <button class="btn btn-success btn-move" id="relation">인과/의도</button>
                -->
            </div>
        </div>
    </div>
    <div class="row">

        <div class="section-list-Wrap">
            <div id="section-list" class="x_panel section-list">
                <div class="x_title">
                    <h2> 구간 리스트   <small> Section list </small></h2>
                    <div class="clearfix"></div>
                </div>
                <div class="x_content" id="sectionList"> </div>
            </div>
        </div>

        <div class="section-list-Wrap">
            <div id="qaSection-list" class="x_panel section-list">
                <div class="x_title">
                    <h2> QA 구간 리스트 <small> QA Section list </small></h2>
                    <div class="clearfix"></div>
                </div>
                <div class="x_content">
                    <form id="qaSectionForm">
                        <input type="hidden" name="idx" value="${idx}"/>
                        <input type="hidden" name="rate" value="${rate}"/>
                        <table class="table table-striped">
                            <thead>
                            <tr>
                                <th>구간 </th>
                                <th>구간 범위 </th>
                            </tr>
                            </thead>
                            <tbody  id="qaSectionList">
                            </tbody>
                        </table>
                    </form>
                </div>
            </div>
        </div>

        <div class="section-list-Wrap" style="width:calc(100% - 620px)">
            <div class="row">
                <div class="x_panel">
                    <div class="x_title">
                        <h2>영상<small>Video</small></h2>
                        <ul class="nav navbar-right panel_toolbox">
                            <li>
                                <a class="table-btn" onclick="help_hotkey()">
                                    <i class="fas fa-keyboard"></i> 단축키
                                </a>
                            </li>
                        </ul>
                        <div class="clearfix"></div>
                    </div>
                    <div class="x_content" id="scrollImgDiv">
                        <div class="col-xs-6 col-sm-6 col-md-6 col-lg-6 videobox">
                            <video id="videojs" class="video-js vjs-default-skin vjs-big-play-centered" controls preload="auto" data-setup='{ "playbackRates" : [0.5, 1.0, 1.5, 2.0] }' style="width: 100%; height: 100%;"></video>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row" id="imgShot">
                <div class="img-shot-title x_panel">
                    <div class="x_title">
                        <h2>구간 묘사<small> Section description</small></h2>
                        <ul class="nav navbar-right panel_toolbox">
                            <li>
                                <a id="allObjView" class="table-btn" onclick="putDepiction()">
                                    <i class="fas fa-file"></i> 저장
                                </a>
                            </li>
                        </ul>
                        <div class="clearfix"></div>
                    </div>
                    <div class="x_content" id="repreImgView">
                        <form class="needs-validation" id="qaForm" name="qaForm">
                        </form>
                    </div>
                </div>
            </div>
        </div>

    </div>
    <!-- row end -->
</div>
<!-- /page content -->
<!-- END #MAIN PANEL -->
<c:import url="../includes/script.jsp"/>
<c:import url="./_section_script.jsp"/>
<script>
    var _rate = ${rate};
    var _startsec = 0;
    var _endsec = 0;

    /*qa구간 선택*/
    function setQaSectionInfo(tr){
        if(myPlayer1!=null){
            myPlayer1.pause();
        }
        var $tr = $(tr);
        var hasClass =$tr.hasClass('ui-selected');
        $('.table tbody tr[name="qaSecTr"]').removeClass('ui-selected');
        if(!hasClass){
            $tr.addClass('ui-selected');
            _startsec = $tr.find("input[name=startsec]").val();
            _endsec = $tr.find("input[name=endsec]").val();
            if(myPlayer1!=null){
                myPlayer1.currentTime(_startsec);
            }
        }else{
            _startsec=0;
            _endsec=0;
        }

        getDepictionList($tr.find("input[name=sectionid]").val());
    }

    /*묘사조회*/
    function getDepictionList(sectionid){
        if(sectionid==null||sectionid==''){
            MSG.alert("getDepictionList </br> 생성된 QA구간이 없습니다.");
            return;
        }
        $.ajax({
            url: '<c:url value="/section/getDepictionList"/>',
            type: 'POST',
            data: {"sectionid":sectionid},
            async: false,
            dataType: 'html',
            // contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            success: function (html) {
                $div = html;
                $("#qaForm").html($div);
            },
            error: function (xhr, ajaxOptions, thrownError) {
                MSG.alert("putQaSectionList </br> status:"+xhr.status+"</br> message:"+xhr.responseText);
            }
        });
    }

    /*저장*/
    function putDepiction(){
        $.ajax({
            url: '<c:url value="/section/putDepictionList"/>',
            type: 'POST',
            data: $("#qaForm").serializeArray(),
            async: false,
            dataType: 'html',
            // contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            success: function (html) {
                $div = html;
                $("#qaForm").html($div);
                MSG.alert("구간묘사가 저장되었습니다.");
            },
            error: function (xhr, ajaxOptions, thrownError) {
                MSG.alert("putQaSectionList </br> status:"+xhr.status+"</br> message:"+xhr.responseText);
            }
        });
    }

    $(document).ready(function() {
        // videojs
        var _player = player.init("videojs", "video", "${ videoServerUrl }/${ contentField.assetfilepath }/${ contentField.assetfilename}", "");
        _player.on('timeupdate', function(){
            var nowsec = _player.currentTime()+0.02;
            $("#sectionList tr").each(function(){
                var startsec = $(this).find("input[name=startsec]").val();
                var endsec = $(this).find("input[name=endsec]").val();
                if(nowsec>=startsec && nowsec<endsec){
                    $("#sectionList tr").removeClass('ui-selected');
                    $(this).addClass('ui-selected');
                    document.getElementById("section-list").scrollTop = $(this).position().top-40;
                    return false;
                }
            });
            $("#qaSectionList tr").each(function(){
                var startsec = $(this).find("input[name=startsec]").val();
                var endsec = $(this).find("input[name=endsec]").val();
                if(nowsec>=startsec && nowsec<endsec){
                    $("#qaSectionList tr").removeClass('ui-selected');
                    $(this).addClass('ui-selected');
                    document.getElementById("qaSection-list").scrollTop = $(this).position().top-40;
                    return false;
                }
            });
            if(_startsec!=_endsec){
                if(nowsec>=_endsec-0.2){
                    _player.pause();
                }
            }
        });
        _player.on("play",function(){
            _player.currentTime(_startsec);
        });

        getSectionList(<c:out value="${idx}"/>); // 리스트 idx 넘겨받음.(ex) 624
        getQaSectionList(<c:out value="${idx}"/>); // 리스트 idx 넘겨받음.(ex) 624
        resizeVideo();
        proceedHotkey();
    });
</script>
<c:import url="../includes/footer.jsp"/>