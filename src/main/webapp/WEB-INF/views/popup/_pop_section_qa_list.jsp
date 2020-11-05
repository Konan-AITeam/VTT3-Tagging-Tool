<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../includes/taglib.jsp" %>

<c:forEach var="result" items="${qaSectionList}" varStatus="i">
<tr name ="qaSecTr" id="qaSecTr_${result.sectionid}" onclick="setQaSectionInfoPOP(this)">
    <th name="qaSecName" scope="row" class="section-num">
        <c:out value="${result.sectionname}"/>
    </th>
    <td name="qaSecTdTime" value="${result.sectionid}">
        ${result.starttimecode}~${result.endtimecode}
    </td>
    <input type="hidden" name="sectionid" value="${result.sectionid}"/>
    <input type="hidden" name="sectionname" value="${result.sectionname}"/>
    <input type="hidden" name="startsec" value="${result.startframeindex}"/>
    <input type="hidden" name="endsec" value="${result.endframeindex}"/>
    <input type="hidden" name="startshotid" value="${result.startshotid}"/>
    <input type="hidden" name="endshotid" value="${result.endshotid}"/>
    <input type="hidden" name="delflag" value="false"/>
</tr>
</c:forEach>
<tr name ="qaSecTr" id="qaSectionTr_new" onclick="setQaSectionInfoPOP(this)" style="display:none;">
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
</tr>