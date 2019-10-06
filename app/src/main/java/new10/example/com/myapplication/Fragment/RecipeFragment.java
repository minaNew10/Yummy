package new10.example.com.myapplication.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import new10.example.com.myapplication.Adapter.MainRecipesAdapter;
import new10.example.com.myapplication.Adapter.RecipeDetailsAdapter;
import new10.example.com.myapplication.Model.Recipe;
import new10.example.com.myapplication.R;
import new10.example.com.myapplication.ViewModel.RecipeDetailsViewModel;

public class RecipeFragment extends Fragment {
    @BindView(R.id.recycler_recipe_detail)
    RecyclerView recyclerView;
    Recipe currRecipe;
    RecipeDetailsAdapter adapter;
    private RecipeDetailsViewModel viewModel;

    public RecipeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_detail,container,false);
        ButterKnife.bind(this,view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getActivity(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecipeDetailsAdapter(getActivity(),currRecipe);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(RecipeDetailsViewModel.class);
        MutableLiveData<Recipe> recipe = viewModel.getRecipe();

        recipe.observe(getViewLifecycleOwner(), new Observer<Recipe>() {
            @Override
            public void onChanged(Recipe recipe) {
                currRecipe = recipe;
                adapter.setRecipe(currRecipe);

            }
        });
    }
}
