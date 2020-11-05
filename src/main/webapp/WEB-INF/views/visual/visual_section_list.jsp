<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../includes/taglib.jsp" %>

<table class="table table-striped">
    <thead>
    <tr>
        <th>구간 </th>
       <!-- <th>구간 범위 </th>-->
    </tr>
    </thead>
    <tbody>
    <c:set var="stno" value="0"/>
    <c:set var="no" value="0"/>
    <c:forEach var="result" items="${sectionList}" varStatus="i">
        <tr class="section_row" onclick="onclickSection(this);" style="display:${result.delflag?'none':'table-row'};" delflag="${result.delflag}" name="secTr" id="secTr_${result.shotid}" >
            <th name="secTdNum" scope="row" class="section-num">
                <c:choose>
                    <c:when test="${result.startno != stno}">
                        <c:set var="stno" value="${result.startno}"/>
                        <c:set var="no" value="0"/>
                    </c:when>
                    <c:otherwise>
                        <c:set var="no" value="${no+1}"/>
                    </c:otherwise>
                </c:choose>
                <c:out value="${result.startno+no}"/>구간(${result.activescore})
                <button class="btn ${result.delflag?'btn-success':'btn-red'}" style="display:none;padding:2px 7px;">${result.delflag?'복구':'삭제'}</button>
            </th>
            <!--
            <td name="secTdTime">
                result.starttimecode}~${result.endtimecode}
            </td>
            -->
            <input type="hidden" name="startsec" value="${result.startframeindex/param.rate}"/>
            <input type="hidden" name="endsec" value="${result.endframeindex/param.rate}"/>
            <input type="hidden" name="shotid" value="${result.shotid}"/>
            <input type="hidden" name="assetfilepath" value='<c:out value="${result.assetfilepath}"/>'/>
            <input type="hidden" name="assetfilename" value='<c:out value="${result.assetfilename}"/>' />
            <input type="hidden" name="startframeindex" value="${result.startframeindex}"/>
            <input type="hidden" name="endframeindex" value="${result.endframeindex}"/>
            <input type="hidden" name="frame_cut" value="${result.step}" />
        </tr>
    </c:forEach>


    </tbody>
</table>