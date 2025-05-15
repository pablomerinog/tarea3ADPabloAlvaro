package com.Tarea3AD.Tarea3AD_PabloMerino.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.Carnet;
import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.Peregrino;
import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.Usuario;
import com.Tarea3AD.Tarea3AD_PabloMerino.repository.CarnetRepository;

@Service
public class CarnetService {

	@Autowired
	private CarnetRepository carnetRepository;

	public Carnet save(Carnet carnet) {
		return carnetRepository.save(carnet);
	}

	public Carnet update(Carnet entity) {
		return carnetRepository.save(entity);
	}

	public void delete(Carnet entity) {
		carnetRepository.delete(entity);
	}

	public void delete(Long id) {
		carnetRepository.deleteById(id);
	}

	public Carnet find(Long id) {
		return carnetRepository.findById(id).get();
	}

	public List<Carnet> findAll() {
		return carnetRepository.findAll();
	}

	public void deleteInBatch(List<Carnet> carnet) {
		carnetRepository.deleteAll(carnet);
	}

}
