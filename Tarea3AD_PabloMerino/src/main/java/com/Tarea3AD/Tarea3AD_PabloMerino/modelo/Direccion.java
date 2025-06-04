package com.Tarea3AD.Tarea3AD_PabloMerino.modelo;



import jakarta.persistence.*;

@Embeddable
public class Direccion {

	private String direccion;
	private String localidad;

	public Direccion() {
		super();
	}

	public Direccion(String direccion, String localidad) {
		super();
		this.direccion = direccion;
		this.localidad = localidad;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}
}
