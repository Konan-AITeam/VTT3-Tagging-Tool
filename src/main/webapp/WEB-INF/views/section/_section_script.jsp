<%--
  Created by IntelliJ IDEA.
  User: jeunghakmoon
  Date: 26/11/2018
  Time: 8:24 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../includes/taglib.jsp" %>
<script>
    String.prototype.capitalize = function() {
        return this.charAt(0).toUpperCase() + this.slice(1);
    }

    String.prototype.onlyEngNum = function () {
        return this.replace(/[^a-zA-Z0-9\s.,'"!~\-?]/gi, '');
    }

    function firstLetterUpperCase() {
        $("input[type=text].form-control").off('keypress');
        $("input[type=text].form-control").on("keypress",function (e) {
            $(this).val($(this).val().capitalize());
        });
    }
    function onlyEngNum() {
        $("input[type=text].form-control").off('keyup');
        $("input[type=text].form-control").on("keyup",function (e) {
            if (!(e.keyCode >= 37 && e.keyCode <= 40)) {
                var inputValue = $(this).val();
                $(this).val(inputValue.onlyEngNum());
            }
        });
    }
    function bindCheckErrorTyping() {
        $("input[type=text].form-control").off('blur');
        $("input[type=text].form-control").on('blur', function (e) {
            checkTypingError($(this));
        });
    }
    function tabChange() {
        $("#qaTabs > li").off('click');
        $("#qaTabs > li").on('click', function () {
            $("input[type=text]").each(function () {
                var isChk = false;
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
        });
    }
    /*구간 선택*/
    function getSectionShotList(videoid,shotid,delflag,filepath,filename){
        console.log("getSectionShotList");
        if(myPlayer1!=null) {
            myPlayer1.pause();
        }
        $('.table tbody tr[name="secTr"]').removeClass('ui-selected');
        var $tr = $('#secTr_'+shotid);
        $tr.addClass('ui-selected');
        var startsec=$tr.find("input[name=startsec]").val();
        var endsec=$tr.find("input[name=endsec]").val();
        _startsec = startsec;
        _endsec = endsec;
        myPlayer1.currentTime(startsec);
        getShotQuestionList(shotid);
    }

    /*구간 조회*/
    function getSectionList(idx) {
        var $div='';
        $.ajax({
            url: '<c:url value="/section/getSectionList"/>',
            type: 'POST',
            data: {'videoid': idx, 'rate':_rate},
            async: false,
            dataType: 'html',
            // contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            success: function (html) {
                $div = html;
                $("#sectionList").html($div);
            },
            error: function (xhr, ajaxOptions, thrownError) {
                MSG.alert("getSectionList </br> status:"+xhr.status+"</br> message:"+xhr.responseText);
            }
        });
    }
    /*구간 조회*/
    function getSectionOfSceneList(videoid,sectionid) {
        var $div='';
        $.ajax({
            url: '<c:url value="/section/getSectionOfSceneList"/>',
            type: 'POST',
            data: {'videoid': videoid, 'sectionid': sectionid, 'rate':_rate},
            async: false,
            dataType: 'html',
            // contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            success: function (html) {
                $div = html;
                $("#sectionList").html($div);
                bindCheckErrorTyping();
                onlyEngNum();
                firstLetterUpperCase();
                tabChange();
            },
            error: function (xhr, ajaxOptions, thrownError) {
                MSG.alert("getSectionOfSceneList </br> status:"+xhr.status+"</br> message:"+xhr.responseText);
            }
        });
    }

    /*QA구간 조회*/
    function getQaSectionList(idx) {
        var $div='';
        $.ajax({
            url: '<c:url value="/section/getQaSectionList"/>',
            type: 'POST',
            data: {'idx': idx, 'rate':_rate},
            dataType: 'html',
            // contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            success: function (html) {
                $div = html;
                $("#qaSectionList").html($div);
                $("#qaSectionList tr:first").click();
                bindCheckErrorTyping();
                onlyEngNum();
                firstLetterUpperCase();
                tabChange();
            },
            error: function (xhr, ajaxOptions, thrownError) {
                MSG.alert("getQaSectionList </br> status:"+xhr.status+"</br> message:"+xhr.responseText);
            }
        });
    }



    function resizeVideo() {
        var w = $("#scrollImgDiv").width() - 30;
        var h = $(".section-list-Wrap").height();
        $(".videobox").css("height",h/3);
        $(".videobox-layout").css("height",h/3);
        $(".videobox").css("width",w);
        $(".videobox-layout").css("width",w);
        $(".select-video").css("margin-top",h/3 * -1);
    }


    var param = {};
    param.idx = ${idx};
    param.viewChk = 'section';

    $(".btn-move").on("click",function(){
        var type = $(this).attr("id");
        location.href="<c:url value="/section/"/>"+type+"?" + $.param(param);;
    });
    $(window).resize(function(){
        resizeVideo();
    });
</script>