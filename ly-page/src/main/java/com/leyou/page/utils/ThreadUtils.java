package com.leyou.page.utils;

import java.util.concurrent.*;

/**
 * 简易线程池
 *
 * @author: cooFive
 * @CreateDate: 2018/8/2 14:02
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
public class ThreadUtils {
    private static final ExecutorService es = Executors.newFixedThreadPool(10);

    public static void execute(Runnable runnable) {
        es.submit(runnable);
    }
}
