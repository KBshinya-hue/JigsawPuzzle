package com.example.jigsawpuzzle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ImageButton pt01,pt02,pt03 ,pt11,pt12,pt13,pt21,pt22,pt23;
    Button reStart;
    TextView time;

    int timer = 0;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            if(msg.what == 1){
                timer++;
                time.setText("Time :"+timer+"s");//一秒发送一次
                handler.sendEmptyMessageDelayed(1,1000);//再次发送

            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        handler.sendEmptyMessageDelayed(1,1000);
    }

    private void initView() {
        pt01 = findViewById(R.id.pt_01);
        pt02 = findViewById(R.id.pt_02);
        pt03 = findViewById(R.id.pt_03);
        pt11 = findViewById(R.id.pt_11);
        pt12 = findViewById(R.id.pt_12);
        pt13 = findViewById(R.id.pt_13);
        pt21 = findViewById(R.id.pt_21);
        pt22 = findViewById(R.id.pt_22);
        pt23 = findViewById(R.id.pt_23);
        time = findViewById(R.id.pt_time);
        reStart = findViewById(R.id.pt_restart);
    }

    public void onClick(View view) {

    }
//重新开始的点击事件，打乱拼图并且时间归零
    public void reStart(View view) {
        handler.removeMessages(1);//将发送的消息移除
        timer = 0;
        time.setText("Time :"+timer+"s");
        handler.sendEmptyMessageDelayed(1,1000);//重新发送
    }
}
