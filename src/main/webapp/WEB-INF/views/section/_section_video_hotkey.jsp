<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../includes/taglib.jsp" %>


<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
    <h3 class="modal-title" id="popModalLabel">
        <i class="fa fa-keyboard-o"></i>&nbsp;단축키 도움말
    </h3>
</div>

<div class="modal-body" style="height:450px;font-size:13px;">
    <div class="col-lg-12 col-md-12 col-xs-12">
        <div class="col-md-4 col-xs-4">
            <dl class="row subtitle">
                <i class="fa fa-play txt-color-customGreen"></i>&nbsp;재생 / 볼륨
            </dl>

            <dl class="row">
                <dt>SPACE</dt>
                <dd>재생 / 일시 정지</dd>
            </dl>

            <dl class="row">
                <dt>ALT + <</dt>
                <dd>재생 시간 1초 뒤로</dd>
            </dl>

            <dl class="row">
                <dt>ALT + ></dt>
                <dd>재생 시간 1초 앞으로</dd>
            </dl>

            <dl class="row">
                <dt>CTRL + <</dt>
                <dd>재생 시간 5초 뒤로</dd>
            </dl>

            <dl class="row">
                <dt>CTRL + ></dt>
                <dd>재생 시간 5초 앞으로</dd>
            </dl>

            <dl class="row">
                <dt>CTRL + ▲(방향키)</dt>
                <dd>볼륨 크게</dd>
            </dl>

            <dl class="row">
                <dt>CTRL + ▼(방향키)</dt>
                <dd>볼륨 작게</dd>
            </dl>
        </div>

        <div class="col-md-4 col-xs-4">

            <dl class="row subtitle">
                <i class="fa fa-forward txt-color-customGreen"></i>&nbsp; 배속
            </dl>

            <dl class="row">
                <dt>ALT + PAGE UP</dt>
                <dd>배속 재생 빠르게</dd>
            </dl>

            <dl class="row">
                <dt>ALT + PAGE DOWN</dt>
                <dd>배속 재생 느리게</dd>
            </dl>
        </div>

        <div class="col-md-4 col-xs-4">
            <dl class="row subtitle">
                <i class="fa fa-scissors txt-color-customGreen"></i>&nbsp;샷 편집 (단,편집모드에서만 동작)
            </dl>

            <dl class="row">
                <dt>CTRL + [</dt>
                <dd>시작구간 설정</dd>
            </dl>

            <dl class="row">
                <dt>CTRL + ]</dt>
                <dd>종료구간 설정</dd>
            </dl>

            <dl class="row">
                <dt>CTRL + N</dt>
                <dd>신규구간 생성</dd>
            </dl>

            <dl class="row">
                <dt>CTRL + Space</dt>
                <dd>구간 반복</dd>
            </dl>
        </div>
    </div>


</div>

<div class="modal-footer">
    <div>
        <button type="button" class="btn btn-default" data-dismiss="modal"><i class="fa fa-times"></i> 닫기</button>
    </div>
</div>