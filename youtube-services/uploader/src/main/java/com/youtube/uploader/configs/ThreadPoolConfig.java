package com.youtube.uploader.configs;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;

@Configuration
public class ThreadPoolConfig implements AsyncConfigurer {
    private ThreadPoolExecutor threadPoolExecutor;

    @Override
    public synchronized Executor getAsyncExecutor(){
        if(threadPoolExecutor == null){
            int minPoolSize = 8;
            int maxPoolSize = 16;
            int queueSize = 3000;
            
            threadPoolExecutor = new ThreadPoolExecutor(minPoolSize, maxPoolSize, 1, TimeUnit.HOURS, new ArrayBlockingQueue<>(queueSize));
        }
        return threadPoolExecutor;
    }
}