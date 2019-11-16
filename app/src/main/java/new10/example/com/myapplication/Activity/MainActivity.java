package new10.example.com.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


import java.util.List;


import new10.example.com.myapplication.Fragment.RecipesRecyclerFragment;
import new10.example.com.myapplication.Model.Recipe;
import new10.example.com.myapplication.R;

public class MainActivity extends AppCompatActivity implements RecipesRecyclerFragment.OnRecipesChangedListener{
    RecipesRecyclerFragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState == null) {
            fragment = new RecipesRecyclerFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().addToBackStack(null)
                    .add(R.id.recipes_List_fragment_container, fragment,getString(R.string.recipes_list_fragment_tag))
                    .commit();
        }else {
            fragment = (RecipesRecyclerFragment) getSupportFragmentManager().findFragmentByTag(getString(R.string.recipes_list_fragment_tag));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_show_favourites:
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.detach(fragment);
                Bundle b = new Bundle();
                b.putString(getString(R.string.key_Favourites),getString(R.string.show_fav_recipes));
                fragment.setArguments(b);
                fragmentTransaction.attach(fragment).addToBackStack(null);
                fragmentTransaction.commit();
        }
        return true;
    }

    @Override
    public void onRecipesChanged(List<Recipe> recipes) {

    }
}
