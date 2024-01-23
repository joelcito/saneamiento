package com.saneamiento.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
}
