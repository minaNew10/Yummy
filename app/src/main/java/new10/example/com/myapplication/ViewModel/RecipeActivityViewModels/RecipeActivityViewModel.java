package new10.example.com.myapplication.ViewModel.RecipeActivityViewModels;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.greenrobot.eventbus.EventBus;

import new10.example.com.myapplication.Fragment.MainActivityFragments.ChildFavListFragment;
import new10.example.com.myapplication.Fragment.RecipeActivityFragments.IngredientFragment;
import new10.example.com.myapplication.Fragment.RecipeActivityFragments.RecipeFragment;
import new10.example.com.myapplication.Fragment.RecipeActivityFragments.StepFragment;
import new10.example.com.myapplication.Model.Recipe;
import new10.example.com.myapplication.Repository.FavRecipeRepository;

public class RecipeActivityViewModel extends ViewModel {
    private RecipeFragment recipeFragment;
    private IngredientFragment ingredientFragment;

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
    public IngredientFragment getIngredientFragment() {
        if(ingredientFragment == null){
            ingredientFragment = new IngredientFragment();
        }
        return ingredientFragment;
    }



    public void insertRecipeIntoFav(Recipe recipe, Context context){

        FavRecipeRepository.insertRecipeIntoFav(recipe,context);


    }

    public void removeRecipeFromFav(Recipe recipe,Context context){

        FavRecipeRepository.removeRecipeFromFav(recipe,context);

    }

    public MutableLiveData<Boolean> isFav(Context context, Recipe recipe){
        return FavRecipeRepository.isFavRecipe(context,recipe.getId());

    }


}
