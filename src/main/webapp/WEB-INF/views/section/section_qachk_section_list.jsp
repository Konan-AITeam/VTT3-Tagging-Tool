<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../includes/taglib.jsp" %>

<table class="table table-striped">
    <thead>
    <tr>
        <th>구간 </th>
        <th>작성자</th>
        <th>QA LV </th>
    </tr>
    </thead>
    <tbody id="qaChkShotListTbody">

    <c:forEach var="result" items="${sectionList}" varStatus="i">
        <tr class="section_row" style="display:${result.delflag?'none':'table-row'};" delflag="${result.delflag}" name ="secTr" id="secTr_${result.shotid}" onclick="getQaChkSectionShotList('${result.videoid}', '${result.shotid}', '${result.delflag}', '${result.assetfilepath}', '${result.assetfilename}', '${result.qachkworkerid}', '${result.questionid}');" title="${result.starttimecode}~${result.endtimecode}">
            <th name="secTdNum" scope="row" class="section-num">
                <c:out value="${(result.startno) + (i.index)}"/>구간
                <button class="btn ${result.delflag?'btn-success':'btn-red'}" style="display:none;padding:2px 7px;" >${result.delflag?'복구':'삭제'}</button>
            </th>
            <td name="qachkworkerid" value="${result.qachkworkerid}">
                <c:out value="${result.qachkworkerid}"/>
            </td>
            <td name="secTdTime">
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
            <input type="hidden" name="startsec" value="${result.startframeindex/param.rate}"/>
            <input type="hidden" name="endsec" value="${result.endframeindex/param.rate}"/>
            <input type="hidden" name="shotid" value="${result.shotid}"/>
            <input type="hidden" name="assetfilepath" value='<c:out value="${result.assetfilepath}"/>'/>
            <input type="hidden" name="assetfilename" value='<c:out value="${result.assetfilename}"/>' />
            <input type="hidden" name="startframeindex" value="${result.startframeindex}"/>
            <input type="hidden" name="endframeindex" value="${result.endframeindex}"/>
            <input type="hidden" name="qachkworkerid" value="${result.qachkworkerid}"/>
            <input type="hidden" name="questionid" value="${result.questionid}"/>
            <input type="hidden" name="frame_cut" value="${result.step}" />
        </tr>
    </c:forEach>


    </tbody>
</table>