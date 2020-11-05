<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../includes/taglib.jsp" %>
<c:set var="servlet_path" scope="request"><c:out value="${ requestScope['javax.servlet.forward.servlet_path'] }"/></c:set>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="icon" href="<c:url value="/resources/custom/img/favicon.ico" />" type="image/ico" />

    <title> VTT </title>

    <!-- Bootstrap -->
    <link rel="stylesheet" type="text/css" href="<c:url value="/webjars/bootstrap/3.3.6/css/bootstrap.min.css"/>">
    <!-- Font Awesome -->
    <link rel="stylesheet" type="text/css" href="<c:url value="/webjars/font-awesome/4.7.0/css/font-awesome.min.css"/>">
    <!-- NProgress -->
    <link rel="stylesheet" type="text/css" href="<c:url value="/webjars/nprogress/0.2.0/nprogress.css"/>">
    <!-- iCheck -->
    <link rel="stylesheet" type="text/css" href="<c:url value="/webjars/iCheck/1.0.2/skins/flat/green.css"/>">
    <!-- bootstrap-progressbar -->
    <link rel="stylesheet" type="text/css" href="<c:url value="/webjars/bootstrap-progressbar/0.9.0/css/bootstrap-progressbar-3.3.4.min.css"/>">
    <!-- bootstrap-daterangepicker -->
    <link rel="stylesheet" type="text/css" href="<c:url value="/webjars/bootstrap-daterangepicker/2.1.24/css/bootstrap-daterangepicker.css"/>">
    <!-- Custom Theme Style -->
    <link rel="stylesheet" type="text/css" href="<c:url value="/webjars/iCheck/1.0.2/skins/flat/green.css"/>">

    <!-- Custom Theme Style -->
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/custom.min.css"/>" rel="stylesheet">

    <!-- Custom Theme Style -->
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/custom/css/j-css.css?_d=${pageContext.session.id}"/>" rel="stylesheet">

    <!-- Toast -->
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/custom/css/jquery.toast.css"/>">
    <%-- selectarea --%>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/jquery.selectareas.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/custom/css/style.css?_d=${pageContext.session.id}"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/custom/css/style.new.css?_d=${pageContext.session.id}"/>">
    <!-- Videojs -->
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/custom/css/video-js.css"/>">
    <!-- dropzone style -->
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/custom/js/dropzone/dropzone.min.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/owl.carousel.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/owl.theme.default.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/owl.theme.green.css"/>">

    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.3/css/all.css" integrity="sha384-UHRtZLI+pbxtHCWp1t77Bi1L4ZtiqrqD80Kn4Z8NTSRyMA2Fd33n5dQ8lWUE00s/" crossorigin="anonymous">

    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">


</head>

<body class="nav-sm">

<div class="main_container" style="padding-left: 0px;">
