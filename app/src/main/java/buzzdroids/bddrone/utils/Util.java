package buzzdroids.bddrone.utils;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

/**
 * Created by Adam Piech on 2017-06-02.
 */

public class Util {

    public static final String TAG = "BuzzDroids";
    public static final String SERVER_URL = "http://52.42.148.142:9000";

    public static void exitToastMessage(Context context, String message, int duration, int delay) {
        Toast.makeText(context, message, duration).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                System.exit(-1);
            }
        }, delay);
    }

}