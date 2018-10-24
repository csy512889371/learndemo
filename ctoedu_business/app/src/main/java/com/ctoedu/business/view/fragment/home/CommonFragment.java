package com.ctoedu.business.view.fragment.home;


import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CommonFragment extends Fragment {
    
    int index;

    public CommonFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return getContentView();
    }

    private View getContentView() {

        RelativeLayout relativeLayout = new RelativeLayout(this.getActivity());
        relativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
        TextView textView = new TextView(this.getActivity());
        textView.setTextColor(Color.WHITE);
        textView.setTextSize(50);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        if (index == 1) {
            textView.setText("鱼糖页");
        } else {
            textView.setText("我的页");
        }
        textView.setLayoutParams(params);

        relativeLayout.addView(textView);
        return relativeLayout;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
