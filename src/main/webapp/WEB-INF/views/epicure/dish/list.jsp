<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:list>
	<acme:list-column code="epicure.dish.list.label.status" path="status" width="20%"/>
	<acme:list-column code="epicure.dish.list.label.code" path="code" width="20%"/>
	<acme:list-column code="epicure.dish.list.label.creationDate" path="creationDate" width="20%"/>
	<acme:list-column code="epicure.dish.list.label.published" path="published" width="20%"/>
</acme:list>
<acme:button code="epicure.dish.list.button.create" action="/epicure/dish/create"/>