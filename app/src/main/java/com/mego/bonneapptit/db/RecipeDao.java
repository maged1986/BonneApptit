package com.mego.bonneapptit.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.mego.bonneapptit.models.Recipe;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;


@Dao
public interface RecipeDao {
    //this is dao interface
    @Insert(onConflict = REPLACE)
    public void insertRecipe(Recipe recipe);

    @Query("delete from recipeTable where title =:RecipeName")
    public void deleteRecipe(String RecipeName);
    @Query("DELETE FROM recipeTable")
    public void deleteAll( );

    @Query("select * from recipeTable")
    public LiveData<List<Recipe>> getRecipes();
}
