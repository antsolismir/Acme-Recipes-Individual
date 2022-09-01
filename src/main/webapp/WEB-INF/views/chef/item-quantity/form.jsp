<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:form>
	<acme:input-textbox code="chef.itemQuantity.form.label.amount" path="amount" readonly="${published == true}"/>
	<jstl:choose>
		<jstl:when test="${acme:anyOf(command, 'show, update, delete')}">
			<acme:input-textbox code="chef.itemQuantity.form.label.item.name" path="item.name" readonly="${true}"/>
			<acme:input-textbox code="chef.itemQuantity.form.label.item.type" path="item.itemType" readonly="${true}"/>
			<acme:input-textbox code="chef.itemQuantity.form.label.item.code" path="item.code" readonly="${true}"/>
			<jstl:if test="${published == false}">
				<acme:submit code="chef.itemQuantity.form.button.update" action="/chef/item-quantity/update"/>
				<acme:submit code="chef.itemQuantity.form.button.delete" action="/chef/item-quantity/delete"/>			
			</jstl:if>
		</jstl:when>
		<jstl:when test="${command == 'create'}">
		<jstl:choose>
				<jstl:when test="${ItemsSize==0}">
					<acme:input-select path="items"
						code="chef.itemQuantity.form.label.items" readonly="${true}">
							<acme:input-option
								code="chef.itemQuantity.form.label.noItems"
								value=""  />
					</acme:input-select>
				</jstl:when>
				<jstl:when test="${ItemsSize!=0}">
					<acme:input-select path="items"
						code="chef.itemQuantity.form.label.items">
						<jstl:forEach items="${items}" var="item">
							<acme:input-option
								code="${item.name} - ${item.itemType} - ${item.retailPrice} "
								value="${item.id}" />
						</jstl:forEach>
					</acme:input-select>
					<acme:submit code="chef.itemQuantity.form.button.create" action="/chef/item-quantity/create?masterId=${masterId}"/>
				</jstl:when>
			</jstl:choose>
		</jstl:when>
	</jstl:choose>
</acme:form>
