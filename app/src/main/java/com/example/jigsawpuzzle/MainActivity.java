package com.example.jigsawpuzzle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
    //    每行的图片个数
    private int imageX = 3;
    private int imageY = 3;  //每列的图片的个数

    //    图片的总数目
    private int imgCount = imageX*imageY;
    //    空白区域的位置
    private int blankSwap = imgCount-1;
    //    初始化空白区域的按钮id
    private int blankImgid = R.id.pt_23;
    int timer = 0;//计时器
    //存放切割图片的数组
    private int[]image = {R.mipmap.image_01,R.mipmap.image_02,R.mipmap.image_03,
            R.mipmap.image_11,R.mipmap.image_12,R.mipmap.image_13,
            R.mipmap.image_21,R.mipmap.image_22,R.mipmap.image_23};

    //  声明一个图片数组的下标数组，随机排列这个数组
    private int[]imageIndex = new int[image.length];

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
        disruptRandom();//打乱
        handler.sendEmptyMessageDelayed(1,1000);
    }
    //数组元素的打乱
    private void disruptRandom() {
        for (int i = 0; i < imageIndex.length; i++) {
            imageIndex[i] = i;
        }
        //数组从0到9依次排列
//        规定15次，随机选择两个角标对应的值进行交换
        int rand1,rand2;//选择角标值
        for (int j = 0; j < 15; j++) {
//            随机生成第一个角标
            rand1 = (int)(Math.random()*(imageIndex.length-1));
//            第二次随机生成的角标，不能和第一次随机生成的角标相同，如果相同，就不方便交换了
            do {
                rand2 = (int)(Math.random()*(imageIndex.length-1));
                if (rand1!=rand2) {
                    break;
                }
            }while (true);
//            交换两个角标上对应的值
            swap(rand1,rand2);
        }
//        随机排列到指定的控件上
        pt01.setImageResource(image[imageIndex[0]]);
        pt02.setImageResource(image[imageIndex[1]]);
        pt03.setImageResource(image[imageIndex[2]]);
        pt11.setImageResource(image[imageIndex[3]]);
        pt12.setImageResource(image[imageIndex[4]]);
        pt13.setImageResource(image[imageIndex[5]]);
        pt21.setImageResource(image[imageIndex[6]]);
        pt22.setImageResource(image[imageIndex[7]]);
        pt23.setImageResource(image[imageIndex[8]]);

    }
    //  交换数组指定角标上的数据
    private void swap(int rand1, int rand2) {
        int temp = imageIndex[rand1];
        imageIndex[rand1] = imageIndex[rand2];
        imageIndex[rand2] = temp;
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
    }//数据初始化

    public void onClick(View view) {

    }



    //重新开始的点击事件，打乱拼图并且时间归零
    public void reStart(View view) {
    }
}

