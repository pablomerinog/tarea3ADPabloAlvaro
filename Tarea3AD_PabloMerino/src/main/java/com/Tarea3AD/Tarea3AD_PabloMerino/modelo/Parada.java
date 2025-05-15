package com.Tarea3AD.Tarea3AD_PabloMerino.modelo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Paradas")
public class Parada {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idParada", updatable = false, nullable = false)
	private Long id;

	@Column(name = "nombreParada", nullable = false)
	private String nombre;

	@Column(name = "region", nullable = false, length = 1)
	private char region;

	@Column(name = "responsable", nullable = false)
	private String responsable;

//	@OneToOne(cascade = CascadeType.ALL)
//	@JoinColumn(name = "usuario", referencedColumnName = "id_usuario", nullable = false)
//	private Usuario usuario;

	private Long idUsuario;
	
	@OneToMany(mappedBy = "parada", cascade = { CascadeType.MERGE } , fetch = FetchType.LAZY)
	private Set<PereParada> pereParada = new HashSet<>();

	public Parada(Long id, String nombre, char region, String responsable, Long idUsuario) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.region = region;
		this.responsable = responsable;
		this.idUsuario = idUsuario;
	}

	public Parada(String nombre, char region, String responsable, Long idUsuario) {
		super();
		this.nombre = nombre;
		this.region = region;
		this.responsable = responsable;
		this.idUsuario = idUsuario;
	}

	public Parada(Long id, String nombre, char region) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.region = region;
	}

	public Parada() {
		super();
	}

	public Parada(long id, String nombre, char region, String responsable) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.region = region;
		this.responsable = responsable;
	}

	public Parada(long id) {
		// TODO Auto-generated constructor stub
		super();
		this.id = id;
	}

	public Parada(String nombre, char region, String responsable) {
		super();

		this.nombre = nombre;
		this.region = region;
		this.responsable = responsable;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public char getRegion() {
		return region;
	}

	public void setRegion(char region) {
		this.region = region;
	}

	public String getResponsable() {
		return responsable;
	}

	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}



	@Override
	public String toString() {
		return "Parada [id=" + id + ", nombre=" + nombre + ", region=" + region + ", responsable=" + responsable
				+ ", idUsuario=" + idUsuario + ", pereParada=" + pereParada + "]";
	}

//	public Usuario getUsuario() {
//		return usuario;
//	}
//
//	public void setUsuario(Usuario usuario) {
//		this.usuario = usuario;
//	}
//
//	@Override
//	public String toString() {
//		return "Parada [id=" + id + ", nombre=" + nombre + ", region=" + region + ", responsable=" + responsable
//				+ ", usuario=" + usuario + "]";
//	}
	
	

}
