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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author Administrator
 * 
 */
@Entity
@Table(name = "cliente")
@NamedQueries({
		@NamedQuery(name = "Cliente.EncontrarXID", query = "select distinct c from Cliente c where c.id = :id")
})

public class Cliente {

	private Long id;
	private String nombre;
	private String direccion;
	private String telefono;
	private List<Cuenta> cuentas;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@NotEmpty(message = "Se requiere el nombre del cliente.")
	@Size(max = 50, message = "El nombre no puede ser mayor a 50 caracteres")
	@Column(name = "nombre")
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Size(max = 20, message = "La dirección no puede ser mayor a 20 caracteres")
	@Column(name = "direccion")
	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	@Size(max = 20, message = "La dirección no puede ser mayor a 10 caracteres")
	@Column(name = "telefono")
	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	@OneToMany(mappedBy="cliente",cascade=CascadeType.ALL,orphanRemoval=true)
	public List<Cuenta> getCuentas()
	{
		return cuentas;
	}
	
	public void setCuentas(List<Cuenta> cuentas)
	{
		this.cuentas = cuentas; 
	}
}
