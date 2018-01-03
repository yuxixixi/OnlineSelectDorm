package cn.edu.pku.yuxi.onlineselectdorm;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import cn.edu.pku.yuxi.util.SslUtils;

/**
 * Created by yuxi on 17/11/29.
 */

public class MainActivity extends Activity implements View.OnClickListener {
    private TextView studentid,name,gender,vcode,location,grade,building,room;
    private HttpURLConnection conn;
    private static final int RESEARCH =1;
    private int errcode=1;
    private String data;
    private Button bedsBtn;
    private Button infoBtn;
    private Button chooseBtn;
    @Override
    protected void onCreate(@Nullable  Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        //查询按钮
        Button bedsBtn=(Button) findViewById(R.id.search_bed);
        bedsBtn.setOnClickListener(this);
        Button infoBtn=(Button) findViewById(R.id.search_info);
        infoBtn.setOnClickListener(this);
        Button chooseBtn=(Button) findViewById(R.id.choose_room);
        chooseBtn.setOnClickListener(this);
        //退出按钮
        Button logOut=(Button) findViewById(R.id.log_out);
        logOut.setOnClickListener(this);
    }
    private void initView(){


        //获取登陆人学号
        SharedPreferences sharedPreferences=getSharedPreferences("config",MODE_PRIVATE);
        String usercode=sharedPreferences.getString("usercode","");
        Log.d("学号","学号"+usercode);
        Log.d("myapp","登陆标志"+sharedPreferences.getInt("logInFlag", 1));

        final String ip1 ="https://api.mysspku.com/index.php/V1/MobileCourse/getDetail?stuid="+usercode;

        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            URL url=new URL(ip1);
                            if("https".equalsIgnoreCase(url.getProtocol())){
                                SslUtils.ignoreSsl();
                            }
                            conn=(HttpURLConnection)url.openConnection();
                            conn.setRequestMethod("GET");
                            InputStream in = conn.getInputStream();
                            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                            StringBuilder response = new StringBuilder();
                            String str;
                            while((str=reader.readLine()) != null){
                                response.append(str);
                                Log.d("查询", str);
                            }
                            String responseStr=response.toString();
                            Log.d("查询", "查询成功"+responseStr);

                            //将结果传给主线程
                            Message msg = new Message();
                            msg.what=RESEARCH;
                            msg.obj=responseStr;
                            mHandler.sendMessage(msg);

                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }
        ).start();
    }
    private Handler mHandler =new Handler(){
        public void handleMessage(android.os.Message msg){
            switch (msg.what){
                case RESEARCH:
                    String responseStr=(String)msg.obj;
                    Log.d("myapp","查询结果"+responseStr);
                    try {
                        //          创建JSON解析对象(两条规则的体现:大括号用JSONObject,注意传入数据对象)
                        JSONObject obj = new JSONObject(responseStr);
                        errcode = obj.getInt("errcode");
                        data = obj.getString("data");

                        //第二层解析
                        JSONObject obj1 = new JSONObject(data);

                        //更新查询信息
                        studentid=(TextView)findViewById(R.id.studentid);
                        name=(TextView)findViewById(R.id.name);
                        gender=(TextView)findViewById(R.id.gender);
                        vcode=(TextView)findViewById(R.id.vcode);
                        location=(TextView)findViewById(R.id.location);
                        grade=(TextView)findViewById(R.id.grade);
                        building=(TextView)findViewById(R.id.building);
                        room=(TextView)findViewById(R.id.room);

                        Log.d("myapp","解析结果"+obj1.getString("studentid"));

                        studentid.setText("学号："+obj1.getString("studentid"));
                        name.setText("姓名："+obj1.getString("name"));
                        gender.setText("性别："+obj1.getString("gender"));
                        vcode.setText("验证码："+obj1.getString("vcode"));
                        location.setText("校区："+obj1.getString("location"));
                        grade.setText("年级："+obj1.getString("grade"));
                        int i = Integer.parseInt(obj1.getString("studentid"));
                        if(i%2==0){
                            building.setText("已选宿舍楼号："+obj1.getString("building"));
                            room.setText("宿舍号："+obj1.getString("room"));
                        }else{
                            building.setText("已选宿舍楼号：未选");
                            room.setText("宿舍号：未选");
                        }

                        // 存储解析结果
                        SharedPreferences.Editor editor = getSharedPreferences("config",MODE_PRIVATE).edit();
                        editor.putString("name", obj1.getString("name"));
                        editor.putString("gender", obj1.getString("gender"));
                        editor.putString("vcode", obj1.getString("vcode"));
                        editor.putString("location", obj1.getString("location"));
                        editor.putString("grade", obj1.getString("grade"));
                        editor.commit();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            }
        }
    };

    @Override
    public void onClick(View v) {
        if(v.getId()== R.id.log_out){
            // 清除存储信息
            SharedPreferences.Editor editor = getSharedPreferences("config",MODE_PRIVATE).edit();
            editor.putInt("logInFlag", 1);
            editor.putString("usercode", "");
            editor.putString("password",  "");
            editor.putString("name","");
            editor.putString("gender", "");
            editor.putString("vcode", "");
            editor.putString("location", "");
            editor.putString("grade","");
            editor.commit();

            //跳转到登陆页
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            //关闭当前界面
            finish();
        }

        if(v.getId()==R.id.search_bed){
            //跳转到页
            Intent intent = new Intent(MainActivity.this, BedsActivity.class);
            startActivity(intent);
        }
        if(v.getId()==R.id.choose_room){
            //跳转到页
            Intent intent = new Intent(MainActivity.this, ChooseActivity.class);
            startActivity(intent);
        }
        if(v.getId()==R.id.search_info){
            initView();
        }
    }
}
