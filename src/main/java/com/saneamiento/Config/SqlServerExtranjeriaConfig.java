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
		basePackages = "com.saneamiento.models.extranjeria.repository",
		entityManagerFactoryRef = "sqlServerExtranjeriaEMF",
		transactionManagerRef = "sqlServerExtranjeriaTrxManager"
)
@EnableTransactionManagement
public class SqlServerExtranjeriaConfig {
	
	@Bean("sqlServerExtranjeriaEMF")
	public LocalContainerEntityManagerFactoryBean sqlServerExtranjeriaEntityManagerFactory(
																				@Qualifier("sqlServerExtranjeriaDataSource") DataSource sqlServerExtranjeriaDS,
																				EntityManagerFactoryBuilder builder
																				) {
		
		Map<String, String> additionalPros = new HashMap<>();
		//additionalPros.put("hibernate.dialect", "org.hibernate.dialect.SQLServer2012Dialect");
		//additionalPros.put("hibernate.dialect", "org.hibernate.dialect.SQLServer2012Dialect");
		//additionalPros.put("spring.jpa.show-sql", "true");
		//additionalPros.put("spring.datasource.hikari.sslProtocol", "TLSv1.2");
		
		// Agrega la propiedad para la consulta de prueba de conexión
        //additionalPros.put("hikari.connection-test-query", "SELECT 1");
		
		return builder.dataSource(sqlServerExtranjeriaDS)
				.persistenceUnit("sqlServerExtranjeria")
				.properties(additionalPros)
				.packages("com.saneamiento.models.extranjeria.entity")
				.build();
	}
	

	@Bean("sqlServerExtranjeriaTrxManager")
	public JpaTransactionManager getSqlServerExtranjeriaTrxManager(@Qualifier("sqlServerExtranjeriaEMF") LocalContainerEntityManagerFactoryBean sqlServerExtranjeriaEMF ) {
		return new JpaTransactionManager(Objects.requireNonNull(sqlServerExtranjeriaEMF.getObject()));
	}

}