package com.mego.bonneapptit.di;

import android.app.Application;

import androidx.room.Room;

import com.mego.bonneapptit.db.RecipeDB;
import com.mego.bonneapptit.db.RecipeDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;

@Module
@InstallIn(ApplicationComponent.class)
public class DataBaseModule {
    @Provides
    @Singleton
    public static RecipeDB provideDB(Application application){
        return Room.databaseBuilder(application, RecipeDB.class, "recipe_DB")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }
    @Provides
    @Singleton
    public static RecipeDao provideDao(RecipeDB recipeDB){
        return recipeDB.recipeDao();
    }
}
