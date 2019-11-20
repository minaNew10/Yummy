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

@Entity(tableName = Step.TABLE_NAME,foreignKeys = @ForeignKey(entity = Recipe.class,parentColumns = "id", childColumns = "recipe_id",onDelete = CASCADE))
public class Step implements Parcelable {

    public static final String TABLE_NAME = "step";
    public static final String COLUMN_SHORT_DESCRIPTION ="shortDescription";
    public static final String COLUMN_DESCRIPTION ="description";
    public static final String COLUMN_VIDEO_URL ="videoURL";
    public static final String COLUMN_THUMBNAIL_URL = "thumbnailURL";
    public static final String COLUMN_RECIPE_ID  = "recipe_id";
    public static final String CONTENT_LIST_TYPE =
            ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + RecipeProvider.AUTHORITY + "/" + TABLE_NAME;

    @PrimaryKey(autoGenerate = true)
    public int id;
    private String shortDescription;
    private String description;
    private String videoURL;
    private String thumbnailURL;
    public int recipe_id;

    @Ignore
    public Step() {
    }

    public Step(int id, String shortDescription, String description, String videoURL, String thumbnailURL, int recipe_id) {
        this.id = id;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoURL;
        this.thumbnailURL = thumbnailURL;
        this.recipe_id = recipe_id;
    }

    @Ignore
    public Step(int id, String shortDescription, String description, String videoURL, String thumbnailURL) {
        this.id = id;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoURL;
        this.thumbnailURL = thumbnailURL;
    }
    protected Step(Parcel in){
//        id = in.readInt();
        shortDescription = in.readString();
        description = in.readString();
        videoURL= in.readString();
        thumbnailURL = in.readString();
    }
    @Override
    public void writeToParcel(Parcel parcel, int i) {
//        parcel.writeInt(id);
        parcel.writeString(shortDescription);
        parcel.writeString(description);
        parcel.writeString(videoURL);
        parcel.writeString(thumbnailURL);
    }

    public static final Creator<Step> CREATOR = new Creator<Step>() {
        @Override
        public Step createFromParcel(Parcel in) {
            return new Step(in);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public int getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(int recipe_id) {
        this.recipe_id = recipe_id;
    }

    public static Step fromContentValues(ContentValues values){
        final Step step = new Step();
        if(values.containsKey(COLUMN_SHORT_DESCRIPTION)){
            step.shortDescription = values.getAsString(COLUMN_SHORT_DESCRIPTION);
        }
        if(values.containsKey(COLUMN_DESCRIPTION)){
            step.description = values.getAsString(COLUMN_DESCRIPTION);
        }
        if(values.containsKey(COLUMN_THUMBNAIL_URL)){
            step.thumbnailURL = values.getAsString(COLUMN_THUMBNAIL_URL);
        }
        if(values.containsKey(COLUMN_VIDEO_URL)){
            step.videoURL = values.getAsString(COLUMN_VIDEO_URL);
        }
        if(values.containsKey(COLUMN_RECIPE_ID)){
            step.recipe_id = values.getAsInteger(COLUMN_RECIPE_ID);
        }

        return step;
    }
    @Override
    public int describeContents() {
        return 0;
    }
}
