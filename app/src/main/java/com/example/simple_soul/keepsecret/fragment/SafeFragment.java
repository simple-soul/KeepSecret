package com.example.simple_soul.keepsecret.fragment;

import android.view.View;

import com.example.simple_soul.keepsecret.R;

/**
 * Created by simple_soul on 2017/6/18.
 */

public class SafeFragment extends BaseFragment
{


    @Override
    public View initView()
    {
        View view = View.inflate(mActivity, R.layout.fragment_safe, null);

        return view;
    }

    @Override
    public void initData()
    {

    }
}
