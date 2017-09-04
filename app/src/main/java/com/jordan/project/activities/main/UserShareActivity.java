package com.jordan.project.activities.main;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jordan.project.R;
import com.jordan.project.activities.adapter.PlayBallListAdapter;
import com.jordan.project.activities.adapter.UserShareListAdapter;

import java.util.ArrayList;

public class UserShareActivity extends Activity {

    ArrayList<String> mUserShareList = new ArrayList<String>();
    ListView mLvUserShare;
    UserShareListAdapter mUserShareListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_user_share);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.common_back_title);
        TextView mTvTitle = (TextView) findViewById(R.id.title_text);
        mTvTitle.setText(getResources().getString(R.string.my_share));
        RelativeLayout mRLBack=(RelativeLayout)findViewById(R.id.title_back);
        mRLBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setView();
        setListener();
    }
    private void setView() {

        mLvUserShare = (ListView)findViewById(R.id.user_share_list);

        mUserShareList=new ArrayList<String>();


        mUserShareListAdapter = new UserShareListAdapter(UserShareActivity.this,mUserShareList);
        mLvUserShare.setAdapter(mUserShareListAdapter);

    }

    private void setListener() {

    }
}
