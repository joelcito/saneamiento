package com.saneamiento.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saneamiento.models.entity.TipoSaneo;
import com.saneamiento.models.services.ITipoSaneoService;

@RestController
@RequestMapping("/api/tipoSaneos")
public class TipoSaneoRestController {
	
	@Autowired
	private ITipoSaneoService tipoSaneoService;
	
	@GetMapping("")
	public List<TipoSaneo> index() {
		return tipoSaneoService.findAll();
	}
}
