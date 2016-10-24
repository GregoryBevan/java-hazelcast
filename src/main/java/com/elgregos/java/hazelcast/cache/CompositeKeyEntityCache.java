package com.elgregos.java.hazelcast.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.elgregos.java.hazelcast.aspect.LogTime;
import com.elgregos.java.hazelcast.entities.CompositeKeyEntity;
import com.elgregos.java.hazelcast.entities.key.DoubleKey;
import com.elgregos.java.hazelcast.service.CompositeKeyEntityService;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

@Component
public class CompositeKeyEntityCache {

	public static final String COMPOSITE_KEY_ENTITIES = "composite-key-entities";

	@Autowired
	private CompositeKeyEntityService compositeKeyEntityService;

	@Autowired
	private HazelcastInstance hazelcastInstance;

	private IMap<DoubleKey, CompositeKeyEntity> map;

	public void clear() {
		map.clear();
	}

	@LogTime
	public CompositeKeyEntity get(DoubleKey key) {
		return map.get(key);
	}

	@LogTime
	public List<CompositeKeyEntity> getAllFromCache() {
		return map.values().stream().collect(Collectors.toList());
	}

	@LogTime
	public List<CompositeKeyEntity> getWithMultiGet(List<DoubleKey> randomDoubleKeys) {
		final List<CompositeKeyEntity> compositeKeyEntities = new ArrayList<>(randomDoubleKeys.size());
		for (final DoubleKey doubleKey : randomDoubleKeys) {
			compositeKeyEntities.add(map.get(doubleKey));
		}
		return compositeKeyEntities;
	}

	@LogTime
	public List<CompositeKeyEntity> getWithOneGet(Set<DoubleKey> randomDoubleKeys) {
		return new ArrayList<>(map.getAll(randomDoubleKeys).values());
	}

	@PostConstruct
	public void init() {
		map = hazelcastInstance.getMap(COMPOSITE_KEY_ENTITIES);
	}

	@LogTime
	public void loadCache() {
		final Map<DoubleKey, CompositeKeyEntity> compositeEntityMap = compositeKeyEntityService
				.getCompositeKeyEntities().stream().collect(
						Collectors.toMap(c -> new DoubleKey(c.getFirstCode(), c.getSecondCode()), Function.identity()));
		map.putAll(compositeEntityMap);
	}

}
