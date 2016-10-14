package com.elgregos.java.hazelcast.service;

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

	public SimpleEntity loadFromCache(String code) {
		return simpleEntityCache.get(code);
	}
}
