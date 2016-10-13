package com.elgregos.java.hazelcast.cache.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.elgregos.java.hazelcast.cache.serializer.DoubleKeySerializer;
import com.elgregos.java.hazelcast.cache.serializer.ValueSerializer;
import com.elgregos.java.hazelcast.entities.key.DoubleKey;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.config.SerializerConfig;
import com.hazelcast.core.HazelcastInstance;

@Configuration
public class CacheConfiguration {

	@Bean
	ClientConfig clientConfig() {
		final ClientConfig clientConfig = new ClientConfig();
		clientConfig.setInstanceName("localhost");
		clientConfig.getNetworkConfig().addAddress("localhost:5701");
		return clientConfig;
	}

	@Bean
	SerializerConfig doubleKeySerializerConfig() {
		return new SerializerConfig().setImplementation(new DoubleKeySerializer()).setTypeClass(DoubleKey.class);
	}

	@Bean
	HazelcastInstance hazelcastInstance() {
		clientConfig().getSerializationConfig().addSerializerConfig(doubleKeySerializerConfig())
				.addSerializerConfig(valueSerializerConfig());
		return HazelcastClient.newHazelcastClient(clientConfig());
	}

	@Bean
	SerializerConfig valueSerializerConfig() {
		return new SerializerConfig().setImplementation(new ValueSerializer()).setTypeClass(Object.class);
	}

}
