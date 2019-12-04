package new10.example.com.myapplication.ViewModel.MainActivityViewModels;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import new10.example.com.myapplication.Utils.CursorLiveData;
import new10.example.com.myapplication.Utils.CursorViewModel;

public class FavCursorViewModel  extends CursorViewModel {
    Uri uri;
    public FavCursorViewModel(Uri uri,@NonNull Application application) {
        super(application);
        this.uri = uri;
    }

    @NonNull
    @Override
    protected CursorLiveData createCursorLiveData(@NonNull Application application) {
        CursorLiveData cursorLiveData = new CursorLiveData(application) {
            @Nullable
            @Override
            public String[] getCursorProjection() {
                return new String[0];
            }

            @Nullable
            @Override
            public String getCursorSelection() {
                return null;
            }

            @Nullable
            @Override
            public String[] getCursorSelectionArgs() {
                return new String[0];
            }

            @Nullable
            @Override
            public String getCursorSortOrder() {
                return null;
            }

            @NonNull
            @Override
            public Uri getCursorUri() {
                return uri;
            }
        };
        return cursorLiveData;
    }
}
