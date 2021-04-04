package com.mego.bonneapptit.ui.fragments.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mego.bonneapptit.R;
import com.mego.bonneapptit.adapters.RecipeAdapter;
import com.mego.bonneapptit.databinding.FragmentRecipeBinding;
import com.mego.bonneapptit.models.Recipe;
import com.mego.bonneapptit.viewmodels.CategoryRecipesViewModel;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CategoryRecipesFragment extends Fragment {
    private CategoryRecipesViewModel viewModel;
    private CategoryRecipesFragmentArgs args;
    private FragmentRecipeBinding binding;
    private RecipeAdapter adapter;
    private String CategoryName;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel =  new ViewModelProvider(this).get(CategoryRecipesViewModel.class);
        if (getArguments() != null) {
            args = CategoryRecipesFragmentArgs.fromBundle(getArguments());
            CategoryName = args.getCategoryName();
        }
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        binding.recipeFragRvRecipes.setLayoutManager(llm);
        adapter=new RecipeAdapter(getContext());
        binding.recipeFragRvRecipes.setAdapter(adapter);
        viewModel.setRecipes(CategoryName);
        viewModel.getRecipes().observe(getViewLifecycleOwner(), new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                adapter.setList(recipes);
            }
        });
        binding.recipeFragRvRecipes.setAdapter(adapter);
        adapter.setOnItemClickListener(new RecipeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String recipe_id) {
                Bundle bundle=new Bundle();
                bundle.putString("rId",recipe_id);
                Navigation.findNavController(binding.recipeFragRvRecipes).
                        navigate(R.id.action_recipeFragment_to_articleFragment,bundle);
            }
        });

    }


}