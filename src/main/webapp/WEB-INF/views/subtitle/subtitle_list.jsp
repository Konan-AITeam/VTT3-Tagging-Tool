<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../includes/taglib.jsp" %>

<c:forEach var="result" items="${subtitleList}" varStatus="status">
<tr name ="secTr" id="subtitleTr_${result.subtitleid}" onclick="getSubtitleInfo(this)">
    <input type="hidden" name="subtitleid" value="${result.subtitleid}"/>
    <input type="hidden" name="starttimecode" value="${result.starttimecode}"/>
    <input type="hidden" name="endtimecode" value="${result.endtimecode}"/>
    <input type="hidden" name="delflag" value="false"/>
    <td>
        ${status.count}
    </td>
    <td name="startTime" scope="row" class="section-num">
        <p>${result.starttimecode}</p>
    </td>
    <td name="endTime" scope="row" class="section-num">
        <p>${result.endtimecode}</p>
    </td>
    <td name="secTdTime">
        <select class="form-control form-select" style="float:left;" name = "person" >
            <c:forEach var="map" items="${personCode}" varStatus="i">
                <option value="${map.code}" ${result.person==map.code?'selected':''} >${map.code_name}</option>
            </c:forEach>
        </select>
    </td>
    <td name="secTdTime" style="text-align: left !important; padding-bottom: 0px;">
        <input type="text" class="form-control form-text" style="..." name="subtitle" value="${result.subtitle}"/>
    </td>
</tr>
</c:forEach>

<tr name ="secTr" id="subtitleTr_new" onclick="getSubtitleInfo(this)"  style="display:none;">>
    <input type="hidden" name="subtitleid" value=""/>
    <input type="hidden" name="starttimecode" value="0:00:00"/>
    <input type="hidden" name="endtimecode" value="0:00:00"/>
    <input type="hidden" name="relationid" value=""/>
    <input type="hidden" name="delflag" value="false"/>
    <td>
        New
    </td>
    <td name="startTime" scope="row" class="section-num">
        <p>10:00:00</p>
    </td>
    <td name="endTime" scope="row" class="section-num">
        <p>0:00:00</p>
    </td>
    <td name="secTdTime">
        <select class="form-control form-select" style="float:left;" name = "person" >
            <option value="">선택</option>
            <c:forEach var="map" items="${personCode}" varStatus="i">
                <option value="${map.code}">${map.code_name}</option>
            </c:forEach>
        </select>
    </td>
    <td name="secTdTime" style="text-align: left !important; padding-bottom: 0px;">
        <input type="text" class="form-control form-text" style="..." name="subtitle"/>
    </td>
</tr>