<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2018-07-12
  Time: 오후 1:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../includes/taglib.jsp" %>

<div class="modal-dialog" role="document">
    <div class="modal-content">
        <div class="modal-header">
            <h5 class="modal-title" id="addUserModalLabel">사용자 추가</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
        <div class="modal-body">
            <form id= "userForm" class="container">
                <div class="form-group" id="formGrpIdDiv">
                    <label for="username" class="form-control-label form-control-sm">사용자id:</label>
                    <input type="text" class="form-control" id="username" name="username" placeholder="사용자id" required>
                    <div class="invalid-feedback" id="usernameChk"></div>
                </div>
                <div class="form-group">
                    <label for="name" class="form-control-label form-control-sm">사용자명:</label>
                    <input type="text" class="form-control" id="name" name="name" placeholder="사용자명" required>
                    <div class="invalid-feedback"></div>
                </div>
                <div class="form-group">
                    <label for="userAuth" class="form-control-label form-control-sm">사용자권한:</label>
                    <select id = "userAuth" name = "userAuth" class="form-control" required>
                        <c:forEach var="map" items="${roleList}" varStatus="i">
                            <option value="${map.roleid}"> ${map.caption}</option>
                        </c:forEach>
                    </select>
                    <div class="invalid-feedback"></div>
                </div>
                <div class="form-group">
                    <label for="password1" class="form-control-label form-control-sm">비밀번호:</label>
                    <input type="password" class="form-control" id="password1" name="password1" placeholder="Password" required>
                    <div class="invalid-feedback"></div>
                </div>
                <div class="form-group">
                    <label for="password2" class="form-control-label form-control-sm">비밀번호 확인:</label>
                    <input type="password" class="form-control" id="password2" name="password2" placeholder="Password" required>
                    <div class="invalid-feedback" id="pwReChk"></div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" id="btnSmit">등록</button>
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">취소</button>
                </div>
            </form>
        </div>
    </div>
</div>