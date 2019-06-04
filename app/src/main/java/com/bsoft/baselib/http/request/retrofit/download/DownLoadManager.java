package com.bsoft.baselib.http.request.retrofit.download;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;

import com.bsoft.baselib.http.HttpEnginerConfig;
import com.bsoft.baselib.http.entity.ProgressInfo;
import com.bsoft.baselib.http.exception.HttpException;
import com.bsoft.baselib.http.request.listener.OnLoadProgressListener;
import com.bsoft.baselib.http.request.retrofit.RetrofitClient;
import com.bsoft.baselib.utils.IOUtil;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import io.reactivex.plugins.RxJavaPlugins;
import okhttp3.ResponseBody;
import okio.BufferedSource;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Author by wangzhaox,
 * Email wangzhaox@bsoft.com.cn,
 * Date on 2019/4/16.
 * Description:文件下载
 * PS: Not easy to write code, please indicate.
 */
public class DownLoadManager {
    private Context mContext;
    private SharedPreferences mSharedPreferences;
    private long mFileCurrentLen;//文件的当前长度
    private long mFileTotalLen;//文件的总长度
    private Call<ResponseBody> mCall;
    private long recordTime; //刷新进度后记录上次的时间
    private String mLastModify;//文件最后修改时间
    private long mRefreshTime;//回调间隔时间
    private Handler mHandler;


    public DownLoadManager(Context context) {
        mContext = context;
        mSharedPreferences = mContext.getSharedPreferences("saveFileInfo", Context.MODE_PRIVATE);
        mRefreshTime = 1000;
        mHandler = new Handler(Looper.getMainLooper());

    }

    /**
     * 同步下载文件，调用的时候需要在子线程执行
     *
     * @param config
     * @param url          下载文件的url
     * @param saveRootPath 保存文件的根目录
     */
    private void downLoadSync(final HttpEnginerConfig config, final String url, String saveRootPath, String fileName, final OnLoadProgressListener listener) {
        recordTime = 0;
        final ProgressInfo progressInfo = new ProgressInfo();
        BufferedSource source = null;
        RandomAccessFile randomAccessFile = null;
        long intervalBytesRead = 0L;
        File savefile = new File(saveRootPath, fileName != null ? fileName : getFileName(url));
        mFileCurrentLen = savefile.length();
        try {
            while (true) {
                if (!savefile.exists()) {
                    savefile.createNewFile();
                }
                //1：已下载的文件长度大于0时，如果当前文件的长度大于服务端文件的长度，那么必然需要重新下载
                // 如果小于服务端文件的长度则判断已下载文件的最后修改时间是否和服务端的文件最后修改时间相同，
                //文件最后修改时间不相同，则表示服务端文件发生变化，那就需要重新开始从第一个字节下载。
                //文件最后修改时间相同，则表示服务端文件未发生变化。那就再判断已下载文件的大小是否和服务端文件大小相同
                //文件大小相同表示已下载完成，不相同则继续断点下载。
                if (mFileCurrentLen != 0) {
                    getServiceFileInfo(config, url);
                    if (mFileCurrentLen > mFileTotalLen) {
                        savefile.delete();
                        mFileCurrentLen = 0;
                        continue;
                    }
                    //缓存的文件最后修改时间和服务器端的文件最后修改时间进行比对
                    progressInfo.setCurrentSize(mFileCurrentLen);
                    progressInfo.setTotalSize(mFileTotalLen);
                    if (getCacheLastModify().equals(mLastModify)) {
                        //已下载的文件长度和通过getServiceFileInfo()获取的文件总长比对，相同则表示下载完毕
                        if (mFileCurrentLen == mFileTotalLen) {
                            if (listener != null) {
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        listener.onProgress(progressInfo);
                                    }
                                });
                            }
                            break;
                        }
                    } else {
                        //服务端文件发生变化不能断点下载，需要从新下载
                        savefile.delete();
                        mFileCurrentLen = 0;
                        continue;
                    }
                }
                //2：已下载部分的文件在服务器上没有发生变化进行断点下载，或者从新开始下载
                final Response<ResponseBody> response = RetrofitClient.getServiceApi(config).download("bytes=" + mFileCurrentLen + "-", url)
                        .execute();
                if (response.isSuccessful()) {
                    randomAccessFile = new RandomAccessFile(savefile, "rw");
                    randomAccessFile.seek(savefile.length());
                    long responseContLen = response.body().contentLength();
                    String lastModify = response.headers().get("Last-Modified");
                    source = response.body().source();
                    if (mFileCurrentLen == 0) {
                        //从第一个字节开始请求
                        mFileTotalLen = responseContLen;
                        cacheFileInfo(mFileTotalLen, lastModify);
                    }
                    progressInfo.setTotalSize(mFileTotalLen);
                    byte[] buf = new byte[8192];
                    int len = 0;
                    while ((len = source.read(buf)) != -1) {
                        randomAccessFile.write(buf, 0, len);
                        if (listener != null) {
                            mFileCurrentLen += len;
                            intervalBytesRead += len;
                            //处理进度,不能过度回调,默认至少1000ms调用一次
                            long curTime = SystemClock.elapsedRealtime();
                            if (curTime - recordTime > mRefreshTime || mFileCurrentLen == mFileTotalLen) {
                                progressInfo.setCurrentSize(mFileCurrentLen);
                                progressInfo.setIntervalTime(curTime - recordTime);
                                progressInfo.setIntervalBytes(intervalBytesRead);
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        listener.onProgress(progressInfo);
                                    }
                                });
                                recordTime = curTime;
                                intervalBytesRead = 0;
                            }
                        }
                    }
                } else {
                    if (listener != null) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                listener.onFailure(new HttpException(response.code(), response.message()));
                            }
                        });
                    }
                }
                break;
            }
        } catch (final IOException e) {
            if (listener != null) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onFailure(e);
                    }
                });
            }
        } finally {
            IOUtil.close(source, randomAccessFile);
        }
    }

    /**
     * 异步下载文件
     *
     * @param config
     * @param url
     * @param saveRootPath
     * @param listener     进度监听
     */
    public void downloadAsnyc(final HttpEnginerConfig config, final String url, final String saveRootPath, final String fileName, final OnLoadProgressListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                downLoadSync(config, url, saveRootPath, fileName, listener);
            }
        }).start();
    }

    /**
     * 同步下载，兼容Rxjava
     *
     * @param config
     * @param url
     * @param saveRootPath
     * @return
     */
    public Observable<ProgressInfo> downLoad(final HttpEnginerConfig config, final String url, final String saveRootPath,final String fileName) {
        return Observable.create(new ObservableOnSubscribe<ProgressInfo>() {
            @Override
            public void subscribe(ObservableEmitter<ProgressInfo> emitter) throws Exception {
                ProgressInfo progressInfo = new ProgressInfo();
                BufferedSource source = null;
                RandomAccessFile randomAccessFile = null;
                long intervalBytesRead = 0L;
                File savefile = new File(saveRootPath, fileName!=null?fileName:getFileName(url));
                mFileCurrentLen = savefile.length();
                try {
                    while (true) {
                        if (!savefile.exists()) {
                            savefile.createNewFile();
                        }
                        //1：已下载的文件长度大于0时，如果当前文件的长度大于服务端文件的长度，那么必然需要重新下载
                        // 如果小于服务端文件的长度则判断已下载文件的最后修改时间是否和服务端的文件最后修改时间相同，
                        //文件最后修改时间不相同，则表示服务端文件发生变化，那就需要重新开始从第一个字节下载。
                        //文件最后修改时间相同，则表示服务端文件未发生变化。那就再判断已下载文件的大小是否和服务端文件大小相同
                        //文件大小相同表示已下载完成，不相同则继续断点下载。
                        if (mFileCurrentLen != 0) {
                            getServiceFileInfo(config, url);
                            if (mFileCurrentLen > mFileTotalLen) {
                                savefile.delete();
                                mFileCurrentLen = 0;
                                continue;
                            }
                            if (getCacheLastModify().equals(mLastModify)) {
                                //文件修改时间未变化
                                if (mFileCurrentLen == mFileTotalLen) {
                                    //已下载的文件长度和获取的文件总长一致表示已经下载完毕
                                    progressInfo.setCurrentSize(mFileCurrentLen);
                                    progressInfo.setTotalSize(mFileTotalLen);
                                    emitter.onNext(progressInfo);
                                    emitter.onComplete();
                                    break;
                                }
                            } else {
                                //文件发生变化不能断点下载，需要从新下载
                                savefile.delete();
                                mFileCurrentLen = 0;
                                continue;
                            }
                        }
                        //2：已下载部分的文件在服务器上没有发生变化进行断点下载，或者从新开始下载
                        mCall = RetrofitClient.getServiceApi(config).download("bytes=" + mFileCurrentLen + "-", url);
                        Response<ResponseBody> mResponse = mCall.execute();
                        if (mResponse.isSuccessful()) {
                            randomAccessFile = new RandomAccessFile(savefile, "rw");
                            randomAccessFile.seek(savefile.length());
                            source = mResponse.body().source();
                            long responseContLen = mResponse.body().contentLength();
                            mLastModify = mResponse.headers().get("Last-Modified");
                            if (mFileCurrentLen == 0) {
                                //从第一个字节开始从新下载
                                mFileTotalLen = responseContLen;
                                //缓存响应的 Last-Modified头信息和文件的总长度
                                cacheFileInfo(mFileTotalLen, mLastModify);
                            }
                            progressInfo.setTotalSize(mFileTotalLen);
                            byte[] buf = new byte[8192];
                            int len = 0;
                            while ((len = source.read(buf)) != -1) {
                                randomAccessFile.write(buf, 0, len);
                                mFileCurrentLen += len;
                                intervalBytesRead += len;
                                //处理进度,不能过度回调,默认至少1000ms调用一次
                                long curTime = SystemClock.elapsedRealtime();
                                if (curTime - recordTime > mRefreshTime || mFileCurrentLen == mFileTotalLen) {
                                    progressInfo.setCurrentSize(mFileCurrentLen);
                                    progressInfo.setIntervalTime(curTime - recordTime);
                                    progressInfo.setIntervalBytes(intervalBytesRead);
                                    recordTime = curTime;
                                    intervalBytesRead = 0;
                                    if (emitter.isDisposed()) return;
                                    emitter.onNext(progressInfo);
                                }
                            }
                            if (emitter.isDisposed()) return;
                            emitter.onComplete();
                        } else {
                            if (emitter.isDisposed()) return;
                            emitter.onError(new HttpException(mResponse.code(), mResponse.message()));
                        }
                        break;
                    }

                } catch (IOException e) {
                    if (mCall != null) {
                        mCall.cancel();
                    }
                    RxJavaPlugins.setErrorHandler(new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            Log.e("TAG", "异常交给全局处理");
                        }
                    });
                    throw e;
                }
            }
        });
    }


    /**
     * 根据下载文件的url获取文件名。
     *
     * @param url
     * @return
     */
    private String getFileName(String url) {
        int index = url.lastIndexOf('/');
        if (index != -1) {
            return url.substring(index);
        }
        throw new IllegalArgumentException();
    }

    /**
     * 缓存文件的总长度信息和最后修改时间，主要是为了防止服务端文件修改了，但是文件名并未变化
     * 此时断点下载的时候就需要从新下载。
     *
     * @param responseContentLen
     * @param lastModify
     */
    public void cacheFileInfo(long responseContentLen, String lastModify) {
        //像SharedPreference中写入数据需要使用Editor
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putLong("fileLength", responseContentLen);
        editor.putString("lastModify", lastModify);
        editor.commit();

    }

    public long getCacheTotalLen() {
        return mSharedPreferences.getLong("fileLength", 0);
    }

    public String getCacheLastModify() {
        String last = mSharedPreferences.getString("lastModify", "");
        return last;
    }

    /**
     * 判断文件是否发生改变
     *
     * @param lastModify      文件的最后修改时间
     * @param responseContLen 获取响应的长度
     * @return
     */
    public boolean isFileModified(String lastModify, long responseContLen) {


        //服务器上文件没有发生变化，断点下载
        //  mFileTotalLen = getCacheTotalLen();
        //服务端的文件已发生变化，但是文件名并未改变,则删除文件从新请求 (主要根据这个Last-Modified头信息判断)
        // 此处也可以不判断（因为文件是否发生修改已进行了判断）
                                /*if (isFileModified(mLastModify, responseContLen)) {
                                    mResponse.body().close();
                                    savefile.delete();
                                    mFileCurrentLen = 0;
                                    continue;
                                }*/

        return !(getCacheLastModify().equals(lastModify)) || (mFileTotalLen != mFileCurrentLen + responseContLen);
    }


    /**
     * 获取服务器端文件的总长度和最后修改时间的信息
     *
     * @param config
     * @param url
     */
    public void getServiceFileInfo(HttpEnginerConfig config, String url) throws IOException {
        Response<ResponseBody> response = RetrofitClient.getServiceApi(config).download("bytes=0-", url).execute();
        mFileTotalLen = response.body().contentLength();
        mLastModify = response.headers().get("Last-Modified");
        response.body().close();
    }

}
