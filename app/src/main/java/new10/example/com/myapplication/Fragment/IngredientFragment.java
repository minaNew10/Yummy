package new10.example.com.myapplication.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import new10.example.com.myapplication.Adapter.IngredientAdapter;
import new10.example.com.myapplication.Adapter.RecipeDetailsAdapter;
import new10.example.com.myapplication.Model.Ingredient;
import new10.example.com.myapplication.Model.Recipe;
import new10.example.com.myapplication.R;
import new10.example.com.myapplication.ViewModel.RecipeDetailsViewModel;

public class IngredientFragment extends Fragment {
    @BindView(R.id.recycler_ingredients)
    RecyclerView recyclerView;
    private RecipeDetailsViewModel viewModel;
    IngredientAdapter ingredientAdapter;
    ArrayList<Ingredient> ingredients;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ingredients,container,false);
        ButterKnife.bind(this,v);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getActivity(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        ingredientAdapter = new IngredientAdapter(getActivity());
        recyclerView.setAdapter(ingredientAdapter);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(RecipeDetailsViewModel.class);
        MutableLiveData<Recipe> recipe = viewModel.getRecipe();
        recipe.observe(getViewLifecycleOwner(), new Observer<Recipe>() {
            @Override
            public void onChanged(Recipe recipe) {
                ingredients = (ArrayList) recipe.getIngredients();
                ingredientAdapter.setItems(ingredients);
            }
        });
    }
}
