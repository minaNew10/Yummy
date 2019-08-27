package new10.example.com.myapplication.NetworkOperations;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class QueryUtils {
    public static final String BASE_URI_RECIPES = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";

    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
