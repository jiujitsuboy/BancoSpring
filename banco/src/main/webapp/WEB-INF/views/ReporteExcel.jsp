<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="application/vnd.ms-excel">
<title>Insert title here</title>
</head>
<body>	
<%
   response.setContentType("application/vnd.ms-excel");
   response.setHeader("Content-Disposition", "inline; filename=excel.xls");     
%>
	<table border="1" style="margin: auto; width: 800px">
		<tr>
			<th>Cliente</th>
			<th>Cuenta</th>
			<th>Movimiento</th>
			<th>Valor</th>
			<th>Saldo</th>
			<th>Fecha</th>
		</tr>
		<c:choose>
			<c:when test="${empty clientes}">
				<tr>
					<td colspan="6" align="left">No hay Registros</td>
				</tr>
			</c:when>
			<c:otherwise>
				<c:forEach items="${clientes}" var="cliente">
					<tr>
						<td>${cliente.cliente}</td>
						<td>${cliente.numeroCuenta}</td>
						<td>${cliente.tipoMovimiento}</td>
						<td>${cliente.valorMovimiento}</td>
						<td>${cliente.saldoTemporal}</td>
						<td>${cliente.fecha}</td>
					</tr>
				</c:forEach>
			</c:otherwise>
		</c:choose>		
	</table>
</body>
</html>