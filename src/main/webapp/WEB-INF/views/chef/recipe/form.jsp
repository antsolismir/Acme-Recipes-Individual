<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:form>
	<acme:input-textbox code="chef.recipe.form.label.heading" path="heading" />
	<jstl:choose>
    	<jstl:when test="${command == 'create'}">	
    		<acme:input-textbox code="chef.recipe.form.label.code" path="code"/>
    	</jstl:when>
    	<jstl:when test="${acme:anyOf(command, 'show, update, delete, publish')}">
    		<acme:input-textbox code="chef.recipe.form.label.code" path="code" readonly="${true}"/>
    			<acme:input-textbox code="chef.recipe.form.label.price" path="money" readonly="true"/>
    	</jstl:when>
    </jstl:choose>
	<acme:input-textbox code="chef.recipe.form.label.description" path="description" />
	<acme:input-textbox code="chef.recipe.form.label.preparationNotes" path="preparationNotes" />
	<acme:input-textbox code="chef.recipe.form.label.info" path="info" />
		<jstl:choose>	 
		<jstl:when test="${command == 'show' && published == true}">
			<acme:button code="chef.recipe.form.button.items" action="/chef/item-quantity/list?masterId=${id}"/>
		</jstl:when>
		<jstl:when test="${acme:anyOf(command, 'show, update, delete, publish') && published == false}">
			<acme:button code="chef.recipe.form.button.items" action="/chef/item-quantity/list?masterId=${id}&?draftMode=${draftMode}"/>
			<acme:submit code="chef.recipe.form.button.update" action="/chef/recipe/update"/>
			<acme:submit code="chef.recipe.form.button.delete" action="/chef/recipe/delete"/>
			<acme:submit code="chef.recipe.form.button.publish" action="/chef/recipe/publish"/>
		</jstl:when>
		<jstl:when test="${command == 'create'}">
			<acme:submit code="chef.recipe.form.button.create" action="/chef/recipe/create"/>
		</jstl:when>		
	</jstl:choose>
</acme:form>
