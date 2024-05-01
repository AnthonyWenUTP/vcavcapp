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
	  <spring:url value="/admin/casos/saveEntity/" var="saveUrl"></spring:url>
  	  <spring:url value="/admin/casos/" var="listUrl"/>	
  	  <spring:url value="/admin/casos/{id}/"
                  var="cancelUrl">
          <spring:param name="id" value="${caso.ident}" />
      </spring:url>
      <!-- Breadcrumb -->
      <ol class="breadcrumb">
        <li class="breadcrumb-item"><a href="<spring:url value="/" htmlEscape="true "/>"><spring:message code="home" /></a></li>
        <li class="breadcrumb-item"><a href="<spring:url value="/admin/casos/" htmlEscape="true "/>"><spring:message code="cases" /></a></li>
        <li class="breadcrumb-item active"><c:out value="${caso.codigo}" /></li>
        
      </ol>
	  <!-- Container -->
      <div class="container-fluid">

        <div class="animated fadeIn">
          <div class="row">
            <div class="col-md-12">
              <div class="card">
                <div class="card-header">
                  <i class="icon-note"></i> <spring:message code="edit" /> <spring:message code="cases" />
                  <div class="card-actions">
                    
                  </div>
                </div>
                <div class="card-body">

                  <div class="row">

                    <div class="col-md-10">
                      <form action="#" autocomplete="off" id="add-form">                      
						<div class="form-group">
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-key"></i></span>
	                        <input type="text" id="ident" name="ident" readonly value="${caso.ident}" class="form-control" placeholder="<spring:message code="ident" />">
	                      </div>
	                    </div>	
	                    <div class="form-group">
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-id-card"></i></span>
	                        <input type="text" id="codigo" name="codigo" value="${caso.codigo}" class="form-control" placeholder="<spring:message code="casecode" />">
	                      </div>
	                    </div>  
	                    
	                    
	                    <div class="form-group">
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="icon-layers"></i></span>
	                        <select name="local" id="local" class="form-control select2-single">
	                        	<option value=""><spring:message code="locality" /> - <spring:message code="empty" /></option>
	                        	<c:forEach items="${localidades}" var="localidad">
									<c:choose> 
										<c:when test="${localidad.ident eq caso.local.ident}">
											<option selected value="${localidad.ident}"><spring:message code="${localidad.name}" /></option>
										</c:when>
										<c:otherwise>
											<option value="${localidad.ident}"><spring:message code="${localidad.name}" /></option>
										</c:otherwise>
									</c:choose> 
								</c:forEach>
	                        </select>
	                      </div>
	                    </div>
	                    <div class="form-group">
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="icon-eye"></i></span>
	                        <select name="sint" id="sint" class="form-control select2-single">
	                        	<option value=""><spring:message code="sint" /> - <spring:message code="empty" /></option>
	                        	<c:forEach items="${siNo}" var="sn">
									<c:choose> 
										<c:when test="${sn.catKey eq caso.sint}">
											<option selected value="${sn.catKey}"><spring:message code="${sn.messageKey}" /></option>
										</c:when>
										<c:otherwise>
											<option value="${sn.catKey}"><spring:message code="${sn.messageKey}" /></option>
										</c:otherwise>
									</c:choose> 
								</c:forEach>
	                        </select>
	                      </div>
	                    </div>
	                    
	                    <fmt:formatDate value="${caso.fisDate}" var="fecSintomas" pattern="yyyy-MM-dd" />
	                    <div class="form-group">
	                      <label><spring:message code="fisDate" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
	                        <input type="text" id="fisDate" name="fisDate" value="${fecSintomas}" class="form-control date-picker" data-date-format="yyyy-mm-dd" data-date-start-date="-180d" data-date-end-date="+0d">
	                      </div>
	                    </div> 
	                    
	                    <fmt:formatDate value="${caso.mxDate}" var="fecMuestra" pattern="yyyy-MM-dd" />
	                    <div class="form-group">
	                      <label><spring:message code="mxDate" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
	                        <input type="text" id="mxDate" name="mxDate" value="${fecMuestra}" class="form-control date-picker" data-date-format="yyyy-mm-dd" data-date-start-date="-180d" data-date-end-date="+0d">
	                      </div>
	                    </div>
	                    <div class="form-group">
	                      <label><spring:message code="mxType" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="icon-eye"></i></span>
	                        <select name="mxType" id="mxType" class="form-control select2-multiple" multiple="">
	                        	<c:forEach items="${tiposPrueba}" var="tipo">
									<c:choose> 
										<c:when test="${fn:contains(caso.mxType, tipo.catKey)}">
											<option selected value="${tipo.catKey}"><spring:message code="${tipo.messageKey}" /></option>
										</c:when>
										<c:otherwise>
											<option value="${tipo.catKey}"><spring:message code="${tipo.messageKey}" /></option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
	                        </select>
	                      </div>
	                    </div> 
	                    
	                    <div class="form-group">
	                      <div class="input-group">
	                        <a href="#" class="btn btn-secondary modalmap"><i class="fa fa-location-arrow"></i>&nbsp;<spring:message code="detectionLocation" /></a>
	                      </div>
	                    </div>
	                    
	                    <div class="form-group">
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-location-arrow"></i></span>
	                        <input type="text" id="latitude" name="latitude" value="${caso.latitude}" readonly class="form-control" placeholder="<spring:message code="latitude" />">
	                      </div>
	                    </div>
	                    <div class="form-group">
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-location-arrow"></i></span>
	                        <input type="text" id="longitude" name="longitude" value="${caso.longitude}" readonly class="form-control" placeholder="<spring:message code="longitude" />">
	                      </div>
	                    </div>
	                    <div class="form-group">
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-location-arrow"></i></span>
	                        <input type="text" id="zoom" name="zoom" value="${caso.zoom}" readonly class="form-control" placeholder="<spring:message code="zoom" />">
	                      </div>
	                    </div>
	                    
	                    
	                    
	                    <div class="form-group">
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-id-card"></i></span>
	                        <input type="text" id="cui" name="cui" value="${caso.cui}" class="form-control" placeholder="<spring:message code="cuicase" />">
	                      </div>
	                    </div>
	                    <div class="form-group">
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-id-card"></i></span>
	                        <input type="text" id="codE1" name="codE1" value="${caso.codE1}" class="form-control" placeholder="<spring:message code="codE1" />">
	                      </div>
	                    </div>
	                    <div class="form-group">
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-id-card"></i></span>
	                        <input type="text" id="casa" name="casa" value="${caso.casa}" class="form-control" placeholder="<spring:message code="housecase" />">
	                      </div>
	                    </div>
	                    <div class="form-group">
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-id-card"></i></span>
	                        <input type="text" id="nombre" name="nombre" value="${caso.nombre}" class="form-control" placeholder="<spring:message code="namecase" />">
	                      </div>
	                    </div>
	                    <div class="form-group">
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="icon-eye"></i></span>
	                        <select name="sexo" id="sexo" class="form-control select2-single">
	                        	<option value=""><spring:message code="sex" /> - <spring:message code="empty" /></option>
	                        	<c:forEach items="${sexo}" var="sex">
									<c:choose> 
										<c:when test="${sex.catKey eq caso.sexo}">
											<option selected value="${sex.catKey}"><spring:message code="${sex.messageKey}" /></option>
										</c:when>
										<c:otherwise>
											<option value="${sex.catKey}"><spring:message code="${sex.messageKey}" /></option>
										</c:otherwise>
									</c:choose> 
								</c:forEach>
	                        </select>
	                      </div>
	                    </div>
	                    <div class="form-group">
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-location-arrow"></i></span>
	                        <input type="text" id="edad" name="edad" value="${caso.edad}" class="form-control" placeholder="<spring:message code="edad" />">
	                      </div>
	                    </div>
	                    <div class="form-group" id="divemb">
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="icon-eye"></i></span>
	                        <select name="embarazada" id="embarazada" class="form-control select2-single">
	                        	<option value=""><spring:message code="pregnant" /> - <spring:message code="empty" /></option>
	                        	<c:forEach items="${siNo}" var="sn">
									<c:choose> 
										<c:when test="${sn.catKey eq caso.embarazada}">
											<option selected value="${sn.catKey}"><spring:message code="${sn.messageKey}" /></option>
										</c:when>
										<c:otherwise>
											<option value="${sn.catKey}"><spring:message code="${sn.messageKey}" /></option>
										</c:otherwise>
									</c:choose> 
								</c:forEach>
	                        </select>
	                      </div>
	                    </div>
	                    <div class="form-group" id="div6meses">
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="icon-eye"></i></span>
	                        <select name="menor6meses" id="menor6meses" class="form-control select2-single">
	                        	<option value=""><spring:message code="menor6meses" /> - <spring:message code="empty" /></option>
	                        	<c:forEach items="${siNo}" var="sn">
									<c:choose> 
										<c:when test="${sn.catKey eq caso.menor6meses}">
											<option selected value="${sn.catKey}"><spring:message code="${sn.messageKey}" /></option>
										</c:when>
										<c:otherwise>
											<option value="${sn.catKey}"><spring:message code="${sn.messageKey}" /></option>
										</c:otherwise>
									</c:choose> 
								</c:forEach>
	                        </select>
	                      </div>
	                    </div>
	                    <div class="form-group">
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-sticky-note-o"></i></span>
	                        <input type="text" id="info" name="info" value="${caso.info}" class="form-control" placeholder="<spring:message code="infocase" />">
	                      </div>
	                    </div>
	                    
	                    <div class="form-group">
	                      <div class="input-group">
	                        <a href="#" class="btn btn-secondary modalmap2"><i class="fa fa-location-arrow"></i>&nbsp;<spring:message code="originLocation" /></a>
	                      </div>
	                    </div>
	                    <div class="form-group">
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-location-arrow"></i></span>
	                        <input type="text" id="latitudeOrigin" name="latitudeOrigin" value="${caso.latitudeOrigin}" readonly class="form-control" placeholder="<spring:message code="latitude" />">
	                      </div>
	                    </div>
	                    <div class="form-group">
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-location-arrow"></i></span>
	                        <input type="text" id="longitudeOrigin" name="longitudeOrigin" value="${caso.longitudeOrigin}" readonly class="form-control" placeholder="<spring:message code="longitude" />">
	                      </div>
	                    </div>
	                    <div class="form-group">
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-location-arrow"></i></span>
	                        <input type="text" id="zoomOrigin" name="zoomOrigin" value="${caso.zoomOrigin}" readonly class="form-control" placeholder="<spring:message code="zoom" />">
	                      </div>
	                    </div>
	                    
	                    
	                    
	                    
                        <div class="form-group">
                          
                          <button type="submit" class="btn btn-primary" id="guardar"><i class="fa fa-save"></i>&nbsp;<spring:message code="save" /></button>
                          <c:choose>
							<c:when test="${empty caso.ident}">
								<a href="${fn:escapeXml(listUrl)}" class="btn btn-danger"><i class="fa fa-undo"></i>&nbsp;<spring:message code="cancel" /></a>
							</c:when>
							<c:otherwise>
								<a href="${fn:escapeXml(cancelUrl)}" class="btn btn-danger"><i class="fa fa-undo"></i>&nbsp;<spring:message code="cancel" /></a>
							</c:otherwise>
						  </c:choose>
						  
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
      
      
      <!-- Modal -->
  	  <div class="modal fade" id="basic" tabindex="-1" data-role="basic" data-backdrop="static" data-aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<div id="titulo"></div>
				</div>
				<div class="modal-body">
					<div id="mapid" style="width: 100%; height: 500px;"></div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" data-dismiss="modal"><spring:message code="ok" /></button>
				</div>
			</div>
			<!-- /.modal-content -->
	    </div>
	  <!-- /.modal-dialog -->
	  </div>
	  
	  
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
  
  <spring:url value="/resources/vendors/js/bootstrap-datepicker.js" var="datepicker" />
  <script src="${datepicker}" type="text/javascript"></script>
  
  <spring:url value="/resources/vendors/js/select2.min.js" var="Select2" />
  <script src="${Select2}" type="text/javascript"></script>
  
  <spring:url value="/resources/vendors/js/leaflet.js" var="leafletJS" />
  <script src="${leafletJS}" type="text/javascript"></script>

  <!-- Custom scripts required by this view -->
  <spring:url value="/resources/js/views/Entidad.js" var="processEntity" />
  <script src="${processEntity}"></script>
  
<c:set var="successmessage"><spring:message code="process.success" /></c:set>
<c:set var="errormessage"><spring:message code="process.errors" /></c:set>
<c:set var="waitmessage"><spring:message code="process.wait" /></c:set>
<c:set var="detectionLocation"><spring:message code="detectionLocation" /></c:set>
<c:set var="originLocation"><spring:message code="originLocation" /></c:set>

<script>
	jQuery(document).ready(function() {
		var parametros = {saveUrl: "${saveUrl}", successmessage: "${successmessage}",
				errormessage: "${errormessage}",waitmessage: "${waitmessage}",
				listUrl: "${listUrl}", latitudMinima: "${latitudMinima}", latitudMaxima: "${latitudMaxima}"  
					, longitudMinima: "${longitudMinima}", longitudMaxima: "${longitudMaxima}" 
		};
		ProcessEntity.init(parametros);
	});
	
	var ubicacionSeleccionada = 0;
	
	$('#local,#estadocaso,#mxType,#sint,#menor6meses,#embarazada,#sexo').select2({
	    theme: "bootstrap",
	    width: '100%'
	});
	
	$(".modalmap").click(function(){ 
    	$('#titulo').html('<h2 class="modal-title">'+"${detectionLocation}"+'</h2>');
    	ubicacionSeleccionada = 1;
    	$('#basic').modal('show');
    });
	
	$(".modalmap2").click(function(){ 
    	$('#titulo').html('<h2 class="modal-title">'+"${originLocation}"+'</h2>');
    	ubicacionSeleccionada = 2;
    	$('#basic').modal('show');
    });
	
	$('#basic').on('show.bs.modal', function(){
		setTimeout(function(){ mymap.invalidateSize()}, 400);	  
		 });
	
	
	if ($('#sint').val()=="1") {
        $('#fisDate').show();
    }
    else {
        $('#fisDate').hide();
    }
	
	if ($('#sexo').val()=="F" && parseInt($("#edad").val()) > 12 && parseInt($("#edad").val()) <50) {
        $('#divemb').show();
    }
    else {
    	$('#embarazada').val('').trigger('change');
        $('#divemb').hide();
    }
	
    if (parseInt($("#edad").val()) <1) {
    	$('#div6meses').show();
    }
    else{
    	$("#menor6meses").val('').trigger('change');
    	
        $('#div6meses').hide();
    }
	
	var latDef = "${latitudDef}";
	var lonDef = "${longitudDef}";
	var zoomDef = "${zoomDef}";
	var mymap = L.map('mapid').setView([latDef, lonDef], zoomDef);
	
	var miLat = "${caso.latitude}";
	var miLong = "${caso.longitude}";
	var zoom = "${caso.zoom}";
	
	if(!(miLat == "" || miLong == "")){
		mymap.setView([miLat, miLong], zoom);
	}
	var theMarker = {};
	L.tileLayer('http://a.tile.openstreetmap.fr/hot/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw', {
		maxZoom: 18,
		attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors, ' +
			'<a href="https://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, ' +
			'Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
		id: 'mapbox.streets'
	}).addTo(mymap);
	
	
	if(!(miLat == "" || miLong == "")){
		theMarker = L.marker([miLat, miLong],{title: "${caso.codigo}", draggable: true}).addTo(mymap).on('dragend', function() {
			$("#latitude").val(theMarker.getLatLng().lat);
			$("#longitude").val(theMarker.getLatLng().lng);
		});
	}
	
	mymap.on('click', onMapClick);
	
	function onMapClick(e) {
		lat = e.latlng.lat;
	    lon = e.latlng.lng;
	    if (ubicacionSeleccionada==1) {
	    	$("#latitude").val(lat);
			$("#longitude").val(lon);
			$("#zoom").val(mymap.getZoom());
	    }
	    else if (ubicacionSeleccionada==2) {
	    	$("#latitudeOrigin").val(lat);
			$("#longitudeOrigin").val(lon);
			$("#zoomOrigin").val(mymap.getZoom());
	    }

	    if (theMarker != undefined) {
	       	mymap.removeLayer(theMarker);
	    };

	    theMarker = L.marker([lat, lon],{title: "${caso.codigo}", draggable: true}).addTo(mymap).on('dragend', function() {
	    	if (ubicacionSeleccionada==1) {
	    		$("#latitude").val(theMarker.getLatLng().lat);
				$("#longitude").val(theMarker.getLatLng().lng);
	    	}
	    	else if (ubicacionSeleccionada==2) {
	    		$("#latitudeOrigin").val(theMarker.getLatLng().lat);
				$("#longitudeOrigin").val(theMarker.getLatLng().lng);
	    	}
		});
	}
	
	mymap.on('zoomend',function(e){
		if (ubicacionSeleccionada==1) {
			$("#zoom").val(mymap.getZoom());
		}
		else if (ubicacionSeleccionada==2) {
			$("#zoomOrigin").val(mymap.getZoom());
		}
	});
	
	
</script>
  
</body>
</html>