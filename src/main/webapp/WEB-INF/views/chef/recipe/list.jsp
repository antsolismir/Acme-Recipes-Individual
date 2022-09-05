<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:list>

	<acme:list-column code="chef.recipe.list.label.heading" path="heading" width="40%"/>
	<acme:list-column code="chef.recipe.list.label.code" path="code" width="35%"/>
	<acme:list-column code="chef.recipe.list.label.totalPrice" path="money" width="15%"/>	
	<acme:list-column code="chef.recipe.list.label.published" path="published" width="10%"/>	
</acme:list>

<acme:button code="chef.recipe.list.button.create" action="/chef/recipe/create"/>