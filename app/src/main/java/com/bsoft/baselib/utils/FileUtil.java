package com.bsoft.baselib.utils;

import android.text.TextUtils;

import java.net.FileNameMap;
import java.net.URLConnection;

/**
 * Author by wangzhaox,
 * Email wangzhaox@bsoft.com.cn,
 * Date on 2019/4/22.
 * Description:获取文件类型MIME（content—type）
 * PS: Not easy to write code, please indicate.
 */
public class FileUtil {
    public static String getMIME(String filePath){
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String mime = fileNameMap.getContentTypeFor(filePath);
        if(TextUtils.isEmpty(mime)){
            return  "application/octet-stream";
        }
        return mime;
    }
}
