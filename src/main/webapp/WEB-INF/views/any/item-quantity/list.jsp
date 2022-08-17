<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:list>	
	<acme:list-column code="any.itemQuantity.list.label.amount" path="amount" width="20%"/>
	<acme:list-column code="any.itemQuantity.list.label.item.name" path="item.name" width="80%"/>
</acme:list>

