package new10.example.com.myapplication.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import new10.example.com.myapplication.Activity.MainActivity;

import new10.example.com.myapplication.R;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {


        // Construct the RemoteViews object
        RemoteViews views = getFavRecipesGridRemoteViews(context);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
    //is called when widget is created first then with each update
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
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

    /**
     * Creates and returns the Remote views to be displayed in the GridView mode Widget*/

    private static RemoteViews getFavRecipesGridRemoteViews(Context context){
        RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.recipe_widget_gridview);
        //set the adapter for the gridView
        Intent intent = new Intent(context, GridWidgetService.class);
        views.setRemoteAdapter(R.id.grid_view_widget,intent);

        //set the Main activity to launch when clicked
        Intent appIntent = new Intent(context,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,appIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.grid_view_widget,pendingIntent);
//        views.setEmptyView(R.id.grid_view_widget,R.id.empty_appwidget_text);
        return views;
    }
}

