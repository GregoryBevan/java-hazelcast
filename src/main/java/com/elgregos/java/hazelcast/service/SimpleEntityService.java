package com.elgregos.java.hazelcast.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elgregos.java.hazelcast.aspect.LogTime;
import com.elgregos.java.hazelcast.cache.SimpleEntityCache;
import com.elgregos.java.hazelcast.entities.SimpleEntity;
import com.elgregos.java.hazelcast.entities.repositories.SimpleEntityRepository;

@Service
public class SimpleEntityService {

	@Autowired
	private SimpleEntityRepository repository;

	@Autowired
	private SimpleEntityCache simpleEntityCache;

	public List<SimpleEntity> getAllFromCache() {
		return simpleEntityCache.getAllFromCache();
	}

	@LogTime
	public List<SimpleEntity> getSimpleEntities() {
		return repository.findAll();
	}

	public SimpleEntity loadFromCache(Long id) {
		return simpleEntityCache.get(id);
	}

	public void testMulti(Long number) {
		final List<Long> randomIds = getIds(number);
		final List<SimpleEntity> withOneGet = simpleEntityCache.getWithOneGet(new HashSet<>(randomIds));
		final List<SimpleEntity> withMultiGet = simpleEntityCache.getWithMultiGet(randomIds);
		System.out.println("Sizes : " + withOneGet.size() + " & " + withMultiGet.size());
	}

	private List<Long> getIds(Long number) {
		final List<Long> ids = new ArrayList<>();
		for (int i = 1; i <= number; i++) {
			ids.add(Long.valueOf(i));
		}
		return ids;
	}
}
