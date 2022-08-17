<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<acme:list>
	<acme:list-column code="chef.memorandum.list.label.sequenceNumber" path="sequenceNumber" width="20%"/>
	<acme:list-column code="chef.memorandum.list.label.instantiationMoment" path="instantiationMoment" width="10%"/>
</acme:list>