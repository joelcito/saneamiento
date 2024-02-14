package com.saneamiento.models.services;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RuiSegipServiceImpl {
		
	private final JdbcTemplate jdbcTemplate;
	
	public RuiSegipServiceImpl(@Qualifier("postGreeRuiSegipDataSource") DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
	
	@Transactional(readOnly = true)
	public Map<String, Object> liberarPciExtranjero(String cie){
		
		String sql = "SELECT r.* as estado FROM saneo_ruisegip.fn_db_libera_pci_libre_bloqueado("+cie+") r";
		//String sql = "SELECT r.* FROM saneo_ruisegip.fn_db_libera_pci_libre_bloqueado(100398980) as r ";
		
		return jdbcTemplate.queryForMap(sql);
	}
	
	@Transactional(readOnly = true)
	public Map<String, Object> buscarPersona(String cedula) {
		String sql = "SELECT * FROM public.persona p WHERE p.pci = '"+cedula+"'";
		
		try {
            return jdbcTemplate.queryForMap(sql);
        } catch (EmptyResultDataAccessException e) {
            // Manejar la situación en la que no se encuentra ningún registro
            return null; // O podrías lanzar una excepción personalizada
        }
		
		//return jdbcTemplate.queryForMap(sql);
	}
	
	@Transactional(readOnly = true)
	public Map<String, Object> verificarSiestaBloqueado(String numero) {
		String sql = "SELECT * FROM catalogo_segip.pci_libre_bloqueado p WHERE p.nro_ci_libre = '"+numero+"'";
		try {
            return jdbcTemplate.queryForMap(sql);
        } catch (EmptyResultDataAccessException e) {
            return null; // O podrías lanzar una excepción personalizada
        }		
	}
	
}
