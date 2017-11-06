package com.direct.materialtest;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.direct.materialtest.base.BaseActivity;

import java.util.Timer;
import java.util.TimerTask;

public class HelloActivity extends BaseActivity {
    private int index=0;
    private Timer timer;
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_hello);
        iv = (ImageView)this.findViewById(R.id.iv);
        timer = new Timer(true);
        timer.schedule(hello, 200, 150); //延迟200毫秒执行，每150毫秒执行一次
    }

    TimerTask hello = new TimerTask() {
        @Override
        public void run() {
            if (index < 5) {
                index++;
                Message msg = new Message();
                msg.what = index;
                handler.sendMessage(msg);
            } else if (index < 8) {
                index++;  //静止片刻
            } else {
                //停止播放，启动主Activity
                timer.cancel();
                Intent intent = new Intent(HelloActivity.this, MainActivity.class);
                startActivity(intent);
                finish();  //不加这一句，按回退键就会回到欢迎界面不合理。
            }
        }
    };

    final Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            //循环播放图片
            iv.setBackgroundResource(R.drawable.firstimg);
        }
    };
}
