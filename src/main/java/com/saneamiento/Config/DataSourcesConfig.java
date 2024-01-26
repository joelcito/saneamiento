package com.saneamiento.Config;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourcesConfig {

	@Bean("postGressProperties")
	@ConfigurationProperties("spring.datasource")
	public DataSourceProperties getPostGreeProperties() {
		return new DataSourceProperties();
	} 
	
	@Bean("postGreeDataSource")
	public DataSource getPostGreeDataSource() {
		return getPostGreeProperties().initializeDataSourceBuilder().build();
	}
	
	@Bean("postGressComunProperties")
	@ConfigurationProperties("spring.datasource.comun")
	public DataSourceProperties getPostGreeComunProperties() {
		return new DataSourceProperties();
	} 
	
	@Bean("postGreeComunDataSource")
	public DataSource getPostGreeComunDataSource() {
		return getPostGreeComunProperties().initializeDataSourceBuilder().build();
	}
	
}
