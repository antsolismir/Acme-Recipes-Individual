<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- examen --%>
<acme:list>
	<acme:list-column code="chef.pimpam.list.code" path="code" width="10%"/>
	<acme:list-column code="chef.pimpam.list.instantiationMoment" path="instantiationMoment" width="10%"/>
	<acme:list-column code="chef.pimpam.list.title" path="title" width="10%"/>
	<acme:list-column code="chef.pimpam.list.budget" path="budget" width="10%"/>
</acme:list>
<acme:button code="chef.pimpam.list.create" action="/chef/pimpam/create"/>