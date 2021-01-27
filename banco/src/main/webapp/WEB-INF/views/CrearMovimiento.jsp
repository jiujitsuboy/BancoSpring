<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<div style="margin: auto; width: 200px">
		<h3>Crear Movimiento</h3>
	</div>
	<form:form modelAttribute="movimiento" id="frmMovimiento" method="post">
		<table border="0" style="margin: auto; width: 400px">
			<tr>
				<th colspan="7"><c:if test="${not empty mensaje}">
			    ${mensaje}
			</c:if></th>
			</tr>
			<tr>
				<th>Cuenta</th>
				<td align="right">${cuenta.numero}</td>
			</tr>
			<tr>
				<th>Cliente</th>
				<td align="right">${cuenta.cliente.nombre}</td>
			</tr>
			<tr>
				<th>Movimiento</th>
				<td align="right"><select name="tipo" id="tipo">
						<option value="1" selected>Debito</option>
						<option value="2">Credito</option>
				</select></td>
			</tr>
			<tr>
				<th>Saldo</th>
				<td align="right">${cuenta.saldo}</td>
			</tr>
			<tr>
				<th>Valor</th>
				<td align="right"><form:input path="valor" type="text"
						name="valor" id="valor" /> <br><form:errors path="valor" class="mensajeError"/></td>
			</tr>
			<tr>
				<td><a href="/banco/movimiento/${pagina}">Regresar</a></td>
				<td colspan="2" align="right"><input type="submit"
					value="Generar" /></td>
			</tr>
		</table>
	</form:form>
</body>
</html>