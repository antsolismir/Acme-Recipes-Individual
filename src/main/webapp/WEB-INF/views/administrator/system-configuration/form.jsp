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

<acme:form readonly="false">

	<acme:input-textbox code="administrator.configuration.form.label.systemCurrency" path="defaultCurrency"/>
	<acme:input-textbox code="administrator.configuration.form.label.acceptedCurrencies" path="acceptedCurrencies"/>
	<acme:input-textbox code="administrator.configuration.form.label.spamTermsEn" path="spamTermsEn"/>
	<acme:input-textbox code="administrator.configuration.form.label.spamTermsEs" path="spamTermsEs"/>
	<acme:input-textbox code="administrator.configuration.form.label.spamThreshold" path="spamThreshold"/>

	<jstl:choose>
    <jstl:when test="${acme:anyOf(command,'show, update')}">
		<acme:submit code="administrator.configuration.form.button.update" action="/administrator/system-configuration/update"/>
	</jstl:when>
	
</jstl:choose>

</acme:form>