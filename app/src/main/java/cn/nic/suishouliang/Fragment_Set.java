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
 * Created by nic on 2018/3/6.
 */

public class Fragment_Set extends Fragment implements View.OnClickListener{
    private LocalBroadcastManager localBroadcastManager;
    private Intent intent;
    private Bundle bundle;

    private Button btn_fix;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view=inflater.inflate(R.layout.fragment_set,null);
        btn_fix = (Button) view.findViewById(R.id.btn_fix);
        btn_fix.setOnClickListener(this);

        intent = new Intent();
        localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        return view;
    }

    @Override
    public void onClick(View v) {
        if(bundle == null){
            bundle = new Bundle();
        }
        intent.setAction("nex_suishouliang");
        switch (v.getId()){
            case R.id.btn_fix:
                bundle.putString("set","fix");
                break;
        }
        intent.putExtras(bundle);
        localBroadcastManager.sendBroadcast(intent);
    }
}
