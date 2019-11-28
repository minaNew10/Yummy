package new10.example.com.myapplication.ViewModel.RecipeActivityViewModels;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import new10.example.com.myapplication.Fragment.RecipeActivityFragments.IngredientFragment;
import new10.example.com.myapplication.Fragment.RecipeActivityFragments.RecipeFragment;
import new10.example.com.myapplication.Fragment.RecipeActivityFragments.StepFragment;
import new10.example.com.myapplication.Model.Recipe;
import new10.example.com.myapplication.Repository.FavRecipeRepository;

public class RecipeActivityViewModel extends ViewModel {
    private RecipeFragment recipeFragment;

    private Recipe recipe;

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public RecipeFragment getRecipeFragment(){
        if(recipeFragment == null){
            recipeFragment = new RecipeFragment();
        }
        return recipeFragment;
    }



    public void insertRecipeIntoFav(Recipe recipe, Context context){
        FavRecipeRepository.insertRecipeIntoFav(recipe,context);

    }

    public void removeRecipeFromFav(Recipe recipe){
        FavRecipeRepository.removeRecipeFromFav(recipe);
    }

    public boolean isFav(Context context, Recipe recipe){
        return FavRecipeRepository.isFavRecipe(context,recipe.getId());

    }
}
