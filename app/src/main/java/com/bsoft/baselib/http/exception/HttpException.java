package com.bsoft.baselib.http.exception;

/**
 * Author by wangzhaox,
 * Email wangzhaox@bsoft.com.cn,
 * Date on 2019/4/10.
 * Description:
 * PS: Not easy to write code, please indicate.
 */
public class HttpException extends RuntimeException {


    private final int mCode;
    private final String mMessage;

    public HttpException(int code, String message) {
        this.mCode = code;
        this.mMessage = message;
    }

    /**
     * HTTP status code.
     */
    public int getCode() {
        return mCode;
    }

    @Override
    public String getMessage() {
        return mMessage;
    }
}
