<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:form readonly="false"> 
	<acme:input-textbox code="chef.item.list.label.name" path="name"/>
	<acme:input-textbox code="chef.item.list.label.code" path="code"/>
	<acme:input-textarea code="chef.item.list.label.description" path="description"/>
	<acme:input-money code="chef.item.list.label.retailprice" path="retailPrice"/>
	<acme:input-select code="chef.item.list.label.type" path="itemType">
		<acme:input-option code="chef.item.form.label.ingredient" value="INGREDIENT" selected="${ itemType == 'INGREDIENT' }"/>
		<acme:input-option code="chef.item.form.label.kitchen_utensil" value="KITCHEN_UTENSIL" selected="${ itemType == 'KITCHEN_UTENSIL' }"/>
	</acme:input-select>
	<acme:input-checkbox readonly="true" code="chef.item.list.label.published" path="published"/>
	<acme:input-textbox code="chef.item.list.label.link" path="link"/>
	
	<jstl:choose>
		<jstl:when test="${acme:anyOf(command, 'show, update, delete, publish') && published == false }">		
			<acme:submit code="chef.item.form.button.update" action="/chef/item/update"/>
			<acme:submit code="chef.item.form.button.delete" action="/chef/item/delete"/>
			<acme:submit code="chef.item.form.button.publish" action="/chef/item/publish"/>
		</jstl:when>
		<jstl:when test="${command == 'create'}">
			<acme:submit code="chef.item.form.button.create" action="/chef/item/create"/>
		</jstl:when>	
	</jstl:choose>
		
</acme:form>

