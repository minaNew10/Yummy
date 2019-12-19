package new10.example.com.myapplication.ViewModel.RecipeActivityViewModels;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import new10.example.com.myapplication.Fragment.RecipeActivityFragments.IngredientFragment;
import new10.example.com.myapplication.Fragment.RecipeActivityFragments.StepFragment;
import new10.example.com.myapplication.Model.Recipe;
import new10.example.com.myapplication.Repository.FavRecipeRepository;

public class RecipeDetailsFragmentViewModel extends ViewModel {

    private Recipe recipe;

    private IngredientFragment ingredientFragment;
    private StepFragment stepFragment;
    public void setRecipe(Recipe recipe){
        this.recipe = recipe;
    }

    public Recipe getRecipe(){
        return recipe;
    }

    public IngredientFragment getIngredientFragment(){
        if(ingredientFragment == null){
            ingredientFragment = new IngredientFragment();
        }
        return ingredientFragment;
    }

    public StepFragment getStepFragment(){
            stepFragment = new StepFragment();
        return stepFragment;
    }

}
