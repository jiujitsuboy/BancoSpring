package com.pagosonline.banco.servicio;

import java.util.List;

import com.pagosonline.banco.dominio.Cuenta;
import com.pagosonline.banco.utiles.Paginas;

public interface ServicioCuenta {	

	// Obtener todos las Cuentas
	public List<Cuenta> ObtenerCuentas();

	// Obtener la Cuenta especificada
	public Paginas<Cuenta> ObtenerCuentas(String numero, String nombre, int pagina);

	// Obtener la cuenta especificada
	public Cuenta ObtenerCuenta(Long id);

	// Insertar o Actualizar una Cuenta
	public Cuenta SalvarCuenta(Long idCliente,Cuenta cuenta);

	// Borrar una Cuenta
	public void BorrarCuenta(Cuenta cuenta);

	// Borrar un grupo de cuentas
	public void BorrarCuentas(Long[] idClientes);
}
