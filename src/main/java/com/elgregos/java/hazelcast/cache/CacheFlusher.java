package com.elgregos.java.hazelcast.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CacheFlusher {

	@Autowired
	private SimpleEntityCache simpleEntityCache;

	@Autowired
	private CompositeKeyEntityCache compositeKeyEntityCache;

	@Autowired
	private HierarchyValueCache hierarchyValueCache;

	public void flushCache() {
		simpleEntityCache.clear();
		compositeKeyEntityCache.clear();
		hierarchyValueCache.clear();
	}
}
