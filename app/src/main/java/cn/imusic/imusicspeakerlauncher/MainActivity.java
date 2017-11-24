package cn.imusic.imusicspeakerlauncher;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class MainActivity extends Activity {
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CustomBroadcastReceiver.isFinishBoot = false;
        setContentView(R.layout.activity_main);

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                ComponentName cn = new ComponentName("com.imusic.dx", "com.imusic.dx.ui.activity.WelcomeActivity");
                intent.setComponent(cn);
                startActivity(intent);
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
//        Toast.makeText(this, "isFinishBoot为"+CustomBroadcastReceiver.isFinishBoot, Toast.LENGTH_LONG).show();
        if(!CustomBroadcastReceiver.isFinishBoot)
        handler.sendEmptyMessageDelayed(0, 7000);
        else{
            startActivity(new Intent(this, UpdateActivity.class));
            finish();
        }
    }
}
