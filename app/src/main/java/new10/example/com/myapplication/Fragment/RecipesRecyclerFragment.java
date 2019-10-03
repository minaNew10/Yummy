package new10.example.com.myapplication.Fragment;

import android.os.Bundle;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import new10.example.com.myapplication.Adapter.MainRecipesAdapter;
import new10.example.com.myapplication.Common.Common;
import new10.example.com.myapplication.Model.Recipe;
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
        MutableLiveData<List<Recipe>> recipes =  viewModel.getRecipes();
        //if we pass this in the observe it get linked to the life cycle of the fragment instance not the view which means it's only remove
        //in onDestroy so every time we destroy the view we add a new observer without removing the old one,
        recipes.observe(getViewLifecycleOwner(), new Observer<List<Recipe>>() {
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
        adapter = new MainRecipesAdapter(this);
        recyclerView.setAdapter(adapter);
        toast = Toast.makeText(getActivity(),"",Toast.LENGTH_LONG);
        return view;

    }


    @Override
    public void onClick(Recipe currRecipe) {
        Common.currRecipe = currRecipe;
        if(toast != null)
            toast.cancel();
        toast = Toast.makeText(getActivity(),currRecipe.getName(),Toast.LENGTH_LONG);
        toast.show();
    }


}
