package new10.example.com.myapplication.Fragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.List;

import new10.example.com.myapplication.Activity.MainActivity;
import new10.example.com.myapplication.Model.Ingredient;
import new10.example.com.myapplication.Model.Recipe;
import new10.example.com.myapplication.Model.Step;
import new10.example.com.myapplication.R;
import new10.example.com.myapplication.ViewModel.FavRecipesFragmentViewModel;
import new10.example.com.myapplication.ViewModel.MainRecipesFragmentViewModel;

public class ChildMainListFragment extends ParentFragmentForMainlist {
    private MainRecipesFragmentViewModel viewModel;

    //we use this method to to intialize view Model to be sure that we are using the view model of the activity
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //we intialize the view model here and not in oncreate because we want the data to be updated every time we create a view as
        // there is alife cycle for the fragmet instance and another for the view it contains so if we set the view model in oncreate
        //the data will not be updated as it is linked to the life cycle of the fragment itself we also want to be sure that the Activity is created
        viewModel = ViewModelProviders.of(getActivity()).get(MainRecipesFragmentViewModel.class);
        ((MainActivity)getActivity()).getSupportActionBar().setTitle(getString(R.string.app_name));
        viewModel.getRecipes().observe(getViewLifecycleOwner(), new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                recipesList = recipes;

                adapter.setRecipes(recipesList);
            }
        });

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.getItem(0).setTitle(R.string.show_fav_recipes);
    }


}
