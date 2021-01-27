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
	<spring:eval expression="${empty cliente} ? 'Crear Nueva Cuenta':'Actualizar Cuenta'"
		var="tituloForm" />

	<div style="margin: auto; width: 200px">
		<h3>${tituloForm}</h3>
	</div>

	<form:form modelAttribute="cuenta" id="frmCuenta" method="post">
		<table border="0" style="margin: auto; width: 500px">
			<tr>
				<th colspan="2"><c:if test="${not empty mensaje}">
			    ${mensaje}
			</c:if></th>
			</tr>
			<tr>
				<th>N&uacute;mero</th>
				<td align="right"><form:input path="numero" type="text"
						id="numero" value="${cuenta.numero}" size="20" maxlength="20" /><br>
						<form:errors path="numero" class="mensajeError"/></td>
			</tr>
			<tr>
				<th>Cliente</th>
				<td align="right"><select name="clienteS" id="clienteS">
						<c:forEach items="${clientes}" var="cliente">
							<option value="${cliente.id}"
								${(cuenta.cliente!=null && cuenta.cliente.id==cliente.id)?"selected":""}>${cliente.nombre}</option>
						</c:forEach>
				</select></td>
			</tr>
			<tr>
				<th>Saldo</th>
				<td align="right"><form:input path="saldo" type="text"
						id="saldo" value="${cuenta.saldo}" size="10" maxlength="10" /><br><form:errors
						path="saldo" class="mensajeError"/></td>
			</tr>
			<tr>
				<td><a href="/banco/cuenta/${pagina}">Regresar</a></td>
				<td align="right"><input type="submit"
					value="${(cuenta.id==null)?'Crear':'Actualizar'}"></td>
			</tr>
		</table>
	</form:form>
</body>
</html>