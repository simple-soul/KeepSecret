package com.example.simple_soul.keepsecret.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.simple_soul.keepsecret.R;
import com.example.simple_soul.keepsecret.utils.FilesUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by simple_soul on 2017/5/22.
 */

public class FileListAdapter extends BaseAdapter
{
    private Context context;
    private File[] fileList;
    private List<Boolean> checkBoxList;
    private boolean isVisibility = false;
    private onCheckCallBack callBack;
    private List<Integer> checkList = new ArrayList<>();

    public FileListAdapter(Context context, Fragment fragment, File[] fileList)
    {
        this.context = context;
        this.fileList = fileList;
        callBack = (onCheckCallBack) fragment;
        checkBoxList = new ArrayList<>();
        for (int i = 0; i < fileList.length; i++)
        {
            checkBoxList.add(false);
        }
    }

    @Override
    public int getCount()
    {
        return fileList.length;
    }

    @Override
    public Object getItem(int position)
    {
        return fileList[position];
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder viewHolder = null;
        if (convertView == null)
        {
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.listitem_folder, null);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.folder_img);
            viewHolder.title = (TextView) convertView.findViewById(R.id.folder_name);
            viewHolder.info = (TextView) convertView.findViewById(R.id.folder_info);
            viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.folder_check);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        File file = fileList[position];
        if (file.getParentFile().getName().equals("storage"))
        {
            viewHolder.image.setImageResource(R.drawable.bootcamp);
            viewHolder.info.setText("总计:" + FilesUtils.getTotalSize(
                    file.getPath()) + " 可用:" + FilesUtils.getAvailableSize(file.getPath()));
            if (file.getName().equals("emulated"))
            {
                viewHolder.title.setText("系统空间");
            }
            else
            {
                viewHolder.title.setText(file.getName());
            }
        }
        else
        {
            viewHolder.title.setText(file.getName());
            if (file.isDirectory())
            {
                viewHolder.image.setImageResource(R.drawable.folder);
                viewHolder.info.setText("文件：" + (file.list().length - FilesUtils.getDirectoryNum(
                        file)) + "，文件夹：" + FilesUtils.getDirectoryNum(file));
            }
            else
            {
                checkType(FilesUtils.getFileType(file.getName()), viewHolder.image);
                viewHolder.info.setText("大小：" + FilesUtils.getTotalSize(file.getPath()));
            }

            if (isVisibility)
            {
                viewHolder.checkBox.setVisibility(View.VISIBLE);
            }
            else
            {
                viewHolder.checkBox.setVisibility(View.INVISIBLE);
            }
            viewHolder.checkBox.setChecked(checkBoxList.get(position));
        }
        return convertView;
    }

    private void checkType(String type, ImageView image)
    {
        if (type.equals("text") || type.equals("txt"))
        {
            image.setImageResource(R.drawable.text);
        }
        else if (type.equals("mkv") || type.equals("mp4") || type.equals("avi") || type.equals(
                "rmvb") || type.equals("wmv") || type.equals("rm") || type.equals("3gp"))
        {
            image.setImageResource(R.drawable.video);
        }
        else if (type.equals("mp3") || type.equals("wav") || type.equals("wmv") || type.equals(
                "ogg") || type.equals("ape") || type.equals("acc"))
        {
            image.setImageResource(R.drawable.music);
        }
        else
        {
            image.setImageResource(R.drawable.unknow);
        }
    }

    public void setVisibility(boolean isVisibility)
    {
        this.isVisibility = isVisibility;
        notifyDataSetChanged();
    }

    public void notifyData(File[] fileList)
    {
        this.fileList = fileList;
        checkBoxList.clear();
        for (int i = 0; i < fileList.length; i++)
        {
            checkBoxList.add(false);
        }
        notifyDataSetChanged();
    }

    public void setCheckChanged(int pos)
    {
        if (checkBoxList.get(pos))
        {
            checkBoxList.set(pos, false);
        }
        else
        {
            checkBoxList.set(pos, true);
        }
        check();
    }

    private void check()
    {
        int t = 0;
        checkList.clear();
        for (int i = 0; i < checkBoxList.size(); i++)
        {
            if (checkBoxList.get(i))
            {
                t++;
                checkList.add(i);
            }
        }
        callBack.checkList(checkList);

        if (t == checkBoxList.size())
        {
            callBack.allChecked();
            Log.i("main", "allChecked");
        }
        else
        {
            callBack.allClear();
            Log.i("main", "allClear");
        }
    }

    public void setCheckedAllOrNull(boolean isAll)
    {
        for (int i = 0; i < checkBoxList.size(); i++)
        {
            checkBoxList.set(i, isAll);
        }
        check();
        notifyDataSetChanged();
    }

    public interface onCheckCallBack
    {
        void checkList(List<Integer> checkList);

        void allChecked();

        void allClear();
    }

    public class ViewHolder
    {
        public ImageView image;
        public TextView title, info;
        public CheckBox checkBox;
    }
}
