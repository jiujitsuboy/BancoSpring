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
	<form:form id="frmMovimiento" method="post">
		<table border="0" style="margin: auto; width: 800px">
			<tr>
				<th>Cliente</th>
				<td><input type="text" id="nombre" name="nombre" size="50"
					maxlength="50"></td>
				<th>Fecha Ini</th>
				<td><input type="text" id="fechaIni" name="fechaIni" size="10"
					maxlength="10"></td>
				<th>Fecha Fin</th>
				<td><input type="text" id="fechaFin" name="fechaFin" size="10"
					maxlength="10"></td>
				<td><input type="submit" value="Generar" /></td>
			</tr>
		</table>
	</form:form>

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
				<tr>
					<td align="left" colspan="5"><c:choose>
							<c:when test="${pagina>1}">
								<a href="/banco/reporte/${pagina-1}">&lt;&lt;</a>
							</c:when>
							<c:otherwise>&lt;&lt;</c:otherwise>
						</c:choose> <span
						style="padding: 0 2px 0 2px; border-width: 1px; border-style: solid;">${pagina}-${paginas}</span>
						<c:choose>
							<c:when test="${pagina<paginas}">
								<a href="/banco/reporte/${pagina+1}">&gt;&gt;</a>
							</c:when>
							<c:otherwise>&gt;&gt;</c:otherwise>
						</c:choose></td>
					<td><a href="/banco/reporte/exportar">Exportar Excel</a></td>
				</tr>
			</c:otherwise>
		</c:choose>
	</table>
</body>
</html>