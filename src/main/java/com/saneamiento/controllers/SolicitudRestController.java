package com.saneamiento.controllers;


import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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
import com.saneamiento.models.entity.SolicitudArchivo;
import com.saneamiento.models.entity.TemporalSolicitud;
import com.saneamiento.models.entity.Tramite;
import com.saneamiento.models.entity.Usuario;
import com.saneamiento.models.services.IFormularioService;
import com.saneamiento.models.services.ISolicitudService;
import com.saneamiento.models.services.IUsuarioService;

import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/api/solicitud")
@CrossOrigin(origins = {"http://localhost:4200", "*"}) 
public class SolicitudRestController {

	@Autowired
	private ISolicitudService solicitudService;
	
	@Autowired
	private IFormularioService formularioService;
	
	@Autowired
	private IUsuarioService usuarioService;
	
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
	
	
	@PostMapping("/saveSolicitudTemporal")
	public Solicitud saveSolicitudTemporal(@RequestBody Map<String, Object> requestbody) {

		String serialExtRegistros = requestbody.get("serialExtRegistros").toString();
		String estado             = requestbody.get("estado").toString();
		
		Extranjeria ex =  this.solicitudService.buscaSerialExtranjero(serialExtRegistros);		
				
		if(ex == null) {			
			String serialDocumentoExtRegistros =  requestbody.get("serialDocumentoExtRegistros").toString();
			String nroCedulaBolExtRegistros =  requestbody.get("nroCedulaBolExtRegistros").toString();
			this.solicitudService.saveExtranjeria(serialExtRegistros, serialDocumentoExtRegistros, nroCedulaBolExtRegistros);
			ex =  this.solicitudService.buscaSerialExtranjero(serialExtRegistros);
		}
		
		Long formulario_id 		= Long.parseLong(requestbody.get("formulario_id").toString());
		Formulario formBuscado 	= this.formularioService.findById(formulario_id);
		
		Long solicitante_id 		= Long.parseLong(requestbody.get("funcionario_id").toString());
		Usuario usuarioSolicitante  =  this.usuarioService.findById(solicitante_id);
		
		Long tipo_solicitud_id = Long.parseLong(requestbody.get("tipo_solicitud").toString());

		// ******************************** DE AQUI COMIENZA LO BUENO QUE ES LA ASIGNACION SIMPLIFICADO ********************************
		Long solicitud_id 		= 0L;
		String tipoSistema 		= "extranjeria";
		Solicitud newsolicitud 	= generaSolicitud(formBuscado, usuarioSolicitante, ex, tipoSistema, estado, solicitud_id);
		// ******************************** DE AQUI TERMINA COMIENZA LO BUENO QUE ES LA ASIGNACION SIMPLIFICADO ********************************

		Solicitud solicitudGuardada = this.solicitudService.save(newsolicitud);
		Long      newSolicitudId    = solicitudGuardada.getId();
		
		//**************** PARA EL TRAMITE ****************
		this.solicitudService.saveTramite(tipo_solicitud_id, newSolicitudId);
		//Tramite tramiteBuscado = this.solicitudService.buscaByTipoSolicitudBySolicitudId(newSolicitudId , tipo_solicitud_id);
		this.solicitudService.buscaByTipoSolicitudBySolicitudId(newSolicitudId , tipo_solicitud_id);
	
		return solicitudGuardada;
		// return new Solicitud();
	}
	
	@PostMapping("/saveTemporalSolicitud")
	public TemporalSolicitud saveTemporalSolicitud(@RequestBody Map<String, Object> requestBody) {
		
		//System.out.println(requestBody);
		
		Long solicitud_id 		= Long.parseLong(requestBody.get("solicitud_id").toString());
		String campoModificado 	= (requestBody.get("campoModificado") == null)? "" 	: requestBody.get("campoModificado").toString();
		String datoActual 		= (requestBody.get("datoActual") == null)? "" 		: requestBody.get("datoActual").toString();
		String datoRespuesta 	= (requestBody.get("datoRespuesta") == null)? "" 	: requestBody.get("datoRespuesta").toString();
		String usuario_id		= (requestBody.get("funcionario_id") == null)? "" 	: requestBody.get("funcionario_id").toString();
		
		//System.out.println(solicitud_id);
		//System.out.println(campoModificado);
		//System.out.println(datoActual);
		//System.out.println(datoRespuesta);
		//System.out.println(usuario_id);
		
		List<TemporalSolicitud> lsiatdo = this.solicitudService.listadoTemporalSolicitud(campoModificado, datoActual, datoRespuesta, solicitud_id);
		
		//System.out.println(lsiatdo.size());		

		Instant instant = new Date().toInstant();
		LocalDateTime localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
		
		if(lsiatdo.size() == 0) {
			this.solicitudService.saveTemporalSolicitud(campoModificado, datoActual, datoRespuesta, solicitud_id, localDateTime, usuario_id);
		}
		
		this.solicitudService.eliminacionLogicaTemporalSolicitud(localDateTime, usuario_id, solicitud_id, campoModificado, datoRespuesta);
		
		return new TemporalSolicitud();
	}
	
	@PostMapping("/eliminacionLogicaTemporalSolicitudDeseleccion")
	public int eliminacionLogicaTemporalSolicitudDeseleccion(@RequestBody Map<String, Object> requestBody) {
		
		Instant instant = new Date().toInstant();
		LocalDateTime localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
				
		String nombreCampo 			= requestBody.get("nombreCampo").toString();
		String UsuarioEliminador 	= requestBody.get("usarioEliminador").toString();
		Long solicitud_id 			= Long.parseLong(requestBody.get("solicitud_id").toString());
		
		return this.solicitudService.eliminacionLogicaTemporalSolicitudDeseleccion(localDateTime, UsuarioEliminador, solicitud_id, nombreCampo);
	}
	
	@GetMapping("/getTemporalesByIdSolicitud/{solicitud_id}")
	public List<TemporalSolicitud> getTemporalesByIdSolicitud(@PathVariable Long solicitud_id){
		return this.solicitudService.getTemporalesByIdSolicitud(solicitud_id);
	}
	
	@PostMapping("/")
	public Solicitud save(@RequestBody Map<String, Object> requestbody) {
		//System.out.println(requestbody);
		String      serialExtRegistros = requestbody.get("serialExtRegistros").toString();
		Extranjeria ex                 = this.solicitudService.buscaSerialExtranjero(serialExtRegistros);
				
		if(ex == null) {			
			String serialDocumentoExtRegistros = requestbody.get("serialDocumentoExtRegistros").toString();
			String nroCedulaBolExtRegistros    = requestbody.get("nroCedulaBolExtRegistros").toString();
			this.solicitudService.saveExtranjeria(serialExtRegistros, serialDocumentoExtRegistros, nroCedulaBolExtRegistros);
			ex =  this.solicitudService.buscaSerialExtranjero(serialExtRegistros);
		}
		
		Long       formulario_id 	= Long.parseLong(requestbody.get("formulario_id").toString());
		Formulario formBuscado   	= this.formularioService.findById(formulario_id);
		
		Long    solicitante_id     	= Long.parseLong(requestbody.get("funcionario_id").toString());
		Usuario usuarioSolicitante 	= this.usuarioService.findById(solicitante_id);
		
		Long tipo_solicitud_id 		= Long.parseLong(requestbody.get("tipo_solicitud").toString());
		
		String estado 				= requestbody.get("estado").toString();
		Long solicitud_id 			= Long.parseLong(requestbody.get("solicitud_id").toString());
		
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
			Solicitud newsolicitud = generaSolicitud(formBuscado, usuarioSolicitante, ex, tipoSistema, estado, solicitud_id);
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
		       
      			
		return savedSolicitud;
	}
	
	@PostMapping("/saveSolicitudDesbloqueoDirectiva0082019")
	public Solicitud saveSolicitudDesbloqueoDirectiva0082019(@RequestBody Map<String, Object> requestbody) {
		
		System.out.println(requestbody);
		
		String serialExtRegistros =  requestbody.get("serialExtRegistros").toString();
		Extranjeria ex =  this.solicitudService.buscaSerialExtranjero(serialExtRegistros);	
		
		if(ex == null) {			
			String serialDocumentoExtRegistros = requestbody.get("serialDocumentoExtRegistros").toString();
			String nroCedulaBolExtRegistros    = requestbody.get("nroCedulaBolExtRegistros").toString();
			this.solicitudService.saveExtranjeria(serialExtRegistros, serialDocumentoExtRegistros, nroCedulaBolExtRegistros);
			ex =  this.solicitudService.buscaSerialExtranjero(serialExtRegistros);
		}
		
		Long       formulario_id = Long.parseLong(requestbody.get("formulario_id").toString());
		Formulario formBuscado   = this.formularioService.findById(formulario_id);
		
		Long    solicitante_id     = Long.parseLong(requestbody.get("funcionario_id").toString());
		Usuario usuarioSolicitante = this.usuarioService.findById(solicitante_id);
		
		Long tipo_solicitud_id = Long.parseLong(requestbody.get("tipo_solicitud").toString());
		
		String estado 				= requestbody.get("estado").toString();
		Long solicitud_id 			= Long.parseLong(requestbody.get("solicitud_id").toString());
		
		// ******************************** DE AQUI COMIENZA LO BUENO QUE ES LA ASIGNACION SIMPLIFICADO ********************************
		String    tipoSistema  = "extranjeria";
		Solicitud newsolicitud = generaSolicitud(formBuscado, usuarioSolicitante, ex, tipoSistema, estado, solicitud_id);
		// ******************************** DE AQUI TERMINA COMIENZA LO BUENO QUE ES LA ASIGNACION SIMPLIFICADO ********************************

		Solicitud savedSolicitud = this.solicitudService.save(newsolicitud);
		Long      newSolicitudId = savedSolicitud.getId();

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
	
	@PostMapping("/saveCorreccionesCIE")
	public  Solicitud saveCorreccionesCIE(@RequestBody Map<String, Object> requestBody) {
				
		String serialExtRegistros 	= requestBody.get("serialExtRegistros").toString();
				
		Extranjeria ex 				= this.solicitudService.buscaSerialExtranjero(serialExtRegistros);	
		
		if(ex == null) {			
			String serialDocumentoExtRegistros = requestBody.get("serialDocumentoExtRegistros").toString();
			String nroCedulaBolExtRegistros    = requestBody.get("nroCedulaBolExtRegistros").toString();
			this.solicitudService.saveExtranjeria(serialExtRegistros, serialDocumentoExtRegistros, nroCedulaBolExtRegistros);
			ex = this.solicitudService.buscaSerialExtranjero(serialExtRegistros);
		}
		
		Long       formulario_id 	= Long.parseLong(requestBody.get("formulario_id").toString());
		Formulario formBuscado   	= this.formularioService.findById(formulario_id);
		
		Long    solicitante_id     	= Long.parseLong(requestBody.get("funcionario_id").toString());
		Usuario usuarioSolicitante 	= this.usuarioService.findById(solicitante_id);
		
		Long tipo_solicitud_id 		= Long.parseLong(requestBody.get("tipo_solicitud").toString());
		
		String estado 				= requestBody.get("estado").toString();
		Long solicitud_id 			= Long.parseLong(requestBody.get("solicitud_id").toString());
		
		// ******************************** DE AQUI COMIENZA LO BUENO QUE ES LA ASIGNACION SIMPLIFICADO ********************************
		String    tipoSistema  = "extranjeria";
		Solicitud newsolicitud = generaSolicitud(formBuscado, usuarioSolicitante, ex, tipoSistema, estado, solicitud_id);
		// ******************************** DE AQUI TERMINA COMIENZA LO BUENO QUE ES LA ASIGNACION SIMPLIFICADO ********************************
		
		Solicitud savedSolicitud = this.solicitudService.save(newsolicitud);
		Long      newSolicitudId = savedSolicitud.getId();
		this.solicitudService.save(generaCodigoSolicitud(savedSolicitud, tipoSistema));
		
		this.solicitudService.saveTramite(tipo_solicitud_id, newSolicitudId);
		Tramite tramiteBuscado 		= this.solicitudService.buscaByTipoSolicitudBySolicitudId(newSolicitudId , tipo_solicitud_id);
		Long tramite_id 			= tramiteBuscado.getId();
		
		
		//**************** PARA EL TRAMITE DETALLE****************
    	requestBody.forEach((key, valor) -> {
			if( valor instanceof Boolean && (Boolean) valor) {    	
				
    			String[] parts = key.split("_");
    			
    			//System.out.println("*****************************");
    			//System.out.println(parts[1]);
    			String dato_actual   = ((requestBody.get("actual_"+parts[1]) != null) ? requestBody.get("actual_"+parts[1]).toString() : "" );
    			String dato_corregir = "";
    			if(parts[1].contains("Fec")) {    				
    				String fechaEnFormatoOriginal = requestBody.get("nuevo_"+parts[1]).toString();
    				LocalDate fecha = LocalDate.parse(fechaEnFormatoOriginal);
    				dato_corregir = fecha.format(DateTimeFormatter.ofPattern("d/M/yyyy"));

    			}else {
    				dato_corregir = ((requestBody.get("nuevo_"+parts[1]) != null) ? requestBody.get("nuevo_"+parts[1]).toString() : "" );	
    			}
    			this.solicitudService.saveTramiteDetalle(tramite_id, "actual_"+parts[1], dato_actual);
    			this.solicitudService.saveTramiteDetalle(tramite_id, "nuevo_"+parts[1], dato_corregir);
    		}   		
    	});
    	
		return savedSolicitud;
	}
	
	
	@PostMapping("/saveSolicitudBajaOrpeNaturalizacion")
	public Solicitud saveSolicitudBajaOrpeNaturalizacion(@RequestBody Map<String, Object> requestBody) {
		
		System.out.println(requestBody);
		
		
		String serialExtRegistros 	= requestBody.get("serialExtRegistros").toString();
		
		Extranjeria ex 				= this.solicitudService.buscaSerialExtranjero(serialExtRegistros);	
		
		if(ex == null) {			
			String serialDocumentoExtRegistros = requestBody.get("serialDocumentoExtRegistros").toString();
			String nroCedulaBolExtRegistros    = requestBody.get("nroCedulaBolExtRegistros").toString();
			this.solicitudService.saveExtranjeria(serialExtRegistros, serialDocumentoExtRegistros, nroCedulaBolExtRegistros);
			ex = this.solicitudService.buscaSerialExtranjero(serialExtRegistros);
		}	
		
		Long       formulario_id 	= Long.parseLong(requestBody.get("formulario_id").toString());
		Formulario formBuscado   	= this.formularioService.findById(formulario_id);
		
		Long    solicitante_id     	= Long.parseLong(requestBody.get("funcionario_id").toString());
		Usuario usuarioSolicitante 	= this.usuarioService.findById(solicitante_id);
		
		Long tipo_solicitud_id 		= Long.parseLong(requestBody.get("tipo_solicitud").toString());
		
		String estado 				= requestBody.get("estado").toString();
		Long solicitud_id 			= Long.parseLong(requestBody.get("solicitud_id").toString());
		
		// ******************************** DE AQUI COMIENZA LO BUENO QUE ES LA ASIGNACION SIMPLIFICADO ********************************
		String    tipoSistema  = "extranjeria";
		Solicitud newsolicitud = generaSolicitud(formBuscado, usuarioSolicitante, ex, tipoSistema, estado, solicitud_id);
		// ******************************** DE AQUI TERMINA COMIENZA LO BUENO QUE ES LA ASIGNACION SIMPLIFICADO ********************************
		
		Solicitud savedSolicitud = this.solicitudService.save(newsolicitud);
		Long      newSolicitudId = savedSolicitud.getId();
		this.solicitudService.save(generaCodigoSolicitud(savedSolicitud, tipoSistema));
		
		this.solicitudService.saveTramite(tipo_solicitud_id, newSolicitudId);
		Tramite tramiteBuscado 		= this.solicitudService.buscaByTipoSolicitudBySolicitudId(newSolicitudId , tipo_solicitud_id);
		Long tramite_id 			= tramiteBuscado.getId();
		
		//**************** PARA EL TRAMITE DETALLE****************		
		String naturalizacion = requestBody.get("naturalizacion").toString();
		String baja_orpe = requestBody.get("baja_orpe").toString();
		
		this.solicitudService.saveTramiteDetalle(tramite_id, "naturalizado", naturalizacion);
		this.solicitudService.saveTramiteDetalle(tramite_id, "baja_orpe", baja_orpe);
			
		return savedSolicitud;
	}
	
	@PostMapping("/sanearBajaOrpeNaturalizado")
//public Solicitud sanearBajaOrpeNaturalizado(@RequestBody Map<String, Object> requestBoy) 
	public Solicitud sanearBajaOrpeNaturalizado(@RequestBody List<Map<String, Object>>  requestBoy) {
			
		System.out.println(requestBoy);
		
		return new Solicitud();
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

	
	@PostMapping("/verificaSiTieneTramatiesEnviados")
	public List<Map<String, Object>> verificaSiTieneTramatiesEnviados(@RequestBody Map<String, Object> requestBody) {
		
		String serial = requestBody.get("serial").toString();
		Long detalle_tipo_saneo_id = Long.parseLong(requestBody.get("detalle_tipo_saneo_id").toString());
		
		return this.solicitudService.verificaSiTieneTramatiesEnviados(serial, detalle_tipo_saneo_id);
	}
	
	//
	@PostMapping("/saveSolicitudArchivo")
	public int saveSolicitudArchivo(@RequestBody Map<String, Object> requestBody) {
		
		//System.out.println(requestBody);
		
		Long solicitud_id 			= Long.parseLong(requestBody.get("solicitud").toString());
		String usuario_ud  			= requestBody.get("usuario_creador").toString();
		String gestion  			= requestBody.get("gestion").toString();
		String sistema  			= requestBody.get("sistema").toString();
		String mes  				= requestBody.get("mes").toString();
		//String fecha  = requestBody.get("fecha").toString();
		
		Date utilDate = new Date(); // Supongamos que la fecha es la fecha actual
		// Suponiendo que tienes un java.util.Date llamado utilDate
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());		

		String nombre_archivo  = requestBody.get("nombre_archivo").toString();
		String ETag  = requestBody.get("ETag").toString();
		String Location  = requestBody.get("Location").toString();
		String key  = requestBody.get("key").toString();
		String Bucket  = requestBody.get("Bucket").toString();
		
		Instant instant = new Date().toInstant();
		LocalDateTime localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
			
		//this.solicitudService.saveSolicitudArchivo(solicitudBuscado, "", gestion, sistema, mes, sqlDate, nombre_archivo, ETag, Location, key, Bucket, localDateTime);
		return this.solicitudService.saveSolicitudArchivo(solicitud_id, usuario_ud, gestion, sistema, mes, sqlDate, nombre_archivo, ETag, Location, key, Bucket, localDateTime);
		
	}
	
	@GetMapping("/getSolicitudArchivosById/{solicitud_id}")
	public List<SolicitudArchivo> getSolicitudArchivosById(@PathVariable Long solicitud_id) {
		return this.solicitudService.getSolicitudArchivosById(solicitud_id);
	}



	

	// ******************** FUNCIONES PRIVADAS ********************

	private Solicitud generaCodigoSolicitud(Solicitud solicitud, String sistema){
		String cod          = "";
		Long   solicitud_id = solicitud.getId();
		
		if(sistema.equals("extranjeria"))
			cod = "UNBD-E-"+solicitud_id;
		
		solicitud.setCodigo(cod);
		return solicitud;
	}

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
			//Long regla_id = Long.parseLong(primeroLista.get("id").toString());    			
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
    
	private Solicitud generaSolicitud(Formulario formBuscado, Usuario usuarioSolicitante, Extranjeria ex, String tipoSistema, String estado, Long solicitud_id) {

		//**************** PARA EL SOLICITUD ****************
		Solicitud newsolicitud = (estado.equals("ASIGNADO") && solicitud_id != 0)?  this.solicitudService.findById(solicitud_id) : new Solicitud();

		// Solicitud newsolicitud =  new Solicitud();
		newsolicitud.setFormulario(formBuscado);
		newsolicitud.setUsuario_creador(usuarioSolicitante.getId().toString());
		newsolicitud.setUsuarioSolicitante(usuarioSolicitante);
		Instant       instant       = new Date().toInstant();
		LocalDateTime localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
		newsolicitud.setFechaCreacion(localDateTime);
		newsolicitud.setFechaSolicitud(localDateTime);
		newsolicitud.setTabla_id(ex.getId().toString());
		newsolicitud.setSistema(tipoSistema);
		newsolicitud.setEstado(estado);	
				
		if(estado.equals("ASIGNADO")) {
			
			// ******************************** DE AQUI COMIENZA LO BUENO QUE ES LA ASIGNACION ********************************
			LocalDate fecha = localDateTime.toLocalDate();
			LocalTime hora  = localDateTime.toLocalTime();
			
			Boolean swIerable = true;
			List<Map<String, Object>> listaReglas = this.solicitudService.getReglasVigentes(hora.toString(), fecha, fecha);
			
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
			
		}else {
			
		}
				
		return newsolicitud;
		
    }
}
