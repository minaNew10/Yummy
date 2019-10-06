package new10.example.com.myapplication.Repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import new10.example.com.myapplication.Model.Recipe;

public class RecipeDetailsRepository {
    private static RecipeDetailsRepository recipeDetailsRepository;
    MutableLiveData<Recipe> currRecipe = new MutableLiveData<>();

    public static RecipeDetailsRepository getInstance(){
        if(recipeDetailsRepository == null){
            recipeDetailsRepository = new RecipeDetailsRepository();
        }
        return recipeDetailsRepository;
    }

    public RecipeDetailsRepository() {

    }

    public void setRecipe(Recipe recipe){
        currRecipe.setValue(recipe);
    }

    public MutableLiveData<Recipe> getRecipe(){
        return currRecipe;
    }
}
