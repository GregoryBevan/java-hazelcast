package com.elgregos.java.hazelcast.entities.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.elgregos.java.hazelcast.entities.SimpleEntity;

public interface SimpleEntityRepository extends JpaRepository<SimpleEntity, Long> {

}
