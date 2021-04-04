package com.mego.bonneapptit.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.mego.bonneapptit.R;
import com.mego.bonneapptit.models.Categories;

import java.util.ArrayList;
import java.util.Locale;

public class CategoryAdapter extends ArrayAdapter<Categories> {


    public CategoryAdapter(@NonNull Context context, ArrayList<Categories> categoryList) {
        super(context, 0,categoryList);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Categories categories=getItem(position);

        convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_category_list_item, parent, false);
        TextView tvName = convertView.findViewById(R.id.category_title);
        ImageView ivImage = convertView.findViewById(R.id.category_image);
        tvName.setText(categories.getCategoryName());
        Glide.with(convertView).load(categories.getImageUrl()).fitCenter().into(ivImage);
        return convertView;

    }

}
