package com.szamraj.recipe_app_mongodb.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

@Document(collection = "recipes")
public class Recipe {
	
	@Id
	private String id;
	
	private String description;
	
	private Integer prepTime;
	
	private Integer cookTime;
	
	private Integer servings;
	
	private String source;
	
	private String url;
	
	private String directions;
	
    private Set<RecipeIngredient> recipeIngredients = new HashSet<>();
	
	public Difficulty difficulty;
	
	private byte[] image;
	
	private Set<Category> categories = new HashSet<>();
	
	private Notes notes;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getPrepTime() {
		return prepTime;
	}

	public void setPrepTime(Integer prepTime) {
		this.prepTime = prepTime;
	}

	public Integer getCookTime() {
		return cookTime;
	}

	public void setCookTime(Integer cookTime) {
		this.cookTime = cookTime;
	}

	public Integer getServings() {
		return servings;
	}

	public void setServings(Integer servings) {
		this.servings = servings;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDirections() {
		return directions;
	}

	public void setDirections(String directions) {
		this.directions = directions;
	}

	public Set<RecipeIngredient> getRecipeIngredients() {
		return recipeIngredients;
	}

	public void setRecipeIngredients(Set<RecipeIngredient> recipeIngredients) {
		this.recipeIngredients = recipeIngredients;
	}

	public Difficulty getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(Difficulty difficulty) {
		this.difficulty = difficulty;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public Set<Category> getCategories() {
		return categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}

	public Notes getNotes() {
		return notes;
	}

	public void setNotes(Notes notes) {
		this.notes = notes;
	}
	
    public Recipe addRecipeIngredient(Ingredient ingredient, BigDecimal amount, UnitOfMeasure unit) {
        RecipeIngredient ri = new RecipeIngredient(ingredient, amount, unit);
        this.recipeIngredients.add(ri);
        return this;
    }
    
    public Recipe addRecipeIngredient(RecipeIngredient ri) {
        this.recipeIngredients.add(ri);
        return this;
    }
}