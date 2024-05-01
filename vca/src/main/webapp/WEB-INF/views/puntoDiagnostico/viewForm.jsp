<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<jsp:include page="../fragments/headTag.jsp" />
<!-- Styles required by this views -->
<spring:url value="/resources/vendors/css/dataTables.bootstrap4.min.css" var="dataTablesCSS" />
<link href="${dataTablesCSS}" rel="stylesheet" type="text/css"/>

<spring:url value="/resources/vendors/css/leaflet.css" var="leafletCSS" />
<link href="${leafletCSS}" rel="stylesheet" type="text/css"/>
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
        <li class="breadcrumb-item"><a href="<spring:url value="/admin/puntos/" htmlEscape="true "/>"><spring:message code="pdxs" /></a></li>
        <li class="breadcrumb-item active"><c:out value="${puntoDiagnostico.clave}" /></li>
        
      </ol>
	  <!-- Container -->
      <div class="container-fluid">
      	<div class="animated fadeIn">

          	<div class="row">
	            <div class="col-md-6">
	              <div class="card">
		               	<div class="card-header">
		                  <i class="icon-compass"></i>&nbsp;<strong><c:out value="${puntoDiagnostico.clave}" /></strong>
		                </div>
	                	<div class="card-body">
	                		<div class="form-group row">
		                      <label class="col-md-3 col-form-label"><spring:message code="ident" />:</label>
		                      <div class="col-md-9">
		                        <p class="form-control-static"><strong><c:out value="${puntoDiagnostico.ident}" /></strong></p>
		                      </div>
		                    </div>
		                    <div class="form-group row">
		                      <label class="col-md-3 col-form-label"><spring:message code="clave" />:</label>
		                      <div class="col-md-9">
		                        <p class="form-control-static"><strong><c:out value="${puntoDiagnostico.clave}" /></strong></p>
		                      </div>
		                    </div>
		                    <div class="form-group row">
		                      <label class="col-md-3 col-form-label"><spring:message code="tipo" />:</label>
		                      <div class="col-md-9">
		                        <p class="form-control-static"><strong><c:out value="${puntoDiagnostico.tipo}" /></strong></p>
		                      </div>
		                    </div>
		                    <div class="form-group row">
		                      <label class="col-md-3 col-form-label"><spring:message code="estpdx" />:</label>
		                      <div class="col-md-9">
		                        <p class="form-control-static"><strong><c:out value="${puntoDiagnostico.status}" /></strong></p>
		                      </div>
		                    </div>
		                    <div class="form-group row">
		                      <label class="col-md-3 col-form-label"><spring:message code="locality" />:</label>
		                      <div class="col-md-9">
		                        <p class="form-control-static"><strong><c:out value="${puntoDiagnostico.local.name}" /></strong></p>
		                      </div>
		                    </div>
		                    <div class="form-group row">
		                      <label class="col-md-3 col-form-label"><spring:message code="district" />:</label>
		                      <div class="col-md-9">
		                        <p class="form-control-static"><strong><c:out value="${puntoDiagnostico.local.district.name}" /></strong></p>
		                      </div>
		                    </div>
		                    <div class="form-group row">
		                      <label class="col-md-3 col-form-label"><spring:message code="area" />:</label>
		                      <div class="col-md-9">
		                        <p class="form-control-static"><strong><c:out value="${puntoDiagnostico.local.district.area.name}" /></strong></p>
		                      </div>
		                    </div>
		                    <div class="form-group row">
		                      <label class="col-md-3 col-form-label"><spring:message code="location" />:</label>
		                      <div class="col-md-9">
		                        <p class="form-control-static"><strong><c:out value="${puntoDiagnostico.latitude}" /> , <c:out value="${puntoDiagnostico.longitude}" />, <c:out value="${puntoDiagnostico.zoom}" /></strong></p>
		                      </div>
		                    </div>
		                    <div class="form-group row">
		                      <label class="col-md-3 col-form-label"><spring:message code="infopdx" />:</label>
		                      <div class="col-md-9">
		                        <p class="form-control-static"><strong><c:out value="${puntoDiagnostico.info}" /></strong></p>
		                      </div>
		                    </div>
		                    <div class="form-group row">
		                      <label class="col-md-3 col-form-label"><spring:message code="enabled" />:</label>
		                      <div class="col-md-9">
		                        <p class="form-control-static"><strong>
		                        	<c:choose>
										<c:when test="${puntoDiagnostico.pasive=='0'.charAt(0)}">
											<strong><spring:message code="CAT_SINO_SI" /></strong>
										</c:when>
										<c:otherwise>
											<strong><spring:message code="CAT_SINO_NO" /></strong>
										</c:otherwise>
									</c:choose>
		                        </strong></p>
		                      </div>
		                    </div>
		                    <div class="form-group row">
		                      <label class="col-md-3 col-form-label"><spring:message code="createdBy" />:</label>
		                      <div class="col-md-9">
		                        <p class="form-control-static"><strong><c:out value="${puntoDiagnostico.recordUser}" /></strong></p>
		                      </div>
		                    </div>
		                    <div class="form-group row">
		                      <label class="col-md-3 col-form-label"><spring:message code="dateCreated" />:</label>
		                      <div class="col-md-9">
		                        <p class="form-control-static"><strong><c:out value="${puntoDiagnostico.recordDate}" /></strong></p>
		                      </div>
		                    </div>
	                    	<spring:url value="/admin/puntos/editEntity/{ident}/" var="editUrl">
                              	<spring:param name="ident" value="${puntoDiagnostico.ident}" />
                          	</spring:url>
            					<spring:url value="/admin/puntos/disableEntity/{ident}/" var="disableUrl">
                              	<spring:param name="ident" value="${puntoDiagnostico.ident}" />
                          	</spring:url>
                          	<spring:url value="/admin/puntos/enableEntity/{ident}/" var="enableUrl">
                              	<spring:param name="ident" value="${puntoDiagnostico.ident}" />
                          	</spring:url>
                          	<spring:url value="/admin/puntos/enterLocation/{ident}/" var="locationUrl">
                              	<spring:param name="ident" value="${puntoDiagnostico.ident}" />
                          	</spring:url>
						</div>
						<div class="card-header">
          				<div class="row float-right mr-4" >
          					<button id="edit_entity" onclick="location.href='${fn:escapeXml(editUrl)}'" type="button" class="btn btn-outline-primary"><i class="fa fa-pencil"></i>&nbsp; <spring:message code="edit" /></button>
          					<c:choose>
								<c:when test="${puntoDiagnostico.pasive=='0'.charAt(0)}">
									<button id="disable_entity" onclick="location.href='${fn:escapeXml(disableUrl)}'" type="button" class="btn btn-outline-danger"><i class="fa fa-close"></i>&nbsp; <spring:message code="disable" /></button>
								</c:when>
								<c:otherwise>
									<button id="enable_entity" onclick="location.href='${fn:escapeXml(enableUrl)}'" type="button" class="btn btn-outline-primary"><i class="fa fa-check"></i>&nbsp; <spring:message code="enable" /></button>
								</c:otherwise>
						 	</c:choose>
						 	<button id="location_button" onclick="location.href='${fn:escapeXml(locationUrl)}'" type="button" class="btn btn-outline-primary"><i class="fa fa-location-arrow"></i>&nbsp; <spring:message code="location" /></button>
          					<button id="back_button" onclick="location.href='<spring:url value="/admin/puntos/" htmlEscape="true "/>'" type="button" class="btn btn-outline-primary"><i class="fa fa-undo"></i>&nbsp; <spring:message code="back" /></button>
          				</div>
            			</div>
					</div>
				</div>
				<div class="col-md-6">
					<div class="card">
						<div class="card-body">
							<div id="mapid" style="width: 100%; height: 680px;"></div>
						</div>
					</div>
				</div>
	            <!--/.col-->
         	</div>
         	
         	<div class="row">
	            <div class="col-md-12">
	              <div class="card">
	                <div class="card-header">
	                  <i class="icon-map"></i>&nbsp;<strong><spring:message code="visit" /></strong>
	                </div>
	                <div class="card-body">
	                	<table id="lista_visitas" class="table table-striped table-bordered datatable" width="100%">
			                <thead>
			                	<tr>
									<th><spring:message code="visitDate" /></th>
									<th><spring:message code="visitType" /></th>
									<th><spring:message code="obs" /></th>
									<th><spring:message code="createdBy" /></th>
	                    			<th><spring:message code="dateCreated" /></th>
	                    			<th></th>
			                	</tr>
			                </thead>
			                <tbody>
							<c:forEach items="${visitas}" var="visita">
								<fmt:formatDate value="${visita.visitDate}" var="fecvisita" pattern="yyyy-MM-dd" />
								<spring:url value="/admin/puntos/visits/disableEntity/{id}/"
                                        var="delUrl">
                                	<spring:param name="id" value="${visita.ident}" />
	                            </spring:url>
	                            <spring:url value="/admin/puntos/visits/editEntity/{id}/"
	                                        var="editUrl">
	                                <spring:param name="id" value="${visita.ident}" />
	                            </spring:url>
								<tr>
									<td><c:out value="${fecvisita}" /></td>
									<td><c:out value="${visita.visitType}" /></td>
									<td><c:out value="${visita.obs}" /></td>
									<td><c:out value="${visita.recordUser}" /></td>
									<td><c:out value="${visita.recordDate}" /></td>
									<td><a href="${fn:escapeXml(editUrl)}" class="btn btn-outline-primary btn-sm"><i class="fa fa-pencil"></i></a><a href="${fn:escapeXml(delUrl)}" class="btn btn-outline-primary btn-sm"><i class="fa fa-trash"></i></a></td>
								</tr>
							</c:forEach>
			                </tbody>
			            </table>
	                </div>
	                <div class="card-header">
         				<div class="row float-right mr-4" >
         					<spring:url value="/admin/puntos/visits/newEntity/{id}/"
                                        var="addVisittUrl">
                                <spring:param name="id" value="${puntoDiagnostico.ident}" />
                            </spring:url>
         					<button id="add_entity" onclick="location.href='${fn:escapeXml(addVisittUrl)}'" type="button" class="btn btn-outline-primary"><i class="fa fa-plus"></i>&nbsp; <spring:message code="add" /></button>
         				</div>
           			</div>
	              </div>
	            </div>
            </div>
         	
            <div class="row">
	            <div class="col-md-12">
	              <div class="card">
	                <div class="card-header">
	                  <i class="icon-pencil"></i>&nbsp;<strong><spring:message code="audittrail" /></strong>
	                </div>
	                <div class="card-body">
	                	<table id="lista_cambios" class="table table-striped table-bordered datatable" width="100%">
			                <thead>
			                	<tr>
									<th><spring:message code="entityClass" /></th>
									<th><spring:message code="entityName" /></th>
									<th><spring:message code="entityProperty" /></th>
									<th><spring:message code="entityPropertyOldValue" /></th>
									<th><spring:message code="entityPropertyNewValue" /></th>
									<th><spring:message code="modifiedBy" /></th>
									<th><spring:message code="dateModified" /></th>
			                	</tr>
			                </thead>
			                <tbody>
							<c:forEach items="${bitacora}" var="cambio">
								<tr>
									<td><spring:message code="${cambio.entityClass}" /></td>
									<td><c:out value="${cambio.entityName}" /></td>
									<td><c:out value="${cambio.entityProperty}" /></td>
									<td><c:out value="${cambio.entityPropertyOldValue}" /></td>
									<td><c:out value="${cambio.entityPropertyNewValue}" /></td>
									<td><c:out value="${cambio.username}" /></td>
									<td><c:out value="${cambio.operationDate}" /></td>
								</tr>
							</c:forEach>
			                </tbody>
			            </table>
	                </div>
	              </div>
	            </div>
            </div>
		</div>
      </div>
      <!-- /.container-fluid -->
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
	<c:when test="${cookie.eSivinLang.value == null}">
		<c:set var="lenguaje" value="es"/>
	</c:when>
	<c:otherwise>
		<c:set var="lenguaje" value="${cookie.eSivinLang.value}"/>
	</c:otherwise>
  </c:choose>
  
  <!-- Plugins and scripts required by this views -->
  <spring:url value="/resources/vendors/js/jquery.validate.min.js" var="JQueryValidate" />
  <script src="${JQueryValidate}" type="text/javascript"></script>
  <spring:url value="/resources/vendors/js/i18n/validation/messages_{language}.js" var="jQValidationLoc">
      <spring:param name="language" value="${lenguaje}" />
  </spring:url>
  <script src="${jQValidationLoc}"></script>
  <spring:url value="/resources/vendors/js/i18n/datatables/label_{language}.json" var="dataTablesLang">
  	<spring:param name="language" value="${lenguaje}" />
  </spring:url>
  <spring:url value="/resources/vendors/js/jquery.dataTables.min.js" var="dataTablesSc" />
  <script src="${dataTablesSc}" type="text/javascript"></script>
  <spring:url value="/resources/vendors/js/dataTables.bootstrap4.min.js" var="dataTablesBsSc" />
  <script src="${dataTablesBsSc}" type="text/javascript"></script>
  
  <spring:url value="/resources/vendors/js/leaflet.js" var="leafletJS" />
  <script src="${leafletJS}" type="text/javascript"></script>
  
  <c:set var="entityEnabledLabel"><spring:message code="enabled" /></c:set>
  <c:set var="entityDisabledLabel"><spring:message code="disabled" /></c:set>
  
  <script>
  
    $(function(){
	  $('.datatable').DataTable({
          "oLanguage": {
              "sUrl": "${dataTablesLang}"
          },
          "scrollX": true,
          "lengthMenu": [[5,10, 25, 50], [5,10, 25, 50]]
      });
	  $('.datatable').attr('style', 'border-collapse: collapse !important');
	});
    
    if ("${entidadHabilitada}"){
		toastr.info("${entityEnabledLabel}", "${nombreEntidad}", {
		    closeButton: true,
		    progressBar: true,
		  } );
	}
	if ("${entidadDeshabilitada}"){
		toastr.error("${entityDisabledLabel}", "${nombreEntidad}" , {
		    closeButton: true,
		    progressBar: true,
		  });
	}
	
	var mymap = L.map('mapid').setView([${latitude}, ${longitude}], ${zoom});

	L.tileLayer('http://a.tile.openstreetmap.fr/hot/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw', {
		maxZoom: 18,
		attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors, ' +
			'<a href="https://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, ' +
			'Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
		id: 'mapbox.streets'
	}).addTo(mymap);
	
	var miLat = "${puntoDiagnostico.latitude}";
	var miLong = "${puntoDiagnostico.longitude}";
	
	if(!(miLat == "" || miLong == "")){
		var marker = L.marker([${puntoDiagnostico.latitude}, ${puntoDiagnostico.longitude}]).addTo(mymap);
		marker.bindTooltip("${puntoDiagnostico.clave}");
	}
	
	var popup = L.popup();

  </script>
</body>
</html>