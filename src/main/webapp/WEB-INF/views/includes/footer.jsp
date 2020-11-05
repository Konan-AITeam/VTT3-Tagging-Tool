<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../includes/taglib.jsp" %>
</div>
<!--/main_container  -->
<!-- Logout Modal-->
<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">로그아웃 하겠습니까?</h5>
                <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">×</span>
                </button>
            </div>
            <div class="modal-footer">
                <button class="btn btn-secondary" type="button" data-dismiss="modal">취소</button>
                <a class="btn btn-primary" href="<c:url value="/user/logout"/>">로그아웃</a>
            </div>
        </div>
    </div>
</div>

<!-- user add Modal-->
<div class="modal fade" id="addUserModal" tabindex="-1" role="dialog" aria-labelledby="addUserModalLabel" aria-hidden="true">
</div>

<div class="modal fade" id="addDownloadModal" tabindex="-1" role="dialog" aria-labelledby="addDownloadModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="addDownloadModalLabel">다운로드 항목을 선택해주세요. </h5>
                <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">×</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="row">
                    <label style="padding-right: 20px;"><input type="checkbox" name="chkDown" value="visual"/> 시각정보</label>
                    <label style="padding-right: 20px;"><input type="checkbox" name="chkDown" value="section"/> QA정보</label>
                    <label style="padding-right: 20px;"><input type="checkbox" name="chkDown" value="sound"/> 소리정보</label>
                    <label style="padding-right: 20px;"><input type="checkbox" name="chkDown" value="subtitle"/> 자막정보</label>
                </div>
            </div>
            <div class="modal-footer">
                <button class="btn btn-secondary" type="button" data-dismiss="modal">닫기</button>
                <button class="btn btn-primary" id="btn_download">다운로드</button>
            </div>
        </div>
    </div>
</div>


<!-- Contents add Modal-->
<div class="modal fade" id="addContentsModal" tabindex="-1" role="dialog" aria-labelledby="addContentsModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="addContentsModalLabel">콘텐츠 등록</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="row">
                    <!-- NEW WIDGET START -->
                    <article class="col-xs-12 col-sm-12 col-md-12 col-lg-12 sortable-grid ui-sortable">
                        <div>
                            <div>
                                <div class="widget-body no-padding">

                                    <div id="write_form" class="smart-form">
                                        <fieldset>
                                            <section>
                                                <label>제목</label>
                                                <label class="input">
                                                    <i class="icon-append fa fa-list"></i>
                                                    <input type="text" name="title" id="write_title" placeholder="제목을 입력하세요">
                                                    <b class="tooltip tooltip-bottom-right" title="This is my image's tooltip message!" >제목을 입력하세요</b>
                                                </label>
                                            </section>
                                            <section>
                                                <label>내용</label>
                                                <label class="input">
                                                    <i class="icon-append fa fa-list"></i>
                                                    <textarea name="content" id="write_content" style="width:100%;height:150px;"></textarea>
                                                    <b class="tooltip tooltip-bottom-right">본문 입력하세요</b>
                                                </label>
                                            </section>

                                        </fieldset>

                                        <fieldset>
                                            <dlv class="col col-xs-12 col-sm-12 col-md-12" id="uploader">
                                                <form class="dropzone" id="mydropzone" method="POST"></form>
                                            </dlv>
                                        </fieldset>
                                    </div>
                                    <div class="modal-footer">
                                        <button class="btn btn-primary" id="btn_write">콘텐츠 등록!</button>
                                    </div>
                                </div>
                                <!-- end widget content -->
                            </div>
                            <!-- end widget div -->
                        </div>
                        <!-- end widget -->
                    </article>
                    <!-- WIDGET END -->
                </div>
            </div>
        </div>
    </div>
</div>

<!-- dictionary add Modal-->
<div class="modal fade" id="addDictionaryModal" tabindex="-1" role="dialog" aria-labelledby="addDictionaryModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="addDictionaryModalLabel">오타사전 등록</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="row">
                    <!-- NEW WIDGET START -->
                    <article class="col-xs-12 col-sm-12 col-md-12 col-lg-12 sortable-grid ui-sortable">
                        <div>
                            <div>
                                <div class="widget-body no-padding">

                                    <div id="write_form" class="smart-form">
                                        <fieldset>
                                             <section>
                                                <label class="input">
                                                    <input type="text" name="word" id="write_word" placeholder="단어를 입력하세요.">
                                                </label>
                                                 <button class="btn btn-primary" id="addWord">입력</button>
                                            </section>
                                            <section id="word_list">
                                                <span id="new_word" style="padding-left: 5px; display: none">
                                                    <label style="width: 100px; vertical-align: middle;"><div style="text-overflow: ellipsis; display: block; white-space: nowrap; overflow: hidden; width: 100px;">단어1</div></label>
                                                    <button type="button" class="wordDelete" aria-label="Delete" style="vertical-align: initial; margin: 0px;"><span aria-hidden="true">&times;</span></button>
                                                </span>
                                            </section>
                                        </fieldset>
                                    </div>
                                    <div class="modal-footer">
                                        <%--<button class="btn btn-primary" id="btn_dic_list">확인</button>--%>
                                        <button class="btn btn-primary" id="btn_word_write">등록</button>
                                    </div>
                                </div>
                                <!-- end widget content -->
                            </div>
                            <!-- end widget div -->
                        </div>
                        <!-- end widget -->
                    </article>
                    <!-- WIDGET END -->
                </div>
            </div>
        </div>
    </div>
</div>
<!-- Contents add Modal-->
<div class="modal fade" id="videoHotkeyModal" tabindex="-1" role="dialog" aria-labelledby="addContentsModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content"></div>
    </div>
</div>

<div class="modal fade" id="guideModal" tabindex="-1" role="dialog" aria-labelledby="addContentsModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document" style="width:800px;">
        <div class="modal-content"></div>
    </div>
</div>
<!--/auto tagging  -->
<div class="modal fade" id="autoTaggingModal" tabindex="-1" role="dialog" aria-labelledby="autoTaggingModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="addContentsModalLabel">오토태깅</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="row">
                    <!-- NEW WIDGET START -->
                    <article class="col-xs-12 col-sm-12 col-md-12 col-lg-12 sortable-grid ui-sortable">
                        <div>
                            <div>
                                <div class="widget-body no-padding">
                                    <div id="write_form" class="smart-form">
                                        <div class="form-group" id="formGrpIdDiv">
                                            <label for="startIdx" class="form-control-label form-control-sm">시작 IDX:</label>
                                            <input type="text" class="form-control" id="startIdx" name="startIdx" placeholder="시작 IDX" required>
                                            <div class="invalid-feedback" id="usernameChk"></div>
                                        </div>
                                        <div class="form-group">
                                            <label for="endIdx" class="form-control-label form-control-sm">종료 IDX:</label>
                                            <input type="text" class="form-control" id="endIdx" name="endIdx" placeholder="종료 IDX" required>
                                            <div class="invalid-feedback"></div>
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <button class="btn btn-primary" id="btn_bulk_auto_tagging">오토태깅!</button>
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">취소</button>
                                    </div>
                                </div>
                                <!-- end widget content -->
                            </div>
                            <!-- end widget div -->
                        </div>
                        <!-- end widget -->
                    </article>
                    <!-- WIDGET END -->
                </div>
            </div>
        </div>
    </div>
</div>
<!-- footer content -->
<footer>
    <div class="pull-right">
        © 2018 <a href="http://www.konantech.com/">KONAN</a>. All Rights reserved
    </div>
</footer>
<!-- /footer content -->

</body>
</html>
