package new10.example.com.myapplication.Utils;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;


/**
 * Abstract [LiveData] to observe Android's Content Provider changes.
 * Provide a [uri] to observe changes and implement [getContentProviderValue]
 * to provide data to post when content provider notifies a change.
 */
public abstract class MyLiveData<T> extends MutableLiveData<T> {
    Uri uri;
    Context context;
    ContentObserver observer;

    public MyLiveData(Uri uri, Context context) {

        this.uri = uri;
        this.context = context;
    }

    /**
     * Implement if you need to provide [T] value to be posted
     * when observed content is changed.
     */
    public abstract T getContentProviderValue();

    @Override
    protected void onActive() {
        super.onActive();
        observer = new ContentObserver(null) {
            @Override
            public void onChange(boolean selfChange, Uri uri) {
                super.onChange(selfChange, uri);
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
}

//
//class ContactsLiveData(
//        context: Context
//        ) : ContentProviderLiveData<List<String>>(context, uri) {
//
//        override fun getContentProviderValue(): List<String> {
//        // TODO: query your repository and generate the list of contact's name for example
//        return emptyList()
//        }