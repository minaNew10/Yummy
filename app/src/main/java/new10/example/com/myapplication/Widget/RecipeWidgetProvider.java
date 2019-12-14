package new10.example.com.myapplication.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.widget.RemoteViews;

import androidx.appcompat.app.AlertDialog;

import new10.example.com.myapplication.Activity.MainActivity;

import new10.example.com.myapplication.R;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {


        // Construct the RemoteViews object
//        RemoteViews views = getFavRecipesGridRemoteViews(context);
        RemoteViews views = getFavIngredientRemoteViews(context);


        appWidgetManager.updateAppWidget(appWidgetId, views);

    }

    private static RemoteViews getFavIngredientRemoteViews(Context context) {
        Resources r = context.getResources();
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.recipe_widget);
        SharedPreferences sharedPreferences = context.getSharedPreferences(r.getString(R.string.shared_pref),context.MODE_PRIVATE);
        String title = sharedPreferences.getString(r.getString(R.string.key_recipe_name),"No Recipe Choosed");
        String ingred = sharedPreferences.getString(r.getString(R.string.saved_ingredients), "");
        remoteViews.setTextViewText(R.id.txtv_widget_title,title);
        remoteViews.setTextViewText(R.id.txtv_widget_Ingredients,ingred);
        Intent intent = new Intent(context,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,0);
        remoteViews.setOnClickPendingIntent(R.id.txtv_widget_Ingredients,pendingIntent);
        return remoteViews;
    }

    //is called when widget is created first then with each update according to the timer set on the xml file

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
//        IngredientsViewService.startActionViewIngredients(context);
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager,appWidgetId);
        }

    }


    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

}

