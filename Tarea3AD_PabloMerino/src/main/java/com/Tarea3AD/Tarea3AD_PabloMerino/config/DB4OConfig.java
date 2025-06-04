package com.Tarea3AD.Tarea3AD_PabloMerino.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.ConjuntoContratado;
import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.Servicio;
import com.Tarea3AD.Tarea3AD_PabloMerino.repository.Db4oRepository;
import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;

@Configuration
public class DB4OConfig {

	@Configuration
	public class Db4oConfig {

		@Bean(destroyMethod = "close")
		public ObjectContainer db4oContainer() {
			return Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "peregrinos.db4o");
		}

		@Bean
		public Db4oRepository<Servicio> servicioDb4oRepository(ObjectContainer db4oContainer) {
			return new Db4oRepository<>(db4oContainer, Servicio.class);
		}

		@Bean
		public Db4oRepository<ConjuntoContratado> conjuntoContratadoDb4oRepository(ObjectContainer db4oContainer) {
			return new Db4oRepository<>(db4oContainer, ConjuntoContratado.class);
		}
		
		//PRUEBA
	}
}