package com.mego.bonneapptit.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.mego.bonneapptit.models.Recipe;

@Database(entities = Recipe.class, version = 2, exportSchema = false)
@TypeConverters(Converter.class)
public abstract class RecipeDB extends RoomDatabase {
    //This is Db class
    public abstract RecipeDao recipeDao();
}
