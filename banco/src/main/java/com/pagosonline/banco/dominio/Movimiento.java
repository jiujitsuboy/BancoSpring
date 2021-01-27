package com.pagosonline.banco.dominio;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.Range;

@Entity
@Table(name = "movimiento")
@NamedQueries({
		@NamedQuery(name = "Movimiento.EncontrarXID", query = "select distinct m from Movimiento m where m.id = :id")
})
public class Movimiento {

	private Long id;
	private int tipo;
	private Date fecha;
	private int valor;
	private int saldo;
	private Cuenta cuenta;
	
	public Movimiento()
	{
		fecha = new Date();		
	}
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Range(min=1, max=2, message="Los tipos de operaciones validas son debito y credito")
	@Column(name = "tipo")
	public int getTipo() {
		return tipo;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	
	@Column(name = "fecha")
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	@Range(min=1, max=Integer.MAX_VALUE, message="El valor del movimiento debe ser un entero positivo.")
	@Column(name = "valor")
	public int getValor() {
		return valor;
	}
	public void setValor(int valor) {
		this.valor = valor;
	}
	@ManyToOne
	@JoinColumn(name="id_cuenta")
	public Cuenta getCuenta() {
		return cuenta;
	}
	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}

	@Column(name="saldo")
	public int getSaldo() {
		return saldo;
	}

	public void setSaldo(int saldo) {
		this.saldo = saldo;
	}	
}
