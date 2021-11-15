<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../includes/taglib.jsp" %>
<c:import url="../includes/header.jsp"/>
<style type="text/css">
    .container.section .section-list-Wrap:not(:first-child){
        padding-left:10px;
    }
</style>
<!-- top navigation -->
<div class="top_nav">
    <div class="nav_menu nav_menu-j">
        <nav>
            <div class="col-8 align-self-center">
                <div class="title-txt text-themecolor">
                    CONTENT
                </div>
                <ol class="breadcrumb">
                    <li class="breadcrumb-item">
                        <a href="javascript:void(0)"> 콘텐츠</a>
                    </li>
                    <li class="breadcrumb-item ">
                        <a href="<c:url value="/content"/>"> 콘텐츠 리스트</a>
                    </li>
                    <li class="breadcrumb-item strong active">
                    <c:if test="${qachk}">
                        묘사 & Q&A 검증(${contentField.orifilename})
                    </c:if>
                    <c:if test="${!qachk}">
                        묘사 & Q&A 편집(${contentField.orifilename})
                    </c:if>
                    </li>
                </ol>
            </div>
        </nav>
    </div>
</div>

<!-- page content -->
<div class="container section" role="main">

    <!-- 카드 컨텐츠 시작 -->
    <%--<div class="row">
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
    </div>--%>
    <c:if test="${qachk}">
    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12 btn-group">
            <div class="x_panel">
                <button class="btn btn-success btnQaChk" onclick="qaChkSearch(this,'Who');">Who</button>
                <button class="btn btn-success btnQaChk" onclick="qaChkSearch(this,'When');">When</button>
                <button class="btn btn-success btnQaChk" onclick="qaChkSearch(this,'Where');">Where</button>
                <button class="btn btn-success btnQaChk" onclick="qaChkSearch(this,'What');">What</button>
                <button class="btn btn-success btnQaChk" onclick="qaChkSearch(this,'How');">How</button>
                <button class="btn btn-success btnQaChk" onclick="qaChkSearch(this,'Why');">Why</button>
            </div>
        </div>
    </div>
    </c:if>
    <div class="row">

        <div class="section-list-Wrap" style="width:400px;">
            <div id="qaSection-list" class="x_panel section-list">
                <div class="x_title">
                    <h2> Scene 리스트 <small> Scene list</small></h2>
                    <div class="clearfix"></div>
                </div>
                <div class="x_content">
                    <form id="qaSectionForm">
                        <input type="hidden" name="idx" value="${idx}"/>
                        <input type="hidden" name="rate" value="${rate}"/>
                        <input type="hidden" id="activeTab" value="qnaSceneTap1Div"/>
                        <table class="table table-striped">
                            <thead>
                            <tr>
                                <th>구간 </th>
                                <th>작성자</th>
                                <th>QA LV </th>
                            </tr>
                            </thead>
                            <tbody  id="qaChkSectionList">
                            </tbody>
                        </table>
                    </form>
                </div>
            </div>
        </div>

        <div class="section-list-Wrap"  style="width:400px;">
            <div id="section-list" class="x_panel section-list">
                <div class="x_title">
                    <h2> Shot 리스트<small> Shot list</small>   <%--<small> Section list </small>--%></h2>
                    <div class="clearfix"></div>
                </div>
                <div class="x_content" id="qaChkShotList"> </div>
            </div>
        </div>

        <div class="section-list-Wrap" style="width:calc(100% - 800px)">
            <div class="row">
                <div class="x_panel">
                    <div class="x_title">
                        <h2>영상<small>Video</small></h2>
                        <ul class="nav navbar-right panel_toolbox">
                            <li >
                                <a class="table-btn" onclick="help_hotkey()">
                                    <i class="fas fa-keyboard"></i> 단축키
                                </a>
                            </li>
                        </ul>
                        <div class="clearfix"></div>
                    </div>
                    <div class="x_content" id="scrollImgDiv">
                        <div class="col-xs-6 col-sm-6 col-md-6 col-lg-6 videobox">
                            <video id="videojs" class="video-js vjs-default-skin vjs-big-play-centered" controls preload="auto" data-setup='{ "playbackRates" : [0.5, 1.0, 1.5, 2.0] }' style="width: 100%; height: 100%;">
                                <%--<track label="English" kind="subtitles" srclang="en" src="https://vttfile.ezfinder.co.kr/darc4/video/2020/12/28/1802/OV202000001802.vtt" default>--%>
                            </video>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="img-shot-title x_panel">
                    <div class="x_title">
                        <div id="qaSceneTitle" class="qaTitle" style="display:none;">
                        <h2>Scene 묘사 QA 등록<small> Scene QA</small></h2>
                        <ul class="nav navbar-right panel_toolbox">
                            <li>
                                <a id="btnAllReset" class="table-btn" onclick="allReset();">
                                    <i class="fas fa-file"></i> 전체초기화
                                </a>
                            </li>
                            <li>
                                <a id="btnReset" class="table-btn" onclick="reset();">
                                    <i class="fas fa-file"></i> 초기화
                                </a>
                            </li>
                            <li>
                                <a id="btnGuideScene" class="table-btn" onclick="custModalPopup('section/guide/scene','guideModal');">
                                    <i class="fas fa-file"></i> 가이드라인
                                </a>
                            </li>
                            <li>
                                <a id="allObjView" class="table-btn" onclick="putQuestion()">
                                    <i class="fas fa-file"></i> 저장
                                </a>
                            </li>
                        </ul>
                        </div>
                        <div id="qaShotTitle" class="qaTitle" style="display:none;">
                            <h2 >Shot 묘사 QA 등록<small> Shot QA</small></h2>
                            <ul class="nav navbar-right panel_toolbox">
                                <li>
                                    <a id="btnAllReset" class="table-btn" onclick="allReset()">
                                        <i class="fas fa-file"></i> 전체초기화
                                    </a>
                                </li>

                                <li>
                                    <a id="btnReset" class="table-btn" onclick="reset()">
                                        <i class="fas fa-file"></i> 초기화
                                    </a>
                                </li>
                                <li>
                                    <a id="btnGuideShot" class="table-btn" onclick="custModalPopup('section/guide/shot','guideModal');">
                                        <i class="fas fa-file"></i> 가이드라인
                                    </a>
                                </li>
                                <li>
                                    <a id="allObjViewShot" class="table-btn" onclick="putShotQuestion()">
                                        <i class="fas fa-file"></i> 저장
                                    </a>
                                </li>
                            </ul>
                        </div>
                        <div class="clearfix"></div>
                    </div>
                    <div class="x_content" id="sceneEdit">
                        <div class="row fix" style="height:100%;">
                            <div class="x_content">
                                <form class="needs-validation" id="qaForm" name="qaForm">

                                </form>
                            </div>
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
<c:import url="./_section_script.jsp"/>

<script>

    var _pp_list = ['i','you', 'she','he', 'they'];
    var _rate = ${rate};
    var _startsec = 0;
    var _endsec = 0;

    String.prototype.capitalize = function() {
        return this.charAt(0).toUpperCase() + this.slice(1);
    }

    String.prototype.onlyEngNum = function () {
        return this.replace(/[^a-zA-Z0-9\s.,'"!~\-?]/gi, '');
    }

    function firstLetterUpperCase() {
        $(".form-control").off("keypress");
        $(".form-control").on("keypress",function (e) {
            $(this).val($(this).val().capitalize());
        });
    }
    function onlyEngNum() {
        $(".form-control").off("keyup");
        $(".form-control").on("keyup",function (e) {
            if (!(e.keyCode >= 37 && e.keyCode <= 40)) {
                var inputValue = $(this).val();
                $(this).val(inputValue.onlyEngNum());
            }
        });
    }
    function bindCheckErrorTyping() {
        $("input[type=text].form-control").off('blur');
        $("input[type=text].form-control").on('blur', function (e) {
            specialCharAfterSpace($(this));
            checkTypingError($(this));
        });
    }
    function tabChange() {
        $("#qaTabs > li").off('click');
        $("#qaTabs > li").on('click', function () {
            var isChk = false;
            $("#activeTab").val($(this).find('a').attr("id")+"Div");
            $("input[type=text]").each(function () {
                if($(this).attr("chkTyping") == 'chk'){
                   isChk = true;
                   return false;
               }
            });
            if(isChk){
                MSG.alert("오타검색이 종료되지 않았습니다.");
                return false;
            }
        });
    }
    /*qa구간 선택*/
    function setQaChkSectionInfo(tr,questionid){
        if(myPlayer1!=null){
            myPlayer1.pause();
        }
        var $tr = $(tr);
        var hasClass =$tr.hasClass('ui-selected');
        $('.table tbody tr[name="qaSecTr"]').removeClass('ui-selected');
        $tr.addClass('ui-selected');
        _startsec = $tr.find("input[name=startsec]").val();
        _endsec = $tr.find("input[name=endsec]").val();
        var qachkworkerid = $tr.find("input[name=qachkworkerid]").val();
        if(myPlayer1!=null){
            myPlayer1.currentTime(_startsec);
        }
        var sectionid = $tr.find("input[name=sectionid]").val();
        if(sectionid != null && sectionid != ""){
            getQaChkQuestionList(sectionid,qachkworkerid,questionid);
            //getSectionOfSceneList('<c:out value="${idx}"/>',sectionid);
        }else{
            if($("#qaChkShotListTbody tr").length > 0) {
                $("#qaChkShotListTbody tr:first").click();
            }else {
                MSG.alert("생성된 QA구간이 없습니다.");
                return;
            }
        }
    }

    /*QA조회*/
    function getQaChkQuestionList(sectionid,qachkworkerid,questionid){
        $(".qaTitle").hide();
        $("#qaSceneTitle").show();
        if(sectionid==null||sectionid==''){
            MSG.alert("생성된 QA구간이 없습니다.");
            return;
        }
        $("#workerId").val(qachkworkerid);

        $.ajax({
            url: '<c:url value="/section/getQaChkQuestionList"/>',
            type: 'POST',
            data: {"sectionid":sectionid, "workerId":qachkworkerid, "questionId":questionid},
            async: false,
            dataType: 'html',
            // contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            success: function (html) {
                $div = html;
                $("#qaForm").html($div);
            },
            error: function (xhr, ajaxOptions, thrownError) {
                MSG.alert("getQaChkQuestionList </br> status:"+xhr.status+"</br> message:"+xhr.responseText);
            }
        });
    }

    function getQaChkShotQuestionList(shotid,qachkworkerid,questionid){
        $(".qaTitle").hide();
        $("#qaShotTitle").show();
        if(shotid==null||shotid==''){
            MSG.alert("shot 정보가 없습니다.");
            return;
        }
        $.ajax({
            url: '<c:url value="/section/getQaChkShotQuestionList"/>',
            type: 'POST',
            data: {"shotid":shotid, "workerId":qachkworkerid, "questionId":questionid},
            async: false,
            dataType: 'html',
            // contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            success: function (html) {
                $div = html;
                $("#qaForm").html($div);
                bindCheckErrorTyping();
                onlyEngNum();
                firstLetterUpperCase();
                tabChange();
            },
            error: function (xhr, ajaxOptions, thrownError) {
                MSG.alert("getQaChkShotQuestionList </br> status:"+xhr.status+"</br> message:"+xhr.responseText);
            }
        });
    }

    function getShotQuestionList(shotid){
        $(".qaTitle").hide();
        $("#qaShotTitle").show();
        if(shotid==null||shotid==''){
            MSG.alert("shot 정보가 없습니다.");
            return;
        }
        $.ajax({
            url: '<c:url value="/section/getShotQuestionList"/>',
            type: 'POST',
            data: {"shotid":shotid, "workerId":$("#workerId").val()},
            async: false,
            dataType: 'html',
            // contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            success: function (html) {
                $div = html;
                $("#qaForm").html($div);
                bindCheckErrorTyping();
                onlyEngNum();
                firstLetterUpperCase();
                tabChange();
            },
            error: function (xhr, ajaxOptions, thrownError) {
                MSG.alert("getShotQuestionList </br> status:"+xhr.status+"</br> message:"+xhr.responseText);
            }
        });
    }
    /*저장*/
    function putQuestion(){
        var len = $("input[name=questionid]").length;
        var qaTitle = "";
        var cnt = 0;
        for(var i=0; i<len; i++){
            if($("input[name=questiontype]").eq(i).val() == "DESC" && $("input[name=question]").eq(i).val()!=""){
                /*if($("input[name=question]").eq(i).val()==""){
                    MSG.alert("묘사를 입력하여 주새요.");
                    return false;
                }*/
                if(!checkSpaces($("input[name=question]").eq(i).val())){
                    MSG.alert("묘사의 공백을 최소 2개 이상 입력하여 주새요.");
                    return false;
                }

                if(!lastWordChk($("input[name=question]").eq(i).val(),".")){
                    MSG.alert(" 묘사의 마지막 문자를 '.'로 입력하여 주세요.");
                    return false;
                }

                cnt = $("input[name=question]").eq(i).val().match(/\./g).length;
                if(cnt < 2){
                    MSG.alert("묘사의 문장은 2개 이상 입력해 주세요.");
                   return false;
                }
                if(checkPPList($("input[name=question]").eq(i), "묘사의")){
                    return false;
                }
                if($("input[name=question]").eq(i).attr("chkTyping") == 'chk'){
                    MSG.alert("묘사의 오타검색이 종료되지 않았습니다.");
                    return false;
                }
            } else {
                var tabId = "";
                if($("input[name=questiontype]").eq(i).val() == "QNALV3") {
                    qaTitle = "Q&A Level3";
                }else if($("input[name=questiontype]").eq(i).val() == "QNALV4") {
                    qaTitle = "Q&A Level4";
                }else{
                    qaTitle = "Q&A KB";
                }
                if($("input[name=question]").eq(i).val()!="" || $("input[name=answer]").eq(i).val()!="" || $("input[name=wrong_answer1]").eq(i).val()!="" || $("input[name=wrong_answer2]").eq(i).val()!=""
                    || $("input[name=wrong_answer3]").eq(i).val()!="" || $("input[name=wrong_answer4]").eq(i).val()!=""){
                    if($("input[name=question]").eq(i).val()==""){
                        MSG.alert(qaTitle + " 질문을 입력하여 주새요.");
                        return false;
                    }
                    if(!checkSpaces($("input[name=question]").eq(i).val())){
                        MSG.alert(qaTitle + " 질문의 공백을 최소 2개 이상 입력하여 주새요.");
                        return false;
                    }
                    if(!lastWordChk($("input[name=question]").eq(i).val(),"?")){
                        MSG.alert(qaTitle + " 질문의 마지막 문자를 '?'로 입력하여 주세요.");
                        return false;
                    }
                    cnt = $("input[name=question]").eq(i).val().match(/\?/g).length;
                    if(cnt != 1){
                        MSG.alert("물음표는 한개만 가능합니다.");
                        return false;
                    }
                    if(checkPPList($("input[name=question]").eq(i), "질문의")){
                        return false;
                    }
                    if($("input[name=question]").eq(i).attr("chkTyping") == 'chk'){
                        MSG.alert(qaTitle + " 질문의 오타검색이 종료되지 않았습니다.");
                        return false;
                    }

                    if($("input[name=answer]").eq(i).val()==""){
                        MSG.alert(qaTitle + " 정답을 입력하여 주새요.");
                        return false;
                    }
                    if(!checkSpaces($("input[name=answer]").eq(i).val())){
                        MSG.alert(qaTitle + " 정답의 공백을 최소 2개 이상 입력하여 주새요.");
                        return false;
                    }
                    if(!lastWordChk($("input[name=answer]").eq(i).val(),".")){
                        MSG.alert(qaTitle + " 정답의 마지막 문자를 '.'로 입력하여 주세요.");
                        return false;
                    }
                    cnt = $("input[name=answer]").eq(i).val().match(/\./g).length;
                    if(cnt != 1){
                        MSG.alert(qaTitle +" 정답의 마침표는 한개만 가능합니다.");
                        return false;
                    }
                    if(checkPPList($("input[name=answer]").eq(i), "정답의")){
                        return false;
                    }
                    if($("input[name=answer]").eq(i).attr("chkTyping") == 'chk'){
                        MSG.alert(qaTitle + " 정답의 오타검색이 종료되지 않았습니다.");
                        return false;
                    }
                    if($("input[name=wrong_answer1]").eq(i).val()==""){
                        MSG.alert(qaTitle + " 오답1를 입력하여 주새요.");
                        return false;
                    }
                    if(!checkSpaces($("input[name=wrong_answer1]").eq(i).val())){
                        MSG.alert(qaTitle + " 오답1의 공백을 최소 2개 이상 입력하여 주새요.");
                        return false;
                    }
                    if(!lastWordChk($("input[name=wrong_answer1]").eq(i).val(),".")){
                        MSG.alert(qaTitle + " 오답1의 마지막 문자를 '.'로 입력하여 주세요.");
                        return false;
                    }
                    cnt = $("input[name=wrong_answer1]").eq(i).val().match(/\./g).length;
                    if(cnt != 1){
                        MSG.alert(qaTitle +" 오답1의 마침표는 한개만 가능합니다.");
                        return false;
                    }
                    if(checkPPList($("input[name=wrong_answer1]").eq(i), "오답1의")){
                        return false;
                    }
                    if($("input[name=wrong_answer1]").eq(i).attr("chkTyping") == 'chk'){
                        MSG.alert(qaTitle + " 오답1의 오타검색이 종료되지 않았습니다.");
                        return false;
                    }
                    if($("input[name=wrong_answer2]").eq(i).val()==""){
                        MSG.alert(qaTitle + " 오답2를 입력하여 주새요.");
                        return false;
                    }
                    if(!checkSpaces($("input[name=wrong_answer2]").eq(i).val())){
                        MSG.alert(qaTitle + " 오답2의 공백을 최소 2개 이상 입력하여 주새요.");
                        return false;
                    }
                    if(!lastWordChk($("input[name=wrong_answer2]").eq(i).val(),".")){
                        MSG.alert(qaTitle + " 오답2의 마지막 문자를 '.'로 입력하여 주세요.");
                        return false;
                    }
                    cnt = $("input[name=wrong_answer2]").eq(i).val().match(/\./g).length;
                    if(cnt != 1){
                        MSG.alert(qaTitle +" 오답2의 마침표는 한개만 가능합니다.");
                        return false;
                    }
                    if(checkPPList($("input[name=wrong_answer2]").eq(i), "오답2의")){
                        return false;
                    }
                    if($("input[name=wrong_answer2]").eq(i).attr("chkTyping") == 'chk'){
                        MSG.alert(qaTitle + " 오답2의 오타검색이 종료되지 않았습니다.");
                        return false;
                    }
                    if($("input[name=wrong_answer3]").eq(i).val()==""){
                        MSG.alert(qaTitle + " 오답3를 입력하여 주새요.");
                        return false;
                    }
                    if(!checkSpaces($("input[name=wrong_answer3]").eq(i).val())){
                        MSG.alert(qaTitle + " 오답3의 공백을 최소 2개 이상 입력하여 주새요.");
                        return false;
                    }
                    if(!lastWordChk($("input[name=wrong_answer3]").eq(i).val(),".")){
                        MSG.alert(qaTitle + " 오답3의 마지막 문자를 '.'로 입력하여 주세요.");
                        return false;
                    }
                    cnt = $("input[name=wrong_answer3]").eq(i).val().match(/\./g).length;
                    if(cnt != 1){
                        MSG.alert(qaTitle +" 오답3의 마침표는 한개만 가능합니다.");
                        return false;
                    }
                    if(checkPPList($("input[name=wrong_answer3]").eq(i), "오답3의")){
                        return false;
                    }
                    if($("input[name=wrong_answer3]").eq(i).attr("chkTyping") == 'chk'){
                        MSG.alert(qaTitle + " 오답3의 오타검색이 종료되지 않았습니다.");
                        return false;
                    }
                    if($("input[name=wrong_answer4]").eq(i).val()==""){
                        MSG.alert(qaTitle + " 오답4를 입력하여 주새요.");
                        return false;
                    }
                    if(!checkSpaces($("input[name=wrong_answer4]").eq(i).val())){
                        MSG.alert(qaTitle + " 오답4의 공백을 최소 2개 이상 입력하여 주새요.");
                        return false;
                    }
                    if(!lastWordChk($("input[name=wrong_answer4]").eq(i).val(),".")){
                        MSG.alert(qaTitle + " 오답4의 마지막 문자를 '.'로 입력하여 주세요.");
                        return false;
                    }
                    cnt = $("input[name=wrong_answer4]").eq(i).val().match(/\./g).length;
                    if(cnt != 1){
                        MSG.alert(qaTitle +" 오답4의 마침표는 한개만 가능합니다.");
                        return false;
                    }
                    if(checkPPList($("input[name=wrong_answer4]").eq(i), "오답4의")){
                        return false;
                    }
                    if($("input[name=wrong_answer4]").eq(i).attr("chkTyping") == 'chk'){
                        MSG.alert(qaTitle + " 오답4의 오타검색이 종료되지 않았습니다.");
                        return false;
                    }
                    var arrOverlapChk = [[$("input[name=question]").eq(i).val(),"질문"]
                        ,[$("input[name=answer]").eq(i).val(),"정답"]
                        ,[$("input[name=wrong_answer1]").eq(i).val(),"오답-1"]
                        ,[$("input[name=wrong_answer2]").eq(i).val(),"오답-2"]
                        ,[$("input[name=wrong_answer3]").eq(i).val(),"오답-3"]
                        ,[$("input[name=wrong_answer4]").eq(i).val(),"오답-4"]];
                    for (var n=0; n<arrOverlapChk.length-1; n++){
                        for(var m=n+1;m<arrOverlapChk.length ;m++){
                            if(arrOverlapChk[n][0]==arrOverlapChk[m][0]){
                                MSG.alert("중복된 내용은 입력할 수 없습니다.("+arrOverlapChk[n][1] + "," + arrOverlapChk[m][1]+")");
                                return false;
                            }
                        }
                    }
                }
            }
        }

        var typingNgCnt = 0;
        $(".form-control").each(function (index, object) {
            if($(this).attr("chkTyping") === 'NG'){
                typingNgCnt++;
            }
        });

        var confirmMessage = "저장하시겠습니까?";
        if(typingNgCnt > 0){
            confirmMessage = "오타를 무시하고 저장하시겠습니까?";
        }

        MSG.confirm(confirmMessage,function () {
            $.ajax({
                url: '<c:url value="/section/putQuestionList"/>',
                type: 'POST',
                data: $("#qaForm").serializeArray(),
                async: false,
                dataType: 'html',
                // contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                success: function (html) {
                    $div = html;
                    $("#qaForm").html($div);
                    bindCheckErrorTyping();
                    onlyEngNum();
                    firstLetterUpperCase();
                    tabChange();
                    MSG.alert("구간QA가 저장되었습니다.");
                },
                error: function (xhr, ajaxOptions, thrownError) {
                    MSG.alert("putQuestion </br> status:"+xhr.status+"</br> message:"+xhr.responseText);
                }
            });
        });

    }
    /*저장*/
    function putShotQuestion(){
        var len = $("input[name=questionid]").length;
        var cnt = 0;
        for(var i=0; i<len; i++) {
            if ($("input[name=questiontype]").eq(i).val() == "DESC" && $("input[name=question]").eq(i).val() != "") {
                if ($("input[name=question]").eq(i).val() == "") {
                    MSG.alert("묘사를 입력하여 주새요.");
                    return false;
                }
                if (!checkSpaces($("input[name=question]").eq(i).val())) {
                    MSG.alert("묘사의 공백을 최소 2개 이상 입력하여 주새요.");
                    return false;
                }

                if (!lastWordChk($("input[name=question]").eq(i).val(), ".")) {
                    MSG.alert(" 묘사의 마지막 문자를 '.'로 입력하여 주세요.");
                    return false;
                }

                cnt = $("input[name=question]").eq(i).val().match(/\./g).length;
                if (cnt === 0 || cnt > 1) {
                    MSG.alert("묘사의 문장은 한개만 입력해 주세요.");
                    return false;
                }
                if (checkPPList($("input[name=question]").eq(i), "묘사의")) {
                    return false;
                }
                if ($("input[name=question]").eq(i).attr("chkTyping") == 'chk') {
                    MSG.alert("묘사의 오타검색이 종료되지 않았습니다.");
                    return false;
                }
            } else {
                /*if($("input[name=questiontype]").eq(i).val()==""){
                    MSG.alert("Q&A Level를 입력하여 주새요.");
                    return false;
                }
                */
                if ($("input[name=question]").eq(i).val() != "" || $("input[name=answer]").eq(i).val() != "" || $("input[name=wrong_answer1]").eq(i).val() != "" || $("input[name=wrong_answer2]").eq(i).val() != ""
                    || $("input[name=wrong_answer3]").eq(i).val() != "" || $("input[name=wrong_answer4]").eq(i).val() != "") {
                    if ($("input[name=question]").eq(i).val() != "") {
                        if ($("input[name=question]").eq(i).val() == "") {
                            MSG.alert("Q&A 질문을 입력하여 주새요.");
                            return false;
                        }
                        if (!checkSpaces($("input[name=question]").eq(i).val())) {
                            MSG.alert("Q&A 질문의 공백을 최소 2개 이상 입력하여 주새요.");
                            return false;
                        }
                        if (!lastWordChk($("input[name=question]").eq(i).val(), "?")) {
                            MSG.alert("Q&A 질문의 마지막 문자를 '?'로 입력하여 주세요.");
                            return false;
                        }
                        cnt = $("input[name=question]").eq(i).val().match(/\?/g).length;
                        if (cnt != 1) {
                            MSG.alert("물음표는 한개만 가능합니다.");
                            return false;
                        }
                        if (checkPPList($("input[name=question]").eq(i), "Q&A 질문의")) {
                            return false;
                        }
                        if ($("input[name=question]").eq(i).attr("chkTyping") == 'chk') {
                            MSG.alert("Q&A 질문의 오타검색이 종료되지 않았습니다.");
                            return false;
                        }
                        if ($("input[name=answer]").eq(i).val() == "") {
                            MSG.alert("Q&A 정답을 입력하여 주새요.");
                            return false;
                        }
                        if (!checkSpaces($("input[name=answer]").eq(i).val())) {
                            MSG.alert("Q&A 정답의 공백을 최소 2개 이상 입력하여 주새요.");
                            return false;
                        }
                        if (!lastWordChk($("input[name=answer]").eq(i).val(), ".")) {
                            MSG.alert("Q&A 정답의 마지막 문자를 '.'로 입력하여 주세요.");
                            return false;
                        }
                        cnt = $("input[name=answer]").eq(i).val().match(/\./g).length;
                        if (cnt != 1) {
                            MSG.alert("Q&A 정답의 마침표는 한개만 가능합니다.");
                            return false;
                        }
                        if (checkPPList($("input[name=answer]").eq(i), "Q&A 정답의")) {
                            return false;
                        }
                        if ($("input[name=answer]").eq(i).attr("chkTyping") == 'chk') {
                            MSG.alert("Q&A 정답의 오타검색이 종료되지 않았습니다.");
                            return false;
                        }
                        if ($("input[name=wrong_answer1]").eq(i).val() == "") {
                            MSG.alert("Q&A 오답1를 입력하여 주새요.");
                            return false;
                        }
                        if (!checkSpaces($("input[name=wrong_answer1]").eq(i).val())) {
                            MSG.alert("Q&A 오답1의 공백을 최소 2개 이상 입력하여 주새요.");
                            return false;
                        }
                        if (!lastWordChk($("input[name=wrong_answer1]").eq(i).val(), ".")) {
                            MSG.alert("Q&A 오답1의 마지막 문자를 '.'로 입력하여 주세요.");
                            return false;
                        }
                        cnt = $("input[name=wrong_answer1]").eq(i).val().match(/\./g).length;
                        if (cnt != 1) {
                            MSG.alert("Q&A 오답1의 마침표는 한개만 가능합니다.");
                            return false;
                        }
                        if (checkPPList($("input[name=wrong_answer1]").eq(i), "Q&A 오답1의")) {
                            return false;
                        }
                        if ($("input[name=wrong_answer1]").eq(i).attr("chkTyping") == 'chk') {
                            MSG.alert("Q&A 오답1의 오타검색이 종료되지 않았습니다.");
                            return false;
                        }
                        if ($("input[name=wrong_answer2]").eq(i).val() == "") {
                            MSG.alert("Q&A 오답2를 입력하여 주새요.");
                            return false;
                        }
                        if (!checkSpaces($("input[name=wrong_answer2]").eq(i).val())) {
                            MSG.alert("Q&A 오답2의 공백을 최소 2개 이상 입력하여 주새요.");
                            return false;
                        }
                        if (!lastWordChk($("input[name=wrong_answer2]").eq(i).val(), ".")) {
                            MSG.alert("Q&A 오답2의 마지막 문자를 '.'로 입력하여 주세요.");
                            return false;
                        }
                        cnt = $("input[name=wrong_answer2]").eq(i).val().match(/\./g).length;
                        if (cnt != 1) {
                            MSG.alert("Q&A 오답2의 마침표는 한개만 가능합니다.");
                            return false;
                        }
                        if (checkPPList($("input[name=wrong_answer2]").eq(i), "Q&A 오답2의")) {
                            return false;
                        }
                        if ($("input[name=wrong_answer2]").eq(i).attr("chkTyping") == 'chk') {
                            MSG.alert("Q&A 오답2의 오타검색이 종료되지 않았습니다.");
                            return false;
                        }
                        if ($("input[name=wrong_answer3]").eq(i).val() == "") {
                            MSG.alert("Q&A 오답3를 입력하여 주새요.");
                            return false;
                        }
                        if (!checkSpaces($("input[name=wrong_answer3]").eq(i).val())) {
                            MSG.alert("Q&A 오답3의 공백을 최소 2개 이상 입력하여 주새요.");
                            return false;
                        }
                        if (!lastWordChk($("input[name=wrong_answer3]").eq(i).val(), ".")) {
                            MSG.alert("Q&A 오답3의 마지막 문자를 '.'로 입력하여 주세요.");
                            return false;
                        }
                        cnt = $("input[name=wrong_answer3]").eq(i).val().match(/\./g).length;
                        if (cnt != 1) {
                            MSG.alert("Q&A 오답3의 마침표는 한개만 가능합니다.");
                            return false;
                        }
                        if (checkPPList($("input[name=wrong_answer3]").eq(i), "Q&A 오답3의")) {
                            return false;
                        }
                        if ($("input[name=wrong_answer3]").eq(i).attr("chkTyping") == 'chk') {
                            MSG.alert("Q&A 오답3의 오타검색이 종료되지 않았습니다.");
                            return false;
                        }
                        if ($("input[name=wrong_answer4]").eq(i).val() == "") {
                            MSG.alert("Q&A 오답4를 입력하여 주새요.");
                            return false;
                        }
                        if (!checkSpaces($("input[name=wrong_answer4]").eq(i).val())) {
                            MSG.alert("Q&A 오답4의 공백을 최소 2개 이상 입력하여 주새요.");
                            return false;
                        }
                        if (!lastWordChk($("input[name=wrong_answer4]").eq(i).val(), ".")) {
                            MSG.alert("Q&A 오답4의 마지막 문자를 '.'로 입력하여 주세요.");
                            return false;
                        }
                        cnt = $("input[name=wrong_answer4]").eq(i).val().match(/\./g).length;
                        if (cnt != 1) {
                            MSG.alert("Q&A 오답4의 마침표는 한개만 가능합니다.");
                            return false;
                        }
                        if (checkPPList($("input[name=wrong_answer4]").eq(i), "Q&A 오답4의")) {
                            return false;
                        }
                        if ($("input[name=wrong_answer4]").eq(i).attr("chkTyping") == 'chk') {
                            MSG.alert("Q&A 오답4의 오타검색이 종료되지 않았습니다.");
                            return false;
                        }
                        var arrOverlapChk = [[$("input[name=question]").eq(i).val(),"질문"]
                            ,[$("input[name=answer]").eq(i).val(),"정답"]
                            ,[$("input[name=wrong_answer1]").eq(i).val(),"오답-1"]
                            ,[$("input[name=wrong_answer2]").eq(i).val(),"오답-2"]
                            ,[$("input[name=wrong_answer3]").eq(i).val(),"오답-3"]
                            ,[$("input[name=wrong_answer4]").eq(i).val(),"오답-4"]];
                        for (var n=0; n<arrOverlapChk.length-1; n++){
                            for(var m=n+1;m<arrOverlapChk.length ;m++){
                                if(arrOverlapChk[n][0]==arrOverlapChk[m][0]){
                                    MSG.alert("중복된 내용은 입력할 수 없습니다.("+arrOverlapChk[n][1] + "," + arrOverlapChk[m][1]+")");
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }

        var typingNgCnt = 0;
        $(".form-control").each(function (index, object) {
            if($(this).attr("chkTyping") === 'NG'){
                typingNgCnt++;
            }
        });

        var confirmMessage = "저장하시겠습니까?";
        if(typingNgCnt > 0){
            confirmMessage = "오타를 무시하고 저장하시겠습니까?";
        }

        MSG.confirm(confirmMessage,function () {
            $.ajax({
                url: '<c:url value="/section/putShotQuestionList"/>',
                type: 'POST',
                data: $("#qaForm").serializeArray(),
                async: false,
                dataType: 'html',
                // contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                success: function (html) {
                    $div = html;
                    $("#qaForm").html($div);
                    bindCheckErrorTyping();
                    onlyEngNum();
                    firstLetterUpperCase();
                    tabChange();
                    MSG.alert("구간QA가 저장되었습니다.");
                },
                error: function (xhr, ajaxOptions, thrownError) {
                    MSG.alert("putQuestion </br> status:"+xhr.status+"</br> message:"+xhr.responseText);
                }
            });
        });
    }
    function lastWordChk(str,word){
        if(str.substr(str.length-1,str.length) == word){
            return true;
        }else{
            return false;
        }
    }
    function checkSpaces(str){
        var spaceCnt = (str.match(/ /g) || []).length;
        if(spaceCnt < 2){
            return false;
        }else{
            return true;
        }
    }

    function checkTypingError(obj) {

        var status = false;
        var text = $(obj).val();

        if(text != ''){

            $(obj).attr("chkTyping", "chk");

            $(obj).closest("div").find(".errorTxt").remove();
            var errorTxt = "<div class='errorTxt'><label style='padding-top: 5px;'>오타검색중..</label></div>";
            $(obj).closest("div").append(errorTxt);

            var _param = {};
            _param.text = text;

            $.ajax({
                type: "POST",
                url: "<c:url value='/content/checkTypingError' />",
                data: _param,
                param: {},
                success: function(response) {
                    $(obj).closest("div").find(".errorTxt").remove();
                    /*var result = response.result.toString().replace(/[\[\]]/gi, '');*/
                    var result = response.result;
                    if(result != ''){
                        /*var errorTxt = "<label class='errorTxt' style='padding-top: 5px;' onclick='changeErrorText(this)'>"++"</label>";*/
                        $(obj).closest("div").append("<div class='errorTxt'>" + result + "</div>");
                        $(obj).closest("div").find("label").each(function () {
                            $(this).css("cursor", "pointer");
                            $(this).on('click',function () {
                                changeErrorText($(this));
                            });
                        });
                        status = true;
                        $(obj).attr("chkTyping","NG");
                    }else{
                        var errorTxt = "<div class='errorTxt'><label class='errorTxt'>없음.</label></div>";
                        $(obj).closest("div").append(errorTxt);
                        $(obj).attr("chkTyping","");
                    }
                },
                error: function(xhr, opt, err) {
                    console.log(err);
                    $(obj).closest("div").find(".errorTxt").remove();
                    var errorTxt = "<div class='errorTxt'><label class='errorTxt' style='padding-top: 5px;'>에러발생.</label></div>";
                    $(obj).closest("div").append(errorTxt);
                    $(obj).attr("chkTyping","");
                }
            });
        }

        return status;
    }

    function changeErrorText(obj){

        var errorTxt = $(obj).text().replace(/[() >]/gi,"");
        var errorTxts = errorTxt.split("-");
        var text_obj = $(obj).parent().parent().find("input[type=text].form-control");
        var text_val = $(text_obj).val();

        $(text_obj).val(text_val.replace(new RegExp(errorTxts[0], "gi"),errorTxts[1]));

    }

    function checkPPList(obj, title){

        var org_text = $(obj).val();
        var org_words = org_text.split(" ");

        var text = $(obj).val().toLowerCase().replace(/[.,'"!~\-?]/gi,"");
        var words = text.split(" ");

        for(var j = 0; j < words.length; j++){
            var word = words[j];
            var org_word = org_words[j];
            for(var i = 0; i < _pp_list.length; i++){
                if(word === _pp_list[i]) {
                    MSG.alert(title + " 인칭대명사(" + org_word + ")는 사용할 수 없습니다.");
                    return true;
                }
            }
        }

        return false
    }

    function specialCharAfterSpace(obj){

        var org_txt = $(obj).val();
        var result = "";
        var texts = [];

        var spChrList = (org_txt.match(/[.,?]/gi) || []);

        for(var i = 0; i < spChrList.length; i++){
            texts.push(org_txt.substring(0, org_txt.indexOf(spChrList[i])+1).trim());
            org_txt = org_txt.substring(org_txt.indexOf(spChrList[i])+1, org_txt.length).trim();
        }
        if(org_txt.trim() !== '') texts.push(org_txt);

        for(var i = 0; i < texts.length; i++){
            result = result + texts[i] + " ";
        }

        $(obj).val(result.trim());
    }


    $(document).ready(function() {
        var _player = player.init("videojs", "video", "${ videoServerUrl }/${ contentField.assetfilepath }/${ contentField.assetfilename}", "");


        _player.on('timeupdate', function(){
            var tracks = _player.textTracks();
            /*   for (var i = 0; i < tracks.length; i++) {
                   var track = tracks[i];
                   console.log(track);
                   // Find the English captions track and mark it as "showing".
                   if (track.kind === 'subtitles') {
                       track.mode = 'showing';
                   }

               }*/
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
            if(_startsec!=_endsec){
                if(nowsec>=_endsec-0.2){
                    _player.pause();
                }
            }
        });
        _player.on("play",function(){
            _player.currentTime(_startsec);
        });

        // videojs

        //getSectionList(<c:out value="${idx}"/>); // 리스트 idx 넘겨받음.(ex) 624

        //getSectionOfSceneList(<c:out value="${idx}"/>); // 리스트 idx 넘겨받음.(ex) 624
        //getQaChkSectionList(<c:out value="${idx}"/>,<c:out value="${qaSearchWord}"/>); // 리스트 idx 넘겨받음.(ex) 624
        //getQaChkSectionOfSceneList('<c:out value="${idx}"/>',<c:out value="${qaSearchWord}"/>);
        resizeVideo();
        proceedHotkey();
        $("#relationList").height($(".section-list-Wrap").height()/2);
    });

    $('a[data-toggle="tab"]').on('show.bs.tab',function(e) {

        var futureTab = $(e.relatedTarget).text();
        alert(futureTab);
    });
    function reset(){
        $("#"+$("#activeTab").val()).find("input[name=question]").val('');
        $("#"+$("#activeTab").val()).find("input[name=answer]").val('');
        $("#"+$("#activeTab").val()).find("input[name=wrong_answer1]").val('');
        $("#"+$("#activeTab").val()).find("input[name=wrong_answer2]").val('');
        $("#"+$("#activeTab").val()).find("input[name=wrong_answer3]").val('');
        $("#"+$("#activeTab").val()).find("input[name=wrong_answer4]").val('');
    }

    function allReset(){
        $("input[name=question]").val('');
        $("input[name=answer]").val('');
        $("input[name=wrong_answer1]").val('');
        $("input[name=wrong_answer2]").val('');
        $("input[name=wrong_answer3]").val('');
        $("input[name=wrong_answer4]").val('');
    }

    function sectionWorkerChange(){
        $('tr[name="qaSecTr"]').each(function(){
            if($(this).hasClass('ui-selected')){
                $(this).click();
            }
        });
    }
    function shotWorkerChange() {
        $('tr[name="secTr"]').each(function () {
            if ($(this).hasClass('ui-selected')) {
                $(this).click();
            }
        });
    }
    function qaChkSearch(obj,qaSearchWord){
        $(".btnQaChk").removeClass("btn-red");
        $(obj).addClass("btn-red");
        getQaChkSectionList(${idx},qaSearchWord);
        getQaChkSectionOfSceneList(${idx},qaSearchWord);
    }

</script>
<c:import url="../includes/footer.jsp"/>