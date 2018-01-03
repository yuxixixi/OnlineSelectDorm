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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import cn.edu.pku.yuxi.util.SslUtils;

import static cn.edu.pku.yuxi.onlineselectdorm.R.id.building;

/**
 * Created by yuxi on 17/12/25.
 */

public class ChooseActivity extends Activity implements View.OnClickListener{
    private ImageView backForward,nextBut2;

    private EditText xueHao1,xueHao2,xueHao3,xueHao4,yanZhengma1,yanZhengma2,yanZhengma3,yanZhengma4,buildingNo;
    private int classMateNumbe=0;
    private HttpURLConnection conn;
    private static final int SUCCESS =1;
    private int errcode=1;
    private String building;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_layout);

        Button backForward=(Button)findViewById(R.id.backforward1);
        backForward.setOnClickListener(this);

        Button nextBut2=(Button)findViewById(R.id.ok_btn);
        nextBut2.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.backforward1){
            //清空同学储存信息
            SharedPreferences.Editor editor = getSharedPreferences("config",MODE_PRIVATE).edit();
            editor.putString("xueHao1","");
            editor.putString("xueHao2","");
            editor.putString("xueHao3","");
            editor.putString("xueHao4","");
            editor.putString("yanZhengma1","");
            editor.putString("yanZhengma2","");
            editor.putString("yanZhengma3","");
            editor.putString("yanZhengma4","");
            editor.putString("building","");
            classMateNumbe=0;
            editor.putInt("classMateNumbe",classMateNumbe);
            editor.commit();

            //跳转到页
            Intent intent = new Intent(ChooseActivity.this, MainActivity.class);
            startActivity(intent);
            //关闭当前界面
            finish();
        }
        if(v.getId()==R.id.ok_btn){
            xueHao1=(EditText)findViewById(R.id.classmxuehao1);
            xueHao2=(EditText)findViewById(R.id.classmxuehao2);
            xueHao3=(EditText)findViewById(R.id.classmxuehao3);
            yanZhengma1=(EditText)findViewById(R.id.classmyanzheng1);
            yanZhengma2=(EditText)findViewById(R.id.classmyanzheng2);
            yanZhengma3=(EditText)findViewById(R.id.classmyanzheng3);
            buildingNo=(EditText)findViewById(R.id.building_num);
            SharedPreferences.Editor editor = getSharedPreferences("config",MODE_PRIVATE).edit();
            building = buildingNo.getText().toString();
            editor.putInt("building",Integer.parseInt(building));
            // 存储同学信息
            if(xueHao1.getText().toString().length() != 0){
//                SharedPreferences.Editor editor = getSharedPreferences("config",MODE_PRIVATE).edit();
                editor.putString("xueHao1",xueHao1.getText().toString());
                Log.d("myapp","同学1的学号"+xueHao1.getText().toString());
                classMateNumbe++;
                editor.putInt("classMateNumbe",classMateNumbe);
                editor.commit();
            }
            if(yanZhengma1.getText().toString().length() != 0){
//                SharedPreferences.Editor editor = getSharedPreferences("config",MODE_PRIVATE).edit();
                editor.putString("yanZhengma1",yanZhengma1.getText().toString());
                Log.d("myapp","同学1的验证码"+yanZhengma1.getText().toString());
                editor.commit();
            }

            if(xueHao2.getText().toString().length() != 0){
//                SharedPreferences.Editor editor = getSharedPreferences("config",MODE_PRIVATE).edit();
                editor.putString("xueHao2",xueHao2.getText().toString());
                Log.d("myapp","同学2的学号"+xueHao2.getText().toString());
                classMateNumbe++;
                editor.putInt("classMateNumbe",classMateNumbe);
                editor.commit();
            }
            if(yanZhengma2.getText().toString().length() != 0){
//                SharedPreferences.Editor editor = getSharedPreferences("config",MODE_PRIVATE).edit();
                editor.putString("yanZhengma2",yanZhengma2.getText().toString());
                Log.d("myapp","同学2的验证码"+yanZhengma2.getText().toString());
                editor.commit();
            }
            if(xueHao3.getText().toString().length() != 0){
//                SharedPreferences.Editor editor = getSharedPreferences("config",MODE_PRIVATE).edit();
                editor.putString("xueHao3",xueHao3.getText().toString());
                Log.d("myapp","同学3的学号"+xueHao3.getText().toString());
                classMateNumbe++;
                editor.putInt("classMateNumbe",classMateNumbe);
                editor.commit();
            }
            if(yanZhengma3.getText().toString().length() != 0){
//                SharedPreferences.Editor editor = getSharedPreferences("config",MODE_PRIVATE).edit();
                editor.putString("yanZhengma3",yanZhengma3.getText().toString());
                Log.d("myapp","同学3的验证码"+yanZhengma3.getText().toString());
                editor.commit();
            }

            Log.d("myapp","同选学生人数"+classMateNumbe);
            //创建map类型
            Map<String, Object> map = new HashMap<String, Object>();
            //获取总人数信息
            SharedPreferences sharedPreferences=getSharedPreferences("config",MODE_PRIVATE);
            int classMateNumbe=sharedPreferences.getInt("classMateNumbe",0);
            int totalNM=classMateNumbe+1;
            int i=classMateNumbe;

            Log.d("myapp","办理人总数"+totalNM);
            map.put("num", totalNM);
            //获取办理人
            String usercode=sharedPreferences.getString("usercode","");
            Log.d("myapp","办理人id"+usercode);
            map.put("stuid", usercode);
            if(i>0){
                String xueHao1=sharedPreferences.getString("xueHao1","");
                map.put("stu1id", xueHao1);
                String yanZhengma1=sharedPreferences.getString("yanZhengma1","");
                map.put("v1code", yanZhengma1);
                i--;
            }
            if(i>0){
                String xueHao2=sharedPreferences.getString("xueHao2","");
                map.put("stu2id", xueHao2);
                String yanZhengma2=sharedPreferences.getString("yanZhengma2","");
                map.put("v2code", yanZhengma2);
                i--;
            }
            if(i>0){
                String xueHao3=sharedPreferences.getString("xueHao3","");
                map.put("stu3id", xueHao3);
                String yanZhengma3=sharedPreferences.getString("yanZhengma2","");
                map.put("v3code", yanZhengma3);
                i--;
            }
            if(i>0){
                String xueHao4=sharedPreferences.getString("xueHao4","");
                map.put("stu4id", xueHao4);
                String yanZhengma4=sharedPreferences.getString("yanZhengma4","");
                map.put("v4code", yanZhengma4);
                i--;
            }
            int DormitoryNum=sharedPreferences.getInt("building",0);
            map.put("buildingNo",DormitoryNum );
            Log.d("myapp","map里的值"+map);

            //连接网络
            StringBuffer sbRequest =new StringBuffer();
            if(map!=null&&map.size()>0){
                for (String key:map.keySet()){
                    sbRequest.append(key+"="+map.get(key)+"&");
                }
            }
            final String request = sbRequest.substring(0,sbRequest.length()-1);
            final String ip3="https://api.mysspku.com/index.php/V1/MobileCourse/SelectRoom";
            new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            try {
                                URL url = new URL(ip3);
                                if("https".equalsIgnoreCase(url.getProtocol())){
                                    SslUtils.ignoreSsl();
                                }
                                conn=(HttpURLConnection)url.openConnection();
                                conn.setRequestMethod("POST");
                                //通过正文发送数据
                                OutputStream os =conn.getOutputStream();
                                os.write(request.getBytes());
                                os.flush();

                                InputStream in = conn.getInputStream();
                                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                                StringBuilder response = new StringBuilder();
                                String str;
                                while((str=reader.readLine()) != null){
                                    response.append(str);
                                    Log.d("myapp", str);
                                }
                                String responseStr=response.toString();
                                Log.d("myapp", "选宿舍结果"+responseStr);

                                //将结果传给主线程
                                Message msg = new Message();
                                msg.what=SUCCESS;
                                msg.obj=responseStr;
                                mHandler.sendMessage(msg);
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }
            ).start();

        }


    }
    private Handler mHandler =new Handler(){
        public void handleMessage(android.os.Message msg){
            switch (msg.what){
                case SUCCESS:
                    String responseStr=(String)msg.obj;
                    Log.d("myapp","查询结果"+responseStr);
                    try {
                        //          创建JSON解析对象(两条规则的体现:大括号用JSONObject,注意传入数据对象)
                        JSONObject obj = new JSONObject(responseStr);
                        errcode = obj.getInt("errcode");
                        if(errcode==0){
                            Toast.makeText(ChooseActivity.this, "选宿舍成功", Toast.LENGTH_LONG).show();
                            //跳转到页
                            Intent intent = new Intent(ChooseActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(ChooseActivity.this, "选宿舍失败，请重新选宿舍", Toast.LENGTH_LONG).show();
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            }
        }
    };
}
