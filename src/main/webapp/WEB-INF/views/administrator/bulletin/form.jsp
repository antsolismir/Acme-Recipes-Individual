<%--
- form.jsp
-
- Copyright (C) 2012-2022 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:form readonly="${readonly}">
	<acme:input-textbox code="administrator.bulletin.form.label.heading" path="heading"/>	
	<acme:input-select code="administrator.bulletin.form.label.critical" path="critical">
	<acme:input-option code="administrator.bulletin.form.label.notCritical" value="false" selected="${critical == false}"/>
	<acme:input-option code="administrator.bulletin.form.label.isCritical" value="true" selected="${critical == true}"/>
	</acme:input-select>
	<acme:input-textarea code="administrator.bulletin.form.label.text" path="text"/>
	<acme:input-textbox code="administrator.bulletin.form.label.info" path="info"/>
	<acme:input-checkbox code="administrator.bulletin.form.label.confirmation" path="confirmation"/>
	<acme:submit test="${command == 'create'}" code="administrator.bulletin.form.button.create" action="/administrator/bulletin/create"/>
</acme:form> 