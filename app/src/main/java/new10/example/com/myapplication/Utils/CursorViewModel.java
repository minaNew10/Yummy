package new10.example.com.myapplication.Utils;

import android.app.Application;
import android.database.Cursor;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public abstract class CursorViewModel<D extends CursorLiveData> extends AndroidViewModel {
    private static final String TAG = "CursorViewModel";

    @NonNull
    protected final D mCursorLiveData;

    public CursorViewModel(@NonNull Application application) {
        super(application);
        mCursorLiveData = createCursorLiveData(application);
    }

    @NonNull
    protected abstract D createCursorLiveData(@NonNull Application application);

    @Override
    protected void onCleared() {
        Cursor cursor = mCursorLiveData.getValue();
        if (cursor != null) {
            Log.d(TAG, "onCleared() cursor.close()");
            cursor.close();
        }
    }
}
