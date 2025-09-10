package com.szamraj.recipe_app_mongodb.repository;

import org.springframework.data.repository.CrudRepository;

import com.szamraj.recipe_app_mongodb.model.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {

}
