package com.pagosonline.banco.dao.hibernate;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pagosonline.banco.dao.MovimientoDao;
import com.pagosonline.banco.dominio.Cliente;
import com.pagosonline.banco.dominio.Cuenta;
import com.pagosonline.banco.dominio.Movimiento;

@Repository("movimientoDao")
@Transactional
public class MovimientoDaoImpl implements MovimientoDao {

	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Resource(name = "sessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public boolean CrearMovimiento(Long idCuenta, int valor, int tipo) {

		boolean resultado = false;
		Session sesion = sessionFactory.getCurrentSession();

		Cuenta cuenta = (Cuenta) sesion.get(Cuenta.class, idCuenta);

		Movimiento movimiento = new Movimiento();

		int saldo = cuenta.getSaldo();
		if (tipo == 1) {
			saldo -= valor;
		} else {
			saldo += valor;
		}

		if (saldo >= 0 && saldo <= Integer.MAX_VALUE) {
			movimiento.setCuenta(cuenta);
			movimiento.setTipo(tipo);
			movimiento.setValor(valor);
			movimiento.setFecha(new Date());
			movimiento.setSaldo(saldo);
			cuenta.setSaldo(saldo);
			sesion.saveOrUpdate(cuenta);
			sesion.saveOrUpdate(movimiento);
						
			resultado = true;
		}

		return resultado;
	}

	@Override
	public List<Movimiento> ObtenerMovimiento(Long idCuenta) {		
		return sessionFactory.getCurrentSession().createQuery("from Movimiento m where m.cuenta.id=:idCuenta").setLong("idCuenta", idCuenta).list();
	}

	@Override
	public List<Movimiento> ObtenerMovimiento(Long idCuenta, String nombre,
			int tipo) {
		
		List<Movimiento> movimientos = null;

		Session sesion = sessionFactory.getCurrentSession();
		
		Criteria criteria = sesion.createCriteria(Movimiento.class,"movimiento");
		criteria.createAlias("movimiento.cuenta", "cuenta");
		criteria.createAlias("cuenta.cliente", "cliente");
		//Criteria criteria2 = sesion.createCriteria(Cliente.class);

		if ((nombre != null && !nombre.isEmpty())
				|| (tipo==1 || tipo==2)) {

			if (nombre != null && !nombre.isEmpty()) {
				criteria.add(Restrictions.like("cliente.nombre", "%" + nombre + "%"));
				//criteria2.add(Restrictions.like("nombre", "%" + nombre + "%"));
			}
			if (tipo==1 || tipo==2) {
				criteria.add(Restrictions.eq("movimiento.tipo", tipo));
				//criteria2.add(Restrictions.like("direccion", direccion, MatchMode.ANYWHERE));
			}			
		}
		
		//Long nroRegistros = (Long)criteria2.setProjection(Projections.rowCount()).uniqueResult();
		
		//criteria2.setFirstResult((pagina-1)*regXPagina);
		//criteria2.setMaxResults(regXPagina);
		
		movimientos = criteria.list();
		
		//long paginas =  nroRegistros/regXPagina;
		
		//if(nroRegistros % regXPagina!= 0)
		//{
		//	paginas++;
		//}

		//return new Paginas<Cliente>((int)paginas, clientes);
		return movimientos;
	}

}
