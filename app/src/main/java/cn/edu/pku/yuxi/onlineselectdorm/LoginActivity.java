package cn.edu.pku.yuxi.onlineselectdorm;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity {
    private EditText mUser;
    private EditText mPassword;

    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.login);

        mUser = (EditText)findViewById(R.id.login_user_edit);
        mPassword = (EditText)findViewById(R.id.login_pwd_edit);
       //sendRequestWithOkHttp();

    }

    public void login_main(View v){
        if("yuxi".equals(mUser.getText().toString())&&"123".equals(mPassword.getText().toString()))
        {
            Intent intent = new Intent();
            intent.setClass(LoginActivity.this,MainActivity.class);
            startActivity(intent);
        }
        else if ("".equals(mUser.getText().toString()) || "".equals(mPassword.getText().toString()))
        {
            new AlertDialog.Builder(LoginActivity.this)
                    //.setIcon(getResources().getDrawable(R.drawable.login_error_icon))
                    .setTitle("登录错误")
                    .create().show();
        }else {
            new AlertDialog.Builder(LoginActivity.this)
                    .setTitle("failed")
                    .create().show();
        }
        Intent intent = new Intent();
            intent.setClass(LoginActivity.this,MainActivity.class);
            startActivity(intent);
        Toast.makeText(getApplicationContext(),"",Toast.LENGTH_SHORT).show();
        this.finish();
    }
    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("https://api.mysspku.com/index.php/V1/MobileCourse/Login")
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.d("tag",responseData);

                }catch (IOException ex){
                    ex.printStackTrace();
                }
            }
        }).start();
    }
    public void login_back(View v){
        this.finish();
    }
    public void login_pw(View v){

    }

}