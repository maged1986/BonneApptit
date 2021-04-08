package com.mego.bonneapptit.ui.fragments.main;

import android.content.Context;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.ViewModel;

import com.mego.bonneapptit.repository.MainRepository;

public class MainViewModel extends ViewModel {
    MainRepository repository;

    @ViewModelInject
    public MainViewModel(MainRepository repository) {
        this.repository = repository;
    }
    public void checkSavedRecipes(){repository.checkSavedRecipes();}
    public void signOut(Context context){repository.signOut(context);}



}
