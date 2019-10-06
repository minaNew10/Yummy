package new10.example.com.myapplication.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.widget.Toast;

import new10.example.com.myapplication.Fragment.RecipeFragment;
import new10.example.com.myapplication.Fragment.RecipesRecyclerFragment;
import new10.example.com.myapplication.Model.Recipe;
import new10.example.com.myapplication.R;
import new10.example.com.myapplication.ViewModel.RecipeDetailsViewModel;

public class RecipeActivity extends AppCompatActivity {
private RecipeDetailsViewModel recipeDetailsViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        Bundle bundle = getIntent().getExtras();
        Recipe recipe = bundle.getParcelable(getString(R.string.recipe_tag));

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(recipe.getName());

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
}
