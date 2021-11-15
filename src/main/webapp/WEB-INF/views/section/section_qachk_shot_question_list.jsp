<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../includes/taglib.jsp" %>
<style>
    .input-group{
        margin-bottom: -10px;
    }

</style>
<input type="hidden" name="shotid" value="${param.shotid}"/>
<div class="" role="tabpanel" data-example-id="togglable-tabs"  style="height:100%;padding-bottom:10px;">
    <ul id="qaTabs" class="nav nav-tabs qa-tabs" role="tablist">
        <li role="presentation" class="active">
            <a href="#qnaSceneTap1Div" id="qnaSceneTap1" role="tab" data-toggle="tab" aria-expanded="true"> 묘사</a>
        </li>
        <li role="presentation" class="">
            <a href="#qnaSceneTapLv1Div" role="tab" id="qnaSceneTapLv1" data-toggle="tab" aria-expanded="false"> Q&A - Level1</a>
        </li>
        <li role="presentation" class="">
            <a href="#qnaSceneTapLv2Div" role="tab" id="qnaSceneTapLv2" data-toggle="tab" aria-expanded="false"> Q&A - Level2</a>
        </li>
        <li role="presentation" class="">
            <a href="#qnaSceneTapLv5Div" role="tab" id="qnaSceneTapLv5" data-toggle="tab" aria-expanded="false"> Q&A - KB</a>
        </li>
        <li style="float: right;">
            <sec:authorize access="hasAnyRole('ROLE_ADMIN')" var="u">
                <div class="img-shot-select m-right5">
                    <select id="workerId" name="workerId" class="form-control" style="height:30px; font-size:12px;" onchange="shotWorkerChange();">
                        <option value>
                            작업자
                        </option>
                        <c:if test="${ userList.size() != 0 }">
                            <c:forEach var="list" items="${userList}" varStatus="i">
                                <option value="${list.username}" <c:if test="${workerId eq list.username}">selected</c:if> >
                                        ${list.username}
                                </option>
                            </c:forEach>
                        </c:if>
                    </select>
                </div>
            </sec:authorize>
        </li>
    </ul>
    <div id="nav-objTabContent" class="layer-edit-wrap tab-content">
    </div>
</div>
<c:set var="descHtml" value=""/>
<c:set var="qaLv1Html" value=""/>
<c:set var="qaLv2Html" value=""/>
<c:set var="qaLv5Html" value=""/>
<c:choose>
    <c:when test="${ questionList.size() != 0 }">
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
            <c:otherwise>
                <c:set var="qaHtml" value='
        <div class="form-group edit-form">
            <label class="control-label col-md-1 col-sm-1 col-xs-12"> 질문</label>
            <div class="col-md-11 col-sm-11 col-xs-12">
                <div class="input-group">
                    <input type="hidden" name="questiontype" value="${result.questiontype}" />
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
                <c:when test="${result.questiontype eq 'QNALV1'}">
                    <c:if test="${result.questionid eq questionid }">
                        <c:set var="chkLevelId" value='qnaSceneTapLv1'/>
                    </c:if>
                    <c:set var="qaLv1Html" value="${qaHtml}"/>
                </c:when>
                <c:when test="${result.questiontype eq 'QNALV2'}">
                    <c:if test="${result.questionid eq questionid }">
                        <c:set var="chkLevelId" value='qnaSceneTapLv2'/>
                    </c:if>
                    <c:set var="qaLv2Html" value="${qaHtml}"/>
                </c:when>
                <c:when test="${result.questiontype eq 'QNALV5'}">
                    <c:if test="${result.questionid eq questionid }">
                        <c:set var="chkLevelId" value='qnaSceneTapLv5'/>
                    </c:if>
                    <c:set var="qaLv5Html" value="${qaHtml}"/>
                </c:when>
                </c:choose>
            </c:otherwise>
        </c:choose>
    </c:forEach>
    </c:when>
</c:choose>
        <c:set var="qaBlankHtml" value='
<div class="form-group edit-form">
    <label class="control-label col-md-1 col-sm-1 col-xs-12"> 질문</label>
    <div class="col-md-11 col-sm-11 col-xs-12">
        <div class="input-group">
            <input type="hidden" name="questiontype" value="tmpquestiontype" />
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
<c:if test="${descHtml == null ||  descHtml.equals('')}">
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
</c:if>
<c:if test="${qaLv1Html == null || qaLv1Html.equals('')}">
    <c:set var="qaLv1Html" value="${qaBlankHtml.replace('tmpquestiontype','QNALV1')}"/>
</c:if>
<c:if test="${qaLv2Html == null || qaLv2Html.equals('')}">
    <c:set var="qaLv2Html" value="${qaBlankHtml.replace('tmpquestiontype','QNALV2')}"/>
</c:if>
<c:if test="${qaLv5Html == null || qaLv5Html.equals('')}">
    <c:set var="qaLv5Html" value="${qaBlankHtml.replace('tmpquestiontype','QNALV5')}"/>
</c:if>
<div role="tabpanel" class="tab-pane fade active in" id="qnaSceneTap1Div" aria-labelledby="qnaSceneTap1">
    <input type="hidden" name="questiontype" value="DESC"/>
    <input type="hidden" name="answer" value="">
    <input type="hidden" name="wrong_answer1" value="">
    <input type="hidden" name="wrong_answer2" value="">
    <input type="hidden" name="wrong_answer3" value="">
    <input type="hidden" name="wrong_answer4" value="">
    ${descHtml}
</div>
<div role="tabpanel" class="tab-pane fade" id="qnaSceneTapLv1Div" aria-labelledby="qnaSceneTapLv1">
    ${qaLv1Html}
</div>
<div role="tabpanel" class="tab-pane fade" id="qnaSceneTapLv2Div" aria-labelledby="qnaSceneTapLv2">
    ${qaLv2Html}
</div>
<div role="tabpanel" class="tab-pane fade" id="qnaSceneTapLv5Div" aria-labelledby="qnaSceneTapLv5">
    ${qaLv5Html}
</div>
<script>
    function onCheckQuestType(obj){
        $(obj).parent("div").find("input[name=questiontype]").val($(obj).val());
    }
    $("#<c:out value="${chkLevelId}"/>").click();
</script>