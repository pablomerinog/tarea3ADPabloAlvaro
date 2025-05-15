package com.Tarea3AD.Tarea3AD_PabloMerino.modelo;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Estancias")
public class Estancia {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idEstancia", updatable = false, nullable = false)
	private Long id;

	@Column(name = "fecha", nullable = false)
	private LocalDate fecha;

	@Column(name = "vip", nullable = false)
	private boolean vip = false;

	@ManyToOne(optional = false)
	@JoinColumn(name = "idPeregrino", referencedColumnName = "idPeregrino")
	private Peregrino peregrino;

	@ManyToOne(optional = false)
	@JoinColumn(name = "idParada", referencedColumnName = "idParada")
	private Parada parada;

	public Estancia(Long id, LocalDate fecha, boolean vip, Parada parada, Peregrino peregrino) {
		super();
		this.id = id;
		this.fecha = fecha;
		this.vip = vip;
		this.parada = parada;
		this.peregrino = peregrino;
	}

	public Estancia(LocalDate fecha, boolean vip, Parada parada, Peregrino peregrino) {
		super();
		this.fecha = fecha;
		this.vip = vip;
		this.parada = parada;
		this.peregrino = peregrino;
	}

	public Estancia(Long id, LocalDate fecha, boolean vip, Parada parada) {
		super();
		this.id = id;
		this.fecha = fecha;
		this.vip = vip;
		this.parada = parada;
	}

	public Estancia() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public boolean isVip() {
		return this.vip;
	}

	public void setVip(boolean vip) {
		this.vip = vip;
	}

	public Peregrino getPeregrino() {
		return peregrino;
	}

	public void setPeregrino(Peregrino peregrino) {
		this.peregrino = peregrino;
	}

	public Parada getParada() {
		return parada;
	}

	public void setParada(Parada parada) {
		this.parada = parada;
	}

	@Override
	public String toString() {
		return "Estancia [id=" + id + ", fecha=" + fecha + ", vip=" + vip + ", peregrino=" + peregrino + ", parada="
				+ parada + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(fecha, id, parada, peregrino, vip);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Estancia other = (Estancia) obj;
		return Objects.equals(fecha, other.fecha) && Objects.equals(id, other.id)
				&& Objects.equals(parada, other.parada) && Objects.equals(peregrino, other.peregrino)
				&& vip == other.vip;
	}
	
	

}
