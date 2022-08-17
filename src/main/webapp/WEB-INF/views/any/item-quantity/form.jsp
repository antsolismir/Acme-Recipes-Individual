<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:form>
	<acme:input-textbox code="any.itemQuantity.form.label.amount" path="amount" readonly="${true}"/>
	<acme:input-textbox code="any.itemQuantity.form.label.item.name" path="item.name" readonly="${true}"/>
	<acme:input-textbox code="any.itemQuantity.form.label.item.type" path="item.itemType" readonly="${true}"/>
	<acme:input-textbox code="any.itemQuantity.form.label.item.code" path="item.code" readonly="${true}"/>
</acme:form>