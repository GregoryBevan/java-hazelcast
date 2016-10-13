package com.elgregos.java.hazelcast.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.elgregos.java.hazelcast.entities.CompositeKeyEntity;
import com.elgregos.java.hazelcast.entities.key.DoubleKey;
import com.elgregos.java.hazelcast.service.CompositeKeyEntityService;

@RestController
@RequestMapping("composite-key-entities")
public class CompositeKeyEntityRestImpl {

	@Autowired
	private CompositeKeyEntityService service;

	@RequestMapping(method = RequestMethod.GET, value = "/{firstCode}/{secondCode}")
	public CompositeKeyEntity get(@PathVariable("firstCode") String firstCode,
			@PathVariable("secondCode") String secondCode) {
		return service.loadFromCache(new DoubleKey(firstCode, secondCode));
	}

	@RequestMapping(method = RequestMethod.GET)
	public List<CompositeKeyEntity> getAll() {
		return service.getAllFromCache();
	}

}
