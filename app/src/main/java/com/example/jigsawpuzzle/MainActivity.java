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
        judgeGameOver();
    }



    /* 判断拼图是否成功*/
    private void judgeGameOver() {
        boolean loop = true;   //定义标志位
        for (int i = 0; i < imageIndex.length; i++) {
            if (imageIndex[i]!=i) {
                loop = false;
                break;
            }
        }
        if (loop) {
//            拼图成功了
//            停止计时
            handler.removeMessages(1);
//            拼图成功后，禁止玩家继续移动按钮
            pt01.setClickable(false);
            pt02.setClickable(false);
            pt03.setClickable(false);
            pt11.setClickable(false);
            pt12.setClickable(false);
            pt13.setClickable(false);
            pt21.setClickable(false);
            pt22.setClickable(false);
            pt23.setClickable(false);
            pt23.setImageResource(image[8]);
            pt23.setVisibility(View.VISIBLE);
//            弹出提示用户成功的对话框
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("恭喜，拼图成功！您用的时间为"+time+"秒")
                    .setPositiveButton("确认",null);
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    /* 重新开始按钮的点击事件*/
    public void restart(View view) {
//        将状态还原
        restore();
//       将拼图重新打乱
        disruptRandom();
        handler.removeMessages(1);
//        将时间重新归0，并且重新开始计时
        timer = 0;
        time.setText("时间 : "+time+" 秒");
        handler.sendEmptyMessageDelayed(1,1000);
    }

    private void restore() {
        //      拼图游戏重新开始，允许完成移动碎片按钮
        pt01.setClickable(true);
        pt02.setClickable(true);
        pt03.setClickable(true);
        pt11.setClickable(true);
        pt12.setClickable(true);
        pt13.setClickable(true);
        pt21.setClickable(true);
        pt22.setClickable(true);
        pt23.setClickable(true);
//        还原被点击的图片按钮变成初始化的模样
        ImageButton clickBtn = findViewById(blankImgid);
        clickBtn.setVisibility(View.VISIBLE);
//        默认隐藏第九章图片
        ImageButton blankBtn = findViewById(R.id.pt_23);
        blankBtn.setVisibility(View.INVISIBLE);
        blankImgid = R.id.pt_23;   //初始化空白区域的按钮id
        blankSwap = imgCount - 1;
    }
    public void suspend(View view){
        handler.removeMessages(1);
        pt01.setClickable(false);
        pt02.setClickable(false);
        pt03.setClickable(false);
        pt11.setClickable(false);
        pt12.setClickable(false);
        pt13.setClickable(false);
        pt21.setClickable(false);
        pt22.setClickable(false);
        pt23.setClickable(false);
    }

    public void Continue(View view) {
        handler.sendEmptyMessageDelayed(1,1000);
        pt01.setClickable(true);
        pt02.setClickable(true);
        pt03.setClickable(true);
        pt11.setClickable(true);
        pt12.setClickable(true);
        pt13.setClickable(true);
        pt21.setClickable(true);
        pt22.setClickable(true);
        pt23.setClickable(true);
    }
}

