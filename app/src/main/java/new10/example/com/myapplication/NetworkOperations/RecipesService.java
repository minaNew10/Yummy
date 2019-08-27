package new10.example.com.myapplication.NetworkOperations;

import java.util.List;

import new10.example.com.myapplication.Model.Recipe;
import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipesService {
    @GET("baking.json")
    Call<List<Recipe>> getRecipesList();
}
