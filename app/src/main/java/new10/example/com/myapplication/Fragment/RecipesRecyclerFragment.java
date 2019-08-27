package new10.example.com.myapplication.Fragment;

import android.os.Bundle;
import android.util.Log;
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
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import new10.example.com.myapplication.Adapter.MainRecipesAdapter;
import new10.example.com.myapplication.Model.Recipe;
import new10.example.com.myapplication.R;
import new10.example.com.myapplication.ViewModel.MainRecipesViewModel;

public class RecipesRecyclerFragment extends Fragment {
    private static final String TAG = "RecipesRecyclerFragment";
    @BindView(R.id.recycler_recipes_main)
    RecyclerView recyclerView;
    List<Recipe> recipesList = new ArrayList<>();
    MainRecipesAdapter adapter;
    private MainRecipesViewModel viewModel;
    public RecipesRecyclerFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(MainRecipesViewModel.class);
        adapter = new MainRecipesAdapter();
        MutableLiveData<List<Recipe>> recipes =  viewModel.getRecipes();
        boolean b = recipes == null;
        Log.i(TAG, "onCreate: " + b);
        recipes.observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                recipesList = recipes;

                adapter.setRecipes(recipesList);

            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipes_recycler,container,false);
        ButterKnife.bind(this,view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getActivity(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        return view;
    }
}
