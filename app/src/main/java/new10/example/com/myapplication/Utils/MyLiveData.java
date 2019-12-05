package new10.example.com.myapplication.Utils;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import new10.example.com.myapplication.Model.Recipe;

public abstract class MyLiveData<T> extends MutableLiveData<T> {
    Uri uri;
    Context context;
    ContentObserver observer;

    public MyLiveData(Uri uri, Context context) {
        getContentProviderValue();
        this.uri = uri;
        this.context = context;
    }

    @Override
    protected void onActive() {
        super.onActive();
        observer = new ContentObserver(null) {
            @Override
            public void onChange(boolean selfChange) {
                super.onChange(selfChange);
                postValue(getContentProviderValue());
            }
        };
        context.getContentResolver().registerContentObserver(uri,true,observer);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        context.getContentResolver().unregisterContentObserver(observer);
    }

    public abstract T getContentProviderValue();
}
