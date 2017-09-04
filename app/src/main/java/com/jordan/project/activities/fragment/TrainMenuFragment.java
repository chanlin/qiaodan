package com.jordan.project.activities.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.safari.httplibs.config.InnerMessageConfig;
import com.jordan.project.R;
import com.jordan.project.activities.adapter.TrainMenuAdapter;
import com.jordan.project.activities.train.TrainListActivity;
import com.jordan.project.data.TrainDictData;
import com.jordan.project.utils.JsonFalseUtils;
import com.jordan.project.utils.JsonSuccessUtils;
import com.jordan.project.utils.LogUtils;
import com.jordan.project.utils.ToastUtils;
import com.jordan.project.widget.PathOfParticleGridView;
import com.jordan.usersystemlibrary.UserManager;

import org.json.JSONException;

import java.util.ArrayList;

public class TrainMenuFragment extends Fragment  {

    private UserManager userManager;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case InnerMessageConfig.USER_TRAIN_DICT_MESSAGE_SUCCESS:
                    //解析
                    LogUtils.showLog("Result", "USER_TRAIN_DICT_MESSAGE_SUCCESS result:"+(String)msg.obj);
                    try {
                        //获取list
                        ArrayList<TrainDictData> list = JsonSuccessUtils.getTrainList((String)msg.obj);
                        if(list.size()!=0) {
                            topList = new ArrayList<TrainDictData>();
                            endList = new ArrayList<TrainDictData>();
                            //分类
                            for (int i=0;i<list.size();i++){
                                TrainDictData trainDictData= list.get(i);
                                if(trainDictData.getType()==1){
                                    topList.add(trainDictData);
                                }else if(trainDictData.getType()==2){
                                    endList.add(trainDictData);
                                }
                            }
                            //分别update
                            mTopTrainMenuAdapter.updateList(topList);
                            mEndTrainMenuAdapter.updateList(endList);
                        }
                    }catch (Exception e){
                        mHandler.sendEmptyMessage(InnerMessageConfig.USER_TRAIN_DICT_MESSAGE_EXCEPTION);
                        e.printStackTrace();
                    }
                    break;
                case InnerMessageConfig.USER_TRAIN_DICT_MESSAGE_EXCEPTION:
                    try {
                        ToastUtils.shortToast(getActivity(), R.string.http_exception);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case InnerMessageConfig.USER_TRAIN_DICT_MESSAGE_FALSE:
                    try {
                        String errorMsg = JsonFalseUtils.onlyErrorCodeResult((String) msg.obj);
                        if (errorMsg != null)
                            ToastUtils.shortToast(getActivity(), errorMsg);
                    } catch (JSONException e) {
                        mHandler.sendEmptyMessage(InnerMessageConfig.USER_TRAIN_DICT_MESSAGE_EXCEPTION);
                        e.printStackTrace();
                    }
                    break;
            }

        }
    };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
    }

    private PathOfParticleGridView mGvTop;//train_over_view_item_top_gv
    private TrainMenuAdapter mTopTrainMenuAdapter;
    private ArrayList<TrainDictData> topList = new ArrayList<TrainDictData>();
    private PathOfParticleGridView mGvEnd;//train_over_view_item_end_gv
    private TrainMenuAdapter mEndTrainMenuAdapter;
    private ArrayList<TrainDictData> endList = new ArrayList<TrainDictData>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_train_menu, null);
        userManager = new UserManager(getActivity(), mHandler);
        setView(view);
        setListener();
        trainDict();


        return view;
    }
    private void trainDict() {
        userManager.trainDict();
    }


    private void setListener() {
    }


    public void setView(View view) {
        mGvTop = (PathOfParticleGridView) view.findViewById(R.id.train_over_view_item_top_gv);
        mTopTrainMenuAdapter = new TrainMenuAdapter(getActivity(), topList);
        mGvTop.setAdapter(mTopTrainMenuAdapter);
        mGvTop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(),TrainListActivity.class);
                intent.putExtra("id",topList.get(position).getId());
                startActivity(intent);
            }
        });
        mGvEnd = (PathOfParticleGridView) view.findViewById(R.id.train_over_view_item_end_gv);
        mEndTrainMenuAdapter = new TrainMenuAdapter(getActivity(), endList);
        mGvEnd.setAdapter(mEndTrainMenuAdapter);
        mGvEnd.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(),TrainListActivity.class);
                intent.putExtra("id",endList.get(position).getId());
                startActivity(intent);
            }
        });

    }
}
