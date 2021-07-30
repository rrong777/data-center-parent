package com.slzh.utils;

import java.io.Closeable;
import java.io.IOException;
import java.util.Date;

/**
 * IO相关工具类
 * @author lanb
 * @date Oct 29, 2018
 */
public class IOUtils {

	/**
	 * 关闭对象，连接
	 * @param closeable
	 */
    public static void closeQuietly(final Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (final IOException ioe) {
            // ignore
        }
    }

    public static void main(String[] args) {
        System.out.println(new Date(1589438101918L));
    }
}
