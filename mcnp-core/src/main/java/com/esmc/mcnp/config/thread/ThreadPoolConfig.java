package com.kreatech.config.thread;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class ThreadPoolConfig {

    /**
     * Taille du pool de threads de base
     */
    private final int corePoolSize = 50;

    /**
     * Nombre maximum de threads pouvant être créés
     */
    private final int maxPoolSize = 200;

    /**
     * Longueur maximale de la file d'attente
     */
    private final int queueCapacity = 1000;

    /**
     * Le temps d'inactivité autorisé par les threads de maintenance du pool de threads
     */
    private final int keepAliveSeconds = 300;

    @Bean(name = "threadPoolTaskExecutor")
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setMaxPoolSize(maxPoolSize);
        executor.setCorePoolSize(corePoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setKeepAliveSeconds(keepAliveSeconds);
        // Stratégie de traitement du pool de threads pour les tâches rejetées (aucun thread n'est disponible)
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }

    /**
     * Effectuer des tâches périodiques ou chronométrées
     */
    @Bean(name = "scheduledExecutorService")
    protected ScheduledExecutorService scheduledExecutorService() {
        BasicThreadFactory build = new BasicThreadFactory.Builder().namingPattern("schedule-pool-%d").daemon(true).build();
        return new ScheduledThreadPoolExecutor(corePoolSize, build) {
            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                super.afterExecute(r, t);
                Threads.printException(r, t);
            }
        };
    }

}
