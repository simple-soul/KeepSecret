package com.example.simple_soul.keepsecret.domain.dao;

import android.content.Context;

import com.example.simple_soul.keepsecret.domain.Safe;
import com.example.simple_soul.keepsecret.utils.OrmDataBaseOpenHelper;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

/**
 * Created by simple_soul on 2017/6/18.
 */

public class SafeDao
{
    private Context context;
    private OrmDataBaseOpenHelper helper;
    private Dao dao;

    public SafeDao(Context context)
    {
        this.context = context;
        this.helper = OrmDataBaseOpenHelper.getHelper(context);
        try
        {
            this.dao = helper.getDao(Safe.class);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void createDefaultSafe()
    {
        try
        {
            if(dao.queryForId(0)==null)
            {
                dao.create(new Safe("默认保险箱"));
            }
            else
            {

            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
