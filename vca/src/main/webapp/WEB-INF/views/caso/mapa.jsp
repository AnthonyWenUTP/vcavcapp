<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
<jsp:include page="../fragments//headTag.jsp" />
<!-- Styles required by this views -->

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
  <jsp:include page="../fragments//bodyHeader.jsp" />
  <div class="app-body">
  	<!-- Navigation -->
  	<jsp:include page="../fragments//sideBar.jsp" />
    <!-- Main content -->
    <main class="main">
    	<spring:url value="/resources/img/icons-maps/blue.png" var="iconBlue" />
  		<spring:url value="/resources/img/icons-maps/green.png" var="iconGreen" />
  		<spring:url value="/resources/img/icons-maps/red.png" var="iconRed" />

      <!-- Breadcrumb -->
      <ol class="breadcrumb">
        <li class="breadcrumb-item"><a href="<spring:url value="/" htmlEscape="true "/>"><spring:message code="home" /></a></li>
        <li class="breadcrumb-item active"><spring:message code="cases" /></li>
        
      </ol>
	  <!-- Container -->
	  <div class="container-fluid">
      <div class="animated fadeIn">
          <div class="card">
            <div class="card-header">
              <i class="fa fa-list"></i> <spring:message code="cases" />
              <div class="card-actions">
              </div>
            </div>
            <div class="card-body">
              <spring:url value="/admin/casos/newEntity/"	var="newEntity"/>
              <spring:url value="/admin/casos/"	var="viewList"/>	
              <button id="lista_cases_new" onclick="location.href='${fn:escapeXml(newEntity)}'" type="button" class="btn btn-outline-primary"><i class="fa fa-plus"></i>&nbsp; <spring:message code="add" /></button>
              <div class="row float-right mr-4" >
              	<button id="cases_list" onclick="location.href='${fn:escapeXml(viewList)}'" type="button" class="btn btn-outline-primary"><i class="fa fa-list-ul"></i>&nbsp; <spring:message code="list" /></button>
              </div>
              <br><br>
              <div id="mapid" style="width: 100%; height: 600px;"></div>
              
            </div>
          </div>
        </div>
        </div>
      <!-- /.container-fluid -->
    </main>
    
  </div>
  <!-- Pie de página -->
  <jsp:include page="../fragments//bodyFooter.jsp" />

  <!-- Bootstrap and necessary plugins -->
  <jsp:include page="../fragments//corePlugins.jsp" />
  <jsp:include page="../fragments//bodyUtils.jsp" />
  
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
  
  <spring:url value="/resources/vendors/js/leaflet.js" var="leafletJS" />

  <script src="${leafletJS}" type="text/javascript"></script>
  
  <!-- Custom scripts required by this view -->
  
  <script>
  
	  	var mymap = L.map('mapid').setView(["${latitude}", "${longitude}"], "${zoom}");
		
		var theMarker = {};
		var locMarkers = new L.FeatureGroup();
		


		L.tileLayer('http://a.tile.openstreetmap.fr/hot/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw', {
			maxZoom: 18,
			attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors, ' +
				'<a href="https://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, ' +
				'Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
			id: 'mapbox.streets'
		}).addTo(mymap);
		
		
		var PointIcon = L.Icon.extend({
		    options: {
		        iconSize:     [16, 16]
		    }
		});
		
		var blueIcon = new PointIcon({iconUrl: '${iconBlue}'});
		var redIcon = new PointIcon({iconUrl: '${iconRed}'});
		
		<c:forEach var="caso" items="${casos}">
			var miLat = "${caso.latitude}";
			var miLong = "${caso.longitude}";
			
			if(!(miLat == "" || miLong == "")){
				if("${caso.pasive}"=='1'.charAt(0)){
					theMarker = L.marker(["${caso.latitude}", "${caso.longitude}"],{url: "${viewList}"+"/"+"${caso.ident}"+"/"}).addTo(mymap).setIcon(redIcon).on('click', onClick);
				}
				else{
					theMarker = L.marker(["${caso.latitude}", "${caso.longitude}"],{url: "${viewList}"+"/"+"${caso.ident}"+"/"}).addTo(mymap).setIcon(blueIcon).on('click', onClick);
				}
				theMarker.addTo(locMarkers);
				theMarker.bindTooltip("${caso.codigo}");
			}
		</c:forEach>
		
		
		var states = [{
		    "type": "Feature",
		    "properties": {"party": "Republican"},
		    "geometry": {
		        "type": "Polygon",
		        "coordinates": [[
		            [-104.05, 48.99],
		            [-97.22,  48.98],
		            [-96.58,  45.94],
		            [-104.03, 45.94],
		            [-104.05, 48.99]
		        ]]
		    }
		}, {
		    "type": "Feature",
		    "properties": {"party": "Democrat"},
		    "geometry": {
		        "type": "Polygon",
		        "coordinates": [[
		            [-109.05, 41.00],
		            [-102.06, 40.99],
		            [-102.03, 36.99],
		            [-109.04, 36.99],
		            [-109.05, 41.00]
		        ]]
		    }
		}];

		L.geoJSON(states, {
		    style: function(feature) {
		        switch (feature.properties.party) {
		            case 'Republican': return {color: "#ff0000"};
		            case 'Democrat':   return {color: "#0000ff"};
		        }
		    }
		}).addTo(mymap);
		
		
		
		mymap.fitBounds(locMarkers.getBounds());
		var popup = L.popup();
		
		function onClick(e) {
			window.location.href = this.options.url;
		}
		
		/*Legend specific*/
		var legend = L.control({ position: "bottomleft" });

		legend.onAdd = function(map) {
		  var div = L.DomUtil.create("div", "legend");
		  div.innerHTML += "<h4><spring:message code="cases" /></h4>";
		  div.innerHTML += '<i class="icon" style="background-image: url(${iconBlue});background-repeat: no-repeat;"></i><span><spring:message code="enabled" /></span><br>';
		  div.innerHTML += '<i class="icon" style="background-image: url(${iconRed});background-repeat: no-repeat;"></i><span><spring:message code="disabled" /></span><br>';
		  return div;
		};

		legend.addTo(mymap);
		

  </script>
</body>
</html>