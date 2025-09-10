package com.szamraj.recipe_app_mongodb.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.szamraj.recipe_app_mongodb.model.Category;

public interface CategoryRepository extends CrudRepository<Category, String> {
	public Optional<Category> findByDescription(String description);
}
