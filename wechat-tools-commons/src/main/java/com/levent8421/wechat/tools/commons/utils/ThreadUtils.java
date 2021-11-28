package com.levent8421.wechat.tools.commons.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.*;

/**
 * Create By Levent8421
 * Create Time: 2020/6/13 14:36
 * Class Name: ThreadUtils
 * Author: Levent8421
 * Description:
 * 线程工具类
 *
 * @author Levent8421
 */
@Slf4j
public class ThreadUtils {
    /**
     * Named Thread Factory
     */
    private static class NamedThreadFactory implements ThreadFactory {
        private int threadNum = 1;
        private final String threadNamePrefix;

        NamedThreadFactory(String threadNamePrefix) {
            this.threadNamePrefix = threadNamePrefix;
        }

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, getNextThreadName());
        }

        private String getNextThreadName() {
            return String.format("%s-%s", threadNamePrefix, threadNum++);
        }
    }

    /**
     * Fixed name thread factory
     */
    private static class FixedNameThreadFactory implements ThreadFactory {
        private final String name;

        private FixedNameThreadFactory(String name) {
            this.name = name;
        }

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, name);
        }
    }

    /**
     * 创建单线程 线程池
     *
     * @param threadName 线程名称
     * @return 线程池（单线程）
     */
    public static ExecutorService createSingleThreadPool(String threadName) {
        return createThreadPoolExecutorService(1, 1, threadName);
    }

    /**
     * 创建线程池
     *
     * @param poolCoreSize 核心线程数量
     * @param poolMaxSize  最大线程数量
     * @param threadName   线程名称
     * @return 线程池
     */
    public static ExecutorService createThreadPoolExecutorService(int poolCoreSize, int poolMaxSize, String threadName) {
        return new ThreadPoolExecutor(poolCoreSize, poolMaxSize, 0, TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<>(), new FixedNameThreadFactory(threadName));
    }

    /**
     * 当前线程睡眠
     *
     * @param ms 毫秒数
     */
    public static void trySleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            log.error("Error on thread sleep", e);
        }
    }

    /**
     * 停止线程池
     *
     * @param threadPool 线程池
     * @param awaitTime  最大等待时间
     */
    public static void shutdownExecutorService(ExecutorService threadPool, long awaitTime) {
        log.debug("Shutting down threadPool: [{}]", threadPool);
        try {
            final boolean success = threadPool.awaitTermination(awaitTime, TimeUnit.MILLISECONDS);
            if (!success) {
                log.warn("Can not shutdown threadPool[{}], return value is false, force shutdown it!", threadPool);
                forceShutdown(threadPool);
            }
        } catch (InterruptedException e) {
            log.warn("Can not shutdown threadPool [{}] with maxTime[{}], force shutdown it!", threadPool, awaitTime);
            forceShutdown(threadPool);
        }
    }

    /**
     * 强制停止线程池
     *
     * @param threadPool 线程池
     */
    public static void forceShutdown(ExecutorService threadPool) {
        final List<Runnable> tasks = threadPool.shutdownNow();
        log.warn("Shutdown threadPool[{}] success, tasks=[{}]", threadPool, tasks);
    }
}