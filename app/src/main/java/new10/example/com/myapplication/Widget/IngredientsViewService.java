package new10.example.com.myapplication.Widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import new10.example.com.myapplication.Database.RecipeProvider;
import new10.example.com.myapplication.Model.Ingredient;
import new10.example.com.myapplication.Model.Recipe;
import new10.example.com.myapplication.Repository.FavRecipeRepository;

public class IngredientsViewService extends IntentService {
    public static final String ACION_VIEW_INGREDIENTS = "new10.example.com.myapplication.Widget.action.view.ingredients";

    public IngredientsViewService() {
        super("IngredientsViewService");
    }

    public static void startActionViewIngredients(Context context){
        Intent intent = new Intent(context,IngredientsViewService.class);
        intent.setAction(ACION_VIEW_INGREDIENTS);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if(intent != null){

            final String action = intent.getAction();
            if(ACION_VIEW_INGREDIENTS.equals(action)){
                handleActionViewIngredients();
            }

        }

    }

    private void handleActionViewIngredients() {

        String ingred = "water , milk, wheat";
        Cursor cursor = getContentResolver().query(RecipeProvider.URI_RECIPE,
                null,null,null,null,null);
        cursor.moveToPosition(0);
        int columnId = cursor.getColumnIndex(Recipe.COLUMN_ID);
        int id = cursor.getInt(columnId);
        String[] selecArg = {String.valueOf(id)};
        Cursor ingredCursor = getContentResolver().query(RecipeProvider.URI_INGREDIENT,
                null,Ingredient.COLUMN_RECIPE_ID,selecArg,null);
        List<String> ingredients = handleIngredientsCursor(ingredCursor);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetsId = appWidgetManager.getAppWidgetIds
                (new ComponentName(this,RecipeWidgetProvider.class))  ;
        RecipeWidgetProvider.updateRecipeWidgets(this,appWidgetManager,ingredients.toString(),appWidgetsId);
    }

    private List<String> handleIngredientsCursor(Cursor cursor) {
        List<String> ingredients = new ArrayList<>();
        int id_index = cursor.getColumnIndex(Ingredient.COLUMN_ID);
        int name_index = cursor.getColumnIndex(Ingredient.COLUMN_NAME);
        int measure_index = cursor.getColumnIndex(Ingredient.COLUMN_MEASURE);
        int quan_index = cursor.getColumnIndex(Ingredient.COLUMN_QUANTITY);
        int recipe_id_index = cursor.getColumnIndex(Ingredient.COLUMN_RECIPE_ID);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(id_index);
            String name = cursor.getString(name_index);
            String measure = cursor.getString(measure_index);
            float quan = cursor.getFloat(quan_index);
            int recipe_id = cursor.getInt(recipe_id_index);
            Ingredient ingredient = new Ingredient(quan, measure, name);
            ingredients.add(name);
        }
        return ingredients;
    }
}
