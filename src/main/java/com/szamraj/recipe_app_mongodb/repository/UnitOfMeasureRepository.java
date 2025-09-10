package com.szamraj.recipe_app_mongodb.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.szamraj.recipe_app_mongodb.model.UnitOfMeasure;

public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, String> {
	public Optional<UnitOfMeasure> findByDescription(String description);
}
