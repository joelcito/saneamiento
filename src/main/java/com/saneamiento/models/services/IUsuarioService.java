package com.saneamiento.models.services;

import java.util.List;

import com.saneamiento.models.entity.Usuario;

public interface IUsuarioService {
	
	public List<Usuario> findAll();
	
	public Usuario findById(Long id);

}
