<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:form >
	<%-- examen --%>
	<jstl:choose>
		<jstl:when test="${command == 'create'}">	
    		<acme:input-textbox code="Code" path="code" />
    	</jstl:when>
		<jstl:when test="${acme:anyOf(command, 'show, update, delete')}">
			<acme:input-textbox readonly="true" code="Code" path="code" />
		</jstl:when>
	</jstl:choose>
	
	<jstl:choose>
		<jstl:when test="${command == 'show'}">
			<acme:input-textbox readonly="true" code="InstantiationMoment" path="instantiationMoment"/>
		</jstl:when>
	</jstl:choose>
	
	<acme:input-textbox code="Title" path="title"/>
	
	<acme:input-textarea code="Description" path="description"/>
	
	<acme:input-textbox code="InitialPeriodDate" path="initialPeriodDate"/>
	<acme:input-textbox code="FinalPeriodDate" path="finalPeriodDate"/>
	<acme:input-textbox code="Budget" path="budget"/>
	<acme:input-textbox code="Link" path="link"/>
	
	<jstl:choose>
		<jstl:when test="${acme:anyOf(command, 'show, update')}">
			<acme:input-textbox readonly="true" code="Item.code" path="item.code"/>
			<acme:input-textbox readonly="true" code="Item.name" path="item.name"/>
		</jstl:when>
	</jstl:choose>
	
	<acme:hidden-data path="itemId"/>
	
	<jstl:choose>
				<jstl:when test="${command == 'create'}">
					<acme:input-select code="Item" path="itemId">
	   					<jstl:forEach items="${items}" var="aa">
							<acme:input-option code="${aa.getName()}" value="${aa.getId()}" selected="${ aa.getId() == itemId }"/>
						</jstl:forEach>
					</acme:input-select>
				</jstl:when>
	</jstl:choose>
	<jstl:choose>
		<jstl:when test="${acme:anyOf(command,'show, update, delete') && published==false}">
			<acme:submit code="update" action="/chef/pimpam/update"/>
			<acme:submit code="delete" action="/chef/pimpam/delete"/>
		</jstl:when>
		<jstl:when test="${command=='create'}">
			<acme:submit code="create" action="/chef/pimpam/create"/>
		</jstl:when>
	</jstl:choose>
</acme:form>
