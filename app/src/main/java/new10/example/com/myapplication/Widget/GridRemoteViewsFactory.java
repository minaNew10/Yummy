package new10.example.com.myapplication.Widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;
import java.util.List;

import new10.example.com.myapplication.Database.RecipeProvider;
import new10.example.com.myapplication.Model.Recipe;
import new10.example.com.myapplication.R;

public class GridRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
   Context context;
   Cursor mCursor;
    private static final String TAG = "GridRemoteViewsFactory";
    public GridRemoteViewsFactory(Context applicationContext) {
        this.context = applicationContext;
    }

    @Override
    public void onCreate() {

    }

    //to trigger this method you need to call notifyAppWidgetviewDataChanged
    @Override
    public void onDataSetChanged() {
        Uri uri = RecipeProvider.URI_RECIPE;
        if(mCursor != null) mCursor.close();
        mCursor = context.getContentResolver().query(
                uri,
                null,null,null,null
        );

    }

    @Override
    public void onDestroy() {
        mCursor.close();
    }

    @Override
    public int getCount() {
        if(mCursor == null) return 0;
        return mCursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        if(this.mCursor == null || this.mCursor.getCount() == 0){
            Log.i(TAG, "getViewAt: " + mCursor);
            return null;
        }
        Log.i(TAG, "getViewAt: "+ mCursor.getCount());
        this.mCursor.moveToPosition(i);
        int id_index = mCursor.getColumnIndex(Recipe.COLUMN_ID);
        int name_index = mCursor.getColumnIndex(Recipe.COLUMN_NAME);
        int serving_index = mCursor.getColumnIndex(Recipe.COLUMN_SERVINGS);
        int image_index = mCursor.getColumnIndex(Recipe.COLUMN_IMAGE);

        int id = mCursor.getInt(id_index);
        String name = mCursor.getString(name_index);
        String image = mCursor.getString(image_index);
        int servings = mCursor.getInt(serving_index);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);

        views.setTextViewText(R.id.txtv_widger_name,name);
        if(name.equalsIgnoreCase(context.getString(R.string.yellow_cake))){
            views.setImageViewResource(R.id.imgv_widget_recipe,R.drawable.yellow_cake);
        }else if(name.equalsIgnoreCase(context.getString(R.string.brownies))){
            views.setImageViewResource(R.id.imgv_widget_recipe,R.drawable.brownies);
        }else if(name.equalsIgnoreCase(context.getString(R.string.cheese_cake))) {
            views.setImageViewResource(R.id.imgv_widget_recipe,R.drawable.cheese_cake);
        }else{
            views.setImageViewResource(R.id.imgv_widget_recipe,R.drawable.nutella_pie);
        }

        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
