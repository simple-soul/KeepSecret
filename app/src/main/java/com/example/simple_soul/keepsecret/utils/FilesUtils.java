package com.example.simple_soul.keepsecret.utils;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by simple_soul on 2017/5/20.
 */

public class FilesUtils
{
    public static final String FILE_TYPE_FOLDER = "wFl2d";

    public static final String FILE_INFO_NAME = "fName";
    public static final String FILE_INFO_ISFOLDER = "fIsDir";
    public static final String FILE_INFO_TYPE = "fFileType";
    public static final String FILE_INFO_NUM_SONDIRS = "fSonDirs";
    public static final String FILE_INFO_NUM_SONFILES = "fSonFiles";
    public static final String FILE_INFO_PATH = "fPath";

    public CallBackLockNum callback;

    public FilesUtils(CallBackLockNum callback)
    {
        this.callback = callback;
    }

    /**
     * 获取文件path文件夹下的文件列表
     *
     * @param path 手机上的文件夹
     * @return path文件夹下的文件列表的信息，信息存储在Map中，Map的key的列表如下：<br />
     * FILE_INFO_NAME : String 文件名称 <br />
     * FILE_INFO_ISFOLDER: boolean 是否为文件夹   <br />
     * FILE_INFO_TYPE: string 文件的后缀 <br />
     * FILE_INFO_NUM_SONDIRS : int 子文件夹个数   <br />
     * FILE_INFO_NUM_SONFILES: int 子文件个数    <br />
     * FILE_INFO_PATH : String 文件的绝对路径  <br />
     * @see #getSonNode(String)
     **/
    public static List<Map<String, Object>> getSonNode(File path)
    {
        if (path.isDirectory())
        {
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            File[] files = path.listFiles();
            if (files != null)
            {

                for (int i = 0; i < files.length; i++)
                {
                    Map<String, Object> fileInfo = new HashMap<String, Object>();
                    fileInfo.put(FILE_INFO_NAME, files[i].getName());
                    if (files[i].isDirectory())
                    {
                        fileInfo.put(FILE_INFO_ISFOLDER, true);
                        File[] bFiles = files[i].listFiles();
                        if (bFiles == null)
                        {
                            fileInfo.put(FILE_INFO_NUM_SONDIRS, 0);
                            fileInfo.put(FILE_INFO_NUM_SONFILES, 0);
                        }
                        else
                        {
                            int getNumOfDir = 0;
                            for (int j = 0; j < bFiles.length; j++)
                            {
                                if (bFiles[j].isDirectory())
                                {
                                    getNumOfDir++;
                                }
                            }
                            fileInfo.put(FILE_INFO_NUM_SONDIRS, getNumOfDir);
                            fileInfo.put(FILE_INFO_NUM_SONFILES, bFiles.length - getNumOfDir);
                        }
                        fileInfo.put(FILE_INFO_TYPE, FILE_TYPE_FOLDER);
                    }
                    else
                    {
                        fileInfo.put(FILE_INFO_ISFOLDER, false);
                        fileInfo.put(FILE_INFO_NUM_SONDIRS, 0);
                        fileInfo.put(FILE_INFO_NUM_SONFILES, 0);
                        fileInfo.put(FILE_INFO_TYPE, getFileType(files[i].getName()));
                    }
                    fileInfo.put(FILE_INFO_PATH, files[i].getAbsoluteFile());
                    list.add(fileInfo);
                }
                return list;
            }
            else
            {
                return null;
            }
        }
        else
        {
            return null;
        }
    }

    /**
     * 获取文件pathStr文件夹下的文件列表
     *
     * @param pathStr 手机上的文件夹的绝对路径
     * @return pathStr文件夹下的文件列表的信息，信息存储在Map中，Map的key的列表如下：<br />
     * FILE_INFO_NAME : String 文件名称 <br />
     * FILE_INFO_ISFOLDER: boolean 是否为文件夹   <br />
     * FILE_INFO_TYPE: string 文件的后缀 <br />
     * FILE_INFO_NUM_SONDIRS : int 子文件夹个数   <br />
     * FILE_INFO_NUM_SONFILES: int 子文件个数    <br />
     * FILE_INFO_PATH : String 文件的绝对路径  <br />
     * @see #getSonNode(File)
     **/
    public static List<Map<String, Object>> getSonNode(String pathStr)
    {
        File path = new File(pathStr);
        return getSonNode(path);
    }

    /**
     * 获取文件path文件或文件夹的兄弟节点文件列表
     *
     * @param path 手机上的文件夹
     * @return path文件夹下的文件列表的信息，信息存储在Map中，Map的key的列表如下：<br />
     * FILE_INFO_NAME : String 文件名称 <br />
     * FILE_INFO_ISFOLDER: boolean 是否为文件夹   <br />
     * FILE_INFO_TYPE: string 文件的后缀 <br />
     * FILE_INFO_NUM_SONDIRS : int 子文件夹个数   <br />
     * FILE_INFO_NUM_SONFILES: int 子文件个数    <br />
     * FILE_INFO_PATH : String 文件的绝对路径  <br />
     * @see #getBrotherNode(String)
     **/
    public static List<Map<String, Object>> getBrotherNode(File path)
    {
        if (path.getParentFile() != null)
        {
            return getSonNode(path.getParentFile());
        }
        else
        {
            return null;
        }
    }

    /**
     * 获取文件path文件或文件夹的兄弟节点文件列表
     *
     * @param pathStr 手机上的文件夹
     * @return path文件夹下的文件列表的信息，信息存储在Map中，Map的key的列表如下：<br />
     * FILE_INFO_NAME : String 文件名称 <br />
     * FILE_INFO_ISFOLDER: boolean 是否为文件夹   <br />
     * FILE_INFO_TYPE: string 文件的后缀 <br />
     * FILE_INFO_NUM_SONDIRS : int 子文件夹个数   <br />
     * FILE_INFO_NUM_SONFILES: int 子文件个数    <br />
     * FILE_INFO_PATH : String 文件的绝对路径  <br />
     * @see #getBrotherNode(File)
     **/
    public static List<Map<String, Object>> getBrotherNode(String pathStr)
    {
        File path = new File(pathStr);
        return getBrotherNode(path);
    }

    /**
     * 获取文件或文件夹的父路径
     *
     * @param path 文件或者文件夹
     * @return String path的父路径
     **/
    public static String getParentPath(File path)
    {
        if (path.getParentFile() == null)
        {
            return null;
        }
        else
        {
            return path.getParent();
        }
    }

    /**
     * 获取文件或文件的父路径
     *
     * @param pathStr 文件或者文件夹路径
     * @return String pathStr的父路径
     **/
    public static String getParentPath(String pathStr)
    {
        File path = new File(pathStr);
        if (path.getParentFile() == null)
        {
            return null;
        }
        else
        {
            return path.getParent();
        }
    }

    /**
     * 获取sd卡的绝对路径
     *
     * @return String 如果sd卡存在，返回sd卡的绝对路径，否则返回null
     **/
    public static String getSDPath()
    {
        String sdcard = Environment.getExternalStorageState();
        if (sdcard.equals(Environment.MEDIA_MOUNTED))
        {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        else
        {
            return null;
        }
    }

    /**
     * 获取一个基本的路径，一般应用创建存放应用数据可以用到
     *
     * @return String 如果SD卡存在，返回SD卡的绝对路径，如果SD卡不存在，返回Android数据目录的绝对路径
     **/
    public static String getBasePath()
    {
        String basePath = getSDPath();
        if (basePath == null)
        {
            return Environment.getDataDirectory().getAbsolutePath();
        }
        else
        {
            return basePath;
        }
    }

    /**
     * 获取文件path的大小
     *
     * @return String path的大小
     **/
    public static String getFileSize(File path) throws IOException
    {
        if (path.exists())
        {
            DecimalFormat df = new DecimalFormat("#.00");
            String sizeStr = "";
            FileInputStream fis = new FileInputStream(path);
            long size = fis.available();
            fis.close();
            if (size < 1024)
            {
                sizeStr = size + "B";
            }
            else if (size < 1048576)
            {
                sizeStr = df.format(size / (double) 1024) + "KB";
            }
            else if (size < 1073741824)
            {
                sizeStr = df.format(size / (double) 1048576) + "MB";
            }
            else
            {
                sizeStr = df.format(size / (double) 1073741824) + "GB";
            }
            return sizeStr;
        }
        else
        {
            return null;
        }
    }

    /**
     * 获取文件fpath的大小
     *
     * @return String path的大小
     **/
    public static String getFileSize(String fpath)
    {
        File path = new File(fpath);
        if (path.exists())
        {
            DecimalFormat df = new DecimalFormat("#.00");
            String sizeStr = "";
            long size = 0;
            try
            {
                FileInputStream fis = new FileInputStream(path);
                size = fis.available();
                fis.close();
            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
                return "未知大小";
            }
            catch (IOException e)
            {
                e.printStackTrace();
                return "未知大小";
            }
            if (size < 1024)
            {
                sizeStr = size + "B";
            }
            else if (size < 1048576)
            {
                sizeStr = df.format(size / (double) 1024) + "KB";
            }
            else if (size < 1073741824)
            {
                sizeStr = df.format(size / (double) 1048576) + "MB";
            }
            else
            {
                sizeStr = df.format(size / (double) 1073741824) + "GB";
            }
            return sizeStr;
        }
        else
        {
            return "未知大小";
        }
    }

    /**
     * 根据后缀获取文件fileName的类型
     *
     * @return String 文件的类型
     **/
    public static String getFileType(String fileName)
    {
        if (fileName != "" && fileName.length() > 3)
        {
            int dot = fileName.lastIndexOf(".");
            if (dot > 0)
            {
                return fileName.substring(dot + 1);
            }
            else
            {
                return "";
            }
        }
        return "";
    }

    public static Comparator<Map<String, Object>> defaultOrder()
    {
        final String orderBy0 = FILE_INFO_ISFOLDER;
        final String orderBy1 = FILE_INFO_TYPE;
        final String orderBy2 = FILE_INFO_NAME;

        Comparator<Map<String, Object>> order = new Comparator<Map<String, Object>>()
        {

            @Override
            public int compare(Map<String, Object> lhs, Map<String, Object> rhs)
            {
                int left0 = lhs.get(orderBy0).equals(true) ? 0 : 1;
                int right0 = rhs.get(orderBy0).equals(true) ? 0 : 1;
                if (left0 == right0)
                {
                    String left1 = lhs.get(orderBy1).toString();
                    String right1 = rhs.get(orderBy1).toString();
                    if (left1.compareTo(right1) == 0)
                    {
                        String left2 = lhs.get(orderBy2).toString();
                        String right2 = rhs.get(orderBy2).toString();
                        return left2.compareTo(right2);
                    }
                    else
                    {
                        return left1.compareTo(right1);
                    }
                }
                else
                {
                    return left0 - right0;
                }
            }
        };

        return order;
    }

    /**
     * 将long型的文件字节大小转换为合适单位的字符串表示
     *
     * @param fileS long型的数值
     * @return String类型的字符串
     */
    public static String formatFileSize(long fileS)
    {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        String wrongSize = "0B";
        if (fileS == 0)
        {
            return wrongSize;
        }
        if (fileS < 1024)
        {
            fileSizeString = df.format((double) fileS) + "B";
        }
        else if (fileS < 1048576)
        {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        }
        else if (fileS < 1073741824)
        {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        }
        else
        {
            fileSizeString = df.format((double) fileS / 1073741824) + "GB";
        }
        return fileSizeString;
    }

    /**
     * 获得文件或文件夹总大小
     *
     * @return String类型的大小
     */
    public static String getTotalSize(String path)
    {
        StatFs stat = new StatFs(path);
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return formatFileSize(blockSize * totalBlocks);
    }

    public static String getTotalSize(File file)
    {
        StatFs stat = new StatFs(file.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return formatFileSize(blockSize * totalBlocks);
    }

    /**
     * 获得剩余容量，即可用大小
     *
     * @return String类型的大小
     */
    public static String getAvailableSize(String path)
    {
        StatFs stat = new StatFs(path);
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return formatFileSize(blockSize * availableBlocks);
    }

    public static String getAvailableSize(File file)
    {
        StatFs stat = new StatFs(file.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return formatFileSize(blockSize * availableBlocks);
    }

    /**
     * 得到当前文件夹下的文件夹数量
     *
     * @param file 文件夹对象
     * @return 当前文件夹下的文件夹数量
     */
    public static int getDirectoryNum(File file)
    {
        int num = 0;
        for (File item:file.listFiles())
        {
            if(item.isDirectory())
            {
                num++;
            }
        }
        return num;
    }

    /**
     * 随机生成对应位数的字符串(生成的元素在a-z,A-Z,0-9之内)
     *
     * @return 生成的字符串
     */
    public static String getRandomString() { //length表示生成字符串的长度
        String base = "QWERTYUIOPLKJHGFDSAZXCVBNMqwertyuioplkjhgfdsazxcvbnm0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 20; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 一级加密，修改名字和后缀名
     *
     * @param files 需要加密的file文件，可以是文件或文件夹
     */
    public void lock(Context context, String safe, List<File> files)
    {
        for (int i = 0; i < files.size(); i++)
        {
            File file = files.get(i);
            String path = file.getParent();
            if(file.isDirectory())
            {
                for(File item:file.listFiles())
                {
                    List<File> fs = new ArrayList<>();
                    fs.add(item);
                    lock(context, safe, fs);
                }
            }
            else
            {
                String oldName = file.getAbsolutePath();
                String newName = path+"\\"+getRandomString()+".ks";
                PreUtils.setString(context, safe, newName, oldName);
                file.renameTo(new File(newName));
                callback.complete();
            }
        }

    }

    public void unlock(Context context, String safe, List<File> files)
    {
        for (int i = 0; i < files.size(); i++)
        {
            File file = files.get(i);
            if (file.isDirectory())
            {
                for (File item : file.listFiles())
                {
                    List<File> fs = new ArrayList<>();
                    fs.add(item);
                    unlock(context, safe, fs);
                }
            }
            else
            {
                String oldName = PreUtils.getString(context, safe, file.getAbsolutePath(),
                        file.getAbsolutePath());
                file.renameTo(new File(oldName));
                callback.complete();
            }
        }
    }

    interface CallBackLockNum
    {
        public void complete();
    }
}
