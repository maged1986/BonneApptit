package com.mego.bonneapptit.ui.fragments.main;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mego.bonneapptit.R;
import com.mego.bonneapptit.adapters.RecipeAdapter;
import com.mego.bonneapptit.databinding.SearchFragmentBinding;
import com.mego.bonneapptit.models.Recipe;
import com.mego.bonneapptit.ui.MainActivity;
import com.mego.bonneapptit.viewmodels.SearchViewModel;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SearchFragment extends Fragment {
    private SearchFragmentArgs args;
    private SearchViewModel viewModel;
    private String searchItem;
    private RecipeAdapter adapter;
    private SearchFragmentBinding binding;
    private List<Recipe> recipeList = new ArrayList<>();


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.search_fragment, container, false);
        return binding.getRoot();

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        if (getArguments() != null) {
            args = SearchFragmentArgs.fromBundle(getArguments());
            searchItem = args.getSearchItem();
        }
       if( viewModel.checkNullSearch() == false){
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        binding.searchFragRvRecipes.setLayoutManager(llm);
        adapter = new RecipeAdapter(getContext());
        viewModel.setRecipes(searchItem);
        viewModel.getRecipes().observe(getViewLifecycleOwner(), new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                recipeList = recipes;
            }
        });
        adapter.setList(recipeList);
        binding.searchFragRvRecipes.setAdapter(adapter);
        adapter.setOnItemClickListener(new RecipeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String recipe_id) {
                Bundle bundle = new Bundle();
                bundle.putString("rId", recipe_id);
                Navigation.findNavController(binding.searchFragRvRecipes).
                        navigate(R.id.action_recipeFragment_to_articleFragment, bundle);
            }
        });}else {
          binding.searchFragRvRecipes.setVisibility(View.GONE);
          binding.parent.setVisibility(View.VISIBLE);
          binding.errorLayoutBtnAll.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Navigation.findNavController(getActivity(),R.id.mainNavHostFragment).navigate(R.id.allRecipesFragment);

              }
          });
          binding.errorLayoutBtnCategories.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Navigation.findNavController(getActivity(),R.id.mainNavHostFragment).navigate(R.id.categoriesFragment);

              }
          });
       }
    }
}