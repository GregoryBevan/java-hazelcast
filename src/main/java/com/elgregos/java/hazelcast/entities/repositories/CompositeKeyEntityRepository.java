package com.elgregos.java.hazelcast.entities.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.elgregos.java.hazelcast.entities.CompositeKeyEntity;

public interface CompositeKeyEntityRepository extends JpaRepository<CompositeKeyEntity, Long> {

}
