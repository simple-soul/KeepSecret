package com.example.simple_soul.keepsecret.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Created by simple_soul on 2017/5/31.
 */
@DatabaseTable(tableName = "safe")
public class Safe
{
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(columnName = "name")
    private String name;
    @DatabaseField(columnName = "time")
    private Date time = new Date();
    @DatabaseField(columnName = "isLock")
    private boolean isLock = false;
    @DatabaseField(columnName = "isDelete")
    private boolean isDelete = false;

    public Safe(String name)
    {
        this.name = name;
    }

    public Safe(int id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public Date getTime()
    {
        return time;
    }

    public boolean isLock()
    {
        return isLock;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setLock(boolean lock)
    {
        isLock = lock;
    }

    public boolean isDelete()
    {
        return isDelete;
    }

    public void setDelete(boolean delete)
    {
        isDelete = delete;
    }
}
