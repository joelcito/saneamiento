package com.saneamiento.models.services;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
public class ExtranjeriaServiceImpl {

	
	private final JdbcTemplate jdbcTemplate;

    public ExtranjeriaServiceImpl(@Qualifier("sqlServerExtranjeriaDataSource") DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    @Transactional(readOnly = true)
    public List<Map<String, Object>> listadoChe(){
    	String sql = "SELECT * FROM ExtRegistros er WHERE er.NroCedulaBolExtRegistros = '0070144'";
        return jdbcTemplate.queryForList(sql);
    }
    
    @Transactional(readOnly = true)
    public List<Map<String, Object>> buscaExtranjero(Map<String, Object> datos) {
    	
	  //StringBuilder sql = new StringBuilder("SELECT * FROM ExtRegistros er INNER JOIN ExtImagenes ei ON ei.SerialExtRegistros = er.SerialExtRegistros WHERE 1=1");
	  //StringBuilder sql = new StringBuilder("SELECT * FROM ExtRegistros er WHERE 1=1");
	  StringBuilder sql = new StringBuilder("SELECT * FROM ExtRegistros er INNER JOIN SegUsuarios su ON er.UsuModExtRegistros = su.LoginSegUsuarios WHERE 1=1");	  
	  if (datos.containsKey("numero_cedula") && datos.get("numero_cedula") != null && !datos.get("numero_cedula").equals("")) {
	     sql.append(" AND er.NroCedulaBolExtRegistros = '").append(datos.get("numero_cedula")).append("'");
	  }
	  
	  if (datos.containsKey("complemento") && datos.get("complemento") != null && !datos.get("complemento").equals("")) {
	     sql.append(" AND er.HexadecimalExtRegistros = '").append(datos.get("complemento")).append("'");
	  }
	  
	  if (datos.containsKey("nombres") && datos.get("nombres") != null && !datos.get("nombres").equals("")) {
	     sql.append(" AND er.NombresExtRegistros = '").append(datos.get("nombres")).append("'");
	  }
	  
	  if (datos.containsKey("primer_apellido") && datos.get("primer_apellido") != null && !datos.get("primer_apellido").equals("")) {
	     sql.append(" AND er.PrimerApExtRegistros = '").append(datos.get("primer_apellido")).append("'");
	  }
	  
	  if (datos.containsKey("segundo_apellido") && datos.get("segundo_apellido") != null && !datos.get("segundo_apellido").equals("")) {
	     sql.append(" AND er.SegundoApExtRegistros = '").append(datos.get("segundo_apellido")).append("'");
	  }
	  
	  //sql.append(" AND ei.CodigoExtTipoImagen = 3");
	  	  
	  System.out.println(sql.toString());
	  
	  /*
	  System.out.println(datos);
	  System.out.println(datos.get("complemento") != null);
	  System.out.println(datos.containsKey("complemento"));
	  System.out.println(!datos.get("complemento").equals(""));
	  System.out.println(!datos.get("numero_cedula").equals(""));
	  */
	  
	  // Ejecutar la consulta
	  return jdbcTemplate.queryForList(sql.toString());
 	
	}
    
    @Transactional(readOnly = true)
    public Map<String, Object> buscarporSerialExtranjero(Map<String, Object> datos){
    	
    	StringBuilder sql = new StringBuilder("SELECT * FROM ExtRegistros er WHERE 1=1");
    	sql.append(" AND er.SerialExtRegistros = '").append(datos.get("serial")).append("'");
    	
    	return jdbcTemplate.queryForMap(sql.toString());
    }
    
    
    /*
    public ResponseEntity<byte[]> getImagenExtranjero(Map<String, Object> datos) {
    	
    	String id =  datos.get("numero_cedula").toString();
    	
        byte[] imageData = imageService.getImageDataById(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG); // Ajusta el tipo de contenido según tu necesidad

        return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
    }
    */    
    /*
    @Transactional
    public ResponseEntity<byte[]> getImagenExtranjero(Map<String, Object> datos) throws SQLException {
    	
    	String id =  datos.get("numero_cedula").toString();
    	byte[] imageData = jdbcTemplate.queryForObject(
				"SELECT ValorExtImagenes FROM ExtImagenes WHERE SerialExtRegistros = ? AND CodigoExtTipoImagen = 3",
		        new Object[]{id},
		        byte[].class
		);

		if (imageData != null) {
		    HttpHeaders headers = new HttpHeaders();
		    headers.setContentType(MediaType.IMAGE_JPEG);
		    return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
		} else {
		    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}
	*/
    
        
    /*
    @Transactional(readOnly = true)
    public Map<String, Object>  getImagenExtranjero(Map<String, Object> datos) {
    	
    	String cedua = datos.get("numero_cedula").toString();
    	
    	String sql = "SELECT * FROM ExtRegistros er WHERE er.NroCedulaBolExtRegistros = '"+cedua+"' AND CodigoExtTipoImagen = '3'";
    	 	
    	
		return jdbcTemplate.queryForMap(sql);
	}
	*/
    

}
