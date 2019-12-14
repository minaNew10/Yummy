package new10.example.com.myapplication.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.RemoteViews;

import androidx.appcompat.app.AlertDialog;

import new10.example.com.myapplication.Activity.MainActivity;

import new10.example.com.myapplication.R;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                String ingred,int appWidgetId) {


        // Construct the RemoteViews object
//        RemoteViews views = getFavRecipesGridRemoteViews(context);
        RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.recipe_widget);
        views.setTextViewText(R.id.txtv_widget_Ingredients,ingred);
        Intent intent = new Intent(context,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,0);
        views.setOnClickPendingIntent(R.id.txtv_widget_Ingredients,pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetId, views);

    }

    //is called when widget is created first then with each update according to the timer set on the xml file

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        IngredientsViewService.startActionViewIngredients(context);

    }
    public static void updateRecipeWidgets(Context context, AppWidgetManager appWidgetManager, String ingred, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager,ingred,appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setTitle("")
                .setMessage("")
                .setPositiveButton("", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                    }
                }).setNegativeButton("", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

}

