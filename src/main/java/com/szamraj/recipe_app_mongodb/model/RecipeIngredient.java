package com.szamraj.recipe_app_mongodb.model;

import java.math.BigDecimal;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

@Document(collection = "recipe_ingredients")
public class RecipeIngredient {

    @Id
    private String id;

    private Ingredient ingredient;

    private BigDecimal amount;

    private UnitOfMeasure unitOfMeasure;

    // Constructor for convenience when creating a recipe ingredient
    public RecipeIngredient(Ingredient ingredient, BigDecimal amount, UnitOfMeasure unit) {
        this.ingredient = ingredient;
        this.amount = amount;
        this.unitOfMeasure = unit;
    }

    public RecipeIngredient() {
    	super();
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Ingredient getIngredient() {
		return ingredient;
	}

	public void setIngredient(Ingredient ingredient) {
		this.ingredient = ingredient;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public UnitOfMeasure getUnitOfMeasure() {
		return unitOfMeasure;
	}

	public void setUnitOfMeasure(UnitOfMeasure unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	} 
}
