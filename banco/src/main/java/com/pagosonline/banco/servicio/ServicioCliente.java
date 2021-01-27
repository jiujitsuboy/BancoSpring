package com.pagosonline.banco.servicio;

import java.util.List;

import com.pagosonline.banco.dominio.Cliente;
import com.pagosonline.banco.utiles.ClienteResTran;
import com.pagosonline.banco.utiles.Paginas;

/**
 * @author Jose Alejandro Niño Mora
 * 
 */
public interface ServicioCliente {

	// Obtener todos los clientes
	public List<Cliente> ObtenerClientes();

	// Obtener el cliente especificado
	public Paginas<Cliente> ObtenerClientes(String nombre, String direccion,String telefono ,int pagina);

	// Obtener el reporte de clientes
	public Paginas<ClienteResTran> ObtenerReporte(String nombre, String fechaIni,	String fechaFin, int pagina);
	
	// Obtener el reporte de clientes
	public List<ClienteResTran> ObtenerReporte(String nombre, String fechaIni,	String fechaFin);

	// Obtener el cliente especificado
	public Cliente ObtenerCliente(Long id);

	// Insertar o Actualizar un Cliente
	public Cliente SalvarCliente(Cliente cliente);

	// Borrar un cliente
	public void BorrarCliente(Cliente cliente);

	// Borrar un grupo de clientes
	public void BorrarClientes(Long[] idClientes);
}
