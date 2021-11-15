<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../includes/taglib.jsp" %>
<c:import url="../includes/header.jsp"/>
<c:import url="./_section_header.jsp"/>

<!-- page content -->
<div class="container section" role="main">

    <!-- 카드 컨텐츠 시작 -->
   <%-- <div class="row">
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
    <div class="row">--%>

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
                        <tbody  id="qaSectionList"></tbody>
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
                            <input type="hidden" id="currentTime" name="currentTime"/>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row" id="imgShot">
                <div class="img-shot-title x_panel">
                    <div class="x_title">
                        <h2>구간 편집<small> Section edit</small></h2>
                        <div class="clearfix"></div>
                    </div>
                    <div class="x_content btn-group" id="repreImgView">
                        <div>
                            <button class="btn btn-success" onclick="appendSection()">신규구간 생성</button>
                            <button class="btn btn-success" onclick="setStartSection()">시작구간 설정</button>
                            <button class="btn btn-success" onclick="setEndSection()">종료구간 설정</button>
                            <button class="btn btn-success" onclick="deleteSection()">구간정보 삭제</button>
                            <button class="btn btn-success" onclick="putSection()">저장</button>
                        </div>
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
<script>
    var _rate = ${rate};
    var _startsec = 0;
    var _endsec = 0;

     /*qa구간 선택*/
    function setQaSectionInfo(tr){
        if(myPlayer1!=null) {
            myPlayer1.pause();
        }
        var $tr = $(tr);
        $('.table tbody tr[name="qaSecTr"]').removeClass('ui-selected');
        $tr.addClass('ui-selected');
        _startsec = $tr.find("input[name=startsec]").val();
        _endsec = $tr.find("input[name=endsec]").val();
        if($tr.find("input[name=sectionid]").val()==''){
            // _endsec
        }
        if(_startsec!=_endsec&&myPlayer1!=null) {
            myPlayer1.currentTime(_startsec);
        }
    }

    /*시각구간 설정*/
    function setStartSection(){
        var $data = $("#sectionList tr.ui-selected");
        var $target = $("#qaSectionList tr.ui-selected");

        var shotid = $data.find("input[name=shotid]").val();
        var isSet = chkSection(shotid);
        if(!isSet){
            return false;
        }

        var startSection = $data.find("th").text().trim().split('구간');
        var text = $data.find("td").text().trim().split("~");

        $target.find("th").text(startSection[0]+"-");
        $target.find("td").text(text[0]+"~");
        $target.find("input[name=startsec]").val($data.find("input[name=startsec]").val());
        $target.find("input[name=startshotid]").val(shotid);
        $target.find("input[name=endsec]").val("");
        $target.find("input[name=endshotid]").val("");
        myPlayer1.play();
    }

    /*종료구간 설정*/
    function setEndSection(){
        var $data = $("#sectionList tr.ui-selected");
        var $target = $("#qaSectionList tr.ui-selected");

        var shotid = $data.find("input[name=shotid]").val();
        var isSet = chkSection(shotid);
        if(!isSet){
            return false;
        }

        var endSection = $data.find("th").text().trim().split('구간');
        var text = $data.find("td").text().trim().split("~");

        $target.find("th").text($target.find("th").text().trim().split("-")[0]+"-"+endSection[0]);
        $target.find("td").text($target.find("td").text().trim().split("~")[0]+"~"+text[1]);
        $target.find("input[name=endsec]").val($data.find("input[name=endsec]").val());
        $target.find("input[name=endshotid]").val($data.find("input[name=shotid]").val());
        $target.find("input[name=sectionname]").val( $target.find("th").text().trim());

        myPlayer1.pause();
    }
    function chkSection(shotid){
        var flag = true;
        $("#qaSectionList tr").each(function(){
            var startshotid = $(this).find("input[name=startshotid]").val();
            var endshotid = $(this).find("input[name=endshotid]").val();
            if(!$(this).hasClass("ui-selected")){
                if(endshotid!=null && endshotid!="") {
                    if (shotid >= startshotid && shotid <= endshotid) {
                        MSG.alert("이미 지정된 구간입니다.");
                        flag = false;
                        return false;
                    }
                }
            }
        });
        return flag;
    }

    /*신규구간 생성*/
    function appendSection(){
        var $tr = $("#qaSectionTr_new").clone();
        $tr.attr("id","");
        $tr.show();
        if($("#qaSectionList .ui-selected").length>0){
            $("#qaSectionList .ui-selected").after($tr);
        }else{
            $("#qaSectionList").append($tr);
        }
        $("#qaSectionList tr").removeClass("ui-selected");
        $tr.addClass("ui-selected");
    }

    /*구간 반복*/
    function replay(){
        _startsec = $("#qaSectionList .ui-selected").find("input[name=startsec]").val();
        _endsec = $("#qaSectionList .ui-selected").find("input[name=endsec]").val();
        myPlayer1.currentTime(_startsec);
        myPlayer1.play();
    }
    /*구간 삭제*/
    function deleteSection(){
        var $this = $("#qaSectionList .ui-selected");
        var delflag = $this.find("input[name=delflag]").val();
        $this.toggleClass("delete");
        if(delflag =='false') {
            $this.find("input[name=delflag]").val("true");
        }else{
            $this.find("input[name=delflag]").val("false");
        }
    }

    /*저장*/
    function putSection(){
        $("#qaSectionTr_new").remove();
        $.ajax({
            url: '<c:url value="/section/putQaSectionList"/>',
            type: 'POST',
            data: $("#qaSectionForm").serializeArray(),
            async: false,
            dataType: 'html',
            // contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            success: function (html) {
                $div = html;
                $("#qaSectionList").html($div);
                MSG.alert("QA구간이 저장되었습니다.");
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
            var nowsec = _player.currentTime()+0.1;
            $("#sectionList tr").each(function(){
                var startsec = $(this).find("input[name=startsec]").val();
                var endsec = $(this).find("input[name=endsec]").val();
                if(nowsec>startsec && nowsec<=endsec){
                    $("#sectionList tr").removeClass('ui-selected');
                    $(this).addClass('ui-selected');
                    document.getElementById("section-list").scrollTop = $(this).position().top-40;
                    return false;
                }
            });
            if(_startsec!=_endsec){
                if(nowsec>=_endsec-0.2){
                    _player.pause();
                    _startsec=0;
                    _endsec=0;
                }
            }
        });

        getSectionList(<c:out value="${idx}"/>); // 리스트 idx 넘겨받음.(ex) 624
        getQaSectionList(<c:out value="${idx}"/>); // 리스트 idx 넘겨받음.(ex) 624
        resizeVideo();
        //단축키
        proceedHotkey();
        $(this).bind('keydown.ctrl_[',function (e){ /* ctrl + [ = 시작구간 설정 */
            setStartSection();
            return false;
        });
        $(this).bind('keydown.ctrl_]',function (e){ /* ctrl + ] = 종료구간 설정 */
            setEndSection();
            return false;
        });
        $(this).bind('keydown.ctrl_n',function (e){ /* ctrl + ] = 신규구간 생성 */
            appendSection();
            return false;
        });
        $(this).bind('keydown.ctrl_Space',function (e){ /* ctrl + ] = 구간 반복 */
            replay();
            return false;
        });
    });
</script>
<c:import url="./_section_script.jsp"/>
<c:import url="../includes/footer.jsp"/>