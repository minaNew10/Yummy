package new10.example.com.myapplication.Database;

import android.database.Cursor;

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
    long insert(Ingredient ingredient);

    @Delete
    void delete(Ingredient... ingredients);

    @Query("SELECT * FROM ingredient")
    List<Ingredient> getAllIngredients();

    @Query("SELECT * FROM ingredient")
    Cursor getAllIngredientsInCursor();

    @Query("SElECT * FROM ingredient WHERE recipe_id =:recipe_id")
    LiveData<List<Ingredient>> findIngredientsForRecipe(int recipe_id);

    @Query("DELETE FROM "+ Ingredient.TABLE_NAME + " WHERE "+ Ingredient.COLUMN_RECIPE_ID +" =:recipe_id")
    int  delIngredientsForRecipe(int recipe_id);
}
