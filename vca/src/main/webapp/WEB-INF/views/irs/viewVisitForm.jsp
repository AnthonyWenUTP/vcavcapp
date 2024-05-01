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
		                  <i class="icon-house"></i>&nbsp;<strong><spring:message code="ownerName" />: <c:out value="${visita.target.household.ownerName}" /></strong>
		                </div>
	                	<div class="card-body">
	                		<div class="form-group row">
		                      <label class="col-md-6 col-form-label"><spring:message code="ident" />:</label>
		                      <div class="col-md-6">
		                        <p class="form-control-static"><strong><c:out value="${visita.ident}" /></strong></p>
		                      </div>
		                    </div>
		                    <div class="form-group row">
		                      <label class="col-md-6 col-form-label"><spring:message code="season" />:</label>
		                      <div class="col-md-6">
		                        <p class="form-control-static"><strong><c:out value="${visita.target.irsSeason.name}" /></strong></p>
		                      </div>
		                    </div>
		                    <div class="form-group row">
		                      <label class="col-md-6 col-form-label"><spring:message code="code" />:</label>
		                      <div class="col-md-6">
		                        <p class="form-control-static"><strong><c:out value="${visita.target.household.code}" /></strong></p>
		                      </div>
		                    </div>
		                    <div class="form-group row">
		                      <label class="col-md-6 col-form-label"><spring:message code="locality" />:</label>
		                      <div class="col-md-6">
		                        <p class="form-control-static"><strong><c:out value="${visita.target.household.local.name}" /></strong></p>
		                      </div>
		                    </div>
		                    <div class="form-group row">
		                      <label class="col-md-6 col-form-label"><spring:message code="visitDate" />:</label>
		                      <div class="col-md-6">
		                        <p class="form-control-static"><strong><c:out value="${visita.visitDate}" /></strong></p>
		                      </div>
		                    </div>
		                    <div class="form-group row">
		                      <label class="col-md-6 col-form-label"><spring:message code="visitor" />:</label>
		                      <div class="col-md-6">
		                        <p class="form-control-static"><strong><c:out value="${visita.visitor.name}" /></strong></p>
		                      </div>
		                    </div>
		                    <div class="form-group row">
		                      <label class="col-md-6 col-form-label"><spring:message code="supervisor" />:</label>
		                      <div class="col-md-6">
		                        <p class="form-control-static"><strong><c:out value="${visita.supervisor.name}" /></strong></p>
		                      </div>
		                    </div>
		                    <div class="form-group row">
		                      <label class="col-md-6 col-form-label"><spring:message code="brigada" />:</label>
		                      <div class="col-md-6">
		                        <p class="form-control-static"><strong><c:out value="${visita.brigada.name}" /></strong></p>
		                      </div>
		                    </div>
		                    <div class="form-group row">
		                      <label class="col-md-6 col-form-label"><spring:message code="visit" />:</label>
		                      <div class="col-md-6">
		                        <p class="form-control-static"><strong><c:out value="${visita.visit}" /></strong></p>
		                      </div>
		                    </div>
		                    <div class="form-group row">
		                      <label class="col-md-6 col-form-label"><spring:message code="activity" />:</label>
		                      <div class="col-md-6">
		                        <p class="form-control-static"><strong><c:out value="${visita.activity}" /></strong></p>
		                      </div>
		                    </div>
		                    <div class="form-group row">
		                      <label class="col-md-6 col-form-label"><spring:message code="compVisit" />:</label>
		                      <div class="col-md-6">
		                        <p class="form-control-static"><strong><c:out value="${visita.compVisit}" /></strong></p>
		                      </div>
		                    </div>
		                    <div class="form-group row">
		                      <label class="col-md-6 col-form-label"><spring:message code="reasonNoVisit" />:</label>
		                      <div class="col-md-6">
		                        <p class="form-control-static"><strong><c:out value="${visita.reasonNoVisit}" /></strong></p>
		                      </div>
		                    </div>
		                    <div class="form-group row">
		                      <label class="col-md-6 col-form-label"><spring:message code="reasonNoVisitOther" />:</label>
		                      <div class="col-md-6">
		                        <p class="form-control-static"><strong><c:out value="${visita.reasonNoVisitOther}" /></strong></p>
		                      </div>
		                    </div>
		                    <div class="form-group row">
		                      <label class="col-md-6 col-form-label"><spring:message code="reasonReluctant" />:</label>
		                      <div class="col-md-6">
		                        <p class="form-control-static"><strong><c:out value="${visita.reasonReluctant}" /></strong></p>
		                      </div>
		                    </div>
		                    <div class="form-group row">
		                      <label class="col-md-6 col-form-label"><spring:message code="reasonReluctantOther" />:</label>
		                      <div class="col-md-6">
		                        <p class="form-control-static"><strong><c:out value="${visita.reasonReluctantOther}" /></strong></p>
		                      </div>
		                    </div>
		                    <div class="form-group row">
		                      <label class="col-md-6 col-form-label"><spring:message code="sprayedRooms" />:</label>
		                      <div class="col-md-6">
		                        <p class="form-control-static"><strong><c:out value="${visita.sprayedRooms}" /></strong></p>
		                      </div>
		                    </div>
		                    <div class="form-group row">
		                      <label class="col-md-6 col-form-label"><spring:message code="numCharges" />:</label>
		                      <div class="col-md-6">
		                        <p class="form-control-static"><strong><c:out value="${visita.numCharges}" /></strong></p>
		                      </div>
		                    </div>
		                    <div class="form-group row">
		                      <label class="col-md-6 col-form-label"><spring:message code="reasonIncomplete" />:</label>
		                      <div class="col-md-6">
		                        <p class="form-control-static"><strong><c:out value="${visita.reasonIncomplete}" /></strong></p>
		                      </div>
		                    </div>
		                    <div class="form-group row">
		                      <label class="col-md-6 col-form-label"><spring:message code="supervised" />:</label>
		                      <div class="col-md-6">
		                        <p class="form-control-static"><strong><c:out value="${visita.supervised}" /></strong></p>
		                      </div>
		                    </div>
		                    <div class="form-group row">
		                      <label class="col-md-6 col-form-label"><spring:message code="personasCharlas" />:</label>
		                      <div class="col-md-6">
		                        <p class="form-control-static"><strong><c:out value="${visita.personasCharlas}" /></strong></p>
		                      </div>
		                    </div>
		                    <div class="form-group row">
		                      <label class="col-md-6 col-form-label"><spring:message code="obs" />:</label>
		                      <div class="col-md-6">
		                        <p class="form-control-static"><strong><c:out value="${visita.obs}" /></strong></p>
		                      </div>
		                    </div>
		                    <div class="form-group row">
		                      <label class="col-md-6 col-form-label"><spring:message code="enabled" />:</label>
		                      <div class="col-md-6">
		                        <p class="form-control-static"><strong>
		                        	<c:choose>
										<c:when test="${visita.pasive=='0'.charAt(0)}">
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
		                      <label class="col-md-6 col-form-label"><spring:message code="createdBy" />:</label>
		                      <div class="col-md-6">
		                        <p class="form-control-static"><strong><c:out value="${visita.recordUser}" /></strong></p>
		                      </div>
		                    </div>
		                    <div class="form-group row">
		                      <label class="col-md-6 col-form-label"><spring:message code="dateCreated" />:</label>
		                      <div class="col-md-6">
		                        <p class="form-control-static"><strong><c:out value="${visita.recordDate}" /></strong></p>
		                      </div>
		                    </div>
            				<spring:url value="/irs/visit/disableEntity/{ident}/" var="disableUrl">
                              	<spring:param name="ident" value="${visita.ident}" />
                          	</spring:url>
                          	<spring:url value="/irs/visit/enableEntity/{ident}/" var="enableUrl">
                              	<spring:param name="ident" value="${visita.ident}" />
                          	</spring:url>
                          	<spring:url value="/irs/visit/editEntity/{ident}/" var="editUrl">
                              	<spring:param name="ident" value="${visita.ident}" />
                          	</spring:url>
						</div>
						<div class="card-header">
          				<div class="row float-right mr-4" >
          					<button id="edit_entity" onclick="location.href='${fn:escapeXml(editUrl)}'" type="button" class="btn btn-outline-primary"><i class="fa fa-pencil"></i>&nbsp; <spring:message code="edit" /></button>
          					<c:choose>
								<c:when test="${visita.pasive=='0'.charAt(0)}">
									<button id="disable_entity" onclick="location.href='${fn:escapeXml(disableUrl)}'" type="button" class="btn btn-outline-danger"><i class="fa fa-close"></i>&nbsp; <spring:message code="disable" /></button>
								</c:when>
								<c:otherwise>
									<button id="enable_entity" onclick="location.href='${fn:escapeXml(enableUrl)}'" type="button" class="btn btn-outline-primary"><i class="fa fa-check"></i>&nbsp; <spring:message code="enable" /></button>
								</c:otherwise>
						 	</c:choose>
						 	<button id="back_button" onclick="location.href='<spring:url value="/irs/visit/" htmlEscape="true "/>'" type="button" class="btn btn-outline-primary"><i class="fa fa-undo"></i>&nbsp; <spring:message code="back" /></button>
          				</div>
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

  </script>
</body>
</html>