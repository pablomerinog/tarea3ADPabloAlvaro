package com.Tarea3AD.Tarea3AD_PabloMerino.modelo;

import java.util.List;

public class ConjuntoContratado {
	private Long id;
    private double precioTotal;
    private char modoPago;
    private String extra = null;

    private List<Servicio> servicios;

	public ConjuntoContratado(Long id, double precioTotal, char modoPago, String extra, List<Servicio> servicios) {
		super();
		this.id = id;
		this.precioTotal = precioTotal;
		this.modoPago = modoPago;
		this.extra = extra;
		this.servicios = servicios;
	}
	
	

	public ConjuntoContratado(Long id, double precioTotal, char modoPago, String extra) {
		super();
		this.id = id;
		this.precioTotal = precioTotal;
		this.modoPago = modoPago;
		this.extra = extra;
	}



	public ConjuntoContratado() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getPrecioTotal() {
		return precioTotal;
	}

	public void setPrecioTotal(double precioTotal) {
		this.precioTotal = precioTotal;
	}

	public char getModoPago() {
		return modoPago;
	}

	public void setModoPago(char modoPago) {
		this.modoPago = modoPago;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	public List<Servicio> getServicios() {
		return servicios;
	}

	public void setServicios(List<Servicio> servicios) {
		this.servicios = servicios;
	}

	@Override
	public String toString() {
		return "ConjuntoContratado [id=" + id + ", precioTotal=" + precioTotal + ", modoPago=" + modoPago + ", extra="
				+ extra + ", servicios=" + servicios + "]";
	}

   
}
