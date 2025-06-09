package com.Tarea3AD.Tarea3AD_PabloMerino.repository;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import com.Tarea3AD.Tarea3AD_PabloMerino.config.ExistDBConfig;

@Repository
public class ExistDBRepository {
	@Autowired
	private ExistDBConfig existDBConfig;

	public void guardarCarnetExistDB(String nombreParada, File carnet) {
		try {
			String paradaFormateada = nombreParada.replaceAll(" ", "_");
			String coleccionParadaRuta = existDBConfig.getURI() + "/practicaAD/" + paradaFormateada;
			Collection coleccionParada = DatabaseManager.getCollection(coleccionParadaRuta, existDBConfig.getUSER(),
					existDBConfig.getPASSWORD());

			if (coleccionParada == null) {
				existDBConfig.crearColeccionParada(nombreParada);
				coleccionParada = DatabaseManager.getCollection(coleccionParadaRuta, existDBConfig.getUSER(),
						existDBConfig.getPASSWORD());
			}

			String nombreCarnet = carnet.getName().replaceAll(" ", "_");

			XMLResource resource = (XMLResource) coleccionParada.createResource(nombreCarnet, "XMLResource");
			resource.setContent(carnet);
			coleccionParada.storeResource(resource);

			System.out.println("Carnet almacenado en ExistDB en la parada " + nombreParada + ": " + nombreCarnet);
		} catch (XMLDBException e) {
			e.printStackTrace();
		}
	}

	public List<String> listarCarnetsPorParada(String nombreParada) {
		List<String> listaCarnets = new ArrayList<>();
		try {
			String paradaFormateada = nombreParada.replaceAll(" ", "_");
			String coleccionParadaRuta = existDBConfig.getURI() + "/practicaAD/" + paradaFormateada;

			Collection coleccionParada = DatabaseManager.getCollection(coleccionParadaRuta, existDBConfig.getUSER(),
					existDBConfig.getPASSWORD());

			if (coleccionParada == null) {
				System.out.println("No existe la colección para la parada: " + nombreParada);
				return listaCarnets;
			}

			String[] recursos = coleccionParada.listResources();
			for (String recursoNombre : recursos) {
				XMLResource recurso = (XMLResource) coleccionParada.getResource(recursoNombre);
				if (recurso != null) {

					listaCarnets.add(recurso.getContent().toString());
				}
			}

			coleccionParada.close();

		} catch (XMLDBException e) {
			e.printStackTrace();
		}
		return listaCarnets;
	}

}
