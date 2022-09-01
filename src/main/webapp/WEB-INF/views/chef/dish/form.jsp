<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<jstl:choose>
	<jstl:when test="${acme:anyOf(command, 'show')}">
		<acme:form>
			<acme:input-textbox readonly="true"
				code="chef.dish.form.label.status" path="status" />
			<acme:input-textbox readonly="true"
				code="chef.dish.form.label.code" path="code" />
			<acme:input-textarea readonly="true"
				code="chef.dish.form.label.request" path="request" />
			<acme:input-money readonly="true"
				code="chef.dish.form.label.budget" path="budget" />
			<acme:input-money readonly="true"
				code="chef.dish.form.label.money" path="money"/>
			<acme:input-moment readonly="true"
				code="chef.dish.form.label.creationDate"
				path="creationDate" />
			<acme:input-moment readonly="true"
				code="chef.dish.form.label.initialPeriodDate"
				path="initialPeriodDate" />
			<acme:input-moment readonly="true"
				code="chef.dish.form.label.finalPeriodDate"
				path="finalPeriodDate" />
			<acme:input-url readonly="true"
				code="chef.dish.form.label.link" path="link" />
			<acme:input-textbox readonly="true"
				code="chef.dish.form.label.epicure.organisation"
				path="epicure.organisation" />
			<acme:input-textbox readonly="true"
				code="chef.dish.form.label.epicure.assertion"
				path="epicure.assertion" />
			<acme:input-textbox readonly="true"
				code="chef.dish.form.label.epicure.username"
				path="epicure.userAccount.username" />
		
			<jstl:choose>
				<jstl:when test="${status == 'PROPOSED'}">
					<acme:submit code="chef.dish.form.label.patron.accept"
						action="/chef/dish/accept" />
					<acme:submit code="chef.dish.form.label.patron.denie"
						action="/chef/dish/denie" />
				</jstl:when>
			</jstl:choose>
			
			
			
			
			
			
			
			
			
			<jstl:choose>
				<jstl:when test="${command == 'show'}">
					<acme:button code="chef.dish.form.button.memorandum"
						action="/chef/memorandum/list-group?masterId=${id}" />
				</jstl:when>
			</jstl:choose>
		</acme:form>
	</jstl:when>
</jstl:choose>