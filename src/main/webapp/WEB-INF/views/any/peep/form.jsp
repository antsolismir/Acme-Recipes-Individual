<%@page language="java"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>
<acme:form>
	<acme:input-moment code="any.peep.form.label.moment" path="moment" readonly="true"/>
	<acme:input-textarea code="any.peep.form.label.heading" path="heading"/>
	<acme:input-textarea code="any.peep.form.label.writer" path="writer"/>
	<acme:input-textarea code="any.peep.form.label.text" path="text"/>
	<acme:input-email code="any.peep.form.label.email" path="email"/>
	<acme:input-checkbox code="any.peep.form.label.confirmation" path="confirmation"/>

	<jstl:if test="${command == 'create'}">
			<acme:submit code="any.peep.form.button.create" action="/any/peep/create"/>		
	</jstl:if>

</acme:form>