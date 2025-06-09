package com.Tarea3AD.Tarea3AD_PabloMerino.services;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Tarea3AD.Tarea3AD_PabloMerino.repository.ExistDBRepository;

@Service
public class ExistDBService {

	@Autowired
	private ExistDBRepository existDBRepository;

	public void guardarCarnetExistDB(String nombreParada, File carnet) {
		existDBRepository.guardarCarnetExistDB(nombreParada, carnet);
	}

	public List<String> listarCarnetsPorParada(String nombreParada) {
		return existDBRepository.listarCarnetsPorParada(nombreParada);
	}
}
