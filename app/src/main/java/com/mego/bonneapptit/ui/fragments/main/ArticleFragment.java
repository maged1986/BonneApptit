package com.mego.bonneapptit.ui.fragments.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mego.bonneapptit.R;
import com.mego.bonneapptit.databinding.FragmentArticleBinding;
import com.mego.bonneapptit.models.responses.RecipeResponse;
import com.mego.bonneapptit.viewmodels.ArticleViewModel;


public class ArticleFragment extends Fragment {
    private ArticleFragmentArgs args;
    private FragmentArticleBinding binding;
    private String recipe_id;
    private ArticleViewModel viewModel;
    private RecipeResponse response;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_article, container, false);
        return binding.getRoot();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) {
            args = ArticleFragmentArgs.fromBundle(getArguments());
            recipe_id = args.getRId();
        }
        viewModel = new ViewModelProvider(getActivity()).get(ArticleViewModel.class);
        viewModel.setRecipes(recipe_id);
        viewModel.getRecipe().observe(getViewLifecycleOwner(), new Observer<RecipeResponse>() {
            @Override
            public void onChanged(RecipeResponse recipeResponse) {
                response = recipeResponse;
                binding.articleFragTvTitle.setText(recipeResponse.getRecipe().getTitle());
                float rating = recipeResponse.getRecipe().getSocial_rank() / 20;
                binding.articleFragRbSocialRank.setRating(rating);
                Glide.with(binding.getRoot()).load(recipeResponse.getRecipe().getImage_url())
                        .centerCrop().into(binding.articleFragIvImage);
                binding.articleFragTvPublisher.setText(recipeResponse.getRecipe().getPublisher());
                for (String ingredient : recipeResponse.getRecipe().getIngredients()) {
                    TextView textView = new TextView(getContext());
                    textView.setText(ingredient);
                    textView.setTextSize(15);
                    textView.setLayoutParams(new LinearLayout.
                            LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    ));
                    binding.articleFragTvIngredients.addView(textView);

                }
                binding.articleFragFabFav.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(response.getRecipe() != null){
                        viewModel.insertRecipe(response.getRecipe());
                        Toast.makeText(getContext(), "done", Toast.LENGTH_LONG).show();}
                        else {
                            Toast.makeText(getContext(), " Not done", Toast.LENGTH_LONG).show();}
                    }
                });
            }
        });
    }
}