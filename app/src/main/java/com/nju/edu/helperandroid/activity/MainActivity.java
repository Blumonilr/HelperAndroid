package com.nju.edu.helperandroid.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nju.edu.helperandroid.R;
import com.nju.edu.helperandroid.util.NetUtil;
import com.nju.edu.helperandroid.web.SeecoderHelperException;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText et_username;
    private EditText et_password;
    private Button bt_log;
    private Button bt_res;
    private NetUtil netUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        bt_log = findViewById(R.id.bt_log);
        bt_res = findViewById(R.id.bt_res);
        netUtil=new NetUtil("47.101.177.55","");

        bt_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = et_username.getText().toString();
                String password = et_password.getText().toString();
                login(username,password);
            }
        });
    }

    private void login(String username, String password) {
        Map<String,String> body = new HashMap<>();
        body.put("username",username);
        body.put("password",password);

        Map<String,Object> response = new HashMap<>();
        try {
            response = netUtil.sendPostRequest("/api/user/login",body);
            Toast.makeText(MainActivity.this,"登录成功！",Toast.LENGTH_LONG);
            //生成VO并保存用户信息
            SharedPreferences sharedPreferences = getSharedPreferences("user_info",MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("userId",response.get("userId").toString());
            editor.apply();
        } catch (SeecoderHelperException e) {
            Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT);
        } catch (NullPointerException e){
            Toast.makeText(MainActivity.this,"网络请求返回参数错误",Toast.LENGTH_SHORT);
        }
    }
}
