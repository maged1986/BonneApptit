package com.mego.bonneapptit.viewmodels;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mego.bonneapptit.models.Recipe;
import com.mego.bonneapptit.models.responses.RecipeResponse;
import com.mego.bonneapptit.repository.MainRepository;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ArticleViewModel extends ViewModel {
    // this calss to bring recipe details
    private MainRepository repository;
    private MutableLiveData<RecipeResponse> MutableLiveData = new MutableLiveData<>();

    @ViewModelInject
    public ArticleViewModel(MainRepository repository) {
        this.repository = repository;
    }


    @SuppressLint("CheckResult")
    public void setRecipes(String recipe_id) {
        repository.getRecipe(recipe_id).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(
                        result -> {
                            MutableLiveData.postValue(result);
                            Log.d("TAG", "getRecipe: " + result);
                        }, throwable -> {
                            Log.d("TAG", "getRecipes: " + throwable.getMessage());
                        }
                );
    }

    public LiveData<RecipeResponse> getRecipe() {
        return MutableLiveData;
    }
    public void insertRecipe(Recipe recipe) {
        repository.insertRecipe(recipe);
    }
    public void uploadAd(Recipe recipe, Context context) {
        repository.uploadAd(recipe, context);
    }

}


