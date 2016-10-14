package com.elgregos.java.hazelcast.cache;

import java.util.List;
import java.util.Map;
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

	private IMap<String, SimpleEntity> map;

	public void clear() {
		map.clear();
	}

	@LogTime
	public SimpleEntity get(String code) {
		return map.get(code);
	}

	@LogTime
	public List<SimpleEntity> getAllFromCache() {
		return map.values().stream().collect(Collectors.toList());
	}

	@PostConstruct
	public void init() {
		map = hazelcastInstance.getMap(SIMPLE_ENTITIES);
	}

	@LogTime
	public void loadCache() {
		final Map<String, SimpleEntity> simpleEntitiesMap = simpleEntityService.getSimpleEntities().stream()
				.collect(Collectors.toMap(SimpleEntity::getCode, Function.identity()));
		map.putAll(simpleEntitiesMap);
	}
}
