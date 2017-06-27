package com.example.simple_soul.keepsecret.fragment;

import android.view.View;

import com.example.simple_soul.keepsecret.R;
import com.example.simple_soul.keepsecret.custom.PasswordView;

/**
 * Created by simple_soul on 2017/6/19.
 */

public class VerifyFragment extends BaseFragment
{
    private PasswordView passwordView;

    @Override
    public View initView()
    {
        View view = View.inflate(mActivity, R.layout.fragment_verification, null);

        passwordView = (PasswordView) view.findViewById(R.id.ver_pass);


        return view;
    }

    @Override
    public void initData()
    {

    }
}
