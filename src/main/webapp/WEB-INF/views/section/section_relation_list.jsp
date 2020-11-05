<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../includes/taglib.jsp" %>

<tr name ="secTr" id="relationTr_new" style="display:none;">
    <input type="hidden" name="relationid" value="">
    <input type="hidden" name="delflag" value="false">
    <td>New</td>
    <td name="secTdNum" scope="row" class="section-num">
        <select class="form-control" name = "subject_sectionid" >
            <c:forEach var="subSection" items="${qaSectionList}" varStatus="i">
                <option value="${subSection.sectionid}">${subSection.sectionname}</option>
            </c:forEach>
        </select>
    </td>
    <td>
        <select class="form-control" name = "object_sectionid" >
            <c:forEach var="objSection" items="${qaSectionList}" varStatus="i">
                <option value="${objSection.sectionid}">${objSection.sectionname}</option>
            </c:forEach>
        </select>
    </td>
    <td name="secTdTime">
        <select class="form-control" name = "relationcode" >
            <c:forEach var="relCode" items="${relationCode}" varStatus="i">
                <option value="${relCode.code}">${relCode.code_name}(${relCode.code_reference})</option>
            </c:forEach>
        </select>
    </td>
</tr>
<c:forEach var="result" items="${relationList}" varStatus="status">
<tr name ="secTr" id="relationTr_${result.relationid}">
    <input type="hidden" name="relationid" value="${result.relationid}">
    <input type="hidden" name="delflag" value="false">
    <td>${status.count}</td>
    <td name="secTdNum" scope="row" class="section-num">
        <select class="form-control" name = "subject_sectionid" >
            <c:forEach var="subSection" items="${qaSectionList}" varStatus="i">
                <option value="${subSection.sectionid}" ${result.subject_sectionid==subSection.sectionid?'selected':''}>${subSection.sectionname}</option>
            </c:forEach>
        </select>
    </td>
    <td>
        <select class="form-control" name = "object_sectionid" >
            <c:forEach var="objSection" items="${qaSectionList}" varStatus="i">
                <option value="${objSection.sectionid}" ${result.object_sectionid==objSection.sectionid?'selected':''}>${objSection.sectionname}</option>
            </c:forEach>
        </select>
    </td>
    <td name="secTdTime">
        <select class="form-control" name = "relationcode" >
            <c:forEach var="relCode" items="${relationCode}" varStatus="i">
                <option value="${relCode.code}" ${result.relationcode==relCode.code?'selected':''}>${relCode.code_name}(${relCode.code_reference})</option>
            </c:forEach>
        </select>
    </td>
</tr>
</c:forEach>