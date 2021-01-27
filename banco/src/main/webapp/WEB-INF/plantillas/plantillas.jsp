<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<spring:theme code="styleSheet" var="app_css" />
<spring:url value="/${app_css}" var="app_css_url" />
<link rel="stylesheet" type="text/css" media="screen"
	href="${app_css_url}" />
<script type="text/javascript" src="/banco/resources/script.js"></script>	
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Banco PagosOnLine</title>
</head>
<body style="background-color: #9e9e9e;">
	<tiles:insertAttribute name="header" ignore="true" />
	<tiles:insertAttribute name="menu" ignore="true" />
	<div class="visor">
		<b class="ct1"></b><b class="ct2"></b><b class="ct3"></b><b
			class="ct4"></b>
		<div class="plantilla">
			<br />
			<tiles:insertAttribute name="body" />
		</div>
		<b class="cb4"></b><b class="cb3"></b><b class="cb2"></b><b
			class="cb1"></b>
	</div>
</body>
</html>