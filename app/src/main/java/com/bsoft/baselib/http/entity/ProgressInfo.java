package com.bsoft.baselib.http.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Author by wangzhaox,
 * Email wangzhaox@bsoft.com.cn,
 * Date on 2019/4/18.
 * Description:上传和下载的进度实体类
 * PS: Not easy to write code, please indicate.
 */
public class ProgressInfo implements Parcelable{
    private long mCurrentSize;//当前已完成的字节大小
    private long mTotalSize; //总字节大小
    private long mIntervalTime; //当次进度回调和上次回调的间隔时间(毫秒)
    private long mIntervalBytes; //当次进度回调和上次回调的时间间隔内上传或下载的byte长度

    public ProgressInfo() {
    }

    public long getCurrentSize() {
        return mCurrentSize;
    }

    public long getTotalSize() {
        return mTotalSize;
    }

    public void setCurrentSize(long currentSize) {
        this.mCurrentSize = currentSize;
    }

    public void setTotalSize(long totalSize) {
        this.mTotalSize = totalSize;
    }

    public long getIntervalTime() {
        return mIntervalTime;
    }

    public void setIntervalTime(long intervalTime) {
        this.mIntervalTime = intervalTime;
    }

    public long getIntervalBytes() {
        return mIntervalBytes;
    }

    public void setIntervalBytes(long intervalBytes) {
        this.mIntervalBytes = intervalBytes;
    }

    /**
     * @return 上传/下载是否完成
     */
    public boolean isCompleted() {
        return mCurrentSize >= 0 && mCurrentSize == mTotalSize;
    }

    /**
     * 获取上传下载的进度值[0,100]
     *
     * @return
     */
    public int getProgressNum() {
        if (mCurrentSize <= 0 || mTotalSize <= 0) {
            return 0;
        }
        return (int) (100 * mCurrentSize / mTotalSize);
    }

    /**
     * 获取上传或下载速度byte/s
     *
     * @return
     */
    public long getSpeed() {
        if (mIntervalBytes <= 0 || mIntervalTime <= 0) {
            return 0;
        }
        return mIntervalBytes * 1000 / mIntervalTime;
    }

    protected ProgressInfo(Parcel in) {
        mCurrentSize = in.readLong();
        mTotalSize = in.readLong();
        mIntervalTime = in.readLong();
        mIntervalBytes = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mCurrentSize);
        dest.writeLong(mTotalSize);
        dest.writeLong(mIntervalTime);
        dest.writeLong(mIntervalBytes);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ProgressInfo> CREATOR = new Creator<ProgressInfo>() {
        @Override
        public ProgressInfo createFromParcel(Parcel in) {
            return new ProgressInfo(in);
        }

        @Override
        public ProgressInfo[] newArray(int size) {
            return new ProgressInfo[size];
        }
    };

}
