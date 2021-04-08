package com.mego.bonneapptit.viewmodels;

import android.content.Context;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.ViewModel;

import com.mego.bonneapptit.db.RecipeDao;
import com.mego.bonneapptit.models.Recipe;
import com.mego.bonneapptit.repository.AuthRepository;

import java.util.List;

import io.reactivex.Observable;

public class AuthViewModel extends ViewModel {
  // this class to login and logout
  private AuthRepository repository;
    @ViewModelInject
    public AuthViewModel(AuthRepository repository) {
        this.repository = repository;
    }

    public void login(String email, String password, Context context){repository.login(email, password, context);}
    public void singup(String name, String country, String city,
                       String email, String password, Context context){repository.singup(name, country, city, email, password, context);}
  //  public void updateUserData(String userId,String email,String password){repository.updateUserData(userId,email,password);}

}
