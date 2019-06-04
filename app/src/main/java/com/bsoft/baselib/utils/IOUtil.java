package com.bsoft.baselib.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * Author by wangzhaox,
 * Email wangzhaox@bsoft.com.cn,
 * Date on 2019/4/18.
 * Description:数据流的辅助工具类
 * PS: Not easy to write code, please indicate.
 */
public  final class IOUtil {


    /**
     * 关闭数据流
     * @param closeables
     */
    public static void close(Closeable... closeables){
        for (Closeable closeable:closeables) {
            if(null!=closeable){
                try{
                    closeable.close();
                }catch (IOException e){

                }
            }
        }
    }
}

