package com.saneamiento.models.services;

import java.util.List;

import com.saneamiento.models.entity.Validador;

public interface IValidadorService {
	
	public List<Validador> findAll();
	
	public Validador findByCampoByFormulario(String campo, String formulario);

}
