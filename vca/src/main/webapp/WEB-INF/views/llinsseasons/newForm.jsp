<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
<jsp:include page="../fragments/headTag.jsp" />
<!-- Styles required by this views -->
<spring:url value="/resources/vendors/css/select2.min.css" var="select2css" />
<link href="${select2css}" rel="stylesheet" type="text/css"/>
<spring:url value="/resources/vendors/css/datepicker.css" var="datepickercss" />
<link href="${datepickercss}" rel="stylesheet" type="text/css"/>
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
  <jsp:include page="../fragments/bodyHeader.jsp" />
  <div class="app-body">
  	<!-- Navigation -->
  	<jsp:include page="../fragments/sideBar.jsp" />
    <!-- Main content -->
    <main class="main">
	  <spring:url value="/llins/season/saveEntity/" var="saveUrl"></spring:url>
  	  <spring:url value="/llins/season/" var="seasonsUrl"></spring:url>	
      <!-- Breadcrumb -->
      <ol class="breadcrumb">
        <li class="breadcrumb-item"><a href="<spring:url value="/" htmlEscape="true "/>"><spring:message code="home" /></a></li>
        <li class="breadcrumb-item active"><a href="<spring:url value="/llins/dashboard/" htmlEscape="true "/>"><spring:message code="strLlins" /></a></li>
        <li class="breadcrumb-item"><a href="<spring:url value="/llins/season/" htmlEscape="true "/>"><spring:message code="strCycle" /></a></li>
        <li class="breadcrumb-item active"><spring:message code="add" /></li>
        
      </ol>
	  <!-- Container -->
      <div class="container-fluid">

        <div class="animated fadeIn">
          <div class="row">
            <div class="col-md-12">
              <div class="card">
                <div class="card-header">
                  <i class="icon-note"></i> <spring:message code="add" />
                  <div class="card-actions">
                    
                  </div>
                </div>
                <div class="card-body">

                  <div class="row">

                    <div class="col-md-8">
                      <form action="#" autocomplete="off" id="add-form">                      
						<div class="form-group">
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-key"></i></span>
	                        <input type="text" id="ident" name="ident" readonly class="form-control" placeholder="<spring:message code="ident" />">
	                      </div>
	                    </div>	
	                    <div class="form-group">
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-id-card"></i></span>
	                        <input type="text" id="code" name="code" class="form-control" placeholder="<spring:message code="code" />">
	                      </div>
	                    </div>  
                        <div class="form-group">
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-address-book"></i></span>
	                        <input type="text" id="name" name="name" class="form-control" placeholder="<spring:message code="name" />">
	                      </div>
	                    </div>
	                    
	                    <div class="form-group">
	                      <label><spring:message code="startDate" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
	                        <input type="text" id="startDate" name="startDate" class="form-control date-picker" data-date-format="dd/mm/yyyy" data-date-start-date="-90d" data-date-end-date="+90d" placeholder="dd/mm/yyy">
	                      </div>
	                    </div>
	                    
	                    <div class="form-group">
	                      <label><spring:message code="endDate" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
	                        <input type="text" id="endDate" name="endDate" class="form-control date-picker" data-date-format="dd/mm/yyyy" data-date-start-date="+0d" data-date-end-date="+0d" placeholder="dd/mm/yyy">
	                      </div>
	                    </div>
	                    
	                    <fieldset class="form-group">
		                 	<i class="fa fa-map-o"></i>
		                    <label><spring:message code="locality" /></label>
		                    <select id="localidades" name="localidades" class="form-control select2-multiple" multiple="">
		                      <c:forEach items="${localidades}" var="localidad">
		                      	<option value="${localidad.ident}">${localidad.code}-${localidad.name}-${localidad.district.name}-${localidad.district.area.name}</option>
		                      </c:forEach>
		                    </select>
		                 </fieldset>
		                 
		                 <div class="form-group">
	                      <label><spring:message code="obs" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-sticky-note-o"></i></span>
	                        <input type="text" id="obs" name="obs" class="form-control" placeholder="<spring:message code="obs" />">
	                      </div>
	                    </div>
                        
                        <div class="form-group">
                          <button type="submit" class="btn btn-primary" id="guardar"><i class="fa fa-save"></i>&nbsp;<spring:message code="save" /></button>
						  <a href="${fn:escapeXml(seasonsUrl)}" class="btn btn-danger"><i class="fa fa-undo"></i>&nbsp;<spring:message code="cancel" /></a>
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
      <!-- /.conainer-fluid -->
    </main>
    
  </div>
  <!-- Pie de p�gina -->
  <jsp:include page="../fragments/bodyFooter.jsp" />

  <!-- Bootstrap and necessary plugins -->
  <jsp:include page="../fragments/corePlugins.jsp" />
  <jsp:include page="../fragments/bodyUtils.jsp" />

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
  <spring:url value="/resources/vendors/js/select2.min.js" var="Select2" />
  <script src="${Select2}" type="text/javascript"></script>
  
  <spring:url value="/resources/vendors/js/bootstrap-datepicker.js" var="datepicker" />
  <script src="${datepicker}" type="text/javascript"></script>
  
  <spring:url value="/resources/vendors/js/moment.min.js" var="moment" />
  <script src="${moment}" type="text/javascript"></script>
  

  <!-- Custom scripts required by this view -->
  <spring:url value="/resources/js/views/Season.js" var="processSeason" />
  <script src="${processSeason}"></script>
  
<c:set var="successmessage"><spring:message code="process.success" /></c:set>
<c:set var="errormessage"><spring:message code="process.errors" /></c:set>
<c:set var="waitmessage"><spring:message code="process.wait" /></c:set>

<script>
	jQuery(document).ready(function() {
		var parametros = {saveUrl: "${saveUrl}", successmessage: "${successmessage}",
				errormessage: "${errormessage}",waitmessage: "${waitmessage}",
				seasonsUrl: "${seasonsUrl}" 
		};
		ProcessEntity.init(parametros);
	});
</script>
  
</body>
</html>