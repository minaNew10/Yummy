package new10.example.com.myapplication.ViewModel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import new10.example.com.myapplication.Model.Ingredient;
import new10.example.com.myapplication.Model.Recipe;
import new10.example.com.myapplication.Model.Step;
import new10.example.com.myapplication.Repository.FavRecipeRepository;

public class FavRecipesFragmentViewModel extends ViewModel {

    private LiveData<List<Recipe>> favRecipes;
    private LiveData<List<Step>> favSteps;
    private LiveData<List<Ingredient>> favIngredient;

    public LiveData<List<Recipe>> getFavRecipes(Context context){
        if(favRecipes == null){
            favRecipes = FavRecipeRepository.getFavRecipes(context);
        }
        return favRecipes;
    }

    public LiveData<List<Step>> getFavRecipeSteps(Context context, int recipeId){
        if(favSteps == null){
            favSteps = FavRecipeRepository.getFavRecipeSteps(context,recipeId);
        }
        return favSteps;
    }

    public LiveData<List<Ingredient>> getFavRecipeIngredients(Context context,int recipeId){
        if(favIngredient == null){
            favIngredient = FavRecipeRepository.getFavRecipeIngredients(context,recipeId);
        }
        return favIngredient;
    }
}
