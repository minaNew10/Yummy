package new10.example.com.myapplication.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import new10.example.com.myapplication.Activity.RecipeActivity;
import new10.example.com.myapplication.Adapter.MainRecipesAdapter;
import new10.example.com.myapplication.Model.Recipe;
import new10.example.com.myapplication.R;
import new10.example.com.myapplication.ViewModel.MainRecipesFragmentViewModel;

public class ParentFragmentForMainlist extends Fragment implements MainRecipesAdapter.MainRecipesListener {
    @BindView(R.id.recycler_recipes_main)
    RecyclerView recyclerView;
    List<Recipe> recipesList = new ArrayList<>();
    MainRecipesAdapter adapter;
    private MainRecipesFragmentViewModel viewModel;
    Toast toast;
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
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main,menu);
    }

    @Override
    public void onClick(Recipe currRecipe) {
        Intent intent = new Intent(getActivity(), RecipeActivity.class);
        intent.putExtra(getString(R.string.recipe_tag),currRecipe);
        startActivity(intent);
    }
}
