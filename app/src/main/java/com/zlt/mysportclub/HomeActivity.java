package com.zlt.mysportclub;

import android.app.Fragment;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.baidu.mapapi.SDKInitializer;
import com.zlt.mysportclub.database.TrainerRepo;
import com.zlt.mysportclub.model.Trainer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import devlight.io.library.ntb.NavigationTabBar;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initUI();

        SDKInitializer.initialize(getApplicationContext());

        //("http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640");
        //initVideo();
    }

    private void initVideo(){
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
                    jzvdStd = view.findViewById(R.id.videoplayer);
                    jzvdStd.setUp("http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4"
                            , "最燃烧运动", Jzvd.SCREEN_WINDOW_NORMAL);

                    jzvdStd1 = view.findViewById(R.id.videoplayer1);
                    jzvdStd1.setUp("http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4"
                            , "最燃烧运动", Jzvd.SCREEN_WINDOW_NORMAL);

                    jzvdStd2 = view.findViewById(R.id.videoplayer2);
                    jzvdStd2.setUp("http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4"
                            , "最燃烧运动", Jzvd.SCREEN_WINDOW_NORMAL);
                    Uri uri = Uri.parse("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1544527645995&di=de7a5e7a5bd0c08fe5b430aacfc92ffb&imgtype=0&src=http%3A%2F%2Fp1.img.cctvpic.com%2Fphotoworkspace%2Fcontentimg%2F2015%2F05%2F20%2F2015052009401236361.jpg");
                    jzvdStd.thumbImageView.setImageURI(uri);
                }else if(position==3){
                    k=3;
                    view = LayoutInflater.from(
                            getBaseContext()).inflate(R.layout.item_vp3, null, false);
                }else{
                    k=4;
                    view = LayoutInflater.from(
                            getBaseContext()).inflate(R.layout.item_vp4, null, false);
                }
//                final TextView txtPage = (TextView) view.findViewById(R.id.txt_vp_item_page);
//                txtPage.setText(String.format("Page #%d", position));
                Random random = new Random();
                rand_img = random.nextInt(4);

//                final ImageView img = (ImageView) view.findViewById(R.id.image_random);
//                img = (ImageView) view.findViewById(R.id.image_random);
//                if(position==1){
//                    if(rand_img==0) {
//                        img.setImageDrawable(getResources().getDrawable(R.drawable.tomas0));
//                    }
//                    if(rand_img==1) {
//                        img.setImageDrawable(getResources().getDrawable(R.drawable.tomas1));
//                    }
//                    if(rand_img==2) {
//                        img.setImageDrawable(getResources().getDrawable(R.drawable.tomas2));
//                    }
//                    if(rand_img==3) {
//                        img.setImageDrawable(getResources().getDrawable(R.drawable.tomas3));
//                    }
//                }
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
