package com.jordan.project.activities.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jordan.project.R;

public class FingerpostFragment extends Fragment {
	private int number;

	@SuppressLint({"NewApi", "ValidFragment"})
	public FingerpostFragment(){}

	@SuppressLint({"NewApi", "ValidFragment"})
    public FingerpostFragment(int number){
		this.number = number;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

     @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
    	View view=inflater.inflate(R.layout.frist_start_fragment, null);
		 switch (number){
			 case 1:
				 view.setBackgroundResource(R.mipmap.fingerpost_001);
			 	break;
			 case 2:
				 view.setBackgroundResource(R.mipmap.fingerpost_002);
			 	break;
			 case 3:
				 view.setBackgroundResource(R.mipmap.fingerpost_003);
			 	break;
			 case 4:
				 view.setBackgroundResource(R.mipmap.fingerpost_004);
			 	break;

		 }
		 view.setOnClickListener(new View.OnClickListener() {
			 @Override
			 public void onClick(View v) {
				 getActivity().finish();
			 }
		 });
    	return view;
    }
}
