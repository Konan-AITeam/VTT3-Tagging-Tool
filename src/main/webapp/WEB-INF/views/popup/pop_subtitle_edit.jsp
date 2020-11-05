<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../includes/taglib.jsp" %>
<c:import url="pop_header.jsp"/>

<!-- page content -->
<div class="container" role="main">
    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2>영상<small>Video</small></h2>
                    <ul class="nav navbar-right panel_toolbox">
                        <li>
<%--                            <a class="table-btn" onclick="help_hotkey()">
                                <i class="fa fa-keyboard-o"></i>
                                단축키
                            </a>--%>
                        </li>
                    </ul>
                    <div class="clearfix"></div>
                </div>
                <div class="x_content superbox" id = "content">
                    <div class="row">
                        <div class="col-xs-6 col-sm-6 col-md-6 col-lg-6 videobox">
                            <video id="videojs" class="video-js vjs-default-skin vjs-big-play-centered" controls preload="auto" data-setup='{ "inactivityTimeout": 0, "playbackRates" : [0.5, 0.7, 1.0, 1.5, 2.0] }' style="width: 100%; height: 100%;"></video>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="col-md-12 col-sm-12 col-xs-12" id="imgShot">
            <div class="img-shot-title x_panel">
                <div class="x_title">
                    <h2>구간 편집<small> Section edit</small></h2>
                    <ul class="nav navbar-right panel_toolbox">
<%--                        <li>
                            <button class="btn btn-success" onclick="appendSection()">신규구간 생성</button>
                        </li>--%>
                        <li>
                            <button class="btn btn-success" onclick="setTime('start')">시작구간 설정</button>
                        </li>
                        <li>
                            <button class="btn btn-success" onclick="setTime('end')">종료구간 설정</button>
                        </li>
                        <li>
                            <button class="btn btn-success" onclick="replay()">구간듣기</button>
                        </li>
                        <li>
                            <button class="btn btn-success" onclick="">삭제</button>
                        </li>
<%--                        <li>
                            <button class="btn btn-success" onclick="">저장</button>
                        </li>--%>
                    </ul>
                    <div class="clearfix"></div>
                </div>
                <div class="x_content" id="repreImgView">

                    <div class="scroll">
                        <form class="needs-validation" id="subtitleForm" name="subtitleForm">
                            <input type="hidden" name="idx" value="${idx}"/>
                            <table class="table table-striped">
                                <thead>
                                <tr>
                                    <th>순번</th>
                                    <th>시작시간</th>
                                    <th>종료시간</th>
                                    <th>인물</th>
                                    <th>자막</th>
                                </tr>
                                </thead>
                                <tbody>
                                </tbody>
                            </table>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<c:import url="../includes/script.jsp"/>
<script>
    var _pp_list = ['i','my','me','mine','we','our','ours','us','ours','you','your','yours','she','her','hers','he','his','him','they','their','them','theirs'];
    var _startsec = "0:00:00";
    var _endsec = "0:00:00";
    var isReplay = false;

    String.prototype.capitalize = function() {
        return this.charAt(0).toUpperCase() + this.slice(1);
    }

    String.prototype.onlyEngNum = function () {
        return this.replace(/[^a-zA-Z0-9\s.,?"'()]/gi, '');
    }

    function firstLetterUpperCase(obj) {
        $(obj).on("keypress",function (e) {
            $(obj).val($(this).val().capitalize());
        });
    }
    function onlyEngNum(obj) {
        $(obj).on("keyup",function (e) {
            if (!(e.keyCode >= 37 && e.keyCode <= 40)) {
                var inputValue = $(this).val();
                $(obj).val(inputValue.onlyEngNum());
            }
        });
    }

    function bindCheckErrorTyping(obj) {
        $(obj).off('blur');
        $(obj).on('blur', function (e) {
            specialCharAfterSpace($(this));
            checkTypingError($(this));
            checkPPList($(this));
        });
    }

    /* 자막정보 조회 */
    function getSubtitleSectionList(idx){
        $.ajax({
            url: '<c:url value="/popup/subtitle/subtitleList"/>',
            type: 'POST',
            data: {'idx': idx},
            async: false,
            dataType: 'html',
            // contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            success: function (html) {
                $div = html;
                $("#subtitleForm tbody").html($div);
                $("#subtitleForm tbody tr").each(function () {
                    if($(this).attr("id") !== 'subtitleTr_new'){
                        var obj = $(this).find("input[name=subtitle]");
                        firstLetterUpperCase($(obj));
                        onlyEngNum($(obj));
                        bindCheckErrorTyping($(obj));
                    }
                });

            },
            error: function (xhr, ajaxOptions, thrownError) {
                MSG.alert("getSectionList </br> status:"+xhr.status+"</br> message:"+xhr.responseText);
            }
        });
    }

    /*qa구간 선택*/
    function getSubtitleInfo(tr){
        if(myPlayer1!=null) {
            myPlayer1.pause();
        }
        var $tr = $(tr);
        if($tr.hasClass("ui-selected")) {
            _startsec = $tr.find("input[name=starttimecode]").val();
            _endsec = $tr.find("input[name=endtimecode]").val();
        }else{
            _startsec = "0:00:00";
            _endsec = "0:00:00";
        }
        if(myPlayer1!=null&&_startsec!=0) {
            myPlayer1.currentTime(code2time(_startsec));
        }
    }
    /*신규구간 생성*/
    function appendSection(){

        var nowTime = myPlayer1.currentTime();
        if(nowTime>0.4){
            nowTime-=0.4;
        }
        var time = time2Code('audio',nowTime,999);

        $tr = $("#subtitleTr_new").clone();
        $tr.attr("id","");
        $tr.show();
        $("#subtitleForm tbody").append($tr);
        $("#subtitleForm tr").removeClass("ui-selected");
        $tr.addClass("ui-selected");

        $tr.find("input[name=starttimecode]").val(time);
        $tr.find("td[name=startTime]").text(time);
        $tr.find("input[name=endtimecode]").val(time);
        $tr.find("td[name=endTime]").text(time);

        firstLetterUpperCase($tr.find("input[name=subtitle]"));
        onlyEngNum($tr.find("input[name=subtitle]"));
        bindCheckErrorTyping($tr.find("input[name=subtitle]"));
    }

    /*시간 설정*/
    function setTime(type){
        var nowTime = myPlayer1.currentTime();
        if(nowTime>0.4){
            nowTime-=0.4;
        }
        var time = time2Code('audio',nowTime,999);
        var $tr = $("#subtitleForm tr.ui-selected");
        if(type=="start") {
            $tr.find("input[name=starttimecode]").val(time);
            $tr.find("td[name=startTime]").text(time);
        }else{
            var stTime = $tr.find("input[name=starttimecode]").val();
            var timeDiff =  code2time(time) - code2time(stTime);
            if(timeDiff < 0.1 || timeDiff > 5){
                myPlayer1.pause();
                $tr.find("input[name=endtimecode]").val(stTime);
                $tr.find("td[name=endTime]").text(stTime);
                MSG.alert("구간 시간은 5초 이내로 설정해 주세요.");
                return false;
            }

            $tr.find("input[name=endtimecode]").val(time);
            $tr.find("td[name=endTime]").text(time);
            myPlayer1.pause();
        }
    }

    function replay(){
        isReplay = true;
        var $tr = $("#subtitleForm tr.ui-selected");

        _startsec = $tr.find("input[name=starttimecode]").val();
        _endsec = $tr.find("input[name=endtimecode]").val();
        myPlayer1.currentTime(code2time(_startsec));
        myPlayer1.play();
    }

    /*구간 삭제*/
    function deleteSubtitleSection(){
        var $this = $("#subtitleForm .ui-selected");
        var delflag = $this.find("input[name=delflag]").val();
        $this.toggleClass("delete");
        if(delflag =='false') {
            $this.find("input[name=delflag]").val("true");
        }else{
            $this.find("input[name=delflag]").val("false");
        }
    }

    /*저장*/
    function putSubtitleSection(){

        $("#subtitleForm tbody tr").each(function () {
            $(this).removeClass("ui-selected");
        });

        var errorStatus = false;
        $("#subtitleForm tbody tr").each(function () {
            if($(this).attr("id") !== 'subtitleTr_new'){

                if($(this).find("input[name=subtitle]").attr("chkTyping") == 'chk'){
                    $(this).addClass("ui-selected");
                    MSG.alert("오타검색이 종료되지 않았습니다.");
                    errorStatus = true;
                    return false;
                }

                var stTime = $(this).find("input[name=starttimecode]").val();
                var edTime = $(this).find("input[name=endtimecode]").val();
                var delflag = $(this).find("input[name=delflag]").val()
                var person = $(this).find("select[name=person]").val();

                if(stTime === edTime && delflag == 'false'){
                    $(this).addClass("ui-selected");
                    MSG.alert("구간 시간 설정을 해주세요.");
                    errorStatus = true;
                    return false;
                }

                if(person === ''){
                    $(this).find("select[name=person]").val('V010307');
                }

            }
        });

        if(errorStatus) return false;

        var typingNgCnt = 0;
        $("input[name=subtitle]").each(function (index, object) {
            if($(this).attr("chkTyping") === 'NG'){
                typingNgCnt++;
            }
        });

        var confirmMessage = "저장하시겠습니까?";
        if(typingNgCnt > 0){
            confirmMessage = "오타를 무시하고 저장하시겠습니까?";
        }

        MSG.confirm(confirmMessage, function () {
            $("#subtitleTr_new").remove();
            $.ajax({
                url: '<c:url value="/subtitle/putSubtitleList"/>',
                type: 'POST',
                data: $("#subtitleForm").serializeArray(),
                async: false,
                dataType: 'html',
                // contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                success: function (html) {
                    $div = html;
                    $("#subtitleForm tbody").html($div);
                    $("#subtitleForm tbody tr").each(function () {
                        if($(this).attr("id") !== 'subtitleTr_new'){
                            var obj = $(this).find("input[name=subtitle]");
                            firstLetterUpperCase($(obj));
                            onlyEngNum($(obj));
                            bindCheckErrorTyping($(obj));
                        }
                    });
                    MSG.alert("자막 정보가 저장되었습니다.");
                },
                error: function (xhr, ajaxOptions, thrownError) {
                    MSG.alert("putQaSectionList </br> status:"+xhr.status+"</br> message:"+xhr.responseText);
                }
            });
        });
    }
    function resizeVideo() {
        var w = $("#content").width() - 30;
        var h = $(window).height() - 400;
        $(".videobox").css("height",h/2);
        $(".videobox-layout").css("height",h/2);
        $(".videobox").css("width",w);
        $(".videobox-layout").css("width",w);
        $(".select-video").css("margin-top",h/2 * -1);
    }

    function checkTypingError(obj) {

        var status = false;
        var text = $(obj).val();

        if(text != ''){

            $(obj).attr("chkTyping", "chk");

            $(obj).closest("td").find(".errorTxt").remove();
            var errorTxt = "<div class='errorTxt'><label style='padding-top: 5px;'>오타검색중..</label></div>";
            $(obj).closest("td").append(errorTxt);

            var _param = {};
            _param.text = text;

            $.ajax({
                type: "POST",
                url: "<c:url value='/content/checkTypingError' />",
                data: _param,
                param: {},
                success: function(response) {
                    $(obj).closest("td").find(".errorTxt").remove();
                    var result = response.result;
                    if(result != ''){
                        $(obj).closest("td").append("<div class='errorTxt'>" + result + "</div>");
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
                        $(obj).closest("td").append(errorTxt);
                        $(obj).attr("chkTyping","");
                    }
                },
                error: function(xhr, opt, err) {
                    $(obj).closest("td").find(".errorTxt").remove();
                    var errorTxt = "<div class='errorTxt'><label class='errorTxt' style='padding-top: 5px;'>에러발생.</label></div>";
                    $(obj).closest("td").append(errorTxt);
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

    function checkPPList(obj){

        var org_text = $(obj).val();
        var org_words = org_text.split(" ");

        var text = $(obj).val().toLowerCase().replace(/[.,'"!~\-?]/gi,"");
        var words = text.split(" ");

        var result = "";
        for(var j = 0; j < words.length; j++){
            var word = words[j] + "";
            var org_word = org_words[j] + " ";
            for(var i = 0; i < _pp_list.length; i++){
                if(word === (_pp_list[i]+"")) {
                    if(org_word.length !== word.length){
                        org_word = org_word.substring(0, word.length) + "()" + org_word.substring(word.length, org_word.length);
                    }else{
                        org_word = org_word.trim() + "() ";
                    }
                }
            }
            result = result + org_word;
        }
        $(obj).val(result.trim());
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

    var param = {};
    param.idx = ${idx};
    param.viewChk = 'section';
    $(window).resize(function(){
        resizeVideo();
    });

    $(document).ready(function() {
        $("#subtitleForm tbody").on("click",".section-num>p",function(){
            myPlayer1.currentTime(code2time($(this).text().trim()));
            return false;
        });
        // videojs
        var _player = player.init("videojs", "video", "${ videoServerUrl }/${ contentField.assetfilepath }/${ contentField.assetfilename}", "");
        _player.on('timeupdate',function(){
            var nowTime = _player.currentTime();
            if(isReplay&&nowTime>=code2time(_endsec)){
                isReplay=false;
                _player.pause();
            }
        });

        resizeVideo();
        getSubtitleSectionList(<c:out value="${idx}"/>);

        $("#subtitleForm").on("click","tr", function(){
            $("#subtitleForm tr").removeClass("ui-selected");
            $(this).addClass("ui-selected");
            var startTime = $(this).find("input[name=starttimecode]").val();
            _player.currentTime(code2time(startTime));
        });
        //단축키
        proceedHotkey();
        $(this).bind('keydown.ctrl_[',function (e){ /* ctrl + [ = 마크인 */
            setTime("start");
            return false;
        });
        $(this).bind('keydown.ctrl_]',function (e){ /* ctrl + ] = 마크아웃 */
            setTime("end");
            return false;
        });
        $(this).bind('keydown.ctrl_space',function (e){ /* ctrl + ] = 구간 반복 */
            replay();
            return false;
        });
        $(this).bind('keydown.ctrl_n',function (e){ /* ctrl + ] = 신규 생성 */
            appendSection();
            return false;
        });
    });
</script>
<c:import url="../includes/footer.jsp"/>