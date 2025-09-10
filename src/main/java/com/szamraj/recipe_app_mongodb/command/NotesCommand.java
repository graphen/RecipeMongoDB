package com.szamraj.recipe_app_mongodb.command;

public class NotesCommand {
	private String id;
	private String recipeNotes;
	
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
