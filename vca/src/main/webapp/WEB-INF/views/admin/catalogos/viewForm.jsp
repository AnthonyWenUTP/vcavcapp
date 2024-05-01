<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
<jsp:include page="../../fragments/headTag.jsp" />
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
  <jsp:include page="../../fragments/bodyHeader.jsp" />
  <div class="app-body">
  	<!-- Navigation -->
  	<jsp:include page="../../fragments/sideBar.jsp" />
    <!-- Main content -->
    <main class="main">
      <!-- Breadcrumb -->
      <ol class="breadcrumb">
        <li class="breadcrumb-item"><a href="<spring:url value="/" htmlEscape="true "/>"><spring:message code="home" /></a></li>
        <li class="breadcrumb-item"><a href="<spring:url value="/admin/catalogs/" htmlEscape="true "/>"><spring:message code="seccatalogs" /></a></li>
        <li class="breadcrumb-item active"><spring:message code="seccatalogsform" /></li>
       
      </ol>
	  <!-- Container -->
      <div class="container-fluid">
      	<div class="animated fadeIn">
			<spring:url value="/admin/catalogs/saveCatalogo" var="saveCatalogoUrl"></spring:url>
  	  		<spring:url value="/admin/catalogs/" var="catalogosUrl"/>
          	<div class="row">
	            <div class="col-md-12">
	              <div class="card">
	                <div class="card-header">
	                  <i class="icon-note"></i>&nbsp;<strong><c:out value="${catalogo.messageKey}" /></strong>
	                </div>
	                <div class="card-body">
	                	<form action="#" autocomplete="off" id="edit-mensaje-form">                      
						<div class="form-group row">
	                      <label class="col-md-3 col-form-label" for="messageKey"><strong><spring:message code="ident" /></strong></label>
	                      <div class="col-md-9">
	                      	<div class="input-group">
	                      		<span class="input-group-addon"><i class="fa fa-key"></i></span>
	                        	<input type="text" id="messageKey" readonly name="messageKey" value="${catalogo.messageKey}" class="form-control" placeholder="<spring:message code="messageKey" />">
	                        	<span class="input-group-addon"><i class="fa fa-sort-alpha-asc"></i></span>
	                        </div>
	                        <span class="help-block"></span>
	                      </div>
	                    </div>
	                    <div class="form-group row">
	                      <label class="col-md-3 col-form-label" for="spanish"><strong><spring:message code="spanish" /></strong></label>
	                      <div class="col-md-9">
	                      	<div class="input-group">
	                      		<span class="input-group-addon"><i class="fa fa-flag"></i></span>
	                        	<input type="text" id="spanish" name="spanish" value="${catalogo.spanish}" class="form-control" placeholder="<spring:message code="spanish" />">
	                        	<span class="input-group-addon"><i class="fa fa-sort-alpha-asc"></i></span>
	                        </div>
	                        <span class="help-block"></span>
	                      </div>
	                    </div>
	                    <div class="form-group row">
	                      <label class="col-md-3 col-form-label" for="english"><strong><spring:message code="english" /></strong></label>
	                      <div class="col-md-9">
	                      	<div class="input-group">
	                      		<span class="input-group-addon"><i class="fa fa-flag"></i></span>
	                        	<input type="text" id="english" name="english" value="${catalogo.english}" class="form-control" placeholder="<spring:message code="english" />">
	                        	<span class="input-group-addon"><i class="fa fa-sort-alpha-asc"></i></span>
	                        </div>
	                        <span class="help-block"></span>
	                      </div>
	                    </div>
	                    <input type="text" hidden id="isCat" name="isCat" value="${catalogo.isCat}">
	                    <input type="text" hidden id="pasive" name="pasive" value="${catalogo.pasive}">
	                    <input type="text" hidden id="catRoot" name="catRoot" value="${catalogo.catRoot}">
	                    <input type="text" hidden id="catKey" name="pasive" value="${catalogo.catKey}">
	                    <input type="text" hidden id="order" name="order" value="${catalogo.order}">
                        <div class="form-group">
                          <button type="submit" class="btn btn-primary" id="guardar"><i class="fa fa-save"></i>&nbsp;<spring:message code="save" /></button>
						  <a href="${fn:escapeXml(catalogosUrl)}" class="btn btn-danger"><i class="fa fa-undo"></i>&nbsp;<spring:message code="end" /></a>
                        </div>
                      </form>
	                </div>
	              </div>
	            </div> 
	            <!--/.col-->
         	</div>
         	<div class="row">
	            <div class="col-md-12">
	              <div class="card">
	                <div class="card-header">
	                  <i class="icon-check"></i>&nbsp;<strong><spring:message code="opcionesCatalogo" /></strong>
	                </div>
	                <div class="card-body">
	                	<table id="lista_respuestas" class="table table-striped table-bordered datatable" width="100%">
			                <thead>
			                	<tr>
			                		<th><spring:message code="order" /></th>
				                    <th><spring:message code="ident" /></th>
									<th><spring:message code="spanish" /></th>
									<th><spring:message code="english" /></th>
									<th><spring:message code="catKey" /></th>
									<th><spring:message code="enabled" /></th>
									<th></th>
			                	</tr>
			                </thead>
			                <tbody>
			                	<c:forEach items="${opciones}" var="respuesta">
								<tr>
									<spring:url value="/admin/catalogs/disableRespuesta/{messageKey}/" var="disableRespuestaUrl">
		                               <spring:param name="messageKey" value="${respuesta.messageKey}" />
		                            </spring:url>
		                            <spring:url value="/admin/catalogs/enableRespuesta/{messageKey}/" var="enableRespuestaUrl">
		                               <spring:param name="messageKey" value="${respuesta.messageKey}" />
		                            </spring:url>
									<td><c:out value="${respuesta.order}" /></td>
									<td><c:out value="${respuesta.messageKey}" /></td>
									<td><c:out value="${respuesta.spanish}" /></td>
									<td><c:out value="${respuesta.english}" /></td>
									<td><c:out value="${respuesta.catKey}" /></td>
									<c:choose>
										<c:when test="${respuesta.pasive=='0'.charAt(0)}">
											<td><span class="badge badge-success"><spring:message code="CAT_SINO_SI" /></span></td>
										</c:when>
										<c:otherwise>
											<td><span class="badge badge-danger"><spring:message code="CAT_SINO_NO" /></span></td>
										</c:otherwise>
									</c:choose>
									<c:choose>
										<c:when test="${respuesta.pasive=='0'.charAt(0)}">
											<td><a href="#" data-toggle="modal" data-whatever="${fn:escapeXml(disableRespuestaUrl)}" class="btn btn-outline-primary btn-sm desact"><i class="fa fa-trash-o"></i></a></td>
										</c:when>
										<c:otherwise>
											<td><a href="#" data-toggle="modal" data-whatever="${fn:escapeXml(enableRespuestaUrl)}" class="btn btn-outline-primary btn-sm act"><i class="fa fa-check"></i></a></td>
										</c:otherwise>
									</c:choose>
								</tr>
							</c:forEach>
			                </tbody>
			            </table>
	                </div>
	                <div class="card-header">
	                    <div class="row float-right mr-4" >
			            	<spring:url value="/admin/catalogs/addRespuesta/{messageKey}/" var="addRespuestaUrl"><spring:param name="messageKey" value="${catalogo.messageKey}" /></spring:url>
			            	<a href="${fn:escapeXml(addRespuestaUrl)}" class="btn btn-primary"><i class="fa fa-plus"></i>&nbsp;<spring:message code="add" /></a>
			            </div>
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
  	  
  	  </div>
    </main>
    
  </div>
  <!-- Pie de p�gina -->
  <jsp:include page="../../fragments/bodyFooter.jsp" />

  <!-- Bootstrap and necessary plugins -->
  <jsp:include page="../../fragments/corePlugins.jsp" />
  <jsp:include page="../../fragments/bodyUtils.jsp" />

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
  <c:set var="habilitar"><spring:message code="enable" /></c:set>
  <c:set var="deshabilitar"><spring:message code="disable" /></c:set>
  <c:set var="confirmar"><spring:message code="confirm" /></c:set>
  
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

	  $.validator.setDefaults( {
		    submitHandler: function () {
		    	processMensaje();
		    }
		  } );	
	  $( '#edit-mensaje-form' ).validate( {
	    rules: {
	      spanish: {
	    	  minlength: 1,
	          maxlength: 255,
	          required: true
	      },
	      english: {
	    	  minlength: 1,
	          maxlength: 255,
	          required: true
	      }
	    },
	    errorElement: 'em',
	    errorPlacement: function ( error, element ) {
	      // Add the `help-block` class to the error element
	      error.addClass( 'form-control-feedback' );
	      if ( element.prop( 'type' ) === 'checkbox' ) {
	        error.insertAfter( element.parent( 'label' ) );
	      } else {
	        error.insertAfter( element );
	      }
	    },
	    highlight: function ( element, errorClass, validClass ) {
	      $( element ).addClass( 'form-control-danger alert-danger' ).removeClass( 'form-control-success' );
	      $( element ).parents( '.form-group' ).addClass( 'alert-danger' ).removeClass( 'has-success' );
	    },
	    unhighlight: function (element, errorClass, validClass) {
	      $( element ).addClass( 'form-control-success' ).removeClass( 'form-control-danger alert-danger' );
	      $( element ).parents( '.form-group' ).addClass( 'has-success' ).removeClass( 'alert-danger' );
	    }
	  });		

	  function processMensaje(){
		  $.blockUI({ message: "${waitmessage}" });
		    $.post( "${saveCatalogoUrl}"
		            , $( '#edit-mensaje-form' ).serialize()
		            , function( data )
		            {
		    			mensaje = JSON.parse(data);
		    			if (mensaje.messageKey === undefined) {
		    				toastr.error(data, "${errormessage}", {
		    					    closeButton: true,
		    					    progressBar: true,
		    					  });
		    				$.unblockUI();
						}
						else{
							$.blockUI({ message: "${successmessage}"});
							$('#messageKey').val(mensaje.messageKey); 
							$.unblockUI();
						}
		            }
		            , 'text' )
			  		.fail(function(XMLHttpRequest, textStatus, errorThrown) {
			    		alert( "error:" + errorThrown);
			    		$.unblockUI();
			  		});
		}	
	});

    $(".act").click(function(){ 
		var titHab = $(this).data('whatever').substr(0,$(this).data('whatever').length-1);
		$('#accionUrl').val($(this).data('whatever'));
    	$('#titulo').html('<h2 class="modal-title">'+"${confirmar}"+'</h2>');
    	$('#cuerpo').html('<h3>'+"${habilitar}"+' '+ titHab.substr(titHab.lastIndexOf("/")+1) +'?</h3>');
    	$('#basic').modal('show');
    });
    
    $(".desact").click(function(){ 
        var titDes = $(this).data('whatever').substr(0,$(this).data('whatever').length-1);
    	$('#accionUrl').val($(this).data('whatever'));
    	$('#titulo').html('<h2 class="modal-title">'+"${confirmar}"+'</h2>');
    	$('#cuerpo').html('<h3>'+"${deshabilitar}"+ ' ' + titDes.substr(titDes.lastIndexOf("/")+1) +'?</h3>');
    	$('#basic').modal('show');
    });	

    function ejecutarAccion() {
		window.location.href = $('#accionUrl').val();		
	}
	
  </script>
</body>
</html>