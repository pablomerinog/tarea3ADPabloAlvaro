package com.Tarea3AD.Tarea3AD_PabloMerino.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.Parada;
import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.Usuario;

public interface ParadaRepository extends JpaRepository<Parada, Long> {

	Optional<Parada> findByNombre(String nombre);
	Parada findByNombreAndRegion(String nombre, char region);

	
	
//	List<Parada> findByidUsuario(Long idUsuario);
}
