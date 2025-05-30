package com.Tarea3AD.Tarea3AD_PabloMerino.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.ConjuntoContratado;
import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.Servicio;
import com.Tarea3AD.Tarea3AD_PabloMerino.repository.Db4oRepository;
import com.db4o.ObjectContainer;
import com.db4o.query.Predicate;

@Service
public class db4oService {

	private final Db4oRepository<Servicio> servicioRepo;
	private final Db4oRepository<ConjuntoContratado> conjuntoRepo;

	public db4oService(ObjectContainer db) {
		this.servicioRepo = new Db4oRepository<>(db, Servicio.class);
		this.conjuntoRepo = new Db4oRepository<>(db, ConjuntoContratado.class);
	}

	// Servicio
	public void guardarServicio(Servicio servicio) {
		servicioRepo.save(servicio);
	}

	public List<Servicio> listarServicios() {
		return servicioRepo.findAll();
	}

	public Servicio buscarServicioPorId(Long id) {
		return servicioRepo.findByPredicate(new Predicate<Servicio>() {
			@Override
			public boolean match(Servicio c) {
				return c.getIdServicio().equals(id);
			}
		});

	}

	// ConjuntoContratado
	public void guardarConjunto(ConjuntoContratado conjunto) {
		conjuntoRepo.save(conjunto);
	}

	public List<ConjuntoContratado> listarConjuntos() {
		return conjuntoRepo.findAll();
	}

	public ConjuntoContratado buscarConjuntoPorId(Long id) {
		return conjuntoRepo.findByPredicate(new Predicate<ConjuntoContratado>() {
			@Override
			public boolean match(ConjuntoContratado c) {
				return c.getId().equals(id);
			}
		});

	}
}
