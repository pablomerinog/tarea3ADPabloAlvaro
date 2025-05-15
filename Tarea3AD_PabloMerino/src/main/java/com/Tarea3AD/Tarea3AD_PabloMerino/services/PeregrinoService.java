package com.Tarea3AD.Tarea3AD_PabloMerino.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.Carnet;
import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.Parada;
import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.Peregrino;
import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.Usuario;
import com.Tarea3AD.Tarea3AD_PabloMerino.repository.PeregrinoRepository;

@Service
public class PeregrinoService {

	@Autowired
	private PeregrinoRepository peregrinoRepository;

	public Peregrino save(Peregrino peregrino) {
		return peregrinoRepository.save(peregrino);
	}

	public Peregrino update(Peregrino entity) {
		return peregrinoRepository.save(entity);
	}

	public void delete(Peregrino entity) {
		peregrinoRepository.delete(entity);
	}

	public void delete(Long id) {
		peregrinoRepository.deleteById(id);
	}

	public Peregrino find(Long id) {
		return peregrinoRepository.findById(id).get();
	}

	public List<Peregrino> findAll() {
		return peregrinoRepository.findAll();
	}

	public void deleteInBatch(List<Peregrino> carnet) {
		peregrinoRepository.deleteAll(carnet);
	}

	public Optional<Peregrino> findBynombrePeregrino(String nombrePeregrino) {
	    return peregrinoRepository.findBynombrePeregrino(nombrePeregrino);
	}
	public Optional<Peregrino> findByUsuario(Usuario usuario){
		return peregrinoRepository.findByUsuario(usuario);
	}

//	public Optional<Peregrino> findByNombreUsuario(String nombreUsuario) {
//		
//		return  peregrinoRepository.findByNombreUsuario(nombreUsuario);
//	}
	
}
