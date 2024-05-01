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
<spring:url value="/resources/vendors/css/responsive.dataTables.min.css" var="dataTablesResponsiveCSS" />
<link href="${dataTablesResponsiveCSS}" rel="stylesheet" type="text/css"/>
<spring:url value="/resources/vendors/css/buttons.dataTables.min.css" var="dataTablesButtonCSS" />
<link href="${dataTablesButtonCSS}" rel="stylesheet" type="text/css"/>

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
        <li class="breadcrumb-item active"><spring:message code="tests" /></li>
        
      </ol>
	  <!-- Container -->
	  <div class="container-fluid">
      <div class="animated fadeIn">
          <div class="card">
            <div class="card-header">
              <i class="fa fa-list"></i> <spring:message code="tests" />
              <div class="card-actions">
              </div>
            </div>
            <div class="card-body">
              <spring:url value="/admin/tests/newEntity/"	var="newEntity"/>
              <spring:url value="/admin/tests/map/"	var="viewMap"/>
              <button id="lista_tests_new" onclick="location.href='${fn:escapeXml(newEntity)}'" type="button" class="btn btn-outline-primary"><i class="fa fa-plus"></i>&nbsp; <spring:message code="add" /></button>
              <div class="row float-right mr-4" >
              	<button id="tests_map" onclick="location.href='${fn:escapeXml(viewMap)}'" type="button" class="btn btn-outline-primary"><i class="icon-globe"></i>&nbsp; <spring:message code="map" /></button>
              </div>
              
              <br><br><br><br>	
              <table id="lista_tests" class="table table-striped table-bordered datatable" width="100%">
                <thead>
                	<tr>
                		<th></th>
                		<th class="hidden-xs"><spring:message code="housecase" /></th>
	                    <th class="hidden-xs"><spring:message code="locality" /></th>
	                    <th class="hidden-xs"><spring:message code="mxDate" /></th>
	                    <th class="hidden-xs"><spring:message code="mxProactiva" /></th>
	                    <th class="hidden-xs"><spring:message code="mxReactiva" /></th>
	                    <th><spring:message code="enabled" /></th>
	                    <th><spring:message code="createdBy" /></th>
	                    <th><spring:message code="dateCreated" /></th>
                	</tr>
                </thead>
                <tbody>
                	<c:forEach items="${muestras}" var="muestra">
                		<fmt:formatDate value="${muestra.mxDate}" var="fecmuestra" pattern="yyyy-MM-dd" />
                		<tr>
                			<spring:url value="/admin/tests/{id}/"
                                        var="idUrl">
                                <spring:param name="id" value="${muestra.ident}" />
                            </spring:url>
                            <spring:url value="/admin/tests/editEntity/{id}/"
                                        var="editUrl">
                                <spring:param name="id" value="${muestra.ident}" />
                            </spring:url>
                            <td><a href="${fn:escapeXml(editUrl)}" class="btn btn-outline-primary btn-sm"><i class="fa fa-pencil"></i></a></td>
                            <td><a href="${fn:escapeXml(idUrl)}"><c:out value="${muestra.casa}" /></a></td>
                            <td class="hidden-xs"><c:out value="${muestra.local.name}" /></td>
                            <td class="hidden-xs"><c:out value="${fecmuestra}" /></td>
                            <td class="hidden-xs"><c:out value="${muestra.mxProactiva}" /></td>
                            <td class="hidden-xs"><c:out value="${muestra.mxReactiva}" /></td>
                            <c:choose>
                                <c:when test="${muestra.pasive eq '0'.charAt(0)}">
                                    <td><span class="badge badge-success"><spring:message code="CAT_SINO_SI" /></span></td>
                                </c:when>
                                <c:otherwise>
                                    <td><span class="badge badge-danger"><spring:message code="CAT_SINO_NO" /></span></td>
                                </c:otherwise>
                            </c:choose>
                            <td><c:out value="${muestra.recordUser}" /></td>
                            <td><c:out value="${muestra.recordDate}" /></td>
                		</tr>
                	</c:forEach>
                </tbody>
              </table>
            </div>
          </div>
        </div>
        </div>
      <!-- /.container-fluid -->
    </main>
    
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
  <spring:url value="/resources/vendors/js/i18n/datatables/label_{language}.json" var="dataTablesLang">
  	<spring:param name="language" value="${lenguaje}" />
  </spring:url>
  
  <!-- Plugins and scripts required by this views -->
  <spring:url value="/resources/vendors/js/jquery.dataTables.min.js" var="dataTablesSc" />
  <script src="${dataTablesSc}" type="text/javascript"></script>
  <spring:url value="/resources/vendors/js/dataTables.bootstrap4.min.js" var="dataTablesBsSc" />
  <script src="${dataTablesBsSc}" type="text/javascript"></script>
  <spring:url value="/resources/vendors/js/dataTables.responsive.min.js" var="dataTablesResponsive" />
  <script src="${dataTablesResponsive}" type="text/javascript"></script>
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
  
  <!-- Custom scripts required by this view -->
  <script>
  	$(function(){
	  $('.datatable').DataTable({
		  dom: 'lBfrtip',
          "oLanguage": {
              "sUrl": "${dataTablesLang}"
          },
          "responsive": true,
          "buttons": [
              {
                  extend: 'excel'
              },
              {
                  extend: 'pdfHtml5',
                  orientation: 'portrait',
                  pageSize: 'LETTER'
              }
          ]
      });
	  $('.datatable').attr('style', 'border-collapse: collapse !important');
	});
  </script>
</body>
</html>