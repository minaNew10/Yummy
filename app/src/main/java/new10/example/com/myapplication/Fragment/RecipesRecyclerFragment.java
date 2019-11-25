package new10.example.com.myapplication.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import butterknife.ButterKnife;
import new10.example.com.myapplication.Activity.MainActivity;
import new10.example.com.myapplication.Activity.RecipeActivity;
import new10.example.com.myapplication.Adapter.MainRecipesAdapter;
import new10.example.com.myapplication.Common.Common;
import new10.example.com.myapplication.Model.Ingredient;
import new10.example.com.myapplication.Model.Recipe;
import new10.example.com.myapplication.Model.Step;
import new10.example.com.myapplication.R;
import new10.example.com.myapplication.ViewModel.MainRecipesViewModel;

public class RecipesRecyclerFragment extends Fragment implements MainRecipesAdapter.MainRecipesListener {
    private static final String TAG = "RecipesRecyclerFragment";
    @BindView(R.id.recycler_recipes_main)
    RecyclerView recyclerView;
    List<Recipe> recipesList = new ArrayList<>();
    MainRecipesAdapter adapter;
    private MainRecipesViewModel viewModel;
    Toast toast;
    Bundle b;
    public RecipesRecyclerFragment() {
    }
    //we use this method to be sure that we are using the view model of the activity
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //we intialize the view model here and not in oncreate because we want the data to be updated every time we create a view as
        // there is alife cycle for the fragmet instance and another for the view it contains so if we set the view model in oncreate
        //the data will not be updated as it is linked to the life cycle of the fragment itself we also want to be sure that the Activity is created
        viewModel = ViewModelProviders.of(getActivity()).get(MainRecipesViewModel.class);
         b = getArguments();
        if(b != null && b.get(getString(R.string.key_Favourites)).equals(getString(R.string.show_fav_recipes))){
            ((MainActivity)getActivity()).getSupportActionBar().setTitle(getString(R.string.show_fav_recipes));
            viewModel.getFavRecipes(getActivity()).observe(getViewLifecycleOwner(), new Observer<List<Recipe>>() {
                @Override
                public void onChanged(List<Recipe> recipes) {
                    recipesList = recipes;
                    for(int i = 0; i < recipesList.size(); ++i){
                        Recipe currRecipe = recipesList.get(i);
                        viewModel.getRecipeSteps(getActivity(),currRecipe.getId()).observe(getViewLifecycleOwner(), new Observer<List<Step>>() {
                            @Override
                            public void onChanged(List<Step> steps) {
                                currRecipe.setSteps(steps);
                            }
                        });
                        viewModel.getRecipeIngredients(getActivity(),currRecipe.getId()).observe(getViewLifecycleOwner(), new Observer<List<Ingredient>>() {
                            @Override
                            public void onChanged(List<Ingredient> ingredients) {
                                currRecipe.setIngredients(ingredients);
                            }
                        });
                    }
                    adapter.setRecipes(recipesList);
                }
            });

        }else {
            ((MainActivity)getActivity()).getSupportActionBar().setTitle(getString(R.string.app_name));
            viewModel.getRecipes().observe(getViewLifecycleOwner(), new Observer<List<Recipe>>() {
                @Override
                public void onChanged(List<Recipe> recipes) {
                    recipesList = recipes;
                    adapter.setRecipes(recipesList);
                }
            });


        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recipes_recycler,container,false);
        ButterKnife.bind(this,view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getActivity(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MainRecipesAdapter(getActivity(),this);
        recyclerView.setAdapter(adapter);
        toast = Toast.makeText(getActivity(),"",Toast.LENGTH_LONG);
//        if(b != null && b.get(getString(R.string.key_Favourites)).equals(getString(R.string.show_fav_recipes))){
//            ((MainActivity)getActivity()).getSupportActionBar().setTitle(getString(R.string.show_fav_recipes));
//        }else {
//            ((MainActivity)getActivity()).getSupportActionBar().setTitle(getString(R.string.show_main_list));
//        }
        setHasOptionsMenu(true);
        return view;

    }



    @Override
    public void onClick(Recipe currRecipe) {
        Intent intent = new Intent(getActivity(), RecipeActivity.class);
        intent.putExtra(getString(R.string.recipe_tag),currRecipe);
        startActivity(intent);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_show_favourites){
            if (b != null && b.get(getString(R.string.key_Favourites)).equals(getString(R.string.show_fav_recipes))) {
                item.setTitle(R.string.show_fav_recipes);
            } else {
                item.setTitle(R.string.show_main_list);
            }
        }
        return super.onOptionsItemSelected(item);
    }
}

