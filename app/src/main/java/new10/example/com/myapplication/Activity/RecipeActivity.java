package new10.example.com.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        Bundle bundle = getIntent().getExtras();

        recipe = bundle.getParcelable(getString(R.string.recipe_tag));

        setActionBarTitle(recipe.getName());

        recipeDetailsViewModel = ViewModelProviders.of(this).get(RecipeDetailsViewModel.class);
        recipeDetailsViewModel.setRecipe(recipe);
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

    @Override
    public void onStepSelected(Recipe recipe, int position) {
        Bundle b = new Bundle();
        b.putString(getString(R.string.key_recipe_name),recipe.getName());
        if(position == 0){
            b.putParcelableArrayList(getString(R.string.key_ingredients),(ArrayList<Ingredient>) recipe.getIngredients());
            IngredientFragment ingredientFragment = new IngredientFragment();
            ingredientFragment.setArguments(b);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction().addToBackStack(null);
            ft.replace(R.id.recipe_fragment_container,ingredientFragment,getString(R.string.tag_ingredient_fragment));
            ft.commit();
        }else {
            b.putParcelableArrayList(getString(R.string.key_steps),(ArrayList<Step>) recipe.getSteps());
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
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_add_to_favourites:
                saveRecipe();
                break;
        }
        return true;
    }

    private void saveRecipe() {
        recipeDetailsViewModel.insertRecipeIntoFav(recipe,this);
    }
}
