package com.saneamiento.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saneamiento.models.entity.Formulario;
import com.saneamiento.models.services.IFormularioService;

@RestController
@RequestMapping("/api/formulario")
@CrossOrigin(origins = {"http://localhost:4200"})
public class FormularioRestController {
	@Autowired
	private IFormularioService formularioService;
	
	@GetMapping("/")
	private List<Formulario> name() {
		return this.formularioService.findAll();
	}

}
