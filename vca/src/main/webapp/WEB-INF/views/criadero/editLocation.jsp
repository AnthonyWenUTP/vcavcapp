<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
         		<div class="col-md-12">
					<div class="card">
						<div class="card-header">
							<strong><spring:message code="location" /></strong> <i class="icon-graph"></i>&nbsp;<strong><c:out value="${criadero.info}" /></strong>
						</div>
						<div class="card-body">
							<div id="mapid" style="width: 100%; height: 600px;"></div>
						</div>
						<spring:url value="/admin/criaderos/{ident}/" var="criaderoUrl">
                        	<spring:param name="ident" value="${criadero.ident}" />
                         </spring:url>
						<div class="card-header">
	          				<div class="row float-right mr-4" >
	          					<button type="submit" class="btn btn-primary" onclick="processEntidad()" id="guardar"><i class="fa fa-save"></i>&nbsp;<spring:message code="save" /></button>
						  		<a href="${fn:escapeXml(criaderoUrl)}" class="btn btn-danger"><i class="fa fa-undo"></i>&nbsp;<spring:message code="cancel" /></a>
	          				</div>
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
  
  <spring:url value="/admin/criaderos/saveEntityPolygon/" var="saveUrl"></spring:url>
  <c:set var="successmessage"><spring:message code="process.success" /></c:set>
	<c:set var="errormessage"><spring:message code="process.errors" /></c:set>
	<c:set var="waitmessage"><spring:message code="process.wait" /></c:set>
  
  <script>
  
	
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
	
	$('#guardar').prop('disabled',true);
	
	L.tileLayer('http://a.tile.openstreetmap.fr/hot/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw', {
		maxZoom: 18,
		attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors, ' +
			'<a href="https://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, ' +
			'Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
		id: 'mapbox.streets'
	}).addTo(mymap);
	
	
	
	
	// Initialise the FeatureGroup to store editable layers
	var editableLayers = new L.FeatureGroup();
	
	
	var drawPluginOptions = {
		  position: 'topright',
		  draw: {
		    polygon: {
		      allowIntersection: false, // Restricts shapes to simple polygons
		      drawError: {
		        color: '#e1e100', // Color the shape will turn when intersects
		        message: '<strong>Ay caray!<strong> No se puede dibujar esto!' // Message that will show when intersect
		      },
		      shapeOptions: {
		        color: '#97009c'
		      }
		    },
		    // disable toolbar item by setting it to false
		    polyline: false,
		    circle: false, // Turns off this drawing tool	
		    rectangle: false,
		    marker: false,
		    circlemarker: false,
		    },
		  edit: {
		    featureGroup: editableLayers
		  }
		};

		// Initialise the draw control and pass it the FeatureGroup of editable layers
		var drawControl = new L.Control.Draw(drawPluginOptions);
		mymap.addControl(drawControl);
		
		mymap.on(L.Draw.Event.CREATED, function(e) {
			$('#guardar').prop('disabled',false);
			if(mymap.hasLayer(editableLayers)){
			    // map.removeLayer(drawnFeatures); <-- this the wrong way
			    editableLayers.eachLayer(
			        function(l){
			        	editableLayers.removeLayer(l);
			    });
			}	

		  	var type = e.layerType,
		    layer = e.layer;
		    coordinates = layer._latlngs;
		  	editableLayers.addLayer(layer);
		});

		
		mymap.on(L.Draw.Event.EDITED, function(e) {
			  $('#guardar').prop('disabled',false);
			  let layers = e.layers;
			  layers.eachLayer(function(layer) {
				coordinates = layer._latlngs;
			    console.log(layer);
			  });
			});
	
	
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
	
	var foco = [{
	    "type": "Feature",
	    "properties": {"name": "${criadero.info}",
	    	"popupContent": "${criadero.info}"},
	    "geometry": {
	        "type": "Polygon",
	        "coordinates": coordinates
	    }
	}];


  var focoLayer = L.geoJSON(foco, {
	    style: function(feature) {
	        switch (feature.properties.name) {
	            case "${criadero.info}": return {color: "#0000FF"};
	        }
	    }
	});
  
  focoLayer.eachLayer(function (layer) {
	  editableLayers.addLayer(layer);
	});
  //focoLayer.addTo(mymap);
  mymap.addLayer(editableLayers);
  
  mymap.fitBounds(editableLayers.getBounds());
  
  function processEntidad(){
	  $.blockUI({ message: "${waitmessage}" });
	    $.post( "${saveUrl}"
	            , {"ident":"${criadero.ident}", "coordinates":JSON.stringify(coordinates[0])}
	            , function( data )
	            {
	    			entidad = JSON.parse(data);
	    			if (entidad.ident === undefined) {
	    				data = data.replace(/u0027/g,"");
	    				toastr.error(data, "${errormessage}", {
	    					    closeButton: true,
	    					    progressBar: true,
	    					  });
	    				$.unblockUI();
					}
					else{
						$.blockUI({ message: "${successmessage}" });
						setTimeout(function() { 
				           $.unblockUI({ 
				                onUnblock: function(){ window.location.href = "${criaderoUrl}"; } 
				            }); 
				        }, 1000); 
					}
	            }
	            , 'text' )
		  		.fail(function(XMLHttpRequest, textStatus, errorThrown) {
		    		alert( "error:" + errorThrown);
		    		$.unblockUI();
		  		});
	}
  

  </script>
</body>
</html>