package com.saneamiento.models.services;


import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ComunServiceImpl {
	
	 private final JdbcTemplate jdbcTemplate;

	    public ComunServiceImpl(@Qualifier("postGreeComunDataSource") DataSource dataSource) {
	        this.jdbcTemplate = new JdbcTemplate(dataSource);
	    }

	    @Transactional(readOnly = true)
	    public Map<String, Object> getAllDataFromComunDatabase(String cedula, String complemento) {
	    	
	    	String sql = "SELECT * FROM funcionario.fun_func_dato_catalogo WHERE num_cedula = ? LIMIT 10";
	    	
	    	try {
	    		return jdbcTemplate.queryForMap(sql, cedula);	
			} catch (EmptyResultDataAccessException  e) {
				// TODO: handle exception
				return null;
			}
	        
	    	
	    	/*
	    	String sql = "SELECT " +
	                "  num_cedula," +
	                "  complemento," +
	                "  complemento_impreso," +
	                "  complemento_impresoi," +
	                "  nombres," +
	                "  primer_apellido," +
	                "  segundo_apellido," +
	                "  fecha_nacimiento," +
	                "  nombre_organizacion," +
	                "  nombre_dependencia," +
	                "  nombre_cargo," +
	                "  id_ciud," +
	                "  id_funcionario," +
	                "  es_activo," +
	                "  id_jer_func," +
	                "  id_jer_org," +
	                "  id_dependencia," +
	                "  id_fun_dep" +
	                //" FROM funcionario.fun_func_dato_catalogo" +
	                " FROM fun_func_dato_catalogo" +
	                " WHERE num_cedula = :cedula ";
	    	
	    	if(complemento.length() > 0)
				sql += " AND complemento = :complemento ";
			
	    	sql += " LIMIT 1 ";
	    	
	    	if(complemento.length() > 0)
	    		return jdbcTemplate.queryForMap(sql, cedula, complemento);
	    	else
	    		return jdbcTemplate.queryForMap(sql, cedula);
	    	
	    	*/
	    	
	        //String sql = "SELECT * FROM funcionario.fun_funcionario ff";
	        //return jdbcTemplate.queryForList(sql);
	    }
}
