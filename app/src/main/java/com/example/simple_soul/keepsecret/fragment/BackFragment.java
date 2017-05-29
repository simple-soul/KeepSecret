package com.example.simple_soul.keepsecret.fragment;

import android.os.Bundle;

/**
 * Created by simple_soul on 2017/5/21.
 */

public abstract class BackFragment extends BaseFragment
{
    public interface BackInterface
    {
        public abstract void setCurrentFragment(BackFragment backFragment);
    }

    protected BackInterface mBackInterface;

    /**
     * 所有继承BackHandledFragment的子类都将在这个方法中实现物理Back键按下后的逻辑
     * Activity捕捉到物理返回键点击事件后会首先询问Fragment是否消费该事件
     * 如果没有Fragment消息时Activity自己才会消费该事件
     */

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (!(mActivity instanceof BackInterface))
        {
            throw new ClassCastException("Hosting Activity must implement BackHandledInterface");
        }
        else
        {
            this.mBackInterface = (BackInterface) getActivity();
        }
    }

    @Override
    public void onStart()
    {
        super.onStart();
        //告诉FragmentActivity，当前Fragment在栈顶
        mBackInterface.setCurrentFragment(this);
    }

    public abstract boolean onBackPressed();
}
