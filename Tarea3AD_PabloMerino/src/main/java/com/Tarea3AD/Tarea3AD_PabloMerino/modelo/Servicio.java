package com.Tarea3AD.Tarea3AD_PabloMerino.modelo;

import java.util.ArrayList;
import java.util.List;

public class Servicio {
	private Long idServicio;
	private String nombreServicio;
	private double precio;
	List<Long> idParadas = new ArrayList<>();
	List<Long> ConjuntoContratado = new ArrayList<>();

	public Servicio(Long idServicio, String nombreServicio, double precio) {
		super();
		this.idServicio = idServicio;
		this.nombreServicio = nombreServicio;
		this.precio = precio;
	}

	public Servicio(Long idServicio, String nombreServicio, double precio, List<Long> idParadas,
			List<Long> conjuntoContratado) {
		super();
		this.idServicio = idServicio;
		this.nombreServicio = nombreServicio;
		this.precio = precio;
		this.idParadas = idParadas;
		ConjuntoContratado = conjuntoContratado;
	}

	
	
	public Servicio(Long idServicio, String nombreServicio, double precio, List<Long> idParadas) {
		super();
		this.idServicio = idServicio;
		this.nombreServicio = nombreServicio;
		this.precio = precio;
		this.idParadas = idParadas;
	}

	public Servicio() {
		super();
	}

	public Long getIdServicio() {
		return idServicio;
	}

	public void setIdServicio(Long idServicio) {
		this.idServicio = idServicio;
	}

	public String getNombreServicio() {
		return nombreServicio;
	}

	public void setNombreServicio(String nombreServicio) {
		this.nombreServicio = nombreServicio;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public List<Long> getIdParadas() {
		return idParadas;
	}

	public void setIdParadas(List<Long> idParadas) {
		this.idParadas = idParadas;
	}

	public List<Long> getConjuntoContratado() {
		return ConjuntoContratado;
	}

	public void setConjuntoContratado(List<Long> conjuntoContratado) {
		ConjuntoContratado = conjuntoContratado;
	}

	@Override
	public String toString() {
		return "Servicio [idServicio=" + idServicio + ", nombreServicio=" + nombreServicio + ", precio=" + precio
				+ ", idParadas=" + idParadas + ", ConjuntoContratado=" + ConjuntoContratado + "]";
	}

}
