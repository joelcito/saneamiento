package com.saneamiento.models.services;

import java.util.List;

import com.saneamiento.models.entity.Solicitud;

public interface ISolicitudService {
	
	public List<Solicitud> findAll();
	
	public Solicitud save(Solicitud solicitud);
	
	public Solicitud findById(Long id);
	
}
