package com.Tarea3AD.Tarea3AD_PabloMerino.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.BackupCarnet;
import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.BackupCarnet.CarnetBackupJson;
import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.Estancia;
import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.Parada;
import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.PereParada;
import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.Peregrino;
import com.Tarea3AD.Tarea3AD_PabloMerino.repository.EstanciaRepository;
import com.Tarea3AD.Tarea3AD_PabloMerino.repository.MongoDBRepository;

import jakarta.transaction.Transactional;



@Service
public class MongoDBService {

	@Autowired
	private MongoDBRepository mongoDBRepository;
	
	@Autowired 
	private PeregrinoService peregrinoService;
	
	
	@Autowired
	private ParadaService paradaService;
	
	@Autowired
	private EstanciaService estanciaService;
	
	private BackupCarnet saveBackup(BackupCarnet backup) {
		return mongoDBRepository.save(backup);
	}
	
	public List<BackupCarnet> getAllBackups() {
		return mongoDBRepository.findAll();
	}
	
	 @Transactional
     public Set<Estancia> findByPeregrinoId(Long id) {
         Set<Estancia> estancias = estanciaService.findByPeregrinoId(id);
         for (Estancia e : estancias) {
             e.getParada().getNombre(); 
             e.getPeregrino().getNombrePeregrino();
         }
         return estancias;
     }
	
	public boolean backupAllCarnets() {
	    List<Peregrino> peregrino = peregrinoService.findAll();
	    List<CarnetBackupJson> listaBackup = new ArrayList<>();
	    boolean ret = false;

	    for(Peregrino p : peregrino) {
	        try {
	           
	            Set<PereParada> cjtoParadas = p.getPereParadas();
	            List<PereParada> listaParadas = new ArrayList<>(cjtoParadas);
	            Set<Estancia> listaEstancias = estanciaService.findByPeregrinoId(p.getId());
	           
	            System.out.println(listaEstancias);
	            
	           
	            
	            CarnetBackupJson backup = new CarnetBackupJson();
	            backup.setIdPeregrino(p.getId());
	            backup.setNombre(p.getNombrePeregrino());
	            backup.setNacionalidad(p.getNacionalidad());
	            backup.setFechaExp(p.getCarnet().getFechaexp());
	            backup.setExpedidoEn(p.getCarnet().getParadaIncial().getNombre());
	            backup.setHoy(LocalDate.now());
	            backup.setDistancia(p.getCarnet().getDistancia());
	            backup.setBackupFecha(LocalDate.now());

	            List<CarnetBackupJson.ParadasJson> paradas = listaParadas.stream().map(s -> {
	                CarnetBackupJson.ParadasJson parada = new CarnetBackupJson.ParadasJson();
	                parada.setOrden(listaParadas.indexOf(s) + 1);
	                parada.setNombre(s.getParada().getNombre());
	                parada.setRegion(String.valueOf(s.getParada().getRegion()));
	                return parada;
	            }).collect(Collectors.toList());
	            backup.setParadas(paradas);

	            List<CarnetBackupJson.EstanciasJson> estanciasJson = listaEstancias.stream().map(estancia -> {
	                CarnetBackupJson.EstanciasJson e = new CarnetBackupJson.EstanciasJson();

	                e.setId(estancia.getId());
	                e.setFechaEstancia(estancia.getFecha());
	                Parada parada = paradaService.find(estancia.getParada().getId());
	                e.setParada(parada.getNombre());
	                e.setVip(estancia.isVip());
	                
	                return e;
	            }).collect(Collectors.toList());
	            backup.setEstancias(estanciasJson);

	            listaBackup.add(backup);
	        } catch(Exception ex) {
	            ex.printStackTrace();  
	        }
	    }

	    BackupCarnet contenedor = new BackupCarnet();
	    contenedor.setNombreFichero("backupcarnets" + LocalDate.now().toString());
	    contenedor.setCarnets(listaBackup);
   
	    saveBackup(contenedor);
	    ret = true;

	    return ret;
	}

	
}