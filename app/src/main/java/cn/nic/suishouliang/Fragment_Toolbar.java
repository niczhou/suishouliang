package cn.nic.suishouliang;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by nic on 2018/3/21.
 */

public class Fragment_Toolbar extends Fragment implements View.OnClickListener{
    private String TAG="nex.print";
    private LocalBroadcastManager localBroadcastManager;
    private Intent intent;
    private Bundle bundle;
    private Button btn_fullscreen;
    private Button btn_fix;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_toolbar,null);
        localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        intent = new Intent();
        btn_fullscreen = (Button) view.findViewById(R.id.btn_fullscreen);
        btn_fullscreen.setOnClickListener(this);
        btn_fix = (Button) view.findViewById(R.id.btn_fix);
        btn_fix.setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick(View v) {
        if(bundle == null){
            bundle = new Bundle();
        }
        intent.setAction("nex_suishouliang");
        switch (v.getId()){
            case R.id.btn_fullscreen:
                bundle.putString("toolbar","fullscreen");
                break;
            case R.id.btn_fix:
                bundle.putString("toolbar","fix");
                break;
        }
        intent.putExtras(bundle);
        localBroadcastManager.sendBroadcast(intent);
    }
}
