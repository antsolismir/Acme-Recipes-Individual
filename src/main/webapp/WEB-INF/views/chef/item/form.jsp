<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:form readonly="true"> 
	<acme:input-textbox code="chef.item.list.label.name" path="name"/>
	<acme:input-textbox code="chef.item.list.label.code" path="code"/>
	<acme:input-textarea code="chef.item.list.label.description" path="description"/>
	<acme:input-money code="chef.item.list.label.retailprice" path="retailPrice"/>
	<acme:input-textbox code="chef.item.list.label.type" path="itemType"/>
	<acme:input-textbox code="chef.item.list.label.published" path="published"/>
	<acme:input-textbox code="chef.item.list.label.link" path="link"/>
	
		
</acme:form>

