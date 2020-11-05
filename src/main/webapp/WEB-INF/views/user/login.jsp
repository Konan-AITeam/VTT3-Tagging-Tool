<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../includes/taglib.jsp" %>

<!DOCTYPE html>
<html lang="en-us" id="extr-page">
<head>
    <meta charset="utf-8">
    <title>DEMO</title>
    <meta name="description" content="">
    <meta name="author" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">

    <link rel="stylesheet" type="text/css" href="<c:url value="/webjars/jquery-ui/1.12.1/jquery-ui.min.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/webjars/bootstrap/3.3.6/css/bootstrap.min.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/webjars/font-awesome/4.7.0/css/font-awesome.min.css"/>">

    <!-- Custom styles for this template-->
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/sb-admin.css"/>">

    <!-- CUSTOM CSS -->
    <link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/resources/custom/css/style.css" />"/>
    <!-- #FAVICONS -->
    <link rel="shortcut icon" type="image/ico" href="<c:url value="/resources/custom/img/favicon.ico" />"/>
    <link rel="icon" href="<c:url value="/resources/custom/img/favicon.ico" />" type="image/x-icon">

</head>


<body class="bg-dark">

<div class="container">
    <div class="card card-login mx-auto mt-5" style="margin: 8% auto;">
        <div class="card-header">Login</div>
        <div class="card-body">
            <form id="login-form" class="smart-form client-form" method="post">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <div class="form-group">
                    <label for="exampleInputEmail1">ID</label>
                    <input class="form-control" id="exampleInputEmail1" name="username" value="input" type="text" aria-describedby="emailHelp" placeholder="Enter ID">

                </div>
                <div class="form-group">
                    <label for="exampleInputPassword1">Password</label>
                    <input class="form-control" id="exampleInputPassword1" type="password" placeholder="Password" name="password" value="">
                </div>
                <div class="form-group">
                    <div class="form-check">
                        <label class="form-check-label">
                            <input class="form-check-input" type="checkbox" name="remember" checked=""> Remember Password</label>
                    </div>
                </div>
                <button class="btn btn-primary btn-block" >
                    <spring:message code="login" text="로그인"/>
                </button>
            </form>
            <div class="text-center">

                <div class="error" id="err_common" style="display:none;color:red;">
                    <p>
                        <spring:message code="login.check" text="ID 또는 비밀번호를 다시 확인하세요."></spring:message><br>
                        <spring:message code="login.wrong" text="등록되지 않은 ID이거나, 아이디 또는 비밀번호를 잘못 입력하셨습니다."/>
                    </p>
                </div>

                <div class="error" id="logout_common" style="display:none;color:red;">
                    <p><spring:message code="logout.completed" text="로그아웃 되었습니다."/></p>
                </div>

            </div>
        </div>
    </div>
</div>

<!-- Bootstrap core JavaScript-->
<script src="<c:url value="/webjars/jquery/2.2.4/jquery.min.js"/>"></script>
<script src="<c:url value="/webjars/jquery-ui/1.12.1/jquery-ui.min.js"/>"></script>
<script src="<c:url value="/webjars/bootstrap/3.3.6/js/bootstrap.min.js"/>"></script>
<script src="<c:url value="/webjars/bootbox/4.4.0/bootbox.js"/>"></script>
<script src="<c:url value="/webjars/jquery-validation/1.17.0/jquery.validate.min.js"/>"></script>

<script type="text/javascript">

    $(function () {

        <c:if test="${param.error != null}">
        showErrorMsg();
        </c:if>

        <c:if test="${ param.logout != null}">
        showLogoutMsg();
        </c:if>

        // Validation
        $("#login-form").validate({
            // Rules for form validation
            rules: {
                email: {
                    required: true,
                    email: true
                },
                password: {
                    required: true,
                    minlength: 3,
                    maxlength: 20
                }
            },

            // Messages for form validation
            messages: {
                email: {
                    required: '<spring:message code="login.input.id" text="ID를 입력해주세요."/>'
                },
                password: {
                    required: '<spring:message code="login.input.password" text="비밀번호를 입력해주세요."/>'
                }
            },

            // Do not change code below
            errorPlacement: function (error, element) {
                error.insertAfter(element.parent());
            }
        });

        $("#login-form").submit(function () {
            if ($("#login-form").valid() == false) {
                return false;
            }
            return true;
        });

        // Login Error
        function showErrorMsg() {
            $("#err_common").show();

            $("input[name=username]").val("");
            $("input[name=password]").focus();

            setTimeout(function () {
                $("#err_common").hide();
            }, 2000);
        }

        // Logout
        function showLogoutMsg() {
            $("#logout_common").show();
            setTimeout(function () {
                $("#logout_common").hide();
            }, 2000)
        }
    });

</script>
</body>
</html>