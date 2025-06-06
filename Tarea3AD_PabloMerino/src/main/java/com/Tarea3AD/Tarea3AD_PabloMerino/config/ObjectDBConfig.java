package com.Tarea3AD.Tarea3AD_PabloMerino.config;

import java.io.IOException;
import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.Servicio;
import com.Tarea3AD.Tarea3AD_PabloMerino.repository.Db4oRepository;
import com.db4o.ObjectContainer;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

@Configuration
public class ObjectDBConfig {

	private static final EntityManagerFactory emf;

	static {
		Properties properties = new Properties();
		try {
			properties.load(ObjectDBConfig.class.getClassLoader().getResourceAsStream("application.properties"));
		} catch (IOException e) {
			throw new RuntimeException("No se pudo cargar el archivo application.properties", e);
		}
		String dbPath = properties.getProperty("odb.path");
		emf = Persistence.createEntityManagerFactory(dbPath);
	}

	public static EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public static void close() {

		emf.close();

	}
}
