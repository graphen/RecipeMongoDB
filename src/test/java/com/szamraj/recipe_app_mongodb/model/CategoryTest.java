package com.szamraj.recipe_app_mongodb.model;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CategoryTest {

	Category category;

	@BeforeEach
	void setUp() throws Exception {
		category = new Category();
	}

	@Test
	void testGetId() {
		String id = "42L";
		category.setId(id);
		assertEquals(id, category.getId());
	}

	@Test
	void testGetDescription() {
		String desc = "Test Description";
		category.setDescription(desc);
		assertEquals(desc, category.getDescription());
	}

}