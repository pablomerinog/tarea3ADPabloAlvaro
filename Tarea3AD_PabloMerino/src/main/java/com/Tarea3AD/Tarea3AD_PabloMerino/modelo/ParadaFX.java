package com.Tarea3AD.Tarea3AD_PabloMerino.modelo;

import org.springframework.data.annotation.Transient;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class ParadaFX {
	private Parada parada;
	
	private SimpleBooleanProperty seleccionado;

	public ParadaFX(Parada parada) {
		this.parada = parada;
		this.seleccionado = new SimpleBooleanProperty(parada.isSeleccionado());

		seleccionado.addListener((obs, oldVal, newVal) -> parada.setSeleccionado(newVal));
	}

	public Parada getParada() {
		return parada;
	}

	public void setParada(Parada parada) {
		this.parada = parada;
	}

	public BooleanProperty getSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(SimpleBooleanProperty seleccionado) {
		this.seleccionado = seleccionado;
	}

	public boolean isSeleccionado() {
		return seleccionado.get();
	}

	public void setSeleccionado(boolean seleccionado) {
		this.seleccionado.set(seleccionado);
	}

	public SimpleBooleanProperty seleccionadoProperty() {
		return seleccionado;
	}
	
	public Long getIdParada() {
        return parada.getId();
    }
	
	public void setIdParada(Long id) {
		this.parada.setId(id);
	}
	
	public String getNombreParada() {
		return parada.getNombre();
	}
	
	public void setNombreParada(String parada) {
		this.parada.setNombre(parada);
	}

	
	public char getRegion() {
		return parada.getRegion();
	}
	
	public void setRegion(char region) {
		this.parada.setRegion(region);
	}
	

}
