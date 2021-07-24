package com.esmc.mcnp.logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;

public class CacheLogger implements CacheEventListener<Object, Object> {

	private final Logger LOG = LogManager.getLogger(CacheLogger.class);

	@Override
	public void onEvent(CacheEvent<? extends Object, ? extends Object> cacheEvent) {
		LOG.info("Key: {} | EventType: {} | Old value: {} | New value: {}", cacheEvent.getKey(), cacheEvent.getType(),
				cacheEvent.getOldValue(), cacheEvent.getNewValue());
	}

}
