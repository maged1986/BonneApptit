package com.mego.bonneapptit.repository;

import android.content.Context;

import com.mego.bonneapptit.db.RecipeDao;
import com.mego.bonneapptit.firebase.Firebase;

import javax.inject.Inject;

public class AuthRepository {

    private Firebase manager;
    private RecipeDao recipeDao;

    @Inject
    public AuthRepository(Firebase manager) {
        this.manager = manager;
    }

    public void login(String email, String password, Context context){manager.logIn(email, password, context);}
    public void singup(String name, String country, String city,
                       String email, String password, Context context){manager.createNewUser(name, country, city, email, password, context);}




}
