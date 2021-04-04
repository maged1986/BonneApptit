package com.mego.bonneapptit.ui.fragments.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mego.bonneapptit.R;
import com.mego.bonneapptit.adapters.RecipeAdapter;
import com.mego.bonneapptit.databinding.FragmentFavoriteBinding;
import com.mego.bonneapptit.models.Recipe;
import com.mego.bonneapptit.viewmodels.FavoriteViewModel;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class FavoriteFragment extends Fragment {
 private FragmentFavoriteBinding binding;
 private FavoriteViewModel viewModel;
    private RecipeAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_favorite,container,false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter=new RecipeAdapter(getContext());
        viewModel=new ViewModelProvider(getActivity()).get(FavoriteViewModel.class);
        RecyclerView.LayoutManager llm=new LinearLayoutManager(getContext());
        binding.favFragRvRecipes.setLayoutManager(llm);
        viewModel.getFavList().observe(getViewLifecycleOwner(), new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                adapter.setList(recipes);
                Log.d("TAG", "onChanged: fav"+ recipes);
            }
        });
            binding.favFragRvRecipes.setAdapter(adapter);

        adapter.setOnItemClickListener(new RecipeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String recipe_id) {
                Bundle bundle=new Bundle();
                bundle.putString("rId",recipe_id);
                Navigation.findNavController(binding.favFragRvRecipes).
                        navigate(R.id.action_favoriteFragment_to_articleFragment,bundle);
            }
        });
        setupSwipe();
    }
    private void setupSwipe() {
        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int swipedRecipePosition = viewHolder.getAdapterPosition();
                Recipe swipedRecipe = adapter.getRecipePosition(swipedRecipePosition);
                viewModel.deleteRecipe(swipedRecipe.getTitle());
                adapter.notifyDataSetChanged();
                Toast.makeText(getContext(), "recipe deleted from database", Toast.LENGTH_SHORT).show();
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(binding.favFragRvRecipes);
    }
}