package com.mego.bonneapptit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mego.bonneapptit.R;
import com.mego.bonneapptit.models.Recipe;

import java.util.ArrayList;
import java.util.List;
public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeHolder> {
    // this class is adapter for recipe list

    private  OnItemClickListener listener;

    Context context;
    private List<Recipe> mList = new ArrayList<>();
    public RecipeAdapter( Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecipeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recipe_list_item,parent,false);
        return new RecipeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeHolder holder, int position) {
        Recipe recipe=mList.get(position);
        holder.bind(recipe);

    }
    public Recipe getRecipePosition(int position){
        return mList.get(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public void setList(List<Recipe> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    class RecipeHolder extends RecyclerView.ViewHolder {
        TextView title, publisher;
        ImageView imageView;


        public RecipeHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.recipe_title);
            publisher = itemView.findViewById(R.id.recipe_publisher);
            imageView = itemView.findViewById(R.id.recipe_image);
        }

        public void bind(Recipe recipe) {
            title.setText(recipe.getTitle());
            publisher.setText(recipe.getPublisher());
            Glide.with(imageView).load(recipe.getImage_url()).centerCrop().into(imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(recipe.getRecipe_id());
                }
            });
        }
    }
    public interface OnItemClickListener {
        void onItemClick(String recipe_id);
    }
}
