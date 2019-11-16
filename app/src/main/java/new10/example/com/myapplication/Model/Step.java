package new10.example.com.myapplication.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "step",foreignKeys = @ForeignKey(entity = Recipe.class,parentColumns = "id", childColumns = "recipe_id",onDelete = CASCADE))
public class Step implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    public int id;
    private String shortDescription;
    private String description;
    private String videoURL;
    private String thumbnailURL;
    public int recipe_id;

    public Step(int id,String shortDescription, String description, String videoURL, String thumbnailURL, int recipe_id) {
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

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    public void setRecipe_id(int recipe_id) {
        this.recipe_id = recipe_id;
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
