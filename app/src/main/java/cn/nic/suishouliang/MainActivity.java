package cn.nic.suishouliang;

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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private List<Fragment> list;
    private ViewPager vp_main;
    private Button btn_fullscreen;
    private boolean isVisible=true;
    private String TAG="nex.print";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

        private void initView(){
            vp_main= (ViewPager) findViewById(R.id.vp_main);
            btn_fullscreen= (Button) findViewById(R.id.btn_fullscreen);

            list=new ArrayList<>();
            list.add(new FragmentCustomize());
            list.add(new FragmentMain());

            MainFragmentPagerAdapter mAdapter=new MainFragmentPagerAdapter(getSupportFragmentManager(),list);
            vp_main.setAdapter(mAdapter);
            vp_main.setCurrentItem(1);

//            btn_fullscreen.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    toggle();
//                }
//            });
        }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_fullscreen:
                toggle();
                Log.v(TAG,"click");
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
            Log.v(TAG,"toggle: "+isVisible);
        }
        //implements hide
            private void hide(){
                ActionBar actionBar=getSupportActionBar();
                if(actionBar!=null){
                    actionBar.hide();
                    Log.v(TAG,"hide:");
                }
            }
        //implement show
            private void show(){

            }

}
