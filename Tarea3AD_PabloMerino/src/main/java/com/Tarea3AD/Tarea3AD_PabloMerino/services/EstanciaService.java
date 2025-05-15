package com.Tarea3AD.Tarea3AD_PabloMerino.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.Carnet;
import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.Estancia;
import com.Tarea3AD.Tarea3AD_PabloMerino.repository.EstanciaRepository;

@Service
public class EstanciaService {

	@Autowired
	private EstanciaRepository estanciaRepository;

	public Estancia save(Estancia carnet) {
		return estanciaRepository.save(carnet);
	}

	public Estancia update(Estancia entity) {
		return estanciaRepository.save(entity);
	}

	public void delete(Estancia entity) {
		estanciaRepository.delete(entity);
	}

	public void delete(Long id) {
		estanciaRepository.deleteById(id);
	}

	public Estancia find(Long id) {
		return estanciaRepository.findById(id).get();
	}

	public List<Estancia> findAll() {
		return estanciaRepository.findAll();
	}

	public void deleteInBatch(List<Estancia> estancia) {
		estanciaRepository.deleteAll(estancia);
	}

}
