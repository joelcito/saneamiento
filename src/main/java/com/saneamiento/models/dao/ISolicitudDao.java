package com.saneamiento.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.saneamiento.models.entity.Solicitud;

public interface ISolicitudDao extends CrudRepository<Solicitud, Long> {
	
}
