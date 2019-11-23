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

public class MainActivity extends AppCompatActivity{
    RecipesRecyclerFragment mainFragment;
    RecipesRecyclerFragment favFragment;
    boolean isFav;
    FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        if(savedInstanceState == null) {
            mainFragment = new RecipesRecyclerFragment();
            favFragment = new RecipesRecyclerFragment();
            Bundle b = new Bundle();
            b.putString(getString(R.string.key_Favourites),getString(R.string.show_fav_recipes));
            favFragment.setArguments(b);
            fragmentManager.beginTransaction()
                    .add(R.id.recipes_List_fragment_container, mainFragment,getString(R.string.recipes_list_fragment_tag))
                    .commit();
        }else {
            mainFragment = (RecipesRecyclerFragment) fragmentManager.findFragmentByTag(getString(R.string.recipes_list_fragment_tag));
            favFragment = (RecipesRecyclerFragment) fragmentManager.findFragmentByTag(getString(R.string.fav_fragment_tag));
            isFav = savedInstanceState.getBoolean(getString(R.string.key_Favourites));
            if(isFav){
                replaceFragment(favFragment,getString(R.string.fav_fragment_tag));
            }else {
                replaceFragment(mainFragment,getString(R.string.recipes_list_fragment_tag));
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        if(getSupportActionBar().getTitle().equals(getString(R.string.show_fav_recipes))){
            menu.getItem(0).setTitle(getString(R.string.show_main_list));
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_show_favourites:

                if(item.getTitle().equals(getString(R.string.show_fav_recipes))) {
                    isFav = true;

                    replaceFragment(favFragment,getString(R.string.fav_fragment_tag));
                    item.setTitle(getString(R.string.show_main_list));
                }else{
                    isFav = false;

                    replaceFragment(mainFragment,getString(R.string.recipes_list_fragment_tag));
                    item.setTitle(getString(R.string.show_fav_recipes));
                }
                break;

        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(!isFav){
            finish();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(getString(R.string.key_Favourites),isFav);
    }

    private void replaceFragment (Fragment fragment,String tag){


        FragmentManager manager = getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate (tag, 0);

        if (!fragmentPopped){ //fragment not in back stack, create it.
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.recipes_List_fragment_container, fragment);
            ft.addToBackStack(tag);
            ft.commit();
        }
    }
}
