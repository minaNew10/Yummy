package new10.example.com.myapplication.Fragment.MainActivityFragments;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

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
import new10.example.com.myapplication.ViewModel.MainActivityViewModels.FavRecipesFragmentViewModel;

public class ChildFavListFragment extends ParentFragmentForMainlist {
    private FavRecipesFragmentViewModel viewModel;
    //we use this method to intialize view Model to be sure that we are using the view model of the activity

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //we intialize the view model here and not in oncreate because we want the data to be updated every time we create a view as
        // there is alife cycle for the fragmet instance and another for the view it contains so if we set the view model in oncreate
        //the data will not be updated as it is linked to the life cycle of the fragment itself we also want to be sure that the Activity is created
        viewModel = ViewModelProviders.of(getActivity()).get(FavRecipesFragmentViewModel.class);
        ((MainActivity)getActivity()).getSupportActionBar().setTitle(getString(R.string.show_fav_recipes));
        viewModel.getFavRecipes(getActivity()).observe(getViewLifecycleOwner(), new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                recipesList = recipes;
                for(int i = 0; i < recipesList.size(); ++i){
                    Recipe currRecipe = recipesList.get(i);
                    viewModel.getFavRecipeSteps(getActivity(),currRecipe.getId()).observe(getViewLifecycleOwner(), new Observer<List<Step>>() {
                        @Override
                        public void onChanged(List<Step> steps) {
                            currRecipe.setSteps(steps);
                        }
                    });
                    viewModel.getFavRecipeIngredients(getActivity(),currRecipe.getId()).observe(getViewLifecycleOwner(), new Observer<List<Ingredient>>() {
                        @Override
                        public void onChanged(List<Ingredient> ingredients) {
                            currRecipe.setIngredients(ingredients);
                        }
                    });
                }
                adapter.setRecipes(recipesList);
            }
        });
    }



    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.getItem(0).setTitle(R.string.show_main_list);
    }
}