<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

		<acme:form>
			<jstl:choose>
				<jstl:when test="${command =='show'}">
					<acme:input-textbox code="epicure.dish.form.label.status" readonly="true" path="status"/>
				</jstl:when>
			</jstl:choose>
			
			<jstl:choose>
				<jstl:when test="${command == 'create'}">	
    				<acme:input-textbox code="epicure.dish.form.label.code" path="code" />
    			</jstl:when>
				<jstl:when test="${acme:anyOf(command, 'show, update, delete, publish')}">
					<acme:input-textbox readonly="true" code="epicure.dish.form.label.code" path="code" />
				</jstl:when>
			</jstl:choose>
			
			<acme:input-textarea code="epicure.dish.form.label.request" path="request" />
			<acme:input-money code="epicure.dish.form.label.budget" path="budget" />
			
			<jstl:choose>
				<jstl:when test="${command =='show'}">
					<acme:input-money readonly="true" code="epicure.dish.form.label.money" path="money" />
				</jstl:when>
			</jstl:choose>
			
			<acme:input-url code="epicure.dish.form.label.link" path="link" />
						
			<jstl:choose>
				<jstl:when test="${published==true}">
					<acme:input-moment code="epicure.dish.form.label.creationDate" path="creationDate"/>
				</jstl:when>
			</jstl:choose>
			
			
			<acme:input-moment code="epicure.dish.form.label.initialPeriodDate" path="initialPeriodDate" />
			<acme:input-moment code="epicure.dish.form.label.finalPeriodDate" path="finalPeriodDate" />
			
			<hr>
    		
			<jstl:choose>
				<jstl:when test="${published==false || publish==null}">
					<acme:input-select code="epicure.dish.form.label.chef" path="chefId">
	   					<jstl:forEach items="${chefs}" var="aa">
							<acme:input-option code="${aa.getUserAccount().getUsername()}" value="${aa.getId()}" selected="${ aa.getId() == chefId }"/>
						</jstl:forEach>
					</acme:input-select>
				</jstl:when>
			</jstl:choose>
			
			<jstl:choose>
			<jstl:when test="${published == true}">
					<acme:input-textbox code="epicure.dish.form.label.epicure.organisation" path="chefOrganisation" />
					<acme:input-textbox code="epicure.dish.form.label.epicure.assertion" path="chefAssertion" />
					<acme:input-textbox code="epicure.dish.form.label.epicure.username" path="chefUserAccount" />
				</jstl:when>
			</jstl:choose>
			
			<acme:hidden-data path="published"/>
			
			<jstl:choose>
			

				<jstl:when test="${acme:anyOf(command,'show, update, delete, publish') && published == false}">
					<acme:submit code="epicure.dish.form.button.update" action="/epicure/dish/update"/>
					<acme:submit code="epicure.dish.form.button.delete" action="/epicure/dish/delete"/>
					<acme:submit code="epicure.dish.form.button.publish" action="/epicure/dish/publish"/>
				</jstl:when>
				<jstl:when test="${command=='create'}">
					<acme:submit code="epicure.dish.form.button.create" action="/epicure/dish/create"/>
				</jstl:when>
				
			</jstl:choose>
			
			<jstl:choose>
				<jstl:when test="${command == 'show' && published == true}">
					<acme:button code="chef.dish.form.button.memorandum"
						action="/epicure/memorandum/list-group?masterId=${id}" />
				</jstl:when>
			</jstl:choose>
		</acme:form>

