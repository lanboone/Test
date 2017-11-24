package cn.imusic.imusicspeakerlauncher;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import java.io.File;

/**
 * Created by hasee on 2017/9/23.
 */

public class UpdateActivity extends Activity {
    Handler handler = new Handler(){
        public void handleMessage(Message msg) {
            Log.e("dxHelper", "handleMessage：安装完成了，即将启动app");
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            ComponentName cn = new ComponentName("com.imusic.dx", "com.imusic.dx.ui.activity.WelcomeActivity");
            intent.setComponent(cn);
            startActivity(intent);
//            MainActivity.this.finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        Log.e("dxHelper", "提示APP启动了，onCreate() isFromSpeaker："+CustomBroadcastReceiver.isFinishBoot);
        final String filePath = "/sdcard/iMusic.apk";
        File file = new File(filePath);
        if (CustomBroadcastReceiver.isFinishBoot && file.exists()){
            new Thread(){
                @Override
                public void run() {
//                    PackageUtils.installSilent(MainActivity.this, filePath, " -r ");
                    Log.e("dxHelper", "音响app新版安装结果："+nativedInstallSilent(filePath));
                    Log.e("dxHelper", "即使Activity被销毁了，子线程还在运行");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.e("dxHelper", "runOnUiThread 安装完成了，即将启动app");
                            Intent intent = new Intent(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_LAUNCHER);
                            ComponentName cn = new ComponentName("com.imusic.dx", "com.imusic.dx.ui.activity.WelcomeActivity");
                            intent.setComponent(cn);
                            startActivity(intent);
//                            MainActivity.this.finish();
                        }
                    });
                    handler.sendEmptyMessage(0);
//					handler.sendEmptyMessageDelayed(0, 30000);
                }
            }.start();
        }
        System.gc();
    }

    private ShellUtils.CommandResult nativedInstallSilent(String filePath){
        StringBuilder command = new StringBuilder().append("LD_LIBRARY_PATH=/vendor/lib*:/system/lib* pm install -r ")
                .append(filePath.replace(" ", "\\ "));
        ShellUtils.CommandResult commandResult = ShellUtils.execCommand(command.toString(), true, true);
        return commandResult;
    }
}
