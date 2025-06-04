package com.Tarea3AD.Tarea3AD_PabloMerino.modelo;

import java.util.Collections;
import java.util.List;

import javafx.beans.property.SimpleBooleanProperty;

public class ServicioFX {
	private Servicio servicio;
	private SimpleBooleanProperty seleccionado;

	public ServicioFX(Servicio servicio) {
		this.servicio = servicio;
		this.seleccionado = new SimpleBooleanProperty(servicio.isSeleccionado());

		
		seleccionado.addListener((obs, oldVal, newVal) -> servicio.setSeleccionado(newVal));
	}
	

	public Long getIdServicio() {
        return servicio.getIdServicio();
    }
	
	public void setPrecio(Long idServicio) {
		this.servicio.setIdServicio(idServicio);
	}
	
	public String getNombreServicio() {
		return servicio.getNombreServicio();
	}
	
	public void setNombreServicio(String nombreServicio) {
		this.servicio.setNombreServicio(nombreServicio);
	}

	
	public double getPrecio() {
	    return servicio.getPrecio();
	}
	
	public void setPrecio(double precio) {
		this.servicio.setPrecio(precio);
	}
	
	public List<Long> getIdParadas() {
		if (servicio == null) {
	        return Collections.emptyList();
	    }
	    List<Long> idParadas = servicio.getIdParadas();
	    return idParadas != null ? idParadas : Collections.emptyList();
	}
	

	
	public List<Long> getConjuntoContratado() {
		return servicio.getConjuntoContratado();
	}
	
	public Servicio getServicio() {
		return servicio;
	}
	
	
	
	

	public SimpleBooleanProperty seleccionadoProperty() {
		return seleccionado;
	}

	public boolean isSeleccionado() {
		return seleccionado.get();
	}

	public void setSeleccionado(boolean seleccionado) {
		this.seleccionado.set(seleccionado);
	}
}
