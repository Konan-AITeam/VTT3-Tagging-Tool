<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../includes/taglib.jsp" %>
<style>
    .input-group{
        margin-bottom: -10px;
    }

</style>
<c:set var="qaHtml" value='
<div class="form-group edit-form">
    <label class="control-label col-md-1 col-sm-1 col-xs-12"> 질문</label>
    <div class="col-md-11 col-sm-11 col-xs-12">
        <div class="input-group" style="padding-top:5px;">
            <input type="hidden" name="questionid" value=""/>
            <input type="hidden" name="questiontype" value=""/>
            <input type="radio" class="" placeholder="" id="QNALV1" name="questiontypelv" value="QNALV1" onclick="onCheckQuestType(this);"> <label for="QNALV1" style="margin-left:5px;"> Level 1</label>
            <input type="radio" class="" placeholder="" id="QNALV2" name="questiontypelv" value="QNALV2" style="margin-left:15px;" onclick="onCheckQuestType(this);"> <label for="QNALV2" style="margin-left:5px;"> Level 2</label>
            <input type="radio" class="" placeholder="" id="QNALV5" name="questiontypelv" value="QNALV5" style="margin-left:15px;" onclick="onCheckQuestType(this);"> <label for="QNALV5" style="margin-left:5px;"> Level 5</label>
        </div>
    </div>
</div>
<div class="form-group edit-form">
    <label class="control-label col-md-1 col-sm-1 col-xs-12"> 질문</label>
    <div class="col-md-11 col-sm-11 col-xs-12">
        <div class="input-group">
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
<input type="hidden" name="shotid" value="${param.shotid}"/>
<div class="" role="tabpanel" data-example-id="togglable-tabs"  style="height:100%;padding-bottom:10px;">
    <ul id="qaTabs" class="nav nav-tabs qa-tabs" role="tablist">
        <li role="presentation" class="active">
            <a href="#qnaSceneTap1Div" id="qnaSceneTap1" role="tab" data-toggle="tab" aria-expanded="true"> 묘사</a>
        </li>
        <li role="presentation" class="">
            <a href="#qnaSceneTap2Div" role="tab" id="qnaSceneTap2" data-toggle="tab" aria-expanded="false"> Q&A</a>
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
        <c:when test="${result.questiontype eq 'QNALV1' or result.questiontype eq 'QNALV2' or result.questiontype eq 'QNALV5'}">
            <c:if test="${result.questiontype eq 'QNALV1'}">
                <c:set var="qnaLv1Checked" value="checked"/>
                <c:set var="qnaLv2Checked" value=""/>
                <c:set var="qnaLv5Checked" value=""/>
            </c:if>
            <c:if test="${result.questiontype eq 'QNALV2'}">
                <c:set var="qnaLv1Checked" value=""/>
                <c:set var="qnaLv2Checked" value="checked"/>
                <c:set var="qnaLv5Checked" value=""/>
            </c:if>
            <c:if test="${result.questiontype eq 'QNALV5'}">
                <c:set var="qnaLv1Checked" value=""/>
                <c:set var="qnaLv2Checked" value=""/>
                <c:set var="qnaLv5Checked" value="checked"/>
            </c:if>
            <c:set var="qaHtml" value='
    <div class="form-group edit-form">
        <label class="control-label col-md-1 col-sm-1 col-xs-12"> 질문</label>
        <div class="col-md-11 col-sm-11 col-xs-12">
            <div class="input-group" style="padding-top:5px;">
                <input type="hidden" name="questiontype" value="${result.questiontype}" />
                <input type="radio" class="" placeholder="" name="questiontypelv" id="QNALV1" value="QNALV1" ${qnaLv1Checked} onclick="onCheckQuestType(this);" > <label for="QNALV1" style="margin-left:5px;"> Level 1</label>
                <input type="radio" class="" placeholder="" name="questiontypelv" id="QNALV2" value="QNALV2" style="margin-left:15px;" ${qnaLv2Checked} onclick="onCheckQuestType(this);"> <label for="QNALV2" style="margin-left:5px;"> Level 2</label>
                <input type="radio" class="" placeholder="" name="questiontypelv" id="QNALV5" value="QNALV5" style="margin-left:15px;" ${qnaLv5Checked} onclick="onCheckQuestType(this);"> <label for="QNALV5" style="margin-left:5px;"> Level 5</label>
            </div>
        </div>
    </div>
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
    ${qaHtml}
</div>
<script>
    function onCheckQuestType(obj){
        $(obj).parent("div").find("input[name=questiontype]").val($(obj).val());
    }
</script>