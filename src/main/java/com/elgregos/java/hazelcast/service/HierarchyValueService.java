package com.elgregos.java.hazelcast.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elgregos.java.hazelcast.cache.HierarchyValueCache;
import com.elgregos.java.hazelcast.entities.hierarchy.HierarchyValue;
import com.elgregos.java.hazelcast.entities.hierarchy.HierarchyValueRepository;

@Service
public class HierarchyValueService {

	@Autowired
	private HierarchyValueCache cache;

	@Autowired
	private HierarchyValueRepository hierarchyValueRepository;

	public List<HierarchyValue> findByHierarchyCode(final String hierarchyCode) {
		return hierarchyValueRepository.findByHierarchyCode(hierarchyCode);
	}

	public HierarchyValue getByIdFromCache(final Long id) {
		return cache.getById(id);
	}

}
