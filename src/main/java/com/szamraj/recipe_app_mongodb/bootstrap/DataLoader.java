package com.szamraj.recipe_app_mongodb.bootstrap;

import java.math.BigDecimal;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.szamraj.recipe_app_mongodb.model.Category;
import com.szamraj.recipe_app_mongodb.model.Difficulty;
import com.szamraj.recipe_app_mongodb.model.Ingredient;
import com.szamraj.recipe_app_mongodb.model.Notes;
import com.szamraj.recipe_app_mongodb.model.Recipe;
import com.szamraj.recipe_app_mongodb.model.UnitOfMeasure;
import com.szamraj.recipe_app_mongodb.repository.CategoryRepository;
import com.szamraj.recipe_app_mongodb.repository.IngredientRepository;
import com.szamraj.recipe_app_mongodb.repository.RecipeRepository;
import com.szamraj.recipe_app_mongodb.repository.UnitOfMeasureRepository;

@Component
public class DataLoader implements CommandLineRunner {

    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final CategoryRepository categoryRepository;

    public DataLoader(RecipeRepository recipeRepository,
            IngredientRepository ingredientRepository,
            UnitOfMeasureRepository unitOfMeasureRepository,
            CategoryRepository categoryRepository) {
this.recipeRepository = recipeRepository;
this.ingredientRepository = ingredientRepository;
this.unitOfMeasureRepository = unitOfMeasureRepository;
this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) {
    	if(recipeRepository.count() == 0) {
			System.out.println("Loading data...");
			loadData();
		} else {
			System.out.println("Data already loaded, skipping.");
		}
    }

    
    private void loadData() {
        // === Units of Measure ===
        UnitOfMeasure teaspoon = new UnitOfMeasure();
        teaspoon.setDescription("Teaspoon");
        teaspoon = unitOfMeasureRepository.save(teaspoon);

        UnitOfMeasure tablespoon = new UnitOfMeasure();
        tablespoon.setDescription("Tablespoon");
        tablespoon = unitOfMeasureRepository.save(tablespoon);

        UnitOfMeasure piece = new UnitOfMeasure();
        piece.setDescription("Piece");
        piece = unitOfMeasureRepository.save(piece);

        // === Categories ===
        Category american = new Category();
        american.setDescription("American");
        american = categoryRepository.save(american);

        Category mexican = new Category();
        mexican.setDescription("Mexican");
        mexican = categoryRepository.save(mexican);

        // === Ingredients ===
        Ingredient avocado = new Ingredient();
        avocado.setDescription("Avocado");
        avocado = ingredientRepository.save(avocado);

        Ingredient salt = new Ingredient();
        salt.setDescription("Salt");
        salt = ingredientRepository.save(salt);

        Ingredient limeJuice = new Ingredient();
        limeJuice.setDescription("Lime Juice");
        limeJuice = ingredientRepository.save(limeJuice);

        Ingredient redOnion = new Ingredient();
        redOnion.setDescription("Red Onion");
        redOnion = ingredientRepository.save(redOnion);

        Ingredient garlic = new Ingredient();
        garlic.setDescription("Garlic");
        garlic = ingredientRepository.save(garlic);

        Ingredient chili = new Ingredient();
        chili.setDescription("Chili");
        chili = ingredientRepository.save(chili);

        Ingredient tomato = new Ingredient();
        tomato.setDescription("Tomato");
        tomato = ingredientRepository.save(tomato);

        Ingredient chicken = new Ingredient();
        chicken.setDescription("Chicken");
        chicken = ingredientRepository.save(chicken);

        Ingredient paprika = new Ingredient();
        paprika.setDescription("Paprika");
        paprika = ingredientRepository.save(paprika);

        // === Recipe: Perfect Guacamole ===
        Recipe guac = new Recipe();
        guac.setDescription("Perfect Guacamole");
        guac.setPrepTime(10);
        guac.setCookTime(0);
        guac.setServings(2);
        guac.setSource("Simply Recipes");
        guac.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");
        guac.setDirections("1 Cut avocado ...\n2 Mash ...\n3 Add rest...");
        guac.setDifficulty(Difficulty.EASY);
        guac.setCategories(Set.of(american, mexican));

        Notes guacNote = new Notes();
        guacNote.setRecipeNotes("For best flavor, let it sit for 30 minutes.");
        guac.setNotes(guacNote);

        guac.addRecipeIngredient(avocado, new BigDecimal(2), piece);
        guac.addRecipeIngredient(salt, new BigDecimal(0.5), teaspoon);
        guac.addRecipeIngredient(limeJuice, new BigDecimal(1), tablespoon);
        guac.addRecipeIngredient(redOnion, new BigDecimal(2), tablespoon);
        guac.addRecipeIngredient(garlic, new BigDecimal(1), piece);
        guac.addRecipeIngredient(chili, new BigDecimal(1), piece);
        guac.addRecipeIngredient(tomato, new BigDecimal(1), piece);

        recipeRepository.save(guac);

        // === Recipe: Spicy Grilled Chicken ===
        Recipe chickenRecipe = new Recipe();
        chickenRecipe.setDescription("Spicy Grilled Chicken");
        chickenRecipe.setPrepTime(15);
        chickenRecipe.setCookTime(20);
        chickenRecipe.setServings(4);
        chickenRecipe.setSource("Spice Masters");
        chickenRecipe.setUrl("https://www.example.com/spicy-grilled-chicken");
        chickenRecipe.setDirections("1 Mix spices ...\n2 Marinate ...\n3 Grill ...");
        chickenRecipe.setDifficulty(Difficulty.MODERATE);
        chickenRecipe.setCategories(Set.of(american));

        Notes chickenNote = new Notes();
        chickenNote.setRecipeNotes("Marinate overnight for best results.");
        chickenRecipe.setNotes(chickenNote);

        chickenRecipe.addRecipeIngredient(chicken, new BigDecimal(1), piece);
        chickenRecipe.addRecipeIngredient(garlic, new BigDecimal(2), piece);
        chickenRecipe.addRecipeIngredient(salt, new BigDecimal(1), teaspoon);
        chickenRecipe.addRecipeIngredient(paprika, new BigDecimal(1), tablespoon);
        chickenRecipe.addRecipeIngredient(limeJuice, new BigDecimal(1), tablespoon);

        recipeRepository.save(chickenRecipe);
        System.out.println("Data loaded successfully!");    	
    }
    
    
    
}