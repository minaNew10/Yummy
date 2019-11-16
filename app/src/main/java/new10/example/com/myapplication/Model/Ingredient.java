package new10.example.com.myapplication.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

import static androidx.room.ForeignKey.CASCADE;


@Entity(tableName = "ingredient",foreignKeys = @ForeignKey(entity = Recipe.class,parentColumns = "id",childColumns = "recipe_id",onDelete = CASCADE))
public class Ingredient implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    public int id;
    private float quantity;
    private String measure;
    // at database is "ingredient"
    private String name;
    public int recipe_id;

    public Ingredient(float quantity, String measure, String name) {
        this.quantity = quantity;
        this.measure = measure;
        this.name = name;
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

    public String getName() {
        return name;
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
        name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeFloat(quantity);
        parcel.writeString(measure);
        parcel.writeString(name);
    }
}
