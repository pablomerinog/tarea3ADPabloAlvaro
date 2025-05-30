package com.Tarea3AD.Tarea3AD_PabloMerino.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.Estancia;
import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.Parada;

public interface EstanciaRepository extends JpaRepository<Estancia, Long> {
	List<Estancia> findByParadaAndFechaBetween(Parada parada, LocalDate fechaInicio, LocalDate fechaFin);

	Set<Estancia> findByPeregrinoId(Long idPere);

}
