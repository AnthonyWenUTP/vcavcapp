<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
<jsp:include page="fragments/headTag.jsp" />
<!-- Styles required by this views -->

</head>
<!-- BODY options, add following classes to body to change options

// Header options
1. '.header-fixed'					- Fixed Header

// Brand options
1. '.brand-minimized'       - Minimized brand (Only symbol)

// Sidebar options
1. '.sidebar-fixed'					- Fixed Sidebar
2. '.sidebar-hidden'				- Hidden Sidebar
3. '.sidebar-off-canvas'		- Off Canvas Sidebar
4. '.sidebar-minimized'			- Minimized Sidebar (Only icons)
5. '.sidebar-compact'			  - Compact Sidebar

// Aside options
1. '.aside-menu-fixed'			- Fixed Aside Menu
2. '.aside-menu-hidden'			- Hidden Aside Menu
3. '.aside-menu-off-canvas'	- Off Canvas Aside Menu

// Breadcrumb options
1. '.breadcrumb-fixed'			- Fixed Breadcrumb

// Footer options
1. '.footer-fixed'					- Fixed footer

-->
<body class="app header-fixed sidebar-fixed aside-menu-fixed aside-menu-hidden">
  <!-- Header -->
  <div class="app-body">
  	
    <!-- Main content -->
    <main class="main">

	  <!-- Container -->
      <div class="container-fluid">
        <div class="animated fadeIn">
        	<div class="row">
            <div class="col-md-12">
              <div class="card">
                <div class="card-header">
                  <i class="icon-key"></i> <spring:message code="resetPassword" />
                  <div class="card-actions">
                    
                  </div>
                </div>
                <div class="card-body">

                  <div class="row">

                    <div class="col-md-8">
                      <form action="#" autocomplete="off" id="send-pass-form">
                      	<div class="form-group">
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-user"></i></span>
	                        <input type="text" id="username" name="username" value="" class="form-control" placeholder="<spring:message code="username" />">
	                      </div>
	                    </div>                      
	                    <div class="form-group">
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-envelope"></i></span>
	                        <input type="text" id="email" name="email" value="" class="form-control" placeholder="<spring:message code="useremail" />">
	                      </div>
	                    </div>
                        
                        <div class="form-group">
                          <button type="submit" class="btn btn-primary" id="guardar" style="width:25%"><i class="fa fa-unlock-alt"></i>&nbsp;<spring:message code="ok" /></button>
                        </div>
                        
                        <div class="col-6 text-right">
		                    <a href="<spring:url value="/login" htmlEscape="true "/>" class="btn btn-link px-0"><spring:message code="backLogin"/></a>
		                </div>
                      </form>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <!-- /.col -->
          </div>
          <!-- /.row -->
        </div>
      </div>
      <!-- /.container-fluid -->
    </main>
    
  </div>
  <!-- Pie de página -->
  <jsp:include page="fragments/bodyFooter.jsp" />

  <!-- Bootstrap and necessary plugins -->
  <jsp:include page="fragments/corePlugins.jsp" />

  <!-- GenesisUI main scripts -->
  <spring:url value="/resources/js/app.js" var="App" />
  <script src="${App}" type="text/javascript"></script>
  
  <!-- Lenguaje -->
  <c:choose>
	<c:when test="${cookie.evcaLang.value == null}">
		<c:set var="lenguaje" value="es"/>
	</c:when>
	<c:otherwise>
		<c:set var="lenguaje" value="${cookie.evcaLang.value}"/>
	</c:otherwise>
  </c:choose>
  
  <!-- Plugins and scripts required by this views -->
  <spring:url value="/resources/vendors/js/jquery.validate.min.js" var="JQueryValidate" />
  <script src="${JQueryValidate}" type="text/javascript"></script>
  <spring:url value="/resources/vendors/js/i18n/validation/messages_{language}.js" var="jQValidationLoc">
      <spring:param name="language" value="${lenguaje}" />
  </spring:url>
  <script src="${jQValidationLoc}"></script>
  
  <!-- Custom scripts required by this view -->
  <spring:url value="/resources/js/views/resetPassword.js" var="resetPassword" />
  <script src="${resetPassword}"></script>
  
  <spring:url value="/resetPassword" var="generateTokenUrl"></spring:url>
  <spring:url value="/login" var="loginUrl"></spring:url>
  
  <script>
	jQuery(document).ready(function() {
		var parametros = {generateTokenUrl: "${generateTokenUrl}", successmessage: "${successmessage}",
				errormessage: "${errormessage}",waitmessage: "${waitmessage}",
				loginUrl: "${loginUrl}" 
		};
		ResetPassword.init(parametros);
	});
</script>

</body>
</html>