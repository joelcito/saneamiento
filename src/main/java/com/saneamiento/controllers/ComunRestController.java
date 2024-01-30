package com.saneamiento.controllers;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saneamiento.models.services.ComunServiceImpl;


@RestController
@RequestMapping("/api/comun")
@CrossOrigin(origins = {"http://localhost:4200"})
public class ComunRestController {
	
	@Autowired
	private ComunServiceImpl comunService;
	
	@PostMapping("/datos")
	public Map<String, Object> getAllDataFromComunDatabase(@RequestBody Map<String, Object> requestBody) {
		//System.out.println(requestBody); 
		String cedula = requestBody.get("cedula").toString();
		String complemneto = requestBody.get("complemento").toString();
		return this.comunService.getAllDataFromComunDatabase(cedula,complemneto);
	}

}
