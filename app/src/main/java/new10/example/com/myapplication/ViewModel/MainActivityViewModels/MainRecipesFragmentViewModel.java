package new10.example.com.myapplication.ViewModel.MainActivityViewModels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import new10.example.com.myapplication.Model.Ingredient;
import new10.example.com.myapplication.Model.Recipe;
import new10.example.com.myapplication.Model.Step;
import new10.example.com.myapplication.Repository.FavRecipeRepository;
import new10.example.com.myapplication.Repository.MainRecipesRepository;

public class MainRecipesFragmentViewModel extends ViewModel {


    private MutableLiveData<List<Recipe>> recipesMutLiveData;


    private MainRecipesRepository recipesRepository = MainRecipesRepository.getInstance();


    public MutableLiveData<List<Recipe>> getRecipes(){
        if(recipesMutLiveData == null){
            recipesMutLiveData = recipesRepository.getRecipes();
        }
        return recipesMutLiveData;
    }




}
