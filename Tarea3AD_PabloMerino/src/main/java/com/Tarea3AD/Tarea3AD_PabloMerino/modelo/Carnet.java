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
@Table(name = "Carnets")
public class Carnet {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idCarnet", updatable = false, nullable = false)
	private Long id;

	@Column(name = "fechaexp", nullable = false)
	private LocalDate fechaexp = LocalDate.now();

	@Column(name = "distancia", nullable = false)
	private double distancia = 0.0;

	@Column(name = "nvips", nullable = false)
	private int nvips = 0;

	@ManyToOne(optional = false)
	@JoinColumn(name = "paradaInicial", referencedColumnName = "idParada")
	private Parada paradaIncial;

	public Carnet(Long id, LocalDate fechaexp, double distancia, int nvips, Parada paradaIncial) {
		super();
		this.id = id;
		this.fechaexp = fechaexp;
		this.distancia = distancia;
		this.nvips = nvips;
		this.paradaIncial = paradaIncial;
	}

	public Carnet(LocalDate fechaexp, double distancia, int nvips, Parada paradaIncial) {
		super();
		this.fechaexp = fechaexp;
		this.distancia = distancia;
		this.nvips = nvips;
		this.paradaIncial = paradaIncial;
	}

	public Carnet() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getFechaexp() {
		return fechaexp;
	}

	public void setFechaexp(LocalDate fechaexp) {
		this.fechaexp = fechaexp;
	}

	public double getDistancia() {
		return distancia;
	}

	public void setDistancia(double distancia) {
		this.distancia = distancia;
	}

	public int getNvips() {
		return nvips;
	}

	public void setNvips(int nvips) {
		this.nvips = nvips;
	}

	public Parada getParadaIncial() {
		return paradaIncial;
	}

	public void setParadaIncial(Parada paradaIncial) {
		this.paradaIncial = paradaIncial;
	}

	@Override
	public String toString() {
		return "Carnet [id=" + id + ", fechaexp=" + fechaexp + ", distancia=" + distancia + ", nvips=" + nvips
				+ ", paradaIncial=" + paradaIncial + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(distancia, fechaexp, id, nvips, paradaIncial);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Carnet other = (Carnet) obj;
		return Double.doubleToLongBits(distancia) == Double.doubleToLongBits(other.distancia)
				&& Objects.equals(fechaexp, other.fechaexp) && Objects.equals(id, other.id) && nvips == other.nvips
				&& Objects.equals(paradaIncial, other.paradaIncial);
	}
	
	

}
