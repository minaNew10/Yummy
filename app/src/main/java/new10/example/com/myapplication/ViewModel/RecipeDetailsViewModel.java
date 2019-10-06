package new10.example.com.myapplication.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import new10.example.com.myapplication.Model.Recipe;
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
}
