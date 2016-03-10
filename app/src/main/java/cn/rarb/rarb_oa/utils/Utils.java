package cn.rarb.rarb_oa.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import java.io.File;
import cn.rarb.rarb_oa.app.MyAppliction;

/**
 * Created by wenhuaijun on 2015/11/7 0007.
 */
public class Utils {
    public static String getSDPath(){
        File sdDir;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED); //判断sd卡是否存在
        if (sdCardExist)
        {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        }else {
            sdDir =  MyAppliction.getInstance().getFilesDir();
        }
        return sdDir.toString();
    }

    //分享图片
    public  static void  startShareImg(String path,Context context){
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/*");
        File file = new File(path);
        Uri uri = Uri.fromFile(file);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        context.startActivity(Intent.createChooser(shareIntent, "请选择"));
    }

}
