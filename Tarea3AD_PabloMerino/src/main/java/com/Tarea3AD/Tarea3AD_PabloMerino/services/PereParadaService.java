package com.Tarea3AD.Tarea3AD_PabloMerino.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.PereParada;
import com.Tarea3AD.Tarea3AD_PabloMerino.repository.PereParadaRepository;

@Service
public class PereParadaService {

	@Autowired
	private PereParadaRepository pereParadaRepository;
	

	public PereParada save(PereParada entity) {
		return pereParadaRepository.save(entity);
	}

	public PereParada update(PereParada entity) {
		return pereParadaRepository.save(entity);
	}

	public void delete(PereParada entity) {
		pereParadaRepository.delete(entity);
	}

	public void delete(Long id) {
		pereParadaRepository.deleteById(id);
	}

	public PereParada find(Long id) {
		return pereParadaRepository.findById(id).get();
	}

	public List<PereParada> findAll() {
		return pereParadaRepository.findAll();
	}
	
//	public List<PereParada> findPeregrino(Long id) {
//        return pereParadaRepository.findPeregrino(id);
//    }

}
