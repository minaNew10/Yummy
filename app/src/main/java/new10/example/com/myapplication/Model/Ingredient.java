package new10.example.com.myapplication.Model;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import new10.example.com.myapplication.Database.RecipeProvider;

import static androidx.room.ForeignKey.CASCADE;


@Entity(tableName = Ingredient.TABLE_NAME,foreignKeys = @ForeignKey(entity = Recipe.class,parentColumns = "id",childColumns = "recipe_id",onDelete = CASCADE))
public class Ingredient implements Parcelable {

    public static final String TABLE_NAME = "ingredient";
    public static final String CONTENT_LIST_TYPE =
            ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + RecipeProvider.AUTHORITY + "/" + TABLE_NAME;
    public static final String COLUMN_ID= "id";
    public static final String COLUMN_QUANTITY = "quantity";
    public static final String COLUMN_MEASURE = "measure";
    public static final String COLUMN_NAME = "ingredient";
    public static final String COLUMN_RECIPE_ID  = "recipe_id";
    @PrimaryKey(autoGenerate = true)
    public int id;
    private float quantity;
    private String measure;
    // at database is "ingredient"
    private String ingredient;
    public int recipe_id;
    @Ignore
    public Ingredient() {
    }

    public Ingredient(float quantity, String measure, String ingredient) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }

    public void setRecipe_id(int recipe_id) {
        this.recipe_id = recipe_id;
    }

    public float getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    @Override
    public int describeContents() {
        return 0;
    }
    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    protected Ingredient(Parcel in){
        quantity = in.readFloat();
        measure = in.readString();
        ingredient = in.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeFloat(quantity);
        parcel.writeString(measure);
        parcel.writeString(ingredient);
    }

    public static Ingredient fromContentValues(ContentValues values){
        final Ingredient ingredient = new Ingredient();
        if(values.containsKey(COLUMN_NAME)){
            ingredient.ingredient = values.getAsString(COLUMN_NAME);
        }
        if(values.containsKey(COLUMN_QUANTITY)){
            ingredient.quantity = values.getAsInteger(COLUMN_QUANTITY);
        }
        if(values.containsKey(COLUMN_MEASURE)){
            ingredient.measure = values.getAsString(COLUMN_MEASURE);
        }
        if(values.containsKey(COLUMN_RECIPE_ID)){
            ingredient.recipe_id = values.getAsInteger(COLUMN_RECIPE_ID);
        }


        return ingredient;
    }
}
