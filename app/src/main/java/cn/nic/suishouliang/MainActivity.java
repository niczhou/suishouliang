package cn.nic.suishouliang;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private String TAG="nex.print";
    private List<Fragment> list_fragment;
    private List<Button> list_button;
    private ViewPager vp_main;
    private Button btn_measure;
    private Button btn_set;
    private Button btn_convert;
    private boolean isVisible=true;
    private final Handler mHideHandler=new Handler(); //handler to hide/show elements in ui
    private View docorView;
    private LocalBroadcastManager localBroadcastManager;
    private BroadcastReceiver broadcastReceiver;
    private Bundle bundle;

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
//            btn_fullscreen= (Button)getSupportFragmentManager().findFragmentById().findViewById(R.id.btn_measure);

            list_button =new ArrayList<>();
            list_button.add(btn_set);
            list_button.add(btn_measure);
            list_button.add(btn_convert);
            list_fragment =new ArrayList<>();
            list_fragment.add(new FragmentSet());
            list_fragment.add(new FragmentMeasure());
            list_fragment.add(new FragmentConvert());

            MainFragmentPagerAdapter mAdapter=new MainFragmentPagerAdapter(getSupportFragmentManager(), list_fragment);
            vp_main.setAdapter(mAdapter);
            vp_main.setCurrentItem(1);

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
        intentFilter.addAction("nex_fragment_measure");
        intentFilter.addAction("nex_fragment_set");
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                bundle = intent.getExtras();
                if(bundle != null && bundle.containsKey("measure")){
                    switch (bundle.getString("measure")){
                        case "fullscreen":
                            Log.v(TAG,"toggle");
                            toggle();
                            break;
                        case "redraw":
                            Log.v(TAG,"redraw");
//                            toggle();
                            break;
                    }
                }
                if(bundle != null && bundle.containsKey("measure")){
                    switch (bundle.getString("measure")){
                        case "fullscreen":
                            Log.v(TAG,"toggle");
                            toggle();
                            break;
                        case "redraw":
                            Log.v(TAG,"redraw");
//                            toggle();
                            break;
                    }
                }
            }
        };
        localBroadcastManager.registerReceiver(broadcastReceiver,intentFilter);
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
            if(isVisible){
                hide();
                isVisible=false;
            }else {
                show();
                isVisible=true;
            }
//            Log.v(TAG,"isvisible: "+isVisible);
        }
            //implements hide
            private void hide(){
//                Log.v(TAG,"hide:");
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
}
