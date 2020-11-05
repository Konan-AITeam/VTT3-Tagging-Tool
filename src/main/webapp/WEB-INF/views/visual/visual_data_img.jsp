<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../includes/taglib.jsp" %>
<div class="x_title">
    <h2> 이미지
        <small> Image</small>
    </h2>
    <div class="image-tag">
        <sec:authorize access="hasAnyRole('ROLE_ADMIN,ROLE_EDITER,ROLE_USER')" var="u">
            <div class="img-shot-select m-right5">
                <select id="userSelect" class="form-control">
                    <option value>
                        작업자
                    </option>
                    <c:if test="${ userList.size() != 0 }">
                        <c:forEach var="list" items="${userList}" varStatus="i">
                            <option value="${list.username}">
                                    ${list.username}
                            </option>
                        </c:forEach>
                    </c:if>
                </select>
            </div>
        </sec:authorize>
<sec:authorize access="hasAnyRole('ROLE_ADMIN,ROLE_EDITER')" var="u">
        <button id="autoTagging" class="btn btn-success"> 오토태깅 </button>
        <button id="dataCopy" class="btn btn-success"> Data Copy </button>
</sec:authorize>
    </div>
    <div class="clearfix"></div>
</div>

<div class="x_content" style="overflow:visible;">
    <div class="title-shot-wrap" role="tabpanel" data-example-id="togglable-tabs" >
        <div id="represent-img-nav" name="repImgDiv" aria-labelledby="represent-img-nav">
            <img style="width: 100%; display: block;" id="vttImg" name="represent-img" src="${ shotServerUrl }/${param.assetfilepath}/${param.assetimgfilename}" class="figure-img img-fluid rounded" alt="${param.assetfilename}">
            <input type = "hidden" name = "repJson" value = "<c:out value="${repImg.repjson}" escapeXml="true" />"/>
            <input type = "hidden" name = "savechk" value = "<c:out value="${repImg.savechk}" escapeXml="true" />"/>
            <input type = "hidden" name = "frameimgid" value = "${repImg.frameimgid}"/>
            <input type = "hidden" name = "vtt_meta_idx" value = "${repImg.vtt_meta_idx}"/>
        </div>
    </div>
</div>