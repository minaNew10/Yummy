package new10.example.com.myapplication.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
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
import new10.example.com.myapplication.Model.Step;
import new10.example.com.myapplication.R;
import new10.example.com.myapplication.ViewModel.RecipeDetailsViewModel;

public class RecipeFragment extends Fragment implements RecipeDetailsAdapter.RecipeDetailsListener{
    @BindView(R.id.recycler_recipe_detail)
    RecyclerView recyclerView;
    Recipe currRecipe;
    RecipeDetailsAdapter adapter;
    private RecipeDetailsViewModel viewModel;

    OnStepClickListener onStepClickListener;

    public interface OnStepClickListener{
        void onStepSelected(Recipe recipe,int position);
    }

    public RecipeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_detail,container,false);
        ButterKnife.bind(this,view);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getActivity(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecipeDetailsAdapter(getActivity(),currRecipe,this);
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

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
      try {
          onStepClickListener = (OnStepClickListener) context;
      }catch(ClassCastException e) {
              throw new ClassCastException(context.toString() + " must implement OnStepClickListener ");
    }
    }

    @Override
    public void onClick(Recipe recipe,int position) {
        onStepClickListener.onStepSelected(currRecipe,position);
    }
}
