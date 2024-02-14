package com.saneamiento.Config;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DataSourcesConfig {

	// ******************  ESTO ES PARA EL PRIMARIO ****************** 
	@Bean("postGressProperties")
	@ConfigurationProperties("spring.datasource")
	public DataSourceProperties getPostGreeProperties() {
		return new DataSourceProperties();
	} 
	
	@Bean("postGreeDataSource")
	public DataSource getPostGreeDataSource() {
		return getPostGreeProperties().initializeDataSourceBuilder().build();
	}
	
	// ******************  ESTO ES PARA EL COMUN ******************
	@Bean("postGressComunProperties")
	@ConfigurationProperties("spring.datasource.comun")
	public DataSourceProperties getPostGreeComunProperties() {
		return new DataSourceProperties();
	} 
	
	@Bean("postGreeComunDataSource")
	public DataSource getPostGreeComunDataSource() {
		return getPostGreeComunProperties().initializeDataSourceBuilder().build();
	}
	
	
	
	// ******************  ESTO ES PARA BUSCAR EXTRANJERO SQL SERVER ******************
	@Bean("sqlServerExtranjeriaProperties")
	@ConfigurationProperties("spring.datasource.extranjeriabuscaper")
	public DataSourceProperties getSqlServerExtranjeriaProperties() {
		return new DataSourceProperties();
	} 
	
	@Bean("sqlServerExtranjeriaDataSource")
	public DataSource getSqlServerExtranjeriaDataSource() {
		DataSourceProperties properties = getSqlServerExtranjeriaProperties();
        HikariDataSource dataSource = properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
        dataSource.setConnectionTestQuery("SELECT 1");  // Configura la consulta de prueba de conexión
        return dataSource;		
		//return getSqlServerExtranjeriaProperties().initializeDataSourceBuilder().build();
	}
	
	// ******************  ESTO ES PARA EL COMUN ******************
	@Bean("postGressRuiSegipProperties")
	@ConfigurationProperties("spring.datasource.ruisegip")
	public DataSourceProperties getPostGreeRuiSegipProperties() {
		return new DataSourceProperties();
	} 
	
	@Bean("postGreeRuiSegipDataSource")
	public DataSource getPostGreeRuiSegipDataSource() {
		return getPostGreeRuiSegipProperties().initializeDataSourceBuilder().build();
	}	
}
