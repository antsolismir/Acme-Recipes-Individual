<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:form>
	<acme:input-textbox code="chef.recipe.form.label.heading" path="heading" readonly="true"/>
	<acme:input-textbox code="chef.recipe.form.label.code" path="code" readonly="true"/>
	<acme:input-textbox code="chef.recipe.form.label.description" path="description" readonly="true"/>
	<acme:input-textbox code="chef.recipe.form.label.preparationNotes" path="preparationNotes" readonly="true"/>
	<acme:input-textbox code="chef.recipe.form.label.info" path="info" readonly="true"/>
	<acme:input-textbox code="chef.recipe.form.label.price" path="money" readonly="true"/>
	<acme:input-textbox code="chef.recipe.form.label.published" path="published" readonly="true"/>
	<acme:button code="chef.recipe.form.button.items" action="/chef/item-quantity/list?masterId=${id}"/>
</acme:form>
