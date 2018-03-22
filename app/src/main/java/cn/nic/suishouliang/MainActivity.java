package cn.nic.suishouliang;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
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
    private final Handler mHideHandler = new Handler(); //handler to hide/show elements in ui
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
                            log("toggle");
                            toggle();
                            int padding = getStatusbarHeight();
                            log("statusheight:"+padding);
//                            sv_east.fitScroll(sv_east.margin,padding);
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
                Display rootDisplay = getWindow().getWindowManager().getDefaultDisplay();  // sys window
                Rect appRect = new Rect();  // app window
                Rect drawRect = new Rect(); //draw area;
                getWindow().getDecorView().getWindowVisibleDisplayFrame(appRect);
                getWindow().findViewById(Window.ID_ANDROID_CONTENT).getDrawingRect(drawRect);

                Point point = new Point();
                rootDisplay.getSize(point);
                int root_height = point.y;
                int app_height = appRect.top;
                int h = root_height - app_height;
                return  h;
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
//                mHideHandler.removeCallbacks(mShowPart2Runnalble);
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
