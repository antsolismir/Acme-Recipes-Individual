<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- examen --%>
<acme:list>
	<acme:list-column code="chef.quotela.list.code" path="code" width="10%"/>
	<acme:list-column code="chef.quotela.list.instantiationMoment" path="instantiationMoment" width="10%"/>
	<acme:list-column code="chef.quotela.list.title" path="name" width="10%"/>
	<acme:list-column code="chef.quotela.list.budget" path="share" width="10%"/>
</acme:list>
<acme:button code="chef.quotela.list.create" action="/chef/quotelas/create"/>