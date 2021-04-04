package com.mego.bonneapptit.repository;

import androidx.lifecycle.LiveData;

import com.mego.bonneapptit.apiservice.RecipeApi;
import com.mego.bonneapptit.db.RecipeDao;
import com.mego.bonneapptit.models.Recipe;
import com.mego.bonneapptit.models.responses.RecipeListResponse;
import com.mego.bonneapptit.models.responses.RecipeResponse;


import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class MainRepository {

    private RecipeApi apiService;
    private RecipeDao recipeDao;


    @Inject
    public MainRepository(RecipeApi apiService, RecipeDao recipeDao) {
        this.apiService = apiService;
        this.recipeDao = recipeDao;
    }



    public Observable<RecipeListResponse> getRecipes() {
        return apiService.getAllRecipes();
    }

    public Observable<RecipeResponse> getRecipe(String recipe_id) {
        return apiService.getRecipe(recipe_id);
    }

    public Observable<RecipeListResponse> searchRecipes(String query,int page) {
        return apiService.searchRecipes(query,page);
    }


    public void insertRecipe(Recipe recipe) {
        recipeDao.insertRecipe(recipe);
    }

    public void deleteRecipe(String recipeName) {
        recipeDao.deleteRecipe(recipeName);
    }
    public void deleteAll( ){recipeDao.deleteAll();}
    public LiveData<List<Recipe>> getRecipeList() {
        return recipeDao.getRecipes();
    }

   /* public void signOut() {
        firebaseModule.signOut();
    }*/
}
