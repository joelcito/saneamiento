package com.saneamiento.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.saneamiento.models.entity.Rol;


public interface IRolDao extends CrudRepository<Rol, Long> {
	
		
}
