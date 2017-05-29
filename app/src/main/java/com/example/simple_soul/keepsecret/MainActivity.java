package com.example.simple_soul.keepsecret;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simple_soul.keepsecret.fragment.BackFragment;
import com.example.simple_soul.keepsecret.fragment.FileFragment;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends FragmentActivity implements BackFragment.BackInterface
{
    private FrameLayout frame;
    private ImageView menu, setting;
    private TextView title;

    private BackFragment backFragment;
    private boolean isExit = false;
    private FragmentManager manager;

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
    }

    private void initData()
    {
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
                // If request is cancelled, the result arrays are empty.
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


}
