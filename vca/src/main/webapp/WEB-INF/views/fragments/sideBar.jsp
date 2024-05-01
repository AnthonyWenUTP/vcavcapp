<%@ page contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>
<div class="sidebar">
    <nav class="sidebar-nav">
        <ul class="nav">
            <li class="nav-item">
                <a class="nav-link" href="<spring:url value="/" htmlEscape="true "/>"><i class="icon-home"></i><spring:message code="home" /></a>
            </li>
            <sec:authorize url="/census/">
            <li class="nav-item censo">
                <a class="nav-link" href="<spring:url value="/census/" htmlEscape="true "/>"><i class="fa fa-database"></i><spring:message code="census" /></a>
            </li>
            </sec:authorize>
            
            <sec:authorize url="/irs/">
	        <li class="nav-item nav-dropdown lenguaje">
	            <a class="nav-link nav-dropdown-toggle" href="#"><i class="fa fa-shower"></i><spring:message code="irs" /></a>
	            <ul class="nav-dropdown-items">
	            	<li class="nav-item">
	                    <a class="nav-link" href="<spring:url value="/irs/dashboard/" htmlEscape="true "/>"><i class="fa fa-signal"></i><spring:message code="dashboard" /></a>
	                </li>
	                <sec:authorize url="/irs/season/">
	                <li class="nav-item">
	                    <a class="nav-link" href="<spring:url value="/irs/season/" htmlEscape="true "/>"><i class="fa fa-calendar-plus-o"></i><spring:message code="season" /></a>
	                </li>
	                <li class="nav-item">
	                    <a class="nav-link" href="<spring:url value="/irs/season/targets/" htmlEscape="true "/>"><i class="fa fa-calendar-times-o"></i><spring:message code="targets" /></a>
	                </li>
	                </sec:authorize>
	                <sec:authorize url="/irs/spray/">
	                <li class="nav-item">
	                    <a class="nav-link" href="<spring:url value="/irs/visit/" htmlEscape="true "/>"><i class="fa fa-shield"></i><spring:message code="visits" /></a>
	                </li>
	                </sec:authorize>
	                <sec:authorize url="/irs/superv/">
	                <li class="nav-item">
	                    <a class="nav-link" href="<spring:url value="/irs/supervision/" htmlEscape="true "/>"><i class="fa fa-wpexplorer"></i><spring:message code="supervision" /></a>
	                </li>
	                </sec:authorize>
	                <li class="nav-item">
	                    <a class="nav-link" href="<spring:url value="/irs/consumo/" htmlEscape="true "/>"><i class="fa fa-calculator"></i>Consumo</a>
	                </li>
	            </ul>
	        </li>
	        </sec:authorize>
	        
	        
	        <li class="nav-item">
                <a class="nav-link" href="<spring:url value="/maps/dashboard/" htmlEscape="true "/>"><i class="icon-globe"></i><spring:message code="mapsmod" /></a>
            </li>
                    
	        
	        
            
            <sec:authorize url="/admin/">
            <li class="nav-item nav-dropdown administracion">
	            <a class="nav-link nav-dropdown-toggle" href="#"><i class="icon-settings"></i><spring:message code="admin" /></a>
	            <ul class="nav-dropdown-items">
	            	<li class="nav-item">
	                    <a class="nav-link" href="<spring:url value="/admin/users/" htmlEscape="true "/>"><i class="icon-people"></i><spring:message code="users" /></a>
	                </li>
					<li class="nav-item nav-dropdown">
					  <a class="nav-link nav-dropdown-toggle" href="#"><i class="icon-drawer"></i></i><spring:message code="orgunit" /></a>
					  <ul class="nav-dropdown-items">
					   <li class="nav-item">
					       <a class="nav-link" href="<spring:url value="/admin/areas/" htmlEscape="true "/>"><i class="icon-map"></i><spring:message code="areas" /></a>
					   </li>
					   <li class="nav-item">
					       <a class="nav-link" href="<spring:url value="/admin/districts/" htmlEscape="true "/>"><i class="icon-layers"></i><spring:message code="districts" /></a>
					   </li>
					   <li class="nav-item">
					       <a class="nav-link" href="<spring:url value="/admin/localities/" htmlEscape="true "/>"><i class="icon-compass"></i><spring:message code="localities" /></a>
					   </li>
					   <li class="nav-item">
					       <a class="nav-link" href="<spring:url value="/admin/foci/" htmlEscape="true "/>"><i class="icon-graph"></i><spring:message code="foci" /></a>
					   </li>
					  </ul>
					</li>
					<li class="nav-item nav-dropdown puntos">
			            <a class="nav-link nav-dropdown-toggle" href="#"><i class="icon-map"></i><spring:message code="maps" /></a>
			            <ul class="nav-dropdown-items">
			            	<li class="nav-item">
			                    <a class="nav-link" href="<spring:url value="/admin/puntos/" htmlEscape="true "/>"><i class="fa fa-medkit"></i><spring:message code="pdxs" /></a>
			                </li>
							<li class="nav-item">
			                    <a class="nav-link" href="<spring:url value="/admin/criaderos/" htmlEscape="true "/>"><i class="fa fa-bug"></i><spring:message code="criaderos" /></a>
			                </li>
			                <li class="nav-item">
			                    <a class="nav-link" href="<spring:url value="/admin/casos/" htmlEscape="true "/>"><i class="fa fa-list"></i><spring:message code="cases" /></a>
			                </li>
			                <li class="nav-item">
			                    <a class="nav-link" href="<spring:url value="/admin/tests/" htmlEscape="true "/>"><i class="fa fa-tag"></i><spring:message code="tests" /></a>
			                </li>
			            </ul>
			        </li>
					<li class="nav-item nav-dropdown">
					  <a class="nav-link nav-dropdown-toggle" href="#"><i class="icon-puzzle"></i></i><spring:message code="sysopts" /></a>
					  <ul class="nav-dropdown-items">
					  	<li class="nav-item">
		                    <a class="nav-link" href="<spring:url value="/admin/catalogs/" htmlEscape="true "/>"><i class="icon-book-open"></i><spring:message code="seccatalogs" /></a>
		                </li>
		                <li class="nav-item">
		                    <a class="nav-link" href="<spring:url value="/admin/parameters/" htmlEscape="true "/>"><i class="icon-cursor"></i><spring:message code="parameter" /></a>
		                </li>
		                <li class="nav-item">
		                    <a class="nav-link" href="<spring:url value="/admin/translations/" htmlEscape="true "/>"><i class="icon-globe"></i><spring:message code="translation" /></a>
		                </li>
					  </ul>
					</li>
					<li class="nav-item nav-dropdown">
					  <a class="nav-link nav-dropdown-toggle" href="#"><i class="icon-user-following"></i></i><spring:message code="resources" /></a>
					  <ul class="nav-dropdown-items">
					  	<li class="nav-item">
		                    <a class="nav-link" href="<spring:url value="/admin/censustakers/" htmlEscape="true "/>"><i class="icon-notebook"></i><spring:message code="censustaker" /></a>
		                </li>
		                <li class="nav-item">
		                    <a class="nav-link" href="<spring:url value="/admin/personal/" htmlEscape="true "/>"><i class="fa fa-fire-extinguisher"></i><spring:message code="personnel" /></a>
		                </li>
		                <li class="nav-item">
		                    <a class="nav-link" href="<spring:url value="/admin/brigades/" htmlEscape="true "/>"><i class="fa fa-truck"></i><spring:message code="brigades" /></a>
		                </li>
					  </ul>
					</li>
	            </ul>
	        </li>
	        </sec:authorize>

	        
	        <sec:authorize url="/qc/">
	        <li class="nav-item nav-dropdown lenguaje">
	            <a class="nav-link nav-dropdown-toggle" href="#"><i class="fa fa-check"></i><spring:message code="qc" /></a>
	            <ul class="nav-dropdown-items">
	            	<li class="nav-item">
	                    <a class="nav-link" href="<spring:url value="/qc/duplicates/" htmlEscape="true "/>"><i class="fa fa-clone"></i><spring:message code="qc_duplicated_houses" /></a>
	                </li>
	            </ul>
	        </li>
	        </sec:authorize>
	        
            <li class="nav-item nav-dropdown lenguaje">
	            <a class="nav-link nav-dropdown-toggle" href="#"><i class="fa fa-language"></i><spring:message code="language" /></a>
	            <ul class="nav-dropdown-items">
	                <li class="nav-item">
	                    <a class="nav-link" href="?lang=en"><i class="fa fa-flag"></i>English</a>
	                    <a class="nav-link" href="?lang=es"><i class="icon-flag"></i>Español</a>
	                </li>
	            </ul>
	        </li>
	        
	        <li class="nav-item help">
                <a class="nav-link" href="<spring:url value="/help/" htmlEscape="true "/>"><i class="fa fa-question"></i><spring:message code="help" /></a>
            </li>
	        
	        <li class="nav-item">
                <a class="nav-link" href="<spring:url value="/logout" htmlEscape="true" />"><i class="icon-logout"></i><spring:message code="logout" /></a>
            </li>
            
        </ul>
    </nav>
    <button class="sidebar-minimizer brand-minimizer" type="button"></button>
</div>