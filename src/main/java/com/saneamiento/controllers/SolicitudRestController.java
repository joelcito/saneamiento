package com.saneamiento.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saneamiento.models.entity.Solicitud;
import com.saneamiento.models.services.ISolicitudService;

@RestController
@RequestMapping("/api/solicitud")
@CrossOrigin(origins = {"http://localhost:4200"})
public class SolicitudRestController {

	@Autowired
	private ISolicitudService solicitudService;
	
	@GetMapping("/listado")
	public List<Solicitud> listado(){
		return this.solicitudService.findAll();
	}
	
	@PostMapping("/")
	public Solicitud save(@RequestBody Map<String, Object> requestbody) {
		//System.out.println(requestbody);
		
		Map<String, Object> datosFormularioSolicitud = (Map<String, Object>) requestbody.get("datosFormularioSolicitud");
		
		System.out.println(datosFormularioSolicitud);
		
		return new Solicitud();
	}
}
