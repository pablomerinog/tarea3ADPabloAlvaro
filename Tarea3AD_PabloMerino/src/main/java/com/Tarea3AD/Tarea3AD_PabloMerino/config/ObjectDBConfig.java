package com.Tarea3AD.Tarea3AD_PabloMerino.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

@Configuration
public class ObjectDBConfig {

    @Value("${odb.path}")
    private String odbPath;

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        Map<String, String> props = new HashMap<>();
        props.put("javax.persistence.jdbc.url", odbPath);
        props.put("javax.persistence.provider", "com.objectdb.jpa.Provider");

        return Persistence.createEntityManagerFactory("objectdbPU", props);
    }

    @Bean
    public EntityManager entityManager(EntityManagerFactory emf) {
        return emf.createEntityManager();
    }
}