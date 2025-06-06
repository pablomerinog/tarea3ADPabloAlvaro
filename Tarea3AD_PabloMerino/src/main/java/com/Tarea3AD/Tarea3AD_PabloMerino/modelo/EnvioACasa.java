package com.Tarea3AD.Tarea3AD_PabloMerino.modelo;

import java.util.Arrays;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class EnvioACasa extends Servicio {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private double peso;
	private int[] volumen = new int[3];
	private boolean urgente;
	private long idParada;
	@Embedded
	private Direccion direccion;

	public EnvioACasa( double precio, Long id, double peso, int[] volumen,
			boolean urgente, long idParada, Direccion direccion) {
		this.id = id;
		this.peso = peso;
		this.volumen = volumen;
		this.urgente = urgente;
		this.idParada = idParada;
		this.direccion = direccion;
	}

	public EnvioACasa(Long idServicio, String nombreServicio, double precio) {
		super(idServicio, nombreServicio, precio);
	}

	
	public EnvioACasa() {
		super();
	}

	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	public int[] getVolumen() {
		return volumen;
	}

	public void setVolumen(int[] volumen) {
		this.volumen = volumen;
	}

	public boolean isUrgente() {
		return urgente;
	}

	public void setUrgente(boolean urgente) {
		this.urgente = urgente;
	}

	public long getIdParada() {
		return idParada;
	}

	public void setIdParada(long idParada) {
		this.idParada = idParada;
	}

	public Direccion getDireccion() {
		return direccion;
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}
	

	@Override
	public String toString() {
		return "EnvioACasa [id=" + id + ", peso=" + peso + ", volumen=" + Arrays.toString(volumen) + ", urgente="
				+ urgente + ", idParada=" + idParada + ", direccion=" + direccion + "]";
	}

}
