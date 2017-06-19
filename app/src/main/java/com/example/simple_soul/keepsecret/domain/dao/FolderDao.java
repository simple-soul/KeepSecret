package com.example.simple_soul.keepsecret.domain.dao;

import android.content.Context;

import com.example.simple_soul.keepsecret.domain.Folder;
import com.example.simple_soul.keepsecret.utils.OrmDataBaseOpenHelper;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

/**
 * Created by simple_soul on 2017/6/18.
 */

public class FolderDao
{
    private Context context;
    private OrmDataBaseOpenHelper helper;
    private Dao dao;

    public FolderDao(Context context)
    {
        this.context = context;
        this.helper = OrmDataBaseOpenHelper.getHelper(context);
        try
        {
            this.dao = helper.getDao(Folder.class);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }


}
