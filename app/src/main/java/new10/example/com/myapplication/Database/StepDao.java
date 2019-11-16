package new10.example.com.myapplication.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import new10.example.com.myapplication.Model.Step;

@Dao
public interface StepDao {
    @Insert
    void insert(Step step);

    @Delete
    void delete(Step... steps);

    @Query("SELECT * FROM step")
    List<Step> getAllSteps();

    @Query("SElECT * FROM step WHERE recipe_id =:recipe_id")
    LiveData<List<Step>> findStepsForRecipe(int recipe_id);
}
