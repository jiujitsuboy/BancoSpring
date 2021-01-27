package com.pagosonline.banco.dao.hibernate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pagosonline.banco.dao.ClienteDao;
import com.pagosonline.banco.dominio.Cliente;
import com.pagosonline.banco.utiles.ClienteResTran;
import com.pagosonline.banco.utiles.Paginas;

@Repository("clienteDao")
@Transactional
public class ClienteDaoImpl implements ClienteDao {

	private SessionFactory sessionFactory;
	private final int regXPagina = 2;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Resource(name = "sessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Cliente> ObtenerClientes() {				
		return sessionFactory.getCurrentSession().createQuery("from Cliente c").list();						
	}
	
	@Override
	public Paginas<Cliente> ObtenerClientes(String nombre, String direccion,String telefono , int pagina) {		

		Session sesion = sessionFactory.getCurrentSession();

		Criteria criteria = sesion.createCriteria(Cliente.class);
		Criteria criteriaCuenta = sesion.createCriteria(Cliente.class);

		if ((nombre != null && !nombre.isEmpty())
				|| (direccion != null && !direccion.isEmpty())
				|| (telefono != null && !telefono.isEmpty())) {

			if (nombre != null && !nombre.isEmpty()) {
				criteria.add(Restrictions.like("nombre", nombre,MatchMode.ANYWHERE));
				criteriaCuenta.add(Restrictions.like("nombre", nombre,MatchMode.ANYWHERE));
			}
			if (direccion != null && !direccion.isEmpty()) {
				criteria.add(Restrictions.like("direccion", direccion,MatchMode.ANYWHERE));
				criteriaCuenta.add(Restrictions.like("direccion", direccion,MatchMode.ANYWHERE));
			}
			if (telefono != null & !telefono.isEmpty()) {
				criteria.add(Restrictions.like("telefono", telefono,MatchMode.ANYWHERE));
				criteriaCuenta.add(Restrictions.like("telefono", telefono,MatchMode.ANYWHERE));
			}
		}
				 		
		return Paginas.Paginacion(criteria,criteriaCuenta, pagina);		
	}

	@Override
	public Paginas<ClienteResTran> ObtenerReporte(String nombre, String fechaIni,String fechaFin,int pagina) {
		List<ClienteResTran> clienteRes =null;
		
		Criteria criteria = sessionFactory
				.getCurrentSession()
				.createCriteria(Cliente.class, "cliente")
				.createAlias("cliente.cuentas", "cuenta")
				.createAlias("cuenta.movimiento", "movimiento")
				.setProjection(
						Projections
								.projectionList()
								.add(Projections.property("cliente.nombre"),
										"cliente_nomre")
								.add(Projections.property("cuenta.numero"),
										"cuenta_numero")
								.add(Projections.property("movimiento.tipo"),
										"movimiento_tipo")
								.add(Projections.property("movimiento.valor"),
										"movimiento_valor")
								.add(Projections.property("movimiento.saldo"),
										"movimiento_saldo")
								.add(Projections.property("movimiento.fecha"),
										"movimiento_fecha"));
		Criteria criteriaCuenta = sessionFactory
				.getCurrentSession()
				.createCriteria(Cliente.class, "cliente")
				.createAlias("cliente.cuentas", "cuenta")
				.createAlias("cuenta.movimiento", "movimiento")
				.setProjection(
						Projections
								.projectionList()
								.add(Projections.property("cliente.nombre"),
										"cliente_nomre")
								.add(Projections.property("cuenta.numero"),
										"cuenta_numero")
								.add(Projections.property("movimiento.tipo"),
										"movimiento_tipo")
								.add(Projections.property("movimiento.valor"),
										"movimiento_valor")
								.add(Projections.property("movimiento.saldo"),
										"movimiento_saldo")
								.add(Projections.property("movimiento.fecha"),
										"movimiento_fecha"));

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

		if ((nombre != null && !nombre.isEmpty())
				|| (fechaIni != null && !fechaIni.isEmpty())
				|| (fechaFin != null && !fechaFin.isEmpty())) {

			if ((fechaIni != null && !fechaIni.isEmpty())
					&& (fechaFin != null && !fechaFin.isEmpty())) {
				try {
					Date fecIni = df.parse(fechaIni);
					Date fecfin = df.parse(fechaFin);

					if (fecIni.before(fecfin)) {
						criteria.add(Restrictions.between("movimiento.fecha",
								fecIni, fecfin));
						criteriaCuenta.add(Restrictions.between("movimiento.fecha",
								fecIni, fecfin));

					}
				} catch (ParseException p) {

				}
			} else {
				if (fechaIni != null && !fechaIni.isEmpty()) {

					try {
						Date fec = df.parse(fechaIni);
						criteria.add(Restrictions.ge("movimiento.fecha", fec));
						criteriaCuenta.add(Restrictions.ge("movimiento.fecha", fec));
					} catch (ParseException p) {

					}
				}
				if (fechaFin != null && !fechaFin.isEmpty()) {

					try {
						Date fec = df.parse(fechaFin);
						criteria.add(Restrictions.le("movimiento.fecha", fec));
						criteriaCuenta.add(Restrictions.le("movimiento.fecha", fec));
					} catch (ParseException p) {

					}
				}
			}
			if (nombre != null && !nombre.isEmpty()) {
				criteria.add(Restrictions.like("cliente.nombre", nombre,
						MatchMode.ANYWHERE));
				criteriaCuenta.add(Restrictions.like("cliente.nombre", nombre,
						MatchMode.ANYWHERE));
			}
		}

		Paginas<Object> resultado = Paginas.Paginacion(criteria, criteriaCuenta, pagina);
		
		if (resultado.getResultado().size() > 0) {
			
			List<Object> items = resultado.getResultado();
			clienteRes = new ArrayList<ClienteResTran>();
			
			for (int i = 0; i < items.size(); i++) {
				try {
					Object[] obj = (Object[]) items.get(i);
					clienteRes.add(new ClienteResTran(obj[0].toString(), obj[1]
							.toString(), Integer.parseInt(obj[2].toString()),
							Integer.parseInt(obj[3].toString()), Integer
									.parseInt(obj[4].toString()), df
									.parse(obj[5].toString())));
				} catch (ParseException p) {
					System.out.println(p.getMessage());
				}
			}						
		}

		return new Paginas<ClienteResTran>(resultado.getNroPaginas(),clienteRes);
	}
	
	public List<ClienteResTran> ObtenerReporte(String nombre, String fechaIni,String fechaFin) {
		List<ClienteResTran> clienteRes =null;
		
		Criteria criteria = sessionFactory
				.getCurrentSession()
				.createCriteria(Cliente.class, "cliente")
				.createAlias("cliente.cuentas", "cuenta")
				.createAlias("cuenta.movimiento", "movimiento")
				.setProjection(
						Projections
								.projectionList()
								.add(Projections.property("cliente.nombre"),
										"cliente_nomre")
								.add(Projections.property("cuenta.numero"),
										"cuenta_numero")
								.add(Projections.property("movimiento.tipo"),
										"movimiento_tipo")
								.add(Projections.property("movimiento.valor"),
										"movimiento_valor")
								.add(Projections.property("movimiento.saldo"),
										"movimiento_saldo")
								.add(Projections.property("movimiento.fecha"),
										"movimiento_fecha"));
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

		if ((nombre != null && !nombre.isEmpty())
				|| (fechaIni != null && !fechaIni.isEmpty())
				|| (fechaFin != null && !fechaFin.isEmpty())) {

			if ((fechaIni != null && !fechaIni.isEmpty())
					&& (fechaFin != null && !fechaFin.isEmpty())) {
				try {
					Date fecIni = df.parse(fechaIni);
					Date fecfin = df.parse(fechaFin);

					if (fecIni.before(fecfin)) {
						criteria.add(Restrictions.between("movimiento.fecha",
								fecIni, fecfin));	

					}
				} catch (ParseException p) {

				}
			} else {
				if (fechaIni != null && !fechaIni.isEmpty()) {

					try {
						Date fec = df.parse(fechaIni);
						criteria.add(Restrictions.ge("movimiento.fecha", fec));						
					} catch (ParseException p) {

					}
				}
				if (fechaFin != null && !fechaFin.isEmpty()) {

					try {
						Date fec = df.parse(fechaFin);
						criteria.add(Restrictions.le("movimiento.fecha", fec));						
					} catch (ParseException p) {

					}
				}
			}
			if (nombre != null && !nombre.isEmpty()) {
				criteria.add(Restrictions.like("cliente.nombre", nombre,
						MatchMode.ANYWHERE));				
			}
		}	
		
		List resultado = criteria.list(); 
		if (resultado.size() > 0) {
						
			clienteRes = new ArrayList<ClienteResTran>();
			
			for (int i = 0; i < resultado.size(); i++) {
				try {
					Object[] obj = (Object[]) resultado.get(i);
					clienteRes.add(new ClienteResTran(obj[0].toString(), obj[1]
							.toString(), Integer.parseInt(obj[2].toString()),
							Integer.parseInt(obj[3].toString()), Integer
									.parseInt(obj[4].toString()), df
									.parse(obj[5].toString())));
				} catch (ParseException p) {
					System.out.println(p.getMessage());
				}
			}						
		}

		return clienteRes;
	}

	@Override
	@Transactional(readOnly = true)
	public Cliente ObtenerCliente(Long id) {
		return (Cliente) sessionFactory.getCurrentSession()
				.getNamedQuery("Cliente.EncontrarXID").setLong("id", id)
				.uniqueResult();
	}

	@Override
	public Cliente SalvarCliente(Cliente cliente) {
		sessionFactory.getCurrentSession().saveOrUpdate(cliente);
		return cliente;
	}

	@Override
	public void BorrarCliente(Cliente cliente) {

		sessionFactory.getCurrentSession().delete(cliente);
	}

	@Override
	public void BorrarClientes(Long[] idClientes) {
		StringBuilder strCondicion = new StringBuilder();

		if (idClientes.length > 0) {

			/*
			 * strCondicion.append("delete Cliente c where c.id in (");
			 * 
			 * for (int i = 0; i < idClientes.length - 1; i++) {
			 * strCondicion.append(idClientes[i]); strCondicion.append(","); }
			 * strCondicion.append(idClientes[idClientes.length - 1]);
			 * strCondicion.append(")");
			 * sessionFactory.getCurrentSession().createQuery
			 * (strCondicion.toString());
			 */
			sessionFactory.getCurrentSession()
					.createQuery("delete Cliente c where c.id in :idClientes")
					.setParameterList("idClientes", idClientes).executeUpdate();

		}
	}
}
