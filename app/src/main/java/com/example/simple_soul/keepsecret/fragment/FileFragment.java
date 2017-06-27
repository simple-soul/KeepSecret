package com.example.simple_soul.keepsecret.fragment;

import android.app.AlertDialog;
import android.os.Environment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simple_soul.keepsecret.R;
import com.example.simple_soul.keepsecret.adapter.FileListAdapter;
import com.example.simple_soul.keepsecret.custom.MyScrollView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by simple_soul on 2017/5/21.
 */

public class FileFragment extends BackFragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, View.OnClickListener, FileListAdapter.onCheckCallBack
{
    private MyScrollView scrollView;
    private ListView listView;
    private CardView cardView;
    private Button lock, all;

    private static final String TAG = "FileFragment";

    private boolean isExit = false;
    private List<String> pathList = new ArrayList<>();
    private List<TextView> viewList = new ArrayList<>();
    private List<Integer> checkList = new ArrayList<>();
    private FileListAdapter fileListAdapter;
    private File[] files;
    private boolean isVisibility = false;
    private boolean showSelectAll = true;

    @Override
    public View initView()
    {
        View view = View.inflate(mActivity, R.layout.fragment_file, null);

        scrollView = (MyScrollView) view.findViewById(R.id.file_scroll);
        listView = (ListView) view.findViewById(R.id.file_list);
        cardView = (CardView) view.findViewById(R.id.file_card);
        lock = (Button) view.findViewById(R.id.file_btn_lock);
        all = (Button) view.findViewById(R.id.file_btn_all);

        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);
        lock.setOnClickListener(this);
        all.setOnClickListener(this);

        return view;
    }

    @Override
    public void initData()
    {
        // 1.获取内置存储卡和SD卡，显示列表
        initDefaultList();

        // 2.点击列表进入文件夹
//        内置SD卡：/storage/emulated/0

        // 3.返回键回退上一级，点击目录可以跳转

        // 4.长按可以多选，选项中出现全选

    }

    private void initDefaultList()
    {
        scrollView.removeAllViews();
        viewList.clear();
        pathList.clear();
        notifyData("/storage");
    }

    private void notifyDataAndView(int index)
    {
        notifyData(pathList.get(index));
        notifyTextViews(index);
    }

    private void notifyData(String path)
    {
        File file = new File(path);

        files = file.listFiles();
        if (files == null)
        {
            files = new File[0];
        }
        if (fileListAdapter == null)
        {
            fileListAdapter = new FileListAdapter(mActivity, this, files);
            listView.setAdapter(fileListAdapter);
        }
        else
        {
            fileListAdapter.notifyData(files);
        }
    }

    private void notifyTextViews(int index)
    {
        for (int i = viewList.size() - 1; i > index; i--)
        {
            scrollView.removeView(viewList.get(i));
            viewList.remove(i);
            pathList.remove(i);
        }
    }

    @Override
    public boolean onBackPressed()
    {
        if (!isVisibility)
        {
            if (pathList.size() <= 0)
            {
                isExit = true;
            }
            else
            {
                isExit = false;
            }
            if (isExit)
            {
                Log.i(TAG, "给activity了pathList的length=" + pathList.size());
                return false;
            }
            else
            {
                if (pathList.size() == 1)
                {
                    initDefaultList();
                }
                else
                {
                    notifyDataAndView(viewList.size() - 2);
                    Log.i(TAG, "file拿到了pathList的length=" + pathList.size());
                }
                return true;
            }
        }
        else
        {
            fileListAdapter.setVisibility(false);
            isVisibility = false;
            cardView.setVisibility(View.GONE);
            return true;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        if(isVisibility)
        {
            Log.i("main", "点击了");
            FileListAdapter.ViewHolder viewHolder= (FileListAdapter.ViewHolder) view.getTag();
            viewHolder.checkBox.toggle();
            fileListAdapter.setCheckChanged(position);
        }
        else
        {
            TextView textView = new TextView(mActivity);
            File file = files[position];
            if (file.getName().equals("emulated"))
            {
                pathList.add(Environment.getExternalStorageDirectory().getPath());
                textView.setText("系统空间");
                notifyData(Environment.getExternalStorageDirectory().getPath());
            }
            else
            {
                pathList.add(file.getPath());
                textView.setText("/" + file.getName() + " ");
                notifyData(file.getPath());
            }
            textView.setOnClickListener(this);
            textView.setTag(viewList.size());
            viewList.add(textView);
            scrollView.addView(textView);
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
    {
        Log.i(TAG, "LongClick");
        if (!isVisibility)
        {
            fileListAdapter.setVisibility(true);
            cardView.setVisibility(View.VISIBLE);
            isVisibility = true;
        }
        return true;
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.file_btn_lock:
                if(checkList.size() == 0)
                {
                    Toast.makeText(mActivity, "请至少选择一个文件", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    showDialog();
                }
                break;

            case R.id.file_btn_all:
                fileListAdapter.setCheckedAllOrNull(showSelectAll);
                break;

            default:
                notifyDataAndView((Integer) v.getTag());
        }
    }

    private void showDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        View view = View.inflate(mActivity, R.layout.dialog_item, null);
        ListView list = (ListView) view.findViewById(R.id.dialog_list);
        list.setAdapter(new ArrayAdapter<String>(mActivity, android.R.layout.simple_list_item_1, new String[]{"一级加密"}));
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                switch (position)
                {
                    case 0:

                        break;
                }
            }
        });
        builder.setView(view);
        AlertDialog dialog = builder.create();
        Window window = dialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();

        params.width = 100;
        window.setAttributes(params);
        dialog.show();
    }

    @Override
    public void checkList(List<Integer> checkList)
    {
        this.checkList = checkList;
    }

    @Override
    public void allChecked()
    {
        all.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_clear, 0, 0);
        all.setText("取消全选");
        showSelectAll = false;
    }

    @Override
    public void allClear()
    {
        all.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_check, 0, 0);
        all.setText("全选");
        showSelectAll = true;
    }
}
