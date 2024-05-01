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
	  <spring:url value="/irs/visit/saveEntity/" var="saveUrl"></spring:url>
	  <spring:url value="/irs/visit/{ident}/" var="visitaUrl">
		<spring:param name="ident" value="${visita.ident}" />
	  </spring:url>
  	  
      <!-- Breadcrumb -->
      <ol class="breadcrumb">
        <li class="breadcrumb-item"><a href="<spring:url value="/" htmlEscape="true "/>"><spring:message code="home" /></a></li>
        <li class="breadcrumb-item active"><a href="<spring:url value="/irs/dashboard/" htmlEscape="true "/>"><spring:message code="irs" /></a></li>
        <li class="breadcrumb-item"><a href="<spring:url value="/irs/visit/" htmlEscape="true "/>"><spring:message code="visits" /></a></li>
        <li class="breadcrumb-item active"><c:out value="${visita.target.household.code}" /></li>
        
      </ol>
	  <!-- Container -->
      <div class="container-fluid">

        <div class="animated fadeIn">
          <div class="row">
            <div class="col-md-12">
              <div class="card">
                <div class="card-header">
                  <i class="icon-note"></i> <spring:message code="edit" /> <spring:message code="visits" />
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
	                        <input type="text" id="ident" name="ident" readonly value="${visita.ident}" class="form-control" placeholder="<spring:message code="ident" />">
	                      </div>
	                    </div>	
	                    
	                    <div class="form-group" hidden>
						  <label><spring:message code="target" /></label>	
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-key"></i></span>
	                        <input type="text" id="target" name="target" readonly value="${visita.target.ident}" class="form-control" placeholder="<spring:message code="target" />">
	                      </div>
	                    </div>
			            
	                    <div class="form-group">
	                      <label><spring:message code="visitDate" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
	                        <fmt:formatDate value="${visita.visitDate}" var="fecVisita" pattern="dd/MM/yyyy" />
	                        <input type="text" id="visitDate" name="visitDate" value="${fecVisita}" class="form-control date-picker" data-date-format="dd/mm/yyyy">
	                      </div>
	                    </div>  
	                    
	                   <div class="form-group">
	                      <label><spring:message code="visitor" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="icon-note"></i></span>
	                        <select name="rociador" id="rociador" class="form-control select2-single">
	                        	<option value=""><spring:message code="visitor" /> - <spring:message code="empty" /></option>
	                        	<c:forEach items="${rociadores}" var="rociador">
									<c:choose> 
										<c:when test="${rociador.ident eq visita.visitor.ident}">
											<option selected value="${rociador.ident}"><spring:message code="${rociador.name}" /></option>
										</c:when>
										<c:otherwise>
											<option value="${rociador.ident}"><spring:message code="${rociador.name}" /></option>
										</c:otherwise>
									</c:choose> 
								</c:forEach>
	                        </select>
	                      </div>
	                    </div>
	                    
	                    <div class="form-group">
	                      <label><spring:message code="supervisor" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="icon-note"></i></span>
	                        <select name="supervisor" id="supervisor" class="form-control select2-single">
	                        	<option value=""><spring:message code="supervisor" /> - <spring:message code="empty" /></option>
	                        	<c:forEach items="${supervisores}" var="supervisor">
									<c:choose> 
										<c:when test="${supervisor.ident eq visita.supervisor.ident}">
											<option selected value="${supervisor.ident}"><spring:message code="${supervisor.name}" /></option>
										</c:when>
										<c:otherwise>
											<option value="${supervisor.ident}"><spring:message code="${supervisor.name}" /></option>
										</c:otherwise>
									</c:choose> 
								</c:forEach>
	                        </select>
	                      </div>
	                    </div>
	                    
	                    <div class="form-group">
	                      <label><spring:message code="brigades" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="icon-note"></i></span>
	                        <select name="brigada" id="brigada" class="form-control select2-single">
	                        	<option value=""><spring:message code="brigades" /> - <spring:message code="empty" /></option>
	                        	<c:forEach items="${brigadas}" var="brigada">
									<c:choose> 
										<c:when test="${brigada.ident eq visita.brigada.ident}">
											<option selected value="${brigada.ident}"><spring:message code="${brigada.name}" /></option>
										</c:when>
										<c:otherwise>
											<option value="${brigada.ident}"><spring:message code="${brigada.name}" /></option>
										</c:otherwise>
									</c:choose> 
								</c:forEach>
	                        </select>
	                      </div>
	                    </div>
	                    
	                    <div class="form-group">
	                      <label><spring:message code="visit" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-pencil"></i></span>
	                        <select name="visit" id="visit" class="form-control select2-single">
	                        	<option value=""><spring:message code="visit" /> - <spring:message code="empty" /></option>
	                        	<c:forEach items="${categorias}" var="cate">
									<c:choose> 
										<c:when test="${cate.catKey eq visita.visit}">
											<option selected value="${cate.catKey}"><spring:message code="${cate.messageKey}" /></option>
										</c:when>
										<c:otherwise>
											<option value="${cate.catKey}"><spring:message code="${cate.messageKey}" /></option>
										</c:otherwise>
									</c:choose> 
								</c:forEach>
	                        </select>
	                      </div>
	                    </div>
	                    
	                    <div class="form-group">
	                      <label><spring:message code="activity" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-pencil"></i></span>
	                        <select name="activity" id="activity" class="form-control select2-single">
	                        	<option value=""><spring:message code="activity" /> - <spring:message code="empty" /></option>
	                        	<c:forEach items="${tipos}" var="tipo">
									<c:choose> 
										<c:when test="${tipo.catKey eq visita.activity}">
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
	                      <label><spring:message code="compVisit" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-pencil"></i></span>
	                        <select name="compVisit" id="compVisit" class="form-control select2-single">
	                        	<option value=""><spring:message code="compVisit" /> - <spring:message code="empty" /></option>
	                        	<c:forEach items="${sinos}" var="sino">
									<c:choose> 
										<c:when test="${sino.catKey eq visita.compVisit}">
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
	                      <label><spring:message code="reasonNoVisit" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-pencil"></i></span>
	                        <select name="reasonNoVisit" id="reasonNoVisit" class="form-control select2-single">
	                        	<option value=""><spring:message code="reasonNoVisit" /> - <spring:message code="empty" /></option>
	                        	<c:forEach items="${razonesnovis}" var="razon">
									<c:choose> 
										<c:when test="${razon.catKey eq visita.reasonNoVisit}">
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
	                      <label><spring:message code="reasonNoVisitOther" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-sticky-note-o"></i></span>
	                        <input type="text" id="reasonNoVisitOther" name="reasonNoVisitOther" value="${visita.reasonNoVisitOther}" class="form-control" placeholder="<spring:message code="reasonNoVisitOther" />">
	                      </div>
	                    </div>
	                    
	                    <div class="form-group">
	                      <label><spring:message code="reasonReluctant" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-pencil"></i></span>
	                        <select name="reasonReluctant" id="reasonReluctant" class="form-control select2-single">
	                        	<option value=""><spring:message code="reasonReluctant" /> - <spring:message code="empty" /></option>
	                        	<c:forEach items="${razonesrenuente}" var="razon">
									<c:choose> 
										<c:when test="${razon.catKey eq visita.reasonReluctant}">
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
	                      <label><spring:message code="reasonReluctantOther" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-sticky-note-o"></i></span>
	                        <input type="text" id="reasonReluctantOther" name="reasonReluctantOther" value="${visita.reasonReluctantOther}" class="form-control" placeholder="<spring:message code="reasonReluctantOther" />">
	                      </div>
	                    </div>
	                    
	                    <div class="form-group">
	                      <label><spring:message code="sprayedRooms" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-hashtag"></i></span>
	                        <input type="text" id="sprayedRooms" name="sprayedRooms" value="${visita.sprayedRooms}" class="form-control" placeholder="<spring:message code="sprayedRooms" />">
	                      </div>
	                    </div>
	                    
	                    <div class="form-group">
	                      <label><spring:message code="numCharges" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-hashtag"></i></span>
	                        <input type="text" id="numCharges" name="numCharges" value="${visita.numCharges}" class="form-control" placeholder="<spring:message code="numCharges" />">
	                      </div>
	                    </div>
	                    
	                    <div class="form-group">
	                      <label><spring:message code="reasonIncomplete" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-sticky-note-o"></i></span>
	                        <input type="text" id="reasonIncomplete" name="reasonIncomplete" value="${visita.reasonIncomplete}" class="form-control" placeholder="<spring:message code="reasonIncomplete" />">
	                      </div>
	                    </div>
	                    
	                    <div class="form-group">
	                      <label><spring:message code="supervised" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-pencil"></i></span>
	                        <select name="supervised" id="supervised" class="form-control select2-single">
	                        	<option value=""><spring:message code="supervised" /> - <spring:message code="empty" /></option>
	                        	<c:forEach items="${sinos}" var="sino">
									<c:choose> 
										<c:when test="${sino.catKey eq visita.supervised}">
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
	                      <label><spring:message code="personasCharlas" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-hashtag"></i></span>
	                        <input type="text" id="personasCharlas" name="personasCharlas" value="${visita.personasCharlas}" class="form-control" placeholder="<spring:message code="personasCharlas" />">
	                      </div>
	                    </div>
	                    
	                    <div class="form-group">
	                      <label><spring:message code="obs" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-sticky-note-o"></i></span>
	                        <input type="text" id="obs" name="obs" value="${visita.obs}" class="form-control" placeholder="<spring:message code="obs" />">
	                      </div>
	                    </div>
	                    
	                    
                        <div class="form-group">
                          <button type="submit" class="btn btn-primary" id="guardar"><i class="fa fa-save"></i>&nbsp;<spring:message code="save" /></button>
						  <a href="${fn:escapeXml(visitaUrl)}" class="btn btn-danger"><i class="fa fa-undo"></i>&nbsp;<spring:message code="cancel" /></a>
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
  <spring:url value="/resources/vendors/js/moment.min.js" var="moment" />
  <script src="${moment}" type="text/javascript"></script>  
  
  <spring:url value="/resources/vendors/js/select2.min.js" var="Select2" />
  <script src="${Select2}" type="text/javascript"></script>
  

  <!-- Custom scripts required by this view -->
  <spring:url value="/resources/js/views/Visit.js" var="processEntity" />
  <script src="${processEntity}"></script>
  
<c:set var="successmessage"><spring:message code="process.success" /></c:set>
<c:set var="errormessage"><spring:message code="process.errors" /></c:set>
<c:set var="waitmessage"><spring:message code="process.wait" /></c:set>

<script>
	jQuery(document).ready(function() {
		var parametros = {saveUrl: "${saveUrl}", successmessage: "${successmessage}",
				errormessage: "${errormessage}",waitmessage: "${waitmessage}",
				visitaUrl: "${visitaUrl}" 
		};
		ProcessEntity.init(parametros);
	});
	
</script>
  
</body>
</html>