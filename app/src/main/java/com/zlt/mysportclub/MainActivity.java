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
import android.text.TextUtils;
import android.transition.Explode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.zlt.mysportclub.api.Client;
import com.zlt.mysportclub.api.LoginService;
import com.zlt.mysportclub.model.MyUser;
import com.zlt.mysportclub.model.ResultReturn;
import com.zlt.mysportclub.utils.WeiboDialogUtils;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONException;
import org.json.JSONObject;

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

    /*
    * qq登录功能
    */
    public static Tencent mTencent;
    private UserInfo mInfo;
    private Button btqq;
    public static String mAppid="1106062414";

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
        btqq = (Button)findViewById(R.id.btn_qq);
        cv = findViewById(R.id.cv);
        fab = findViewById(R.id.fab);
        avi = (AVLoadingIndicatorView) findViewById(R.id.avi);
        avi.hide();
        if (mTencent == null) {
            mTencent = Tencent.createInstance(mAppid, this);
        }
    }

    private void setListener() {
        btqq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // mTencent.login(MainActivity.this,"all",loginListener);
                onClickLogin();
            }
        });
        btGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,HomeActivity.class);
                Bundle bundle = new Bundle();
                String username = Username.getText().toString();
                String userid = username + "198650";
                bundle.putString("username",username);
                bundle.putString("userid",userid);
                intent.putExtras(bundle);
                Explode explode = new Explode();
                explode.setDuration(500);
                getWindow().setExitTransition(explode);
                getWindow().setEnterTransition(explode);
                ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this);
                Intent i2 = new Intent(MainActivity.this, HomeActivity.class);
                i2.putExtras(bundle);
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
    /**
     * 继承的到BaseUiListener得到doComplete()的方法信息
     */
    IUiListener loginListener = new BaseUiListener() {
        @Override
        protected void doComplete(JSONObject values) {//得到用户的ID  和签名等信息  用来得到用户信息
            Log.i("lkei",values.toString());
            initOpenidAndToken(values);
            updateUserInfo();
        }
    };
    /***
     * QQ平台返回返回数据个体 loginListener的values
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_LOGIN ||
                requestCode == Constants.REQUEST_APPBAR) {
            Tencent.onActivityResultData(requestCode,resultCode,data,loginListener);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private class BaseUiListener implements IUiListener {
        @Override
        public void onComplete(Object response) {
            if (null == response) {
                Toast.makeText(MainActivity.this, "登录失败",Toast.LENGTH_LONG).show();
                return;
            }
            JSONObject jsonResponse = (JSONObject) response;
            if (null != jsonResponse && jsonResponse.length() == 0) {
                Toast.makeText(MainActivity.this, "登录失败",Toast.LENGTH_LONG).show();
                return;
            }
            Toast.makeText(MainActivity.this, "登录成功",Toast.LENGTH_LONG).show();
            doComplete((JSONObject)response);
        }

        protected void doComplete(JSONObject values) {
            
        }
        @Override
        public void onError(UiError e) {
            //登录错误
        }

        @Override
        public void onCancel() {
            // 运行完成
        }
    }
    /**
     * 获取登录QQ腾讯平台的权限信息(用于访问QQ用户信息)
     * @param jsonObject
     */
    public static void initOpenidAndToken(JSONObject jsonObject) {
        try {
            String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
            String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
            String openId = jsonObject.getString(Constants.PARAM_OPEN_ID);
            if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires)
                    && !TextUtils.isEmpty(openId)) {
                mTencent.setAccessToken(token, expires);
                mTencent.setOpenId(openId);
            }
        } catch(Exception e) {
        }
    }
    private void onClickLogin() {
        if (!mTencent.isSessionValid()) {
            mTencent.login(this, "all", loginListener);
        }
    }
    private void updateUserInfo() {
        if (mTencent != null && mTencent.isSessionValid()) {
            IUiListener listener = new IUiListener() {
                @Override
                public void onError(UiError e) {
                }
                @Override
                public void onComplete(final Object response) {
                    Message msg = new Message();
                    msg.obj = response;
                    Log.i("tag", response.toString());
                    msg.what = 0;
                    mHandler.sendMessage(msg);
                }
                @Override
                public void onCancel() {
                }
            };
            mInfo = new UserInfo(this, mTencent.getQQToken());
            mInfo.getUserInfo(listener);

        }
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
