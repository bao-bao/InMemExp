package ThreadPool;

/* Created by AMXPC on 2017/1/5. */

import java.util.concurrent.*;



public class ThreadPoolFactory {

    // thread pool with fixed size nThreads
    public static ThreadPoolExecutor getFixedThreadPool(int nThreads) {
        return new ThreadPoolExecutor(nThreads, nThreads,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
    }

    // thread pool with cached functionality, new thread for every new task, and kill thread when 60s sleep
    public static ThreadPoolExecutor getCachedThreadPool() {
        return new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>());
    }

    // thread pool with only one thread, for serial run
    public static ThreadPoolExecutor getSingleThreadExecutor() {
        return new ThreadPoolExecutor(1, 1,
                        0L, TimeUnit.MILLISECONDS,
                        new LinkedBlockingQueue<Runnable>());
    }
}
