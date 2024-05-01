<%@ page contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<header class="app-header navbar">
  <button class="navbar-toggler mobile-sidebar-toggler d-lg-none mr-auto" type="button">
    <span class="navbar-toggler-icon"></span>
  </button>
  <a class="navbar-brand" href="<spring:url value="/" htmlEscape="true "/>"></a>
  <button class="navbar-toggler sidebar-toggler d-md-down-none" type="button">
    <span class="navbar-toggler-icon"></span>
  </button>
  <ul class="nav navbar-nav ml-auto">
  	<!-- BEGIN LANGUAGE DROPDOWN -->
  	<li class="nav-item dropdown pr-4">
        <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false">
          <i class="fa fa-language"></i>
        </a>
        <div class="dropdown-menu dropdown-menu-right">
          <div class="dropdown-header text-center">
            <strong><spring:message code="choose" /></strong>
          </div>
          <a class="dropdown-item" href="?lang=en"><i class="fa fa-flag"></i>English</a>
          <a class="dropdown-item" href="?lang=es"><i class="icon-flag"></i>Español</a>
        </div>
    </li>
    <!-- BEGIN USER LOGIN DROPDOWN -->
    <li class="nav-item dropdown pr-4">
        <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false">
            <i class="fa fa-user"></i><span class="username"></span>
        </a>
        <div class="dropdown-menu dropdown-menu-right">
        	<div class="dropdown-header text-center">
            	<strong><spring:message code="actions" /></strong>
          	</div>
          	<a class="dropdown-item" href="<spring:url value="/users/profile" htmlEscape="true" />"><i class="fa fa-user"></i> <spring:message code="profile" /></a>
          	<a class="dropdown-item" href="<spring:url value="/users/chgpass" htmlEscape="true" />""><i class="fa fa-key"></i> <spring:message code="changepass" /></a>
        </div>
    </li>
    <!-- END USER LOGIN DROPDOWN -->
    <button class="navbar-toggler aside-menu-toggler" type="button">
      <span class="navbar-toggler-icon"></span>
    </button>
  </ul>
</header>