<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
<jsp:include page="../fragments/headTag.jsp" />
<!-- Styles required by this views -->
<spring:url value="/resources/vendors/css/select2.min.css" var="select2css" />
<link href="${select2css}" rel="stylesheet" type="text/css"/>
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
	  	<spring:url value="/users/editUser" var="editUrl"/>
      <!-- Breadcrumb -->
      <ol class="breadcrumb">
        <li class="breadcrumb-item"><a href="<spring:url value="/" htmlEscape="true "/>"><spring:message code="home" /></a></li>
        <li class="breadcrumb-item active"><c:out value="${user.username}" /></li>
        
      </ol>
	  <!-- Container -->
      <div class="container-fluid">
      	<div class="animated fadeIn">

          	<div class="row">
	            <div class="col-md-12">
	              <div class="card">
	                <div class="card-header">
	                  <i class="icon-user"></i>&nbsp;<strong><c:out value="${user.username}" /></strong>
	                </div>
	                <div class="card-body">
	                	<div class="row">
							<div class="col-md-3">
								<div>
									<label class="control-label"><spring:message code="userdesc" />:</label>
									<div>
										<p class="form-control-static">
											 <strong><c:out value="${user.completeName}" /></strong>
										</p>
									</div>
								</div>
							</div>
							<!--/span-->
							<div class="col-md-3">
								<div>
									<label class="control-label"><spring:message code="useremail" />:</label>
									<div>
										<p class="form-control-static">
											 <strong><c:out value="${user.email}" /></strong>
										</p>
									</div>
								</div>
							</div>
							<div class="col-md-3">
								<div class="form-group">
									<label class="control-label"><spring:message code="enabled" />:</label>
									<div>
										<p class="form-control-static">
											<c:choose>
												<c:when test="${user.enabled}">
													<strong><spring:message code="CAT_SINO_SI" /></strong>
												</c:when>
												<c:otherwise>
													<strong><spring:message code="CAT_SINO_NO" /></strong>
												</c:otherwise>
											</c:choose>
										</p>
									</div>
								</div>
							</div>
							<!--/span-->
							<div class="col-md-3">
								<div class="form-group">
									<label class="control-label"><spring:message code="locked" />:</label>
									<div>
										<p class="form-control-static">
											<c:choose>
												<c:when test="${user.accountNonLocked}">
													<strong><spring:message code="CAT_SINO_NO" /></strong>
												</c:when>
												<c:otherwise>
													<strong><spring:message code="CAT_SINO_SI" /></strong>
												</c:otherwise>
											</c:choose>
										</p>
									</div>
								</div>
							</div>
							<!--/span-->
						</div>
						<!--/row-->
						<div class="row">
							<!--/span-->
							<div class="col-md-3">
								<div class="form-group">
									<label class="control-label"><spring:message code="usercred" />:</label>
									<div>
										<p class="form-control-static">
											<c:choose>
												<c:when test="${user.credentialsNonExpired}">
													<strong><spring:message code="CAT_SINO_NO" /></strong>
												</c:when>
												<c:otherwise>
													<strong><spring:message code="CAT_SINO_SI" /></strong>
												</c:otherwise>
											</c:choose>
										</p>
									</div>
								</div>
							</div>
							<!--/span-->
							<div class="col-md-3">
								<div class="form-group">
									<label class="control-label"><spring:message code="userexp" />:</label>
									<div>
										<p class="form-control-static">
											<c:choose>
												<c:when test="${user.accountNonExpired}">
													<strong><spring:message code="CAT_SINO_NO" /></strong>
												</c:when>
												<c:otherwise>
													<strong><spring:message code="CAT_SINO_SI" /></strong>
												</c:otherwise>
											</c:choose>
										</p>
									</div>
								</div>
							</div>
							<!--/span-->
							<div class="col-md-3">
								<div class="form-group">
									<label class="control-label"><spring:message code="createdBy" />:</label>
									<div>
										<p class="form-control-static">
											 <strong><c:out value="${user.createdBy}" /></strong>
										</p>
									</div>
								</div>
							</div>
							<!--/span-->
							<div class="col-md-3">
								<div class="form-group">
									<label class="control-label"><spring:message code="dateCreated" />:</label>
									<div>
										<p class="form-control-static">
											<strong><c:out value="${user.created}" /></strong>
										</p>
									</div>
								</div>
							</div>
							<!--/span-->
						</div>
						<!--/row-->
						<div class="row">
							<div class="col-md-3">
								<div class="form-group">
									<label class="control-label"><spring:message code="modifiedBy" />:</label>
									<div>
										<p class="form-control-static">
											 <strong><c:out value="${user.modifiedBy}" /></strong>
										</p>
									</div>
								</div>
							</div>
							<!--/span-->
							<div class="col-md-3">
								<div class="form-group">
									<label class="control-label"><spring:message code="dateModified" />:</label>
									<div>
										<p class="form-control-static">
											<strong><c:out value="${user.modified}" /></strong>
										</p>
									</div>
								</div>
							</div>
							<!--/span-->
							<div class="col-md-3">
								<div class="form-group">
									<label class="control-label"><spring:message code="lastAccess" />:</label>
									<div>
										<p class="form-control-static">
											 <strong><c:out value="${user.lastAccess}" /></strong>
										</p>
									</div>
								</div>
							</div>
							<!--/span-->
							<div class="col-md-3">
								<div class="form-group">
									<label class="control-label"><spring:message code="dateCredentials" />:</label>
									<div>
										<p class="form-control-static">
											<strong><c:out value="${user.lastCredentialChange}" /></strong>
										</p>
									</div>
								</div>
							</div>
							<!--/span-->
						</div>
	                </div>
	                <div class="card-header">
	                	<div class="row float-right mr-4" >
			            	<a class="btn btn-primary" href="${fn:escapeXml(editUrl)}"><i class="fa fa-edit"></i> <spring:message code="edit" /></a>
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
	                  <i class="icon-check"></i>&nbsp;<strong><spring:message code="userroles" /></strong>
	                </div>
	                <div class="card-body">
	                	<table id="lista_roles" class="table table-striped table-bordered datatable" width="100%">
			                <thead>
			                	<tr>
				                    <th><spring:message code="userroles" /></th>
									<th><spring:message code="enabled" /></th>
									<th><spring:message code="addedBy" /></th>
									<th><spring:message code="dateAdded" /></th>
			                	</tr>
			                </thead>
			                <tbody>
			                	<c:forEach items="${rolesusuario}" var="rol">
								<tr>
									<spring:url value="/admin/users/disableRol/{username}/{rol}/" var="disableRolUrl">
		                               <spring:param name="username" value="${rol.authId.username}" />
		                               <spring:param name="rol" value="${rol.authId.authority}" />
		                            </spring:url>
		                            <spring:url value="/admin/users/enableRol/{username}/{rol}/" var="enableRolUrl">
		                               <spring:param name="username" value="${rol.authId.username}" />
		                               <spring:param name="rol" value="${rol.authId.authority}" />
		                            </spring:url>
									<td><spring:message code="${rol.rol.authority}" /></td>
									<c:choose>
										<c:when test="${rol.pasive=='0'.charAt(0)}">
											<td><span class="badge badge-success"><spring:message code="CAT_SINO_SI" /></span></td>
										</c:when>
										<c:otherwise>
											<td><span class="badge badge-danger"><spring:message code="CAT_SINO_NO" /></span></td>
										</c:otherwise>
									</c:choose>
									<td><c:out value="${rol.recordUser}" /></td>
									<td><c:out value="${rol.recordDate}" /></td>
								</tr>
							</c:forEach>
			                </tbody>
			            </table>
	                </div>
	              </div>
	            </div>
            </div>
            <div class="row">
	            <div class="col-md-12">
	              <div class="card">
	                <div class="card-header">
	                  <i class="icon-key"></i>&nbsp;<strong><spring:message code="access" /></strong>
	                </div>
	                <div class="card-body">
	                	<table id="lista_accesos" class="table table-striped table-bordered datatable" width="100%">
			                <thead>
			                	<tr>
				                    <th class="hidden-xs"><spring:message code="session" /></th>
									<th class="hidden-xs"><spring:message code="ipaddress" /></th>
									<th><spring:message code="logindate" /></th>
									<th><spring:message code="logoutdate" /></th>
									<th class="hidden-xs"><spring:message code="logouturl" /></th>
			                	</tr>
			                </thead>
			                <tbody>
			                <c:forEach items="${accesses}" var="acceso">
								<tr>
									<td class="hidden-xs"><c:out value="${acceso.sessionId}" /></td>
									<td class="hidden-xs"><c:out value="${acceso.remoteIpAddress}" /></td>
									<td><c:out value="${acceso.loginDate}" /></td>
									<td><c:out value="${acceso.logoutDate}" /></td>
									<td class="hidden-xs"><c:out value="${acceso.logoutRefererUrl}" /></td>
								</tr>
							</c:forEach>
			                </tbody>
			            </table>
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
  	  <div class="modal fade" id="rolesForm" data-role="rolesForm" data-backdrop="static" data-aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h2 class="modal-title"><spring:message code="add" /> <spring:message code="userroles" /></h2>
				</div>
				<div class="modal-body">
					<input type="hidden" id="inputAddRolUrl"/>
					<div id="cuerpo">
						<fieldset class="form-group">
		                 	<i class="fa fa-check"></i>
		                    <label><spring:message code="userroles" /></label>
		                    <select id="roles" name="roles" class="form-control select2-single">
		                      <c:forEach items="${roles}" var="rol">
		                      	<option value="${rol.authority}"><spring:message code="${rol.authority}" /></option>
		                      </c:forEach>
		                    </select>
		                 </fieldset>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="cancel" /></button>
					<button type="button" id="buttonAgregarRol" class="btn btn-info" onclick="ejecutarAgregarRol()"><spring:message code="ok" /></button>
				</div>
			</div>
			<!-- /.modal-content -->
	    </div>
	  <!-- /.modal-dialog -->
  	  </div>
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
  <spring:url value="/resources/vendors/js/i18n/datatables/label_{language}.json" var="dataTablesLang">
  	<spring:param name="language" value="${lenguaje}" />
  </spring:url>
  <spring:url value="/resources/vendors/js/jquery.dataTables.min.js" var="dataTablesSc" />
  <script src="${dataTablesSc}" type="text/javascript"></script>
  <spring:url value="/resources/vendors/js/dataTables.bootstrap4.min.js" var="dataTablesBsSc" />
  <script src="${dataTablesBsSc}" type="text/javascript"></script>
  <spring:url value="/resources/vendors/js/select2.min.js" var="Select2" />
  <script src="${Select2}" type="text/javascript"></script>
  <c:set var="successmessage"><spring:message code="process.success" /></c:set>
  <c:set var="errormessage"><spring:message code="process.errors" /></c:set>
  <c:set var="waitmessage"><spring:message code="process.wait" /></c:set>
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

  </script>
</body>
</html>