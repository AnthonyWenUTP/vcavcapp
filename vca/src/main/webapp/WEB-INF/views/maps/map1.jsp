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
        <li class="breadcrumb-item active"><spring:message code="mapsmod" /></li>
      </ol>
      
	  <!-- Container -->
      <div class="container-fluid">
        <div class="animated fadeIn">
        	<div class="row">
	            <div class="col-6 col-lg-3">
		            <div class="card">
			            <div class="card-body p-3 clearfix">
				            <div class="text-uppercase text-muted font-weight-bold font-xs text-left mb-0 mt-2 tipso" data-tipso="<spring:message code="casostotdesc" />"><spring:message code="casostot" /></div>
				            <div class="h3 text-right"><label id="labelTotCas"></label></div>
				            <div class="text-uppercase text-muted font-weight-bold font-xs text-left mb-0 mt-2 tipso" data-tipso="<spring:message code="casosinvdesc" />"><spring:message code="inv" /></div>
				            <div class="h3 text-right"><label id="labelPorInv"></label></div>
			            </div>
			            <div class="card-footer px-3 py-2">
			            </div>
		            </div>
	            </div>
	            <div class="col-6 col-lg-3">
		            <div class="card">
			            <div class="card-body p-3 clearfix">
				            <div class="text-uppercase text-muted font-weight-bold font-xs text-left mb-0 mt-2 tipso" data-tipso="<spring:message code="casostratdesc" />"><spring:message code="tx" /></div>
				            <div class="h3 text-right"><label id="labelPorTx"></label></div>
				            <div class="text-uppercase text-muted font-weight-bold font-xs text-left mb-0 mt-2 tipso" data-tipso="<spring:message code="casostratcdesc" />"><spring:message code="txComp" /></div>
				            <div class="h3 text-right"><label id="labelPorTxCom"></label></div>
			            </div>
			            <div class="card-footer px-3 py-2">
			            </div>
		            </div>
	            </div>
	            <div class="col-6 col-lg-3">
		            <div class="card">
			            <div class="card-body p-3 clearfix">
			            	<div class="text-uppercase text-muted font-weight-bold font-xs text-left mb-0 mt-2 tipso" data-tipso="<spring:message code="casostratsdesc" />"><spring:message code="txSup" /></div>
				            <div class="h3 text-right"><label id="labelPorTxSup"></label></div>
				            <div class="text-uppercase text-muted font-weight-bold font-xs text-left mb-0 mt-2 tipso" data-tipso="<spring:message code="casossegdesc" />"><spring:message code="sx" /></div>
				            <div class="h3 text-right"><label id="labelPorSx"></label></div>
			            </div>
			            <div class="card-footer px-3 py-2">
			            </div>
		            </div>
	            </div>
	            <div class="col-6 col-lg-3">
		            <div class="card">
			            <div class="card-body p-3 clearfix">
			            	<div class="text-uppercase text-muted font-weight-bold font-xs text-left mb-0 mt-2 tipso" data-tipso="<spring:message code="casossegcdesc" />"><spring:message code="sxComp" /></div>
				            <div class="h3 text-right"><label id="labelPorSxCom"></label></div>
				            <div class="text-uppercase text-muted font-weight-bold font-xs text-left mb-0 mt-2 tipso" data-tipso="<spring:message code="casoslostdesc" />"><spring:message code="lostFollowUp" /></div>
				            <div class="h3 text-right"><label id="labelPorLost"> </label></div>
			            </div>
			            <div class="card-footer px-3 py-2">
			            </div>
		            </div>
	            </div>
            </div>
            
            <div class="row">
	         	<div class="col-12 col-lg-12">
					<div class="card">
						<div id="mapCard" class="card-body">
							<div id="mapid" style="width: 100%; height: 480px;"></div>
						</div>
					</div>
				</div>
			</div>
            
            <div class="row">
            	<div class="col-12 col-lg-12">
	           		<div class="card">
		           		<div class="card-header">
		           			<b><spring:message code="casosday" /></b>
							<ul class="nav nav-tabs float-right" role="tablist">
						        <li class="nav-item">
						          <a class="nav-link active" data-toggle="tab" href="#day-graph" role="tab"><i class="fa fa-bar-chart"></i></a>
						        </li>
						        <li class="nav-item">
						          <a class="nav-link" data-toggle="tab" href="#day-table" role="tab"><i class="fa fa-table"></i></a>
						        </li>
						    </ul>
		           		</div>
		           		<div class="card-body">
		           			<div class="tab-content">
		           				<div class="tab-pane active" id="day-graph" role="tabpanel">
			           				<div class="chart-wrapper daychart">
			                 			<canvas id="dates-chart"  height="300"></canvas>
			             			</div>
			         			</div>
			         			<div class="tab-pane" id="day-table" role="tabpanel">
				           			<div class="chart-wrapper daytable table-responsive">
				                 		<table id="daytable" class="table table-striped table-bordered datatable" width="100%">
							                <thead> 
							                	<tr>
							                		<th><span class="left tipso" title="tipso" data-tipso="<spring:message code="mxdatedesc" />"><spring:message code="mxDate" /></span></th>
							                		<th><span class="left tipso" title="tipso" data-tipso="<spring:message code="casostotdesc" />"><spring:message code="casostot" /></span></th>
							                		<th><spring:message code="inv" /></th>
							                		<th><spring:message code="%" /></th>
							                		<th><spring:message code="tx" /></th>
							                		<th><spring:message code="%" /></th>
							                		<th><spring:message code="txComp" /></th>
							                		<th><spring:message code="%" /></th>
							                		<th><spring:message code="txSup" /></th>
							                		<th><spring:message code="%" /></th>
							                		<th><spring:message code="sx" /></th>
							                		<th><spring:message code="%" /></th>
							                		<th><spring:message code="sxComp" /></th>
							                		<th><spring:message code="%" /></th>
							                		<th><spring:message code="lostFollowUp" /></th>
							                		<th><spring:message code="%" /></th>
							                	</tr>
							                </thead>
							                <tbody>
							                </tbody>
						            	</table>
				             		</div>
				             	</div>
			         		</div>
		         		</div> 
	         		</div> 
         		</div>
         	</div>
         	
         	<div class="row">
            	<div class="col-12 col-lg-12">
	           		<div class="card">
		           		<div class="card-header">
							<ul class="nav nav-tabs float-right" role="tablist">
						        <li class="nav-item">
						          <a class="nav-link active" data-toggle="tab" href="#ou-graph" role="tab"><i class="fa fa-bar-chart"></i></a>
						        </li>
						        <li class="nav-item">
						          <a class="nav-link" data-toggle="tab" href="#ou-table" role="tab"><i class="fa fa-table"></i></a>
						        </li>
						    </ul>
		           		</div>
		           		<div class="card-body">
		           			<div class="tab-content">
		           				<div class="tab-pane active" id="ou-graph" role="tabpanel">
			           				<div class="chart-wrapper ouchart">
			                 			<canvas id="ou-chart" height="300"></canvas>
			             			</div>
			         			</div>
			         			<div class="tab-pane" id="ou-table" role="tabpanel">
				           			<div class="chart-wrapper outable table-responsive">
				                 		<table id="outable" class="table table-striped table-bordered datatable" width="100%">
							                <thead>
							                	<tr>
							                		<th><spring:message code="ou" /></th>
							                		<th><spring:message code="casostot" /></th>
							                		<th><spring:message code="inv" /></th>
								                    <th><spring:message code="%" /></th>
								                    <th><spring:message code="tx" /></th>
							                		<th><spring:message code="%" /></th>
							                		<th><spring:message code="txComp" /></th>
							                		<th><spring:message code="%" /></th>
							                		<th><spring:message code="txSup" /></th>
							                		<th><spring:message code="%" /></th>
							                		<th><spring:message code="sx" /></th>
							                		<th><spring:message code="%" /></th>
							                		<th><spring:message code="sxComp" /></th>
							                		<th><spring:message code="%" /></th>
							                		<th><spring:message code="lostFollowUp" /></th>
							                		<th><spring:message code="%" /></th>
							                	</tr>
							                </thead>
							                <tbody>
							                </tbody>
						            	</table>
				             		</div>
				             	</div>
			         		</div>
		         		</div> 
	         		</div> 
         		</div>
         	</div>
         	
         	<div class="row">
            	<div class="col-12 col-lg-12">
	           		<div class="card">
		           		<div class="card-header">
							<ul class="nav nav-tabs float-right" role="tablist">
						        <li class="nav-item">
						          <a class="nav-link active" data-toggle="tab" href="#est-graph" role="tab"><i class="fa fa-bar-chart"></i></a>
						        </li>
						        <li class="nav-item">
						          <a class="nav-link" data-toggle="tab" href="#est-table" role="tab"><i class="fa fa-table"></i></a>
						        </li>
						    </ul>
		           		</div>
		           		<div class="card-body">
		           			<div class="tab-content">
				             	<div class="tab-pane active" id="est-graph" role="tabpanel">
			           				<div class="chart-wrapper estchart">
			                 			<canvas id="est-chart" height="300"></canvas>
			             			</div>
			         			</div>
			         			<div class="tab-pane" id="est-table" role="tabpanel">
				           			<div class="chart-wrapper outable table-responsive">
				                 		<table id="esttable" class="table table-striped table-bordered datatable" width="100%">
							                <thead>
							                	<tr>
							                		<th><spring:message code="ou" /></th>
							                		<th class="hidden-xs"><spring:message code="casostot" /></th>
							                		<th class="hidden-xs"><spring:message code="CAT_ESTADOCASO_1" /></th>
								                    <th class="hidden-xs" >%</th>
							                		<th class="hidden-xs"><spring:message code="CAT_ESTADOCASO_2" /></th>
								                    <th class="hidden-xs" >%</th>
								                    <th class="hidden-xs"><spring:message code="CAT_ESTADOCASO_3" /></th>
								                    <th class="hidden-xs" >%</th>
								                    <th class="hidden-xs"><spring:message code="CAT_ESTADOCASO_4" /></th>
								                    <th class="hidden-xs" >%</th>
								                    <th class="hidden-xs"><spring:message code="CAT_ESTADOCASO_5" /></th>
								                    <th class="hidden-xs" >%</th>
								                    <th class="hidden-xs"><spring:message code="CAT_ESTADOCASO_6" /></th>
								                    <th class="hidden-xs" >%</th>
								                    <th class="hidden-xs"><spring:message code="CAT_ESTADOCASO_7" /></th>
								                    <th class="hidden-xs" >%</th>
							                	</tr>
							                </thead>
							                <tbody>
							                </tbody>
						            	</table>
				             		</div>
				             	</div>
			         		</div>
		         		</div> 
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
	          <div class="callout callout-info m-0 py-2">
	            <div>
	              <strong><spring:message code="mxDate" /></strong>
	            </div>
	            <div class="input-group">
                   <input id="fecMuestraRange" name="fecMuestraRange" class="form-control" type="text">
                 </div>
	          </div>
	          
	          <hr class="mx-3 my-0">
	          <div class="callout callout-success m-0 py-2">
	            <div>
	              <strong><spring:message code="caseState" /></strong>
	            </div>
	            <select name="estado" id="estado" class="form-control select2-single">
	            	<option value="ALL"><spring:message code="all"/></option>
					<c:forEach items="${estados}" var="estado">
						<option value="${estado.catKey}"><spring:message code="${estado.messageKey}" /></option>
					</c:forEach>
				</select>
	          </div>
	          
	          <hr class="mx-3 my-0">
	          <div class="callout callout-success m-0 py-2">
	            <div>
	              <strong><spring:message code="tipoou" /></strong>
	            </div>
	            <select name="tipoou" id="tipoou" class="form-control select2-single">
	            	<option value="AREA"><spring:message code="area"/></option>
	            	<option value="DISTR"><spring:message code="district"/></option>
					<option selected value="LOCAL"><spring:message code="locality"/></option>
				</select>
	          </div>
	          <hr class="mx-3 my-0">
	          <div class="callout m-0 callout-danger py-2">
	            <div>
	              <strong>Tiempo</strong>
	            </div>
	            <select name="tiempo" id="tiempo" class="form-control select2-single">
	            	<option selected value="Dia"><spring:message code="mxDate"/></option>
	            	<option value="Semana">Semana</option>
					<option value="Mes">Mes</option>
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
  <spring:url value="/resources/vendors/js/Control.FullScreen.js" var="ControlFullScreenJS" />
  <script src="${ControlFullScreenJS}" type="text/javascript"></script>
  <spring:url value="/resources/vendors/js/Leaflet.draw.js" var="leafletDrawJS" />
  <script src="${leafletDrawJS}" type="text/javascript"></script>    
  
  <!-- Custom scripts required by this view -->
  <spring:url value="/resources/js/views/map1.js" var="ProcessMapDashboard" />
  <script src="${ProcessMapDashboard}"></script>
  
  
  <spring:url value="/view/maps/pordia/" var="casosPorDiaUrl"/>
  <spring:url value="/view/maps/porou/" var="casosPorOUUrl"/>
  <spring:url value="/view/maps/porubi/1/" var="casosPorUbiUrl1"/>
  <spring:url value="/view/maps/porubi/2/" var="casosPorUbiUrl2"/>
  <spring:url value="/view/maps/porestado/" var="casosPorEstadoUrl"/>
  
  <spring:url value="/view/maps/case" var="verCasoUrl"/>
  
  
  <spring:url value="/resources/img/icons-maps/marker-icon-blue.png" var="iconBlue" />
  <spring:url value="/resources/img/icons-maps/marker-icon-yellow.png" var="iconYellow" />
  <spring:url value="/resources/img/icons-maps/marker-icon-green.png" var="iconGreen" />
  <spring:url value="/resources/img/icons-maps/marker-icon-red.png" var="iconRed" />
  <spring:url value="/resources/img/icons-maps/marker-icon-orange.png" var="iconOrange" />
  <spring:url value="/resources/img/icons-maps/marker-icon-violet.png" var="iconPurple" />
  <spring:url value="/resources/img/icons-maps/marker-icon-grey.png" var="iconGray" />
  <spring:url value="/resources/img/icons-maps/marker-icon-black.png" var="iconBlack" />
  <spring:url value="/resources/img/icons-maps/pdxs.png" var="iconPdxs" />
  <spring:url value="/resources/img/icons-maps/criadero.png" var="iconCr" />
  <spring:url value="/resources/img/icons-maps/marker-shadow.png" var="iconShadow" />
  
  <c:set var="casostot"><spring:message code="casostot" /></c:set>
  <c:set var="conf"><spring:message code="conf" /></c:set>
  <c:set var="inv"><spring:message code="inv" /></c:set>
  <c:set var="tx"><spring:message code="tx" /></c:set>
  <c:set var="txSup"><spring:message code="txSup" /></c:set>
  <c:set var="txComp"><spring:message code="txComp" /></c:set>
  <c:set var="sx"><spring:message code="sx" /></c:set>
  <c:set var="sxComp"><spring:message code="sxComp" /></c:set>
  <c:set var="lostFollowUp"><spring:message code="lostFollowUp" /></c:set>
  <c:set var="EST1"><spring:message code="CAT_ESTADOCASO_1" /></c:set>
  <c:set var="EST2"><spring:message code="CAT_ESTADOCASO_2" /></c:set>
  <c:set var="EST3"><spring:message code="CAT_ESTADOCASO_3" /></c:set>
  <c:set var="EST4"><spring:message code="CAT_ESTADOCASO_4" /></c:set>
  <c:set var="EST5"><spring:message code="CAT_ESTADOCASO_5" /></c:set>
  <c:set var="EST6"><spring:message code="CAT_ESTADOCASO_6" /></c:set>
  <c:set var="EST7"><spring:message code="CAT_ESTADOCASO_7" /></c:set>
  
  <c:set var="successmessage"><spring:message code="process.success" /></c:set>
  <c:set var="errormessage"><spring:message code="process.errors" /></c:set>
  <c:set var="waitmessage"><spring:message code="process.wait" /></c:set>
  

<script>

jQuery(document).ready(function() {
	$('.tipso').tipso({
		position: 'right',
		background: 'rgba(0,0,0,0.8)',
		useTitle: false
	});
	var parametros = {casosPorDiaUrl: "${casosPorDiaUrl}", casosPorOUUrl: "${casosPorOUUrl}", casosPorUbiUrl1: "${casosPorUbiUrl1}", casosPorUbiUrl2: "${casosPorUbiUrl2}", 
			casosPorEstadoUrl: "${casosPorEstadoUrl}",EST1: "${EST1}",EST2: "${EST2}", EST3: "${EST3}", EST4: "${EST4}", EST5: "${EST5}", EST6: "${EST6}", EST7: "${EST7}",  
			successmessage: "${successmessage}",successmessage: "${successmessage}",verCasoUrl: "${verCasoUrl}",
			errormessage: "${errormessage}",waitmessage: "${waitmessage}",iconBlue: "${iconBlue}",iconYellow: "${iconYellow}",iconRed: "${iconRed}",iconOrange: "${iconOrange}",iconGreen: "${iconGreen}",
			iconPdxs: "${iconPdxs}",iconCr: "${iconCr}",iconPurple: "${iconPurple}",iconGray: "${iconGray}",iconBlack: "${iconBlack}",iconShadow: "${iconShadow}",
			sxComp: "${sxComp}",sx: "${sx}",txComp: "${txComp}",tx: "${tx}",txSup: "${txSup}",lostFollowUp: "${lostFollowUp}",conf: "${conf}",inv: "${inv}",casostot: "${casostot}",dataTablesLang: "${dataTablesLang}"};
	ProcessMapDashboard.init(parametros);
});


</script>

</body>
</html>