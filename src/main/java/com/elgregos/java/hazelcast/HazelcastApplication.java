package com.elgregos.java.hazelcast;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.elgregos.java.hazelcast.cache.CacheLoader;
import com.zaxxer.hikari.HikariDataSource;

@SpringBootApplication
@ComponentScan(basePackages = "com.elgregos.java.hazelcast")
@EnableJpaRepositories(basePackages = "com.elgregos.java.hazelcast.entities")
@EnableAspectJAutoProxy
public class HazelcastApplication {

	@Autowired
	private CacheLoader cacheLoader;

	private final Logger logger = LoggerFactory.getLogger(HazelcastApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(HazelcastApplication.class, args);
	}

	@Bean
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource dataSource() {
		return DataSourceBuilder.create().type(HikariDataSource.class).build();
	}

	@Bean
	protected ServletContextListener listener() {
		return new ServletContextListener() {

			@Override
			public void contextDestroyed(ServletContextEvent sce) {
				logger.info("ServletContext destroyed");
			}

			@Override
			public void contextInitialized(ServletContextEvent sce) {
				cacheLoader.loadCache();
				logger.info("ServletContext initialized");
			}

		};
	}
}
