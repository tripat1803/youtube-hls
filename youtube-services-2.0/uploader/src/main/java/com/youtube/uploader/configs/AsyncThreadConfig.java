package com.youtube.uploader.configs;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;

@Configuration
public class AsyncThreadConfig implements AsyncConfigurer {
    
    private Executor threadPoolExecutor;

    @Override
    public synchronized Executor getAsyncExecutor() {
        if(threadPoolExecutor == null){
            threadPoolExecutor = Executors.newVirtualThreadPerTaskExecutor();
        }
        return threadPoolExecutor;
    }
}
