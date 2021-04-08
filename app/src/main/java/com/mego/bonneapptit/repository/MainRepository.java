package com.mego.bonneapptit.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.mego.bonneapptit.apiservice.RecipeApi;
import com.mego.bonneapptit.db.RecipeDao;
import com.mego.bonneapptit.firebase.Firebase;
import com.mego.bonneapptit.models.Recipe;
import com.mego.bonneapptit.models.responses.RecipeListResponse;
import com.mego.bonneapptit.models.responses.RecipeResponse;


import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class MainRepository {
    // this class to bring data from db and Api
    private RecipeApi apiService;
    private RecipeDao recipeDao;
    private Firebase firebase= new Firebase();


    @Inject
    public MainRepository(RecipeApi apiService, RecipeDao recipeDao) {
        this.apiService = apiService;
        this.recipeDao = recipeDao;
    }


    // to bring list from api
    public Observable<RecipeListResponse> getRecipes() {
        return apiService.getAllRecipes();
    }

    // to bring detials from api
    public Observable<RecipeResponse> getRecipe(String recipe_id) {
        return apiService.getRecipe(recipe_id);
    }

    public Observable<RecipeListResponse> searchRecipes(String query, int page) {
        return apiService.searchRecipes(query, page);
    }

    // to save recipe to room dp
    public void insertRecipe(Recipe recipe) {
        recipeDao.insertRecipe(recipe);
    }
// to delet recipe to room dp

    public void deleteRecipe(String recipeName) {
        recipeDao.deleteRecipe(recipeName);
    }

    // to delete all recipe to room dp
    public void deleteAll() {
        recipeDao.deleteAll();
    }

    public LiveData<List<Recipe>> getRecipeList() {
        return recipeDao.getRecipes();
    }

    public void uploadRecipe(Recipe recipe, Context context) {
        firebase.uploadRecipe(recipe, context);
    }
// to sign out

    public void signOut(Context context) {
        firebase.signOut(context);
    }
    public void checkSavedRecipes(){firebase.checkSavedRecipes();}

    }
