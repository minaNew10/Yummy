package new10.example.com.myapplication.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import new10.example.com.myapplication.Model.Recipe;
import new10.example.com.myapplication.Repository.MainRecipesRepository;

public class MainRecipesViewModel extends ViewModel {


    private MutableLiveData<List<Recipe>> recipesMutLiveData;
    private MainRecipesRepository recipesRepository = MainRecipesRepository.getInstance();


    public MutableLiveData<List<Recipe>> getRecipes(){
        if(recipesMutLiveData == null){
            recipesMutLiveData = recipesRepository.getRecipes();
        }
        return recipesMutLiveData;
    }


}
