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

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CategoryRecipesViewModel extends ViewModel {
    // this class to search for recipe related to Category item

    private MainRepository repository;
    private MutableLiveData<List<Recipe>> listMutableLiveData= new MutableLiveData<>();
    @ViewModelInject
    public CategoryRecipesViewModel(MainRepository repository) {
        this.repository = repository;
    }
    @SuppressLint("CheckResult")
    public void setRecipes(String query) {
        repository.searchRecipes(query,1).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(
                        result -> {
                            listMutableLiveData.postValue(result.getRecipes());
                            Log.d("TAG", "getRecipe: " + result);
                        }, throwable -> {
                            Log.d("TAG", "getRecipes: " + throwable.getMessage());
                        }
                );
    }

    public LiveData<List<Recipe>> getRecipes() {
        return listMutableLiveData;
    }
}
