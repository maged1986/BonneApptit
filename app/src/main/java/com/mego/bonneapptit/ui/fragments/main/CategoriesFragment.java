package com.mego.bonneapptit.ui.fragments.main;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mego.bonneapptit.R;
import com.mego.bonneapptit.adapters.CategoryAdapter;
import com.mego.bonneapptit.databinding.FragmentCategoriesBinding;
import com.mego.bonneapptit.models.Categories;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CategoriesFragment extends Fragment {

    private FragmentCategoriesBinding binding;
    private CategoryAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_categories,container,false);
        return binding.getRoot();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayList<Categories> categoriesList=new ArrayList<>();
        categoriesList.add(new Categories("Barbeque",R.drawable.barbeque));
        categoriesList.add(new Categories("Beef",R.drawable.beef));
        categoriesList.add(new Categories("Breakfast",R.drawable.breakfast));
        categoriesList.add(new Categories("Brunch",R.drawable.brunch));
        categoriesList.add(new Categories("Chicken",R.drawable.chicken));
        categoriesList.add(new Categories("Dinner",R.drawable.dinner));
        categoriesList.add(new Categories("Italian",R.drawable.italian));
        categoriesList.add(new Categories("Wine",R.drawable.wine));

        adapter=new CategoryAdapter(getContext(),categoriesList);
        binding.listview.setAdapter(adapter);
        binding.listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        binding.listview.setItemChecked(0, true);
        binding.listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Categories categories=categoriesList.get(position);
                Bundle bundle=new Bundle();
                bundle.putString("CategoryName",categories.getCategoryName());
                Navigation.findNavController(binding.listview).
                        navigate(R.id.action_categoriesFragment_to_recipeFragment,bundle);
            }
        });

    }
}