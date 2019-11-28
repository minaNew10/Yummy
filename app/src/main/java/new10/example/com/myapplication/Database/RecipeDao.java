package new10.example.com.myapplication.Database;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;


import new10.example.com.myapplication.Model.Recipe;

@Dao
public interface RecipeDao {
    @Insert
    long insert(Recipe recipe);

    @Delete
    void delete(Recipe... recipes);

    @Query("DELETE FROM " + Recipe.TABLE_NAME + " WHERE "+ Recipe.COLUMN_ID  + " = :id")
    int deleteById(int id);

    @Query("SELECT * FROM recipe")
    LiveData<List<Recipe>> getAllRecipes();

    @Query("SELECT * FROM recipe")
    Cursor getAllRecipesInCursor();


    @Query("SELECT * FROM recipe WHERE id  = :id")
    Recipe loadRecipeById(int id);



}
