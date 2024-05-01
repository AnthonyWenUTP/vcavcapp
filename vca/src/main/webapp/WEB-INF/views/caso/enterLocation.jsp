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
        <li class="breadcrumb-item"><a href="<spring:url value="/admin/casos/" htmlEscape="true "/>"><spring:message code="cases" /></a></li>
        <li class="breadcrumb-item active"><c:out value="${caso.codigo}" /></li>
        
      </ol>
	  <!-- Container -->
      <div class="container-fluid">
      	<div class="animated fadeIn">
		<spring:url value="/admin/casos/saveEntity/" var="saveUrl"></spring:url>
		<spring:url value="/admin/casos/" var="listUrl"/>
		<spring:url value="/admin/casos/{id}/"
                  var="cancelUrl">
          <spring:param name="id" value="${caso.ident}" />
      </spring:url>

          	<div class="row">
          		<div class="col-md-4">
					<div class="card">
						<div class="card-header">
		                  <i class="icon-compass"></i> <spring:message code="edit" /> <spring:message code="location" />
		                  <div class="card-actions">
		                    
		                  </div>
		                </div>
		                <div class="card-body">
		                	<div class="row">
			                    <div class="col-md-12">
			                      <form action="#" autocomplete="off" id="add-form">                      
									<div class="form-group" style = "display:none">
				                      <div class="input-group">
				                        <span class="input-group-addon"><i class="fa fa-key"></i></span>
				                        <input type="text" id="ident" name="ident" readonly value="${caso.ident}" class="form-control" placeholder="<spring:message code="ident" />">
				                      </div>
				                    </div>	
				                    <div class="form-group">
				                      <div class="input-group">
				                        <span class="input-group-addon"><i class="fa fa-id-card"></i></span>
				                        <input type="text" id="codigo" name="codigo" readonly value="${caso.codigo}" class="form-control" placeholder="<spring:message code="codigo" />">
				                      </div>
				                    </div>  
				                    
				                    <fmt:formatDate value="${caso.fisDate}" var="fecSintomas" pattern="yyyy-MM-dd" />
				                    <div class="form-group" style = "display:none">
				                      <label><spring:message code="fisDate" /></label>
				                      <div class="input-group">
				                        <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
				                        <input type="text" id="fisDate" name="fisDate" value="${fecSintomas}" class="form-control date-picker" data-date-format="dd/mm/yyyy" data-date-start-date="-90d" data-date-end-date="+0d">
				                      </div>
				                    </div> 
				                    
				                    <fmt:formatDate value="${caso.mxDate}" var="fecMuestra" pattern="yyyy-MM-dd" />
				                    <div class="form-group" style = "display:none">
				                      <label><spring:message code="mxDate" /></label>
				                      <div class="input-group">
				                        <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
				                        <input type="text" id="mxDate" name="mxDate" value="${fecMuestra}" class="form-control date-picker" data-date-format="dd/mm/yyyy" data-date-start-date="-90d" data-date-end-date="+0d">
				                      </div>
				                    </div> 
				                    
				                    <div class="form-group" style = "display:none">
				                      <div class="input-group">
				                        <span class="input-group-addon"><i class="fa fa-address-book"></i></span>
				                        <input type="text" id="info" name="info" readonly value="${caso.info}" class="form-control" placeholder="<spring:message code="infocase" />">
				                      </div>
				                    </div>
				                    <div class="form-group" style = "display:none">
				                      <div class="input-group">
				                        <span class="input-group-addon"><i class="fa fa-address-book"></i></span>
				                        <input type="text" id="cui" name="cui" readonly value="${caso.cui}" class="form-control" placeholder="<spring:message code="cui" />">
				                      </div>
				                    </div>
				                    <div class="form-group" style = "display:none">
				                      <div class="input-group">
				                        <span class="input-group-addon"><i class="fa fa-address-book"></i></span>
				                        <input type="text" id="codE1" name="codE1" readonly value="${caso.codE1}" class="form-control" placeholder="<spring:message code="codE1" />">
				                      </div>
				                    </div>
				                    <div class="form-group" style = "display:none">
				                      <div class="input-group">
				                        <span class="input-group-addon"><i class="fa fa-address-book"></i></span>
				                        <input type="text" id="casa" name="casa" readonly value="${caso.casa}" class="form-control" placeholder="<spring:message code="casa" />">
				                      </div>
				                    </div>
				                    <div class="form-group" style = "display:none">
				                      <div class="input-group">
				                        <span class="input-group-addon"><i class="fa fa-address-book"></i></span>
				                        <input type="text" id="nombre" name="nombre" readonly value="${caso.nombre}" class="form-control" placeholder="<spring:message code="nombre" />">
				                      </div>
				                    </div>
				                    <div class="form-group" style = "display:none">
				                      <div class="input-group">
				                        <span class="input-group-addon"><i class="fa fa-address-book"></i></span>
				                        <input type="text" id="sint" name="sint" readonly value="${caso.sint}" class="form-control" placeholder="<spring:message code="sint" />">
				                      </div>
				                    </div>
				                    
				                    
				                    <div class="form-group" style = "display:none">
				                      <div class="input-group">
				                        <span class="input-group-addon"><i class="fa fa-address-book"></i></span>
				                        <input type="text" id="mxType" name="mxType" readonly value="${caso.mxType}" class="form-control" placeholder="<spring:message code="mxType" />">
				                      </div>
				                    </div>
				                    <div class="form-group" style = "display:none">
				                      <div class="input-group">
				                        <span class="input-group-addon"><i class="fa fa-address-book"></i></span>
				                        <input type="text" id="local" name="local" readonly value="${caso.local.ident}" class="form-control" placeholder="<spring:message code="local" />">
				                      </div>
				                    </div>
				                    <div class="form-group">
				                      <div class="input-group">
				                        <span class="input-group-addon"><i class="fa fa-location-arrow"></i></span>
				                        <input type="text" id="latitude" name="latitude" readonly value="${caso.latitude}" class="form-control" placeholder="<spring:message code="latitude" />">
				                      </div>
				                    </div>
				                    <div class="form-group">
				                      <div class="input-group">
				                        <span class="input-group-addon"><i class="fa fa-location-arrow"></i></span>
				                        <input type="text" id="longitude" name="longitude" readonly value="${caso.longitude}" class="form-control" placeholder="<spring:message code="longitude" />">
				                      </div>
				                    </div>
				                    <div class="form-group">
				                      <div class="input-group">
				                        <span class="input-group-addon"><i class="fa fa-location-arrow"></i></span>
				                        <input type="text" id="zoom" name="zoom" readonly value="${caso.zoom}" class="form-control" placeholder="<spring:message code="zoom" />">
				                      </div>
				                    </div>
			                        <div class="form-group">
			                          <button type="submit" class="btn btn-primary" id="guardar"><i class="fa fa-save"></i>&nbsp;<spring:message code="save" /></button>
									  <a href="${fn:escapeXml(cancelUrl)}" class="btn btn-danger"><i class="fa fa-undo"></i>&nbsp;<spring:message code="cancel" /></a>
			                        </div>
			                      </form>
			                    </div>
			                  </div>
		                </div>
					</div>
				</div>
				<div class="col-md-8">
					<div class="card">
						<div class="card-header">
							<i class="fa fa-hand-pointer-o"></i> <spring:message code="locselect" />
		                  	<div class="card-actions">
		                    
		                  	</div>
						</div>
						<div class="card-body">
							<div id="mapid" style="width: 100%; height: 500px;"></div>
		                </div>
					</div>
				</div>
	            <!--/.col-->
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
  
  <spring:url value="/resources/vendors/js/leaflet.js" var="leafletJS" />
  <script src="${leafletJS}" type="text/javascript"></script>
  
    <spring:url value="/resources/js/views/Entidad.js" var="processEntity" />
  <script src="${processEntity}"></script>
  
<c:set var="successmessage"><spring:message code="process.success" /></c:set>
<c:set var="errormessage"><spring:message code="process.errors" /></c:set>
<c:set var="waitmessage"><spring:message code="process.wait" /></c:set>
  
  <script>
  
  jQuery(document).ready(function() {
		var parametros = {saveUrl: "${saveUrl}", successmessage: "${successmessage}",
				errormessage: "${errormessage}",waitmessage: "${waitmessage}",
				listUrl: "${listUrl}" , latitudMinima: "${latitudMinima}", latitudMaxima: "${latitudMaxima}"  
					, longitudMinima: "${longitudMinima}", longitudMaxima: "${longitudMaxima}" 
		};
		ProcessEntity.init(parametros);
	});
	
	var mymap = L.map('mapid').setView([${latitude}, ${longitude}], ${zoom});
	
	var theMarker = {};


	L.tileLayer('http://a.tile.openstreetmap.fr/hot/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw', {
		maxZoom: 18,
		attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors, ' +
			'<a href="https://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, ' +
			'Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
		id: 'mapbox.streets'
	}).addTo(mymap);
	
	var miLat = "${caso.latitude}";
	var miLong = "${caso.longitude}";
	
	if(!(miLat == "" || miLong == "")){
		theMarker = L.marker([${caso.latitude}, ${caso.longitude}],{title: "${caso.codigo}", draggable: true}).addTo(mymap).on('dragend', function() {
			$("#latitude").val(theMarker.getLatLng().lat);
			$("#longitude").val(theMarker.getLatLng().lng);
		});
	}

	function onMapClick(e) {
		lat = e.latlng.lat;
	    lon = e.latlng.lng;
	    $("#latitude").val(lat);
		$("#longitude").val(lon);
		$("#zoom").val(mymap.getZoom());

	    if (theMarker != undefined) {
	       	mymap.removeLayer(theMarker);
	    };

	    theMarker = L.marker([lat, lon],{title: "${caso.codigo}", draggable: true}).addTo(mymap).on('dragend', function() {
	    	$("#latitude").val(theMarker.getLatLng().lat);
			$("#longitude").val(theMarker.getLatLng().lng);
		});
	}

	mymap.on('click', onMapClick);
	
	mymap.on('zoomend',function(e){
		$("#zoom").val(mymap.getZoom());
	});

  </script>
</body>
</html>