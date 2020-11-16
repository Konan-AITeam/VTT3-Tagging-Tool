<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../includes/taglib.jsp" %>
<c:import url="../includes/script.jsp"/>
<div class="" role="tabpanel" data-example-id="togglable-tabs">

    <ul id="objTabs" class="nav nav-tabs bar_tabs" role="tablist">
        <li role="presentation" class="active">
            <a href="#faceFormDiv" id="faceTab" role="tab" data-toggle="tab" aria-expanded="true"> 인물 <strong id="faceTabNo" >(1)</strong>
            </a>
        </li>
        <li role="presentation" class="">
            <a href="#personAllDiv" role="tab" id="personAllTab" data-toggle="tab" aria-expanded="false"> 인물 전체 <strong id="personAllTabNo" >(3)</strong>
            </a>
        </li>
        <li role="presentation" class="">
            <a href="#objTabDiv" role="tab" id="objTab" data-toggle="tab" aria-expanded="false"> 객체 <strong id="objTabNo">(1)</strong>
            </a>
        </li>
        <li role="presentation" class="">
            <a href="#placeFormDiv" id="placeTab" role="tab" id="place-tab3" data-toggle="tab" aria-expanded="false"> 장소 </a>
        </li>
        <sec:authorize access="hasAnyRole('ROLE_ADMIN,ROLE_EDITER')" var="u">
            <li role="presentation" class="">
                <a href="#qaFormDiv" id="qaTab" role="tab" id="qa-tab3" data-toggle="tab" aria-expanded="false"> QA </a>
            </li>
        </sec:authorize>
    </ul>


    <div id="nav-objTabContent" class="layer-edit-wrap tab-content">
        <div role="tabpanel" class="tab-pane fade active in" id="faceFormDiv" aria-labelledby="faceTab">
            <div class="form-wrap" id= "${area.id}" name="formBody">
               <div class="panel panel-heading">
                   <div class="layer-name">
                       ${area.id}
                   </div>
            <c:if test="RepImageYn">
                   <div class="col-md-3 col-sm-3 col-xs-12">
                       <button name="btnDel" type="submit" class="btn-sm btn-red" onclick="delEditBox('${vttBoxNo}')"> 삭제 </button>
                   </div>
            </c:if>
               </div>
               <form class="needs-validation faceForm" name="faceForm" action="getPutMetaInfo">

                   <div class="form-group edit-form">
                       <label class="control-label col-md-3 col-sm-3 col-xs-12" > 이름</label>
                       <div class="col-md-9 col-sm-9 col-xs-12">
                           <select class="form-control" name = "faceName" id="faceName_'+ area.boxname +area.boxseq+'">
               <c:forEach var="map" items="${codeMap01}" varStatus="i">
                               <option value="${map.code_name}">${map.code_name}</option>
               </c:forEach>
                            </select>
                        </div>
                    </div>

                    <div class="form-group edit-form">
                       <label class="control-label col-md-3 col-sm-3 col-xs-12" > 얼굴좌표 </label>
                       <div class="col-md-9 col-sm-9 col-xs-12">
                           <input type="text" class="form-control form-control-sm" name="faceCoordinate"
                                  placeholder="' + objxystr + '" value="'+ objxystr +'" readonly/>
                       </div>
                    </div>
                   <c:if test="RepImageYn">
                   <div class="form-group edit-form">
                       <label class="control-label col-md-3 col-sm-3 col-xs-12" > 감정 </label>
                       <div class="col-md-9 col-sm-9 col-xs-12">
                           <select class="form-control" name="faceEmotion" id="faceEmotion_'+ area.emotion +area.boxseq+'">
                               <option value="" selected disabled hidden>선택</option>
                   <c:forEach var="map" items="${codeMap03}" varStatus="i">
                               <option value="${map.code_name}" <c:if test="${map.code_name}== '+area.boxname+'">selected="selected"</c:if>> ${map.code_name}</option>
                   </c:forEach>
                           </select>
                        </div>
                   </div>

                   <div class="form-group edit-form">
                       <label class="control-label col-md-3 col-sm-3 col-xs-12" >행동 </label>
                       <div class="col-md-9 col-sm-9 col-xs-12">
                           <select class="form-control" name="faceAction" id="faceAction_'+ area.behavior +area.boxseq+'">
                              <option value="" selected disabled hidden>선택</option>
                   <c:forEach var="map" items="${codeMap02}" varStatus="i">
                              <option value="${map.code_name}" <c:if test="${map.code_name}== '+area.boxname+'">selected="selected"</c:if>> ${map.code_name}</option>
                   </c:forEach>
                           </select>
                       </div>
                   </div>

                   <div class="form-group edit-form">
                       <label class="control-label col-md-3 col-sm-3 col-xs-12" >서술어 </label>
                       <div class="col-md-9 col-sm-9 col-xs-12">
                           <select class="form-control" name="predicate" id="predicate_'+ area.predicate +area.boxseq+'">
                               <option value="" selected disabled hidden>선택</option>
                               <c:forEach var="map" items="${codeMap11}" varStatus="i">
                                   <option value="${map.code_name}" <c:if test="${map.code_name}== '+area.boxname+'">selected="selected"</c:if>> ${map.code_name}</option>
                               </c:forEach>
                           </select>
                       </div>
                       </c:if>
                   </div>
               </form>
            </div>
        </div>

        <div role="tabpanel" class="tab-pane fade" id="personAllDiv" aria-labelledby="personAllTab">
        </div>
        <div role="tabpanel" class="tab-pane fade" id="objTabDiv" aria-labelledby="objTab">
        </div>
        <div role="tabpanel" class="tab-pane fade" id="placeFormDiv" aria-labelledby="placeTab">
        </div>
        <sec:authorize access="hasAnyRole('ROLE_ADMIN,ROLE_EDITER')" var="u">
            <div role="tabpanel" class="tab-pane fade" id="qaFormDiv" aria-labelledby="qaTab">
                <!-- 레이어 시작 -->
                <div class="form-wrap">
                    <div class="panel panel-heading">
                        <div class="layer-name">
                            QA 정보 추가
                        </div>
                    <%--<div class="col-md-3 col-sm-3 col-xs-12">
                            <button type="submit" class="btn-sm btn-red"> 삭제 </button>
                        </div>--%>
                    </div>

                    <div class="form-group edit-form">
                        <label class="control-label col-md-4 col-sm-4 col-xs-12"> 감정에 따른 행동
                        </label>
                        <div class="col-md-8 col-sm-8 col-xs-12">
                            <div class="input-group">
                                <input type="text" class="form-control" placeholder="객체간의 관계">
                                <span class="input-group-btn">
                                  <button type="button" class="btn btn-primary"> 추가</button>
                                </span>
                            </div>
                        </div>
                    </div>

                    <div class="form-group edit-form">
                        <label class="control-label col-md-4 col-sm-4 col-xs-12"> 객체간의 관계
                        </label>
                        <div class="col-md-8 col-sm-8 col-xs-12">
                            <div class="input-group">
                                <input type="text" class="form-control" placeholder="객체간의 관계">
                                <span class="input-group-btn">
                                  <button type="button" class="btn btn-primary"> 추가</button>
                                </span>
                            </div>
                        </div>
                    </div>

                </div>
                <!-- 레이어 끝 -->
            </div>
        </sec:authorize>
        <!-- 5. QA 탭 끝 -->
    </div>
</div>