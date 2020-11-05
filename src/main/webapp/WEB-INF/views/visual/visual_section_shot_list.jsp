<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../includes/taglib.jsp" %>
<script>
    if(${fn:length(sectionShotList)} === 0){
        MSG.alert("No Image.");
    }
</script>

<div class="row shot-list-wrap">
<c:forEach var="result" items="${sectionShotList}" varStatus="i">

    <c:if test="${result.hiddenchk == 'true'}">
        <div class="col-lg-6 item_set" data-index="" name="selShotImgLi" style="display:none;">
            <c:set var="img_path" value="${ shotServerUrl }/${ result.assetfilepath }/${ result.assetfilename }"/>
            <div >
                <div class="image view view-first">
                    <img style="width: 100%; display: block;" name="selShotImg" src="<c:url value="${img_path}"/>" class="img-fluid rounded" />
                    <input type="hidden" name="shotid" value="${ result.shotid }">
                    <input type="hidden" name="shotServerUrl" value="${ shotServerUrl }">
                    <input type="hidden" name="assetfilepath" value="${ result.assetfilepath }">
                    <input type="hidden" name="assetfilename" value="${ result.assetfilename }">
                    <input type="hidden" name="frameimgid" value="${result.frameimgid}">
                    <input type="hidden" name="vtt_meta_idx" value ="${result.vtt_meta_idx}"/>
                    <input type="hidden" name="hiddenchk" value ="${result.hiddenchk}"/>

                    <c:if test="${result.savechk == 'false'}">
                        <div class="rep1">태깅</div>
                    </c:if>
                    <c:if test="${result.savechk == 'true'}">
                        <div class="rep2">작업</div>
                    </c:if>
                </div>
            </div>
        </div>
    </c:if>

    <c:if test="${result.hiddenchk ne 'true'}">
        <div class="col-lg-6 item_set" data-index="${ result.assetfilename }" name="selShotImgLi">
            <c:set var="img_path" value="${ shotServerUrl }/${ result.assetfilepath }/${ result.assetfilename }"/>
            <div >
                <div class="image view view-first">
                    <img style="width: 100%; display: block;" name="selShotImg" src="<c:url value="${img_path}"/>" class="img-fluid rounded" />
                    <input type="hidden" name="shotid" value="${ result.shotid }">
                    <input type="hidden" name="shotServerUrl" value="${ shotServerUrl }">
                    <input type="hidden" name="assetfilepath" value="${ result.assetfilepath }">
                    <input type="hidden" name="assetfilename" value="${ result.assetfilename }">
                    <input type="hidden" name="frameimgid" value="${result.frameimgid}">
                    <input type="hidden" name="vtt_meta_idx" value ="${result.vtt_meta_idx}"/>
                    <input type="hidden" name="hiddenchk" value ="${result.hiddenchk}"/>

                    <c:if test="${result.savechk == 'false'}">
                        <div class="rep1">태깅</div>
                    </c:if>
                    <c:if test="${result.savechk == 'true'}">
                        <div class="rep2">작업</div>
                    </c:if>
                </div>
            </div>
        </div>
    </c:if>
</c:forEach>
</div>