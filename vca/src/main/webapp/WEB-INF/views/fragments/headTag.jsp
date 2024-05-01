<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="<spring:message code="description" />">
<meta name="author" content="ICS Nicaragua">
<meta name="keyword" content="<spring:message code="keyword" />">
<title><spring:message code="title" /> | <spring:message code="heading" /></title>
<!-- BEGIN GLOBAL MANDATORY STYLES -->

<!-- Icons -->
<spring:url value="/resources/vendors/css/font-awesome.min.css" var="fontawesome" />
<link href="${fontawesome}" rel="stylesheet" type="text/css"/>
<spring:url value="/resources/vendors/css/simple-line-icons.min.css" var="simplelineicons" />
<link href="${simplelineicons}" rel="stylesheet" type="text/css"/>

<!-- Main styles for this application -->
<spring:url value="/resources/css/style.css" var="generalstyle" />
<link href="${generalstyle}" rel="stylesheet" type="text/css"/>
<spring:url value="/resources/vendors/css/toastr.min.css" var="toastcss" />
<link href="${toastcss}" rel="stylesheet" type="text/css"/>
<spring:url value="/resources/vendors/css/jquery-confirm.min.css" var="jConfirmcss" />
<link href="${jConfirmcss}" rel="stylesheet" type="text/css"/>
<spring:url value="/resources/vendors/css/chart.min.css" var="Chartcss" />
<link href="${Chartcss}" rel="stylesheet" type="text/css"/>
<spring:url value="/resources/vendors/css/tipso.css" var="Tipsocss" />
<link href="${Tipsocss}" rel="stylesheet" type="text/css"/>

<!-- END GLOBAL MANDATORY STYLES -->

<spring:url value="/resources/img/favicon.ico" var="favicon" />
<link rel="shortcut icon" href="${favicon}"/>