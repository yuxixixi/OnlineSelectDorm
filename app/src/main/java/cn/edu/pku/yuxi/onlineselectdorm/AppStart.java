package cn.edu.pku.yuxi.onlineselectdorm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * Created by yuxi on 17/11/29.
 */

public class AppStart extends Activity{
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_start);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Toast.makeText(getApplicationContext(),"孩子！好好背诵！",Toast.LENGTH_LONG).show();
        //overridePendingTransition(R.anim.hyperspace_in,R.anim.hyperspace_out);
//        new Handler().postDelayed(new Runnable(){
//            public void run(){
//                Intent intent = new Intent(AppStart.this,Welcome.class);
//                startActivity(intent);
//                AppStart.this.finish();
//            }
//        },1000);
    }
}
