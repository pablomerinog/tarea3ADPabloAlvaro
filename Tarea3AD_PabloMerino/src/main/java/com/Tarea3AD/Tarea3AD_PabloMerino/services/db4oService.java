package com.Tarea3AD.Tarea3AD_PabloMerino.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.ConjuntoContratado;
import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.Servicio;
import com.Tarea3AD.Tarea3AD_PabloMerino.repository.Db4oRepository;
import com.Tarea3AD.Tarea3AD_PabloMerino.utils.copy.ContadorID;
import com.db4o.ObjectContainer;
import com.db4o.query.Predicate;

@Service
public class db4oService {

	private final Db4oRepository<Servicio> servicioRepo;
	private final Db4oRepository<ConjuntoContratado> conjuntoRepo;
	private final Db4oRepository<ContadorID> contadorRepo;

	public db4oService(ObjectContainer db) {
		this.servicioRepo = new Db4oRepository<>(db, Servicio.class);
		this.conjuntoRepo = new Db4oRepository<>(db, ConjuntoContratado.class);
		this.contadorRepo = new Db4oRepository<>(db, ContadorID.class);
		
	}


	public long getNuevoId() {
	    ContadorID contador = contadorRepo.findByPredicate(new Predicate<ContadorID>() {
	        @Override
	        public boolean match(ContadorID c) {
	            return true; // Solo queremos el primero, da igual cuál
	        }
	    });

	    if (contador == null) {
	        contador = new ContadorID();
	    }

	    long id = contador.getSiguienteId();
	    contadorRepo.save(contador); // Guarda el contador actualizado
	    return id;
	}

    
	// Servicio
	public void guardarServicio(Servicio servicio) {
		servicioRepo.save(servicio);
	}

	public void eliminarServicio(Servicio servicio) {
		servicioRepo.delete(servicio);
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

	public List<Servicio> listarServiciosPorIdParada(Long idParada) {
		return servicioRepo.findAll().stream()
				.filter(servicio -> servicio.getIdParadas() != null && servicio.getIdParadas().contains(idParada))
				.toList();
	}

	public void actualizarServicio(Servicio servicioActualizado) {
		servicioRepo.update(servicioActualizado, new Predicate<Servicio>() {
			@Override
			public boolean match(Servicio c) {
				return c.getIdServicio().equals(servicioActualizado.getIdServicio());
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
