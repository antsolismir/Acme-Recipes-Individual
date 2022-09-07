<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<acme:list>
	<acme:list-column code="Code" path="code" width="10%"/>
	<acme:list-column code="instantiation Moment" path="instantiationMoment" width="10%"/>
	<acme:list-column code="Title" path="title" width="10%"/>
	<acme:list-column code="Budget" path="budget" width="10%"/>
</acme:list>