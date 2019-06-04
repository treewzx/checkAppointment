package com.bsoft.baselib.http.exception;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;


/**
 * Author by wangzhaox,
 * Email wangzhaox@bsoft.com.cn,
 * Date on 2019/4/10.
 * Description:对网络请求异常进行统一管理
 * PS: Not easy to write code, please indicate.
 */

/*
4XX:这些状态代码表示请求可能出错，妨碍了服务器的处理。
400 （错误请求） 服务器不理解请求的语法。
401 （未授权） 请求要求身份验证。 对于需要登录的网页，服务器可能返回此响应。
403 （禁止） 服务器拒绝请求。
404 （未找到） 服务器找不到请求的网页。
405 （方法禁用） 禁用请求中指定的方法。
406 （不接受） 无法使用请求的内容特性响应请求的网页。
407 （需要代理授权） 此状态代码与 401（未授权）类似，但指定请求者应当授权使用代理。
408 （请求超时） 服务器等候请求时发生超时。
409 （冲突） 服务器在完成请求时发生冲突。 服务器必须在响应中包含有关冲突的信息。
410 （已删除） 如果请求的资源已永久删除，服务器就会返回此响应。
411 （需要有效长度） 服务器不接受不含有效内容长度标头字段的请求。
412 （未满足前提条件） 服务器未满足请求者在请求中设置的其中一个前提条件。
413 （请求实体过大） 服务器无法处理请求，因为请求实体过大，超出服务器的处理能力。
414 （请求的 URI 过长） 请求的 URI（通常为网址）过长，服务器无法处理。
415 （不支持的媒体类型） 请求的格式不受请求页面的支持。
416 （请求范围不符合要求） 如果页面无法提供请求的范围，则服务器会返回此状态代码。
417 （未满足期望值） 服务器未满足"期望"请求标头字段的要求。


5xx（服务器错误）这些状态代码表示服务器在尝试处理请求时发生内部错误。 这些错误可能是服务器本身的错误，而不是请求的错误。
500 （服务器内部错误） 服务器遇到错误，无法完成请求。
501 （尚未实施） 服务器不具备完成请求的功能。 例如，服务器无法识别请求方法时可能会返回此代码。
502 （错误网关） 服务器作为网关或代理，从上游服务器收到无效响应。
503 （服务不可用） 服务器目前无法使用（由于超载或停机维护）。 通常，这只是暂时状态。
504 （网关超时） 服务器作为网关或代理，但是没有及时从上游服务器收到请求。
505 （HTTP 版本不受支持） 服务器不支持请求中所用的 HTTP 协议版本。

 //对应HTTP的请求错误返回状态码（4XX，5XX）
   private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;

    private static final int SERVER_INTERNAL_ERROR = 500;
    private static final int SERVER_UNSUPPORT = 501;
    private static final int SERVER_BAD_GATEWAY = 502;
    private static final int SERVER_UNAVAILABLE = 503;
    private static final int SERVER_GATEWAY_TIMEOUT = 504;
    private static final int SERVER_HTTPVER_UNSUPPORT = 505;
    */


public class ExceptionEnginer {
    public static ApiException handleException(Throwable e) {
        ApiException ex;
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            int responseCode = httpException.getCode();
            ex = new ApiException(e, ERROR.HTTP_ERROR);
            if (responseCode >= 400 && responseCode < 500) {
                ex.message = "客户端请求错误";
            } else if (responseCode > 500) {
                ex.message = "服务器处理出现错误";
            }
            return ex;
        } else if (e instanceof ParseException) {
            ex = new ApiException(e, ERROR.PARSE_ERROR);
            ex.message = e.getMessage();
            return ex;
        } else if (e instanceof SocketTimeoutException) {
            ex = new ApiException(e, ERROR.NETWORK_TIMEOUT);
            ex.message = "请求超时";
            return ex;
        } else if (e instanceof UnknownHostException) {
            ex = new ApiException(e, ERROR.UNKNOWM_HOSE);
            ex.message = "未知主机";
            return ex;
        } else if (e instanceof ConnectException) {
            ex = new ApiException(e, ERROR.NETWORK_ERROR);
            ex.message = "连接失败";
            return ex;
        } else if (e instanceof IOStreamReadException) {
            ex = new ApiException(e, ERROR.DATA_READ_ERROR);
            ex.message = "数据读取失败";
            return ex;
        } else {
            ex = new ApiException(e, ERROR.UNKNOWN);
            ex.message = "未知错误：" + e.getMessage();          //其他错误
            return ex;
        }
    }


    private final class ERROR {
        /**
         * 未知错误
         */
        public static final int UNKNOWN = 1000;
        /**
         * 解析错误
         */
        public static final int PARSE_ERROR = 1001;
        /**
         * 网络超时
         */
        public static final int NETWORK_TIMEOUT = 1002;
        /**
         * 网络错误
         */
        public static final int NETWORK_ERROR = 1003;
        /**
         * 协议出错
         */
        public static final int HTTP_ERROR = 1004;
        /**
         * 未知主机
         */
        public static final int UNKNOWM_HOSE = 1005;

        /**
         * 数据读取错误
         */
        public static final int DATA_READ_ERROR = 1006;
    }
}
