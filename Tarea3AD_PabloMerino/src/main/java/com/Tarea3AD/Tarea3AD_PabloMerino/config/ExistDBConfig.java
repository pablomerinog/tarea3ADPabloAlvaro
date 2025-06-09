package com.Tarea3AD.Tarea3AD_PabloMerino.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.stereotype.Component;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.CollectionManagementService;
@Component
public class ExistDBConfig {
	private static final String URI;
	private static final String DRIVER = "org.exist.xmldb.DatabaseImpl";
	private static final String USER;
	private static final String PASSWORD;
	private Collection collection = null;
	private static ExistDBConfig instance;

	static {
		try (InputStream input = ExistDBConfig.class.getClassLoader().getResourceAsStream("application.properties")) {
			if (input == null) {
				throw new IOException("Archivo de propiedades no encontrado: application.properties");
			}
			Properties properties = new Properties();
			properties.load(input);
			URI = properties.getProperty("existdb.uri");
			USER = properties.getProperty("existdb.user");
			PASSWORD = properties.getProperty("existdb.password");
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Error al cargar la configuración de ExistDB");
		}
	}

	private ExistDBConfig() {
		try {
			Class<?> cl = Class.forName(DRIVER);
			Database database = (Database) cl.getDeclaredConstructor().newInstance();
			DatabaseManager.registerDatabase(database);

			createCollection("/practicaAD");

			collection = DatabaseManager.getCollection(URI + "/practicaAD", USER, PASSWORD);
			if (collection == null) {
				throw new XMLDBException(0, "No se pudo acceder a la colección principal /practicaAD");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static synchronized ExistDBConfig getInstance() {
		if (instance == null) {
			instance = new ExistDBConfig();
		}
		return instance;
	}

	public void createCollection(String ruta) {
		try {
			String fullPath = URI + ruta;
			Collection parent = DatabaseManager.getCollection(URI, USER, PASSWORD);

			if (parent != null) {
				CollectionManagementService mgtService = (CollectionManagementService) parent
						.getService("CollectionManagementService", "1.0");
				Collection coll = DatabaseManager.getCollection(fullPath, USER, PASSWORD);

				if (coll == null) {

					String relativePath = ruta.startsWith("/") ? ruta.substring(1) : ruta;
					mgtService.createCollection(relativePath);
					System.out.println("Colección creada: " + ruta);
				} else {
					System.out.println("La colección ya existe: " + ruta);
				}
			} else {
				System.out.println("No se pudo acceder a la colección raíz 'db'.");
			}
		} catch (XMLDBException e) {
			e.printStackTrace();
		}
	}

	public void crearColeccionParada(String nombreParada) {
		String formatea= nombreParada.replaceAll(" ", "_");
		createCollection("/practicaAD/" + formatea);
	}

	public String getURI() {
		return URI;
	}

	public String getUSER() {
		return USER;
	}
	
	public String getPASSWORD() {
		return PASSWORD;
	}

	public void close() {
		try {
			if (collection != null) {
				collection.close();
			}
		} catch (XMLDBException e) {
			e.printStackTrace();
		}
	}
}
