package com.saneamiento.controllers;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saneamiento.models.entity.Extranjeria;
import com.saneamiento.models.entity.Formulario;
import com.saneamiento.models.entity.Solicitud;
import com.saneamiento.models.entity.Tramite;
import com.saneamiento.models.entity.Usuario;
import com.saneamiento.models.services.IFormularioService;
import com.saneamiento.models.services.ISolicitudService;
import com.saneamiento.models.services.IUsuarioService;



@RestController
@RequestMapping("/api/solicitud")
@CrossOrigin(origins = {"http://localhost:4200"})
public class SolicitudRestController {

	@Autowired
	private ISolicitudService solicitudService;
	
	@Autowired
	private IFormularioService formularioService;
	
	@Autowired
	private IUsuarioService usuarioService;
	
	//@GetMapping("/listado")
	@PostMapping("/listado")
	public List<Solicitud> listado(@RequestBody Map<String, Object> requestBody){
		Long user_id = Long.parseLong(requestBody.get("id").toString());
		List<Solicitud> solicitudes = this.solicitudService.listadoSolicitudes(user_id);
	    // Sort the list in descending order based on a specific field
	    Collections.sort(solicitudes, Comparator.comparing(Solicitud::getId).reversed());
	    return solicitudes;
	}
	
	//**************** PARA EL LISTADO DE TRAMITES SEGUN SOLICITUDES ****************
	
	@GetMapping("/tramitesSolicitudesByIdSolicitud/{solicitud_id}")
	public List<Map<String, Object>> tramitesSolicitudesByIdSolicitud(@PathVariable Long solicitud_id){
		return this.solicitudService.tramitesSolicitudesByIdSolicitud(solicitud_id);		
	}
	
	//**************** END PARA EL LISTADO DE TRAMITES SEGUN SOLICITUDES ****************
	
	//**************** PARA EL LISTADO DE ASIGNACIONES ****************
	@PostMapping("/asignacion/listado")
	public List<Solicitud> listadoAsignaciones(@RequestBody Map<String, Object> requestBody) {
		
		Long user_id = Long.parseLong(requestBody.get("id").toString());
		
		List<Solicitud> solicitudes = this.solicitudService.listadoSolicitudesAsignados(user_id);
			
		// Sort the list in descending order based on a specific field
		Collections.sort(solicitudes, Comparator.comparing(Solicitud::getId).reversed());

	    return solicitudes;	    
		
		//return Collections.emptyList();
		
	}
	
	@GetMapping("/{id}")
	//public Solicitud findByIdsolicitud(@PathVariable Long id) {
	public Map<String, Object> findByIdsolicitud(@PathVariable Long id) {
		
		return this.solicitudService.solicitudesPorIdExtranjero(id);
		
		//System.out.println(id);
		//return this.solicitudService.findById(id);
	}
	
	@PostMapping("/")
	public Solicitud save(@RequestBody Map<String, Object> requestbody) {
		//System.out.println(requestbody);
		String serialExtRegistros =  requestbody.get("serialExtRegistros").toString();
		Extranjeria ex =  this.solicitudService.buscaSerialExtranjero(serialExtRegistros);		
				
		if(ex == null) {			
			String serialDocumentoExtRegistros =  requestbody.get("serialDocumentoExtRegistros").toString();
			String nroCedulaBolExtRegistros =  requestbody.get("nroCedulaBolExtRegistros").toString();
			this.solicitudService.saveExtranjeria(serialExtRegistros, serialDocumentoExtRegistros, nroCedulaBolExtRegistros);
			ex =  this.solicitudService.buscaSerialExtranjero(serialExtRegistros);
		}
		
		Long formulario_id = Long.parseLong(requestbody.get("formulario_id").toString());
		Formulario formBuscado 	= this.formularioService.findById(formulario_id);
		
		Long solicitante_id = Long.parseLong(requestbody.get("funcionario_id").toString());
		Usuario usuarioSolicitante  =  this.usuarioService.findById(solicitante_id);
		
		Long tipo_solicitud_id = Long.parseLong(requestbody.get("tipo_solicitud").toString());
		
		
		// //**************** PARA EL SOLICITUD ****************
		// Solicitud newsolicitud =  new Solicitud();
		// newsolicitud.setFormulario(formBuscado);
		// newsolicitud.setUsuarioSolicitante(usuarioSolicitante);
		// // Convert Date to LocalDateTime
		// Instant instant = new Date().toInstant();
		// LocalDateTime localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();

		// newsolicitud.setFechaSolicitud(localDateTime);
		// newsolicitud.setTabla_id(ex.getId().toString());
		// newsolicitud.setSistema("extranjeria");
		
		// // ******************************** DE AQUI COMIENZA LO BUENO QUE ES LA ASIGNACION ********************************
		// // Obtener la fecha y la hora por separado
		
        // LocalDate fecha = localDateTime.toLocalDate();
        // LocalTime hora = localDateTime.toLocalTime();		
		
        // //LocalDate fecha = LocalDate.of(2024, 2, 5);
        // //LocalTime hora = LocalTime.of(19, 0);
        // //LocalTime hora = LocalTime.of(20, 0,1);
        // //LocalTime hora = LocalTime.of(23, 59,59);

        
        // //System.out.println(horaEspecifica);
        
        // Boolean swIerable = true;
        
        // List<Map<String, Object>> listaReglas = this.solicitudService.getReglasVigentes(hora.toString(), fecha, fecha);
        
        // System.out.println(listaReglas.size());
        
        // if(listaReglas.size() > 1) {       	
        	
        // 	newsolicitud = asignarUsuarioSegunReglas(listaReglas, swIerable, newsolicitud);
        	
        // 	/*
        // 	Iterator<Map<String, Object>> iterator = listaReglas.iterator();
        // 	while (iterator.hasNext() && swIerable) {
        // 		Map<String, Object> r = iterator.next();
        // 		if(r.get("asignacion").toString().equals("0")) {
        // 			Long usuario_regla_id = Long.parseLong(r.get("usuario_id").toString());
        // 			Usuario usuAsiganado = this.usuarioService.findById(usuario_regla_id);
        // 			newsolicitud.setUsuarioAsignado(usuAsiganado);
        // 			swIerable =  false;
        // 			Long regla_id = Long.parseLong(r.get("id").toString());
        // 			this.solicitudService.updateReglaAsignacion("1", regla_id);
        // 			//System.out.println("rols => "+regla_id);
        // 		}else {
        			
        // 		}
        // 	    //System.out.println(r.get("id") + " " + r.get("usuario_id"));
        // 	}
        // 	//System.out.println(swIerable);
        // 	if(swIerable) {
        // 		Map<String, Object> primeroLista = listaReglas.get(0);
        // 		Long usuario_regla_id = Long.parseLong(primeroLista.get("usuario_id").toString());
    	// 		Usuario usuAsiganado = this.usuarioService.findById(usuario_regla_id);
    	// 		newsolicitud.setUsuarioAsignado(usuAsiganado);    			
    	// 		//swIerable =  false;
    	// 		Long regla_id = Long.parseLong(primeroLista.get("id").toString());    			
    	// 		for (Map<String, Object> r : listaReglas) {
    	// 			if(!(r.get("id").equals(primeroLista.get("id").toString()))) {
    	// 				Long aCerear = Long.parseLong(r.get("id").toString());
    	// 				this.solicitudService.updateReglaAsignacion("0", aCerear);	
    	// 				//System.out.println("se cero => "+regla_id);
    	// 			}   				
    	//         	//System.out.println(r.get("id")+" "+r.get("usuario_id"));
    	// 		}        		
        // 	}else {
        		
        // 	}
        // 	*/
        // }else {
        	
        // 	// Definir el rango de la tarde
        //     LocalTime limiteInicioTarde 	= LocalTime.of(12, 0, 0);
        //     LocalTime limiteFinTarde 		= LocalTime.of(23, 59, 59);
            
            
        	
        //     //if (hora.isAfter(limiteInicioTarde) && hora.isBefore(limiteFinTarde)) {
        // 	if ( (hora.isAfter(limiteInicioTarde) && hora.isBefore(limiteFinTarde)) ||  (hora.equals(limiteInicioTarde) || hora.equals(limiteFinTarde))) {
        //         //System.out.println("La hora es por la tarde. "+ hora);
        //         // Avanzar al día siguiente
        //         LocalDate fechaSiguiente = fecha.plusDays(1);
        //         hora 		= LocalTime.of(7, 31, 59);
        //         //System.out.println("Fecha actual: " + fecha);
        //     	//System.out.println("Fecha siguiente: " + fechaSiguiente);        		
        // 		listaReglas = this.solicitudService.getReglasVigentes(hora.toString(), fechaSiguiente, fechaSiguiente);        		
        // 		newsolicitud = asignarUsuarioSegunReglas(listaReglas, swIerable, newsolicitud);                
        // 	}else {   
        // 		hora 		= LocalTime.of(7, 31, 59);
        // 		listaReglas = this.solicitudService.getReglasVigentes(hora.toString(), fecha, fecha);        		
        // 		newsolicitud = asignarUsuarioSegunReglas(listaReglas, swIerable, newsolicitud);        		
        // 		/*
        // 		Iterator<Map<String, Object>> iterator = listaReglas.iterator();
        //     	while (iterator.hasNext() && swIerable) {
        //     		Map<String, Object> r = iterator.next();
        //     		if(r.get("asignacion").toString().equals("0")) {
        //     			Long usuario_regla_id = Long.parseLong(r.get("usuario_id").toString());
        //     			Usuario usuAsiganado = this.usuarioService.findById(usuario_regla_id);
        //     			newsolicitud.setUsuarioAsignado(usuAsiganado);
        //     			swIerable =  false;
        //     			Long regla_id = Long.parseLong(r.get("id").toString());
        //     			this.solicitudService.updateReglaAsignacion("1", regla_id);
        //     		}else {
            			
        //     		}
        //     	}
        //     	if(swIerable) {
        //     		Map<String, Object> primeroLista = listaReglas.get(0);
        //     		Long usuario_regla_id = Long.parseLong(primeroLista.get("usuario_id").toString());
        // 			Usuario usuAsiganado = this.usuarioService.findById(usuario_regla_id);
        // 			newsolicitud.setUsuarioAsignado(usuAsiganado);
        // 			Long regla_id = Long.parseLong(primeroLista.get("id").toString());    			
        // 			for (Map<String, Object> r : listaReglas) {
        // 				if(!(r.get("id").equals(primeroLista.get("id").toString()))) {
        // 					Long aCerear = Long.parseLong(r.get("id").toString());
        // 					this.solicitudService.updateReglaAsignacion("0", aCerear);
        // 				}       	        
        // 			}
        //     	}else {
            		
        //     	}  
        //     	*/      		
        //         //System.out.println("La hora no es por la tarde. "+hora);                
        // 	}        	      	        	
        // }
        // // ******************************** DE AQUI TERMINA COMIENZA LO BUENO QUE ES LA ASIGNACION ********************************
		
		
		// ******************************** DE AQUI COMIENZA LO BUENO QUE ES LA ASIGNACION SIMPLIFICADO ********************************
		String tipoSistema = "extranjeria";
		Solicitud newsolicitud = generaSolicitud(formBuscado, usuarioSolicitante, ex, tipoSistema);
		// ******************************** DE AQUI TERMINA COMIENZA LO BUENO QUE ES LA ASIGNACION SIMPLIFICADO ********************************
        
        
     
		
		Solicitud savedSolicitud = this.solicitudService.save(newsolicitud);
		Long newSolicitudId = savedSolicitud.getId();
		
		//**************** PARA EL TRAMITE ****************
		this.solicitudService.saveTramite(tipo_solicitud_id, newSolicitudId);
		Tramite tramiteBuscado = this.solicitudService.buscaByTipoSolicitudBySolicitudId(newSolicitudId , tipo_solicitud_id);
		//System.out.println("*****************************");
		//System.out.println(tramiteBuscado);
		//System.out.println(tipo_solicitud_id);
		//System.out.println(newSolicitudId);
		//System.out.println("*****************************");
		Long tramite_id = tramiteBuscado.getId();
		
		
		//**************** PARA EL TRAMITE DETALLE****************
		requestbody.forEach((key, valor) -> {
			// Verificar si el key contiene la palabra "respuesta"
		    if (key.toLowerCase().contains("_respuesta")) {
		        //System.out.println("La clave contiene la palabra 'respuesta'");
		    	this.solicitudService.saveTramiteDetalle(tramite_id, key.toString(), valor.toString());
		    	//System.out.println("key: "+key +" | valor: "+valor);
		    }
		});
		       
        
        /*
		System.out.println(localDateTime);
		System.out.println(fecha);
		System.out.println(hora);
		*/				
				
		return savedSolicitud;
	}
	
	@PostMapping("/saveSolicitudDesbloqueoDirectiva0082019")
	public Solicitud saveSolicitudDesbloqueoDirectiva0082019(@RequestBody Map<String, Object> requestbody) {
		
		System.out.println(requestbody);
		
		String serialExtRegistros =  requestbody.get("serialExtRegistros").toString();
		Extranjeria ex =  this.solicitudService.buscaSerialExtranjero(serialExtRegistros);	
		
		if(ex == null) {			
			String serialDocumentoExtRegistros =  requestbody.get("serialDocumentoExtRegistros").toString();
			String nroCedulaBolExtRegistros =  requestbody.get("nroCedulaBolExtRegistros").toString();
			this.solicitudService.saveExtranjeria(serialExtRegistros, serialDocumentoExtRegistros, nroCedulaBolExtRegistros);
			ex =  this.solicitudService.buscaSerialExtranjero(serialExtRegistros);
		}
		
		Long formulario_id = Long.parseLong(requestbody.get("formulario_id").toString());
		Formulario formBuscado 	= this.formularioService.findById(formulario_id);
		
		Long solicitante_id = Long.parseLong(requestbody.get("funcionario_id").toString());
		Usuario usuarioSolicitante  =  this.usuarioService.findById(solicitante_id);
		
		Long tipo_solicitud_id = Long.parseLong(requestbody.get("tipo_solicitud").toString());

		// ******************************** DE AQUI COMIENZA LO BUENO QUE ES LA ASIGNACION SIMPLIFICADO ********************************
		String tipoSistema = "extranjeria";
		Solicitud newsolicitud = generaSolicitud(formBuscado, usuarioSolicitante, ex, tipoSistema);
		// ******************************** DE AQUI TERMINA COMIENZA LO BUENO QUE ES LA ASIGNACION SIMPLIFICADO ********************************

		Solicitud savedSolicitud = this.solicitudService.save(newsolicitud);
		Long newSolicitudId = savedSolicitud.getId();

		this.solicitudService.saveTramite(tipo_solicitud_id, newSolicitudId);
		Tramite tramiteBuscado = this.solicitudService.buscaByTipoSolicitudBySolicitudId(newSolicitudId , tipo_solicitud_id);
		//System.out.println("*****************************");
		//System.out.println(tramiteBuscado);
		//System.out.println(tipo_solicitud_id);
		//System.out.println(newSolicitudId);
		//System.out.println("*****************************");
		Long tramite_id = tramiteBuscado.getId();

		//**************** PARA EL TRAMITE DETALLE****************
		requestbody.forEach((key, valor) -> {
			// Verificar si el key contiene la palabra "respuesta"
		    if (key.toLowerCase().contains("_respuesta")) {
		        //System.out.println("La clave contiene la palabra 'respuesta'");
		    	this.solicitudService.saveTramiteDetalle(tramite_id, key.toString(), valor.toString());
		    	//System.out.println("key: "+key +" | valor: "+valor);
		    }
		});	
				
		return savedSolicitud;
	}
	
	@PostMapping("/sanearDirectiva0082019")
	public Solicitud sanearDirectiva0082019(@RequestBody Map<String, Object> requestBody) {

		Long solicitud_id 		= Long.parseLong(requestBody.get("solicitud").toString());		
		Solicitud soliBuscado 	= this.solicitudService.findById(solicitud_id);
		
		Long usuario_id  		= Long.parseLong(requestBody.get("usuario").toString());
		Usuario usuarioBuscado	= this.usuarioService.findById(usuario_id);
		
		soliBuscado.setEstado("PROCESADO");
		soliBuscado.setUsuarioRespuesta(usuarioBuscado);
		soliBuscado.setUsuario_modificador(usuario_id.toString());
		
		Instant instant = new Date().toInstant();
		LocalDateTime localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();

		soliBuscado.setFechaRespuesta(localDateTime);
		soliBuscado.setFechaModificacion(localDateTime);
		
		this.solicitudService.save(soliBuscado);
		
		return soliBuscado;		
		
	}

	/*
	@PostMapping("/saneoCambioBandeja")
	public Solicitud saneoCambioBandeja(@RequestBody Map<String, Object> requestBody) {
		
		System.out.println(requestBody);
		
		return new Solicitud();
	}
	*/
	
	@PostMapping("/verificaSiTieneTramatiesEnviados")
	public List<Map<String, Object>> verificaSiTieneTramatiesEnviados(@RequestBody Map<String, Object> requestBody) {
		
		String serial = requestBody.get("serial").toString();
		Long detalle_tipo_saneo_id = Long.parseLong(requestBody.get("detalle_tipo_saneo_id").toString());
		
		return this.solicitudService.verificaSiTieneTramatiesEnviados(serial, detalle_tipo_saneo_id);
	}



	

	// ******************** FUNCIONES PRIVADAS ********************
	// Función privada para asignar usuario según reglas
    private Solicitud asignarUsuarioSegunReglas(List<Map<String, Object>> listaReglas , Boolean swIerable, Solicitud newsolicitud) {
    	
    	System.out.println(listaReglas.size());
    	
    	Iterator<Map<String, Object>> iterator = listaReglas.iterator();
    	while (iterator.hasNext() && swIerable) {
    		Map<String, Object> r = iterator.next();
    		if(r.get("asignacion").toString().equals("0")) {
    			Long usuario_regla_id = Long.parseLong(r.get("usuario_id").toString());
    			Usuario usuAsiganado = this.usuarioService.findById(usuario_regla_id);
    			newsolicitud.setUsuarioAsignado(usuAsiganado);
    			swIerable =  false;
    			Long regla_id = Long.parseLong(r.get("id").toString());
    			this.solicitudService.updateReglaAsignacion("1", regla_id);
    			//System.out.println("rols => "+regla_id);
    		}else {
    			
    		}
    	    //System.out.println(r.get("id") + " " + r.get("usuario_id"));
    	}
    	//System.out.println(swIerable);
    	if(swIerable) {
    		Map<String, Object> primeroLista = listaReglas.get(0);
    		Long usuario_regla_id = Long.parseLong(primeroLista.get("usuario_id").toString());
			Usuario usuAsiganado = this.usuarioService.findById(usuario_regla_id);
			newsolicitud.setUsuarioAsignado(usuAsiganado);    			
			//swIerable =  false;
			Long regla_id = Long.parseLong(primeroLista.get("id").toString());    			
			for (Map<String, Object> r : listaReglas) {
				if(!(r.get("id").equals(primeroLista.get("id").toString()))) {
					Long aCerear = Long.parseLong(r.get("id").toString());
					this.solicitudService.updateReglaAsignacion("0", aCerear);	
					//System.out.println("se cero => "+regla_id);
				}   				
	        	//System.out.println(r.get("id")+" "+r.get("usuario_id"));
			}        		
    	}else {
    		
       	}
    	return newsolicitud;
    }

    
    private Solicitud generaSolicitud(Formulario formBuscado, Usuario usuarioSolicitante, Extranjeria ex, String tipoSistema) {
		//**************** PARA EL SOLICITUD ****************
		Solicitud newsolicitud =  new Solicitud();
		newsolicitud.setFormulario(formBuscado);
		newsolicitud.setUsuarioSolicitante(usuarioSolicitante);
		Instant instant = new Date().toInstant();
		LocalDateTime localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
		newsolicitud.setFechaSolicitud(localDateTime);
		newsolicitud.setTabla_id(ex.getId().toString());
		newsolicitud.setSistema(tipoSistema);
		newsolicitud.setEstado("ASIGNADO");
		// ******************************** DE AQUI COMIENZA LO BUENO QUE ES LA ASIGNACION ********************************
		LocalDate fecha = localDateTime.toLocalDate();
		LocalTime hora = localDateTime.toLocalTime();	


		Boolean swIerable = true;
		List<Map<String, Object>> listaReglas = this.solicitudService.getReglasVigentes(hora.toString(), fecha, fecha);

		// System.out.println("********************************");
		// System.out.println(fecha);
		// System.out.println(hora);
		// System.out.println(listaReglas.size());
		// System.out.println("********************************");

		
		if(listaReglas.size() > 0){
			newsolicitud = asignarUsuarioSegunReglas(listaReglas, swIerable, newsolicitud);
		}else{
			LocalTime limiteInicioTarde 	= LocalTime.of(12, 0, 0);
			LocalTime limiteFinTarde 		= LocalTime.of(23, 59, 59);
			if ( (hora.isAfter(limiteInicioTarde) && hora.isBefore(limiteFinTarde)) ||  (hora.equals(limiteInicioTarde) || hora.equals(limiteFinTarde))) {
				LocalDate fechaSiguiente = fecha.plusDays(1);
							hora           = LocalTime.of(7, 31, 59);
							listaReglas    = this.solicitudService.getReglasVigentes(hora.toString(), fechaSiguiente, fechaSiguiente);
							newsolicitud   = asignarUsuarioSegunReglas(listaReglas, swIerable, newsolicitud);
			}else{
				hora         = LocalTime.of(7, 31, 59);
				listaReglas  = this.solicitudService.getReglasVigentes(hora.toString(), fecha, fecha);
				newsolicitud = asignarUsuarioSegunReglas(listaReglas, swIerable, newsolicitud);
			}
		}
		return newsolicitud;
    }
}
