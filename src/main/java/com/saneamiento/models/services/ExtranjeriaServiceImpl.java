package com.saneamiento.models.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
public class ExtranjeriaServiceImpl {

	
	// ****************** PARA LA CONEXION A EXGRANJERIA ******************
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
    	
		//System.out.println(datos);
		
		StringBuilder sql = new StringBuilder("SELECT * FROM ExtRegistros er INNER JOIN SegUsuarios su ON er.UsuModExtRegistros = su.LoginSegUsuarios WHERE 1 = 1");
		
		/*
		if(datos.containsKey("numero_cedula") && datos.get("numero_cedula") != null && !datos.get("numero_cedula").equals("")) {
			
			System.out.println("haber aqu");
			
			if(true) {
				sql.append(" AND er.NroCedulaBolExtRegistros = '").append(datos.get("numero_cedula")).append("'");
			}else {
				
			}
			
		}else {
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
		}   
		
		   */
	  
		
			//StringBuilder sql = new StringBuilder("SELECT * FROM ExtRegistros er INNER JOIN SegUsuarios su ON er.UsuModExtRegistros = su.LoginSegUsuarios WHERE 1=1");
			  	  
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
		  	  	  
		  //System.out.println(sql.toString());
	  
		
	  // Ejecutar la consulta
	  return jdbcTemplate.queryForList(sql.toString());
 	
	}
    
    @Transactional(readOnly = true)
    public Map<String, Object> buscarporSerialExtranjero(Map<String, Object> datos){
    	
    	StringBuilder sql = new StringBuilder("SELECT * FROM ExtRegistros er WHERE 1=1");
    	sql.append(" AND er.SerialExtRegistros = '").append(datos.get("serial")).append("'");
    	
    	return jdbcTemplate.queryForMap(sql.toString());
    }
    
    @Transactional
    public Map<String, Object> saneoCambioBandeja(Map<String, Object> datos) {
    	
    	//System.out.println(datos);
    	String tipo 				= datos.get("tipo_cambio").toString();
    	String serialExtRegistros 	= datos.get("serialExtRegistros").toString();
    	String nroCedulaExt 		= datos.get("nro_cedula").toString();
    	String idUnicoExt 			= datos.get("id_unico_extr").toString();
    	
    	 return jdbcTemplate.queryForMap(
	            "DECLARE @resultado INT; EXEC @resultado = dbo.sp_dbw_CambiodeBandeja ?, ?, ?, ?; SELECT @resultado AS Resultado;",
	            	tipo, serialExtRegistros, nroCedulaExt, idUnicoExt
    	        );
	}
    
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getDatosParametricas(Map<String, Object> requestBody) {
		StringBuilder sql = new StringBuilder("SELECT * FROM ");
		sql.append(requestBody.get("tabla")).append(" WHERE 1 = 1 ");
    	return jdbcTemplate.queryForList(sql.toString());		
    }
    
    
    @Transactional(readOnly = true)
    public List<Map<String, Object>> saneoCorrecionCIESqlServer(Map<String, Object> requestBody) {
    	
    	//System.out.println(requestBody);    	
    	
    	String serialExtRegistros = requestBody.get("serialExtRegistros").toString();
    	String nro_cedula = requestBody.get("nro_cedula").toString();
    	String id_unico = requestBody.get("id_unico").toString();
    	
    	List<Map<String, Object>> listado = (List<Map<String, Object>>) requestBody.get("modificacion"); 
    	
    	List<Map<String, Object>> respuestas = new ArrayList();
    	
    	for(Map<String, Object> da : listado) {
    		
    		//System.out.println("*************************************");    		
    		//System.out.println(da);    		
    		String campo 		= da.get("campo").toString();
    		String actual 		= da.get("actual").toString();
    		String modificar = da.get("modificar").toString();
    		
    		if(campo.contains("Fec")) {
    			String fechaEnFormatoOriginal = modificar;
    	        LocalDate fecha = LocalDate.parse(fechaEnFormatoOriginal, DateTimeFormatter.ofPattern("d/M/yyyy"));
    	        modificar = fecha.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));   			
    		}
    		
    		//System.out.println(campo);
    		//System.out.println(actual);
    		//System.out.println(modificar);
    		
    		Map<String, Object> respuesta = new HashMap();
    		
    		Map<String, Object> dest = jdbcTemplate.queryForMap(
					//"EXEC  f_dbw_UpdateExtregistros N'100398980', N'1Ubzxt1I4n', N'8532', N'NombresExtRegistros', N'ISAO ', N'JOEL FLORES';"
					"EXEC  f_dbw_UpdateExtregistros ?, ?, ?, ?, ?;"
					,nro_cedula, serialExtRegistros, id_unico, campo, modificar
					);
    		//System.out.println(dest);
    		
    		respuesta.put("campo",campo);
    		respuesta.put("respuesta",dest.get("PROCESADO"));
    		respuesta.put("mensaje",(((boolean) dest.get("PROCESADO"))? "¡SI FUE MODIFICADO!" : "¡NO FUE MODIFICADO!"));   		
    		
    		respuestas.add(respuesta);
   				
    	}
    	    	
    	return respuestas;
    }
    
    
    @Transactional(readOnly = true)
    public Map<String, Object> buscaPorNumdeposito(Map<String, Object> requestBody) {
    	
    	System.out.println(requestBody);
   	
    	StringBuilder sql = new StringBuilder("SELECT COUNT(*) as cantidad FROM ExtRegistros e WHERE 1=1");
    	//sql.append(" AND e.NumDepositoExtRegistros = '").append(requestBody.get("numeroBoleta")).append("'");
    	sql.append(" AND e.").append(requestBody.get("nombre_campo")).append(" = '").append(requestBody.get("numeroBoleta")).append("'");
    	
    	System.out.println(sql);
    	
    	return jdbcTemplate.queryForMap(sql.toString());
	}

}
