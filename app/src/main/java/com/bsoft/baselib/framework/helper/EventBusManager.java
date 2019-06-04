package com.bsoft.baselib.framework.helper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.reflect.Method;

/**
 * Author by wangzhaox,
 * Email wangzhaox@bsoft.com.cn,
 * Date on 2019/2/28.
 * Description: Eventbus管理器
 * PS: Not easy to write code, please indicate.
 */
public class EventBusManager {

    private static volatile EventBusManager sInstance;

    public static EventBusManager getInstance() {
        if (sInstance == null) {
            synchronized (EventBusManager.class) {
                if (sInstance == null) {
                    sInstance = new EventBusManager();
                }
            }
        }
        return sInstance;
    }

    private EventBusManager() {
    }

    /**
     * 注册订阅者
     *
     * @param subscriber 订阅者
     */
    public void register(Object subscriber) {
        if (haveAnnotation(subscriber)) {
            EventBus.getDefault().register(subscriber);
        }
    }

    /**
     * 注销订阅者, 允许在项目中同时依赖两个 EventBus, 只要您喜欢
     *
     * @param subscriber 订阅者
     */
    public void unregister(Object subscriber) {
        if (haveAnnotation(subscriber)) {
            org.greenrobot.eventbus.EventBus.getDefault().unregister(subscriber);
        }
    }

    /**
     * {@link org.greenrobot.eventbus.EventBus} 要求注册之前, 订阅者必须含有一个或以上声明 {@link org.greenrobot.eventbus.Subscribe}
     * 注解的方法, 否则会报错, 所以判断其是否有指定的注解
     *
     * @param subscriber 订阅者
     * @return 返回 {@code true} 则表示含有 {@link org.greenrobot.eventbus.Subscribe} 注解, {@code false} 为不含有
     */
    private boolean haveAnnotation(Object subscriber) {
        boolean skipSuperClasses = false;
        Class<?> clazz = subscriber.getClass();
        //查找类中符合注册要求的方法, 直到Object类
        while (clazz != null && !isSystemCalss(clazz.getName()) && !skipSuperClasses) {
            Method[] allMethods;
            try {
                allMethods = clazz.getDeclaredMethods();
            } catch (Throwable th) {
                try {
                    allMethods = clazz.getMethods();
                } catch (Throwable th2) {
                    continue;
                } finally {
                    skipSuperClasses = true;
                }
            }
            for (int i = 0; i < allMethods.length; i++) {
                Method method = allMethods[i];
                Class<?>[] parameterTypes = method.getParameterTypes();
                //查看该方法是否含有 Subscribe 注解
                if (method.isAnnotationPresent(Subscribe.class) && parameterTypes.length == 1) {
                    return true;
                }
            }
            //获取父类, 以继续查找父类中符合要求的方法
            clazz = clazz.getSuperclass();
        }
        return false;
    }

    private boolean isSystemCalss(String name) {
        return name.startsWith("java.") || name.startsWith("javax.") || name.startsWith("android.");
    }

}
