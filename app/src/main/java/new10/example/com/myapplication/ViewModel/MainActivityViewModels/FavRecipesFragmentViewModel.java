package new10.example.com.myapplication.ViewModel.MainActivityViewModels;

import android.content.Context;
import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import new10.example.com.myapplication.Model.Ingredient;
import new10.example.com.myapplication.Model.Recipe;
import new10.example.com.myapplication.Model.Step;
import new10.example.com.myapplication.Repository.FavRecipeRepository;

public class FavRecipesFragmentViewModel extends ViewModel {

    private LiveData<Cursor> favRecipes;
    private LiveData<Cursor> favSteps;
    private LiveData<Cursor> favIngredient;

    public LiveData<Cursor> getFavRecipes(Context context){
        if(favRecipes == null){
            favRecipes = FavRecipeRepository.getFavRecipes(context);
        }
        return favRecipes;
    }

    public LiveData<Cursor> getFavRecipeSteps(Context context, int recipeId){
            favSteps = FavRecipeRepository.getFavRecipeSteps(context,recipeId);
        return favSteps;
    }

    public LiveData<Cursor> getFavRecipeIngredients(Context context,int recipeId){
            favIngredient = FavRecipeRepository.getFavRecipeIngredients(context,recipeId);
        return favIngredient;
    }
}
