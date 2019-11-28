package new10.example.com.myapplication.Fragment.RecipeActivityFragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import new10.example.com.myapplication.Adapter.IngredientAdapter;
import new10.example.com.myapplication.Model.Ingredient;
import new10.example.com.myapplication.R;
import new10.example.com.myapplication.ViewModel.RecipeActivityViewModels.IngredientsFragmentViewModel;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class IngredientFragment extends Fragment {
    @BindView(R.id.recycler_ingredients)
    public RecyclerView recyclerView;
    private IngredientsFragmentViewModel viewModel;
    IngredientAdapter ingredientAdapter;
    List<Ingredient> currIngredients;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ingredients,container,false);
        ButterKnife.bind(this,v);
        Bundle b = getArguments();
        currIngredients = b.getParcelableArrayList(getString(R.string.key_ingredients));
        setupViewModel(currIngredients);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getActivity(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        ingredientAdapter = new IngredientAdapter(getActivity());
        ingredientAdapter.setItems(currIngredients);
        recyclerView.setAdapter(ingredientAdapter);
        return v;
    }

    private void setupViewModel(List<Ingredient> ingredients) {
        viewModel = ViewModelProviders.of(getActivity()).get(IngredientsFragmentViewModel.class);
        viewModel.setRecipeIngredients(ingredients);
        currIngredients = viewModel.getRecipeIngredients();
    }


}
