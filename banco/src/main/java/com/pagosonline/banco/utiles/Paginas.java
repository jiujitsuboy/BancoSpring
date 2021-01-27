package com.pagosonline.banco.utiles;

import java.util.*;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;

public class Paginas<T> {
	private int nroPaginas;
	private List<T> resultado;
	
	public Paginas(int nroPaginas, List<T> resultado)
	{
		this.nroPaginas = nroPaginas;
		this.resultado = resultado;
	}
	
	public int getNroPaginas()
	{
		return nroPaginas;
	}
	
	public List<T> getResultado()
	{
		return resultado;
	}
	public static <T> Paginas<T> Paginacion(Criteria criConsulta,Criteria criCuenta,int pagina)
	{
		final int regXPagina = 2;
		long paginas;				
		
		Long nroRegistros = (Long) criCuenta.setProjection(Projections.rowCount()).uniqueResult();								
		
		criConsulta.setFirstResult((pagina-1)*regXPagina);
		criConsulta.setMaxResults(regXPagina);		
		
		List<T> resultado = criConsulta.list();			
		
		paginas = nroRegistros/regXPagina;

		if(nroRegistros % regXPagina!= 0)
		{
			 paginas++;
		}
		
		return new Paginas<T>((int)paginas,resultado);
	}
}
