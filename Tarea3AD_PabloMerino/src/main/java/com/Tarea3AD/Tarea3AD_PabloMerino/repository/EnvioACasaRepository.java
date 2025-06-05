package com.Tarea3AD.Tarea3AD_PabloMerino.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.EnvioACasa;

@Repository
public interface EnvioACasaRepository extends JpaRepository<EnvioACasa, Long> {
    
    List<EnvioACasa> findByIdParada(long idParada);
}