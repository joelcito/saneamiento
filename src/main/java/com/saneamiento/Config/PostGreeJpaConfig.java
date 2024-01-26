package com.saneamiento.Config;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.sql.DataSource;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(
		basePackages = "com.saneamiento.models.dao",
		entityManagerFactoryRef = "postGressEMF",
		transactionManagerRef = "postGreeTrxManager"
)
@EnableTransactionManagement
public class PostGreeJpaConfig {

	@Bean
	public EntityManagerFactoryBuilder getEntityManagerFactoryBuilder() {
		return new EntityManagerFactoryBuilder(
												new HibernateJpaVendorAdapter(),
												new HashMap<>(),
												null
											);
	}
	
	@Bean("postGressEMF")
	public LocalContainerEntityManagerFactoryBean postGreeEntityManagerFactory(
																				@Qualifier("postGreeDataSource") DataSource postGreeDS,
																				EntityManagerFactoryBuilder builder
																				) {
		
		Map<String, String> additionalPros = new HashMap<>();
		additionalPros.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
		additionalPros.put("hibernate.hbm2ddl.auto", "update"); // Aquí agregas la propiedad
		additionalPros.put("spring.jpa.show-sql", "true"); // Aquí agregas la propiedad
		
		return builder.dataSource(postGreeDS)
				.persistenceUnit("postGree")
				.properties(additionalPros)
				.packages("com.saneamiento.models.entity")
				.build();
		
		
		/*
		LocalContainerEntityManagerFactoryBean localContainer =  new LocalContainerEntityManagerFactoryBean();
		localContainer.setDataSource(postGreeDS);
		localContainer.setPersistenceUnitName("postGree");
		localContainer.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		Map<String, String> additionalPros = new HashMap<>();
		additionalPros.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
		additionalPros.put("hibernate.hbm2ddl.auto", "update"); // Aquí agregas la propiedad
		additionalPros.put("spring.jpa.show-sql", "true"); // Aquí agregas la propiedad		
		localContainer.setJpaPropertyMap(additionalPros);
		localContainer.setPackagesToScan("com.saneamiento.models.entity");
		
				
		return localContainer;
		*/	
		
	}
	
	/*
	@Bean("postGressEMF")
	public LocalContainerEntityManagerFactoryBean postGreeEntityManagerFactory(@Qualifier("postGreeDataSource") DataSource postGreeDS) {
		LocalContainerEntityManagerFactoryBean localContainer =  new LocalContainerEntityManagerFactoryBean();
		localContainer.setDataSource(postGreeDS);
		localContainer.setPersistenceUnitName("postGree");
		localContainer.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		
		Map<String, String> additionalPros = new HashMap<>();
		additionalPros.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
		additionalPros.put("hibernate.hbm2ddl.auto", "update"); // Aquí agregas la propiedad
		additionalPros.put("spring.jpa.show-sql", "true"); // Aquí agregas la propiedad
		
		localContainer.setJpaPropertyMap(additionalPros);		
		localContainer.setPackagesToScan("com.saneamiento.models.entity");
				
		return localContainer;
		
	}
	*/
	
	@Bean("postGreeTrxManager")
	@Primary
	public JpaTransactionManager getPostGreeTrxManager(@Qualifier("postGressEMF") LocalContainerEntityManagerFactoryBean postGreeEMF ) {
		return new JpaTransactionManager(Objects.requireNonNull(postGreeEMF.getObject()));
	}
}
