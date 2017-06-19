package com.example.simple_soul.keepsecret;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simple_soul.keepsecret.fragment.BackFragment;
import com.example.simple_soul.keepsecret.fragment.FileFragment;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends FragmentActivity implements BackFragment.BackInterface, View.OnClickListener, ResideMenu.OnMenuListener
{
    private FrameLayout frame;
    private ImageView menu, setting;
    private TextView title;
    private ResideMenu reSideMenu;

    private BackFragment backFragment;
    private boolean isExit = false;
    private FragmentManager manager;
    private boolean isOpen = false;
    private List<ResideMenuItem> reItemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if (checkSelfPermission(
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 200);
            }
            else
            {
                initData();
            }
        }
        else
        {
            initData();
        }
    }

    private void initView()
    {
        frame = (FrameLayout) findViewById(R.id.main_frame);
        title = (TextView) findViewById(R.id.title_tv);
        menu = (ImageView) findViewById(R.id.title_image_menu);
        setting = (ImageView) findViewById(R.id.title_image_setting);
        reSideMenu = new ResideMenu(this);

        reSideMenu.setBackground(R.drawable.menu_background);
        reSideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);
        reSideMenu.setScaleValue(0.7f);
        reSideMenu.attachToActivity(this);
        reSideMenu.setMenuListener(this);

        menu.setOnClickListener(this);
    }

    private void initData()
    {
        String[] titles = new String[]{"文件", "保险箱", "设置"};
        int[] icons = new int[]{R.drawable.ic_folder, R.drawable.ic_safe, R.drawable.ic_setting};

        for (int i = 0; i < titles.length; i++)
        {
            ResideMenuItem item = new ResideMenuItem(MainActivity.this, icons[i], titles[i]);
            reItemList.add(item);
            reSideMenu.addMenuItem(item, ResideMenu.DIRECTION_LEFT);
            item.setOnClickListener(this);
        }

        setDefaultFragment();
    }

    private void setDefaultFragment()
    {
        title.setText("文件");
        manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.main_frame, new FileFragment());
        transaction.commit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        switch (requestCode)
        {
            case 200:
            {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    initData();
                }
            }
        }
    }

    @Override
    public void setCurrentFragment(BackFragment backFragment)
    {
        this.backFragment = backFragment;
    }

    @Override
    public void onBackPressed()
    {
        if (backFragment == null || !backFragment.onBackPressed())
        {
            if (isExit)
            {
                finish();
            }
            else
            {
                Toast.makeText(this, "再按一次退出系统", Toast.LENGTH_SHORT).show();
                isExit = true;

                new Timer().schedule(new TimerTask()
                {
                    @Override
                    public void run()
                    {
                        isExit = false;
                    }
                }, 1500);

            }
        }
    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.title_image_menu:
                if(isOpen)
                {
                    reSideMenu.closeMenu();
                }
                else
                {
                    reSideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
                }
                break;

            default:
                FragmentTransaction transaction = manager.beginTransaction();
                if(v==reItemList.get(0))
                {
                    title.setText("文件");
                    transaction.replace(R.id.main_frame, new FileFragment());
                }
                else if(v==reItemList.get(1))
                {

                }
                else if(v==reItemList.get(2))
                {

                }
                transaction.commit();
        }

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev)
    {
        return reSideMenu.dispatchTouchEvent(ev);
    }

    @Override
    public void openMenu()
    {
        isOpen = true;
    }

    @Override
    public void closeMenu()
    {
        isOpen = false;
    }
}
