package new10.example.com.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import new10.example.com.myapplication.Database.RecipeProvider;
import new10.example.com.myapplication.Fragment.RecipeActivityFragments.IngredientFragment;
import new10.example.com.myapplication.Fragment.RecipeActivityFragments.RecipeFragment;
import new10.example.com.myapplication.Fragment.RecipeActivityFragments.StepFragment;
import new10.example.com.myapplication.Model.Ingredient;
import new10.example.com.myapplication.Model.Recipe;
import new10.example.com.myapplication.R;
import new10.example.com.myapplication.ViewModel.RecipeActivityViewModels.RecipeActivityViewModel;
import new10.example.com.myapplication.Widget.RecipeWidgetProvider;

public class RecipeActivity extends AppCompatActivity {

    private RecipeActivityViewModel viewModel;
    RecipeFragment recipeFragment;
    IngredientFragment ingredientFragment;
    Toast toast;
    Recipe recipe;
    boolean isFav;
    boolean isTablet;
    Bundle bundle;
    MyContentObserver myContentObserver = new MyContentObserver(null);
    private static final String TAG = "RecipeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        bundle = getIntent().getExtras();
        getContentResolver().registerContentObserver(RecipeProvider.URI_RECIPE,true,myContentObserver);
        recipe = bundle.getParcelable(getString(R.string.recipe_tag));
        extractIngredientsForWidget();
        isTablet = getResources().getBoolean(R.bool.isTablet);
        toast = Toast.makeText(this,"",Toast.LENGTH_SHORT);
        setActionBarTitle(recipe.getName());

        FragmentManager fragmentManager = getSupportFragmentManager();
        setupViewModel();
        if(savedInstanceState == null) {
            fragmentManager.beginTransaction()
                    .add(R.id.recipe_fragment_container, recipeFragment,getString(R.string.recipe_fragment_tag))
                    .commit();
            if(isTablet){
                Bundle ingredientsBundle = new Bundle();
                ingredientsBundle.putParcelableArrayList(getString(R.string.key_ingredients),(ArrayList)recipe.getIngredients());
                ingredientFragment.setArguments(ingredientsBundle);
                fragmentManager.beginTransaction()
                        .add(R.id.step_fragment_container_tab,ingredientFragment,getString(R.string.tag_step_fragment))
                        .commit();
            }
        }
    }

    private  void extractIngredientsForWidget() {
        List<Ingredient> ingredients = recipe.getIngredients();
        StringBuilder ingredientsSb = new StringBuilder();
        for (int i = 0; i < ingredients.size(); i++) {
            Ingredient ingredient = ingredients.get(i);
            ingredientsSb.append(ingredient.getQuantity() + " " + ingredient.getMeasure() + " " + ingredient.getIngredient());
            if(i != ingredients.size()-1)
                ingredientsSb.append("\n");
        }
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.shared_pref),Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.key_recipe_name),recipe.getName());
        editor.putString(getString(R.string.saved_ingredients), ingredientsSb.toString());
        editor.commit();
//        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
//        int[] appWidgetsId = appWidgetManager.getAppWidgetIds
//                (new ComponentName(this,RecipeWidgetProvider.class));

    }

    private void setupViewModel() {
        viewModel = ViewModelProviders.of(this).get(RecipeActivityViewModel.class);
        recipeFragment = viewModel.getRecipeFragment();
        ingredientFragment = viewModel.getIngredientFragment();
        recipeFragment.setArguments(bundle);
        MutableLiveData<Boolean> isFavLive = viewModel.isFav(this,recipe);
        isFavLive.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                isFav = aBoolean;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getContentResolver().unregisterContentObserver(myContentObserver);
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_recipe,menu);
        MenuItem item =  menu.getItem(0);
        if(isFav){
            item.setIcon(R.drawable.star_fav);
        }else {
            item.setIcon(R.drawable.star);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_add_to_favourites:
                handleActionFav(item);
                break;
        }
        return true;
    }

    private void handleActionFav(MenuItem item) {
        if(!isFav) {
            saveRecipe();
            isFav = true;
            item.setIcon(R.drawable.star_fav);
        }else {
            delRecipe();
            isFav = false;
            item.setIcon(R.drawable.star);
        }
    }

    private void delRecipe() {
        viewModel.removeRecipeFromFav(recipe,this);
    }

    private void saveRecipe() {
        viewModel.insertRecipeIntoFav(recipe,this);
    }
}
//this class is for learning purpose
class MyContentObserver extends ContentObserver{
    private static final String TAG = "MyContentObserver";
    public MyContentObserver(Handler handler) {
        super(handler);
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
        Log.i(TAG, "onChange: ");

    }

}