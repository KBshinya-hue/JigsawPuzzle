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
        int id = view.getId();
//        九个按钮执行的点击事件的逻辑应该是相同的，如果有空格在周围，可以改变图片显示的位置，否则点击事件不响应
        switch (id) {
            case R.id.pt_01:
                move(R.id.pt_01,0);
                break;
            case R.id.pt_02:
                move(R.id.pt_02,1);
                break;
            case R.id.pt_03:
                move(R.id.pt_03,2);
                break;
            case R.id.pt_11:
                move(R.id.pt_11,3);
                break;
            case R.id.pt_12:
                move(R.id.pt_12,4);
                break;
            case R.id.pt_13:
                move(R.id.pt_13,5);
                break;
            case R.id.pt_21:
                move(R.id.pt_21,6);
                break;
            case R.id.pt_22:
                move(R.id.pt_22,7);
                break;
            case R.id.pt_23:
                move(R.id.pt_23,8);
                break;
        }
    }
    /*表示移动指定位置的按钮的函数，将图片和空白区域进行交换*/
    private void move(int imagebuttonId, int site) {
//            判断选中的图片在第几行
        int sitex = site / imageX;
        int sitey = site % imageY; //第几列
//        获取空白区域的坐标
        int blankx = blankSwap / imageX;
        int blanky = blankSwap % imageY;
//        可以移动的条件有两个
//        1.在同一行，列数相减，绝对值为1，可移动   2.在同一列，行数相减，绝对值为1，可以移动
        int x = Math.abs(sitex-blankx);
        int y = Math.abs(sitey-blanky);
        if ((x==0&&y==1)||(y==0&&x==1)){
//            通过id，查找到这个可以移动的按钮
            ImageButton clickButton = findViewById(imagebuttonId);
            clickButton.setVisibility(View.INVISIBLE);
//            查找到空白区域的按钮
            ImageButton blankButton = findViewById(blankImgid);
//            将空白区域的按钮设置图片
            blankButton.setImageResource(image[imageIndex[site]]);
//            移动之前是不可见的，移动之后，将控件设置为可见
            blankButton.setVisibility(View.VISIBLE);
//            将改变角标的过程记录到存储图片位置数组当中
            swap(site,blankSwap);
//            新的空白区域位置更新等于传入的点击按钮的位置
            blankSwap = site;
            blankImgid = imagebuttonId;
        }
//      判断本次移动完成后，是否完成了拼图游戏

    }



    //重新开始的点击事件，打乱拼图并且时间归零
    public void reStart(View view) {
    }
}

