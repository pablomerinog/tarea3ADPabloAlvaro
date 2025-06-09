package com.Tarea3AD.Tarea3AD_PabloMerino.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Tarea3AD.Tarea3AD_PabloMerino.config.ObjectDBConfig;
import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.EnvioACasa;
import com.Tarea3AD.Tarea3AD_PabloMerino.repository.EnvioACasaRepository;
import com.db4o.ObjectContainer;

import jakarta.persistence.EntityManager;

@Service
public class EnvioACasaService {

//	@Autowired
//	private EnvioACasaRepository repository;

	public List<EnvioACasa> getAllEnvios() {
		EntityManager em = ObjectDBConfig.getEntityManager();
		List<EnvioACasa> lista = em.createQuery("SELECT e FROM EnvioACasa e", EnvioACasa.class).getResultList();
		em.close();
		return lista;
	}

//	public Optional<EnvioACasa> getEnvioById(Long id) {
//		return repository.findById(id);
//	}

	public EnvioACasa saveEnvio(EnvioACasa envio) {
		EntityManager em = ObjectDBConfig.getEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(envio);
			em.getTransaction().commit();
		} catch (Exception e) {
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();
			e.printStackTrace();
		} finally {
			em.close();
		}
		return envio;
	}

//	public void deleteEnvio(Long id) {
//		repository.deleteById(id);
//	}

	public List<EnvioACasa> findByIdParada(long idParada) {
		EntityManager em = ObjectDBConfig.getEntityManager();
		List<EnvioACasa> lista = em.createQuery("SELECT e FROM EnvioACasa e WHERE e.idParada = :id", EnvioACasa.class)
				.setParameter("id", idParada).getResultList();
		em.close();
		return lista;
	}

	public void cerrarConexion() {
		ObjectDBConfig.close();
	}

}