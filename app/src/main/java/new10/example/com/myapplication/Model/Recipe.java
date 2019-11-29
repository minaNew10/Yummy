package new10.example.com.myapplication.Model;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

import new10.example.com.myapplication.Database.RecipeProvider;

@Entity(tableName = Recipe.TABLE_NAME)
public class Recipe implements Parcelable {
    /**The name of the Recipe Table*/
    public static final String TABLE_NAME = "recipe";

    public static final String CONTENT_LIST_TYPE =
            ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + RecipeProvider.AUTHORITY + "/" + TABLE_NAME;
public static final String CONTENT_ITEM_TYPE =
            ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + RecipeProvider.AUTHORITY + "/" + TABLE_NAME;


    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_SERVINGS = "servings";
    public static final String COLUMN_IMAGE = "image";
    @PrimaryKey
    private int id;
    private String name;
    private int servings;
    private String image;
    @Ignore
    private List<Ingredient> ingredients;
    @Ignore
    private List<Step> steps;


    @Ignore
    public Recipe() {
    }

    @Ignore
    public Recipe(int id, String name, List<Ingredient> ingredients, List<Step> steps, int servings, String image) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.servings = servings;
        this.image = image;
    }

    public Recipe(int id, String name, int servings, String image) {
        this.id = id;
        this.name = name;
        this.servings = servings;
        this.image = image;
    }

    protected Recipe(Parcel in){
        id = in.readInt();
        name = in.readString();
        steps = new ArrayList<>();
        in.readList(steps,Step.class.getClassLoader());
        ingredients = new ArrayList<>();
        in.readList(ingredients,Ingredient.class.getClassLoader());
        servings = in.readInt();
        image = in.readString();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public int getServings() {
        return servings;
    }

    public String getImage() {
        return image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeList(steps);
        parcel.writeList(ingredients);
        parcel.writeInt(servings);
        parcel.writeString(image);
    }

    /**
     * Create a new {@link Recipe} from the specified {@link ContentValues}.
     *
     * @param values A {@link ContentValues} that at least contain {@link #name}.
     * @return A newly created {@link Recipe} instance.
     *
     */
     public static Recipe fromContentValues(ContentValues values){
         final Recipe recipe = new Recipe();
         if (values.containsKey(COLUMN_ID)){
             recipe.id = values.getAsInteger(COLUMN_ID);
         }
         if(values.containsKey(COLUMN_NAME)){
             recipe.name = values.getAsString(COLUMN_NAME);
         }
         if(values.containsKey(COLUMN_IMAGE)){
             recipe.image = values.getAsString(COLUMN_IMAGE);
         }
         if(values.containsKey(COLUMN_SERVINGS)){
             recipe.servings = values.getAsInteger(COLUMN_SERVINGS);
         }
         return recipe;
     }
}
