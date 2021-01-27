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
	<spring:eval expression="${not empty pagina}? '${pagina}':'1'"
		var="nroPagina" />
	<form:form id="frmAdmo" method="post" action="/banco/cuenta">
		<table style="margin: auto; width: 500px">
			<tr>
				<th colspan="7"><c:if test="${not empty mensaje}">
			    ${mensaje}
			</c:if></th>
			</tr>
			<tr>
				<th>N&uacute;mero</th>
				<td><input type="text" name="numero" id="numero" size="20"
					maxlength="20" /></td>
				<th>Cliente</th>
				<td><input type="text" name="nombre" id="nombre" size="50"
					maxlength="50" /></td>
				<td><input type="submit" value="Buscar"></td>
			</tr>
		</table>
	</form:form>
	<form:form id="frmResultados" method="post"
		action="/banco/cuenta/borrar/${nroPagina}" onsubmit="return ValidarOperacion();">

		<c:if test="${accion=='cuenta'}">
			<div style="margin: auto; width: 800px;">
				<a href="/banco/cuenta/crear/${nroPagina}">Crear Cuenta</a>
			</div>
		</c:if>
		<table border="1"
			style="margin: auto; width: 800px; text-align: center">
			<tr>
				<th>&nbsp;</th>
				<th>Cuenta</th>
				<th>Cliente</th>
				<th>Saldo</th>
			</tr>
			<c:choose>
				<c:when test="${empty cuentas}">
					<tr>
						<td colspan="4" align="left">No hay Registros</td>
					</tr>
				</c:when>
				<c:otherwise>
					<c:forEach items="${cuentas}" var="cuenta">
						<tr>
							<td><c:choose>
									<c:when test="${accion=='cuenta'}">
										<input type="checkbox" name="ids" id="ids"
											value="${cuenta.id}" onclick="HaySeleccionados();">
									</c:when>
									<c:otherwise>&nbsp;</c:otherwise>
								</c:choose></td>
							<td><c:choose>
									<c:when test="${accion=='cuenta'}">
										<a href="/banco/cuenta/actualizar/${cuenta.id}/${nroPagina}">${cuenta.numero}</a>
									</c:when>
									<c:otherwise>
										<a href="/banco/movimiento/${cuenta.id}/${nroPagina}">${cuenta.numero}</a>
									</c:otherwise>
								</c:choose></td>
							<td>${cuenta.cliente.nombre}</td>
							<td>${cuenta.saldo}</td>
						</tr>
					</c:forEach>
					<tr>
						<td align="right" colspan="4">
							<table border="0" style="width: 100%;">
								<tr>
									<td align="left" width="20%"><c:choose>
											<c:when test="${pagina>1}">
												<c:choose>
													<c:when test="${accion=='cuenta'}">
														<a href="/banco/cuenta/${pagina-1}">&lt;&lt;</a>
													</c:when>
													<c:otherwise>
														<a href="/banco/movimiento/${pagina-1}">&lt;&lt;</a>
													</c:otherwise>
												</c:choose>
											</c:when>
											<c:otherwise>&lt;&lt;</c:otherwise>
										</c:choose> <span
										style="padding: 0 2px 0 2px; border-width: 1px; border-style: solid;">${pagina}-${paginas}</span>
										<c:choose>
											<c:when test="${pagina<paginas}">
												<c:choose>
													<c:when test="${accion=='cuenta'}">
														<a href="/banco/cuenta/${pagina+1}">&gt;&gt;</a>
													</c:when>
													<c:otherwise>
														<a href="/banco/movimiento/${pagina+1}">&gt;&gt;</a>
													</c:otherwise>
												</c:choose>
											</c:when>
											<c:otherwise>&gt;&gt;</c:otherwise>
										</c:choose></td>
									<td align="right"><c:choose>
											<c:when test="${accion=='cuenta'}">
												<input type="submit" id="btnBorrar" value="Borrar" disabled>
											</c:when>
											<c:otherwise>&nbsp;</c:otherwise>
										</c:choose></td>
								</tr>
							</table>
						</td>
					</tr>
				</c:otherwise>
			</c:choose>
		</table>
	</form:form>
</body>
</html>