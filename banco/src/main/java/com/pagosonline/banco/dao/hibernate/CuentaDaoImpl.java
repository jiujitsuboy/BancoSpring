package com.pagosonline.banco.dao.hibernate;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pagosonline.banco.dao.CuentaDao;
import com.pagosonline.banco.dominio.Cliente;
import com.pagosonline.banco.dominio.Cuenta;
import com.pagosonline.banco.utiles.Paginas;

@Repository("cuentaDao")
@Transactional
public class CuentaDaoImpl implements CuentaDao {

	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Resource(name = "sessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public List<Cuenta> ObtenerCuentas() {
		return sessionFactory.getCurrentSession().createQuery("From Cuenta a").list();
	}

	@Override
	public Paginas<Cuenta> ObtenerCuentas(String numero, String nombre, int pagina) {
		
		Session sesion = sessionFactory.getCurrentSession();
		
		Criteria criteria = sesion.createCriteria(Cuenta.class, "cuenta")
				.createAlias("cuenta.cliente", "cliente");
		
		Criteria criteriaCuenta = sesion.createCriteria(Cuenta.class, "cuenta")
				.createAlias("cuenta.cliente", "cliente");

		if ((numero != null && !numero.isEmpty())
				|| (nombre != null && !nombre.isEmpty())) {

			if (numero != null && !numero.isEmpty()) {
				criteria.add(Restrictions.like("numero", numero, MatchMode.ANYWHERE));
				criteriaCuenta.add(Restrictions.like("numero", numero, MatchMode.ANYWHERE));
			}
			
			if (nombre != null && !nombre.isEmpty()) {
				criteria.add(Restrictions.like("cliente.nombre", nombre, MatchMode.ANYWHERE));
				criteriaCuenta.add(Restrictions.like("cliente.nombre", nombre, MatchMode.ANYWHERE));
			}			

		}						
		
		return Paginas.Paginacion(criteria,criteriaCuenta,pagina);
	}

	@Override
	public Cuenta ObtenerCuenta(Long id) {
		return (Cuenta) sessionFactory.getCurrentSession()
		.getNamedQuery("Cuenta.EncontrarXID").setLong("id", id)
		.uniqueResult();
	}

	@Override
	public Cuenta SalvarCuenta(Long idCliente,Cuenta cuenta) {
		
		Session sesion = sessionFactory.getCurrentSession();
		Cliente cliente = (Cliente)sesion.get(Cliente.class, idCliente);
		cuenta.setCliente(cliente);
		sesion.saveOrUpdate(cuenta);
		return cuenta;
	}

	@Override
	public void BorrarCuenta(Cuenta cuenta) {
		sessionFactory.getCurrentSession().delete(cuenta);
	}

	@Override
	public void BorrarCuentas(Long[] idCuentas) {
		if (idCuentas.length > 0) {		
			sessionFactory.getCurrentSession()
					.createQuery("delete Cuenta c where c.id in :idCuentas")
					.setParameterList("idCuentas", idCuentas).executeUpdate();

		}

	}

}
