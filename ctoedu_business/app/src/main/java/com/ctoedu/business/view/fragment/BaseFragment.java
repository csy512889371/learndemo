package com.ctoedu.business.view.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.support.annotation.NonNull;


public class BaseFragment extends Fragment {

    protected Activity mContext;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
