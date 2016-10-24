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
import com.elgregos.java.hazelcast.entities.SimpleEntity;
import com.elgregos.java.hazelcast.service.SimpleEntityService;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

@Component
public class SimpleEntityCache {

	public static final String SIMPLE_ENTITIES = "simple-entities";

	@Autowired
	private SimpleEntityService simpleEntityService;

	@Autowired
	private HazelcastInstance hazelcastInstance;

	private IMap<Long, SimpleEntity> map;

	public void clear() {
		map.clear();
	}

	@LogTime
	public SimpleEntity get(Long id) {
		return map.get(id);
	}

	@LogTime
	public List<SimpleEntity> getAllFromCache() {
		return map.values().stream().collect(Collectors.toList());
	}

	@LogTime
	public List<SimpleEntity> getWithMultiGet(List<Long> randomIds) {
		final List<SimpleEntity> simpleEntities = new ArrayList<>(randomIds.size());
		for (final Long id : randomIds) {
			simpleEntities.add(map.get(id));
		}
		return simpleEntities;
	}

	@LogTime
	public List<SimpleEntity> getWithOneGet(Set<Long> randomIds) {
		return new ArrayList<>(map.getAll(randomIds).values());
	}

	@PostConstruct
	public void init() {
		map = hazelcastInstance.getMap(SIMPLE_ENTITIES);
	}

	@LogTime
	public void loadCache() {
		final Map<Long, SimpleEntity> simpleEntitiesMap = simpleEntityService.getSimpleEntities().stream()
				.collect(Collectors.toMap(SimpleEntity::getId, Function.identity()));
		map.putAll(simpleEntitiesMap);
	}
}
