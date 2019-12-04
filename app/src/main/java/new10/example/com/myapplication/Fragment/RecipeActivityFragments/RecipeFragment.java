package new10.example.com.myapplication.Fragment.RecipeActivityFragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import new10.example.com.myapplication.Adapter.RecipeDetailsAdapter;
import new10.example.com.myapplication.Model.Recipe;
import new10.example.com.myapplication.R;
import new10.example.com.myapplication.ViewModel.MainActivityViewModels.MainActivityViewModel;
import new10.example.com.myapplication.ViewModel.RecipeActivityViewModels.RecipeDetailsFragmentViewModel;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class RecipeFragment extends Fragment implements RecipeDetailsAdapter.RecipeDetailsListener{
    @BindView(R.id.recycler_recipe_detail)
    public RecyclerView recyclerView;
    Recipe currRecipe;

    RecipeDetailsAdapter adapter;
    IngredientFragment ingredientFragment;
    StepFragment stepFragment;
    Bundle b;
    private RecipeDetailsFragmentViewModel viewModel;

    public RecipeFragment() {
    }

    private void setupViewModel(Recipe recipe){
        viewModel = ViewModelProviders.of(getActivity()).get(RecipeDetailsFragmentViewModel.class);
        viewModel.setRecipe(recipe);
        currRecipe = viewModel.getRecipe();

        stepFragment = viewModel.getStepFragment();
        ingredientFragment = viewModel.getIngredientFragment();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_detail,container,false);
        ButterKnife.bind(this,view);
        Bundle b = getArguments();
        currRecipe = b.getParcelable(getString(R.string.recipe_tag));
        setupViewModel(currRecipe);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getActivity(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecipeDetailsAdapter(getActivity(),currRecipe,this);
        adapter.setRecipe(currRecipe);
        recyclerView.setAdapter(adapter);
        return view;
    }




    @Override
    public void onClick(Recipe recipe,int position) {
        if(position == 0){
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null);
            Bundle ingredientsBundle = new Bundle();
            ingredientsBundle.putParcelableArrayList(getString(R.string.key_ingredients),(ArrayList)currRecipe.getIngredients());
            ingredientFragment.setArguments(ingredientsBundle);
            ft.replace(R.id.recipe_fragment_container,ingredientFragment,getString(R.string.tag_ingredient_fragment));
            ft.commit();
        }else {
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null);

            Bundle stepsBundle = new Bundle();
            stepsBundle.putParcelableArrayList(getString(R.string.key_steps),(ArrayList)currRecipe.getSteps());
            stepsBundle.putInt(getString(R.string.key_position),position);

            stepFragment.setArguments(stepsBundle);
            ft.replace(R.id.recipe_fragment_container,stepFragment,getString(R.string.tag_step_fragment));
            ft.commit();
        }
    }
}
