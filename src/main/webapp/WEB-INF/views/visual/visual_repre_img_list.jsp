<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../includes/taglib.jsp" %>
<div class="row ">
    <div class="x_content">

        <div class="title-shot-wrap" role="tabpanel" data-example-id="togglable-tabs" >
            <ul id="repImgTab" class="nav nav-tabs bar_tabs" role="tablist" style="width:fit-content ;float:left;">
                <li role="presentation" class="active">
                    <a href="#current-img-nav" id="current-img" role="tab" data-toggle="tab" aria-expanded="true" name="current-img-nav"> 현재 이미지
                    </a>
                </li>
                <c:forEach var="list" items="${repImgList}" varStatus="i">
                    <li role="presentation" class="represent-img-nav">
                        <a class="nav-item nav-link" id="represent-img-${list.repImgSeq}" data-toggle="tab"
                           href="#represent-img-nav-${list.repImgSeq}" role="tab" name="represent-img-nav" aria-controls="represent-img-nav-${list.repImgSeq}" aria-selected="false">대표이미지${list.repImgSeq}</a>
                    </li>
                </c:forEach>
            </ul>
            <div style="padding-top:12px;float:right;">
                <sec:authorize access="hasRole('ROLE_ADMIN')">
                <div class="img-shot-select">
                    <select id="userSelect" class="form-control">
                        <option value>
                            선택하세요
                        </option>
                        <c:forEach var="list" items="${userList}" varStatus="i">
                            <option value="${list.username}">
                                    ${list.username}
                            </option>
                        </c:forEach>
                    </select>
                </div>
                </sec:authorize>

                <div class="img-shot-select">
                    <select id="repImgSelect" class="form-control">
                        <option value>
                            선택하세요
                        </option>
                        <option value="1">
                            대표이미지 1
                        </option>
                        <option value="2">
                            대표이미지 2
                        </option>
                        <option value="3">
                            대표이미지 3
                        </option>
                    </select>
                </div>
                <div class="col-md-2 col-sm-2 col-xs-12">
                    <button id="repImgSave" type="submit" class="btn btn-success"> 저장 </button>
                </div>
            </div>
                    <div id="repTabContent" class="tab-content" style="height: 572px;padding-top:51px;">
                        <div role="tabpanel" class="tab-pane fade active in" id="current-img-nav" aria-labelledby="tab">
                            <img style="display: block;width: 100%;" id="currentSectionImg" alt="현재이미지">
                            <form id="repImgForm">
                                <input type="hidden" value="" id="repShotid" name="repShotid"/>
                                <input type="hidden" value="" id="repSectionId" name="repSectionId"/>
                                <input type="hidden" value="" id="repFileId" name="repFileId" />
                                <input type="hidden" value="" id="repImgSeq" name="repImgSeq" />
                                <input type="hidden" value="" id="repIdx" name="repIdx" />
                                <input type="hidden" value="" id="repVideoId" name="repVideoId" />
                                <input type="hidden" value="" id="repImgUrl" name="repImgUrl" />
                                <input type="hidden" value="" id="repSectionPath" name="repSectionPath" />
                                <input type="hidden" value="" id="repShotFileName" name="repShotFileName" />
                                <input type="hidden" value="N" name="regImgYn" />
                            </form>
                        </div>
                    <c:forEach var="list" items="${repImgList}" varStatus="i">
                        <div role="tabpanel" class="tab-pane fade" id="represent-img-nav-${list.repImgSeq}" name="repImgDiv" aria-labelledby="represent-img-nav-${list.repImgSeq}">
                            <img style="width: 100%; display: block;" id="vttImg${list.repImgSeq}" name="represent-img" src="${ shotServerUrl }/${list.repImgUrl}" class="figure-img img-fluid rounded" alt="${list.repFileId}">
                            <form name="repImgForm">
                                <input type="hidden" value="${list.repSectionId}" name="repSectionId"/>
                                <input type="hidden" value="<c:out value="${list.repJson}" escapeXml="true" />" name="repJson"/>
                                <input type="hidden" value="${list.repFileId}" name="repFileId" />
                                <input type="hidden" value="${list.repImgSeq}" name="repImgSeq" />
                                <input type="hidden" value="${list.repIdx}" name="repIdx" />
                                <input type="hidden" value="${list.repVideoId}" name="repVideoId" />
                                <input type="hidden" value="${list.repImgUrl}" name="repImgUrl" />
                                <input type="hidden" value="${list.shotid}" name="repShotid"/>
                                <input type="hidden" value="Y" name="regImgYn" />
                                <%--<button onclick="javascript:repImgDelete(${list.repVideoId},${list.shotid},${list.repImgSeq})" class="btn btn-red repImgDelete">대표이미지 삭제 </button>--%>
                        </form>
                    </div>
                    <script>
                        $("div.rep${list.repImgSeq}").remove();
                        $("#scrollImgDiv").find("input[value=${list.repFileId}]").after("<div class='rep${list.repImgSeq}'>${list.repImgSeq}</div>");
                    </script>
                </c:forEach>
                </div>
            </form>
        </div>
    </div>
</div>