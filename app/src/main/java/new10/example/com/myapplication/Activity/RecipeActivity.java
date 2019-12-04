package new10.example.com.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import new10.example.com.myapplication.Fragment.RecipeActivityFragments.RecipeFragment;
import new10.example.com.myapplication.Model.Recipe;
import new10.example.com.myapplication.R;
import new10.example.com.myapplication.ViewModel.RecipeActivityViewModels.RecipeActivityViewModel;

public class RecipeActivity extends AppCompatActivity {
    private static final String TAG = "bug";
    private RecipeActivityViewModel viewModel;
    RecipeFragment recipeFragment;
    Toast toast;
    Recipe recipe;
    boolean isFav;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        bundle = getIntent().getExtras();

        recipe = bundle.getParcelable(getString(R.string.recipe_tag));
        toast = Toast.makeText(this,"",Toast.LENGTH_SHORT);
        setActionBarTitle(recipe.getName());

        FragmentManager fragmentManager = getSupportFragmentManager();
        setupViewModel();
        if(savedInstanceState == null) {

            fragmentManager.beginTransaction()
                    .add(R.id.recipe_fragment_container, recipeFragment,getString(R.string.recipe_fragment_tag))
                    .commit();
        }
    }

    private void setupViewModel() {
        viewModel = ViewModelProviders.of(this).get(RecipeActivityViewModel.class);
        recipeFragment = viewModel.getRecipeFragment();
        recipeFragment.setArguments(bundle);
        MutableLiveData<Boolean> isFavLive = viewModel.isFav(this,recipe);
        isFavLive.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                isFav = aBoolean;
            }
        });
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
            item.setIcon(R.drawable.star_fav);
        }else {
            delRecipe();
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
