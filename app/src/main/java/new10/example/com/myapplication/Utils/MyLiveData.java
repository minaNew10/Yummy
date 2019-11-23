package new10.example.com.myapplication.Utils;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;

import androidx.lifecycle.LiveData;

import java.util.List;

import new10.example.com.myapplication.Model.Recipe;

public class MyLiveData extends LiveData<List<Recipe>> {
    Uri uri;
    Context context;
    ContentObserver observer;

    public MyLiveData(List<Recipe> value, Uri uri, Context context) {
        super(value);
        this.uri = uri;
        this.context = context;
    }
}
