package com.pagosonline.banco.dao;

import com.pagosonline.banco.dominio.Movimiento;
import java.util.List;

public interface MovimientoDao {
	//Crea un movimiento dependiendo del tipo (1=debito, 2=credito)
	public boolean CrearMovimiento(Long idCuenta,int valor, int tipo);
	//Obtiene todos los movimientos de una cuenta
	public List<Movimiento> ObtenerMovimiento(Long idCuenta);
	//Obtiene todos los movimientos de una cuenta, filtrador por tipo y cliente
	public List<Movimiento> ObtenerMovimiento(Long idCuenta,String nombre ,int tipo);
}
