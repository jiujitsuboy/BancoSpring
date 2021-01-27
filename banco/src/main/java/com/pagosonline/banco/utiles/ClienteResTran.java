package com.pagosonline.banco.utiles;

import java.util.Date;

public class ClienteResTran {
	
	private String cliente;
	private String numeroCuenta;
	private int tipoMovimiento;
	private int valorMovimiento;
	private int saldoTemporal;
	private  Date fecha;
	
	public ClienteResTran(String cliente,String numeroCuenta,int tipoMovimiento,int valorMovimiento,int saldoTemporal,Date fecha)
	{
		this.cliente = cliente;
		this.numeroCuenta = numeroCuenta;
		this.tipoMovimiento = tipoMovimiento;
		this.valorMovimiento = valorMovimiento;
		this.saldoTemporal = saldoTemporal;
	    this.fecha = fecha;
	}
	
	public String getNumeroCuenta() {
		return numeroCuenta;
	}

	public int getTipoMovimiento() {
		return tipoMovimiento;
	}

	public int getValorMovimiento() {
		return valorMovimiento;
	}

	public int getSaldoTemporal() {
		return saldoTemporal;
	}

	public String getCliente()
	{
		return cliente;
	}
	
	public Date getFecha()
	{
		return fecha;
	}

}
