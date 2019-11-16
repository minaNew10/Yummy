package new10.example.com.myapplication.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import new10.example.com.myapplication.Model.Recipe;
import new10.example.com.myapplication.R;

public class MainRecipesAdapter extends RecyclerView.Adapter<MainRecipesAdapter.RecipeViewHolder> {
    List<Recipe> recipes = new ArrayList<>();
    MainRecipesListener mainRecipesListener;
    Context context;
    private static final String TAG = "MainRecipesAdapter";
    public interface MainRecipesListener{
        void onClick(Recipe currRecipe);
    }

    public MainRecipesAdapter() {

    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
        notifyDataSetChanged();
    }

    public MainRecipesAdapter(Context context, MainRecipesListener mainRecipesListener) {
        this.context = context;
        this.mainRecipesListener = mainRecipesListener;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_recipe_item,parent,false);
        return new RecipeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);
        holder.textView.setText(recipe.getName());
        if(recipe.getName().equalsIgnoreCase(context.getString(R.string.yellow_cake))){
            holder.imageView.setImageResource(R.drawable.yellow_cake);
        }else if(recipe.getName().equalsIgnoreCase(context.getString(R.string.brownies))){
            holder.imageView.setImageResource(R.drawable.brownies);
        }else if(recipe.getName().equalsIgnoreCase(context.getString(R.string.cheese_cake))) {
            holder.imageView.setImageResource(R.drawable.cheese_cake);
        }else{
            holder.imageView.setImageResource(R.drawable.nutella_pie);
        }
    }

    @Override
    public int getItemCount(){
        if(recipes == null)
            return 0;
        return recipes.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.txtv_name_item)
        TextView textView;
        @BindView(R.id.imgv_main_recycler_item)
        ImageView imageView;
        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mainRecipesListener.onClick(recipes.get(getAdapterPosition()));
        }
    }
}
