package com.pagosonline.banco.servicio.hibernate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pagosonline.banco.dao.MovimientoDao;
import com.pagosonline.banco.dominio.Movimiento;
import com.pagosonline.banco.servicio.ServicioMovimiento;

@Service("servicioMovimiento")
@Transactional
public class ServicioMovimientoImpl implements ServicioMovimiento {

	@Autowired
	private MovimientoDao movimientoDao; 
	
	@Override
	public boolean CrearMovimiento(Long idCuenta, int valor, int tipo) {
		return movimientoDao.CrearMovimiento(idCuenta, valor, tipo);
	}

	@Override
	public List<Movimiento> ObtenerMovimiento(Long idCuenta) {		
		return movimientoDao.ObtenerMovimiento(idCuenta);
	}

	@Override
	public List<Movimiento> ObtenerMovimiento(Long idCuenta, String nombre,	int tipo) {	
		return movimientoDao.ObtenerMovimiento(idCuenta, nombre, tipo);
	}

}
