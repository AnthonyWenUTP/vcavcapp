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
	  <spring:url value="/irs/supervision/saveEntity/" var="saveUrl"></spring:url>
	  <spring:url value="/irs/supervision/{ident}/" var="supervisionUrl">
		<spring:param name="ident" value="${supervision.ident}" />
	  </spring:url>
  	  
      <!-- Breadcrumb -->
      <ol class="breadcrumb">
        <li class="breadcrumb-item"><a href="<spring:url value="/" htmlEscape="true "/>"><spring:message code="home" /></a></li>
        <li class="breadcrumb-item active"><a href="<spring:url value="/irs/dashboard/" htmlEscape="true "/>"><spring:message code="irs" /></a></li>
        <li class="breadcrumb-item"><a href="<spring:url value="/irs/supervision/" htmlEscape="true "/>"><spring:message code="supervision" /></a></li>
        <li class="breadcrumb-item active"><c:out value="${supervision.target.household.code}" /></li>
        
      </ol>
	  <!-- Container -->
      <div class="container-fluid">

        <div class="animated fadeIn">
          <div class="row">
            <div class="col-md-12">
              <div class="card">
                <div class="card-header">
                  <i class="icon-note"></i> <spring:message code="edit" /> <spring:message code="supervision" />
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
	                        <input type="text" id="ident" name="ident" readonly value="${supervision.ident}" class="form-control" placeholder="<spring:message code="ident" />">
	                      </div>
	                    </div>	
	                    
	                    <div class="form-group" hidden>
						  <label><spring:message code="target" /></label>	
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-key"></i></span>
	                        <input type="text" id="target" name="target" readonly value="${supervision.target.ident}" class="form-control" placeholder="<spring:message code="target" />">
	                      </div>
	                    </div>
			            
	                    <div class="form-group">
	                      <label><spring:message code="supervisionDate" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
	                        <fmt:formatDate value="${supervision.supervisionDate}" var="fecSup" pattern="dd/MM/yyyy" />
	                        <input type="text" id="supervisionDate" name="supervisionDate" value="${fecSup}" class="form-control date-picker" data-date-format="dd/mm/yyyy">
	                      </div>
	                    </div>  
	                    
	                   <div class="form-group">
	                      <label><spring:message code="sprayer" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="icon-note"></i></span>
	                        <select name="rociador" id="rociador" class="form-control select2-single">
	                        	<option value=""><spring:message code="sprayer" /> - <spring:message code="empty" /></option>
	                        	<c:forEach items="${rociadores}" var="rociador">
									<c:choose> 
										<c:when test="${rociador.ident eq supervision.rociador.ident}">
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
										<c:when test="${supervisor.ident eq supervision.supervisor.ident}">
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
	                      <label><spring:message code="usoEqProt" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-pencil"></i></span>
	                        <select name="usoEqProt" id="usoEqProt" class="form-control select2-single">
	                        	<c:forEach items="${sinos}" var="sino">
	                        		<option value=""><spring:message code="empty" /></option>
									<c:choose> 
										<c:when test="${sino.catKey eq supervision.usoEqProt}">
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
	                      <label><spring:message code="eqProtBien" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-pencil"></i></span>
	                        <select name="eqProtBien" id="eqProtBien" class="form-control select2-single">
	                        	<option value=""><spring:message code="empty" /></option>
	                        	<c:forEach items="${sinos}" var="sino">
									<c:choose> 
										<c:when test="${sino.catKey eq supervision.eqProtBien}">
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
	                      <label><spring:message code="numIden" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-pencil"></i></span>
	                        <select name="numIden" id="numIden" class="form-control select2-single">
	                        	<c:forEach items="${sinos}" var="sino">
	                        		<option value=""><spring:message code="empty" /></option>
									<c:choose> 
										<c:when test="${sino.catKey eq supervision.numIden}">
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
	                      <label><spring:message code="aguaOp" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-pencil"></i></span>
	                        <select name="aguaOp" id="aguaOp" class="form-control select2-single">
	                        	<option value=""><spring:message code="empty" /></option>
	                        	<c:forEach items="${sinos}" var="sino">
									<c:choose> 
										<c:when test="${sino.catKey eq supervision.aguaOp}">
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
	                      <label><spring:message code="prepViv" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-pencil"></i></span>
	                        <select name="prepViv" id="prepViv" class="form-control select2-single">
	                        	<option value=""><spring:message code="empty" /></option>
	                        	<c:forEach items="${sinos}" var="sino">
									<c:choose> 
										<c:when test="${sino.catKey eq supervision.prepViv}">
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
	                      <label><spring:message code="coopPrepViv" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-pencil"></i></span>
	                        <select name="coopPrepViv" id="coopPrepViv" class="form-control select2-single">
	                        	<option value=""><spring:message code="empty" /></option>
	                        	<c:forEach items="${sinos}" var="sino">
									<c:choose> 
										<c:when test="${sino.catKey eq supervision.coopPrepViv}">
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
	                      <label><spring:message code="mezcla" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-pencil"></i></span>
	                        <select name="mezcla" id="mezcla" class="form-control select2-single">
	                        	<option value=""><spring:message code="empty" /></option>
	                        	<c:forEach items="${sinos}" var="sino">
									<c:choose> 
										<c:when test="${sino.catKey eq supervision.mezcla}">
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
	                      <label><spring:message code="aguaAdec" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-pencil"></i></span>
	                        <select name="aguaAdec" id="aguaAdec" class="form-control select2-single">
	                        	<option value=""><spring:message code="empty" /></option>
	                        	<c:forEach items="${sinos}" var="sino">
									<c:choose> 
										<c:when test="${sino.catKey eq supervision.aguaAdec}">
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
	                      <label><spring:message code="mezclaPrep" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-pencil"></i></span>
	                        <select name="mezclaPrep" id="mezclaPrep" class="form-control select2-single">
	                        	<option value=""><spring:message code="empty" /></option>
	                        	<c:forEach items="${sinos}" var="sino">
									<c:choose> 
										<c:when test="${sino.catKey eq supervision.mezclaPrep}">
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
	                      <label><spring:message code="agitaBomba" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-pencil"></i></span>
	                        <select name="agitaBomba" id="agitaBomba" class="form-control select2-single">
	                        	<option value=""><spring:message code="empty" /></option>
	                        	<c:forEach items="${sinos}" var="sino">
									<c:choose> 
										<c:when test="${sino.catKey eq supervision.agitaBomba}">
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
	                      <label><spring:message code="bombaCerrada" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-pencil"></i></span>
	                        <select name="bombaCerrada" id="bombaCerrada" class="form-control select2-single">
	                        	<option value=""><spring:message code="empty" /></option>
	                        	<c:forEach items="${sinos}" var="sino">
									<c:choose> 
										<c:when test="${sino.catKey eq supervision.bombaCerrada}">
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
	                      <label><spring:message code="bombaPresion" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-pencil"></i></span>
	                        <select name="bombaPresion" id="bombaPresion" class="form-control select2-single">
	                        	<option value=""><spring:message code="empty" /></option>
	                        	<c:forEach items="${sinos}" var="sino">
									<c:choose> 
										<c:when test="${sino.catKey eq supervision.bombaPresion}">
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
	                      <label><spring:message code="compruebaBomba" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-pencil"></i></span>
	                        <select name="compruebaBomba" id="compruebaBomba" class="form-control select2-single">
	                        	<option value=""><spring:message code="empty" /></option>
	                        	<c:forEach items="${sinos}" var="sino">
									<c:choose> 
										<c:when test="${sino.catKey eq supervision.compruebaBomba}">
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
	                      <label><spring:message code="colocApropiada" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-pencil"></i></span>
	                        <select name="colocApropiada" id="colocApropiada" class="form-control select2-single">
	                        	<option value=""><spring:message code="empty" /></option>
	                        	<c:forEach items="${sinos}" var="sino">
									<c:choose> 
										<c:when test="${sino.catKey eq supervision.colocApropiada}">
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
	                      <label><spring:message code="distApropiada" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-pencil"></i></span>
	                        <select name="distApropiada" id="distApropiada" class="form-control select2-single">
	                        	<option value=""><spring:message code="empty" /></option>
	                        	<c:forEach items="${sinos}" var="sino">
									<c:choose> 
										<c:when test="${sino.catKey eq supervision.distApropiada}">
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
	                      <label><spring:message code="distBoquilla" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-pencil"></i></span>
	                        <select name="distBoquilla" id="distBoquilla" class="form-control select2-single">
	                        	<option value=""><spring:message code="empty" /></option>
	                        	<c:forEach items="${sinos}" var="sino">
									<c:choose> 
										<c:when test="${sino.catKey eq supervision.distBoquilla}">
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
	                      <label><spring:message code="pasoFrente" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-pencil"></i></span>
	                        <select name="pasoFrente" id="pasoFrente" class="form-control select2-single">
	                        	<option value=""><spring:message code="empty" /></option>
	                        	<c:forEach items="${sinos}" var="sino">
									<c:choose> 
										<c:when test="${sino.catKey eq supervision.pasoFrente}">
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
	                      <label><spring:message code="mantRitmo" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-pencil"></i></span>
	                        <select name="mantRitmo" id="mantRitmo" class="form-control select2-single">
	                        	<option value=""><spring:message code="empty" /></option>
	                        	<c:forEach items="${sinos}" var="sino">
									<c:choose> 
										<c:when test="${sino.catKey eq supervision.mantRitmo}">
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
	                      <label><spring:message code="metConteo" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-pencil"></i></span>
	                        <select name="metConteo" id="metConteo" class="form-control select2-single">
	                        	<option value=""><spring:message code="empty" /></option>
	                        	<c:forEach items="${sinos}" var="sino">
									<c:choose> 
										<c:when test="${sino.catKey eq supervision.metConteo}">
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
	                      <label><spring:message code="velocSuperficies" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-pencil"></i></span>
	                        <select name="velocSuperficies" id="velocSuperficies" class="form-control select2-single">
	                        	<option value=""><spring:message code="empty" /></option>
	                        	<c:forEach items="${sinos}" var="sino">
									<c:choose> 
										<c:when test="${sino.catKey eq supervision.velocSuperficies}">
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
	                      <label><spring:message code="supFajas" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-pencil"></i></span>
	                        <select name="supFajas" id="supFajas" class="form-control select2-single">
	                        	<option value=""><spring:message code="empty" /></option>
	                        	<c:forEach items="${sinos}" var="sino">
									<c:choose> 
										<c:when test="${sino.catKey eq supervision.supFajas}">
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
	                      <label><spring:message code="pasosLaterales" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-pencil"></i></span>
	                        <select name="pasosLaterales" id="pasosLaterales" class="form-control select2-single">
	                        	<option value=""><spring:message code="empty" /></option>
	                        	<c:forEach items="${sinos}" var="sino">
									<c:choose> 
										<c:when test="${sino.catKey eq supervision.pasosLaterales}">
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
	                      <label><spring:message code="salvarObstaculos" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-pencil"></i></span>
	                        <select name="salvarObstaculos" id="salvarObstaculos" class="form-control select2-single">
	                        	<option value=""><spring:message code="empty" /></option>
	                        	<c:forEach items="${sinos}" var="sino">
									<c:choose> 
										<c:when test="${sino.catKey eq supervision.salvarObstaculos}">
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
	                      <label><spring:message code="bienRociado" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-pencil"></i></span>
	                        <select name="bienRociado" id="bienRociado" class="form-control select2-single">
	                        	<option value=""><spring:message code="empty" /></option>
	                        	<c:forEach items="${sinos}" var="sino">
									<c:choose> 
										<c:when test="${sino.catKey eq supervision.bienRociado}">
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
	                      <label><spring:message code="supInvertidas" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-pencil"></i></span>
	                        <select name="supInvertidas" id="supInvertidas" class="form-control select2-single">
	                        	<option value=""><spring:message code="empty" /></option>
	                        	<c:forEach items="${sinos}" var="sino">
									<c:choose> 
										<c:when test="${sino.catKey eq supervision.supInvertidas}">
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
	                      <label><spring:message code="objPiso" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-pencil"></i></span>
	                        <select name="objPiso" id="objPiso" class="form-control select2-single">
	                        	<option value=""><spring:message code="empty" /></option>
	                        	<c:forEach items="${sinos}" var="sino">
									<c:choose> 
										<c:when test="${sino.catKey eq supervision.objPiso}">
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
	                      <label><spring:message code="reportaConsumoAprop" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-pencil"></i></span>
	                        <select name="reportaConsumoAprop" id="reportaConsumoAprop" class="form-control select2-single">
	                        	<option value=""><spring:message code="empty" /></option>
	                        	<c:forEach items="${sinos}" var="sino">
									<c:choose> 
										<c:when test="${sino.catKey eq supervision.reportaConsumoAprop}">
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
	                      <label><spring:message code="transEqAprop" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-pencil"></i></span>
	                        <select name="transEqAprop" id="transEqAprop" class="form-control select2-single">
	                        	<option value=""><spring:message code="empty" /></option>
	                        	<c:forEach items="${sinos}" var="sino">
									<c:choose> 
										<c:when test="${sino.catKey eq supervision.transEqAprop}">
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
	                      <label><spring:message code="eqCompleto" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-pencil"></i></span>
	                        <select name="eqCompleto" id="eqCompleto" class="form-control select2-single">
	                        	<option value=""><spring:message code="empty" /></option>
	                        	<c:forEach items="${sinos}" var="sino">
									<c:choose> 
										<c:when test="${sino.catKey eq supervision.eqCompleto}">
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
	                      <label><spring:message code="cuidaMatEq" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-pencil"></i></span>
	                        <select name="cuidaMatEq" id="cuidaMatEq" class="form-control select2-single">
	                        	<option value=""><spring:message code="empty" /></option>
	                        	<c:forEach items="${sinos}" var="sino">
									<c:choose> 
										<c:when test="${sino.catKey eq supervision.cuidaMatEq}">
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
	                      <label><spring:message code="buenAspPersonal" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-pencil"></i></span>
	                        <select name="buenAspPersonal" id="buenAspPersonal" class="form-control select2-single">
	                        	<option value=""><spring:message code="empty" /></option>
	                        	<c:forEach items="${sinos}" var="sino">
									<c:choose> 
										<c:when test="${sino.catKey eq supervision.buenAspPersonal}">
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
	                      <label><spring:message code="cumpleInstrucciones" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-pencil"></i></span>
	                        <select name="cumpleInstrucciones" id="cumpleInstrucciones" class="form-control select2-single">
	                        	<option value=""><spring:message code="empty" /></option>
	                        	<c:forEach items="${sinos}" var="sino">
									<c:choose> 
										<c:when test="${sino.catKey eq supervision.cumpleInstrucciones}">
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
	                      <label><spring:message code="aceptaSuperv" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-pencil"></i></span>
	                        <select name="aceptaSuperv" id="aceptaSuperv" class="form-control select2-single">
	                        	<option value=""><spring:message code="empty" /></option>
	                        	<c:forEach items="${sinos}" var="sino">
									<c:choose> 
										<c:when test="${sino.catKey eq supervision.aceptaSuperv}">
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
	                      <label><spring:message code="respetuoso" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-pencil"></i></span>
	                        <select name="respetuoso" id="respetuoso" class="form-control select2-single">
	                        	<option value=""><spring:message code="empty" /></option>
	                        	<c:forEach items="${sinos}" var="sino">
									<c:choose> 
										<c:when test="${sino.catKey eq supervision.respetuoso}">
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
	                      <label><spring:message code="camp" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-pencil"></i></span>
	                        <select name="camp" id="camp" class="form-control select2-single">
	                        	<option value=""><spring:message code="empty" /></option>
	                        	<c:forEach items="${sinos}" var="sino">
									<c:choose> 
										<c:when test="${sino.catKey eq supervision.camp}">
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
	                      <label><spring:message code="obs" /></label>
	                      <div class="input-group">
	                        <span class="input-group-addon"><i class="fa fa-sticky-note-o"></i></span>
	                        <input type="text" id="obs" name="obs" value="${supervision.obs}" class="form-control" placeholder="<spring:message code="obs" />">
	                      </div>
	                    </div>
	                    
	                    
                        <div class="form-group">
                          <button type="submit" class="btn btn-primary" id="guardar"><i class="fa fa-save"></i>&nbsp;<spring:message code="save" /></button>
						  <a href="${fn:escapeXml(supervisionUrl)}" class="btn btn-danger"><i class="fa fa-undo"></i>&nbsp;<spring:message code="cancel" /></a>
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
  <spring:url value="/resources/js/views/Supervision.js" var="processEntity" />
  <script src="${processEntity}"></script>
  
<c:set var="successmessage"><spring:message code="process.success" /></c:set>
<c:set var="errormessage"><spring:message code="process.errors" /></c:set>
<c:set var="waitmessage"><spring:message code="process.wait" /></c:set>

<script>
	jQuery(document).ready(function() {
		var parametros = {saveUrl: "${saveUrl}", successmessage: "${successmessage}",
				errormessage: "${errormessage}",waitmessage: "${waitmessage}",
				supervisionUrl: "${supervisionUrl}" 
		};
		ProcessEntity.init(parametros);
	});
	
</script>
  
</body>
</html>