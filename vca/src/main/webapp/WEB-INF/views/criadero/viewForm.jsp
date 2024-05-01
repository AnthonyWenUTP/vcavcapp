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
<spring:url value="/resources/vendors/css/Control.FullScreen.css" var="ControlFullScreenCSS" />
<link href="${ControlFullScreenCSS}" rel="stylesheet" type="text/css"/>
<spring:url value="/resources/vendors/css/leaflet.draw.css" var="leafletDrawCSS" />
<link href="${leafletDrawCSS}" rel="stylesheet" type="text/css"/>

<style>

/*Legend specific*/
.legend {
  padding: 6px 8px;
  font: 11px Arial, Helvetica, sans-serif;
  background: white;
  background: rgba(255, 255, 255, 0.8);
  /*box-shadow: 0 0 15px rgba(0, 0, 0, 0.2);*/
  /*border-radius: 5px;*/
  line-height: 24px;
  color: #555;
}
.legend h4 {
  text-align: center;
  font-size: 12px;
  margin: 2px 12px 8px;
  color: #777;
}

.legend span {
  position: relative;
  bottom: 3px;
}

.legend i {
  width: 16px;
  height: 16px;
  float: left;
  margin: 0 8px 0 0;
  opacity: 0.7;
}

.legend i.icon {
  background-size: 16px;
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
        <li class="breadcrumb-item"><a href="<spring:url value="/admin/criaderos/" htmlEscape="true "/>"><spring:message code="criaderos" /></a></li>
        <li class="breadcrumb-item active"><c:out value="${criadero.info}" /></li>
        
      </ol>
	  <!-- Container -->
      <div class="container-fluid">
      	<div class="animated fadeIn">

          	<div class="row">
	            <div class="col-md-6">
	              <div class="card">
		               	<div class="card-header">
		                  <i class="icon-compass"></i>&nbsp;<strong><c:out value="${criadero.info}" /></strong>
		                </div>
	                	<div class="card-body">
	                		<div class="form-group row">
		                      <label class="col-md-3 col-form-label"><spring:message code="ident" />:</label>
		                      <div class="col-md-9">
		                        <p class="form-control-static"><strong><c:out value="${criadero.ident}" /></strong></p>
		                      </div>
		                    </div>
		                    <div class="form-group row">
		                      <label class="col-md-3 col-form-label"><spring:message code="criadinfo" />:</label>
		                      <div class="col-md-9">
		                        <p class="form-control-static"><strong><c:out value="${criadero.info}" /></strong></p>
		                      </div>
		                    </div>
		                    <div class="form-group row">
		                      <label class="col-md-3 col-form-label"><spring:message code="criadtipo" />:</label>
		                      <div class="col-md-9">
		                        <p class="form-control-static"><strong><c:out value="${criadero.tipo}" /></strong></p>
		                      </div>
		                    </div>
		                    <div class="form-group row">
		                      <label class="col-md-3 col-form-label"><spring:message code="locality" />:</label>
		                      <div class="col-md-9">
		                        <p class="form-control-static"><strong><c:out value="${criadero.local.name}" /></strong></p>
		                      </div>
		                    </div>
		                    <div class="form-group row">
		                      <label class="col-md-3 col-form-label"><spring:message code="district" />:</label>
		                      <div class="col-md-9">
		                        <p class="form-control-static"><strong><c:out value="${criadero.local.district.name}" /></strong></p>
		                      </div>
		                    </div>
		                    <div class="form-group row">
		                      <label class="col-md-3 col-form-label"><spring:message code="area" />:</label>
		                      <div class="col-md-9">
		                        <p class="form-control-static"><strong><c:out value="${criadero.local.district.area.name}" /></strong></p>
		                      </div>
		                    </div>
		                    <div class="form-group row">
		                      <label class="col-md-3 col-form-label"><spring:message code="size" />:</label>
		                      <div class="col-md-9">
		                        <p class="form-control-static"><strong><c:out value="${criadero.size}" /></strong></p>
		                      </div>
		                    </div>
		                    <div class="form-group row">
		                      <label class="col-md-3 col-form-label"><spring:message code="criadespecie" />:</label>
		                      <div class="col-md-9">
		                        <p class="form-control-static"><strong><c:out value="${criadero.especie}" /></strong></p>
		                      </div>
		                    </div>
		                    <div class="form-group row">
		                      <label class="col-md-3 col-form-label"><spring:message code="enabled" />:</label>
		                      <div class="col-md-9">
		                        <p class="form-control-static"><strong>
		                        	<c:choose>
										<c:when test="${criadero.pasive=='0'.charAt(0)}">
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
		                        <p class="form-control-static"><strong><c:out value="${criadero.recordUser}" /></strong></p>
		                      </div>
		                    </div>
		                    <div class="form-group row">
		                      <label class="col-md-3 col-form-label"><spring:message code="dateCreated" />:</label>
		                      <div class="col-md-9">
		                        <p class="form-control-static"><strong><c:out value="${criadero.recordDate}" /></strong></p>
		                      </div>
		                    </div>
	                    	<spring:url value="/admin/criaderos/editEntity/{ident}/" var="editUrl">
                              	<spring:param name="ident" value="${criadero.ident}" />
                          	</spring:url>
            					<spring:url value="/admin/criaderos/disableEntity/{ident}/" var="disableUrl">
                              	<spring:param name="ident" value="${criadero.ident}" />
                          	</spring:url>
                          	<spring:url value="/admin/criaderos/enableEntity/{ident}/" var="enableUrl">
                              	<spring:param name="ident" value="${criadero.ident}" />
                          	</spring:url>
                          	<spring:url value="/admin/criaderos/editEntityPolygon/{ident}/" var="editPolUrl">
                              	<spring:param name="ident" value="${criadero.ident}" />
                          	</spring:url>
						</div>
						<div class="card-header">
          				<div class="row float-right mr-4" >
          					<button id="edit_entity" onclick="location.href='${fn:escapeXml(editUrl)}'" type="button" class="btn btn-outline-primary"><i class="fa fa-pencil"></i>&nbsp; <spring:message code="edit" /></button>
          					<button id="edit_poligon" onclick="location.href='${fn:escapeXml(editPolUrl)}'" type="button" class="btn btn-outline-primary"><i class="fa fa-map"></i>&nbsp; <spring:message code="editpol" /></button>
          					<c:choose>
								<c:when test="${criadero.pasive=='0'.charAt(0)}">
									<button id="disable_entity" onclick="location.href='${fn:escapeXml(disableUrl)}'" type="button" class="btn btn-outline-danger"><i class="fa fa-close"></i>&nbsp; <spring:message code="disable" /></button>
								</c:when>
								<c:otherwise>
									<button id="enable_entity" onclick="location.href='${fn:escapeXml(enableUrl)}'" type="button" class="btn btn-outline-primary"><i class="fa fa-check"></i>&nbsp; <spring:message code="enable" /></button>
								</c:otherwise>
						 	</c:choose>
          					<button id="back_button" onclick="location.href='<spring:url value="/admin/criaderos/" htmlEscape="true "/>'" type="button" class="btn btn-outline-primary"><i class="fa fa-undo"></i>&nbsp; <spring:message code="back" /></button>
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
	                  <i class="icon-map"></i>&nbsp;<strong><spring:message code="txs" /></strong>
	                </div>
	                <div class="card-body">
	                	<table id="lista_visitas" class="table table-striped table-bordered datatable" width="100%">
			                <thead>
			                	<tr>
									<th><spring:message code="visitDate" /></th>
									<th><spring:message code="txtipo" /></th>
									<th><spring:message code="obs" /></th>
									<th><spring:message code="createdBy" /></th>
	                    			<th><spring:message code="dateCreated" /></th>
	                    			<th></th>
			                	</tr>
			                </thead>
			                <tbody>
							<c:forEach items="${visitas}" var="visita">
								<fmt:formatDate value="${visita.txDate}" var="fecvisita" pattern="yyyy-MM-dd" />
								<spring:url value="/admin/criaderos/visits/disableEntity/{id}/"
                                        var="delUrl">
                                	<spring:param name="id" value="${visita.ident}" />
	                            </spring:url>
	                            <spring:url value="/admin/criaderos/visits/editEntity/{id}/"
	                                        var="editUrl">
	                                <spring:param name="id" value="${visita.ident}" />
	                            </spring:url>
								<tr>
									<td><c:out value="${fecvisita}" /></td>
									<td><c:out value="${visita.txType}" /></td>
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
         					<spring:url value="/admin/criaderos/visits/newEntity/{id}/"
                                        var="addVisittUrl">
                                <spring:param name="id" value="${criadero.ident}" />
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
    <spring:url value="/resources/vendors/js/Control.FullScreen.js" var="ControlFullScreenJS" />
  <script src="${ControlFullScreenJS}" type="text/javascript"></script>
    <spring:url value="/resources/vendors/js/Leaflet.draw.js" var="leafletDrawJS" />
  <script src="${leafletDrawJS}" type="text/javascript"></script>    
  
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
	  
	});
    
    $('.datatable').attr('style', 'border-collapse: collapse !important');
    
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
	
	//Presenta el mapa
	var latDef = "${latitudDef}";
	var lonDef = "${longitudDef}";
	var zoomDef = "${zoomDef}";
	var mymap = L.map('mapid', {
	    center: [latDef, lonDef],
	    zoom: zoomDef,
	    fullscreenControl: true,
	    fullscreenControlOptions: {
	      position: 'topleft'
	    }
	});
	var coordinates = [];
	var puntos = [];
	
	L.tileLayer('http://a.tile.openstreetmap.fr/hot/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw', {
		maxZoom: 18,
		attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors, ' +
			'<a href="https://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, ' +
			'Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
		id: 'mapbox.streets'
	}).addTo(mymap);
	


	
	
		<c:forEach var="punto" items="${puntos}">
			var miLat = "${punto.latitude}";
			var miLong = "${punto.longitude}";
			if(!(miLat == "" || miLong == "")){
				var punto = [];
				punto.push(miLong);
				punto.push(miLat);
				puntos.push(punto);
			}
		</c:forEach>
		coordinates.push(puntos);
		
		var criadero = [{
		    "type": "Feature",
		    "properties": {"name": "${criadero.info}",
		    	"popupContent": "${criadero.info}"},
		    "geometry": {
		        "type": "Polygon",
		        "coordinates": coordinates
		    }
		}];
	
	
	  var criaderoLayer = L.geoJSON(criadero, {
		    style: function(feature) {
		        switch (feature.properties.info) {
		            case "${criadero.info}": return {color: "#0000FF"};
		        }
		    }
		});
	  
	  criaderoLayer.addTo(mymap);
	  
	  mymap.fitBounds(criaderoLayer.getBounds());
	  
	  /*Legend specific criadero*/
	  var legend = L.control({ position: "bottomleft" });
	
	  legend.onAdd = function(map) {
		  var div = L.DomUtil.create("div", "legend");
		  div.innerHTML += '<i style="width:10px;height:10px;border:2px solid #0000FF;"></i><span>'+"${criadero.info}"+'</span><br>';
		  return div;
	  };
	  
	  legend.addTo(mymap);

  </script>
</body>
</html>