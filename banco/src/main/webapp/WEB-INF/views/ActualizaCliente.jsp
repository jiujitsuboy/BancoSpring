<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<spring:eval expression="${empty cliente}? 'Crear Nuevo Cliente':'Actualizar Cliente'" var="tituloForm" />
	<spring:eval expression="${empty cliente} ? 'Crear':'Actualizar'" var="tituloBoton" />

	<div style="margin: auto; width: 200px">
		<h3>${tituloForm}</h3>
	</div>

	<form:form modelAttribute="cliente" id="frmCliente" method="post">
		<table border="0" style="margin: auto; width: 500px">
			<tr>
				<th colspan="2"><c:if test="${not empty mensaje}">
			    ${mensaje}
			</c:if></th>
			</tr>
			<tr>
				<th>Nombre</th>
				<td align="right"><form:input path="nombre" type="text"
						id="nombre" value="${cliente.nombre}" size="50" maxlength="50" /><br><form:errors
						path="nombre" class="mensajeError" /></td>
			</tr>
			<tr>
				<th>Direcci&oacute;n</th>
				<td align="right"><form:input path="direccion" type="text"
						id="direccion" value="${cliente.direccion}" size="20"
						maxlength="20" />&nbsp;<form:errors path="direccion" class="mensajeError"/></td>
			</tr>
			<tr>
				<th>Telefono</th>
				<td align="right"><form:input path="telefono" type="text"
						id="telefono" value="${cliente.telefono}" size="10" maxlength="10" /><br><form:errors
						path="telefono" class="mensajeError"/></td>
			</tr>
			<tr>
				<td><a href="/banco/cliente/${pagina}">Regresar</a></td>
				<td align="right"><input type="submit"
					value="${tituloBoton}"></td>
			</tr>
		</table>
	</form:form>
</body>
</html>