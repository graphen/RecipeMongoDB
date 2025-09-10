package com.szamraj.recipe_app_mongodb.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

@Document(collection = "notes")
public class Notes {
	
	@Id
	private String id;
	
	private String recipeNotes;
	
	public Notes() {
		super();
	}
	
	public Notes(String recipeNotes) {
		super();
		this.recipeNotes = recipeNotes;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRecipeNotes() {
		return recipeNotes;
	}

	public void setRecipeNotes(String recipeNotes) {
		this.recipeNotes = recipeNotes;
	}
}
