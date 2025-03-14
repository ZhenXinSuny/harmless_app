package com.agridata.network.utils;

import android.util.Log;

import com.elvishew.xlog.XLog;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2024-03-26 14:55.
 * @Description :描述
 */
public class LogUtil {
    private static final String TAG = "Harmless";

    /**
     * 是否需要打印日志，最好在application的onCreate方法中初始化
     * true：打印日志
     * false：不打印日志
     * <p/>
     * 注意：测试环境设置为true，线上环境设置为false
     */
    public static boolean isDebug = true;

    public LogUtil() {
        throw new UnsupportedOperationException("can not be instantiated");
    }

    public static void logOkHttp(String msg) {
            XLog.i(TAG, buildMsg(msg));
    }

    public static void i(String msg) {
        if (isDebug) {
            XLog.tag(TAG).i(buildMsg(msg));
        }
    }

    public static void d(String msg) {
        if (isDebug) {
            XLog.tag(TAG).d(buildMsg(msg));
        }
    }

    public static void e(String msg) {
        if (isDebug) {
            XLog.tag(TAG).e(buildMsg(msg));
        }
    }

    public static void v(String msg) {
        if (isDebug) {
            XLog.tag(TAG).v(buildMsg(msg));
        }
    }

    // 下面是传入自定义tag的函数
    public static void i(String tag, String msg) {
        if (isDebug) {
            XLog.tag(tag).i(buildMsg(msg));
        }
    }

    public static void d(String tag, String msg) {
        if (isDebug) {
            XLog.tag(tag).d(buildMsg(msg));
        }
    }

    public static void e(String tag, String msg) {
        if (isDebug) {
            XLog.tag(tag).e(buildMsg(msg));
        }
    }

    public static void v(String tag, String msg) {
        if (isDebug) {
            XLog.tag(tag).v(buildMsg(msg));
        }
    }

    public static String getStackTraceString(Throwable tr) {
        return Log.getStackTraceString(tr);
    }

    public static void printStackTrace(String tag) {
        if (isDebug) {
            XLog.tag(tag).e(buildMsg(getStackTraceString(new Exception())));
        }
    }

    private static String buildMsg(String msg) {
        StringBuilder buffer = new StringBuilder();

        final StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[4];

        buffer.append("[ (");
        buffer.append(stackTraceElement.getFileName());
        buffer.append(":");
        buffer.append(stackTraceElement.getLineNumber());
        buffer.append(")# ");
        buffer.append(stackTraceElement.getMethodName());
        buffer.append(" -> ");
        buffer.append(Thread.currentThread().getName());
        buffer.append(" ] ");

        buffer.append(msg);

        return buffer.toString();
    }
}
