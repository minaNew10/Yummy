package new10.example.com.myapplication.Repository;

import android.content.Context;
import androidx.lifecycle.MutableLiveData;

import new10.example.com.myapplication.Database.AppDatabase;
import new10.example.com.myapplication.Model.Ingredient;
import new10.example.com.myapplication.Model.Recipe;
import new10.example.com.myapplication.Model.Step;
import new10.example.com.myapplication.Utils.AppExecutors;

import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class FavRecipeRepository {

    private static AppDatabase appDatabase;
    private static Recipe currRecipe;

    public static LiveData<List<Recipe>> getFavRecipes(Context context) {
        appDatabase = AppDatabase.getInstance(context);
        // add your method for querig all the list
        LiveData<List<Recipe>> Recipes = appDatabase.recipeDao().getAllRecipes();

        return Recipes;
    }

    public static LiveData<List<Step>> getRecipeSteps(Context context,int recipeId){
        appDatabase = AppDatabase.getInstance(context);
        LiveData<List<Step>> steps = appDatabase.stepDao().findStepsForRecipe(recipeId);

        return steps;
    }
    public static LiveData<List<Ingredient>> getRecipeIngredients(Context context,int recipeId){
        appDatabase = AppDatabase.getInstance(context);
        LiveData<List<Ingredient>> ingredients = appDatabase.ingredientDao().findIngredientsForRecipe(recipeId);

        return ingredients;
    }

    public static MutableLiveData<Boolean> isFavRecipe(Context context, int itemId) {
        appDatabase = AppDatabase.getInstance(context);
        MutableLiveData<Boolean> isFav = new MutableLiveData<>();

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                //add your method of quering by id from your Dao
                currRecipe = appDatabase.recipeDao().loadRecipeById(itemId);

                if (currRecipe == null) {
                    Log.i(TAG, "run: currMovie is null");
                    isFav.postValue(false);
                } else {
                    Log.i(TAG, "run: currMovie is not null");
                    isFav.postValue(true);
                }
            }
        });

        return isFav;
    }

    public static void insertRecipeIntoFav(Recipe item,Context context) {
        appDatabase = AppDatabase.getInstance(context);
        List<Step> steps = item.getSteps();
        List<Ingredient> ingredients = item.getIngredients();
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {// your insert method from your Dao
                long id = appDatabase.recipeDao().insert(item);
                for(int i = 0; i < steps.size();++i){
                    Step step = steps.get(i);
                    step.setRecipe_id((int)id);
                    appDatabase.stepDao().insert(step);
                }
                for(int i = 0; i < ingredients.size();++i){
                    Ingredient ingredient = ingredients.get(i);
                    ingredient.setRecipe_id((int)id);
                    appDatabase.ingredientDao().insert(ingredient);
                }
            }
        });

    }


    public static void removeRecipeFromFav(Recipe item) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                //add your del method form your Dao
                appDatabase.recipeDao().insert(item);
            }
        });
    }



}
