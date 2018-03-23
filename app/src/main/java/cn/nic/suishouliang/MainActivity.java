package cn.nic.suishouliang;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private String TAG="nex.print";
    private boolean isDecorVisible =  true;
    private Handler handler; //handler to hide/show elements in ui
    private LocalBroadcastManager localBroadcastManager;
    private BroadcastReceiver broadcastReceiver;
    private Bundle bundle;
    private String strLog = "Log";

    private View docorView;
    private List<Fragment> list_fragment;
    private List<Button> list_button;
    private ViewPager vp_main;
    private Button btn_measure;
    private Button btn_set;
    private Button btn_convert;
    private TextView tv_log;
    private ScaleView sv_north;
    private ScaleView sv_east;
    private int sb_height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }
        //init ui
        private void initView(){
            docorView = getWindow().getDecorView();
            vp_main= (ViewPager) findViewById(R.id.vp_main);
            btn_measure= (Button) findViewById(R.id.btn_measure);
            btn_set = (Button) findViewById(R.id.btn_set);
            btn_convert = (Button) findViewById(R.id.btn_convert);
            tv_log = (TextView) findViewById(R.id.tv_log);
            sv_north = (ScaleView) findViewById(R.id.sv_north);
            sv_east = (ScaleView) findViewById(R.id.sv_east);

            list_button =new ArrayList<>();
//            list_button.add(btn_set);
            list_button.add(btn_measure);
            list_button.add(btn_convert);
            list_fragment =new ArrayList<>();
//            list_fragment.add(new Fragment_Set());
            list_fragment.add(new Fragment_Measure());
            list_fragment.add(new Fragment_Convert());

            MainFragmentPagerAdapter mAdapter=new MainFragmentPagerAdapter(getSupportFragmentManager(), list_fragment);
            vp_main.setAdapter(mAdapter);
            vp_main.setCurrentItem(0);

            for(int i=0;i<list_button.size();i++){
                list_button.get(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int j = 1;
                        j= list_button.indexOf(v);
                        vp_main.setCurrentItem(j);
                    }
                });
            }
            //TODO handle scaleview
            handler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    switch (msg.what){
                        case 1:
                            sv_east.refit(sv_east.margin,msg.arg1);
                            sv_east.postInvalidate();
                            break;
                        case 2:
                            sv_north.padding = sv_north.padding + 20;
                            sv_north.refit(sv_north.margin,sv_north.padding);
                            sv_north.postInvalidate();
                            break;
                    }
                }
            };

        }

    @Override
    protected void onResume() {
        super.onResume();
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("nex_suishouliang");
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                bundle = intent.getExtras();
                if(bundle != null && bundle.containsKey("toolbar")){
                    switch (bundle.getString("toolbar")){
                        case "fullscreen":
                            toggle();
                            sb_height = getStatusbarHeight();
                            new Thread(){
                                @Override
                                public void run() {
//                                    super.run();
                                    Message message = new Message();
                                    message.what =1;
//                                    message.arg1 =sb_height;
                                    message.arg1 =12;
                                    handler.sendMessage(message);
                                }
                            }.start();
                            break;
                        case "fix":
                            if(sv_north.isMovable()){
                                sv_north.setMovable(false);
                            }else {
                                sv_north.setMovable(true);
                            }
                            if(sv_east.isMovable()){
                                sv_east.setMovable(false);
                            }else {
                                sv_east.setMovable(true);
                            }
                            log("isMovable:" + sv_north.isMovable());
                            break;
                    }
                }
                if(bundle != null && bundle.containsKey("measure")){
                    switch (bundle.getString("measure")){
                        case "redraw":
                            log("redraw");
                            new Thread(){
                                @Override
                                public void run() {
//                                    super.run();
                                    Message message = new Message();
                                    message.what =2;
                                    handler.sendMessage(message);
                                }
                            }.start();
                            break;
                        case "reset":
                            log("reset");
                            break;
                    }
                }
            }
        };
        localBroadcastManager.registerReceiver(broadcastReceiver,intentFilter);
    }
            private int getStatusbarHeight(){
                Display rootRect = getWindow().getWindowManager().getDefaultDisplay();  // sys window
                Rect appRect = new Rect();  // app window
                Rect drawRect = new Rect(); //draw area;
                getWindow().getDecorView().getWindowVisibleDisplayFrame(appRect);
                getWindow().findViewById(Window.ID_ANDROID_CONTENT).getDrawingRect(drawRect);

                Point point = new Point();
                rootRect.getSize(point);
                int root_height = point.y;
                int app_top = appRect.top;
//                int h = root_height - app_height;
                log("root_height/app_top:"+ root_height+"/"+app_top);
                return  app_top;
            }

    @Override
    protected void onPause() {
        super.onPause();
        localBroadcastManager.unregisterReceiver(broadcastReceiver);
    }

    //implement onclick events
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_measure:
                toggle();
                break;
        }
    }
        //implements toggole
        private void toggle(){
            if(isDecorVisible){
                hide();
                isDecorVisible =false;
            }else {
                show();
                isDecorVisible =true;
            }
        }
            //implements hide
            private void hide(){
                ActionBar actionBar = getSupportActionBar();
                if(actionBar != null){
                    actionBar.hide();
                }
                docorView.setSystemUiVisibility( View.SYSTEM_UI_FLAG_FULLSCREEN);
//                handler.removeCallbacks(mShowPart2Runnalble);
//                vp_main.setSystemUiVisibility(View.GONE);
            }
        //implement show
            private void show(){
//                Log.v(TAG,"show:");
                docorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);

            }
                //showPart2Runnable
                private final Runnable mShowPart2Runnalble=new Runnable() {
                    @Override
                    public void run() {
                        ActionBar actionBar=getSupportActionBar();
                        if(actionBar!=null){
                            actionBar.show();
                        }
                    }
                };
                // runnable to hide
                private final Runnable mHidePart2Runnable=new Runnable(){
                    @Override
                    public void run(){
//                        hide();
                    }
                };

    public void log(String string2log){
        strLog = string2log;
        tv_log.setText(string2log);
    }
}
