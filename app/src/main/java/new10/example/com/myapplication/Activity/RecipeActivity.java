package new10.example.com.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import new10.example.com.myapplication.Fragment.IngredientFragment;
import new10.example.com.myapplication.Fragment.RecipeFragment;
import new10.example.com.myapplication.Fragment.StepFragment;

import new10.example.com.myapplication.Model.Ingredient;
import new10.example.com.myapplication.Model.Recipe;
import new10.example.com.myapplication.Model.Step;
import new10.example.com.myapplication.R;
import new10.example.com.myapplication.ViewModel.RecipeDetailsViewModel;

public class RecipeActivity extends AppCompatActivity implements RecipeFragment.OnStepClickListener {

    private RecipeDetailsViewModel recipeDetailsViewModel;
    Recipe recipe;
    boolean isFav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        Bundle bundle = getIntent().getExtras();

        recipe = bundle.getParcelable(getString(R.string.recipe_tag));

        setActionBarTitle(recipe.getName());

        setupViewModel();
        if(savedInstanceState == null) {
            RecipeFragment fragment = new RecipeFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.recipe_fragment_container, fragment,getString(R.string.recipe_fragment_tag))
                    .commit();
        }else {
            RecipeFragment fragment = (RecipeFragment) getSupportFragmentManager().findFragmentByTag(getString(R.string.recipe_fragment_tag));
        }
    }

    private void setupViewModel() {
        recipeDetailsViewModel = ViewModelProviders.of(this).get(RecipeDetailsViewModel.class);
        recipeDetailsViewModel.setRecipe(recipe);
        MutableLiveData<Boolean> isFavLiveData = recipeDetailsViewModel.isFav(this,recipe);
        isFavLiveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                isFav = aBoolean;
            }
        });
    }

    @Override
    public void onStepSelected(Recipe recipe, int position) {
        Bundle b = new Bundle();
        b.putString(getString(R.string.key_recipe_name),recipe.getName());
        //there is no need to pass neither the ingredients nor the steps list
        //as it is received from the viewModel
        if(position == 0){

            IngredientFragment ingredientFragment = new IngredientFragment();
            ingredientFragment.setArguments(b);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction().addToBackStack(null);
            ft.replace(R.id.recipe_fragment_container,ingredientFragment,getString(R.string.tag_ingredient_fragment));
            ft.commit();

        }else {

            b.putInt(getString(R.string.key_position),position);
            StepFragment stepFragment = new StepFragment();
            stepFragment.setArguments(b);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction().addToBackStack(null);
            ft.replace(R.id.recipe_fragment_container,stepFragment,getString(R.string.tag_step_fragment));
            ft.commit();

        }
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
        recipeDetailsViewModel.removeRecipeFromFav(recipe);
    }

    private void saveRecipe() {
        recipeDetailsViewModel.insertRecipeIntoFav(recipe,this);
    }
}
