package com.bsoft.baselib.http.request.retrofit.upload;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;


import com.bsoft.baselib.http.entity.ProgressInfo;
import com.bsoft.baselib.http.request.listener.OnLoadProgressListener;

import java.io.IOException;

import io.reactivex.ObservableEmitter;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/**
 * Author by wangzhaox,
 * Email wangzhaox@bsoft.com.cn,
 * Date on 2019/4/23.
 * Description:对上传文件的扩展，可以监听文件上传进度。
 * PS: Not easy to write code, please indicate.
 */
public class ExMultipartBody extends RequestBody {
    private RequestBody mRequestBody;
    private OnLoadProgressListener mProgressListener;
    private BufferedSink mBufferedSink;
    private final Handler mHandler;
    private ObservableEmitter<ProgressInfo> mEmitter;
    private int mRefreshTime;//回调进度的时间间隔（单位是ms）


    public ExMultipartBody(RequestBody requestBody) {
        this(requestBody, null, null);

    }

    public ExMultipartBody(RequestBody requestBody, ObservableEmitter<ProgressInfo> emitter) {
        this(requestBody, null, emitter);

    }

    public ExMultipartBody(RequestBody requestBody, OnLoadProgressListener progressListener) {
        this(requestBody, progressListener, null);

    }

    public ExMultipartBody(RequestBody requestBody, OnLoadProgressListener progressListener, ObservableEmitter<ProgressInfo> emitter) {
        mRequestBody = requestBody;
        mProgressListener = progressListener;
        mHandler = new Handler(Looper.getMainLooper());
        mEmitter = emitter;
        mRefreshTime = 1000;

    }

    @Override
    public MediaType contentType() {
        return mRequestBody.contentType();
    }

    @Override
    public long contentLength() throws IOException {
        return mRequestBody.contentLength();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        if (mBufferedSink == null) {
            mBufferedSink = Okio.buffer(new ProgressSink(sink));
        }
        try {
            mRequestBody.writeTo(mBufferedSink);
            mBufferedSink.flush();
        } catch (IOException e) {
            if (mProgressListener != null) {
                mProgressListener.onFailure(e);
            }
            if (mEmitter != null && !mEmitter.isDisposed()) {
                mEmitter.onError(e);
            }
            throw e;
        }
    }

    /**
     * 对sink进行扩展，获取读取数据的时候每次读取的长度
     */
    private class ProgressSink extends ForwardingSink {
        private long totalBytesWrite;//上传的数据长度
        private long recordTime;//刷新进度后记录上次的时间
        private final ProgressInfo mProgressInfo;
        private long intervalBytesWrite;  //当前刷新和上次刷新间隔时间内上传的字节数

        public ProgressSink(Sink delegate) {
            super(delegate);
            mProgressInfo = new ProgressInfo();
        }

        @Override
        public void write(Buffer source, long byteCount) throws IOException {
            super.write(source, byteCount);
            if (mProgressInfo.getTotalSize() == 0) {
                mProgressInfo.setTotalSize(contentLength());
            }
            totalBytesWrite += byteCount;
            intervalBytesWrite += byteCount;
            long curTime = SystemClock.elapsedRealtime();
            if (curTime - recordTime >= mRefreshTime || totalBytesWrite == mProgressInfo.getTotalSize()) {
                //需要更新进度(默认至少1000ms更新一次)
                mProgressInfo.setCurrentSize(totalBytesWrite);
                mProgressInfo.setIntervalBytes(intervalBytesWrite);
                mProgressInfo.setIntervalTime(curTime - recordTime);
                if (mProgressListener != null) {
                    final ProgressInfo finalProgressInfo = mProgressInfo;
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            // Runnable 里的代码是在主线程的,外面代码可能执行在其他线程
                            // 所以必须使用 final ,保证在 Runnable 执行前使用到的进度,在执行时不会被修改
                            mProgressListener.onProgress(finalProgressInfo);
                        }
                    });
                }
                if (mEmitter != null && !mEmitter.isDisposed()) {
                    mEmitter.onNext(mProgressInfo);
                }
                recordTime = curTime;
                intervalBytesWrite = 0;
            }
        }
    }

}
