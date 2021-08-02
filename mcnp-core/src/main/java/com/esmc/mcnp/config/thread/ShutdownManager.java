package com.kreatech.config.thread;


import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Assurez-vous que le thread d'arrière-plan peut être fermé lorsque l'application se ferme
 *
 */
@Component
public class ShutdownManager
{
    private static final Logger logger = LoggerFactory.getLogger("sys-user");

    @PreDestroy
    public void destroy()
    {
        shutdownAsyncManager();
    }


    /**
     * Arrêter d'exécuter des tâches de manière asynchrone
     */
    private void shutdownAsyncManager()
    {
        try
        {
            logger.info("====Fermer le pool de threads des tâches de tâche d'arrière-plan====");
            AsyncManager.asyncManager().shutdown();
        }
        catch (Exception e)
        {
            logger.error(e.getMessage(), e);
        }
    }
}
