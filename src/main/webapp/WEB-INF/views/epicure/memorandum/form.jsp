<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:form>
	<acme:input-textbox readonly="true" code="epicure.memorandum.form.label.sequenceNumber" path="sequenceNumber"/>	
	<acme:input-textarea readonly="true" code="epicure.memorandum.form.label.instantiationMoment" path="instantiationMoment"/>
	<acme:input-textbox code="epicure.memorandum.form.label.report" path="report"/>
	<acme:input-textbox code="epicure.memorandum.form.label.link" path="link"/>
	<acme:input-textbox readonly="true" code="epicure.memorandum.form.label.dish.code" path="dish.code"/>
</acme:form>