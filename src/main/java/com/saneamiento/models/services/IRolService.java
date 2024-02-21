package com.saneamiento.models.services;

import java.time.LocalDateTime;
import java.util.List;


import com.saneamiento.models.entity.Rol;

public interface IRolService {

	public List<Rol> findAll();
	
	public Rol findById(Long id);
	
	public Rol save(Rol rol);
	
	int deleteUsuario(Long id, LocalDateTime fecha);
	
	public  List<Rol> listadoRolVigentes();
}
