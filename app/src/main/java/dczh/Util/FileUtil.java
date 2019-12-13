package dczh.Util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Comparator;


public class FileUtil
{
  private final static String TAG = "FileUtil";


  public static boolean checkSD()
  {
    return Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
  }

  public static String getSDRootPath()
  {
    if (!checkSD())
    {
      return null;
    }
    else
    {
      return Environment.getExternalStorageDirectory().getPath();
    }
  }

  public static boolean makeDir(String dirPath)
  {
    boolean isSuccessful = true;
    File dir = new File(dirPath);
    if (!dir.exists())
    {
      isSuccessful = dir.mkdirs();
    }
    return isSuccessful;
  }

  public static boolean saveBitmapInJPG(String filePath, Bitmap bitmap)
  {
    File f = new File(filePath);
    if (f.exists())
    {
      return true;
    }
    try
    {
      f.createNewFile();
      FileOutputStream fOut = null;
      fOut = new FileOutputStream(f);
      bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fOut);
      fOut.flush();
      fOut.close();
    }
    catch (FileNotFoundException e)
    {
      e.printStackTrace();
      return false;
    }
    catch (IOException e)
    {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  static class CompratorByLastModified implements Comparator<File>
  {
    public int compare(File f1, File f2)
    {
      long diff = f1.lastModified() - f2.lastModified();
      if (diff > 0)
      {
        return -1;
      }
      else if (diff == 0)
      {
        return 0;
      }
      else
      {
        return 1;
      }
    }
  }

  public static Bitmap getThumbnail(String path, int width, int height)
  {
    Bitmap bitmap = null;
    Bitmap thumbnail = null;

    try
    {
      BitmapFactory.Options options = new BitmapFactory.Options();
      options.inSampleSize = 8;
      bitmap = BitmapFactory.decodeFile(path, options);
      thumbnail = ThumbnailUtils.extractThumbnail(bitmap, width, height);
      bitmap.recycle();
    }
    catch (Exception e)
    {
      e.printStackTrace();
      return null;
    }

    return thumbnail;
  }

  public static void delDirectFiles(String path)
  {
    File file = new File(path);
    if (!file.exists())
    {
      return;
    }
    File[] files = file.listFiles();
    for (int i = 0; i < files.length; i++)
    {
      files[i].delete();
    }
  }

  public static void delFiles(String path)
  {
    File file = new File(path);
    if (!file.exists())
    {
      return;
    }
    file.delete();
  }

  public static boolean isFileExist(String path)
  {
    File file = new File(path);
    return file.exists();
  }

  public static boolean isFileEmpty(String path)
  {
    File file = new File(path);
    if (file.exists())
    {
      if (file.length() == 0)
      {
        return true;
      }
    }
    else
    {
      return true;
    }
    return false;
  }







}
