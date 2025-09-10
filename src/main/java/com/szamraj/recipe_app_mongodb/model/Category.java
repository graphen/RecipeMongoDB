package com.szamraj.recipe_app_mongodb.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

@Document(collection = "categories")
public class Category {

	@Id
	private String Id;
	
	private String description;
	
	public Category() {
		super();
	}

	public Category(String description) {
		super();
		this.description = description;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
