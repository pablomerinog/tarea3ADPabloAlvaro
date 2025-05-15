package com.Tarea3AD.Tarea3AD_PabloMerino.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.Peregrino;
import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.Usuario;

public interface PeregrinoRepository  extends JpaRepository<Peregrino, Long> {
	  Optional<Peregrino> findBynombrePeregrino(String nombrePeregrino);
	  
	  Optional<Peregrino> findByUsuario(Usuario usuario);
//	Optional<Peregrino> findByNombreUsuario(String nombreUsuario){
		
	
}
