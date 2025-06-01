package com.Tarea3AD.Tarea3AD_PabloMerino.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.beans.property.SimpleBooleanProperty;

public class Servicio {
	private Long idServicio;
	private String nombreServicio;
	private double precio;
	List<Long> idParadas = new ArrayList<>();
	List<Long> ConjuntoContratado = new ArrayList<>();

//	  private final SimpleBooleanProperty seleccionado = new SimpleBooleanProperty(false);

	private transient boolean seleccionado = false;

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
	    if (this.idParadas == null) {
	        return Collections.emptyList();  // devuelve lista vacía en vez de null
	    }
	    return this.idParadas;
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

//    public boolean isSeleccionado() {
//        return seleccionado.get();
//    }
//
//    public void setSeleccionado(boolean seleccionado) {
//        this.seleccionado.set(seleccionado);
//    }
//
//    public SimpleBooleanProperty seleccionadoProperty() {
//        return seleccionado;
//    }

	public boolean isSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(boolean seleccionado) {
		this.seleccionado = seleccionado;
	}

	@Override
	public String toString() {
		return "Servicio [idServicio=" + idServicio + ", nombreServicio=" + nombreServicio + ", precio=" + precio
				+ ", idParadas=" + idParadas + ", ConjuntoContratado=" + ConjuntoContratado + "]";
	}

}
