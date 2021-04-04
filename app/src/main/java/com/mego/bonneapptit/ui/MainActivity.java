package com.mego.bonneapptit.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.mego.bonneapptit.R;
import com.mego.bonneapptit.databinding.ActivityAuthBinding;
import com.mego.bonneapptit.databinding.ActivityMainBinding;
import com.mego.bonneapptit.firebase.Firebase;
import com.mego.bonneapptit.models.Categories;
import com.mego.bonneapptit.utils.SessionManager;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {
   // private SessionManager manager;
    private Firebase firebase;
    public static void show(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       NavController navController = Navigation.findNavController(this, R.id.mainNavHostFragment);
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.op_menu,menu);
        MenuItem searchItem=menu.findItem(R.id.app_bar_search);
        SearchView searchView= (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if(s.length() >= 3){
                    Bundle bundle= new Bundle();
                    bundle.putString("searchItem",s);
                    Navigation.findNavController(MainActivity.this,R.id.mainNavHostFragment).navigate(R.id.searchFragment,bundle);
                    return false;}
                else {
                    Toast.makeText(getApplicationContext(),"please ",Toast.LENGTH_LONG).show();
                }return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.op_menu_signout){
            firebase.signOut(this);
            startActivity(new Intent(this,AuthActivity.class));
            return true;
        }
        else return super.onOptionsItemSelected(item);
    }
}