<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../includes/taglib.jsp" %>

<input type="hidden" name="sectionid" value="${param.sectionid}"/>
<c:forEach var="result" items="${depictionList}" varStatus="status">
    <div class="form-group edit-form">
        <label class="control-label col-md-1 col-sm-1 col-xs-12"> 구간묘사${status.count}
        </label>
        <div class="col-md-11 col-sm-11 col-xs-12">
            <div class="input-group">
                <input type="text" name="depiction" class="form-control" placeholder="구간묘사${status.count}" value="${result.depiction}">
                <input type="hidden" name = "depictionid" value="${result.depictionid}"/>
            </div>
        </div>
    </div>
</c:forEach>

<c:forEach begin="1" end="${3-depictionList.size()}" step="1" varStatus="status">
    <div class="form-group edit-form">
        <label class="control-label col-md-1 col-sm-1 col-xs-12"> 구간묘사${depictionList.size()+status.count}
        </label>
        <div class="col-md-11 col-sm-11 col-xs-12">
            <div class="input-group">
                <input type="text" name="depiction" class="form-control" placeholder="구간묘사${depictionList.size()+status.count}" value="">
                <input type="hidden" name = "depictionid" value=""/>
            </div>
        </div>
    </div>
</c:forEach>