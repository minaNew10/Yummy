package new10.example.com.myapplication.ViewModel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import new10.example.com.myapplication.Model.Ingredient;
import new10.example.com.myapplication.Model.Recipe;
import new10.example.com.myapplication.Model.Step;
import new10.example.com.myapplication.Repository.FavRecipeRepository;
import new10.example.com.myapplication.Repository.MainRecipesRepository;

public class MainRecipesViewModel extends ViewModel {


    private MutableLiveData<List<Recipe>> recipesMutLiveData;
    private LiveData<List<Recipe>> favRecipes;
    private LiveData<List<Step>> favSteps;
    private LiveData<List<Ingredient>> favIngredient;
    private MainRecipesRepository recipesRepository = MainRecipesRepository.getInstance();


    public MutableLiveData<List<Recipe>> getRecipes(){
        if(recipesMutLiveData == null){
            recipesMutLiveData = recipesRepository.getRecipes();
        }
        return recipesMutLiveData;
    }

    public LiveData<List<Recipe>> getFavRecipes(Context context){
        if(favRecipes == null){
            favRecipes = FavRecipeRepository.getFavRecipes(context);
        }
        return favRecipes;
    }
    public LiveData<List<Step>> getRecipeSteps(Context context,int recipeId){
        if(favSteps == null){
            favSteps = FavRecipeRepository.getRecipeSteps(context,recipeId);
        }
        return favSteps;
    }
    public LiveData<List<Ingredient>> getRecipeIngredients(Context context,int recipeId){
        if(favIngredient == null){
            favIngredient = FavRecipeRepository.getRecipeIngredients(context,recipeId);
        }
        return favIngredient;
    }

//    public LiveData<List<Step>> getFavSteps(Context context){
//        if(favSteps == null){
//            favSteps = FavRecipeRepository.getFavSteps(context);
//        }
//        return favSteps;
//    }

}
