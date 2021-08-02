package com.kreatech.config.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

public class Threads {

	private static final Logger logger = LoggerFactory.getLogger(Threads.class);

	/**
	 * attente de sommeil, en millisecondes
	 */
	public static void sleep(long milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Arrêtez le pool de threads Utilisez d'abord l'arrêt pour arrêter de recevoir
	 * de nouvelles tâches et essayez de terminer toutes les tâches existantes. S'il
	 * expire, appelez shutdownNow, annulez les tâches en attente dans le workQueue
	 * et interrompez toutes les fonctions de blocage. S'il y a encore des gens en
	 * heures supplémentaires, il sera obligé d'arrêter. De plus, le thread lui-même
	 * est appelé et interrompu lors de l'arrêt.
	 */
	public static void shutdownAndAwaitTermination(ExecutorService pool) {
		if (pool != null && !pool.isShutdown()) {
			pool.shutdown();
			try {
				if (!pool.awaitTermination(120, TimeUnit.SECONDS)) {
					pool.shutdownNow();
					if (!pool.awaitTermination(120, TimeUnit.SECONDS)) {
						logger.info("Pool did not terminate");
					}
				}
			} catch (InterruptedException ie) {
				pool.shutdownNow();
				Thread.currentThread().interrupt();
			}
		}
	}

	/**
	 * Imprimer les informations d'exception de thread
	 */
	public static void printException(Runnable r, Throwable t) {
		if (t == null && r instanceof Future<?>) {
			try {
				Future<?> future = (Future<?>) r;
				if (future.isDone()) {
					future.get();
				}
			} catch (CancellationException ce) {
				t = ce;
			} catch (ExecutionException ee) {
				t = ee.getCause();
			} catch (InterruptedException ie) {
				Thread.currentThread().interrupt();
			}
		}
		if (t != null) {
			logger.error(t.getMessage(), t);
		}
	}
}
