package com.slzh.utils;

import java.util.Map;
import java.util.concurrent.*;

public class ThreadManage {

    private static ThreadManage instance = null;


    private ExecutorService threadPoolExecutor;
    private final int corePoolSize = 20;
    private final int maximumPoolSize = 50;
    private final long keepAliveTime = 10;
    private final TimeUnit unit = TimeUnit.SECONDS;
    private final BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(300);

    private ThreadManage() {
        super();
        threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public static ThreadManage getManage() {
        if (instance == null) {
            instance = new ThreadManage();
        }
        return instance;
    }

    public void execute(Runnable runnable) {
        threadPoolExecutor.execute(runnable);
    }


    /**
     * 线程池缓存
     */
    private static final Map<String, ThreadPoolExecutor> THREAD_POOL_EXECUTOR_MAP = new ConcurrentHashMap<>();


    public static ThreadPoolExecutor getThreadPoolExecutor(String executorName) {
        ThreadPoolExecutor threadPoolExecutor = THREAD_POOL_EXECUTOR_MAP.get(executorName);
        if (threadPoolExecutor == null) {
            threadPoolExecutor=new ThreadPoolExecutor(50, 100, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100));
            return threadPoolExecutor;
        }
        return threadPoolExecutor;
    }


}
