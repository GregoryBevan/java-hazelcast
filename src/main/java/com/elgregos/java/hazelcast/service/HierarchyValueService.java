package com.elgregos.java.hazelcast.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elgregos.java.hazelcast.aspect.LogTime;
import com.elgregos.java.hazelcast.cache.HierarchyValueCache;
import com.elgregos.java.hazelcast.entities.hierarchy.HierarchyValue;
import com.elgregos.java.hazelcast.entities.hierarchy.HierarchyValueRepository;

@Service
public class HierarchyValueService {

	@Autowired
	private HierarchyValueCache cache;

	@Autowired
	private HierarchyValueRepository hierarchyValueRepository;

	@LogTime
	public List<HierarchyValue> findByHierarchyCode(final String hierarchyCode) {
		return hierarchyValueRepository.findByHierarchyCode(hierarchyCode);
	}

	public HierarchyValue getByIdFromCache(final Long id) {
		return cache.get(id);
	}

	public void testMulti(Long number) {
		final List<Long> randomIds = getIds(number);
		final List<HierarchyValue> withOneGet = cache.getWithOneGet(new HashSet<>(randomIds));
		final List<HierarchyValue> withMultiGet = cache.getWithMultiGet(randomIds);
		System.out.println("Sizes : " + withOneGet.size() + " & " + withMultiGet.size());
	}

	private List<Long> getIds(Long number) {
		final List<Long> ids = new ArrayList<>();
		for (int i = 360003, max = number.intValue() + 360003; i <= max && i <= 539910; i++) {
			ids.add(Long.valueOf(i));
		}
		return ids;
	}
}
