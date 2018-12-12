package com.zlt.mysportclub;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.transition.Explode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zlt.mysportclub.api.Client;
import com.zlt.mysportclub.api.LoginService;
import com.zlt.mysportclub.model.MyUser;
import com.zlt.mysportclub.model.ResultReturn;
import com.zlt.mysportclub.utils.WeiboDialogUtils;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import cn.bmob.v3.Bmob;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private EditText Username;
    private EditText Password;
    private Button btGo;
    private CardView cv;
    private FloatingActionButton fab;
    private AVLoadingIndicatorView avi;
    Dialog dialog ;
    boolean ifLogin = true;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    WeiboDialogUtils.closeDialog(dialog);
                    Intent i2 = new Intent();
                    i2.setClass(MainActivity.this,HomeActivity.class);
                    startActivity(i2);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        setListener();
        Bmob.initialize(this, "5f5299b27babf6d8d139c6924fae6864");

    }

    private void initView() {
        Username = findViewById(R.id.et_username1);
        Password = findViewById(R.id.et_password1);
        btGo = (Button)findViewById(R.id.bt_go1);
        cv = findViewById(R.id.cv);
        fab = findViewById(R.id.fab);
        avi = (AVLoadingIndicatorView) findViewById(R.id.avi);
        avi.hide();
    }

    private void setListener() {
        btGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Explode explode = new Explode();
                explode.setDuration(500);
                getWindow().setExitTransition(explode);
                getWindow().setEnterTransition(explode);
                ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this);
                Intent i2 = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(i2, oc2.toBundle());
//                boolean control = excuteLogin();
//                if(!control) {
//
//                    Intent i2 = new Intent();
//                    i2.setClass(MainActivity.this,HomeActivity.class);
//                    startActivity(i2);
//                }
//                else
//                {
//                    Explode explode = new Explode();
//                    explode.setDuration(500);
//                    getWindow().setExitTransition(explode);
//                    getWindow().setEnterTransition(explode);
//                    ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this);
//                    Intent i2 = new Intent(MainActivity.this, HomeActivity.class);
//                    startActivity(i2, oc2.toBundle());
//                }
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getWindow().setExitTransition(null);
                getWindow().setEnterTransition(null);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, fab, fab.getTransitionName());
                startActivity(new Intent(MainActivity.this, RegisterActivity.class), options.toBundle());
            }
        });
    }

    private boolean excuteLogin(){
        String email = Username.getText().toString().trim();
        String pwd = Password.getText().toString().trim();

//        LoginService service = Client.retrofit.create(LoginService.class);
//        Call<ResultReturn> result = service.login(email,pwd);
//        result.enqueue(new Callback<ResultReturn>() {
//            @Override
//            public void onResponse(Call<ResultReturn> call, Response<ResultReturn> response) {
//                System.out.print(response.body());
//            }
//
//            @Override
//            public void onFailure(Call<ResultReturn> call, Throwable t) {
//
//            }
//        });

        MyUser bean = new MyUser();
        bean.setUsername(email);
        bean.setPassword(pwd);
        dialog = WeiboDialogUtils.createLoadingDialog(MainActivity.this, "加载中...");
        bean.login(this, new SaveListener() {
            @Override
            public void onSuccess() {
                ifLogin = true;
                Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_LONG)
                        .show();
//                mHandler.sendEmptyMessage(1);
            }

            @Override
            public void onFailure(int arg0, String arg1) {
                Toast.makeText(MainActivity.this, "账号或密码错误",
                        Toast.LENGTH_LONG).show();
                WeiboDialogUtils.closeDialog(dialog);
                ifLogin = false;
            }
        });

        return ifLogin;
    }
}
