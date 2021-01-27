package com.pagosonline.banco.servicio;

import java.util.List;

import com.pagosonline.banco.dominio.Movimiento;

public interface ServicioMovimiento {
	// Crea un movimiento dependiendo del tipo (1=debito, 2=credito)
	public boolean CrearMovimiento(Long idCuenta, int valor, int tipo);

	// Obtiene todos los movimientos de una cuenta
	public List<Movimiento> ObtenerMovimiento(Long idCuenta);

	// Obtiene todos los movimientos de una cuenta, filtrador por tipo y cliente
	public List<Movimiento> ObtenerMovimiento(Long idCuenta, String nombre,	int tipo);
}
