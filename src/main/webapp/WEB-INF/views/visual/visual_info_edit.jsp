<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../includes/taglib.jsp" %>
<c:import url="../includes/header.jsp"/>

<style type="text/css">
    html{
        overflow-y: scroll;
    }
    #visual-row-top {
        max-height: 1100px;
    }

    .container>div,.row>div:not(.x_panel){
        padding-left:10px;
    }
    .container>div:first-of-type,
    .row>div:first-of-type:not(.x_panel){
        padding-left:0;
    }

    .x_content{
        margin-top:0;
    }
    .row.fix > div {
        height: 100%;
    }
    .row.fix > .x_panel,
    .row.fix > div > .x_panel{
        height: 100%;
    }
    .row.fix > .x_panel > .x_content,
    .row.fix > div > .x_panel > .x_content{
        height:100%;
        overflow-y: auto;
    }
    .row.fix > .x_panel > .x_title+.x_content,
    .row.fix > div > .x_panel > .x_title+.x_content{
        height:calc(100% - 50px);
    }
    .row.fix > .x_panel > .x_title+.edit-btns-wrap+.x_content{
        height:calc(100% - 100px);
    }
    .row + .row {
        padding-top: 10px;
    }    .selction-list-Image{
        float:left;
        height:100%;
        width:340px;
    }
    .container.visual .section-video-Wrap{
        width:calc(100% - 900px);
        height:auto;
        float:left;
    }
    #scrollImgDiv > .shot-list-wrap > div{
        padding:2px;
    }
    .section-image-Wrap .image-tag{
        float: right;
        height: 30px;
        margin-top: -3px;
    }
    td[name="secTdTime"]{
        letter-spacing:-1px;
    }
    #visualInfoEdit{
        padding:0;
        overflow: hidden;
        overflow-x:auto;
    }
    .section-image-Edit{
        float: left;
        width: 390px;
        height: 100%;
    }
    #nav-objTabContent{
        /*스크롤 문제로 주석처리*/
        /*height:calc(100% - 90px);  */
    }
    #visual-row-bottom table.table{
        margin-bottom:0;
    }

    .custom-combobox {
        position: relative;
        display: inline-block;
    }
    .custom-combobox-toggle {
        position: absolute;
        top: 0;
        bottom: 0;
        margin-left: -1px;
        padding: 0;
    }
    .custom-combobox-input {
        margin: 0;
        padding: 5px 10px;
    }
    .ui-menu.ui-widget.ui-widget-content.ui-autocomplete.ui-front{
        max-height: 300px; overflow-y: scroll; overflow-x: hidden;
    }

    @media (max-width:1200px) {
        .x_title h2{
            width:initial;
        }
        .container.visual .section-list-Wrap{
            width:120px;
        }
        .selction-list-Image{
            width:120px;
        }
        .container.visual .section-video-Wrap{
            width:calc(100% - 550px);
        }
        .x_panel,
        .section-list-Wrap .x_panel{
            padding: 20px 5px !important;
        }
    }

</style>
<!-- top navigation -->
<div class="top_nav">
    <div class="nav_menu nav_menu-j">
        <nav>
            <div class="align-self-center">
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
                        시각정보 편집(${contentField.orifilename})
                    </li>
                </ol>
            </div>
            <%--<div class="nav toggle">--%>
            <%--<a id="menu_toggle"><i class="fa fa-bars"></i></a>--%>
            <%--</div>--%>
        </nav>
    </div>
</div>
<!-- /top navigation -->
<!-- page content -->
<div class="container visual fix" role="main">

    <!-- 카드 컨텐츠 시작 -->
    <div class="section-list-Wrap">
        <div class="x_panel section-list">
            <div class="x_title">
                <h2>구간 리스트
                    <%-- 리스트 <small> Shot section list </small>--%>
                </h2>
                <!--
                <button class="btn btn-success" id="btn_update_shot" style="padding:2px 7px;margin:0;float:right;">수정</button>
                -->
                <div class="clearfix"></div>
            </div>
            <div class="x_content" id="sectionList"></div>
        </div>
    </div>

    <div class="selction-list-Image" >
        <div class="row fix" style="height:100%;">
            <div class="x_panel">
                <div class="x_title">
                    <h2> 이미지 리스트  <small> Image list </small></h2>
                    <div class="clearfix"></div>
                </div>
                <div class="x_content" id="scrollImgDiv"></div>
            </div>
        </div>
    </div>
    <div class="section-video-Wrap">
        <%--<div class="row fix" id="visual-row-top" style="height:400px;">--%>
        <div class="row fix" id="visual-row-top" >
            <div class="section-image-Wrap" id="imgShot">
                <div class="img-shot-title x_panel" id="repreImgView">
                </div>
            </div>
        </div>

        <div id="visual-row-bottom" class="row fix" style="min-height:240px;">
            <div class="x_panel">
                <div class="x_title">
                    <h2>시각정보 리스트<small> Visual list</small></h2>
                    <div class="clearfix"></div>
                </div>
                <div class="x_content">
                    <div class="table-responsive" role="tabpanel" data-example-id="togglable-tabs" id="putMetaInfo">
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="section-image-Edit">
        <div class="row fix" style="height:100%;">

            <div class="x_panel">
                <div class="x_title">
                    <h2> 시각정보편집<small> Visual edit</small></h2>
                    <div class="clearfix"></div>
                </div>

                <div class="edit-btns-wrap">
                    <sec:authorize access="hasAnyRole('ROLE_ADMIN,ROLE_EDITER')" var="u">
                        <span class="badge badge-secondary" id="clickInfo" style="margin-top:5px;"></span>
                        <ul class="nav panel_toolbox">
                            <li class="m-right5">
                                <a id="allObjView" class="table-btn">
                                    <i class="fa fa-file"></i> 개별
                                </a>
                            </li>
                            <li class="m-right5">
                                <a id="btnNew" class="table-btn">
                                    <i class="fa fa-plus-square"></i> 추가
                                </a>
                            </li>
                            <li class="m-right5">
                                <a id="multiFaceFormSumit" class="table-btn">
                                    <i class="fa fa-edit"></i> 저장/수정
                                </a>
                            </li>
                        </ul>
                    </sec:authorize>
                    <!--  <button type="submit" class="btn btn-primary"> 전체  </button> -->
                </div>

                <div class="x_content" id="visualInfoEdit">
                    <div class="row fix" style="height:100%;">
                        <div class="x_content">
                            <div class="" role="tabpanel" data-example-id="togglable-tabs"  style="height:100%;">
                                <ul id="objTabs" class="nav nav-tabs" role="tablist">
                                    <li role="presentation" class="active">
                                        <a href="#faceFormDiv" id="faceTab" role="tab" data-toggle="tab" aria-expanded="true"> 인물 <strong id="faceTabNo">(0)</strong>
                                        </a>
                                    </li>
                                    <li role="presentation" class="">
                                        <a href="#personAllDiv" role="tab" id="personAllTab" data-toggle="tab" aria-expanded="false"> 인물 전체 <strong id="personAllTabNo">(0)</strong>
                                        </a>
                                    </li>
                                    <li role="presentation" class="">
                                        <a href="#objTabDiv" role="tab" id="objTab" data-toggle="tab" aria-expanded="false"> 객체 <strong id="objTabNo">(0)</strong>
                                        </a>
                                    </li>
                                    <li role="presentation" class="">
                                        <a href="#placeFormDiv" id="placeTab" role="tab" data-toggle="tab" aria-expanded="false"> 장소 </a>
                                    </li>
                                </ul>


                                <div id="nav-objTabContent" class="layer-edit-wrap tab-content">
                                    <div role="tabpanel" class="tab-pane fade active in" id="faceFormDiv" aria-labelledby="faceTab"></div>
                                    <div role="tabpanel" class="tab-pane fade" id="personAllDiv" aria-labelledby="personAllTab"></div>
                                    <div role="tabpanel" class="tab-pane fade" id="objTabDiv" aria-labelledby="objTab"></div>
                                    <div role="tabpanel" class="tab-pane fade" id="placeFormDiv" aria-labelledby="placeTab"></div>
                                    <div role="tabpanel" class="tab-pane fade" id="descFormDiv" aria-labelledby="descTab"></div>

                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- x_content 끝 -->
            </div>
        </div>

    </div>

</div>


<!-- /page content -->
<!-- END #MAIN PANEL -->
<c:import url="../includes/script.jsp"/>

<script>


    //$(document).ready(function(){
        var videoid = ${idx};
        var _param = {videoid: videoid};
        var ImgId = "#vttImg";
        var img_data_index = '';
        //area전체이동가능여부 flag
        var area_all_move = false;
        //이전메타정보 함수에서 호출 여부 flag
        var getPrevMeta_call = false;

        // 최초에 샷구간을 먼저 가져오기
        //샷 구간 리스트
        function getSectionList() {
            var $div = '';
            $.ajax({
                url: '<c:url value="/visual/getSectionList"/>',
                type: 'POST',
                data: _param,
                async: false,
                dataType: 'html',
                // contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                success: function (html) {
                    $div = html;
                    $("#sectionList").html($div);
                    setSectionEvt();
                    //$("tr[name='secTr'][ delflag='false']:first").click();
                },
                error: function (xhr, ajaxOptions, thrownError) {
                    MSG.alert("getSectionList"+thrownError);
                }
            });
        }
        function setSectionEvt(){
            var arr = [];
            /* 2019-09-19(bys) 화면 로딩 후 클릭 이벤트 추가 하던 로직
            $('.section_row').each(function(idx){
                var input_obj = $(this).find('input');
                var obj = {};
                for(var i=0;i<input_obj.length;i++){
                    var name = $(input_obj[i]).attr('name');
                    var val = $(input_obj[i]).val();
                    obj[''+name] = val;
                }

                var frame_cut = $(".section_row").find("input[name=frame_cut]").val();
                obj.frame_cut           = frame_cut;
                obj.pageCnt             = 10;
                obj.curPage             = 1;
                obj.assetfilepathorigin = obj.assetfilepath;
                arr.push(obj);

                $(this).on('click', function(){
                    img_data_index = '';
                    getSectionShotList(arr[idx], 1);
                });
            });*/

            var input_obj = $('.section_row:first').find('input');
            var obj = {};
            for(var i=0;i<input_obj.length;i++){
                var name = $(input_obj[i]).attr('name');
                var val = $(input_obj[i]).val();
                obj[''+name] = val;
            }
            var frame_cut = $(".section_row").find("input[name=frame_cut]").val();
            obj.frame_cut           = frame_cut;
            obj.pageCnt             = 10;
            obj.curPage             = 1;
            obj.assetfilepathorigin = obj.assetfilepath;
            if($("tr[name='secTr'][ delflag='false']:first").length > 0){
                getSectionShotList(obj, 1);
            }
            else{

                MSG.alert("내용이 존재하지 않습니다.", function(){
                    location.href = "<c:url value='/content' />";
                });

                return false;
            }
        }
        getSectionList();

        function getSectionShotList(__data) {
            // console.log("getSectionShotList");
            $('.table tbody tr[name="secTr"]').removeClass('ui-selected');
            $('#secTr_'+__data.shotid).addClass('ui-selected');
            var shotname_arr = __data.assetfilename.split('.');

            _param = {
                videoid             : videoid,
                shotname            : shotname_arr[0] || "",
                shotid              : __data.shotid,
                assetfilename       : __data.assetfilename,
                assetfilepathorigin : __data.assetfilepathorigin,
                assetfilepath       : __data.assetfilepathorigin + shotname_arr[0],
                startframeindex     : __data.startframeindex,
                endframeindex       : __data.endframeindex,
                curPage             : __data.curPage,
                pageCnt             : __data.pageCnt,
                frame_cut           : __data.frame_cut,
                check_mode          : 0,
                otheruserid         : $("#userSelect").val()
            };

            $.ajax({
                url: '<c:url value="/visual/getSectionShotList"/>',
                type: 'POST',
                data: _param,
                dataType: 'html',
                // contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                success: function (html) {
                    // console.log("success");
                    $div = $("#scrollImgDiv").html(html);

                    var img_data_flag = false;
                    if(img_data_index.length > 0){
                        $div.find('.item_set').each(function(){
                            if($(this).attr('data-index') == img_data_index){
                                $(this).addClass('ui-selected');
                                img_data_flag = true;
                            }
                        });
                    }
                    //첫페이지가 아닌 경우 이전 페이지 마지막 이미지 정보 있음
                    if((!img_data_index.length || !img_data_flag) && _param.curPage > 1 ){
                        $div.find('.item_set').eq(1).addClass('ui-selected');
                    }else if(!img_data_index.length || !img_data_flag){
                        $div.find('.item_set').eq(0).addClass('ui-selected');
                    }

                    if($div.find('.item_set').length > 0){

                        getRepreImg();
                        makePaging(__data.curPage);
                        setImageSelect($div);
                    }
                    else{
                        fnVisualEditReset();
                        $("#repreImgView").html('');
                    }


                },
                error: function (xhr, ajaxOptions, thrownError) {
                }
            });
        }

        function setImageSelect($div){
            $("#scrollImgDiv .shot-list-wrap").selectable({
                filter: 'div[name=selShotImgLi]',
                selecting:function(event,ui){
                    $div.find(".ui-selected").removeClass("ui-selected");
                    if ($div.find(".ui-selecting").length > 1) $(ui.selecting).removeClass("ui-selecting");
                },
                selected:function(){
                    fnVisualEditReset();
                    getRepreImg();
                }
            });
        }

        function makePaging(curPage){

            var startIdx = Math.ceil(_param.startframeindex/_param.frame_cut);
            var endIdx = Math.floor((_param.endframeindex)/_param.frame_cut)+1;

            if(_param.endframeindex%_param.frame_cut > 0){
                endIdx++;
            }
            var totCount = (endIdx-startIdx);

            var minPage = 1;
            var maxPage = Math.ceil(totCount/_param.pageCnt);
            var curPage = curPage || 1;

            var pagingObj = $('<div>').css({marginTop:'20px', textAlign: 'center'}).appendTo($('#scrollImgDiv'));
            var pagingUl = $('<ul>').css({listStyle:'none', display: 'inline-block'}).appendTo(pagingObj);

            var arrowLeft = $('<li>').css({listStyle:'none', float: 'left', marginRight: '10px', marginTop: '2px', cursor: 'pointer'}).appendTo(pagingUl);
            var arrowLeftIcon = $('<i>',{'class':'fa fa-angle-left'}).attr({'aria-hidden': true}).appendTo(arrowLeft);

            arrowLeft.on('click', function(){
                if(parseInt(curPage) - 1 < minPage){
                    alert("첫 페이지 입니다.");
                }
                else{
                    --curPage
                    _param.curPage = curPage;
                    getSectionShotList(_param);
                }
            });

            var pagingNumWrap = $('<li>').css({listStyle:'none', float: 'left'}).appendTo(pagingUl);
            var span1 = $('<span>').appendTo(pagingNumWrap);
            var input1 = $('<input type="text">').css({width: '30px', textAlign: 'center', padding: 0, margin: 0}).val(curPage).appendTo(span1);

            function setPageJump(input){
                var val = parseInt($(input).val());
                if(val>=minPage && val<=maxPage){
                    _param.curPage = val;
                    getSectionShotList(_param);
                }
                else{
                    input1.val(curPage);
                }
            }
            input1.on('keydown', function(e){
                if(e.keyCode == 13){
                    setPageJump($(this));
                }
            });
            input1.on('focusout', function(e){
                setPageJump($(this));
            });
            var span2 = $('<span>').html(" / "+maxPage).appendTo(pagingNumWrap);

            var arrowRight = $('<li>').css({listStyle:'none', float: 'left', marginLeft: '10px', marginTop: '2px', cursor: 'pointer'}).appendTo(pagingUl);
            var arrowRightIcon = $('<i>',{'class':'fa fa-angle-right'}).attr({'aria-hidden': true}).appendTo(arrowRight);


            arrowRight.on('click', function(){
                if(parseInt(curPage) + 1 > parseInt(maxPage)){
                    alert("마지막 페이지 입니다.");
                }
                else{
                    ++curPage
                    _param.curPage = curPage
                    getSectionShotList(_param);
                }

            });
        }

        // 이미지 정보 호출
        function getRepreImg(){
            // console.log("getRepreImg");
            $.blockUI({
                message: '<i class="fa fa-spinner fa-spin" style="font-size:50px;"></i> ',
                css: {
                    border: 'none',
                    padding: '15px',
                    backgroundColor: '#000',
                    '-webkit-border-radius': '10px',
                    '-moz-border-radius': '10px',
                    opacity: .5,
                    color: '#fff'
                }
            });

            var $div = $("#scrollImgDiv .ui-selected");
            var assetimgfilename = $div.find("input[name=assetfilename]").val();

            _param.frameimgid = $div.find("input[name=frameimgid]").val();
            _param.vtt_meta_idx = $div.find("input[name=vtt_meta_idx]").val();
            _param.assetimgfilename = assetimgfilename;     // 리스트 호출 후 이미지 리스트 파일명을 _param에 담아둔다.

            var _param2 = {
                videoid             : _param.videoid,
                shotname            : _param.shotname,
                shotid              : _param.shotid,
                assetimgfilename    : _param.assetimgfilename,
                assetfilename       : _param.assetfilename,
                assetfilepathorigin : _param.assetfilepathorigin,
                assetfilepath       : _param.assetfilepath,
                startframeindex     : _param.startframeindex,
                endframeindex       : _param.endframeindex,
                curPage             : _param.curPage,
                pageCnt             : _param.pageCnt,
                frame_cut           : _param.frame_cut,
                check_mode          : _param.check_mode,
                otheruserid         : _param.otheruserid,
                frameimgid          : _param.frameimgid,
                vtt_meta_idx        : _param.vtt_meta_idx
            };

            $.ajax({
                url: '<c:url value="/visual/getRepreImage"/>',
                type: 'POST',
                data: _param2,
                async: false,
                dataType: 'html',
                // contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                success: function (html) {
                    $("#repreImgView").html(html);
                    $.unblockUI();
                    $('#vttImg').on("load",function(){
                        // console.log("auto tagging load");
                        contentResize();
                        setUserSelected();
                        setBtnAutoTagging();
                        setBtnDataCopy();

                        //이전정보 호출시
                        if(getPrevMeta_call){
                            getPrevMetaData();
                            return;
                        }

                        fnLoadSelectArea($('#vttImg'));
                        if(_param.frameimgid && _param.frameimgid > 0){
                            setTimeout(function () {
                                fnVttBox();
                                $.unblockUI();
                            },100);

                        }
                        else{
                            $.unblockUI();
                        }
                        getMetaInfo();
                    });
                },
                error: function (xhr, ajaxOptions, thrownError) {
                    $.unblockUI();
                }
            });
        }

        function getMetaInfo(){
            $.ajax({
                url: '<c:url value="/visual/getMetaInfo"/>',
                type: 'POST',
                data: _param,
                async: false,
                dataType: 'html',
                success: function (html) {
                    $("#putMetaInfo").html(html);
                    area_all_move = false;
                    $("#allObjView").html("개별");
                }
            });
        }

        //오토태깅
        function getPutRepreImg() {
            if(_param.vtt_meta_idx && _param.vtt_meta_idx > 0){
                MSG.alert("작업중인 이미지입니다.");
                return false;
            }

            $.blockUI({
                message: '<i class="fa fa-spinner fa-spin" style="font-size:50px;"></i> 저장중...',
                css: {
                    border: 'none',
                    padding: '15px',
                    backgroundColor: '#000',
                    '-webkit-border-radius': '10px',
                    '-moz-border-radius': '10px',
                    opacity: .5,
                    color: '#fff'
                }
            });

            img_data_index =  $('#scrollImgDiv').find('.shot-list-wrap').find('.item_set.ui-selected').attr('data-index');

            $.ajax({
                url: '<c:url value="/visual/getPutRepreImg"/>',
                type: 'POST',
                data: _param,
                dataType: 'json',
                // contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                success: function (json) {
                    var result = "";
                    if (json.success) {
                        result = "성공";
                        getSectionShotList(_param);
                    }
                    else {
                        result = "실패"
                    }
                    toast("저장여부: " + result, json.message, "info", 5000);
                    $.unblockUI();
                },
                error: function () {
                    $.unblockUI();
                }
            });
        }

        /* 오토태킹 버튼 click */
        function setBtnAutoTagging(){
            $('#autoTagging').off('click');
            $('#autoTagging').on('click', function(){
                getPutRepreImg();
            });
        }

        /* 이전 프레임 카피 */
        function setBtnDataCopy(){
            $('#dataCopy').off('click');
            $('#dataCopy').on('click', function(){
                // console.log("dataCopy clicked!!!")
                getPrevMetaData();

            });
        }

        function getPrevMetaData(){

            // console.log("getPrevMetaData call!! 1 Phase");
            //선택이미지 앞 객체
            var $div = $("#scrollImgDiv .ui-selected").prev();
            if($div.find("input[name=vtt_meta_idx]").val() < 1 || !$div.find("input[name=vtt_meta_idx]").val()){
                MSG.alert("이전 이미지 메타 정보가 없습니다.");
                return false;
            }
            //이전 메타정보 있다면 오토태깅 여부 확인 후 재호출
            if(!_param.frameimgid || _param.frameimgid < 1){

                getPrevMeta_call = true;
                //오토태깅
                getPutRepreImg();
                return;
            }

            // console.log("getPrevMetaData call!! 2 Phase");
            $.blockUI({
                message: '<i class="fa fa-spinner fa-spin" style="font-size:50px;"></i> ',
                css: {
                    border: 'none',
                    padding: '15px',
                    backgroundColor: '#000',
                    '-webkit-border-radius': '10px',
                    '-moz-border-radius': '10px',
                    opacity: .5,
                    color: '#fff'
                }
            });

            var _param2 = {
                videoid             : _param.videoid,
                shotname            : _param.shotname,
                shotid              : _param.shotid,
                assetimgfilename    : _param.assetimgfilename,
                assetfilename       : _param.assetfilename,
                assetfilepathorigin : _param.assetfilepathorigin,
                assetfilepath       : _param.assetfilepath,
                startframeindex     : _param.startframeindex,
                endframeindex       : _param.endframeindex,
                curPage             : _param.curPage,
                pageCnt             : _param.pageCnt,
                frame_cut           : _param.frame_cut,
                check_mode          : _param.check_mode,
                otheruserid         : _param.otheruserid,
                frameimgid          : $div.find("input[name=frameimgid]").val(),
                vtt_meta_idx        : $div.find("input[name=vtt_meta_idx]").val()
            };


            $.ajax({
                url: '<c:url value="/visual/getPrevRepreImage"/>',
                type: 'POST',
                data: _param2,
                async: false,
                dataType: 'html',
                // contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                success: function (html) {
                    $("#repreImgView").html(html);
                    $.unblockUI();
                    $('#vttImg').on("load",function(){
                        // console.log("auto tagging load");
                        contentResize();
                        setUserSelected();
                        setBtnAutoTagging();
                        setBtnDataCopy();

                        fnLoadSelectArea($('#vttImg'));
                        if(_param.frameimgid && _param.frameimgid > 0){
                            setTimeout(function () {
                                fnVttBox();
                                $.unblockUI();
                            },100);

                        }
                        else{
                            $.unblockUI();
                        }

                        $.ajax({
                            url: '<c:url value="/visual/getPrevMetaInfo"/>',
                            type: 'POST',
                            data: _param2,
                            async: false,
                            dataType: 'html',
                            success: function (html) {
                                $("#putMetaInfo").html(html);
                                area_all_move = false;
                                $("#allObjView").html("개별");
                            }
                        });
                    });
                },
                error: function (xhr, ajaxOptions, thrownError) {
                    $.unblockUI();
                }
            });

            getPrevMeta_call = false;
        }

        function setUserSelected(){
            $("#userSelect").off('change');
            $("#userSelect").find("option[value='"+_param.otheruserid+"']").attr("selected","selected");
            $("#userSelect").on("change",function(){
                getSectionShotList(_param);
            });
        }

        function setBtnShowAll(){
            $('#allObjView').off('click');
            $('#allObjView').on('click', function(){
                if (area_all_move) {
                    $('[class*=layer]').show();
                    area_all_move = false;
                    $("#allObjView").html("개별");
                }
                else {
                    $('[class*=layer]').show();
                    area_all_move = true;
                    $("#allObjView").html("전체");
                }
            });
        }

        $('#btn_update_shot').on('click', function(){
            toggleUpdateShot();
        });



        setBtnNew();
        setBtnShowAll();
        contentResize();
        setBtnSendMetaData();

        //추가버튼
        function setBtnNew(){
            $('#btnNew').off('click');
            $('#btnNew').on('click', function(){
                area_all_move = false;
                $("#allObjView").html("개별");
                var tgtEditId = $("#objTabs").find(".active a").attr("id");
                var boxtype = "";
                var boxname = "";
                if (tgtEditId == "faceTab") {
                    boxtype = "face";
                    boxname = "face" + $("#vttImg").selectAreas('areas').length;
                }
                else if (tgtEditId == "personAllTab") {
                    boxtype = "person";
                    boxname = "person";
                }
                else if (tgtEditId == "objTab") {
                    boxtype = "object";
                }
                else {
                    return false;
                }

                var areas = $("#vttImg").selectAreas('areas');
                var seq = 0;
                if (areas.length > 0)
                    seq = areas[areas.length - 1].boxseq + 1;
                var areaOptions = {
                    x: Math.floor((Math.random() * 200)),
                    y: Math.floor((Math.random() * 200)),
                    width: Math.floor((Math.random() * 100)) + 50,
                    height: Math.floor((Math.random() * 100)) + 20,
                    boxtype: boxtype,
                    boxname: boxname,
                    score: 1,
                    boxseq: seq,
                    finalScore:''
                };

                $("#vttImg").selectAreas('add', areaOptions);

                var regImgYn="Y";
                var objxystr = parseInt(areaOptions.x) + ', ' + parseInt(areaOptions.y) + ', ' + parseInt(areaOptions.width) + ', ' + parseInt(areaOptions.height);
                if (areaOptions.boxtype == "face") {
                    addFaceInfo(areaOptions, objxystr, regImgYn);
                }
                else if (areaOptions.boxtype == "person") {
                    addPersonAllInfo(areaOptions, objxystr, regImgYn);
                }
                else if (areaOptions.boxtype == "object") {
                    addObjectInfo(areaOptions, objxystr, regImgYn);
                }
                areas = $("#vttImg").selectAreas('areas');
                fnDataSet(areas);

            });
        }

        /* 시작정보편집 저장/수정 버튼 클릭 시 */
        function setBtnSendMetaData(){
            $('#multiFaceFormSumit').off('click');
            $('#multiFaceFormSumit').on('click', function() {

                if(!_param.frameimgid || _param.frameimgid < 1){
                    MSG.alert("오토태깅을 실행해 주세요.");
                    return false;
                }
                if(metaDataCheck.compareData()){
                    MSG.alert("변경된 데이터가 존재하지 않습니다.");
                    return false;
                }

                var frame_id_arr = _param.assetimgfilename.split('.');
                var frame_id = frame_id_arr[0];

                var visual_info = {
                    image_id    : _param.assetfilename,
                    frame_id    : frame_id,
                    place       : $("[name=placeForm]").find("[name=place]").val() || '',
                    persons     : [],
                    objects     : []
                }

                var emotionList = ['Anger', 'Disgust', 'Fear', 'Happiness', 'Neutral', 'Sadness', 'Surprise'];
                var faceNameForm = [];

                var errorStatus = false;

                var predicateCnt = 0;

                // 인물
                $("[name=faceForm]").each(function(){
                    var tmpCoordinate1 = $(this).find("[name=faceCoordinate]").val();
                    var arrayStr1 = tmpCoordinate1.split(',');
                    var faceName = $(this).find("[name=faceName]").val();
                    var layerName = $(this).parent().attr("id");
                    var predicate = $(this).find("[name=predicate]").val() || '';

                    if(faceName === '' || faceName === 'none' || !faceName){
                        MSG.alert("인물탭의 "+layerName+"의 이름을 선택해 주세요. (none 선택불가)");
                        errorStatus = true;
                        return false;
                    }
                    if(predicate || predicate !== ''){
                        predicateCnt++;
                    }
                    var person = {
                        person_id: faceName || '',
                        person_info: {
                            face_rect: {
                                min_x: parseInt(scaleToRealImg(arrayStr1[0])),
                                min_y: parseInt(scaleToRealImg(arrayStr1[1])),
                                max_x: parseInt(scaleToRealImg(arrayStr1[2])) + parseInt(scaleToRealImg(arrayStr1[0])),
                                max_y: parseInt(scaleToRealImg(arrayStr1[3])) + parseInt(scaleToRealImg(arrayStr1[1]))
                            },
                            full_rect: {
                                min_x: null,
                                min_y: null,
                                max_x: null,
                                max_y: null
                            },
                            face_rect_score: $(this).find("[name=finalScore]").val() || '',
                            full_rect_score: '',
                            behavior: $(this).find("[name=faceAction]").val() || '',
                            predicate: predicate,
                            emotion: {}
                        },
                        related_objects: []
                    };

                    for(var j=0;j<emotionList.length;j++){
                        var faceEmotion = $(this).find("[name=faceEmotion]").val();
                        person.person_info.emotion[''+emotionList[j]] = 0;

                        // console.log("faceEmotion");
                        if(faceEmotion && $.inArray(faceEmotion, emotionList) > -1 && faceEmotion == emotionList[j]){
                            person.person_info.emotion[''+emotionList[j]] = 10;
                        }
                    }

                    visual_info.persons.push(person);
                });

                if(errorStatus) return false;

                // 인물전체
                var isCheck = false;
                for(var i=0;i<visual_info.persons.length;i++){
                    isCheck = false;
                    $("[name=personAllForm]").each(function(){
                        var personAllName = $(this).find("[name=personAllName]").val();
                        if(personAllName === visual_info.persons[i].person_id && personAllName !== "none"){
                            isCheck = true;
                        }
                    });
                    if(!isCheck){
                        MSG.alert("인물전체탭의 "+visual_info.persons[i].person_id+"의 이름이 없습니다.");
                        errorStatus = true;
                        return false;
                    }
                }

                if(errorStatus) return false;

                $("[name=personAllForm]").each(function(){
                    var personAllName = $(this).find("[name=personAllName]").val();
                    var tmpCoordinate2 = $(this).find("[name=personAllCoordinate]").val();
                    var arrayStr2 = tmpCoordinate2.split(',');
                    var person_sw = true;
                    var layerName = $(this).parent().attr("id");

                    for(var i=0;i<visual_info.persons.length;i++){
                        if(personAllName === visual_info.persons[i].person_id && personAllName !== "none"){
                            visual_info.persons[i].person_info.full_rect = {
                                min_x: parseInt(scaleToRealImg(arrayStr2[0])),
                                min_y: parseInt(scaleToRealImg(arrayStr2[1])),
                                max_x: parseInt(scaleToRealImg(arrayStr2[2])) + parseInt(scaleToRealImg(arrayStr2[0])),
                                max_y: parseInt(scaleToRealImg(arrayStr2[3])) + parseInt(scaleToRealImg(arrayStr2[1]))
                            }

                            visual_info.persons[i].person_info.full_rect_score = $(this).find("[name=finalScore]").val();

                            person_sw = false;
                        }
                    }

                    if(person_sw) {
                        if(personAllName === '' || personAllName === null){
                            var obj = {
                                object_id: 'person',
                                score: $(this).find("[name=finalScore]").val() || '',
                                object_rect: {
                                    min_x: parseInt(scaleToRealImg(arrayStr2[0])),
                                    min_y: parseInt(scaleToRealImg(arrayStr2[1])),
                                    max_x: parseInt(scaleToRealImg(arrayStr2[2])) + parseInt(scaleToRealImg(arrayStr2[0])),
                                    max_y: parseInt(scaleToRealImg(arrayStr2[3])) + parseInt(scaleToRealImg(arrayStr2[1]))
                                }
                            }
                            visual_info.objects.push(obj);
                        }else{
                            MSG.alert("인물탭의 "+personAllName+"의 이름이 없습니다.");
                            errorStatus = true;
                            return false;
                        }
                    }
                });

                if(errorStatus) return false;

                // 객체
                var objectCtn = 0;
                for(var i=0;i<visual_info.persons.length;i++){
                    if(visual_info.persons[i].person_info.predicate !== '' && visual_info.persons[i].person_info.predicate !== 'none'){
                        $("[name=objForm]").each(function(){
                            var personName = $(this).find("[name=personName]").val();
                            if(personName === visual_info.persons[i].person_id && personName!=='none'){
                                objectCtn++;
                            }
                        });
                        if(objectCtn === 0){
                            MSG.alert(visual_info.persons[i].person_id+"의 객체가 필요합니다.");
                            errorStatus = true;
                            return false;
                        }else if(objectCtn > 1){
                            MSG.alert(visual_info.persons[i].person_id+"의 객체수가 많습니다.");
                            errorStatus = true;
                            return false;
                        }
                    }
                    objectCtn = 0;
                }

                if(errorStatus) return false;

                $("[name=objForm]").each(function(){
                    var personName = $(this).find("[name=personName]").val();
                    var relatedObj = $(this).find("[name=relatedObj]").val();
                    var tmpCoordinate3 = $(this).find("[name=objCoordinate]").val();
                    var layerName = $(this).parent().attr("id");

                    if(!relatedObj || relatedObj === ''){
                        MSG.alert("객체탭의 "+layerName+"의 객채명을 선택해 주세요.");
                        errorStatus = true;
                        return false;
                    }

                    isCheck = false;
                    if(personName && personName !== ''){
                        for(var i=0;i<visual_info.persons.length;i++){
                            if(personName == visual_info.persons[i].person_id && personName!='none'){
                                isCheck = true;
                            }
                        }
                        if(!isCheck){
                            MSG.alert("객체탭의 "+layerName+"의 관련인물명이 인물탭에 없습니다.");
                            errorStatus = true;
                            return false;
                        }
                    }

                    var arrayStr3 = tmpCoordinate3.split(',');
                    var obj_sw = true;

                    for(var i=0;i<visual_info.persons.length;i++){

                        if(personName == visual_info.persons[i].person_id && personName!='none'){

                            if(visual_info.persons[i].person_info.predicate === ''){
                                MSG.alert("객체탭의 "+layerName+"의 관련인물명의 서술어가 없습니다.");
                                errorStatus = true;
                                return false;
                            }

                            var obj_obj = {
                                related_object_id: relatedObj || '',
                                score: $(this).find("[name=finalScore]").val() || '',
                                related_object_rect: {
                                    min_x: parseInt(scaleToRealImg(arrayStr3[0])),
                                    min_y: parseInt(scaleToRealImg(arrayStr3[1])),
                                    max_x: parseInt(scaleToRealImg(arrayStr3[2])) + parseInt(scaleToRealImg(arrayStr3[0])),
                                    max_y: parseInt(scaleToRealImg(arrayStr3[3])) + parseInt(scaleToRealImg(arrayStr3[1]))
                                }
                            };
                            visual_info.persons[i].related_objects.push(obj_obj);
                            obj_sw = false;
                        }
                    }

                    if(obj_sw){
                        var obj = {
                            object_id: relatedObj || '',
                            score: $(this).find("[name=finalScore]").val() || '',
                            object_rect: {
                                min_x: parseInt(scaleToRealImg(arrayStr3[0])),
                                min_y: parseInt(scaleToRealImg(arrayStr3[1])),
                                max_x: parseInt(scaleToRealImg(arrayStr3[2])) + parseInt(scaleToRealImg(arrayStr3[0])),
                                max_y: parseInt(scaleToRealImg(arrayStr3[3])) + parseInt(scaleToRealImg(arrayStr3[1]))
                            }
                        }
                        visual_info.objects.push(obj);
                    }
                });

                if(errorStatus) return false;

                /*
                                $("[name=descForm]").each(function(){
                                    var obj = [];
                                    for(var i=0;i<5;i++){
                                        obj.push($(this).find("[name=image_descs"+(i+1)+"]").val() || '');
                                    }
                                    visual_info.image_descs = obj;
                                });
                */
                // console.log(JSON.stringify(visual_info));

                img_data_index =  $('#scrollImgDiv').find('.shot-list-wrap').find('.item_set.ui-selected').attr('data-index');
                _param.metaData = JSON.stringify(visual_info);

                if(!_param.vtt_meta_idx){
                    _param.vtt_meta_idx = 0;
                }

                // console.log(_param);

                $.ajax({
                    url: '<c:url value="/visual/getPutMetaInfo"/>',
                    type: 'POST',
                    data: _param,
                    async: false,
                    dataType: 'html',
                    // contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                    success: function (html) {
                        $("#putMetaInfo").html(html);
                        objCnt = 0;
                        toast("시각정보리스트", "저장되었습니다.", "info", 5000);
                        getSectionShotList(_param);
                        contentResize();
                    },
                    error: function (xhr, ajaxOptions, thrownError) {
                        toast("시각정보리스트", "저장이 실패 하였습니다..", "info", 5000);
                    },
                    complete : function() {
                    }
                });
            });
        }


        var metaDataCheck = {
            originData:[],
            newData:[],
            formList:['faceForm', 'personAllForm', 'objForm', 'placeForm', 'descForm'],
            setData: function(){
                var _this = this;
                _this.originData = [];
                for(var i=0;i<this.formList.length;i++){
                    $("[name="+this.formList[i]+"]").each(function(idx){
                        $(this).find('input').each(function(){
                            var val = $(this).val() || '';
                            _this.originData.push(val);
                        });

                    });
                }
            },
            compareData: function(){
                var _this = this;
                _this.newData = [];
                for(var i=0;i<this.formList.length;i++){
                    $("[name="+this.formList[i]+"]").each(function(){
                        $(this).find('input').each(function(){
                            var val = $(this).val() || '';
                            _this.newData.push(val);
                        });
                    });
                }

                if(this.originData.compare(this.newData)){
                    return true;
                }
                else{
                    return false;
                }
            }
        }


        var imgWidth = $(ImgId).width();
        //레이어 그리기
        function fnVttBox(){
            fnVisualEditReset();
            var areasArr = [];
            var repJosn = $("#represent-img-nav").find("[name='repJson']").val();
            var savechk = $("#represent-img-nav").find("[name='savechk']").val();

            var jsonObj = JSON.parse(repJosn) || '';
            var imgRatio = ($(ImgId).width() / $(ImgId).prop("naturalWidth"));

            if(savechk=='true'){

                var arrayFaceData = jsonObj.persons;
                for (var i = 0; i < arrayFaceData.length ; i++) {
                    var min_x = arrayFaceData[i].person_info.face_rect.min_x;
                    var min_y = arrayFaceData[i].person_info.face_rect.min_y;
                    var max_x = arrayFaceData[i].person_info.face_rect.max_x;
                    var max_y = arrayFaceData[i].person_info.face_rect.max_y;
                    var emotion_arr = arrayFaceData[i].person_info.emotion;
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

                    var point = ClienPoint(min_x, min_y, max_x, max_y);
                    var areaOptions = {
                        id: arrayFaceData[i].person_id +i,
                        x: scaleToPopImg(point.x,imgRatio),
                        y: scaleToPopImg(point.y,imgRatio),
                        width: scaleToPopImg(point.w,imgRatio),
                        height: scaleToPopImg(point.h,imgRatio),
                        boxname: arrayFaceData[i].person_id,
                        boxtype:"face",
                        behavior: arrayFaceData[i].person_info.behavior,
                        predicate: arrayFaceData[i].person_info.predicate,
                        emotion: emotion,
                        boxseq: areasArr.length,
                        finalScore:arrayFaceData[i].person_info.face_rect_score===undefined?'':arrayFaceData[i].person_info.face_rect_score
                    };
                    areasArr.push(areaOptions);

                }

                var arrayPersonData1 =  jsonObj.persons;
                for (var i = 0; i < arrayPersonData1.length ; i++) {
                    if(arrayPersonData1[i].person_info.full_rect.max_x) {
                        var min_x = arrayPersonData1[i].person_info.full_rect.min_x;
                        var min_y = arrayPersonData1[i].person_info.full_rect.min_y;
                        var max_x = arrayPersonData1[i].person_info.full_rect.max_x;
                        var max_y = arrayPersonData1[i].person_info.full_rect.max_y;

                        var point = ClienPoint(min_x, min_y, max_x, max_y);
                        var areaOptions = {
                            id: 'person' + i,
                            x: scaleToPopImg(point.x, imgRatio),
                            y: scaleToPopImg(point.y, imgRatio),
                            width: scaleToPopImg(point.w, imgRatio),
                            height: scaleToPopImg(point.h, imgRatio),
                            boxname: 'person',
                            boxtype: "person",
                            relPerson: arrayPersonData1[i].person_id,
                            boxseq: areasArr.length,
                            finalScore:arrayPersonData1[i].person_info.full_rect_score===undefined?'':arrayPersonData1[i].person_info.full_rect_score
                        };
                        areasArr.push(areaOptions);
                    }
                }

                var arrayPersonData2 =  jsonObj.objects;
                for (var i = 0; i < arrayPersonData2.length ; i++) {
                    if (arrayPersonData2[i].object_id == 'person') {
                        var min_x = arrayPersonData2[i].object_rect.min_x;
                        var min_y = arrayPersonData2[i].object_rect.min_y;
                        var max_x = arrayPersonData2[i].object_rect.max_x;
                        var max_y = arrayPersonData2[i].object_rect.max_y;

                        var point = ClienPoint(min_x, min_y, max_x, max_y);
                        var areaOptions = {
                            id: 'person' + (i + arrayPersonData1.length),
                            x: scaleToPopImg(point.x, imgRatio),
                            y: scaleToPopImg(point.y, imgRatio),
                            width: scaleToPopImg(point.w, imgRatio),
                            height: scaleToPopImg(point.h, imgRatio),
                            boxname: 'person',
                            boxtype: "person",
                            relPerson: arrayPersonData2[i].object_id,
                            boxseq: areasArr.length,
                            finalScore:arrayPersonData2[i].score===undefined?'':arrayPersonData2[i].score
                        };
                        areasArr.push(areaOptions);
                    }
                }


                var arrayObjData1 = jsonObj.persons;
                var objCnt = 0;
                for (var i = 0; i < arrayObjData1.length ; i++) {
                    var relatedObjData = arrayObjData1[i];
                    for(var j=0;j<relatedObjData.related_objects.length;j++){
                        objCnt++;
                        var min_x = relatedObjData.related_objects[j].related_object_rect.min_x;
                        var min_y = relatedObjData.related_objects[j].related_object_rect.min_y;
                        var max_x = relatedObjData.related_objects[j].related_object_rect.max_x;
                        var max_y = relatedObjData.related_objects[j].related_object_rect.max_y;

                        var point = ClienPoint(min_x, min_y, max_x, max_y);

                        var areaOptions = {
                            id: 'object'+objCnt,
                            x: scaleToPopImg(point.x,imgRatio),
                            y: scaleToPopImg(point.y,imgRatio),
                            width: scaleToPopImg(point.w,imgRatio),
                            height: scaleToPopImg(point.h,imgRatio),
                            boxname: relatedObjData.related_objects[j].related_object_id,
                            boxtype: "object",
                            relPerson: relatedObjData.person_id,
                            boxseq: areasArr.length,
                            finalScore:relatedObjData.related_objects[j].score===undefined?'':relatedObjData.related_objects[j].score
                        };
                        areasArr.push(areaOptions);
                    }

                }

                var arrayObjData2 = jsonObj.objects;
                for (var i = 0; i < arrayObjData2.length ; i++) {
                    if (arrayPersonData2[i].object_id != 'person') {
                        objCnt++
                        var min_x = arrayObjData2[i].object_rect.min_x;
                        var min_y = arrayObjData2[i].object_rect.min_y;
                        var max_x = arrayObjData2[i].object_rect.max_x;
                        var max_y = arrayObjData2[i].object_rect.max_y;

                        var point = ClienPoint(min_x, min_y, max_x, max_y);

                        var areaOptions = {
                            id: 'object' + objCnt,
                            x: scaleToPopImg(point.x, imgRatio),
                            y: scaleToPopImg(point.y, imgRatio),
                            width: scaleToPopImg(point.w, imgRatio),
                            height: scaleToPopImg(point.h, imgRatio),
                            boxname: arrayObjData2[i].object_id,
                            boxtype: "object",
                            relPerson: '',
                            boxseq: areasArr.length,
                            finalScore:arrayPersonData2[i].score===undefined?'':arrayPersonData2[i].score
                        };
                        areasArr.push(areaOptions);
                    }

                }

                var arrayPlaceData = {
                    id: jsonObj.place,
                    boxname: jsonObj.place,
                    boxtype:"place",
                    boxseq: areasArr.length
                };
                areasArr.push(arrayPlaceData);
                /*
                                var arrayDescData = {
                                    id: 'image_descs',
                                    image_descs: jsonObj.image_descs,
                                    boxtype:"description",
                                    boxseq: areasArr.length
                                };
                                areasArr.push(arrayDescData);
                */
            }

            // 서강대 JSON
            else{

                var jsonResultData = jsonObj.results;
                var arrayFaceData = [];
                var arrayObjData1 = [];
                for(var i=0;i<jsonResultData.length;i++){
                    var module_name = jsonResultData[i].module_name;
                    if(module_name == 'missoh.face'){
                        arrayFaceData = jsonResultData[i].module_result;
                    }
                    else if(module_name == 'object'){
                        arrayObjData1 = jsonResultData[i].module_result;
                    }
                }

                for (var i=0;i<arrayFaceData.length;i++) {
                    var position = arrayFaceData[i].position;
                    var label = arrayFaceData[i].label;

                    // asc: false, desc: true
                    label.sort(fnSortList('score', true, parseFloat));

                    var description = label[0].description;
                    var score = label[0].score.toString().substring(0, 3);

                    var areaOptions = {
                        id: description + i,
                        x: scaleToPopImg(position.x,imgRatio),
                        y: scaleToPopImg(position.y,imgRatio),
                        width: scaleToPopImg(position.w,imgRatio),
                        height: scaleToPopImg(position.h,imgRatio),
                        //boxname: 'face',
                        boxname: description,
                        boxtype:"face",
                        behavior: '',
                        emotion: '',
                        gender:'',
                        generation: '',
                        boxseq: areasArr.length,
                        finalScore:score
                    };
                    areasArr.push(areaOptions);

                }

                var psrsonAllCnt = 0;
                for (var i= 0;i<arrayObjData1.length;i++) {

                    var position = arrayObjData1[i].position;
                    var label = arrayObjData1[i].label;
                    var description = label[0].description;
                    var score = label[0].score.toString().substring(0, 3);

                    if(description=='person'){
                        psrsonAllCnt++;
                        // asc: false, desc: true
                        label.sort(fnSortList('score', true, parseFloat));

                        var areaOptions = {
                            id: 'person' + psrsonAllCnt,
                            x: scaleToPopImg(position.x, imgRatio),
                            y: scaleToPopImg(position.y, imgRatio),
                            width: scaleToPopImg(position.w, imgRatio),
                            height: scaleToPopImg(position.h, imgRatio),
                            boxname: description,
                            boxtype: "person",
                            relPerson: '',
                            boxseq: areasArr.length,
                            finalScore:score
                        };
                        areasArr.push(areaOptions);
                    }
                }

                var objCnt = 0;
                for (var i= 0;i<arrayObjData1.length;i++) {

                    var position = arrayObjData1[i].position;
                    var label = arrayObjData1[i].label;
                    var description = label[0].description;
                    var score = label[0].score.toString().substring(0, 3);

                    if(description!='person'){
                        objCnt++;
                        // asc: false, desc: true
                        label.sort(fnSortList('score', true, parseFloat));

                        var areaOptions = {
                            id: 'object' + objCnt,
                            x: scaleToPopImg(position.x, imgRatio),
                            y: scaleToPopImg(position.y, imgRatio),
                            width: scaleToPopImg(position.w, imgRatio),
                            height: scaleToPopImg(position.h, imgRatio),
                            boxname: description,
                            boxtype: "object",
                            relPerson: '',
                            boxseq: areasArr.length,
                            finalScore:score
                        };
                        areasArr.push(areaOptions);
                    }
                }
                var arrayPlaceData = {
                    id: '',
                    boxname: '',
                    boxtype:"place",
                    boxseq: areasArr.length
                };
                areasArr.push(arrayPlaceData);
                /*
                                var arrayDescData = {
                                    id: 'image_descs',
                                    image_descs: ['','','','',''],
                                    boxtype:"description",
                                    boxseq: areasArr.length
                                };
                                areasArr.push(arrayDescData);
                */
            }

            imgWidth = $(ImgId).width();


            $("#vttImg").selectAreas('add', areasArr);
            displayAreas(areasArr);


        }

        // 편집창 분류 및 추가
        function addMetaObjEditDiv (areas,regImgYn) {

            //var no = area.id.substr(5);
            $.each(areas, function (id, area) {
                if(typeof area.boxname == "undefined"){
                    area.boxname = "";
                }
                // X:area.x , Y:area.y , W:area.width , H: area.height

                var objxystr = parseInt(area.x) + ', ' + parseInt(area.y) + ', ' + parseInt(area.width) + ', ' + parseInt(area.height);

                if (area.boxtype == "face") {
                    addFaceInfo(area, objxystr, regImgYn);
                }
                else if (area.boxtype == "person") {
                    addPersonAllInfo(area, objxystr, regImgYn);
                }
                else if (area.boxtype == "object") {
                    addObjectInfo(area, objxystr, regImgYn);
                }
                else if (area.boxtype == "place") {
                    addPlaceInfo(area, objxystr, regImgYn);
                }
                else if (area.boxtype == "description") {
                    addDescInfo(area, objxystr, regImgYn);
                }

            });

            fnDataSet(areas);
            $.unblockUI();
            metaDataCheck.setData();

        }

        var makeHtml = {
            select: function(opts) {

                var wrap = $('<div>',{'class':'form-group edit-form'});
                var label = $('<label>',{'class':'control-label col-md-3 col-sm-3 col-xs-12'}).css({fontSize: '12px',textAlign: 'left'}).html(opts.labelName).appendTo(wrap);
                var itemWrap = $('<div>',{'class':'col-md-9 col-sm-9 col-xs-12'}).appendTo(wrap);
                var select = $('<select>',{'class':'form-control', id: opts.selectId}).attr({name: opts.selectName, readonly: 'readonly'}).appendTo(itemWrap);

                if(opts.option.length > 1){
                    opts.option.sort(fnSortList('code_reference', false, function(a){return a.toUpperCase()}));
                }
                for(var i=0;i<opts.option.length;i++){
                    var option = $('<option>').val(opts.option[i].code_name).html(opts.option[i].code_reference).appendTo(select);
                    if(opts.selectVal == opts.option[i].code_name){
                        option.prop({selected: true});
                    }
                }
                if(!opts.selectVal){select.val('');}
                $(select).combobox({
                    select: function(event, ui){
                        if(opts.changeCallBack && (typeof(opts.changeCallBack)).toLowerCase() == 'function'){
                            opts.changeCallBack.call(null, event, ui);
                        }
                    }
                });

                return wrap;
            },
            input: function(opts) {
                var wrap = $('<div>',{'class':'form-group edit-form'});
                var label = $('<label>',{'class':'control-label col-md-3 col-sm-3 col-xs-12'}).css({fontSize: '12px',textAlign: 'left'}).html(opts.labelName).appendTo(wrap);
                var itemWrap = $('<div>',{'class':'col-md-9 col-sm-9 col-xs-12'}).appendTo(wrap);
                var input = $('<input type="text" class="form-control form-control-sm" name="'+opts.inputName+'" id="'+opts.inputId+'" placeholder="'+opts.placeholder+'" >').val(opts.value).appendTo(itemWrap);
                if(opts.addClass && opts.addClass.length>0){
                    for(var i=0;i<opts.addClass.length;i++){
                        input.addClass(opts.addClass[i]);
                    }
                }
                if(opts.readonly){
                    input.prop({readonly: true});
                }
                return wrap;
            }
        }

        function delEditBox(no){
            if(no ==null){
                return false;
            }
            area_all_move = false;
            $("#allObjView").html("개별");
            $(ImgId).selectAreas('remove',no);
            $("#layer"+no).remove();
        }

        //레이어 복사(객체)
        function copyEditBox(no){
            if(no ==null){
                return false;
            }

            var areas = $("#vttImg").selectAreas('areas');
            var area = null;
            for(var i = 0; i<areas.length; i++){
                if(areas[i].id=='layer'+no){
                    area = areas[i];
                }
            }
            var seq = 0;
            if (areas.length > 0)
                seq = areas[areas.length - 1].boxseq + 1;
            var areaOptions = {
                x: area.x,
                y: area.y,
                width: area.width,
                height: area.height,
                boxtype: "object",
                score: 1,
                boxname: area.boxname,
                boxseq: seq,
                relPerson: area.relPerson,
            };

            $("#vttImg").selectAreas('add', areaOptions);

            var regImgYn = "Y";
            var objxystr = parseInt(areaOptions.x) + ', ' + parseInt(areaOptions.y) + ', ' + parseInt(areaOptions.width) + ', ' + parseInt(areaOptions.height);
            addObjectInfo(areaOptions, objxystr, regImgYn);
        }

        // 시각정보 편집 select box 생성
        function addFaceInfo(area, objxystr, regImgYn) {
            // console.log("addFaceInfo");

            var RepImageYn = false;
            if(regImgYn =="Y"){
                RepImageYn = true;
            }

            var vttBoxNo = (area.id).replace("layer", "");
            var wrapper = $("#faceFormDiv");

            var div1 = $('<div>',{'class':'form-wrap', id: area.id}).attr({name: 'formBody'}).appendTo(wrapper);
            var div1_1 = $('<div>',{'class': 'panel panel-heading'}).appendTo(div1);
            var div1_1_1 = $('<div>',{'class':'layer-name'}).html(area.id).appendTo(div1_1);

            if(RepImageYn){
                var btnDelWrap = $('<div>',{'class':'col-md-3 col-sm-3 col-xs-12'}).appendTo(div1_1);
                var btnDel = $('<button>',{'class':'btn-sm btn-red'}).attr({type: 'submit', name:'btnDel'}).html("삭제").appendTo(btnDelWrap);
                btnDel.on('click', function(){
                    delEditBox(vttBoxNo);
                });
            }

            var form1 = $('<form>',{'class':'needs-validation faceForm'}).attr({name: 'faceForm', action:'getPutMetaInfo'}).appendTo(div1);
            var score = $('<input type="hidden" name="finalScore" '+'value='+area.finalScore+'>').appendTo(form1);
            var select1 = makeHtml.select({
                labelName: '이름', selectId: "faceName_" +area.boxseq, selectName: 'faceName', selectVal: (area.boxname.indexOf('face') > -1)?'':area.boxname,
                option: [
                    <c:forEach var="map" items="${codeMap01}" varStatus="i">
                    {code_name: "${map.code_name}", code_reference: "${map.code_reference}"},
                    </c:forEach>
                ],
                changeCallBack: function(event, ui){
                    if(ui.item.value == '' || ui.item.value == 'none'){
                        setFaceComboNone(true);
                    }
                    else{
                        setFaceComboNone(false);
                    }
                }
            }).appendTo(form1);


            var input2 = makeHtml.input({
                labelName: '얼굴좌표', inputName: "faceCoordinate", inputId: '',
                placeholder: objxystr, value: objxystr, readonly: true,
                addClass:['coordinate']
            }).appendTo(form1);

            if(RepImageYn){
                var select3 = makeHtml.select({
                    labelName: '감정', selectId: "faceEmotion_" +area.boxseq, selectName: 'faceEmotion', selectVal: area.emotion ? area.emotion :'Neutral',
                    option: [
                        <c:forEach var="map" items="${codeMap03}" varStatus="i">
                        {code_name: "${map.code_name}", code_reference: "${map.code_reference}"},
                        </c:forEach>
                    ]
                }).appendTo(form1);

                var select4 = makeHtml.select({
                    labelName: '행동', selectId: "faceAction_" +area.boxseq, selectName: 'faceAction', selectVal: area.behavior ? area.behavior : 'none',
                    option: [
                        <c:forEach var="map" items="${codeMap02}" varStatus="i">
                        {code_name: "${map.code_name}", code_reference: "${map.code_reference}"},
                        </c:forEach>
                    ]
                }).appendTo(form1);

                var select5 = makeHtml.select({
                    labelName: '서술어', selectId: "predicate_" +area.boxseq, selectName: 'predicate', selectVal: area.predicate,
                    option: [
                        <c:forEach var="map" items="${codeMap11}" varStatus="i">
                        {code_name: "${map.code_name}", code_reference: "${map.code_reference}"},
                        </c:forEach>
                    ]
                }).appendTo(form1);
            }

            form1.find('select[name=faceName]').next().find('.ui-autocomplete-input').on('keydown', function(e){
                if(e.keyCode == 9){
                    checkFaceName();
                }
            });

            function checkFaceName(){
                var _facename = form1.find('select[name=faceName]').next().find('.ui-autocomplete-input').val();
                if(_facename == '' || _facename == 'none'){
                    setFaceComboNone(true);
                }
                else{
                    setFaceComboNone(false);
                }
            }
            checkFaceName();

            function setFaceComboNone(bool){
                // console.log("setFaceComboNone");

                if(bool){
                    form1.find('select[name=faceEmotion]').val('Neutral');
                    form1.find('select[name=faceEmotion]').next().find('.ui-autocomplete-input').val('Neutral');
                    form1.find('select[name=faceAction]').val('none');
                    form1.find('select[name=faceAction]').next().find('.ui-autocomplete-input').val('none');

                }
            }

            form1.find('select[name=predicate]').next().find('.ui-autocomplete-input').on('blur', function(e){
                personNameCtrl();
            });

            var count = $("form[name=faceForm]").length;
            $("#faceTabNo").text(count);
        }

        //인물 전체정보 추가
        function addPersonAllInfo(area, objxystr,regImgYn) {

            var RepImageYn =false;
            if(regImgYn =="Y"){
                RepImageYn = true;
            }
            var vttBoxNo = (area.id).replace("layer", "");
            var wrapper = $("#personAllDiv");

            var div1 = $('<div>',{'class':'form-wrap', id: area.id}).attr({name: 'formBody'}).appendTo(wrapper);
            var div1_1 = $('<div>',{'class': 'panel panel-heading'}).appendTo(div1);
            var div1_1_1 = $('<div>',{'class':'layer-name'}).html(area.id).appendTo(div1_1);

            if(RepImageYn){
                var btnDelWrap = $('<div>',{'class':'col-md-3 col-sm-3 col-xs-12'}).appendTo(div1_1);
                var btnDel = $('<button>',{'class':'btn-sm btn-red'}).attr({type: 'submit', name:'btnDel'}).html("삭제").appendTo(btnDelWrap);
                btnDel.on('click', function(){
                    delEditBox(vttBoxNo);
                });
            }
            var form1 = $('<form>',{'class':'needs-validation personAllForm'}).attr({name: 'personAllForm'}).appendTo(div1);
            var score = $('<input type="hidden" name="finalScore" '+'value='+area.finalScore+'>').appendTo(form1);

            var select1 = makeHtml.select({
                labelName: '이름', selectId: "personAll_" +area.boxseq, selectName: 'personAllName', selectVal: (area.relPerson=='person')?'':area.relPerson,
                option: [
                    <c:forEach var="map" items="${codeMap01}" varStatus="i">
                    {code_name: "${map.code_name}", code_reference: "${map.code_reference}"},
                    </c:forEach>
                ]
            }).appendTo(form1);

            var input2 = makeHtml.input({
                labelName: '전체좌표', inputName: "personAllCoordinate", inputId: '',
                placeholder: objxystr, value: objxystr, readonly: true,
                addClass:['coordinate']
            }).appendTo(form1);

            var count = $("form[name=personAllForm]").length;
            $("#personAllTabNo").text(count);
        }

        //객체정보 추가
        function addObjectInfo(area,objxystr,regImgYn) {

            var RepImageYn =false;
            if(regImgYn =="Y"){
                RepImageYn = true;
            }
            var vttBoxNo = (area.id).replace("layer", "");
            var wrapper = $("#objTabDiv");

            var div1 = $('<div>',{'class':'form-wrap', id: area.id}).attr({name: 'formBody'}).appendTo(wrapper);
            var div1_1 = $('<div>',{'class': 'panel panel-heading'}).appendTo(div1);
            var div1_1_1 = $('<div>',{'class':'layer-name'}).html(area.id).appendTo(div1_1);

            if(RepImageYn){
                var btnDelWrap = $('<div>',{'class':'col-md-2 col-sm-2 col-xs-6'}).appendTo(div1_1);
                var btnDel = $('<button>',{'class':'btn-sm btn-red'}).attr({type: 'submit', name:'btnDel'}).html("삭제").appendTo(btnDelWrap);
                btnDel.on('click', function(){
                    delEditBox(vttBoxNo);
                });
                var btnCopyWrap = $('<div>',{'class':'col-md-2 col-sm-2 col-xs-6'}).appendTo(div1_1);
                var btnCopy = $('<button>',{'class':'btn-sm btn-success'}).attr({type: 'submit', name:'btnCopy'}).html("복제").appendTo(btnCopyWrap);
                btnCopy.on('click', function(){
                    copyEditBox(vttBoxNo);
                });
            }

            var form1 = $('<form>',{'class':'needs-validation objForm'}).attr({name: 'objForm'}).appendTo(div1);
            var score = $('<input type="hidden" name="finalScore" '+'value='+area.finalScore+'>').appendTo(form1);

            var select1 = makeHtml.select({
                labelName: '관련인물명', selectId: "personName_" +area.boxseq, selectName: 'personName', selectVal: (area.relPerson=='person')?'':area.relPerson,
                option: [
                    <c:forEach var="map" items="${codeMap01}" varStatus="i">
                    {code_name: "${map.code_name}", code_reference: "${map.code_reference}"},
                    </c:forEach>
                ]
            }).appendTo(form1);

            var select2 = makeHtml.select({
                labelName: '객체명', selectId: "relatedObj_" +area.boxseq, selectName: 'relatedObj', selectVal: area.boxname,
                option: [
                    <c:forEach var="map" items="${codeMap06}" varStatus="i">
                    {code_name: "${map.code_name}", code_reference: "${map.code_reference}"},
                    </c:forEach>
                ]
            }).appendTo(form1);

            var input3 = makeHtml.input({
                labelName: '객체좌표', inputName: "objCoordinate", inputId: '',
                placeholder: objxystr, value: objxystr, readonly: true,
                addClass:['coordinate']
            }).appendTo(form1);

            var count = $("form[name=objForm]").length;
            $("#objTabNo").text(count);

            $("[name=objForm]").each(function(){
                form1.find('select[name=personName]').next().find('.ui-autocomplete-input').prop("readonly", "readonly");
            });

            personNameCtrl();
        }

        function personNameCtrl(){
            var obj = $('form[name=objForm]').find('select[name=personName]').next().find('.ui-autocomplete-input');
            var predicateCnt = 0;
            $("[name=faceForm]").each(function(){
                var predicate = $(this).find("[name=predicate]").val() || '';
                if(predicate !== '' && predicate !== 'none'){
                    predicateCnt++;
                }
            });

            if(predicateCnt > 0){
                $("[name=objForm]").each(function(){
                    obj.prop("readonly", "");
                });
            }else{
                $("[name=objForm]").each(function(){
                    obj.prop("readonly", "readonly");
                });
            }
        }

        //장소 정보 추가
        function addPlaceInfo(area, objxystr, regImgYn) {

            var vttBoxNo = (area.id).replace("layer", "");
            var wrapper = $("#placeFormDiv");

            var div1 = $('<div>',{'class':'form-wrap', id: area.id}).attr({name: 'formBody'}).appendTo(wrapper);
            var div1_1 = $('<div>',{'class': 'panel panel-heading'}).appendTo(div1);
            var div1_1_1 = $('<div>',{'class':'layer-name'}).html(area.id).appendTo(div1_1);

            var form1 = $('<form>',{'class':'needs-validation objForm'}).attr({name: 'placeForm'}).appendTo(div1);

            var select1 = makeHtml.select({
                labelName: '장소', selectId: "place_" +area.boxseq, selectName: 'place', selectVal: area.boxname === '' ? 'none' : area.boxname,
                option: [
                    <c:forEach var="map" items="${codeMap05}" varStatus="i">
                    {code_name: "${map.code_name}", code_reference: "${map.code_reference}"},
                    </c:forEach>
                ]
            }).appendTo(form1);

        }

        // 이미지 묘사 추가
        function addDescInfo(area, objxystr, regImgYn){

            var vttBoxNo = (area.id).replace("layer", "");
            var wrapper = $("#descFormDiv");

            var div1 = $('<div>',{'class':'form-wrap', id: area.id}).attr({name: 'formBody'}).appendTo(wrapper);
            var div1_1 = $('<div>',{'class': 'panel panel-heading'}).appendTo(div1);
            var div1_1_1 = $('<div>',{'class':'layer-name'}).html(area.id).appendTo(div1_1);

            var form1 = $('<form>',{'class':'needs-validation descForm'}).attr({name: 'descForm'}).appendTo(div1);

            for(var i=0;i<5;i++){
                var key = i+1;
                var input = makeHtml.input({
                    labelName: '묘사'+key, inputName: "image_descs"+key, inputId: 'image_descs_'+key+'_'+area.boxseq,
                    placeholder: '', value: area.image_descs[i] || '', readonly: false
                }).appendTo(form1);
            }
        }

        //레이어 page데이터 유지를 위한 이벤트
        var fnDataSet = function(areas){
            $("[name=formBody] select,input[name=placeDetail]").on("change",function(){
                var layerId = $(this).parents("[name=formBody]").attr("id");

                var value = $(this).val();
                var key = $(this).attr("name");
                var setKeyArr = {
                    faceName: 'boxname', faceAction: 'behavior', faceEmotion: 'emotion',
                    predicate: 'predicate', personAllName: 'relPerson', personName: 'relPerson',
                    relatedObj: 'boxname', place: 'boxname', placeDetail: 'spot',
                    relatedPeople: 'relPerson'
                };
                $.each(areas, function (idx, area) {
                    if(layerId =="layer"+area.boxseq) {
                        area.score=1;
                        for(var a in setKeyArr){
                            if(key == a){
                                area[setKeyArr[a]] = value;
                            }
                        }
                    }
                });

            });
        }

        var __selectAreas;
        function fnLoadSelectArea(ImgIdObj) {
            if (ImgIdObj) {
                __selectAreas = ImgIdObj.selectAreas({
                    minSize: [10, 10],
                    onChanged: onLayerChanged,
                    allowSelect: false,
                    allowNudge: false
                });
            }
        }

        //레이어 변경
        var objCnt = 0;
        var allMove = 1;
        function onLayerChanged (event, id, areas) {
            var check_id_flag = false;
            for (var i = 0; i < areas.length ; i++) {
                var compare_id = areas[i].id;
                if (id == compare_id) {
                    check_id_flag = true;
                }
            }

            if (!check_id_flag) {
                area_all_move = false;
                $("#allObjView").html("개별");
                return;
            }

            var backupVal = '';
            if(objCnt==areas.length) {
                fnVisualEditReset();
                displayAreas(areas);
            } else if(objCnt == 0) {
                $.each(areas, function (idx, area) {
                    if(id=="layer"+area.boxseq) {

                        var objxystr = parseInt(area.x) + ', ' + parseInt(area.y) + ', ' + parseInt(area.width) + ', ' + parseInt(area.height);
                        var input = $("#" + id).find("input.coordinate");
                        //변경 전 point 저장
                        backupVal = $(input).val();
                        if(backupVal!=undefined){
                            var backupValArr = backupVal.split(', ');
                            backupVal = parseInt(parseInt(area.x)-parseInt(backupValArr[0])) + ', ' + parseInt(parseInt(area.y)-parseInt(backupValArr[1])) + ', ' + parseInt(parseInt(area.width)-parseInt(backupValArr[2])) + ', ' + parseInt(parseInt(area.height)-parseInt(backupValArr[3]));
                        }

                        $(input).val(objxystr);
                        $(input).attr({"placeholder": objxystr});

                        area.score = 1;
                    }
                });
            }

            if(backupVal!=undefined && allMove > 0 && area_all_move){
                //작업중 flag
                allMove = 0;
                var img = document.getElementById("vttImg");
                var imgWidth = img.clientWidth;
                var imgHeight = img.clientHeight;

                // console.log("width : " + imgWidth + " height : " + imgHeight);

                $.each(areas, function (idx, area) {
                    
                    if(id !="layer"+area.boxseq && area.boxtype != 'place') {

                        var backupValArr = backupVal.split(',');
                        var area_x = parseInt(parseInt(area.x)+parseInt(backupValArr[0]))>0?parseInt(parseInt(area.x)+parseInt(backupValArr[0])):0;
                        var area_y = parseInt(parseInt(area.y)+parseInt(backupValArr[1]))>0?parseInt(parseInt(area.y)+parseInt(backupValArr[1])):0;
                        var objxystr = parseInt(area_x) + ', ' + parseInt(area_y) + ', ' + parseInt(area.width) + ', ' + parseInt(area.height);

                        var input = $("#" + "layer"+area.boxseq).find("input.coordinate");
                        var objxystrArr = objxystr.split(',');


                        area.x = parseInt(imgWidth)<(parseInt(objxystrArr[0])+parseInt(objxystrArr[2]))?parseInt(parseInt(imgWidth)-parseInt(objxystrArr[2])):parseInt(objxystrArr[0]);
                        area.y = parseInt(imgHeight)<(parseInt(objxystrArr[1])+parseInt(objxystrArr[3]))?parseInt(parseInt(imgHeight)-parseInt(objxystrArr[3])):parseInt(objxystrArr[1]);
                        area.width = parseInt(objxystrArr[2]);
                        area.height = parseInt(objxystrArr[3]);

                        objxystr = parseInt(area.x) + ', ' + parseInt(area.y) + ', ' + parseInt(area.width) + ', ' + parseInt(area.height);

                        $(input).val(objxystr);
                        $(input).attr({"placeholder": objxystr});

                        $("#vttImg").selectAreas(area);
                    }
                });

                var areas = $("#vttImg").selectAreas('areas');
                var imgRatio  = ($("#vttImg").width() / imgWidth);
                for (var i = 0; i < areas.length ; i++) {
                    areas[i].x = scaleToPopImg(areas[i].x,imgRatio);
                    areas[i].y = scaleToPopImg(areas[i].y,imgRatio);
                    areas[i].width = scaleToPopImg(areas[i].width,imgRatio);
                    areas[i].height = scaleToPopImg(areas[i].height,imgRatio);
                    var $layer = $(".layer"+areas[i].boxseq);
                    var $delete = $(".delete-area.ebox_"+areas[i].boxseq);
                    var $resize = $(".select-areas-resize-handler.ebox_"+areas[i].boxseq);
                    $layer.css("left",areas[i].x);
                    $layer.css("top",areas[i].y);
                    $layer.css("width",areas[i].width);
                    $layer.css("height",areas[i].height);
                    $delete.css("left",areas[i].x+areas[i].width);
                    $delete.css("top",areas[i].y);
                    $resize.css("left",areas[i].x+areas[i].width);
                    $resize.css("top",areas[i].y+areas[i].height);
                    var rectAreas = areas[i].x + ", " + areas[i].y + ", "+ areas[i].width + ", " + areas[i].height;
                    $("#layer"+areas[i].boxseq).find("input.coordinate").attr("placeholder",rectAreas);
                    $("#layer"+areas[i].boxseq).find("input.coordinate").val(rectAreas);
                }

                allMove = 1;
            }

        }

        // Display areas coordinates in a div
        function displayAreas (areas,regImgYn) {
            // console.log("displayAreas : "+"getPrevMeta_call : "+getPrevMeta_call);
            objCnt=0;
            if(!regImgYn){
                regImgYn ="Y";
            }
            if(areas != null){
                addMetaObjEditDiv(areas,regImgYn);
            }
        };

        function scaleToPopImg (val,ImgRatio){
            return parseInt(Math.round(parseInt(val) * ImgRatio));
        }
        function scaleToRealImg(val) {
            var imgRatio = $(ImgId).width() / $(ImgId).prop("naturalWidth");
            return parseInt(Math.ceil(val / imgRatio));
        }

        function ClienPoint(min_x, min_y, max_x, max_y){
            var min_x = parseInt(min_x) || 0;
            var min_y = parseInt(min_y) || 0;
            var max_x = parseInt(max_x) || 0;
            var max_y = parseInt(max_y) || 0;
            var w = max_x - min_x;
            var h =  max_y - min_y;

            var obj = {x: min_x, y: min_y, w: w, h: h};

            return obj;
        }

        function contentResize() {
            var imgH = parseInt($(ImgId).height());
            // console.log("imgH"); console.log(imgH);
            // console.log("imgH"); console.log($("#vttImg").height());

            if(!imgH || imgH<200){
                imgH = 424;
            }

            var topH = imgH + 90; // 상단영역 및 마진 제외
            $("#visual-row-top").height(topH);
            // console.log("topH"); console.log(topH);

            // var wrapH = $(".section-list-Wrap").height();
            var wrapH = $("body").height()-($("#visual-row-top").offset().top);
            var bottomH = wrapH - topH - 10 - 40;
            $("#visual-row-bottom").height(bottomH);
            // console.log("wrapH"); console.log(wrapH);
            // console.log("bottomH"); console.log(bottomH);
            // console.log("$(\"body\").height()"); console.log($("body").height());

            // var containerH = $(".container.visual.fix").height();
            var videoWrapH = $(".section-video-Wrap").height();
            $(".container.visual.fix").height(videoWrapH);
            // console.log("videoWrapH"); console.log(videoWrapH);
        }

        function toggleUpdateShot() {
            $(".section-num").find("button").toggle();
            $("[name=secTr][delflag=true]").toggle();
        }
        function fnVisualEditReset(){
            $("#clickInfo").text("");
            $("#faceFormDiv").html("");
            $("#faceTabNo").text(0);
            $("#personAllTabNo").text(0);
            $("#objTabNo").text(0);
            $("#objTabDiv").html("");
            $("#placeFormDiv").html("");
            $("#descFormDiv").html("");
            $("#personAllDiv").html("");
        }

        $(document).on({
            click: function () {
                var as = $(this).attr('class');
                var array = as.split(' ');
                var tgtId = '#' + array[array.length - 1];
                var tgtTab = "[href='#" + $("#nav-objTabContent").find(tgtId).parent().attr('id') + "']";
                $("#clickInfo").html(array[array.length - 1]);
                $("#objTabs").find(tgtTab).tab().click();

                $("[name=formBody]").removeClass("selected");
                $(tgtId).addClass("selected");
                $(tgtId).find("input[name=score]").val(1);

                setTimeout(function(){
                    document.getElementById("nav-objTabContent").scrollTop =  document.getElementById(array[1]).offsetTop-40;
                },500);
            }
        },'.select-areas-background-area');

        $(document).on({
            click: function (e) {

                area_all_move = false;
                $("#allObjView").html("개별");
                var tgtClas = "." + $(this).attr('id');
                $('.select-areas-background-area').hide();
                $('.delete-area').hide();
                $('.select-areas-resize-handler').hide();
                $(tgtClas).show();
                $("[name=formBody]").removeClass("selected");
                $(this).addClass("selected");
                $(this).find("input[name=score]").val(1);

            }
        }, '[name=formBody]');

        $(window).resize(function () {
            contentResize();

            var areas = $("#vttImg").selectAreas('areas');
            var imgRatio  = ($("#vttImg").width() / imgWidth);
            for (var i = 0; i < areas.length ; i++) {
                areas[i].x = scaleToPopImg(areas[i].x,imgRatio);
                areas[i].y = scaleToPopImg(areas[i].y,imgRatio);
                areas[i].width = scaleToPopImg(areas[i].width,imgRatio);
                areas[i].height = scaleToPopImg(areas[i].height,imgRatio);
                var $layer = $(".layer"+areas[i].boxseq);
                var $delete = $(".delete-area.ebox_"+areas[i].boxseq);
                var $resize = $(".select-areas-resize-handler.ebox_"+areas[i].boxseq);
                $layer.css("left",areas[i].x);
                $layer.css("top",areas[i].y);
                $layer.css("width",areas[i].width);
                $layer.css("height",areas[i].height);
                $delete.css("left",areas[i].x+areas[i].width);
                $delete.css("top",areas[i].y);
                $resize.css("left",areas[i].x+areas[i].width);
                $resize.css("top",areas[i].y+areas[i].height);
                var rectAreas = areas[i].x + ", " + areas[i].y + ", "+ areas[i].width + ", " + areas[i].height;
                $("#layer"+areas[i].boxseq).find("input.coordinate").attr("placeholder",rectAreas);
                $("#layer"+areas[i].boxseq).find("input.coordinate").val(rectAreas);
            }

            imgWidth = $("#vttImg").width();
        });

        $('.btn_del_shot_row').each(function(){
            var videoid = $(this).attr('videoid');
            var shotid = $(this).attr('shotid');
            var delflag =$(this).attr('delflag');
            $(this).on('click', function(){
                updateShot(videoid, shotid, delflag);
            })
        });

        //샷 삭제여부 수정정
        function updateShot(idx, shotid, delflag){
            $.ajax({
                url: '<c:url value="/visual/updateShot"/>',
                type: 'POST',
                data: {
                    'videoid': idx,
                    'shotid':shotid,
                    'shotid':shotid,
                    'delflag':delflag
                },
                dataType: 'json',
                success: function (data) {
                    if(data.result==1){
                        getSectionList(idx);
                        toggleUpdateShot();
                    }
                },
                error: function (xhr, ajaxOptions, thrownError) {
                    MSG.alert(thrownError);
                }
            });
        }

    //});
    function onclickSection(sectionRow){
        var input_obj = $(sectionRow).find('input');
        var obj = {};
        for(var i=0;i<input_obj.length;i++){
            var name = $(input_obj[i]).attr('name');
            var val = $(input_obj[i]).val();
            obj[''+name] = val;
        }
        var frame_cut = $(sectionRow).find("input[name=frame_cut]").val();
        obj.frame_cut           = frame_cut;
        obj.pageCnt             = 10;
        obj.curPage             = 1;
        obj.assetfilepathorigin = obj.assetfilepath;
        getSectionShotList(obj, 1);
    }

</script>
<c:import url="../includes/footer.jsp"/>