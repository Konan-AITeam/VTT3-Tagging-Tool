<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../includes/taglib.jsp" %>

<c:choose>
    <c:when test="${ param.status == 1000 }">
        <span class="label-j bg-blue">대기</span>
    </c:when>

    <c:when test="${ param.status >= 2000 and param.status <= 2100 }">
        <c:set var="percentage" value="${ param.status - 2000 }" />
        <div class="progress">
            <div class="progress-bar progress-bar-warning" data-transitiongoal="${ percentage }" aria-valuenow="${ percentage }" style="width: ${ percentage }%;">

            </div>
        </div>
    </c:when>

    <c:when test="${ param.status == 3000 }">
        <span class="label-j bg-green">성공</span>
    </c:when>

    <c:when test="${ param.status == 4000 }">
        <span class="label-j bg-purple">실패</span>
    </c:when>

    <c:when test="${ param.status == 5000 }">
        <span class="label-j label-danger"> 재시도 </span>
    </c:when>

    <c:otherwise>
        <span class="label-j bg-blue">연기</span>
    </c:otherwise>
</c:choose>