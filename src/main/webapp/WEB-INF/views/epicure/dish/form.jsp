<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<jstl:choose>
	<jstl:when test="${acme:anyOf(command, 'show')}">
		<acme:form>
			<acme:input-textbox readonly="true"
				code="epicure.dish.form.label.status" path="status" />
			<acme:input-textbox readonly="true"
				code="epicure.dish.form.label.code" path="code" />
			<acme:input-textarea readonly="true"
				code="epicure.dish.form.label.request" path="request" />
			<acme:input-money readonly="true"
				code="epicure.dish.form.label.budget" path="budget" />
			
			
			
			
			
			<jstl:if test="${convert!=null}">
				<acme:input-textbox readonly="true"
					code="epicure.dish.form.label.convert"
					path="convert" />
			</jstl:if>
			
			
			
			
			
			<acme:input-moment readonly="true"
				code="epicure.dish.form.label.creationDate"
				path="creationDate" />
			<acme:input-moment readonly="true"
				code="epicure.dish.form.label.initialPeriodDate"
				path="initialPeriodDate" />
			<acme:input-moment readonly="true"
				code="epicure.dish.form.label.finalPeriodDate"
				path="finalPeriodDate" />
			<acme:input-url readonly="true"
				code="epicure.dish.form.label.link" path="link" />
			<acme:input-textbox readonly="true"
				code="epicure.dish.form.label.epicure.organisation"
				path="chef.organisation" />
			<acme:input-textbox readonly="true"
				code="epicure.dish.form.label.epicure.assertion"
				path="chef.assertion" />
			<acme:input-textbox readonly="true"
				code="epicure.dish.form.label.epicure.username"
				path="chef.userAccount.username" />
			
		</acme:form>
	</jstl:when>
</jstl:choose>