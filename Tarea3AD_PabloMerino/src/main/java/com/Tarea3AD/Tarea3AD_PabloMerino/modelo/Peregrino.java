package com.Tarea3AD.Tarea3AD_PabloMerino.modelo;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Peregrinos")
public class Peregrino {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idPeregrino", updatable = false, nullable = false)
	private Long id;

	@Column(name = "nombrePeregrino", nullable = false)
	private String nombrePeregrino;

	@Column(name = "nacionalidad", nullable = false)
	private String nacionalidad;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "carnet", referencedColumnName = "idCarnet", nullable = false)
	private Carnet  carnet;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "usuario", referencedColumnName = "idUsuario", nullable = false )
	private Usuario  usuario;
//	@Column(name = "usuario", nullable = false)
//	private Long usuario;

	@OneToMany(mappedBy = "peregrino", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//	private Set<Parada> paradas = new HashSet<Parada>();
	private Set<PereParada> pereParadas = new HashSet<>();

	@OneToMany(mappedBy = "peregrino", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Estancia> estancias = new HashSet<Estancia>();


	public Peregrino(Long id, String nombrePeregrino, String nacionalidad, Carnet  carnet,Usuario  usuario,
			Set<PereParada> pereParadas, Set<Estancia> estancias) {
		super();
		this.id = id;
		this.nombrePeregrino = nombrePeregrino;
		this.nacionalidad = nacionalidad;
		this.carnet = carnet;
		this.usuario = usuario;
		this.pereParadas = pereParadas;
		this.estancias = estancias;
	}

	public Peregrino(String nombrePeregrino, String nacionalidad, Carnet  carnet, Usuario  usuario) {
		super();
		this.nombrePeregrino = nombrePeregrino;
		this.nacionalidad = nacionalidad;
		this.carnet = carnet;
		this.usuario = usuario;
	}

	public Peregrino(String nombrePeregrino, String nacionalidad, Carnet  carnet) {
		super();
		this.nombrePeregrino = nombrePeregrino;
		this.nacionalidad = nacionalidad;
		this.carnet = carnet;
	}

	public Peregrino(Long id, String nombrePeregrino, String nacionalidad) {
		super();
		this.id = id;
		this.nombrePeregrino = nombrePeregrino;
		this.nacionalidad = nacionalidad;
	}

	public Peregrino() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombrePeregrino() {
		return nombrePeregrino;
	}

	public void setNombrePeregrino(String nombrePeregrino) {
		this.nombrePeregrino = nombrePeregrino;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public Carnet  getCarnet() {
		return carnet;
	}

	public void setCarnet(Carnet  carnet) {
		this.carnet = carnet;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

//	public Set<Parada> getParadas() {
//		return paradas;
//	}
//
//	public void setParadas(Set<Parada> paradas) {
//		this.paradas = paradas;
//	}

	public Set<Estancia> getEstancias() {
		return estancias;
	}

	public void setEstancias(Set<Estancia> estancias) {
		this.estancias = estancias;
	}

	public Set<PereParada> getPereParadas() {
		return pereParadas;
	}

	public void setPereParadas(Set<PereParada> pereParadas) {
		this.pereParadas = pereParadas;
	}

	@Override
	public String toString() {
		return "Peregrino [id=" + id + ", nombrePeregrino=" + nombrePeregrino + ", nacionalidad=" + nacionalidad
				+ ", carnet=" + carnet + ", usuario=" + usuario + ", pereParadas=" + pereParadas + ", estancias="
				+ estancias + "]";
	}

//	@Override
//	public String toString() {
//		return "Peregrino [id=" + id + ", nombrePeregrino=" + nombrePeregrino + ", nacionalidad=" + nacionalidad
//				+ ", carnet=" + carnet + ", usuario=" + usuario + ", estancias=" + estancias + ", paradas=" + paradas
//				+ "]";
//	}

}
