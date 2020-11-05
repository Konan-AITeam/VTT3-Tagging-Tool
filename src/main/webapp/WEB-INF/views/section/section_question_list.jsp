<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../includes/taglib.jsp" %>
<style>
    .input-group{
        margin-bottom: -10px;
    }

</style>
<c:set var="blankHtml" value='
<div class="form-group edit-form">
    <label class="control-label col-md-1 col-sm-1 col-xs-12"> 질문</label>
    <div class="col-md-11 col-sm-11 col-xs-12">
        <div class="input-group">
            <input type="hidden" name="questionid" value=""/>
            <input type="text" name="question" class="form-control" placeholder="질문" value="">
        </div>
    </div>
</div>
<div class="form-group edit-form">
    <label class="control-label col-md-1 col-sm-1 col-xs-12"> 정답</label>
    <div class="col-md-11 col-sm-11 col-xs-12">
        <div class="input-group">
            <input type="text" name="answer" class="form-control" placeholder="정답" value="">
        </div>
    </div>
</div>
<div class="form-group edit-form">
    <label class="control-label col-md-1 col-sm-1 col-xs-12"> 오답-1</label>
    <div class="col-md-11 col-sm-11 col-xs-12">
        <div class="input-group">
            <input type="text" name="wrong_answer1" class="form-control" placeholder="오답-1" value="">
        </div>
    </div>
</div>
<div class="form-group edit-form">
    <label class="control-label col-md-1 col-sm-1 col-xs-12"> 오답-2</label>
    <div class="col-md-11 col-sm-11 col-xs-12">
        <div class="input-group">
            <input type="text" name="wrong_answer2" class="form-control" placeholder="오답-2" value="">
        </div>
    </div>
</div>
<div class="form-group edit-form">
    <label class="control-label col-md-1 col-sm-1 col-xs-12"> 오답-3</label>
    <div class="col-md-11 col-sm-11 col-xs-12">
        <div class="input-group">
            <input type="text" name="wrong_answer3" class="form-control" placeholder="오답-3" value="">
        </div>
    </div>
</div>
<div class="form-group edit-form">
    <label class="control-label col-md-1 col-sm-1 col-xs-12"> 오답-4</label>
    <div class="col-md-11 col-sm-11 col-xs-12">
        <div class="input-group">
            <input type="text" name="wrong_answer4" class="form-control" placeholder="오답-4" value="">
        </div>
    </div>
</div>'/>
<c:set var="descHtml" value='
<div class="form-group edit-form">
    <label class="control-label col-md-1 col-sm-1 col-xs-12"> 묘사
    </label>
    <div class="col-md-11 col-sm-11 col-xs-12">
        <div class="input-group">
            <input type="hidden" name="questionid" value=""/>
            <input type="text" name="question" class="form-control" placeholder="묘사" value="">
        </div>
    </div>
</div>'/>
<c:set var="qnaLv31" value="${blankHtml}"/>
<c:set var="qnaLv32" value="${blankHtml}"/>
<c:set var="qnaLv41" value="${blankHtml}"/>
<c:set var="qnaLv42" value="${blankHtml}"/>
<c:set var="qnaLv51" value="${blankHtml}"/>
<c:set var="qnaLv3Cnt" value="${0}"/>
<c:set var="qnaLv4Cnt" value="${0}"/>
<c:set var="qnaLv5Cnt" value="${0}"/>

<input type="hidden" name="sectionid" value="${param.sectionid}"/>
<div class="" role="tabpanel" data-example-id="togglable-tabs"  style="height:100%;padding-bottom:10px;">
    <ul id="qaTabs" class="nav nav-tabs qa-tabs" role="tablist">
        <li role="presentation" class="active">
            <a href="#qnaSceneTap1Div" id="qnaSceneTap1" role="tab" data-toggle="tab" aria-expanded="true"> 묘사</a>
        </li>
        <li role="presentation" class="">
            <a href="#qnaSceneTap2Div" role="tab" id="qnaSceneTap2" data-toggle="tab" aria-expanded="false"> Q&A - Level3</a>
        </li>
        <li role="presentation" class="">
            <a href="#qnaSceneTap3Div" role="tab" id="qnaSceneTap3" data-toggle="tab" aria-expanded="false"> Q&A - Level3</a>
        </li>
        <li role="presentation" class="">
            <a href="#qnaSceneTap4Div" role="tab" id="qnaSceneTap4" data-toggle="tab" aria-expanded="false"> Q&A - Level4 </a>
        </li>
        <li role="presentation" class="">
            <a href="#qnaSceneTap5Div" role="tab" id="qnaSceneTap5" data-toggle="tab" aria-expanded="false"> Q&A - Level4 </a>
        </li>
        <li role="presentation" class="">
            <a href="#qnaSceneTap6Div" role="tab" id="qnaSceneTap6" data-toggle="tab" aria-expanded="false"> Q&A - Level5 </a>
        </li>
    </ul>
    <div id="nav-objTabContent" class="layer-edit-wrap tab-content">

    </div>
</div>

<c:forEach var="result" items="${questionList}" varStatus="status">
    <c:choose>
        <c:when test="${result.questiontype eq 'DESC'}">
            <c:set var="descHtml" value='
<div class="form-group edit-form">
    <label class="control-label col-md-1 col-sm-1 col-xs-12"> 묘사
    </label>
    <div class="col-md-11 col-sm-11 col-xs-12">
        <div class="input-group">
            <input type="hidden" name="questionid" value="${result.questionid}"/>
            <input type="text" name="question" class="form-control" placeholder="묘사" value="${result.question}">
        </div>
    </div>
</div>'/>
        </c:when>
        <c:when test="${result.questiontype eq 'QNALV3' or result.questiontype eq 'QNALV4' or result.questiontype eq 'QNALV5'}">
            <c:set var="qaHtml" value='
    <div class="form-group edit-form">
        <label class="control-label col-md-1 col-sm-1 col-xs-12"> 질문</label>
        <div class="col-md-11 col-sm-11 col-xs-12">
            <div class="input-group">
                <input type="hidden" name="questionid" value="${result.questionid}"/>
                <input type="text" name="question" class="form-control" placeholder="질문" value="${result.question}">
            </div>
        </div>
    </div>
    <div class="form-group edit-form">
        <label class="control-label col-md-1 col-sm-1 col-xs-12"> 정답</label>
        <div class="col-md-11 col-sm-11 col-xs-12">
            <div class="input-group">
                <input type="text" name="answer" class="form-control" placeholder="정답" value="${result.answer}">
            </div>
        </div>
    </div>
    <div class="form-group edit-form">
        <label class="control-label col-md-1 col-sm-1 col-xs-12"> 오답-1</label>
        <div class="col-md-11 col-sm-11 col-xs-12">
            <div class="input-group">
                <input type="text" name="wrong_answer1" class="form-control" placeholder="오답-1" value="${result.wrong_answer1}">
            </div>
        </div>
    </div>
    <div class="form-group edit-form">
        <label class="control-label col-md-1 col-sm-1 col-xs-12"> 오답-2</label>
        <div class="col-md-11 col-sm-11 col-xs-12">
            <div class="input-group">
                <input type="text" name="wrong_answer2" class="form-control" placeholder="오답-2" value="${result.wrong_answer2}">
            </div>
        </div>
    </div>
    <div class="form-group edit-form">
        <label class="control-label col-md-1 col-sm-1 col-xs-12"> 오답-3</label>
        <div class="col-md-11 col-sm-11 col-xs-12">
            <div class="input-group">
                <input type="text" name="wrong_answer3" class="form-control" placeholder="오답-3" value="${result.wrong_answer3}">
            </div>
        </div>
    </div>
    <div class="form-group edit-form">
        <label class="control-label col-md-1 col-sm-1 col-xs-12"> 오답-4</label>
        <div class="col-md-11 col-sm-11 col-xs-12">
            <div class="input-group">
                <input type="text" name="wrong_answer4" class="form-control" placeholder="오답-4" value="${result.wrong_answer4}">
            </div>
        </div>
    </div>'/>
            <c:choose>
            <c:when test="${result.questiontype eq 'QNALV3'}">
                <c:set var="qnaLv3Cnt" value="${qnaLv3Cnt+1}"/>
                <c:choose>
                    <c:when test="${qnaLv3Cnt == 1}">
                        <c:set var="qnaLv31" value="${qaHtml}"/>
                    </c:when>
                    <c:when test="${qnaLv3Cnt == 2}">
                        <c:set var="qnaLv32" value="${qaHtml}"/>
                    </c:when>
                </c:choose>
            </c:when>
            <c:when test="${result.questiontype eq 'QNALV4'}">
                <c:set var="qnaLv4Cnt" value="${qnaLv4Cnt+1}"/>
                <c:choose>
                    <c:when test="${qnaLv4Cnt == 1}">
                        <c:set var="qnaLv41" value="${qaHtml}"/>
                    </c:when>
                    <c:when test="${qnaLv4Cnt == 2}">
                        <c:set var="qnaLv42" value="${qaHtml}"/>
                    </c:when>
                </c:choose>
            </c:when>
            <c:when test="${result.questiontype eq 'QNALV5'}">
                <c:set var="qnaLv5Cnt" value="${qnaLv5Cnt+1}"/>
                <c:choose>
                    <c:when test="${qnaLv5Cnt == 1}">
                        <c:set var="qnaLv51" value="${qaHtml}"/>
                    </c:when>
                </c:choose>
            </c:when>
            </c:choose>
        </c:when>
    </c:choose>
</c:forEach>

<div role="tabpanel" class="tab-pane fade active in" id="qnaSceneTap1Div" aria-labelledby="qnaSceneTap1">
    <input type="hidden" name="questiontype" value="DESC"/>
    <input type="hidden" name="answer" value="">
    <input type="hidden" name="wrong_answer1" value="">
    <input type="hidden" name="wrong_answer2" value="">
    <input type="hidden" name="wrong_answer3" value="">
    <input type="hidden" name="wrong_answer4" value="">
    ${descHtml}
</div>
<div role="tabpanel" class="tab-pane fade" id="qnaSceneTap2Div" aria-labelledby="qnaSceneTap2">
    <input type="hidden" name="questiontype" value="QNALV3"/>
    ${qnaLv31}
</div>
<div role="tabpanel" class="tab-pane fade" id="qnaSceneTap3Div" aria-labelledby="qnaSceneTap3">
    <input type="hidden" name="questiontype" value="QNALV3"/>
    ${qnaLv32}
</div>
<div role="tabpanel" class="tab-pane fade" id="qnaSceneTap4Div" aria-labelledby="qnaSceneTap4">
    <input type="hidden" name="questiontype" value="QNALV4"/>
    ${qnaLv41}
</div>
<div role="tabpanel" class="tab-pane fade" id="qnaSceneTap5Div" aria-labelledby="qnaSceneTap5">
    <input type="hidden" name="questiontype" value="QNALV4"/>
    ${qnaLv42}
</div>
<div role="tabpanel" class="tab-pane fade" id="qnaSceneTap6Div" aria-labelledby="qnaSceneTap6">
    <input type="hidden" name="questiontype" value="QNALV5"/>
    ${qnaLv51}
</div>