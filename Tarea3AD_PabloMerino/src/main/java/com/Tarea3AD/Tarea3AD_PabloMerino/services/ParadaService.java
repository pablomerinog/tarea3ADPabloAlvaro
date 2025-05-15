package com.Tarea3AD.Tarea3AD_PabloMerino.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.Parada;
import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.PereParada;
import com.Tarea3AD.Tarea3AD_PabloMerino.repository.ParadaRepository;

@Service
public class ParadaService {

	@Autowired
	private ParadaRepository paradaRepository;
	

	public Parada save(Parada entity) {
		return paradaRepository.save(entity);
	}

	public Parada update(Parada entity) {
		return paradaRepository.save(entity);
	}

	public void delete(Parada entity) {
		paradaRepository.delete(entity);
	}

	public void delete(Long id) {
		paradaRepository.deleteById(id);
	}

	public Parada find(Long id) {
		return paradaRepository.findById(id).get();
	}

	public List<Parada> findAll() {
		return paradaRepository.findAll();
	}

	public Optional<Parada> findByNombre(String nombre) {
	    return paradaRepository.findByNombre(nombre);
	}
	
//	public List<Parada> findByidUsuario(Long idUsuario){
//		return paradaRepository.findByidUsuario(idUsuario);
//	}
//	
	public void deleteInBatch(List<Parada> users) {
		paradaRepository.deleteAll(users);
	}
	
	

}
