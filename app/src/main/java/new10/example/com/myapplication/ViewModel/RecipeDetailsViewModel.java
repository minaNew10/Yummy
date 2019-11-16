package new10.example.com.myapplication.ViewModel;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import java.util.logging.LogRecord;

import new10.example.com.myapplication.Model.Recipe;
import new10.example.com.myapplication.Model.Step;
import new10.example.com.myapplication.Repository.FavRecipeRepository;
import new10.example.com.myapplication.Repository.RecipeDetailsRepository;

public class RecipeDetailsViewModel extends ViewModel {

    private MutableLiveData<Recipe> recipeLiveData;
    RecipeDetailsRepository repository = RecipeDetailsRepository.getInstance();

    public void setRecipe(Recipe recipe){
        repository.setRecipe(recipe);
    }

    public MutableLiveData<Recipe> getRecipe(){
        if(recipeLiveData == null){
            recipeLiveData = repository.getRecipe();
        }
        return recipeLiveData;
    }
    Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    public void insertRecipeIntoFav(Recipe recipe,Context context){
        FavRecipeRepository.insertRecipeIntoFav(recipe,context);
//        FavRecipeRepository.insertStepsOfRecipe(recipe);
//        FavRecipeRepository.insertIngredientsOfRecipe(recipe);
    }

    public void removeRecipeFromFav(Recipe recipe){
        FavRecipeRepository.removeRecipeFromFav(recipe);
    }
}
