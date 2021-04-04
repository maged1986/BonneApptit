package com.mego.bonneapptit.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.mego.bonneapptit.R;
import com.mego.bonneapptit.databinding.ActivityAuthBinding;
import com.mego.bonneapptit.databinding.ActivityMainBinding;
import com.mego.bonneapptit.viewmodels.AuthViewModel;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class AuthActivity extends AppCompatActivity {
    private NavController controller;
    private AuthViewModel viewModel;
    public static void show(Context context) {
        Intent intent = new Intent(context, AuthActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityAuthBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_auth);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.authNavHostFragment);
        controller = navHostFragment.getNavController();

    }
}