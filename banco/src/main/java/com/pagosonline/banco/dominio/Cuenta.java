/**
 * 
 */
package com.pagosonline.banco.dominio;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author Jose Alejandro Niño Mora
 * 
 */
@Entity
@Table(name = "cuenta")
@NamedQueries({
		@NamedQuery(name = "Cuenta.EncontrarXID", query = "select distinct a from Cuenta a where a.id = :id")
})

public class Cuenta {

	private Long id;
	private String numero;
	private int saldo;
	private Cliente cliente;
	private List<Movimiento> movimientos;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@NotEmpty(message = "Se requiere el número de la cuenta.")
	@Size(max = 20, message = "El número de la cuenta no puede ser mayor a 20 caracteres")
	@Column(name = "numero")
	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}
		
	@Column(name = "saldo")
	@Min(value=0, message="El Saldo no puedo ser negativo")
	@Max(value=Integer.MAX_VALUE, message="El valor del saldo es demasiado grande")
	public int getSaldo() {
		return saldo;
	}

	public void setSaldo(int saldo) {
		this.saldo = saldo;
	}

	
	@ManyToOne
	@JoinColumn(name="id_cliente")
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente= cliente;
	}
	
	@OneToMany(mappedBy="cuenta",cascade=CascadeType.ALL,orphanRemoval=true)
	public List<Movimiento> getMovimiento() {
		return movimientos;
	}

	public void setMovimiento(List<Movimiento> movimientos) {
		this.movimientos = movimientos;
	}
}
