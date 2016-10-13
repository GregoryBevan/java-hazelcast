package com.elgregos.java.hazelcast.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elgregos.java.hazelcast.cache.CompositeKeyEntityCache;
import com.elgregos.java.hazelcast.entities.CompositeKeyEntity;
import com.elgregos.java.hazelcast.entities.key.DoubleKey;
import com.elgregos.java.hazelcast.entities.repositories.CompositeKeyEntityRepository;

@Service
public class CompositeKeyEntityService {

	@Autowired
	private CompositeKeyEntityRepository repository;

	@Autowired
	private CompositeKeyEntityCache cache;

	public List<CompositeKeyEntity> getAllFromCache() {
		return cache.getAllFromCache();
	}

	public List<CompositeKeyEntity> getCompositeKeyEntities() {
		return this.repository.findAll();
	}

	public CompositeKeyEntity loadFromCache(DoubleKey key) {
		return cache.get(key);
	}

}
