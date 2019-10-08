package new10.example.com.myapplication.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

import new10.example.com.myapplication.Fragment.RecipeFragment;
import new10.example.com.myapplication.Fragment.RecipesRecyclerFragment;
import new10.example.com.myapplication.Fragment.StepFragment;
import new10.example.com.myapplication.Model.Recipe;
import new10.example.com.myapplication.Model.Step;
import new10.example.com.myapplication.R;
import new10.example.com.myapplication.ViewModel.RecipeDetailsViewModel;

public class RecipeActivity extends AppCompatActivity implements RecipeFragment.OnStepClickListener {

private RecipeDetailsViewModel recipeDetailsViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        Bundle bundle = getIntent().getExtras();

        Recipe recipe = bundle.getParcelable(getString(R.string.recipe_tag));

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
        if(position == 0){

        }else {
            b.putParcelableArrayList(getString(R.string.key_steps),(ArrayList<Step>) recipe.getSteps());
            b.putInt(getString(R.string.key_position),position);
            b.putString(getString(R.string.key_recipe_name),recipe.getName());
            StepFragment stepFragment = new StepFragment();

            stepFragment.setArguments(b);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.recipe_fragment_container,stepFragment,getString(R.string.tag_step_fragment));
            ft.commit();
        }
    }
    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
}
