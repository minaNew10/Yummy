package new10.example.com.myapplication.ViewModel.RecipeActivityViewModels;

import androidx.lifecycle.ViewModel;

import java.util.List;

import new10.example.com.myapplication.Model.Ingredient;

public class IngredientsFragmentViewModel extends ViewModel {

    private List<Ingredient> recipeIngredients;

    public void setRecipeIngredients(List<Ingredient> ingredients){
        this.recipeIngredients = ingredients;
    }

    public List<Ingredient> getRecipeIngredients(){
        return recipeIngredients;
    }
}
