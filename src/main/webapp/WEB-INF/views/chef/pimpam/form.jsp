<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<jstl:if test="${items.isEmpty()==false || acme:anyOf(command, 'show, update, delete')}">
<acme:form >
	<%-- examen --%>
	<jstl:choose>
		<jstl:when test="${command == 'create'}">	
    		<acme:input-textbox code="chef.pimpam.form.code" path="code" />
    	</jstl:when>
		<jstl:when test="${acme:anyOf(command, 'show, update, delete')}">
			<acme:input-textbox readonly="true" code="chef.pimpam.form.code" path="code" />
		</jstl:when>
	</jstl:choose>
	
	<jstl:choose>
		<jstl:when test="${command == 'show'}">
			<acme:input-textbox readonly="true" code="chef.pimpam.form.instantiationMoment" path="instantiationMoment"/>
		</jstl:when>
	</jstl:choose>
	
	<acme:input-textbox code="chef.pimpam.form.title" path="title"/>
	
	<acme:input-textarea code="chef.pimpam.form.description" path="description"/>
	
	<acme:input-textbox code="chef.pimpam.form.initialPeriodDate" path="initialPeriodDate"/>
	<acme:input-textbox code="chef.pimpam.form.finalPeriodDate" path="finalPeriodDate"/>
	<acme:input-textbox code="chef.pimpam.form.budget" path="budget"/>
	<acme:input-textbox code="chef.pimpam.form.link" path="link"/>
	
	<jstl:choose>
		<jstl:when test="${acme:anyOf(command, 'show, update')}">
			<acme:input-textbox readonly="true" code="chef.pimpam.form.item.code" path="item.code"/>
			<acme:input-textbox readonly="true" code="chef.pimpam.form.item.name" path="item.name"/>
		</jstl:when>
	</jstl:choose>
	
	<acme:hidden-data path="itemId"/>
	
	<jstl:if test="${items.isEmpty()==false}">
	<jstl:choose>
				<jstl:when test="${command == 'create'}">
					<acme:input-select code="Item" path="itemId">
	   					<jstl:forEach items="${items}" var="aa">
							<acme:input-option code="${aa.getName()}" value="${aa.getId()}" selected="${ aa.getId() == itemId }"/>
						</jstl:forEach>
					</acme:input-select>
				</jstl:when>
	</jstl:choose>
	</jstl:if>
	
	<jstl:choose>
		<jstl:when test="${acme:anyOf(command,'show, update, delete') && published==false}">
			<acme:submit code="chef.pimpam.form.update" action="/chef/pimpam/update"/>
			<acme:submit code="chef.pimpam.form.delete" action="/chef/pimpam/delete"/>
		</jstl:when>
		<jstl:when test="${command=='create' && items.isEmpty()==false}">
			<acme:submit code="chef.pimpam.form.create" action="/chef/pimpam/create"/>
		</jstl:when>
	</jstl:choose>
</acme:form>
</jstl:if>
<jstl:if test="${items.isEmpty()==true && !acme:anyOf(command, 'show, update, delete')}">
	<h3 style="color: red"><acme:message code="error.create.item"/></h3>
	<acme:button code="chef.pimpam.list.create-item" action="/chef/item/create"/>
</jstl:if>
