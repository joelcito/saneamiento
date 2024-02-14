package com.saneamiento.Config;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(
		basePackages = "com.saneamiento.models.comun.repository",
		entityManagerFactoryRef = "postGressRuiSegipEMF",
		transactionManagerRef = "postGreeRuiSegipTrxManager"
)
@EnableTransactionManagement
public class PostGreeRuiSegipJpaConfig {
	
	@Bean("postGressRuiSegipEMF")
	public LocalContainerEntityManagerFactoryBean postGreeEntityManagerFactory(
																				@Qualifier("postGreeRuiSegipDataSource") DataSource postGreeRuiSegipDS,
																				EntityManagerFactoryBuilder builder
																				) {
		
		Map<String, String> additionalPros = new HashMap<>();
		additionalPros.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
		//additionalPros.put("hibernate.hbm2ddl.auto", "update"); // Aquí agregas la propiedad
		//additionalPros.put("spring.jpa.show-sql", "true"); // Aquí agregas la propiedad
		
		return builder.dataSource(postGreeRuiSegipDS)
				.persistenceUnit("postGreeRuiSegip")
				.properties(additionalPros)
				.packages("com.saneamiento.models.comun.entity")
				.build();
		
	}
	
	@Bean("postGreeRuiSegipTrxManager")
	public JpaTransactionManager getPostGreeTrxManager(@Qualifier("postGressRuiSegipEMF") LocalContainerEntityManagerFactoryBean postGreeRuiSegipEMF ) {
		return new JpaTransactionManager(Objects.requireNonNull(postGreeRuiSegipEMF.getObject()));
	}

}
