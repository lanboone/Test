package cn.imusic.imusicspeakerlauncher;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by hasee on 2017/9/23.
 */

public class CustomBroadcastReceiver extends BroadcastReceiver {
    /**
     * 该值用于标识开机后是否进入过音响app，进入过了则它的值会变为true，否则为false
     */
    public static boolean isFinishBoot = false;

    @Override
    public void onReceive(Context context, Intent intent) {
//        Toast.makeText(context, "收到主app的广播", Toast.LENGTH_LONG).show();
        isFinishBoot = true;
    }
}
