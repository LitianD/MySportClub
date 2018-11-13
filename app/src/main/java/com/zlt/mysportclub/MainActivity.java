package com.zlt.mysportclub;

import android.app.ActivityOptions;
import android.app.Dialog;
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
import com.zlt.mysportclub.model.ResultReturn;
import com.zlt.mysportclub.utils.WeiboDialogUtils;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private EditText Username;
    private EditText Password;
    private Button btGo;
    private CardView cv;
    private FloatingActionButton fab;
    private AVLoadingIndicatorView avi;
    Dialog dialog ;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    WeiboDialogUtils.closeDialog(dialog);
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
    }

    private void initView() {
        Username = findViewById(R.id.et_username);
        Password = findViewById(R.id.et_password);
        btGo = findViewById(R.id.bt_go);
        cv = findViewById(R.id.cv);
        fab = findViewById(R.id.fab);
        avi = (AVLoadingIndicatorView) findViewById(R.id.avi);
        avi.hide();
    }

    private void setListener() {
        btGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog = WeiboDialogUtils.createLoadingDialog(MainActivity.this, "加载中...");
                excuteLogin();
                mHandler.sendEmptyMessageDelayed(1, 2000);
                Explode explode = new Explode();
                explode.setDuration(500);
                getWindow().setExitTransition(explode);
                getWindow().setEnterTransition(explode);
                ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this);
                Intent i2 = new Intent(MainActivity.this,HomeActivity.class);
                startActivity(i2, oc2.toBundle());
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

    private void excuteLogin(){
        String email = Username.getText().toString().trim();
        String pwd = Password.getText().toString().trim();
        LoginService service = Client.retrofit.create(LoginService.class);
        Call<ResultReturn> result = service.login(email,pwd);
        result.enqueue(new Callback<ResultReturn>() {
            @Override
            public void onResponse(Call<ResultReturn> call, Response<ResultReturn> response) {
                System.out.print(response.body());
            }

            @Override
            public void onFailure(Call<ResultReturn> call, Throwable t) {

            }
        });

    }
}
