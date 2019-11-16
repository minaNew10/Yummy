package new10.example.com.myapplication.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import new10.example.com.myapplication.Model.Ingredient;
import new10.example.com.myapplication.Model.Step;

@Dao
public interface IngredientDao {
    @Insert
    void insert(Ingredient ingredient);

    @Delete
    void delete(Ingredient... ingredients);

    @Query("SELECT * FROM ingredient")
    List<Ingredient> getAllIngredients();

    @Query("SElECT * FROM ingredient WHERE recipe_id =:recipe_id")
    LiveData<List<Ingredient>> findIngredientsForRecipe(int recipe_id);
}
