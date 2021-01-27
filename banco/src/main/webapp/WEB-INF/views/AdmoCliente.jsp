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
	<spring:eval expression="${not empty pagina}? '${pagina}':'1'" var="nroPagina" />
	<form:form id="frmCliente" method="post" action="/banco/cliente">
		<table style="margin: auto; width: 500px">
			<tr>
				<th colspan="7"><c:if test="${not empty mensaje}">
			    ${mensaje}
			</c:if></th>
			</tr>
			<tr>
				<th>Nombre</th>
				<td><input type="text" name="nombre" id="nombre" size="50"
					maxlength="50" /></td>
				<th>Direcci&oacute;n</th>
				<td><input type="text" name="direccion" id="direccion"
					size="20" maxlength="20" /></td>
				<th>Telefono</th>
				<td><input type="text" name="telefono" id="telefono" size="10"
					maxlength="10" /></td>
				<td><input type="submit" value="Buscar"></td>
			</tr>
		</table>
	</form:form>
	<form:form id="frmResultados" method="post" action="/banco/borrar/${nroPagina}" onsubmit="return ValidarOperacion();">
		<div style="margin: auto; width: 800px;">
			<a href="/banco/crear/${nroPagina}">Crear Cliente</a>
		</div>
		<table border="1"
			style="margin: auto; width: 800px; text-align: center">
			<tr>
				<th>&nbsp;</th>
				<th>Nombre</th>
				<th>Direcci&oacute;n</th>
				<th>Telefono</th>
			</tr>
			<c:choose>
				<c:when test="${empty clientes}">
					<tr>
						<td colspan="4" align="left">No hay Registros</td>
					</tr>
				</c:when>
				<c:otherwise>
					<c:forEach items="${clientes}" var="cliente">
						<tr>
							<td><input type="checkbox" name="ids" id="ids"
								value="${cliente.id}" onclick="HaySeleccionados();"></td>
							<td><a href="/banco/actualizar/${cliente.id}/${pagina}">${cliente.nombre}</a></td>
							<td>${cliente.direccion}</td>
							<td>${cliente.telefono}</td>
						</tr>
					</c:forEach>
					<tr>
						<td align="right" colspan="4">
							<table border="0" style="width: 100%;">
								<tr>
									<td align="left" width="20%">
									   <c:choose>
											<c:when test="${pagina>1}">
												<a href="/banco/cliente/${pagina-1}">&lt;&lt;</a>
											</c:when>
											<c:otherwise>&lt;&lt;</c:otherwise>
										</c:choose> <span style="padding: 0 2px 0 2px ; border-width: 1px;border-style: solid;">${pagina}-${paginas}</span> 
										<c:choose>
											<c:when test="${pagina<paginas}">
												<a href="/banco/cliente/${pagina+1}">&gt;&gt;</a>
											</c:when>
											<c:otherwise>&gt;&gt;</c:otherwise>
										</c:choose></td>
									<td align="right"><input type="submit" id="btnBorrar"
										value="Borrar" disabled></td>
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