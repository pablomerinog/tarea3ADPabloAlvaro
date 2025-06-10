package com.Tarea3AD.Tarea3AD_PabloMerino.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.BackupCarnet;

@Repository
public interface MongoDBRepository extends MongoRepository<BackupCarnet, String>{

}
