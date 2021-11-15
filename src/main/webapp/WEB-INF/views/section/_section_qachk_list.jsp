<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../includes/taglib.jsp" %>

<c:forEach var="result" items="${qaSectionList}" varStatus="i">
<tr name ="qaSecTr" id="qaSecTr_${result.sectionid}" onclick="setQaChkSectionInfo(this,${result.questionid})" title="구간범위 : ${result.starttimecode}~${result.endtimecode}">
    <th name="qaSecName" scope="row" class="section-num">
        <c:out value="${result.sectionname}"/>
    </th>
    <td name="qachkworkerid" value="${result.qachkworkerid}">
        <c:out value="${result.qachkworkerid}"/>
    </td>
    <td name="qaSecTdTime" value="${result.sectionid}">
        <c:choose>
            <c:when test="${result.questiontype == 'QNALV1'}">
                Q&A-Level1
            </c:when>
            <c:when test="${result.questiontype == 'QNALV2'}">
                Q&A-Level2
            </c:when>
            <c:when test="${result.questiontype == 'QNALV3'}">
                Q&A-Level3
            </c:when>
            <c:when test="${result.questiontype == 'QNALV4'}">
                Q&A-Level4
            </c:when>
            <c:when test="${result.questiontype == 'QNALV5'}">
                Q&A-KB
            </c:when>
        </c:choose>
    </td>
    <input type="hidden" name="sectionid" value="${result.sectionid}"/>
    <input type="hidden" name="sectionname" value="${result.sectionname}"/>
    <input type="hidden" name="startsec" value="${result.startframeindex}"/>
    <input type="hidden" name="endsec" value="${result.endframeindex}"/>
    <input type="hidden" name="startshotid" value="${result.startshotid}"/>
    <input type="hidden" name="endshotid" value="${result.endshotid}"/>
    <input type="hidden" name="qachkworkerid" value="${result.qachkworkerid}"/>
    <input type="hidden" name="delflag" value="false"/>
</tr>
</c:forEach>
<tr name ="qaSecTr" id="qaSectionTr_new" onclick="setQaChkSectionInfo(this,0,0)" style="display:none;">
    <th name="secTdNum" scope="row" class="section-num">
        -구간
    </th>
    <td name="secTdTime" value="new">
        ~
    </td>
    <input type="hidden" name="sectionid" value=""/>
    <input type="hidden" name="sectionname" value=""/>
    <input type="hidden" name="startsec" value="0"/>
    <input type="hidden" name="endsec" value="0"/>
    <input type="hidden" name="startshotid" value=""/>
    <input type="hidden" name="endshotid" value=""/>
    <input type="hidden" name="delflag" value="false"/>
    <input type="hidden" name="qaChkWorkerId" value=""/>
</tr>