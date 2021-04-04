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
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mego.bonneapptit.adapters.RecipeAdapter;
import com.mego.bonneapptit.databinding.FragmentAllRecipesBinding;
import com.mego.bonneapptit.models.Recipe;
import com.mego.bonneapptit.viewmodels.AllRecipesViewModel;
import com.mego.bonneapptit.R;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AllRecipesFragment extends Fragment {

    private AllRecipesViewModel mViewModel;
    private FragmentAllRecipesBinding binding;
    private RecipeAdapter adapter;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
         binding= DataBindingUtil.inflate(inflater,R.layout.fragment_all_recipes, container, false);
         return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AllRecipesViewModel.class);
        adapter = new RecipeAdapter(getContext());
        RecyclerView.LayoutManager llm=new LinearLayoutManager(getContext());
        binding.allRecipsFragRv.setLayoutManager(llm);
        mViewModel.setRecipes();
        mViewModel.getRecipes().observe(getViewLifecycleOwner(), new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                adapter.setList(recipes);
            }
        });
        binding.allRecipsFragRv.setAdapter(adapter);
        adapter.setOnItemClickListener(new RecipeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String recipe_id) {
                Bundle bundle=new Bundle();
                bundle.putString("rId",recipe_id);
                Navigation.findNavController(binding.allRecipsFragRv).
                        navigate(R.id.action_allRecipesFragment_to_articleFragment,bundle);
            }
        });
    }
}