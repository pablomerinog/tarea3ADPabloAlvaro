package com.Tarea3AD.Tarea3AD_PabloMerino.repository;

import java.util.List;

import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.EnvioACasa;

import jakarta.persistence.EntityManager;

public class EnvioACasaRepository {

	private EntityManager em;

	public EnvioACasaRepository(EntityManager em) {
		this.em = em;
	}

	public void save(EnvioACasa envio) {
		em.persist(envio);
	}

	public List<EnvioACasa> findByIdParada(long idParada) {
		return em.createQuery("SELECT e FROM EnvioACasa e WHERE e.idParada = :idParada", EnvioACasa.class)
				.setParameter("idParada", idParada).getResultList();
	}
}