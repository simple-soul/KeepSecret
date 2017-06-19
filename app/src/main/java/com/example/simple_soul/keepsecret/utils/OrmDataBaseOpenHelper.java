package com.example.simple_soul.keepsecret.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.simple_soul.keepsecret.domain.Folder;
import com.example.simple_soul.keepsecret.domain.Safe;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by simple_soul on 2017/5/31.
 */

public class OrmDataBaseOpenHelper extends OrmLiteSqliteOpenHelper
{
    private static final String DB_NAME = "KeepSecret.db";
    private static OrmDataBaseOpenHelper instance;

    public OrmDataBaseOpenHelper(Context context)
    {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource)
    {
        try
        {
            TableUtils.createTable(connectionSource, Safe.class);
            TableUtils.createTable(connectionSource, Folder.class);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1)
    {
        try
        {
            TableUtils.dropTable(connectionSource, Safe.class, true);
            TableUtils.dropTable(connectionSource, Folder.class, true);
            onCreate(sqLiteDatabase, connectionSource);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static synchronized OrmDataBaseOpenHelper getHelper(Context context)
    {
        context = context.getApplicationContext();
        if(instance == null)
        {
            synchronized (OrmDataBaseOpenHelper.class)
            {
                if(instance == null)
                {
                    OrmDataBaseOpenHelper helper = new OrmDataBaseOpenHelper(context);
                }
            }
        }
        return instance;
    }

}
