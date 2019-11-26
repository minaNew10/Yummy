package new10.example.com.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.MenuItem;

import new10.example.com.myapplication.Fragment.ChildFavListFragment;
import new10.example.com.myapplication.Fragment.ChildMainListFragment;

import new10.example.com.myapplication.R;
import new10.example.com.myapplication.ViewModel.MainActivityViewModel;

public class MainActivity extends AppCompatActivity{
    ChildMainListFragment mainFragment;
    ChildFavListFragment favFragment;
    FragmentManager fragmentManager;
    MainActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViewModel();
        fragmentManager = getSupportFragmentManager();
        if(savedInstanceState == null) {
            fragmentManager.beginTransaction()
                    .add(R.id.recipes_List_fragment_container, mainFragment, getString(R.string.recipes_list_fragment_tag))
                    .commit();
        }
    }

    private void setupViewModel(){
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        mainFragment = viewModel.getMainFragment();
        favFragment = viewModel.getFavFragment();
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_show_list:
                    if(item.getTitle().equals(getString(R.string.show_fav_recipes)))
                        replaceFragment(favFragment, getString(R.string.fav_fragment_tag));

                    else
                        replaceFragment(mainFragment,getString(R.string.recipes_list_fragment_tag));
                break;

        }
        return true;
    }

    @Override
    public void onBackPressed() {
        ChildMainListFragment fragment =(ChildMainListFragment) fragmentManager.findFragmentByTag(getString(R.string.recipes_list_fragment_tag));
        if(fragment != null && fragment.isAdded()){
            fragmentManager.popBackStack();
            finish();
        }
        super.onBackPressed();
    }


    private void replaceFragment (Fragment fragment,String tag){
        FragmentManager manager = getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate (tag, 0);

        if (!fragmentPopped){
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.recipes_List_fragment_container, fragment);
            ft.addToBackStack(tag);
            ft.commit();
        }
    }
}
