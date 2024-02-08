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

import com.saneamiento.models.services.ExtranjeriaServiceImpl;

@RestController
@RequestMapping("/api/extranjeria")
@CrossOrigin(origins = {"http://localhost:4200"})
public class ExtranjeriaRestController {

	@Autowired
	private ExtranjeriaServiceImpl extranjeriaSerice;
	
	@GetMapping("/datos")
	public List<Map<String, Object>> index(){
		return this.extranjeriaSerice.listadoChe();
	}
	
	@PostMapping("/buscaExtranjero")
	public List<Map<String, Object>> buscaExtranjero(@RequestBody Map<String, Object> requestBody) {
		return this.extranjeriaSerice.buscaExtranjero(requestBody);
	}
	
	@PostMapping("/buscaExtranjeroPorSerial")
	public Map<String, Object> buscarporSerialExtranjero(@RequestBody Map<String, Object> requestBody) {
		return this.extranjeriaSerice.buscarporSerialExtranjero(requestBody);
	}
	
	
	
	/*
	@PostMapping("/getImagenExtranjero")
	public ResponseEntity<byte[]> getImagenExtranjero(@RequestBody Map<String, Object> requestBody) throws SQLException {
		return this.extranjeriaSerice.getImagenExtranjero(requestBody);
	}
	*/
}

