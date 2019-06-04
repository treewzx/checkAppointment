package com.bsoft.baselib.http.request.listener;

import com.bsoft.baselib.http.entity.ProgressInfo;

/**
 * Author by wangzhaox,
 * Email wangzhaox@bsoft.com.cn,
 * Date on 2019/4/18.
 * Description:网络上传和下载的进度监听器
 * PS: Not easy to write code, please indicate.
 */
public interface OnLoadProgressListener {
    /**
     * 上传和下载的进度监听
     * @param progressInfo
     */
    void onProgress(ProgressInfo progressInfo);

    /**
     * 文件上传下载失败
     */
    void onFailure(Throwable e);


    public OnLoadProgressListener DEFAULT_LISTENER = new OnLoadProgressListener() {


        @Override
        public void onProgress(ProgressInfo progressInfo) {

        }

        @Override
        public void onFailure(Throwable e) {

        }
    };
}
