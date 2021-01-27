package com.pagosonline.banco.servicio.hibernate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pagosonline.banco.dao.CuentaDao;
import com.pagosonline.banco.dominio.Cuenta;
import com.pagosonline.banco.servicio.ServicioCuenta;
import com.pagosonline.banco.utiles.Paginas;

@Service("servicioCuenta")
@Transactional
public class ServicioCuentaImpl implements ServicioCuenta {

	
	@Autowired
	private CuentaDao cuentaDao;
	
	@Override
	public List<Cuenta> ObtenerCuentas() {
		
		return cuentaDao.ObtenerCuentas();
	}

	@Override
	public Paginas<Cuenta> ObtenerCuentas(String numero, String nombre,int pagina) {		
		return cuentaDao.ObtenerCuentas(numero, nombre,pagina);
	}

	@Override
	public Cuenta ObtenerCuenta(Long id) {		
		return cuentaDao.ObtenerCuenta(id);
	}

	@Override
	public Cuenta SalvarCuenta(Long idCliente,Cuenta cuenta) {		
		return cuentaDao.SalvarCuenta(idCliente,cuenta);
	}

	@Override
	public void BorrarCuenta(Cuenta cuenta) {
		cuentaDao.BorrarCuenta(cuenta);

	}

	@Override
	public void BorrarCuentas(Long[] idCuentas) {
		cuentaDao.BorrarCuentas(idCuentas);
	}	
}
