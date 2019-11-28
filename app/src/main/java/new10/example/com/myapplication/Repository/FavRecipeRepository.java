package new10.example.com.myapplication.Repository;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import androidx.lifecycle.MutableLiveData;

import new10.example.com.myapplication.Database.AppDatabase;
import new10.example.com.myapplication.Database.RecipeProvider;
import new10.example.com.myapplication.Model.Ingredient;
import new10.example.com.myapplication.Model.Recipe;
import new10.example.com.myapplication.Model.Step;
import new10.example.com.myapplication.Utils.AppExecutors;

import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class FavRecipeRepository {

    private static AppDatabase appDatabase;
    private static Recipe currRecipe;
    private static boolean isFav;

    public static LiveData<List<Recipe>> getFavRecipes(Context context) {
        appDatabase = AppDatabase.getInstance(context);
        // add your method for querig all the list
        LiveData<List<Recipe>> Recipes = appDatabase.recipeDao().getAllRecipes();

        return Recipes;
    }

    public static LiveData<List<Step>> getFavRecipeSteps(Context context, int recipeId){
        appDatabase = AppDatabase.getInstance(context);
        LiveData<List<Step>> steps = appDatabase.stepDao().findStepsForRecipe(recipeId);

        return steps;
    }
    public static LiveData<List<Ingredient>> getFavRecipeIngredients(Context context, int recipeId){
        appDatabase = AppDatabase.getInstance(context);
        LiveData<List<Ingredient>> ingredients = appDatabase.ingredientDao().findIngredientsForRecipe(recipeId);

        return ingredients;
    }

    public static boolean isFavRecipe(Context context, int itemId) {
        appDatabase = AppDatabase.getInstance(context);


        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                //add your method of quering by id from your Dao
                currRecipe = appDatabase.recipeDao().loadRecipeById(itemId);

                if (currRecipe == null) {
                    isFav = false;
                } else {
                    isFav = true;
                }
            }
        });

        return isFav;
    }

    public static void insertRecipeIntoFav(Recipe item,Context context) {
        List<Step> steps = item.getSteps();
        List<Ingredient> ingredients = item.getIngredients();
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                ContentValues recipeValues = new ContentValues();
                recipeValues.put(Recipe.COLUMN_NAME,item.getName());
                recipeValues.put(Recipe.COLUMN_SERVINGS,item.getServings());
                recipeValues.put(Recipe.COLUMN_IMAGE,item.getImage());
                Uri uri = context.getContentResolver().insert(RecipeProvider.URI_RECIPE,recipeValues);
                long id = ContentUris.parseId(uri);

                for(int i = 0; i < steps.size();++i){
                    Step step = steps.get(i);
                    ContentValues stepValues = new ContentValues();
                    stepValues.put(Step.COLUMN_VIDEO_URL,step.getVideoURL());
                    stepValues.put(Step.COLUMN_THUMBNAIL_URL,step.getThumbnailURL());
                    stepValues.put(Step.COLUMN_DESCRIPTION,step.getDescription());
                    stepValues.put(Step.COLUMN_SHORT_DESCRIPTION,step.getShortDescription());
                    stepValues.put(Step.COLUMN_RECIPE_ID,id);
                    context.getContentResolver().insert(RecipeProvider.URI_STEP,stepValues);
                }
                for(int i = 0; i < ingredients.size();++i){
                    Ingredient ingredient = ingredients.get(i);
                    ContentValues ingredientValues = new ContentValues();
                    ingredientValues.put(Ingredient.COLUMN_MEASURE,ingredient.getMeasure());
                    ingredientValues.put(Ingredient.COLUMN_QUANTITY,ingredient.getQuantity());
                    ingredientValues.put(Ingredient.COLUMN_NAME,ingredient.getIngredient());
                    ingredientValues.put(Ingredient.COLUMN_RECIPE_ID,id);
                    context.getContentResolver().insert(RecipeProvider.URI_INGREDIENT,ingredientValues);
                }
            }
        });
    }


    public static void removeRecipeFromFav(Recipe item,Context context) {
        Uri recipeUri = ContentUris.withAppendedId(RecipeProvider.URI_RECIPE,item.getId());

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                context.getContentResolver().delete(recipeUri,null,null);
            }
        });
    }



}
