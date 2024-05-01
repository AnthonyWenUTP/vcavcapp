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

<spring:url value="/resources/vendors/css/select2.min.css" var="select2css" />
<link href="${select2css}" rel="stylesheet" type="text/css"/>

<spring:url value="/resources/vendors/css/leaflet.css" var="leafletCSS" />
<link href="${leafletCSS}" rel="stylesheet" type="text/css"/>

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
      <!-- Breadcrumb -->
      <ol class="breadcrumb">
        <li class="breadcrumb-item"><a href="<spring:url value="/" htmlEscape="true "/>"><spring:message code="home" /></a></li>
        <li class="breadcrumb-item"><a href="<spring:url value="/admin/tests/" htmlEscape="true "/>"><spring:message code="tests" /></a></li>
        <li class="breadcrumb-item active"><c:out value="${muestra.casa}" /></li>
        
      </ol>
	  <!-- Container -->
      <div class="container-fluid">
      	<div class="animated fadeIn">
			
			
			
          	<div class="row">
	            <div class="col-md-6">
	              <div class="card">
		               	<div class="card-header">
		                  <i class="icon-compass"></i>&nbsp;<strong><c:out value="${muestra.casa}" /></strong>
		                </div>
	                	<div class="card-body">
	                		<div class="form-group row">
		                      <label class="col-md-3 col-form-label"><spring:message code="ident" />:</label>
		                      <div class="col-md-9">
		                        <p class="form-control-static"><strong><c:out value="${muestra.ident}" /></strong></p>
		                      </div>
		                    </div>
		                    <div class="form-group row">
		                      <label class="col-md-3 col-form-label"><spring:message code="housecase" />:</label>
		                      <div class="col-md-9">
		                        <p class="form-control-static"><strong><c:out value="${muestra.casa}" /></strong></p>
		                      </div>
		                    </div>
		                    <fmt:formatDate value="${muestra.mxDate}" var="fecMuestra" pattern="dd-MMM-yyyy" />
		                    <div class="form-group row">
		                      <label class="col-md-3 col-form-label"><spring:message code="mxDate" />:</label>
		                      <div class="col-md-9">
		                        <p class="form-control-static"><strong><c:out value="${fecMuestra}" /></strong></p>
		                      </div>
		                    </div>
		                    <div class="form-group row">
		                      <label class="col-md-3 col-form-label"><spring:message code="mxProactiva" />:</label>
		                      <div class="col-md-9">
		                        <p class="form-control-static"><strong><c:out value="${muestra.mxProactiva}" /></strong></p>
		                      </div>
		                    </div>
		                    <div class="form-group row">
		                      <label class="col-md-3 col-form-label"><spring:message code="mxReactiva" />:</label>
		                      <div class="col-md-9">
		                        <p class="form-control-static"><strong><c:out value="${muestra.mxReactiva}" /></strong></p>
		                      </div>
		                    </div>
		                    <div class="form-group row">
		                      <label class="col-md-3 col-form-label"><spring:message code="locality" />:</label>
		                      <div class="col-md-9">
		                        <p class="form-control-static"><strong><c:out value="${muestra.local.name}" /></strong></p>
		                      </div>
		                    </div>
		                    <div class="form-group row">
		                      <label class="col-md-3 col-form-label"><spring:message code="district" />:</label>
		                      <div class="col-md-9">
		                        <p class="form-control-static"><strong><c:out value="${muestra.local.district.name}" /></strong></p>
		                      </div>
		                    </div>
		                    <div class="form-group row">
		                      <label class="col-md-3 col-form-label"><spring:message code="area" />:</label>
		                      <div class="col-md-9">
		                        <p class="form-control-static"><strong><c:out value="${muestra.local.district.area.name}" /></strong></p>
		                      </div>
		                    </div>
		                    
		                    
		                    <div class="form-group row">
		                      <label class="col-md-3 col-form-label"><spring:message code="enabled" />:</label>
		                      <div class="col-md-9">
		                        <p class="form-control-static"><strong>
		                        	<c:choose>
										<c:when test="${muestra.pasive=='0'.charAt(0)}">
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
		                        <p class="form-control-static"><strong><c:out value="${muestra.recordUser}" /></strong></p>
		                      </div>
		                    </div>
		                    <div class="form-group row">
		                      <label class="col-md-3 col-form-label"><spring:message code="dateCreated" />:</label>
		                      <div class="col-md-9">
		                        <p class="form-control-static"><strong><c:out value="${muestra.recordDate}" /></strong></p>
		                      </div>
		                    </div>
	                    	<spring:url value="/admin/tests/editEntity/{ident}/" var="editUrl">
                              	<spring:param name="ident" value="${muestra.ident}" />
                          	</spring:url>
            					<spring:url value="/admin/tests/disableEntity/{ident}/" var="disableUrl">
                              	<spring:param name="ident" value="${muestra.ident}" />
                          	</spring:url>
                          	<spring:url value="/admin/tests/enableEntity/{ident}/" var="enableUrl">
                              	<spring:param name="ident" value="${muestra.ident}" />
                          	</spring:url>
                          	<spring:url value="/admin/tests/enterLocation/{ident}/" var="locationUrl">
                              	<spring:param name="ident" value="${muestra.ident}" />
                          	</spring:url>
						</div>
						<div class="card-header">
          				<div class="row float-right mr-4" >
          					<button id="edit_entity" onclick="location.href='${fn:escapeXml(editUrl)}'" type="button" class="btn btn-outline-primary"><i class="fa fa-pencil"></i>&nbsp; <spring:message code="edit" /></button>
          					<c:choose>
								<c:when test="${muestra.pasive=='0'.charAt(0)}">
									<button id="disable_entity" onclick="location.href='${fn:escapeXml(disableUrl)}'" type="button" class="btn btn-outline-danger"><i class="fa fa-close"></i>&nbsp; <spring:message code="disable" /></button>
								</c:when>
								<c:otherwise>
									<button id="enable_entity" onclick="location.href='${fn:escapeXml(enableUrl)}'" type="button" class="btn btn-outline-primary"><i class="fa fa-check"></i>&nbsp; <spring:message code="enable" /></button>
								</c:otherwise>
						 	</c:choose>
						 	<!-- button id="location_button" onclick="location.href='${fn:escapeXml(locationUrl)}'" type="button" class="btn btn-outline-primary"><i class="fa fa-location-arrow"></i>&nbsp; <spring:message code="location" /></button-->
          					<button id="back_button" onclick="location.href='<spring:url value="/admin/tests/" htmlEscape="true "/>'" type="button" class="btn btn-outline-primary"><i class="fa fa-undo"></i>&nbsp; <spring:message code="back" /></button>
          				</div>
            			</div>
					</div>
				</div>
				<div class="col-md-6">
					<div class="card">
						<div class="card-header">
							<strong><spring:message code="detectionLocation" /></strong>
						</div>
						<div class="card-body">
							<div id="mapid" style="width: 100%; height: 500px;"></div>
						</div>
					</div>
				</div>
	            <!--/.col-->
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
      
      
      <!-- Modal -->
  	  <div class="modal fade" id="basic" tabindex="-1" data-role="basic" data-backdrop="static" data-aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<div id="titulo"></div>
				</div>
				<div class="modal-body">
					<input type="hidden" id="accionUrl"/>
					<div id="cuerpo">
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="cancel" /></button>
					<button type="button" class="btn btn-info" onclick="ejecutarAccion()"><spring:message code="ok" /></button>
				</div>
			</div>
			<!-- /.modal-content -->
	    </div>
	  <!-- /.modal-dialog -->
	  </div>
	  
	  
	  <!-- Modal -->
  	  <div class="modal fade" id="datesForm" data-role=""datesForm"" data-backdrop="static" data-aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<div id="titulo"><h2 class="modal-title"><spring:message code="confirm" /></h2></div>
				</div>
				<div class="modal-body">
					<input type="hidden" id="accionUrl"/>
					<div id="encabezado"></div>
					<div id="elemento"></div>
					<div id="cuerpo">
						<form action="#" role="form" autocomplete="off" id="add-form">
						<div class="form-group">
							<input type="text" id="ident" name="ident" readonly hidden value="${muestra.ident}" class="form-control" placeholder="<spring:message code="ident" />">
	                      	<input type="text" id="dataElement" name="dataElement" readonly hidden class="form-control">
	                    </div>
	                    <div class="form-group dias">
		                	<i class="fa fa-map-o"></i>
		                    <label><spring:message code="diaTx" /></label>
		                    <select id="diaTx" name="diaTx" class="form-control select2-single">
		                      <option value=""><spring:message code="empty" /></option>	
		                      <c:forEach items="${diasTx}" var="diaTx">
		                      	<option value="${diaTx.catKey}"><spring:message code="${diaTx.messageKey}" /></option>
		                      </c:forEach>
		                    </select>
		                </div>
	                    <div class="form-group fecha">
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
	                        <input type="text" id="dateValue" name="dateValue"  class="form-control date-picker" data-date-format="dd/mm/yyyy" data-date-start-date="-180d" data-date-end-date="+0d">
	                      </div>
	                    </div>
		                <div class="form-group resultado">
		                	<i class="fa fa-map-o"></i>
		                    <label><spring:message code="sxResult" /></label>
		                    <select id="resultado" name="resultado" class="form-control select2-single">
		                      <option value=""><spring:message code="empty" /></option>	
		                      <c:forEach items="${resultados}" var="resultado">
		                      	<option value="${resultado.catKey}"><spring:message code="${resultado.messageKey}" /></option>
		                      </c:forEach>
		                    </select>
		                </div>
		                <div class="form-group razon">
		                	<i class="fa fa-map-o"></i>
		                    <label><spring:message code="lostFollowUpReason" /></label>
		                    <select id="lostFollowUpReason" name="lostFollowUpReason" class="form-control select2-single">
		                      <option value=""><spring:message code="empty" /></option>	
		                      <c:forEach items="${razones}" var="razon">
		                      	<option value="${razon.catKey}"><spring:message code="${razon.messageKey}" /></option>
		                      </c:forEach>
		                    </select>
		                </div>
	                    <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="cancel" /></button>
						<button type="submit" id="buttonAgregarData" class="btn btn-info"><spring:message code="ok" /></button>
	                    </form>
					</div>
				</div>
				<div class="modal-footer">
				</div>
			</div>
			<!-- /.modal-content -->
	    </div>
	  </div>
	  <!-- /.modal-dialog -->
	  
	  
	  
	  
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
    <spring:url value="/resources/vendors/js/select2.min.js" var="Select2" />
  <script src="${Select2}" type="text/javascript"></script>
  
  <spring:url value="/resources/vendors/js/bootstrap-datepicker.js" var="datepicker" />
  <script src="${datepicker}" type="text/javascript"></script>
  
    <!-- Custom scripts required by this view -->
  <spring:url value="/resources/js/views/Entidad.js" var="processEntity" />
  <script src="${processEntity}"></script>
  
  <c:set var="entityEnabledLabel"><spring:message code="enabled" /></c:set>
  <c:set var="entityDisabledLabel"><spring:message code="disabled" /></c:set>
  <c:set var="exito"><spring:message code="process.success" /></c:set>
  <c:set var="confirmar"><spring:message code="confirm" /></c:set>
  <spring:url value="/admin/tests/dateValue/saveEntity/" var="saveUrl"></spring:url>
  <spring:url value="/admin/tests/" var="listUrl"/>	
  

      
  <c:set var="successmessage"><spring:message code="process.success" /></c:set>
  <c:set var="errormessage"><spring:message code="process.errors" /></c:set>
  <c:set var="waitmessage"><spring:message code="process.wait" /></c:set>
  <c:set var="detectionLocation"><spring:message code="detectionLocation" /></c:set>
  <c:set var="originLocation"><spring:message code="originLocation" /></c:set>
  
  <spring:url value="/resources/img/icons-maps/marker-icon-red.png" var="iconRed" />
  <spring:url value="/resources/img/icons-maps/marker-shadow.png" var="iconShadow" />
	  
  <script>
  	jQuery(document).ready(function() {
		var parametros = {saveUrl: "${saveUrl}", successmessage: "${successmessage}",
				errormessage: "${errormessage}",waitmessage: "${waitmessage}",
				listUrl: "${listUrl}", latitudMinima: "${latitudMinima}", latitudMaxima: "${latitudMaxima}"  
					, longitudMinima: "${longitudMinima}", longitudMaxima: "${longitudMaxima}" 
		};
		ProcessEntity.init(parametros);
	});
  
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
	
	
	if ("${completo}"){
		toastr.info("${exito}", {
		    closeButton: true,
		    progressBar: true,
		  } );
	}
	
	function ejecutarAccion() {
		window.location.href = $('#accionUrl').val();		
	}
	
	var latDef = "${latitudDef}";
	var lonDef = "${longitudDef}";
	var zoomDef = "${zoomDef}";
	var mymap = L.map('mapid').setView([latDef, lonDef], zoomDef);

	L.tileLayer('http://a.tile.openstreetmap.fr/hot/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw', {
		maxZoom: 18,
		attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors, ' +
			'<a href="https://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, ' +
			'Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
		id: 'mapbox.streets'
	}).addTo(mymap);
	
	var miLat = "${muestra.latitude}";
	var miLong = "${muestra.longitude}";
	
	
	
	if(!(miLat == "" || miLong == "")){
		mymap.setView([miLat, miLong], zoomDef);
		var markerDet = L.marker([miLat, miLong]).addTo(mymap);
		markerDet.bindTooltip("${detectionLocation}" + " ${muestra.casa}");		
	}
	
	

  </script>
</body>
</html>