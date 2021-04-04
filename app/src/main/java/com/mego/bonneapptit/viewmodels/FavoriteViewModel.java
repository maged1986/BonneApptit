package com.mego.bonneapptit.viewmodels;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mego.bonneapptit.models.Recipe;
import com.mego.bonneapptit.repository.MainRepository;

import java.util.List;

public class FavoriteViewModel extends ViewModel {
    private MainRepository repository;
    private LiveData<List<Recipe>> favList;


    @ViewModelInject
    public FavoriteViewModel(MainRepository repository) {
        this.repository = repository;
    }


    public LiveData<List<Recipe>> getFavList() {
        favList= repository.getRecipeList();
        return favList;
    }
    public void deleteRecipe(String recipeName) {
        repository.deleteRecipe(recipeName);
    }

}
