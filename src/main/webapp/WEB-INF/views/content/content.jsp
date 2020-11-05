<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include  file="../includes/header.jsp"%>

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
                                콘텐츠 리스트
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
                        <div class="x_title">
                            <h2> 콘텐츠 리스트 <small>Content list</small></h2>
                            <ul class="nav navbar-right panel_toolbox">
<sec:authorize access="hasAnyRole('ROLE_ADMIN')" var="u">
                                <li>
                                    <a class="table-btn" id="btn_active_learn">
                                        <i class="fa fa-file-code-o " ></i>
                                        액티브러닝
                                    </a>
                                </li>
                                <li>
                                    <a class="table-btn" data-toggle="modal" data-target="#autoTaggingModal">
                                        <i class="fa fa-file-code-o " ></i>
                                        오토태깅
                                    </a>
                                </li>
</sec:authorize>
                                <li>
                                    <a class="table-btn" id="btn_vtt_visual_edit">
                                        <i class="fa fa-file-code-o " ></i>
                                        VTT 시각정보 편집
                                    </a>
                                </li>
                                <li>
                                    <a class="table-btn" id="btn_vtt_section_edit">
                                        <i class="fa fa-file-code-o " ></i>
                                        VTT 구간정보 편집
                                    </a>
                                </li>
                                <li>
                                    <a class="table-btn" id="btn_vtt_qna_edit">
                                        <i class="fa fa-file-code-o " ></i>
                                        VTT 묘사 & Q&A 편집
                                    </a>
                                </li>
                                <li>
                                    <a class="table-btn" id="btn_vtt_sound_edit">
                                        <i class="fa fa-file-code-o " ></i>
                                        VTT 소리정보 편집
                                    </a>
                                </li>
                                <li>
                                    <a class="table-btn" id="btn_vtt_subtitle_edit">
                                        <i class="fa fa-file-code-o " ></i>
                                        VTT 자막정보 편집
                                    </a>
                                </li>
<sec:authorize access="hasAnyRole('ROLE_ADMIN')" var="u">
                                <li>
                                    <a class="table-btn" id="btn_catalog">
                                        <i class="fa fa-file-code-o " ></i>
                                        카탈로깅
                                    </a>
                                </li>
                                <li>
                                    <a class="table-btn" id="btn_delete">
                                        <i class="fa fa-file-code-o " ></i>
                                        삭제
                                    </a>
                                </li>
</sec:authorize>
                                <li>
                                    <a class="table-btn" data-toggle="modal" data-target="#addDownloadModal">
                                    <%--<a class="table-btn" id="btn_download">--%>
                                        <i class="fas fa-file-download"></i>
                                        다운로드
                                    </a>
                                </li>
<sec:authorize access="hasAnyRole('ROLE_ADMIN')" var="u">
                                <li>
                                    <a class="table-btn" id="btn_img_download">
                                        <i class="far fa-images"></i>
                                        이미지 다운로드
                                    </a>
                                </li>
                                <li>
                                    <a class="table-btn" id="btn_storyboard">
                                        <i class="fa fa-file-code-o " ></i>
                                        스토리보드
                                    </a>
                                </li>
                                <li>
                                    <a class="table-btn" id="btn_play">
                                        <i class="fa fa-file-code-o " ></i>
                                        객체인식재생
                                    </a>
                                </li>
                                <li>
                                    <a class="table-btn" data-toggle="modal" data-target="#addContentsModal">
                                        <i class="fa fa-file-code-o " ></i>
                                        콘텐츠 등록
                                    </a>
                                </li>
                                <li>
                                    <a class="table-btn" data-toggle="modal" id="btn_add_dic" data-target="#addDictionaryModal">
                                        <i class="fa fa-file-code-o " ></i>
                                        오타사전 등록
                                    </a>
                                </li>
</sec:authorize>
                            </ul>

                            <div class="clearfix"></div>
                        </div>

                        <div class="x_content">
                            <div class="table-responsive">
                                <table class="table table-striped jambo_table bulk_action">
                                    <thead>
                                    <tr class="">
                                        <th>
                                            <input type="checkbox" id="check-all" class="flat" />
                                        </th>
                                        <th class="" > 순번 </th>
                                        <th class=""> IDX </th>
                                        <th class=""> 카탈로깅 </th>
                                        <th class=""> 제목 </th>
                                    </tr>
                                    </thead>

                                    <tbody id="content_assets">
                                    </tbody>
                                </table>
                            </div>
                        </div>

                        <div class="page-wrap">
                            <div class="col-sm-3">
                                <div class="dataTables_info" id="example2_info" role="status" aria-live="polite">
                                    Showing 1 to 10 of 57 entries
                                </div>
                            </div>

                            <div class="col-sm-5">
                                <div class="dataTables_paginate paging_simple_numbers" id="example2_paginate">
                                    <ul class="pagination"></ul>
                                </div>
                            </div>
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

    var params = {};
    params.offset = "${queryMap.offset}";
    params.limit = "${queryMap.limit}";

    $(document).ready(function(){
        $.ajax({
            type: "GET",
            url: "<c:url value='/content/list' />",
            data: params,
            dataType: "html",
            success: function(response) {
                $("#content_assets").html(response);
            },
            error: function(xhr, opt, err) {
                MSG.error("오류가 발생했습니다.!");
                return false;
            }
        });

        // btn
        $("#btn_bulk_auto_tagging").on("click", function() {

            if($("#startIdx").val() == '') {
                MSG.alert("시작 IDX 를 입력해주세요.");
                return;
            }else if($("#endIdx").val() == ''){
                $("#endIdx").val($("#startIdx").val())
            }

            $("#autoTaggingModal").find(".close").click();
            //오토태깅
            getPutBulkRepreImg();


        });

        // btn
        $("#btn_transcoding").on("click", function() {
            //
        });

        $("#btn_vtt_visual_edit").on("click", function() {
            var $T = $("#content_assets .ui-selectee.ui-selected");
            if($T.length == 0) {
                toast("Content", "콘텐츠를 선택하세요.!", "info", 5000);
                return false;
            }
            var param = {};
            param.idx = $T.find("input[name=idx]").val();
            location.href = "<c:url value='/visual/main' />?" + $.param(param);

        });

        $("#btn_vtt_section_edit").on("click", function() {
            var $T = $("#content_assets .ui-selectee.ui-selected");
            if($T.length == 0) {
                toast("Content", "콘텐츠를 선택하세요.!", "info", 5000);
                return false;
            }
            var param = {};
            param.idx = $T.find("input[name=idx]").val();
            param.viewChk = 'section';
            location.href = "<c:url value='/section/info' />?" + $.param(param);

        });
        $("#btn_vtt_qna_edit").on("click", function() {
            var $T = $("#content_assets .ui-selectee.ui-selected");
            if($T.length == 0) {
                toast("Content", "콘텐츠를 선택하세요.!", "info", 5000);
                return false;
            }
            var param = {};
            param.idx = $T.find("input[name=idx]").val();
            param.viewChk = 'qna';
            location.href = "<c:url value='/section/qa' />?" + $.param(param);

        });

        $("#btn_vtt_sound_edit").on("click", function() {
            var $T = $("#content_assets .ui-selectee.ui-selected");
            if($T.length == 0) {
                toast("Content", "콘텐츠를 선택하세요.!", "info", 5000);
                return false;
            }
            var param = {};
            param.idx = $T.find("input[name=idx]").val();
            location.href = "<c:url value='/sound/edit' />?" + $.param(param);

        });

        $("#btn_vtt_subtitle_edit").on("click", function() {
            var $T = $("#content_assets .ui-selectee.ui-selected");
            if($T.length == 0) {
                toast("Content", "콘텐츠를 선택하세요.!", "info", 5000);
                return false;
            }
            var param = {};
            param.idx = $T.find("input[name=idx]").val();
            location.href = "<c:url value='/subtitle/edit' />?" + $.param(param);

        });

        $("#btn_catalog").on("click", function() {
            var $T = $("#content_assets .ui-selectee.ui-selected");
            if($T.length == 0) {
                toast("Content", "콘텐츠를 선택하세요.!", "info", 5000);
                return false;
            }
            var ids = [];
            $T.each(function() {
                ids.push($(this).find("input[name=idx]").val());
            });
            var idx = ids.join("|");
            var _param = {};
            _param.status = 1;
            _param.progress = 30;
            _param.startFlag = "Y";
            _param.error_msg ="error msg!!";

            MSG.confirm("카탈로깅을 요청하겠습니까?", function(){
                $.ajax({
                    type: "POST",
                    url: "<c:url value='/content/retry/catalog/' />" + idx,
                    data: _param,
                    param: {},
                    success: function(response) {
                        MSG.alert("카탈로깅을 요청했습니다",function() {
                            location.href = "<c:url value='/content' />";
                        });
                    },
                    error: function(xhr, opt, err) {
                        MSG.alert("오류가 발생했습니다.!" + err, function() {
                            location.href = "<c:url value='/content' />";
                        });
                        return false;
                    }
                });
            });
        });

        $("#btn_delete").on("click", function() {
            var $T = $("#content_assets .ui-selectee.ui-selected");
            if($T.length == 0) {
                toast("Content", "콘텐츠를 선택하세요.!", "info", 5000);
                return false;
            }
            var ids = [];
            $T.each(function() {
                ids.push($(this).find("input[name=idx]").val());
            });
            var idx = ids.join("|");

            MSG.confirm("삭제를 하겠습니까?", function () {
                $.ajax({
                    type: "GET",
                    url: "<c:url value='/content/delete/' />" + idx,
                    dataType: "json",
                    success: function(response) {
                        MSG.alert("삭제를 했습니다",function() {
                            location.href = "<c:url value='/content' />";
                        });
                    },
                    error: function(xhr, opt, err) {
                        MSG.error("오류가 발생했습니다.!" + err, function() {
                            location.href = "<c:url value='/content' />";
                        });
                    }
                });
            });
        });

        $("#btn_download").on("click", function() {
            var $T = $("#content_assets .ui-selectee.ui-selected");
            if($T.length == 0) {
                toast("Content", "콘텐츠를 선택하세요.!", "info", 5000);
                return false;
            } else {
                var chkDown = [];
                $("input[name=chkDown]").each(function() {
                    if(this.checked) chkDown.push($(this).val());
                });
                if(chkDown.length === 0){
                    toast("Content", "다운로드 항목을 선택해주세요.!", "info", 5000);
                    return false;
                }

                var param = {};
                param.idx = $T.find("input[name=idx]").val();
                param.userid = '${user.username}';
                param.chkDown = chkDown.join("|");
                location.href = "<c:url value='/downloadJsonFile' />?" + $.param(param);
                $("input[name=chkDown]").each(function() {
                    $(this).attr("checked",false);
                });
                $('#addDownloadModal').modal('hide');
            }
        });

        $("#btn_img_download").on("click", function() {
            var $T = $("#content_assets .ui-selectee.ui-selected");
            if($T.length == 0) {
                toast("Content", "콘텐츠를 선택하세요.!", "info", 5000);
                return false;
            } else {
                var param = {};
                param.idx = $T.find("input[name=idx]").val();
                param.userid = '${user.username}';
                location.href = "<c:url value='/downloadWorkedImgFile' />?" + $.param(param);
            }
        });

        $("#btn_storyboard").on("click", function() {
            var $T = $("#content_assets .ui-selectee.ui-selected");
            if($T.length == 0) {
                toast("Content", "콘텐츠를 선택하세요.!", "info", 5000);
                return false;
            } else {
                var param = {};
                param.idx = $T.find("input[name=idx]").val();
                location.href = "<c:url value='/storyboard' />?" + $.param(param);
            }
        });

        $("#btn_play").on("click", function() {
            console.log("#btn_play");
            var $T = $("#content_assets .ui-selectee.ui-selected");
            if($T.length == 0) {
                toast("Content", "콘텐츠를 선택하세요.!", "info", 5000);
                return false;
            } else {
                var param = {};
                param.idx = $T.find("input[name=idx]").val();
                location.href = "<c:url value='/storyboard/play' />?" + $.param(param);
            }
        });

        // btn
        $("#btn_active_learn").on("click", function() {

            var $T = $("#content_assets .ui-selectee.ui-selected");
            if($T.length == 0) {
                toast("Content", "콘텐츠를 선택하세요.!", "info", 5000);
                return false;
            }else if($T.length > 1) {
                toast("Content", "한개의 콘텐츠만 선택하세요.!", "info", 5000);
                return false;
            }

            var idx = $T.find("input[name=idx]").val();

            MSG.confirm("액티브러닝을 하겠습니까?", function () {
                $.ajax({
                    type: "GET",
                    url: "<c:url value='/content/activeCheck/' />" + idx,
                    dataType: "json",
                    success: function(response) {
                        if(response !== 0){
                            toast("Content", "액티브러닝이 실행 중입니다.", "info", 5000);
                        }else{
                            active_learn_run(idx);
                        }
                    },
                    error: function(xhr, opt, err) {
                        MSG.error("오류가 발생했습니다.!" + err, function() {
                            location.href = "<c:url value='/content' />";
                        });
                    }
                });
            });

        });

        function active_learn_run(idx){

            $.ajax({
                type: "GET",
                url: "<c:url value='/content/activeUpdate/' />" + idx,
                dataType: "json",
                success: function(response) {
                    toast("Content", "액티브러닝이 실행되었습니다.", "info", 5000);
                },
                error: function(xhr, opt, err) {
                    MSG.error("오류가 발생했습니다.!" + err, function() {
                        location.href = "<c:url value='/content' />";
                    });
                }
            });
        }

        function getPutBulkRepreImg() {

            var param = {
                startIdx : $("#startIdx").val(),
                endIdx  : $("#endIdx").val()
            };

            $.ajax({
                url: '<c:url value="/visual/getPutBulkRepreImg"/>',
                type: 'POST',
                data: param,
                dataType: 'json',
                // contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                success: function (json) {

                    toast("작업 완료", "태깅 건수 : "+json.taggingCnt+" 에러 건수 : "+json.errorCnt, "info", 5000);
                },
                error: function () {
                }
            });
        }

        // 사전등록
        // 영문만가능
        String.prototype.onlyEngNum = function () {
            return this.replace(/[^a-zA-Z0-9\s']/gi, '');
        }

        // 연문만가능
        $("#write_word").on("keyup",function (e) {
            if (!(e.keyCode >= 37 && e.keyCode <= 40)) {
                var inputValue = $(this).val();
                $(this).val(inputValue.onlyEngNum());
            }
        });

        // 메인메뉴에서 사전등로버튼 클릭
        $("#btn_add_dic").on('click', function(e){
            var word_list = $("#word_list");
            var new_word = $("#new_word").clone();
            $(word_list).html(new_word);
        });

        // 입력버튼 클릭
        $("#addWord").on('click', function(e){
            addWordList();
        });

        // 등록버튼 클릭
        $("#btn_word_write").on('click', function(e){
            postWordList();
        });

        // 엔터입력시 단어 입력
        $("#write_word").on('keypress', function (e) {
           if(e.keyCode == 13){
               addWordList();
           }
        });

        function addWordList() {
            var word_list = $("#word_list");
            var word = $("#write_word").val();
            var new_word = $("#new_word").clone();

            $(new_word).removeAttr('id');
            $(new_word).find("label").attr('title',word);
            $(new_word).find("label > div").html(word);
            $(new_word).show();

            $(new_word).find("button").on('click', function(e){
                removeWordList($(new_word));
            });

            $(word_list).append($(new_word));
            $("#write_word").val('');
            $("#write_word").focus();

        }

        function removeWordList(obj) {
            $(obj).remove();
        }

        function postWordList() {
            var words = [];
            var word_list = $("#word_list > span");

            $(word_list).each(function(){
                if($(this).attr('id') !== 'new_word'){
                    words.push($(this).find('label').attr('title'));
                }
            });
            var _param = {};
            _param.words = words.join(",");
            MSG.confirm('등록하시겠습니까?',function () {
                $.ajax({
                    type: "POST",
                    url: "<c:url value='/content/postDicInfo' />",
                    data: _param,
                    param: {},
                    success: function(response) {
                        MSG.alert(response.result);
                        $('#addDictionaryModal').modal('hide');
                    },
                    error: function(xhr, opt, err) {
                        MSG.alert("오류가 발생했습니다.!" + err, function() {
                            location.href = "<c:url value='/content' />";
                        });
                        return false;
                    }
                });
            });
        }

        $("#btn_dic_list").on('click', function(e){

            $.ajax({
                type: "POST",
                url: "<c:url value='/content/getDicInfo' />",
                param: {},
                success: function(response) {
                    MSG.alert("<label>"+response.result+"</label>");
                },
                error: function(xhr, opt, err) {
                    MSG.alert("오류가 발생했습니다.!" + err, function() {
                        location.href = "<c:url value='/content' />";
                    });
                    return false;
                }
            });

        });
    });
</script>
<c:import url="write_script.jsp"/>
<c:import url="../includes/footer.jsp"/>
