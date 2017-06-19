package com.example.simple_soul.keepsecret.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Created by simple_soul on 2017/5/31.
 */
@DatabaseTable(tableName = "folder")
public class Folder
{
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(columnName = "name")
    private String name;
    @DatabaseField(columnName = "path")
    private String path;
    @DatabaseField(columnName = "belong")
    private int belong;
    @DatabaseField(columnName = "isLock")
    private boolean isLock = false;
    @DatabaseField(columnName = "time")
    private Date time = new Date();

    public Folder(String name, String path, int belong)
    {
        this.name = name;
        this.path = path;
        this.belong = belong;
    }

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public String getPath()
    {
        return path;
    }

    public int getBelong()
    {
        return belong;
    }

    public boolean isLock()
    {
        return isLock;
    }

    public Date getTime()
    {
        return time;
    }

}
