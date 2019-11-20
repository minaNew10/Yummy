package new10.example.com.myapplication.Database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.exoplayer2.source.MediaSource;

import new10.example.com.myapplication.Model.Ingredient;
import new10.example.com.myapplication.Model.Recipe;
import new10.example.com.myapplication.Model.Step;

public class RecipeProvider extends ContentProvider {

    /** The authority of this content provider. */
    public static final String AUTHORITY = "new10.example.com.myapplication";

   /** The URI For the recipe table*/
   public static final Uri URI_RECIPE = Uri.parse("content://" + AUTHORITY + "/"+ Recipe.TABLE_NAME);
   /** The URI For the step table*/
   public static final Uri URI_STEP = Uri.parse("content://" + AUTHORITY + "/"+ Step.TABLE_NAME);
   /** The URI For the ingredient table*/
   public static final Uri URI_INGREDIENT = Uri.parse("content://" + AUTHORITY + "/"+ Ingredient.TABLE_NAME);

   private static final int RECIPES = 100;
   private static final int RECIPE = 101;
   private static final int INGREDIENTS = 102;
   private static final int STEPS = 103;

   private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

   static {
       MATCHER.addURI(AUTHORITY,Recipe.TABLE_NAME,RECIPES);
       MATCHER.addURI(AUTHORITY,Recipe.TABLE_NAME+"/#",RECIPE);
       MATCHER.addURI(AUTHORITY,Ingredient.TABLE_NAME,INGREDIENTS);
       MATCHER.addURI(AUTHORITY,Step.TABLE_NAME,STEPS);

   }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        final Context context = getContext();
        Cursor cursor;
        if (context == null) {
            return null;
        }
       final int code = MATCHER.match(uri);
       switch (code){
           case RECIPES:
               cursor = AppDatabase.getInstance(context).recipeDao().getAllRecipesInCursor();
               break;
           case STEPS:
               cursor = AppDatabase.getInstance(context).stepDao().getAllStepsInCursor();
                break;
           case INGREDIENTS:
               cursor = AppDatabase.getInstance(context).ingredientDao().getAllIngredientsInCursor();
               break;
           default:
               throw new IllegalArgumentException("Cannot query unknown URI " + uri);

       }
       cursor.setNotificationUri(context.getContentResolver(),uri);
       return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
       int match = MATCHER.match(uri);
       switch (match) {
           case RECIPES:
               return Recipe.CONTENT_LIST_TYPE;
           case RECIPE:
               return Recipe.CONTENT_ITEM_TYPE;
           case INGREDIENTS:
               return Ingredient.CONTENT_LIST_TYPE;
           case STEPS:
               return Step.CONTENT_LIST_TYPE;
           default:
               throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
       }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
       final int match = MATCHER.match(uri);
        final Context context = getContext();
        if (context == null) {
            return null;
        }
       switch (match){
           case RECIPES:
               final long id_recipe  = AppDatabase.getInstance(context).recipeDao().
                       insert(Recipe.fromContentValues(contentValues));
                context.getContentResolver().notifyChange(uri,null);
               return ContentUris.withAppendedId(uri,id_recipe);
           case INGREDIENTS:
               final long id_ingredient  = AppDatabase.getInstance(context).ingredientDao().
                       insert(Ingredient.fromContentValues(contentValues));
               context.getContentResolver().notifyChange(uri,null);
               return ContentUris.withAppendedId(uri,id_ingredient);
           case STEPS:
               final long id_step = AppDatabase.getInstance(context).stepDao().
                       insert(Step.fromContentValues(contentValues));
               context.getContentResolver().notifyChange(uri,null);
               return ContentUris.withAppendedId(uri,id_step);
           default:
               throw new IllegalArgumentException("Unknown URI: " + uri);

       }

    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        final int match = MATCHER.match(uri);
        final Context context = getContext();
        if (context == null) {
            return 0;
        }
        switch (match){
            case RECIPE:
                final int count = AppDatabase.getInstance(context).recipeDao()
                        .deleteById(ContentUris.parseId(uri));
                context.getContentResolver().notifyChange(uri, null);
                return count;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);

        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
