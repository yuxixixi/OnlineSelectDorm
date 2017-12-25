package cn.edu.pku.yuxi.onlineselectdorm;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import cn.edu.pku.yuxi.util.HttpUtil;

/**
 * Created by yuxi on 17/11/29.
 */

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//        instance = this;
//        mTabPager=(ViewPager)findViewById(R.id.tabpager);
//        mTabPager.setOnPageChangeListener(new MyOnPageChangeListenr());
//        //tab
//        mTab1 = (ImageView)findViewById(R.id.);
//        mTab1 = (ImageView)findViewById(R.id.);
//        mTab1 = (ImageView)findViewById(R.id.);
//        mTab1 = (ImageView)findViewById(R.id.);
//
//        m.Tab1.setOnClickListener(new MyOnClickListener(0));
//        m.Tab2.setOnClickListener(new MyOnClickListener(1));
//        m.Tab3.setOnClickListener(new MyOnClickListener(2));
//        m.Tab4.setOnClickListener(new MyOnClickListener(3));
//
//        Display currDisplay = getWindowManager().getDefaultDisplay();//获取屏幕当前分辨率
//        int displayWidth = currDisplay.getWidth();
//        int displayHeight = currDisplay.getHeight();
//        one = displayWidth/4;
//        //将要分页显示的View装入数组中
//        LayoutInflater mLi = LayoutInflater.from(this);
        String address = "https://api.mysspku.com/index.php/V1/MobileCourse/getDetail";

        HttpUtil.sendHttpRequest(address, new HttpUtil.HttpCallbackListener() {
            @Override
            public void onFinish(String response) {

            }

            @Override
            public void onError(Exception e) {

            }
        });
    }
    public void choose(View v) {

    }
}
