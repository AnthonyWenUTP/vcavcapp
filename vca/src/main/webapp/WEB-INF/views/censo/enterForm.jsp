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
	  <spring:url value="/census/saveEntity/" var="saveUrl"></spring:url>
	  <spring:url value="/census/{ident}/" var="viviendaUrl">
		<spring:param name="ident" value="${vivienda.ident}" />
	  </spring:url>
	  <spring:url value="/census/{ident}/" var="listUrl">
		<spring:param name="ident" value="${vivienda.ident}" />
	  </spring:url>
	  <spring:url value="/census/localidad/" var="opclocaUrl"/>
  	  
      <!-- Breadcrumb -->
      <ol class="breadcrumb">
        <li class="breadcrumb-item"><a href="<spring:url value="/" htmlEscape="true "/>"><spring:message code="home" /></a></li>
        <li class="breadcrumb-item"><a href="<spring:url value="/census/" htmlEscape="true "/>"><spring:message code="census" /></a></li>
        <li class="breadcrumb-item active"><c:out value="${vivienda.code}" /></li>
        
      </ol>
	  <!-- Container -->
      <div class="container-fluid">

        <div class="animated fadeIn">
          <div class="row">
            <div class="col-md-12">
              <div class="card">
                <div class="card-header">
                  <i class="icon-note"></i> <spring:message code="edit" /> <spring:message code="census" />
                  <div class="card-actions">
                    
                  </div>
                </div>
                <div class="card-body">

                  <div class="row">

                    <div class="col-md-8">
                      <form action="#" autocomplete="off" id="add-form">                      
						<div class="form-group">
						  <label><spring:message code="ident" /></label>	
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-key"></i></span>
	                        <input type="text" id="ident" name="ident" readonly value="${vivienda.ident}" class="form-control" placeholder="<spring:message code="ident" />">
	                      </div>
	                    </div>	
	                    <div class="form-group">
	                      <label><spring:message code="code" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-id-card"></i></span>
	                        <input type="text" id="code" name="code" value="${vivienda.code}" class="form-control" placeholder="<spring:message code="code" />">
	                      </div>
	                    </div> 
	                    
	                    <div class="form-group">
	                      <label><spring:message code="locality" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="icon-layers"></i></span>
	                        <select name="local" id="local" class="form-control select2-single">
	                        	<option value=""><spring:message code="locality" /> - <spring:message code="empty" /></option>
	                        	<c:forEach items="${localidades}" var="localidad">
									<c:choose> 
										<c:when test="${localidad.ident eq vivienda.local.ident}">
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
                          <label><spring:message code="ownerName" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-address-book"></i></span>
	                        <input type="text" id="ownerName" name="ownerName" value="${vivienda.ownerName}" class="form-control" placeholder="<spring:message code="ownerName" />">
	                      </div>
	                    </div>
	                    
	                    <div class="form-group" hidden>
                          <label><spring:message code="pattern" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-address-book"></i></span>
	                        <input type="text" id="pattern" name="pattern" value="${vivienda.local.pattern}" class="form-control" placeholder="<spring:message code="pattern" />">
	                      </div>
	                    </div>
	                    
	                    <div class="form-group">
	                      <label><spring:message code="censustaker" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="icon-note"></i></span>
	                        <select name="censusTaker" id="censusTaker" class="form-control select2-single">
	                        	<option value=""><spring:message code="censustaker" /> - <spring:message code="empty" /></option>
	                        	<c:forEach items="${censadores}" var="censador">
									<c:choose> 
										<c:when test="${censador.ident eq vivienda.censusTaker.ident}">
											<option selected value="${censador.ident}"><spring:message code="${censador.name}" /></option>
										</c:when>
										<c:otherwise>
											<option value="${censador.ident}"><spring:message code="${censador.name}" /></option>
										</c:otherwise>
									</c:choose> 
								</c:forEach>
	                        </select>
	                      </div>
	                    </div>
			            
			            
	                    <div class="form-group">
	                      <label><spring:message code="censusDate" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
	                        <fmt:formatDate value="${vivienda.censusDate}" var="fecCenso" pattern="dd/MM/yyyy" />
	                        <input type="text" id="censusDate" name="censusDate" value="${fecCenso}" class="form-control date-picker" data-date-format="dd/mm/yyyy" data-date-start-date="-90d" data-date-end-date="+0d">
	                      </div>
	                    </div>  
	                    
	                    <div class="form-group">
	                      <label><spring:message code="inhabited" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-pencil"></i></span>
	                        <select name="inhabited" id="inhabited" class="form-control select2-single">
	                        	<option value=""><spring:message code="inhabited" /> - <spring:message code="empty" /></option>
	                        	<c:forEach items="${sinos}" var="sino">
									<c:choose> 
										<c:when test="${sino.catKey eq vivienda.inhabited}">
											<option selected value="${sino.catKey}"><spring:message code="${sino.messageKey}" /></option>
										</c:when>
										<c:otherwise>
											<option value="${sino.catKey}"><spring:message code="${sino.messageKey}" /></option>
										</c:otherwise>
									</c:choose> 
								</c:forEach>
	                        </select>
	                      </div>
	                    </div>
                        <div class="form-group">
                          <label><spring:message code="habitants" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-users"></i></span>
	                        <input type="text" id="habitants" name="habitants" value="${vivienda.habitants}" class="form-control" placeholder="<spring:message code="habitants" />">
	                      </div>
	                    </div>
	                    
	                    <div class="form-group">
	                      <label><spring:message code="material" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-pencil"></i></span>
	                        <select name="material" id="material" class="form-control select2-single">
	                        	<option value=""><spring:message code="material" /> - <spring:message code="empty" /></option>
	                        	<c:forEach items="${materials}" var="material">
									<c:choose> 
										<c:when test="${material.catKey eq vivienda.material}">
											<option selected value="${material.catKey}"><spring:message code="${material.messageKey}" /></option>
										</c:when>
										<c:otherwise>
											<option value="${material.catKey}"><spring:message code="${material.messageKey}" /></option>
										</c:otherwise>
									</c:choose> 
								</c:forEach>
	                        </select>
	                      </div>
	                    </div>
	                    
	                    <div class="form-group">
	                      <label><spring:message code="rooms" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-window-restore"></i></span>
	                        <input type="text" id="rooms" name="rooms" value="${vivienda.rooms}" class="form-control" placeholder="<spring:message code="rooms" />">
	                      </div>
	                    </div>
	                    
	                    <div class="form-group">
	                      <label><spring:message code="sprRooms" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-window-restore"></i></span>
	                        <input type="text" id="sprRooms" name="sprRooms" value="${vivienda.sprRooms}" class="form-control" placeholder="<spring:message code="sprRooms" />">
	                      </div>
	                    </div>
	                    
	                    <div class="form-group">
	                      <label><spring:message code="noSprooms" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-window-restore"></i></span>
	                        <input type="text" id="noSprooms" name="noSprooms" value="${vivienda.noSprooms}" class="form-control" placeholder="<spring:message code="noSprooms" />">
	                      </div>
	                    </div>
	                    
	                    <div class="form-group">
	                      <label><spring:message code="noSproomsReasons" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-pencil"></i></span>
	                        <select name="noSproomsReasons" id="noSproomsReasons" class="form-control select2-multiple" multiple="true">
	                        	<option value=""><spring:message code="noSproomsReasons" /> - <spring:message code="empty" /></option>
	                        	<c:forEach items="${razones}" var="razon">
									<c:choose> 
										<c:when test="${fn:contains(vivienda.noSproomsReasons, razon.catKey)}">
											<option selected value="${razon.catKey}"><spring:message code="${razon.messageKey}" /></option>
										</c:when>
										<c:otherwise>
											<option value="${razon.catKey}"><spring:message code="${razon.messageKey}" /></option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
	                        </select>
	                      </div>
	                    </div>
	                    
	                    <div class="form-group">
	                      <label><spring:message code="sleep" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-window-restore"></i></span>
	                        <input type="text" id="sleep" name="sleep" value="${vivienda.sleep}" class="form-control" placeholder="<spring:message code="sleep" />">
	                      </div>
	                    </div>
	                    
	                    
	                    
	                    
	                    
	                    <div class="form-group">
	                      <label><spring:message code="personasCharlas" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-window-restore"></i></span>
	                        <input type="text" id="personasCharlas" name="personasCharlas" value="${vivienda.personasCharlas}" class="form-control" placeholder="<spring:message code="personasCharlas" />">
	                      </div>
	                    </div>
	                    
	                    
	                    <div class="form-group">
	                      <label><spring:message code="latitude" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-location-arrow"></i></span>
	                        <input type="text" id="latitude" name="latitude" value="${vivienda.latitude}" class="form-control" placeholder="<spring:message code="latitude" />">
	                      </div>
	                    </div>
	                    <div class="form-group">
	                      <label><spring:message code="longitude" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-location-arrow"></i></span>
	                        <input type="text" id="longitude" name="longitude" value="${vivienda.longitude}" class="form-control" placeholder="<spring:message code="longitude" />">
	                      </div>
	                    </div>
	                    
	                    <div class="form-group">
	                      <label><spring:message code="obs" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-sticky-note-o"></i></span>
	                        <input type="text" id="obs" name="obs" value="${vivienda.obs}" class="form-control" placeholder="<spring:message code="obs" />">
	                      </div>
	                    </div>
	                    
	                    <div class="form-group">
	                      <label><spring:message code="masculinos" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-window-restore"></i></span>
	                        <input type="text" id="masculinos" name="masculinos" value="${vivienda.masculinos}" class="form-control" placeholder="<spring:message code="masculinos" />">
	                      </div>
	                    </div>
	                    
	                    <div class="form-group">
	                      <label><spring:message code="femeninos" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-window-restore"></i></span>
	                        <input type="text" id="femeninos" name="femeninos" value="${vivienda.femeninos}" class="form-control" placeholder="<spring:message code="femeninos" />">
	                      </div>
	                    </div>
	                    
	                    <div class="form-group">
	                      <label><spring:message code="menores5" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-window-restore"></i></span>
	                        <input type="text" id="menores5" name="menores5" value="${vivienda.menores5}" class="form-control" placeholder="<spring:message code="menores5" />">
	                      </div>
	                    </div>
	                    
	                    <div class="form-group">
	                      <label><spring:message code="menores5masc" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-window-restore"></i></span>
	                        <input type="text" id="menores5masc" name="menores5masc" value="${vivienda.menores5masc}" class="form-control" placeholder="<spring:message code="menores5masc" />">
	                      </div>
	                    </div>
	                    
	                    <div class="form-group">
	                      <label><spring:message code="menores5fem" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-window-restore"></i></span>
	                        <input type="text" id="menores5fem" name="menores5fem" value="${vivienda.menores5fem}" class="form-control" placeholder="<spring:message code="menores5fem" />">
	                      </div>
	                    </div>
	                    
	                    <div class="form-group">
	                      <label><spring:message code="embarazadas" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-window-restore"></i></span>
	                        <input type="text" id="embarazadas" name="embarazadas" value="${vivienda.embarazadas}" class="form-control" placeholder="<spring:message code="embarazadas" />">
	                      </div>
	                    </div>
	                    
	                    <div class="form-group">
	                      <label><spring:message code="sitiosDormirCama" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-window-restore"></i></span>
	                        <input type="text" id="sitiosDormirCama" name="sitiosDormirCama" value="${vivienda.sitiosDormirCama}" class="form-control" placeholder="<spring:message code="sitiosDormirCama" />">
	                      </div>
	                    </div>
	                    
	                    <div class="form-group">
	                      <label><spring:message code="sitiosDormirHamaca" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-window-restore"></i></span>
	                        <input type="text" id="sitiosDormirHamaca" name="sitiosDormirHamaca" value="${vivienda.sitiosDormirHamaca}" class="form-control" placeholder="<spring:message code="sitiosDormirHamaca" />">
	                      </div>
	                    </div>
	                    
	                    <div class="form-group">
	                      <label><spring:message code="sitiosDormirSuelo" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-window-restore"></i></span>
	                        <input type="text" id="sitiosDormirSuelo" name="sitiosDormirSuelo" value="${vivienda.sitiosDormirSuelo}" class="form-control" placeholder="<spring:message code="sitiosDormirSuelo" />">
	                      </div>
	                    </div>
	                    
	                    <div class="form-group">
	                      <label><spring:message code="sitiosDormirOtro" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-window-restore"></i></span>
	                        <input type="text" id="sitiosDormirOtro" name="sitiosDormirOtro" value="${vivienda.sitiosDormirOtro}" class="form-control" placeholder="<spring:message code="sitiosDormirOtro" />">
	                      </div>
	                    </div>
	                    
	                    
	                    <div class="form-group">
	                      <label><spring:message code="mtildExistentes" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-window-restore"></i></span>
	                        <input type="text" id="mtildExistentes" name="mtildExistentes" value="${vivienda.mtildExistentes}" class="form-control" placeholder="<spring:message code="mtildExistentes" />">
	                      </div>
	                    </div>
	                    
	                    <div class="form-group">
	                      <label><spring:message code="mosqSinInsecticida" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-window-restore"></i></span>
	                        <input type="text" id="mosqSinInsecticida" name="mosqSinInsecticida" value="${vivienda.mosqSinInsecticida}" class="form-control" placeholder="<spring:message code="mosqSinInsecticida" />">
	                      </div>
	                    </div>
	                    
	                    
                        <div class="form-group">
                          <button type="submit" class="btn btn-primary" id="guardar"><i class="fa fa-save"></i>&nbsp;<spring:message code="save" /></button>
						  <a href="${fn:escapeXml(viviendaUrl)}" class="btn btn-danger"><i class="fa fa-undo"></i>&nbsp;<spring:message code="cancel" /></a>
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
  
  <spring:url value="/resources/vendors/js/bootstrap-datepicker.js" var="datepicker" />
  <script src="${datepicker}" type="text/javascript"></script>
  
  <spring:url value="/resources/vendors/js/select2.min.js" var="Select2" />
  <script src="${Select2}" type="text/javascript"></script>
  

  <!-- Custom scripts required by this view -->
  <spring:url value="/resources/js/views/HouseHold.js" var="processEntity" />
  <script src="${processEntity}"></script>
  
<c:set var="successmessage"><spring:message code="process.success" /></c:set>
<c:set var="errormessage"><spring:message code="process.errors" /></c:set>
<c:set var="waitmessage"><spring:message code="process.wait" /></c:set>

<script>
	jQuery(document).ready(function() {
		var parametros = {saveUrl: "${saveUrl}", successmessage: "${successmessage}",
				errormessage: "${errormessage}",waitmessage: "${waitmessage}",
				listUrl: "${listUrl}",opclocaUrl: "${opclocaUrl}" , latitudMinima: "${latitudMinima}", latitudMaxima: "${latitudMaxima}"  
					, longitudMinima: "${longitudMinima}", longitudMaxima: "${longitudMaxima}" 
		};
		ProcessEntity.init(parametros);
	});
	
	$('#censusTaker,#local,#inhabited,#material,#noSproomsReasons').select2({
	    theme: "bootstrap",
	    width: '100%'
	});
</script>
  
</body>
</html>