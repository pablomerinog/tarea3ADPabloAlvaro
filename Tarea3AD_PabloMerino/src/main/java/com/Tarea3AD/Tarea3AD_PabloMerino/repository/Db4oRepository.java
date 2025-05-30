package com.Tarea3AD.Tarea3AD_PabloMerino.repository;

import java.util.List;
import java.util.stream.Collectors;

import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.Servicio;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Predicate;

public class Db4oRepository<T> {

	private final ObjectContainer db;
	private final Class<T> clazz;

	public Db4oRepository(ObjectContainer db, Class<T> clazz) {
		this.db = db;
		this.clazz = clazz;
	}

	public void save(T obj) {
		db.store(obj);
		db.commit();
	}

	public List<T> findAll() {
		return db.query(clazz);
	}

	public T findByPredicate(Predicate<T> predicate) {
		List<T> result = db.query(predicate);
		return result.isEmpty() ? null : result.get(0);
	}

	public void delete(T obj) {
		db.delete(obj);
		db.commit();
	}

	

}