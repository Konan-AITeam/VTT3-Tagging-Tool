<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../includes/taglib.jsp" %>

<script>
    var _home = "<c:url value="/"/>";
</script>

<!-- Bootstrap core JavaScript-->
<script src="<c:url value="/webjars/jquery/2.2.4/jquery.min.js"/>"></script>
<!-- Bootstrap -->
<script src="<c:url value="/webjars/bootstrap/3.3.6/js/bootstrap.min.js"/>"></script>
<!-- FastClick -->
<script src="<c:url value="/webjars/fastclick/1.0.6/fastclick.js"/>"></script>
<!-- NProgress -->
<script src="<c:url value="/webjars/nprogress/0.2.0/nprogress.js"/>"></script>
<!-- Chart.js -->
<script src="<c:url value="/webjars/chartjs/2.1.3/Chart.min.js"/>"></script>
<script src="<c:url value="/resources/js/loader.js"/>"></script>
<!-- gauge.js -->
<script src="<c:url value="/webjars/gauge.js/1.2.1/dist/gauge.min.js"/>"></script>
<!-- bootstrap-progressbar -->
<script src="<c:url value="/webjars/bootstrap-progressbar/0.9.0/bootstrap-progressbar.min.js"/>"></script>
<!-- iCheck -->
<script src="<c:url value="/webjars/iCheck/1.0.2/icheck.min.js"/>"></script>
<!-- Flot -->
<script src="<c:url value="/webjars/flot/0.8.3/jquery.flot.js"/>"></script>
<script src="<c:url value="/webjars/flot/0.8.3/jquery.flot.pie.js"/>"></script>
<script src="<c:url value="/webjars/flot/0.8.3/jquery.flot.time.js"/>"></script>
<script src="<c:url value="/webjars/flot/0.8.3/jquery.flot.stack.js"/>"></script>
<script src="<c:url value="/webjars/flot/0.8.3/jquery.flot.resize.js"/>"></script>
<!-- Flot plugins -->
<script src="<c:url value="/webjars/flot-spline/0.8.2/js/jquery.flot.spline.min.js"/>"></script>
<script src="<c:url value="/webjars/flot.curvedlines/1.1.1/curvedLines.js"/>"></script>
<!-- DateJS -->
<script src="<c:url value="/webjars/datejs/1.0.0-rc3/build/date.js"/>"></script>
<!-- JQVMap -->
<%--<script src="<c:url value="/webjars/jqvmap/1.5.1/dist/jquery.vmap.js"/>"></script>
<script src="<c:url value="/webjars/jqvmap/1.5.1/dist/maps/jquery.vmap.world.js"/>"></script>
<script src="<c:url value="/webjars/jqvmap/1.5.1/examples/js/jquery.vmap.sampledata.js"/>"></script>--%>
<!-- bootstrap-daterangepicker -->
<script src="<c:url value="/webjars/bootstrap-daterangepicker/2.1.24/js/bootstrap-daterangepicker.js"/>"></script>
<!-- momentjs -->
<script src="<c:url value="/webjars/momentjs/2.13.0/min/moment.min.js"/>"></script>
<!-- blockui -->
<script src="<c:url value="/webjars/jquery-blockui/2.70-1/jquery.blockUI.js"/>"></script>

<script src="<c:url value="/webjars/jquery.dirtyforms/2.0.0/jquery.dirtyforms.min.js"/>"></script>
<!-- Skycons -->
<script src="<c:url value="/resources/js/skycons.js"/>"></script>
<!-- Flot plugins -->
<script src="<c:url value="/resources/js/jquery.flot.orderBars.js"/>"></script>

<!-- Custom Theme Scripts -->
<script src="<c:url value="/resources/custom/js/custom.min.js"/>"></script>

<script src="<c:url value="/webjars/jquery-ui/1.12.1/jquery-ui.min.js"/>"></script>
<script src="<c:url value="/webjars/bootbox/4.4.0/bootbox.js"/>"></script>
<script src="<c:url value="/webjars/tooltipster/4.1.4-1/js/tooltipster.bundle.min.js"/>"></script>
<script src="<c:url value="/webjars/bootpag/1.0.7/lib/jquery.bootpag.min.js"/>"></script>

<!-- Core plugin JavaScript-->
<script src="<c:url value="/resources/vendor/jquery-easing/jquery.easing.min.js"/>"></script>
<!-- Custom scripts for all pages-->
<script src="<c:url value="/resources/js/sb-admin.min.js"/>"></script>


<!-- Toast -->
<script src="<c:url value="/resources/custom/js/jquery-toast/jquery.toast.js"/>"></script>

<!-- Videojs -->
<script src="<c:url value="/resources/custom/js/videojs/video.js"/>"></script>

<!-- HotKeys -->
<script src="<c:url value="/resources/custom/js/jquery.hotkeys.konan.js"/>"></script>


<!-- SVG -->
<script src="<c:url value="/resources/custom/js/jquery-svg/jquery.svg.js" />"></script>

<!-- jcanvas -->
<script src="<c:url value="/resources/custom/js/jcanvas.min.js" />"></script>

<!-- superbox -->
<script src="<c:url value="/resources/custom/js/superbox.js"/>"></script>

<!-- custom -->
<script src="<c:url value="/resources/custom/js/custom.js?_d=${pageContext.session.id}"/>"></script>

<%-- selectarea --%>
<script src="<c:url value="/resources/js/selectareas/jquery.selectareas.js"/>"></script>

<%-- jquery object --%>
<script src="<c:url value="/webjars/github-com-macek-jquery-serialize-object/2.5.0/jquery.serialize-object.js"/>"></script>
<script src="<c:url value="/resources/js/owl.carousel.min.js"/>"></script>
<script src="<c:url value="/resources/js/jquery-ui.js"/>"></script>
<script src="<c:url value="/resources/js/module.js"/>"></script>
<%-- jquery.fileDownload --%>
<script src="<c:url value="/webjars/jquery.fileDownload/50171edfab/jquery.fileDownload.js"/>"></script>


<script>
    <c:if test="${(fn:startsWith(servlet_path,'/content') && !fn:startsWith(servlet_path,'/content/write')) || (fn:startsWith(servlet_path,'/storyboard'))}">
    $(".sidenav-second-level").collapse();
    </c:if>
</script>
<script>
    $(document).ready(function () {
        if($("#loading").length>0){
            $("#loading").remove();
            $("#loading_img").remove();
        }
        var loading = $('<div id="loading" class="loading"></div><img id="loading_img" alt="loading" src="<c:url value="/resources/custom/img/loading-bar.gif"/>" />')
            .appendTo(document.body).hide();
        $(window)
            .ajaxStart(function(){
                loading.show();
            })
            .ajaxStop(function(){
                loading.hide();
            });
        $("#getPutUser").on("click",function (){ getPutUser(); return false;});
        getUserInfo();
    });
</script>
<script>
    function getPutUser() {
        $.ajax({
            url: _home+"user/getPutUser",
            async: false,
            type: "GET",
            dataType: "html",
            success: function (response) {
                $("#addUserModal").html(response);
                $('#addUserModal').modal();
                putUser();
            },
            error: function(xhr, textStatus, error) {
                var msg = "오류가 발생했습니다(code:" + xhr.status + ")\n";
                msg += xhr.responseText;
                MSG.error(msg);
                return false;
            }
        });
    }

    function getUserInfo() {
        $('#addUserModal').on('hidden.bs.modal', function (e) {
            $('#username').val("");
            $("#username").removeClass("is-invalid is-valid");
            $('#name').val("");
            $('#password1').val("");
            $('#password2').val("");
            $("#password2").removeClass("is-invalid is-valid");
            $("#btnSmit").attr("disabled","disabled");
        });
    }
</script>
<script>
    function putUser() {
        $('#btnSmit').click(function (event) {
            if(doubleSubmitCheck()) return;
            event.preventDefault();
            if( $('#username').val() == ""||  $('#name').val() == ""
                ||$('#password2').val() == ""){
                toast("사용자정보", "읿부 입력값이 없습니다..", "info", 5000);
                return false;
            }
            var formData = $("#userForm").serialize();
            $.ajax({
                url: '<c:url value="/user/putUser"/>',
                type: 'POST',
                data: formData,
                async: false,
                success: function (data) {
                    if(data){
                        toast("사용자정보", "저장되었습니다.", "info", 5000);
                        //$('#addUserModal').modal('hide');
                    }else {
                        toast("사용자정보", "저장이 실패 하였습니다..", "info", 5000);
                        //$('#addUserModal').modal('hide');
                    }
                },
                error: function (xhr, ajaxOptions, thrownError) {
                    toast("사용자정보", "저장이 실패 하였습니다..", "info", 5000);
                    //$('#addUserModal').modal('hide');
                },
                complete: function () {
                }
            });
        });

        $("#username").keyup(function (e) {
            if (!(e.keyCode >=37 && e.keyCode<=40)) {
                var inputVal = $(this).val();
                $(this).val(inputVal.replace(/[^a-z0-9]/gi,''));
            }
            var userName =$('#username').val().replace(/ /g, '');//공백제거
            if (userName != "") {
                $.post("<c:url value="/user/idDuplChk"/>", {"username": userName}, function (data) {
                    if (data) {
                        $("#username").removeClass("is-invalid").addClass("is-valid");
                        $("#usernameChk").removeClass("invalid-feedback").addClass("valid-feedback").text("사용가능한 아이디입니다.");
                        $("#btnSmit").removeAttr("disabled");
                    } else {
                        $("#username").removeClass("is-valid").addClass("is-invalid");
                        $("#usernameChk").removeClass("valid-feedback").addClass("invalid-feedback").text("이미사용중인 아이디입니다.");
                        $("#btnSmit").attr("disabled","disabled");

                    }
                });
            }else{
                $("#username").removeClass("is-invalid").addClass("is-valid");
                $("#usernameChk").removeClass("invalid-feedback").addClass("valid-feedback").text("사용자 id 를 입력하세요.(영문/숫자)");
                $("#btnSmit").attr("disabled","disabled");

            }
        });

        $("#password1, #password2").keyup(function (e) {
            var tgtId = $(this).attr("id");
            var pw1 = $("#password1").val();
            var pw2 = $("#password2").val();
            if (!(e.keyCode >=37 && e.keyCode<=40)) {
                var inputVal = $(this).val();
                $(this).val(inputVal.replace(/[^a-z0-9]/gi,''));
            }
            if (tgtId == "password1") {
                if (pw1 != pw2 && pw1 != "" && pw2 != "") {
                    $("#password2").removeClass("is-valid").addClass("is-invalid");
                    $("#pwReChk").removeClass("valid-feedback").addClass("invalid-feedback").text("패스워드가 틀립니다.");
                    $("#btnSmit").attr("disabled","disabled");
                }
            }else if(tgtId == "password2"){
                if (pw1 == pw2 && pw1 != "" && pw2 != "") {
                    $("#password2").removeClass("is-invalid").addClass("is-valid");
                    $("#pwReChk").removeClass("invalid-feedback").addClass("valid-feedback").text("패스워드가 동일합니다.");
                    $("#btnSmit").removeAttr("disabled");
                } else {
                    $("#password2").removeClass("is-valid").addClass("is-invalid");
                    $("#pwReChk").removeClass("valid-feedback").addClass("invalid-feedback").text("패스워드가 틀립니다.");
                    $("#btnSmit").attr("disabled","disabled");
                }

            }
        });
    }
</script>
<script>
    /**
     * 중복서브밋 방지
     *
     * @returns {Boolean}
     */
    var doubleSubmitFlag = false;
    function doubleSubmitCheck(){
        if(doubleSubmitFlag){
            return doubleSubmitFlag;
        }else{
            doubleSubmitFlag = true;
            return false;
        }
    }
</script>

