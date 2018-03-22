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

public class Fragment_Measure extends Fragment implements View.OnClickListener {
    private String TAG="nex.print";
    private LocalBroadcastManager localBroadcastManager;
    private Intent intent;
    private Bundle bundle;
    private Button btn_redraw;
    private Button btn_reset;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View  view=inflater.inflate(R.layout.fragment_measure,null);
        localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        intent = new Intent();
        btn_redraw = (Button) view.findViewById(R.id.btn_redraw);
        btn_redraw.setOnClickListener(this);
        btn_reset = (Button) view.findViewById(R.id.btn_reset);
        btn_reset.setOnClickListener(this);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onClick(View v) {
        if(bundle == null){
            bundle = new Bundle();
        }
        intent.setAction("nex_suishouliang");
        switch (v.getId()){
            case R.id.btn_redraw:
                bundle.putString("measure","redraw");
                break;
            case R.id.btn_reset:
                bundle.putString("measure","reset");
                break;
        }
        intent.putExtras(bundle);
        localBroadcastManager.sendBroadcast(intent);
    }
}
