package com.elgregos.java.hazelcast.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.elgregos.java.hazelcast.entities.hierarchy.HierarchyValue;
import com.elgregos.java.hazelcast.service.HierarchyValueService;

@RestController
@RequestMapping("hierarchy-values")
public class HierarchyValueRestImpl {

	@Autowired
	private HierarchyValueService service;

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public HierarchyValue get(@PathVariable("id") Long id) {
		return service.getByIdFromCache(id);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/multi/{number}")
	public void getMilti(@PathVariable("number") Long number) {
		service.testMulti(number);
	}

}
