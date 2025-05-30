package com.Tarea3AD.Tarea3AD_PabloMerino.modelo;

public class Servicio {
	private Long idServicio;
	private String nombreServicio;
	private double precio;
	public Servicio(Long idServicio, String nombreServicio, double precio) {
		super();
		this.idServicio = idServicio;
		this.nombreServicio = nombreServicio;
		this.precio = precio;
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
	@Override
	public String toString() {
		return "Servicio [idServicio=" + idServicio + ", nombreServicio=" + nombreServicio + ", precio=" + precio + "]";
	}
	
	
	
}
