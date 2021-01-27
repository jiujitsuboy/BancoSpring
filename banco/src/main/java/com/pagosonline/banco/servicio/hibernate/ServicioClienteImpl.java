package com.pagosonline.banco.servicio.hibernate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pagosonline.banco.dao.ClienteDao;
import com.pagosonline.banco.dao.CuentaDao;
import com.pagosonline.banco.dominio.Cliente;
import com.pagosonline.banco.servicio.ServicioCliente;
import com.pagosonline.banco.utiles.ClienteResTran;
import com.pagosonline.banco.utiles.Paginas;

@Service("servicioCliente")
@Repository
@Transactional
public class ServicioClienteImpl implements ServicioCliente {

	@Autowired
	private ClienteDao clienteDao;	

	@Override
	@Transactional(readOnly = true)
	public List<Cliente> ObtenerClientes() {
		return clienteDao.ObtenerClientes();
	}

	@Override
	@Transactional(readOnly = true)
	public Paginas<Cliente> ObtenerClientes(String nombre, String direccion,String telefono, int pagina) {		
		return clienteDao.ObtenerClientes(nombre, direccion, telefono,pagina);
	}

	@Override
	@Transactional(readOnly = true)
	public Paginas<ClienteResTran> ObtenerReporte(String nombre, String fechaIni, String fechaFin, int pagina) {		
		return clienteDao.ObtenerReporte(nombre, fechaIni, fechaFin, pagina);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<ClienteResTran> ObtenerReporte(String nombre, String fechaIni, String fechaFin) {		
		return clienteDao.ObtenerReporte(nombre, fechaIni, fechaFin);
	}
	
	@Override
	@Transactional(readOnly = true)
	// Obtener el cliente especificado
	public Cliente ObtenerCliente(Long id) {
		return clienteDao.ObtenerCliente(id);
	}

	@Override
	public Cliente SalvarCliente(Cliente cliente) {
		return clienteDao.SalvarCliente(cliente);
	}

	@Override
	public void BorrarCliente(Cliente cliente) {
		clienteDao.BorrarCliente(cliente);
	}

	@Override
	public void BorrarClientes(Long[] idClientes) {
		clienteDao.BorrarClientes(idClientes);

	}	
}
