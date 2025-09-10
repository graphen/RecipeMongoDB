package com.szamraj.recipe_app_mongodb.command;

import java.util.Set;

import org.hibernate.validator.constraints.URL;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.HashSet;
import java.util.ArrayList;

public class RecipeCommand {
	private String id;
	
	@NotBlank
	@Size(min = 3, max = 255)
	private String description;
	
	@Min(1)
	@Max(1000)
	private Integer cookTime;
	
	@Min(1)
	@Max(1000)
	private Integer prepTime;
	
	@Min(1)
	@Max(100)
	private Integer servings;
	
	private String source;
	
	@URL
	private String url;
	
	@NotBlank
	private String directions;
	
	private List<RecipeIngredientCommand> recipeIngredients = new ArrayList<>();
	private String difficulty;
	private byte[] image;
	private Set<String> categories = new HashSet<>();
	private NotesCommand notes = new NotesCommand();
	
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
	
	public Integer getCookTime() {
		return cookTime;
	}
	
	public void setCookTime(Integer cookTime) {
		this.cookTime = cookTime;
	}
	
	public Integer getPrepTime() {
		return prepTime;
	}
	
	public void setPrepTime(Integer prepTime) {
		this.prepTime = prepTime;
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
	
	public List<RecipeIngredientCommand> getRecipeIngredients() {
		return recipeIngredients;
	}
	
	public void setRecipeIngredients(List<RecipeIngredientCommand> recipeIngredients) {
		this.recipeIngredients = recipeIngredients;
	}
	
	public String getDifficulty() {
		return difficulty;
	}
	
	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}
	
	public byte[] getImage() {
		return image;
	}
	
	public void setImage(byte[] image) {
		this.image = image;
	}
	
	public Set<String> getCategories() {
		return categories;
	}
	
	public void setCategories(Set<String> categories) {
		this.categories = categories;
	}
	
	public NotesCommand getNotes() {
		return notes;
	}
	
	public void setNotes(NotesCommand notes) {
		this.notes = notes;
	}
}
