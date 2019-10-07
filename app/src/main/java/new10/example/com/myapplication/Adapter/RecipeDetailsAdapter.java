package new10.example.com.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import new10.example.com.myapplication.Model.Recipe;
import new10.example.com.myapplication.R;

public class RecipeDetailsAdapter extends RecyclerView.Adapter<RecipeDetailsAdapter.RecipeDetailViewHolder> {
    Context context;
    Recipe recipe;
    RecipeDetailsListener recipeDetailsListener;

    public interface RecipeDetailsListener{
        void onClick(Recipe recipe,int position);
    }


    public RecipeDetailsAdapter(Context context, Recipe recipe) {
        this.context = context;
        this.recipe = recipe;
    }

    public RecipeDetailsAdapter(Context context, Recipe recipe, RecipeDetailsListener recipeDetailsListener) {
        this.context = context;
        this.recipe = recipe;
        this.recipeDetailsListener = recipeDetailsListener;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecipeDetailsAdapter.RecipeDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_detail_item,parent,false);
        return new RecipeDetailViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeDetailsAdapter.RecipeDetailViewHolder holder, int position) {
        if (position == 0) {
            holder.textView.setText(context.getResources().getString(R.string.ingredients));
        } else {
            holder.textView.setText(recipe.getSteps().get(position-1).getShortDescription());
        }
    }

    @Override
    public int getItemCount() {
        if(recipe == null)
            return 0;
        return recipe.getSteps().size() + 1;
    }

    public class RecipeDetailViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.txtv_recipe_detail_item)
        TextView textView;

        public RecipeDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            recipeDetailsListener.onClick(recipe,getAdapterPosition());
        }
    }
    }
