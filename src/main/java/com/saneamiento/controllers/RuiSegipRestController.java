package com.saneamiento.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saneamiento.models.services.RuiSegipServiceImpl;

@RestController
@RequestMapping("/api/ruisegip")
@CrossOrigin(origins = {"http://localhost:4200"})
public class RuiSegipRestController {
	
	@Autowired
	private RuiSegipServiceImpl ruiSegipService;
	
	@PostMapping("/liberarPciExtranjero")
	public Map<String, Object> liberarPciExtranjero(@RequestBody Map<String, Object> requestBody) {
		String cie =   requestBody.get("cie").toString();
		return this.ruiSegipService.liberarPciExtranjero(cie);
	}
	
	@GetMapping("/verificarSiestaBloqueado/{numero_cedula}")
	public Map<String, Object> verificarSiestaBloqueado(@PathVariable String numero_cedula) {
		System.out.println(numero_cedula);
		return this.ruiSegipService.verificarSiestaBloqueado(numero_cedula);
	}

}
