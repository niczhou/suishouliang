package cn.nic.suishouliang;

import android.os.Handler;
import android.support.v4.app.Fragment;
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
    private List<Fragment> list;
    private ViewPager vp_main;
    private Button btn_measure;
    private Button btn_fullscreen;
    private boolean isVisible=true;
    private final Handler mHideHandler=new Handler(); //handler to hide/show elements in ui
    private View docorView;

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
//            btn_fullscreen= (Button)getSupportFragmentManager().findFragmentById().findViewById(R.id.btn_measure);

            list=new ArrayList<>();
            list.add(new FragmentCustomize());
            list.add(new FragmentMain());

            MainFragmentPagerAdapter mAdapter=new MainFragmentPagerAdapter(getSupportFragmentManager(),list);
            vp_main.setAdapter(mAdapter);
            vp_main.setCurrentItem(1);

            btn_measure.setOnClickListener(this);
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
                Log.v(TAG,"hide:");
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
