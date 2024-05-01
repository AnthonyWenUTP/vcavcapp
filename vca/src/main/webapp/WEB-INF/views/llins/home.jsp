<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<jsp:include page="../fragments/headTag.jsp" />
<!-- Styles required by this views -->
<spring:url value="/resources/vendors/css/dataTables.bootstrap4.min.css" var="dataTablesCSS" />
<link href="${dataTablesCSS}" rel="stylesheet" type="text/css"/>
<spring:url value="/resources/vendors/css/responsive.dataTables.min.css" var="dataTablesResponsiveCSS" />
<link href="${dataTablesResponsiveCSS}" rel="stylesheet" type="text/css"/>
<spring:url value="/resources/vendors/css/buttons.dataTables.min.css" var="dataTablesButtonCSS" />
<link href="${dataTablesButtonCSS}" rel="stylesheet" type="text/css"/>
<spring:url value="/resources/vendors/css/select2.min.css" var="select2css" />
<link href="${select2css}" rel="stylesheet" type="text/css"/>
<spring:url value="/resources/vendors/css/daterangepicker.css" var="dtrpcss" />
<link href="${dtrpcss}" rel="stylesheet" type="text/css"/>
<spring:url value="/resources/vendors/css/leaflet.css" var="leafletCSS" />
<link href="${leafletCSS}" rel="stylesheet" type="text/css"/>
<style>

/*Legend specific*/
.legend {
  padding: 6px 8px;
  font: 14px Arial, Helvetica, sans-serif;
  background: white;
  background: rgba(255, 255, 255, 0.8);
  /*box-shadow: 0 0 15px rgba(0, 0, 0, 0.2);*/
  /*border-radius: 5px;*/
  line-height: 24px;
  color: #555;
}
.legend h4 {
  text-align: center;
  font-size: 16px;
  margin: 2px 12px 8px;
  color: #777;
}

.legend span {
  position: relative;
  bottom: 3px;
}

.legend i {
  width: 18px;
  height: 18px;
  float: left;
  margin: 0 8px 0 0;
  opacity: 0.7;
}

.legend i.icon {
  background-size: 18px;
  background-color: rgba(255, 255, 255, 1);
}
.info {
    padding: 6px 8px;
    font: 14px/16px Arial, Helvetica, sans-serif;
    background: white;
    background: rgba(255,255,255,0.8);
    box-shadow: 0 0 15px rgba(0,0,0,0.2);
    border-radius: 5px;
}
.info h4 {
    margin: 0 0 5px;
    color: #777;
}
</style>

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

      <!-- Breadcrumb -->
      <ol class="breadcrumb">
        <li class="breadcrumb-item"><a href="<spring:url value="/" htmlEscape="true "/>"><spring:message code="home" /></a></li>
        <li class="breadcrumb-item active"><a href="<spring:url value="/llins/dashboard/" htmlEscape="true "/>"><spring:message code="strLlins" /></a></li>
        <li class="breadcrumb-item active"><spring:message code="dashboard" /></li>
      </ol>
      
	  <!-- Container -->
      <div class="container-fluid">
        <div class="animated fadeIn">
        	<div class="row">
        		<div class="col-6 col-lg-6">
	        		<div class="card">
	        		<form action="#" autocomplete="off" id="temp-form">
		        		<select name="ciclo" id="ciclo" class="form-control select2-single">
							<c:forEach items="${ciclos}" var="ciclo">
								<option value="${ciclo.ident}"><spring:message code="${ciclo.name}" /></option>
							</c:forEach>
						</select>
					</form>
					</div>
				</div>
        	</div>
        	<div class="row">
        		<div class="col-12 col-lg-4">
	            <div class="card">
              		<i class="fa fa-sliders bg-primary p-3 font-lg mr-6 float-left"> <spring:message code="strTargets" /></i>	
			  		<ul class="list-group list-group-flush">
			    		<li class="list-group-item d-flex justify-content-between align-items-center font-lg">
						    <spring:message code="targets" />
						    <span id="labelTotalMetas" class="badge badge-primary badge-pill"></span>
						</li>
			  		</ul>
				</div>	
				</div>
				
				<div class="col-12 col-lg-4">
	            <div class="card">
              		<i class="fa fa-server bg-primary p-3 font-lg mr-6 float-left"> <spring:message code="strDeliver" /></i>	
			  		<ul class="list-group list-group-flush">
			    		<li class="list-group-item d-flex justify-content-between align-items-center font-lg">
						    <spring:message code="totvisit" />
						    <span id="labelVisitas" class="badge badge-primary badge-pill"></span>
						</li>
			  		</ul>
				</div>	
				</div>
				
				<div class="col-12 col-lg-4">
	            <div class="card">
              		<i class="fa fa-check bg-primary p-3 font-lg mr-6 float-left"> <spring:message code="strEvals" /></i>	
			  		<ul class="list-group list-group-flush">
			    		<li class="list-group-item d-flex justify-content-between align-items-center font-lg">
						    <spring:message code="vivcuartosroc" />
						    <span id="labelRociables" class="badge badge-primary badge-pill"></span>
						</li>
			  		</ul>
				</div>	
				</div>
            </div>
         	
         	
		</div>
      </div>
      <!-- /.container-fluid -->
    </main>
    <aside class="aside-menu">
	  <ul class="nav nav-tabs" role="tablist">
        <li class="nav-item">
          
        </li>
      </ul>
      <!-- Tab panes -->
      <div class="tab-content">
        <div class="tab-pane active" id="filtros" role="tabpanel">
          <form action="#" autocomplete="off" id="filters-form">
	          <div class="callout m-0 py-2 text-muted text-center bg-light text-uppercase">
	            <small><b><spring:message code="filters" /></b>
	          </div>
	          <hr class="transparent mx-3 my-0">
	          <div class="callout callout-info m-0 py-2">
	            <div>
	              <strong><spring:message code="area" /></strong>
	            </div>
	            <select name="area" id="area" class="form-control select2-single">
					<option value="ALL"><spring:message code="all"/></option>
					<c:forEach items="${areas}" var="area">
						<option value="${area.ident}"><spring:message code="${area.name}" /></option>
					</c:forEach>
				</select>
	          </div>
	          <hr class="mx-3 my-0">
	          <div class="callout callout-success m-0 py-2">
	            <div>
	              <strong><spring:message code="district" /></strong>
	            </div>
	            <select name="district" id="district" class="form-control select2-single">
					<option value="ALL"><spring:message code="all"/></option>
					<c:forEach items="${distritos}" var="distrito">
						<option value="${distrito.ident}"><spring:message code="${distrito.name}" /></option>
					</c:forEach>
				</select>
	          </div>
	          <hr class="mx-3 my-0">
	          <div class="callout callout-danger m-0 py-2">
	            <div>
	              <strong><spring:message code="foci" /></strong>
	            </div>
	            <select name="foci" id="foci" class="form-control select2-single">
					<option value="ALL"><spring:message code="all"/></option>
					<c:forEach items="${focos}" var="foco">
						<option value="${foco.ident}"><spring:message code="${foco.name}" /></option>
					</c:forEach>
				</select>
	          </div>
	          <hr class="mx-3 my-0">
	          <div class="callout callout-warning m-0 py-2">
	            <div>
	              <strong><spring:message code="locality" /></strong>
	            </div>
	            <select name="localidad" id="localidad" class="form-control select2-single">
					<option value="ALL"><spring:message code="all"/></option>
					<c:forEach items="${localidades}" var="localidad">
						<option value="${localidad.ident}"><spring:message code="${localidad.name}" /></option>
					</c:forEach>
				</select>
	          </div>
	          <hr class="mx-3 my-0">
	          
	          <div class="callout m-0 py-2">
	          	<div class="float-right">
	              <button type="submit" class="btn btn-primary" id="actualizardatos""><i class="fa fa-refresh"></i>&nbsp;<spring:message code="update" /></button>
	            </div>
	          </div>
          </form>
        </div>
      </div>
    </aside>
    
  </div>
  <!-- Pie de página -->
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
  <spring:url value="/resources/vendors/js/jquery.dataTables.min.js" var="dataTablesSc" />
  <script src="${dataTablesSc}" type="text/javascript"></script>
  <spring:url value="/resources/vendors/js/dataTables.bootstrap4.min.js" var="dataTablesBsSc" />
  <script src="${dataTablesBsSc}" type="text/javascript"></script>
  <spring:url value="/resources/vendors/js/dataTables.responsive.min.js" var="dataTablesResponsive" />
  <script src="${dataTablesResponsive}" type="text/javascript"></script>
  <spring:url value="/resources/vendors/js/i18n/datatables/label_{language}.json" var="dataTablesLang">
  	<spring:param name="language" value="${lenguaje}" />
  </spring:url>
  <spring:url value="/resources/vendors/js/moment.min.js" var="moment" />
  <script src="${moment}" type="text/javascript"></script>  
  <spring:url value="/resources/vendors/js/daterangepicker.min.js" var="drPicker" />
  <script src="${drPicker}" type="text/javascript"></script>
  <spring:url value="/resources/vendors/js/select2.min.js" var="Select2" />
  <script src="${Select2}" type="text/javascript"></script>
  
  
  <spring:url value="/resources/vendors/js/dataTables.buttons.min.js" var="dataTablesButtons" />
  <script src="${dataTablesButtons}" type="text/javascript"></script>
  <spring:url value="/resources/vendors/js/buttons.flash.min.js" var="dataTablesButtonsFlash" />
  <script src="${dataTablesButtonsFlash}" type="text/javascript"></script>
  <spring:url value="/resources/vendors/js/jszip.min.js" var="dataTablesButtonsZip" />
  <script src="${dataTablesButtonsZip}" type="text/javascript"></script>
  <spring:url value="/resources/vendors/js/pdfmake.min.js" var="dataTablesButtonsPdfMake" />
  <script src="${dataTablesButtonsPdfMake}" type="text/javascript"></script>
  <spring:url value="/resources/vendors/js/vfs_fonts.js" var="dataTablesButtonsPdfFonts" />
  <script src="${dataTablesButtonsPdfFonts}" type="text/javascript"></script>
  <spring:url value="/resources/vendors/js/buttons.html5.min.js" var="dataTablesButtonsHTML5" />
  <script src="${dataTablesButtonsHTML5}" type="text/javascript"></script>
  <spring:url value="/resources/vendors/js/buttons.print.min.js" var="dataTablesButtonsPrint" />
  <script src="${dataTablesButtonsPrint}" type="text/javascript"></script>
  <spring:url value="/resources/vendors/js/leaflet.js" var="leafletJS" />
  <script src="${leafletJS}" type="text/javascript"></script>
  
  <!-- Custom scripts required by this view -->
  <spring:url value="/resources/js/views/homeLlins.js" var="ProcessDashboardLlins" />
  <script src="${ProcessDashboardLlins}"></script>
  
  

<script>

jQuery(document).ready(function() {
	var parametros = {};
	ProcessDashboardLlins.init(parametros);
});


</script>

</body>
</html>