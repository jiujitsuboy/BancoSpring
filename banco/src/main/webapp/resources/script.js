function HaySeleccionados() {
		var boton = document.getElementById("btnBorrar");

		for ( var i = 0; i < document.forms["frmResultados"].elements.length; i++) {
			if (document.forms["frmResultados"].elements[i].type == "checkbox") {
				if (document.forms["frmResultados"].elements[i].checked) {
					boton.disabled = false;
					return;
				}
			}
		}

		boton.disabled = true;
}
function ValidarOperacion()
{
	return confirm("¿Esta seguro de eliminar los clientes seleccionados?.");
}