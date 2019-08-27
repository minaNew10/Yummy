package new10.example.com.myapplication.Repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import new10.example.com.myapplication.Model.Recipe;
import new10.example.com.myapplication.NetworkOperations.RecipesService;
import new10.example.com.myapplication.NetworkOperations.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainRecipesRepository {
    private static MainRecipesRepository recipesRepository;
    private static final String TAG = "MainRecipesRepository";

    public static MainRecipesRepository getInstance(){
        if(recipesRepository == null){
            recipesRepository = new MainRecipesRepository();
        }
        return recipesRepository;
    }
    private RecipesService recipesService;

    public MainRecipesRepository() {
        recipesService = RetrofitService.createService(RecipesService.class);
    }

    public MutableLiveData<List<Recipe>> getRecipes(){
        MutableLiveData<List<Recipe>> recipesData = new MutableLiveData<>();
        recipesService.getRecipesList().enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                if(response.isSuccessful()){
                    recipesData.setValue(response.body());
                   
                    Log.i(TAG, "onResponse: "+ response.body().get(0).getName());
                }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                    recipesData.setValue(null);
                Log.i(TAG, "onResponse: "+ t.getMessage());
            }
        });
        return recipesData;
    }


}
