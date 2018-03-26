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
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

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
    private EditText et_phone;
    private EditText et_weight;
    private EditText et_height;
    private EditText et_width;
    private EditText et_mnorth;
    private EditText et_msouth;
    private EditText et_mwest;
    private EditText et_meast;
    private List<EditText> list_et;
    private boolean isEditable = false;


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

        et_phone = (EditText) view.findViewById(R.id.et_phone);
        et_weight = (EditText) view.findViewById(R.id.et_weight);
        et_height = (EditText) view.findViewById(R.id.et_height);
        et_width = (EditText) view.findViewById(R.id.et_width);
        et_mnorth = (EditText) view.findViewById(R.id.et_mnorth);
        et_msouth = (EditText) view.findViewById(R.id.et_msouth);
        et_mwest = (EditText) view.findViewById(R.id.et_mwest);
        et_meast = (EditText) view.findViewById(R.id.et_meast);
        list_et = new ArrayList<>();
        list_et.add(et_phone);
        list_et.add(et_weight);
        list_et.add(et_height);
        list_et.add(et_width);
        list_et.add(et_mnorth);
        list_et.add(et_msouth);
        list_et.add(et_mwest);
        list_et.add(et_meast);

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
                reset();
                break;
        }
        intent.putExtras(bundle);
        localBroadcastManager.sendBroadcast(intent);
    }
        private void reset(){
            if(isEditable){
                for (EditText et:list_et) {
                    et.setEnabled(false);
                }
                btn_reset.setText("自定义");
                isEditable = false;
            }else{
                for (EditText et:list_et) {
                    et.setEnabled(true);
                }
                btn_reset.setText("确定");
                isEditable = true;
            }
        }
}
