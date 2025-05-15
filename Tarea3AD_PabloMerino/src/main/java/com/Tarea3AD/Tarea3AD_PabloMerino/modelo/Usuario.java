package com.Tarea3AD.Tarea3AD_PabloMerino.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Usuarios")
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idUsuario", updatable = false, nullable = false)
	private Long id;
	
	@Column(name = "nombreUsuario", nullable = false)
	private String nombreUsuario;
	
	@Column(name = "contrasena", nullable = false)
	private String contrasena;
	
	@Column(name = "perfil", nullable = false)
	private String perfil;

	public Usuario(Long id, String nombreUsuario, String contrasena, String perfil) {
		super();
		this.id = id;
		this.nombreUsuario = nombreUsuario;
		this.contrasena = contrasena;
		this.perfil = perfil;
	}

	public Usuario(String nombreUsuario, String contrasena, String perfil) {
		super();
		this.nombreUsuario = nombreUsuario;
		this.contrasena = contrasena;
		this.perfil = perfil;
	}

	public Usuario() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getnombreUsuario() {
		return nombreUsuario;
	}

	public void setnombreUsuario(String username) {
		this.nombreUsuario = username;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombreUsuario=" + nombreUsuario + ", contrasena=" + contrasena + ", perfil="
				+ perfil + "]";
	}

	
	
}
