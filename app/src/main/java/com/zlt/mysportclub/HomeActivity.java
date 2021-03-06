package com.zlt.mysportclub;

import android.app.Fragment;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebViewFragment;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.bumptech.glide.Glide;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.volokh.danylo.visibility_utils.calculator.DefaultSingleItemCalculatorCallback;
import com.volokh.danylo.visibility_utils.calculator.ListItemsVisibilityCalculator;
import com.volokh.danylo.visibility_utils.calculator.SingleListViewItemActiveCalculator;
import com.volokh.danylo.visibility_utils.scroll_utils.RecyclerViewItemPositionGetter;
import com.youth.banner.Banner;
import com.zlt.mysportclub.database.TrainerRepo;
import com.zlt.mysportclub.model.Trainer;
import com.zlt.mysportclub.model.VideoListItem;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import devlight.io.library.ntb.NavigationTabBar;

import static cn.bmob.newim.core.BmobIMClient.getContext;

public class HomeActivity extends AppCompatActivity {

    private SurfaceView surfaceView;
    private MediaPlayer player;
    private SurfaceHolder holder;
    private ProgressBar progressBar;
    private int k=2;
    private int rand_img=0;
    private JzvdStd jzvdStd;
    private JzvdStd jzvdStd1;
    private JzvdStd jzvdStd2;
    private JzvdStd jzvdStd3;
    private JzvdStd jzvdStd4;
    private Banner banner;

    String username;
    String userid;
    //设置图片资源:url或本地资源
    String[] images= new String[] {
            "http://pic40.photophoto.cn/20160715/1155115817271643_b.jpg",
            "http://seopic.699pic.com/photo/50043/3085.jpg_wh1200.jpg",
            "http://pic20.photophoto.cn/20110702/0009021158997269_b.jpg",
            "http://imgsrc.baidu.com/imgad/pic/item/0df431adcbef760943acce6924dda3cc7dd99ea4.jpg",};
    String[] titles=new String[]{"火热健身嗨翻全场","每天运动一小时","燃烧你的卡路里！!!","火热健身嗨翻全场"};
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initUI();
        bundle = this.getIntent().getExtras();
        SDKInitializer.initialize(getApplicationContext());
    }

    private void initBanner(View view)
    {
        banner = (Banner) view.findViewById(R.id.banner);

        banner.setBannerStyle(Banner.CIRCLE_INDICATOR_TITLE);

        banner.setIndicatorGravity(Banner.CENTER);

        banner.setBannerTitle(titles);

        //设置是否自动轮播（不设置则默认自动）
        banner.isAutoPlay(true) ;

        banner.setDelayTime(5000);

        banner.setImages(images, new Banner.OnLoadImageListener() {
            @Override
            public void OnLoadImage(ImageView view, Object url) {
                System.out.println("加载中");
                Glide.with(getApplicationContext()).load(url).into(view);
                System.out.println("加载完");
            }
        });
        //设置点击事件，下标是从1开始
        banner.setOnBannerClickListener(new Banner.OnBannerClickListener() {//设置点击事件
            @Override
            public void OnBannerClick(View view, int position) {
                //Toast.makeText(getApplicationContext(), "你点击了：" + position, Toast.LENGTH_LONG).show();
            }
        });
    }
    private void initVideo(View view){

        jzvdStd = view.findViewById(R.id.videoplayer);
        jzvdStd.setUp("http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4"
                , "最燃烧运动", Jzvd.SCREEN_WINDOW_NORMAL);

        jzvdStd1 = view.findViewById(R.id.videoplayer1);
        jzvdStd1.setUp("http://vjs.zencdn.net/v/oceans.mp4"

                , "最燃烧运动", Jzvd.SCREEN_WINDOW_NORMAL);

        jzvdStd2 = view.findViewById(R.id.videoplayer2);
        jzvdStd2.setUp("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"
                , "最燃烧运动", Jzvd.SCREEN_WINDOW_NORMAL);
        jzvdStd3= view.findViewById(R.id.videoplayer3);
        jzvdStd3.setUp("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"
                , "最燃烧运动", Jzvd.SCREEN_WINDOW_NORMAL);
        jzvdStd4 = view.findViewById(R.id.videoplayer4);
        jzvdStd4.setUp("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"
                , "最燃烧运动", Jzvd.SCREEN_WINDOW_NORMAL);
        Uri uri = Uri.parse("D:\\Android_project\\MySportClub\\app\\src\\main\\res\\drawable\\tomas1.jpg");
        try {
            jzvdStd4.thumbImageView.setImageBitmap(getImage("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1546000077941&di=3d3e185178e3e4d3834cca45e430e2f3&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2F908fa0ec08fa513d08dda050376d55fbb3fbd9cd.jpg"));
        }catch (Exception e)
        {
            System.out.print("load image error:"+e);
        }
    }

    public static Bitmap getImage(String path) throws Exception{
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        InputStream is = conn.getInputStream();
        return BitmapFactory.decodeStream(is);
    }

    private class MyCallBack implements SurfaceHolder.Callback {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            player.setDisplay(holder);
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

        }
    }

    private  AlertDialog.Builder initDialog()
    {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("添加教练");

        // 取得自定义View
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        final View myLoginView = layoutInflater.inflate(
                R.layout.dialog, null);
        dialog.setView(myLoginView);

        dialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText dialog_name = (EditText) myLoginView
                                .findViewById(R.id.dialog_name);
                        EditText dialog_phone = (EditText) myLoginView
                                .findViewById(R.id.dialog_phone);
                        EditText dialog_position = (EditText) myLoginView
                                .findViewById(R.id.dialog_position);
                        EditText dialog_course = (EditText) myLoginView
                                .findViewById(R.id.dialog_course);

                        TrainerRepo repo = new TrainerRepo(HomeActivity.this);
                        ArrayList<Trainer> trainerList =  repo.getTrainerList();
                        Trainer trainer = new Trainer();
                        trainer.name = dialog_name.getText().toString();
                        trainer.position = dialog_position.getText().toString();
                        trainer.ID = trainerList.size()+1;
                        trainer.course = dialog_course.getText().toString();
                        trainer.phone = dialog_phone.getText().toString();
                        repo.insert(trainer);
                    }
                });

        dialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });
        return dialog;

    }

    private void initUI() {
        final ViewPager viewPager = (ViewPager) findViewById(R.id.vp_horizontal_ntb);


        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return 5;
            }

            @Override
            public boolean isViewFromObject(final View view, final Object object) {
                return view.equals(object);
            }

            @Override
            public void destroyItem(final View container, final int position, final Object object) {
                ((ViewPager) container).removeView((View) object);
            }

            @Override
            public Object instantiateItem(final ViewGroup container, final int position) {
                final String tel = "18811435290";
                final View view;
                if(position==0){
                    k=0;
                    view = LayoutInflater.from(
                            getBaseContext()).inflate(R.layout.item_vp0, null, false);
                    Button showAll = view.findViewById(R.id.btnGetAll);
                    Button add = view.findViewById(R.id.btnAdd);
                    ListView mylistview = (ListView)view.findViewById(R.id.list);
                    TrainerRepo repo = new TrainerRepo(HomeActivity.this);
                    ArrayList<Trainer> trainerList =  repo.getTrainerList();
                    TrainerAdapter adapter = new TrainerAdapter(HomeActivity.this, R.layout.list_item, trainerList);
                    mylistview.setAdapter(adapter);
                    add.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            initDialog().show();

                            //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,R.layout.list_item,studentList);
                        }
                    });
                    showAll.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            TrainerRepo repo = new TrainerRepo(HomeActivity.this);
                            ListView mylistview = (ListView)view.findViewById(R.id.list);
                            ArrayList<Trainer> trainerList =  repo.getTrainerList();
                            TrainerAdapter adapter = new TrainerAdapter(HomeActivity.this, R.layout.list_item, trainerList);
                            mylistview.setAdapter(adapter);
                            //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,R.layout.list_item,studentList);
                        }
                    });



                }else if(position==1){
                    view = LayoutInflater.from(
                            getBaseContext()).inflate(R.layout.item_vp1, null, false);
                            k=1;
                            final ImageView img = (ImageView) view.findViewById(R.id.image_random);
                            img.setImageDrawable(getResources().getDrawable(R.drawable.tomas0));
                            Button btn0 = (Button)view.findViewById(R.id.btn_tel);
                            btn0.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent();
                                    //设置拨打电话的动作
                                    intent.setAction(Intent.ACTION_CALL);
                                    //设置拨打电话的号码
                                    intent.setData(Uri.parse("tel:" + tel));
                                    //开启打电话的意图
                                    startActivity(intent);
                                }
                            });

                            Button btn1 = (Button)view.findViewById(R.id.button2);
                            btn1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //Intent是一种运行时绑定（run-time binding）机制，它能在程序运行过程中连接两个不同的组件。 
                                //在存放资源代码的文件夹下下， 
                                Intent i = new Intent(HomeActivity.this, MapActivity.class);
                                //启动 
                                startActivity(i);
                                }
                        });

                    //initVideo();
                }else if(position==2){
                    k=2;
                    view = LayoutInflater.from(
                            getBaseContext()).inflate(R.layout.item_vp2, null, false);
                    final TextView t1 =(TextView) view.findViewById(R.id.dip_name);
                    final TextView t2 = (TextView) view.findViewById(R.id.dip_email);
                    t1.setText(String.format("zhang,litian"));
                    t2.setText(String.format("xxx@bjtu.edu.cn"));
                    initVideo(view);
                }else if(position==3){
                    k=3;
                    view = LayoutInflater.from(
                            getBaseContext()).inflate(R.layout.item_vp3, null, false);
                    initBanner(view);
                }else{

                    k=4;
                    view = LayoutInflater.from(
                            getBaseContext()).inflate(R.layout.item_vp4, null, false);
                    TextView tv1 = view.findViewById(R.id.user_name);
                    TextView tv2 = view.findViewById(R.id.user_id);
                    if(bundle!=null){
                        username = bundle.getString("username").toString();
                        userid = bundle.getString("userid").toString();
                        tv1.setText(username);
                        tv2.setText(userid);
                    }
                    LinearLayout about,share;
                    share=(LinearLayout) view.findViewById(R.id.share);
                    share.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final String[] items3 = new String[]{"QQ分享", "QQ空间分享"};//创建item
                            AlertDialog alertDialog3 = new AlertDialog.Builder(HomeActivity.this)
                                    .setTitle("选择您的分享")
                                    .setItems(items3, new DialogInterface.OnClickListener() {//添加列表
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            if(i==0){
                                                onClickShare();
                                            }
                                            if(i==1){
                                                shareToQQzone();
                                            }
                                            Toast.makeText(HomeActivity.this, "点的是：" + items3[i] + i, Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加取消
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Toast.makeText(HomeActivity.this, "这是取消按钮", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .create();
                            alertDialog3.show();
                        }
                    });
                    about = (LinearLayout) view.findViewById(R.id.user_about);
                    about.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            android.app.AlertDialog alertDialog1 = new android.app.AlertDialog.Builder(HomeActivity.this)
                                    .setTitle("关于")//标题
                                    .setMessage("移动应用开发课最终答辩项目：sportclub\n" +
                                            "组员：张力天，朱子潇，莫日根呼\n" +
                                            "所用时间：16weeks\n" +
                                            "版本号：5.0")//内容
                                    .create();
                            alertDialog1.show();
                        }
                    });
                }

                container.addView(view);
                return view;
            }
        });

        final String[] colors = getResources().getStringArray(R.array.default_preview);

        final NavigationTabBar navigationTabBar = (NavigationTabBar) findViewById(R.id.ntb_horizontal);
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_first),
                        Color.parseColor(colors[0]))
                        .selectedIcon(getResources().getDrawable(R.drawable.ic_sixth))
                        .title("Heart")
                        .badgeTitle("NTB")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_second),
                        Color.parseColor(colors[1]))
//                        .selectedIcon(getResources().getDrawable(R.drawable.ic_eighth))
                        .title("Cup")
                        .badgeTitle("with")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_third),
                        Color.parseColor(colors[2]))
                        .selectedIcon(getResources().getDrawable(R.drawable.ic_seventh))
                        .title("Diploma")
                        .badgeTitle("state")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_fourth),
                        Color.parseColor(colors[3]))
//                        .selectedIcon(getResources().getDrawable(R.drawable.ic_eighth))
                        .title("Flag")
                        .badgeTitle("icon")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_fifth),
                        Color.parseColor(colors[4]))
                        .selectedIcon(getResources().getDrawable(R.drawable.ic_eighth))
                        .title("Medal")
                        .badgeTitle("777")
                        .build()
        );

        navigationTabBar.setModels(models);
        navigationTabBar.setViewPager(viewPager, 2);
        navigationTabBar.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
                navigationTabBar.getModels().get(position).hideBadge();
            }

            @Override
            public void onPageScrollStateChanged(final int state) {

            }
        });

        navigationTabBar.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < navigationTabBar.getModels().size(); i++) {
                    final NavigationTabBar.Model model = navigationTabBar.getModels().get(i);
                    navigationTabBar.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            model.showBadge();
                        }
                    }, i * 100);
                }
            }
        }, 500);
        //initVideo();
//        JzvdStd jzvdStd = (JzvdStd) findViewById(R.id.videoplayer);
//        jzvdStd.setUp("http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4"
//                , "饺子闭眼睛", Jzvd.SCREEN_WINDOW_NORMAL);
    }


    //qq分享
    private void onClickShare() {
        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE,
                QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, "要分享的标题");
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, "要分享的摘要");
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,
                "http://blog.csdn.net/DickyQie/article/list/1");
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,
                "http://imgcache.qq.com/qzone/space_item/pre/0/66768.gif");
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "切切歆语");
        params.putString(QQShare.SHARE_TO_QQ_EXT_INT, "其他附加功能");
        Tencent mTencent = Tencent.createInstance("1108071510",
                HomeActivity.this);
        mTencent.shareToQQ(HomeActivity.this, params, new BaseUiListener1());
    }
    //回调接口  (成功和失败的相关操作)
    private class BaseUiListener1 implements IUiListener {
        @Override
        public void onComplete(Object response) {
            doComplete(response);
        }

        protected void doComplete(Object values) {
        }

        @Override
        public void onError(UiError e) {
        }

        @Override
        public void onCancel() {
        }
    }

    private void shareToQQzone() {
        try {
            final Bundle params = new Bundle();
            params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE,
                    QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
            params.putString(QzoneShare.SHARE_TO_QQ_TITLE, "完美天气");
            params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, "sss");
            params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL,
                    "http://blog.csdn.net/DickyQie/article/list/1");
            ArrayList<String> imageUrls = new ArrayList<String>();
            imageUrls.add("http://media-cdn.tripadvisor.com/media/photo-s/01/3e/05/40/the-sandbar-that-links.jpg");
            params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imageUrls);
            params.putInt(QzoneShare.SHARE_TO_QQ_EXT_INT,
                    QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
            Tencent mTencent = Tencent.createInstance("1108071510",
                    HomeActivity.this);
            mTencent.shareToQzone(HomeActivity.this, params,
                    new BaseUiListener1());
        } catch (Exception e) {
        }
    }

    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }
    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
    }
}
