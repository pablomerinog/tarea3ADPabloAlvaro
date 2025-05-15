package com.Tarea3AD.Tarea3AD_PabloMerino.modelo;

import org.springframework.stereotype.Component;

@Component
public class Sesion {
	private Perfil perfil;
	private String nombreUsuario;
	private Usuario usuIniciado;
	
	public Sesion(Perfil perfil, String nombre) {
		super();
		this.perfil = perfil;
		this.nombreUsuario = nombre;
	}

	public Sesion() {

	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}


	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public Usuario getUsuIniciado() {
		return usuIniciado;
	}

	public void setUsuIniciado(Usuario usuario) {
		this.usuIniciado = usuario;
	}
	
	

}
