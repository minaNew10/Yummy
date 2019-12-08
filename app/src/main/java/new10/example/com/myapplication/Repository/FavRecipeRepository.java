package new10.example.com.myapplication.Repository;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import androidx.lifecycle.MutableLiveData;

import new10.example.com.myapplication.Activity.RecipeActivity;
import new10.example.com.myapplication.Database.AppDatabase;
import new10.example.com.myapplication.Database.RecipeProvider;
import new10.example.com.myapplication.Model.Ingredient;
import new10.example.com.myapplication.Model.Recipe;
import new10.example.com.myapplication.Model.Step;
import new10.example.com.myapplication.Utils.AppExecutors;
import new10.example.com.myapplication.Utils.EventMessage;
import new10.example.com.myapplication.Utils.MyLiveData;

import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

import androidx.lifecycle.LiveData;

import org.greenrobot.eventbus.EventBus;

import java.util.List;



public class FavRecipeRepository {

    private static AppDatabase appDatabase;
    private static Recipe currRecipe;
    private static boolean isFav;
    private static final String TAG = "bug";
    static Cursor cursor;
    public static LiveData<Cursor> getFavRecipes(Context context) {
        MutableLiveData<Cursor> recipesInCursor = new MutableLiveData<>();

        final String[] projection = {
                Recipe.COLUMN_ID,
                Recipe.COLUMN_NAME,
                Recipe.COLUMN_IMAGE,
                Recipe.COLUMN_SERVINGS,
        };
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                Cursor cursor = context.getContentResolver().query(RecipeProvider.URI_RECIPE,projection,null,null,null);
                recipesInCursor.postValue(cursor);
            }
        });

        return recipesInCursor;
    }
    public static MyLiveData<Cursor> getMyLiveData(Context context){
        final String[] projection = {
                Recipe.COLUMN_ID,
                Recipe.COLUMN_NAME,
                Recipe.COLUMN_IMAGE,
                Recipe.COLUMN_SERVINGS,
        };
        MyLiveData<Cursor> myLiveData = new MyLiveData<Cursor>(RecipeProvider.URI_RECIPE,context) {
            @Override
            public Cursor getContentProviderValue() {
                AppExecutors.getInstance().diskIO().execute(new Runnable() {

                    @Override
                    public void run() {
                        cursor = context.getContentResolver().query(RecipeProvider.URI_RECIPE,projection,null,null,null);
                        postValue(cursor);
                    }
                });
                return cursor;
            }
        };
        return myLiveData;
    }

    public static LiveData<Cursor> getFavRecipeSteps(Context context, int recipeId){
        MutableLiveData<Cursor> stepsInCursor = new MutableLiveData<>();
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                Cursor cursor  = context.getContentResolver().query(RecipeProvider.URI_STEP,null,null,null,null);
                stepsInCursor.postValue(cursor);
            }
        });

        return stepsInCursor;
    }
    public static LiveData<Cursor> getFavRecipeIngredients(Context context, int recipeId){
        MutableLiveData<Cursor> ingredientInCursor = new MutableLiveData<>();

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                Cursor cursor  = context.getContentResolver().query(RecipeProvider.URI_INGREDIENT,null,null,null,null);

            }
        });

        return ingredientInCursor;
    }

    public static MutableLiveData<Boolean> isFavRecipe(Context context, int itemId) {
        appDatabase = AppDatabase.getInstance(context);

        MutableLiveData<Boolean> isFavLive = new MutableLiveData<>();
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                //add your method of quering by id from your Dao
                currRecipe = appDatabase.recipeDao().loadRecipeById(itemId);
                if (currRecipe == null) {
                    isFavLive.postValue(false);
                } else {
                    isFavLive.postValue(true);
                }
            }
        });
        return isFavLive;
    }

    public static void insertRecipeIntoFav(Recipe item,Context context) {
        List<Step> steps = item.getSteps();
        List<Ingredient> ingredients = item.getIngredients();
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                ContentValues recipeValues = new ContentValues();
                recipeValues.put(Recipe.COLUMN_ID,item.getId());
                recipeValues.put(Recipe.COLUMN_NAME,item.getName());
                recipeValues.put(Recipe.COLUMN_SERVINGS,item.getServings());
                recipeValues.put(Recipe.COLUMN_IMAGE,item.getImage());
                Uri uri = context.getContentResolver().insert(RecipeProvider.URI_RECIPE,recipeValues);
                long id = ContentUris.parseId(uri);
                Log.i("Cursor", "recipe inserted with id = " + id);

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
        EventBus.getDefault().post(new EventMessage("recipe inserted"));
    }


    public static void removeRecipeFromFav(Recipe item,Context context) {
        Uri recipeUri = ContentUris.withAppendedId(RecipeProvider.URI_RECIPE,item.getId());

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                context.getContentResolver().delete(recipeUri,null,null);
            }
        });
        EventBus.getDefault().post(new EventMessage("recipe removed"));
    }

}

