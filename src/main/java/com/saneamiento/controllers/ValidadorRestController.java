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

import com.saneamiento.models.entity.Validador;
import com.saneamiento.models.services.IValidadorService;

@RestController
@RequestMapping("/api/validador")
@CrossOrigin(origins = {"http://localhost:4200"})
public class ValidadorRestController {

	@Autowired
	private IValidadorService validadorService;
		
	@GetMapping("/listado")
	public List<Validador> index() {
		return this.validadorService.findAll();			 
	}
	
	@PostMapping("/formulario")
	public Validador getValidador(@RequestBody Map<String, Object> requestBody) {
		
		System.out.println(requestBody.get("campo"));
		System.out.println(requestBody.get("formulario"));
		
		String campo = requestBody.get("campo").toString();
		String formulario = requestBody.get("formulario").toString();
		
		//Validador d = new Validador();
		Validador d = this.validadorService.findByCampoByFormulario(campo, formulario);
		
		return d;
		
	}
}
