package com.kreatech.config.thread;

import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.kreatech.api.util.SpringUtils;

public class AsyncManager {

	/**
	 * Délai de fonctionnement 10 millisecondes
	 */
	private final int OPERATE_DELAY_TIME = 10;

	/**
	 * Pool de threads de planification des tâches d'opération asynchrone
	 */
	private final ScheduledExecutorService executor = SpringUtils.getBean("scheduledExecutorService");

	/**
	 * Mode singleton
	 */
	private AsyncManager() {
	}

	private static final AsyncManager ASYNC_MANAGER = new AsyncManager();

	public static AsyncManager asyncManager() {
		return ASYNC_MANAGER;
	}

	/**
	 * Effectuer la tâche
	 *
	 * @param task tâche
	 */
	public void execute(TimerTask task) {
		executor.schedule(task, OPERATE_DELAY_TIME, TimeUnit.MILLISECONDS);
	}

	/**
	 * Arrêter le pool de threads de tâches
	 */
	public void shutdown() {
		Threads.shutdownAndAwaitTermination(executor);
	}
}
