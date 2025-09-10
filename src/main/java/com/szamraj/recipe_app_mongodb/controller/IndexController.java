package com.szamraj.recipe_app_mongodb.controller;

import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.szamraj.recipe_app_mongodb.command.RecipeCommand;
import com.szamraj.recipe_app_mongodb.service.RecipeService;

@Controller
public class IndexController {
	
	RecipeService recipeService;
	
	public IndexController(RecipeService recipeService) {
		super();
		this.recipeService = recipeService;
	}

	@GetMapping({"/list", "/", ""})
	public String index(Model model) {
        Set<RecipeCommand> recipes = recipeService.getAllRecipes();
        model.addAttribute("recipes", recipes);
        return "recipes/list";
	}
}
