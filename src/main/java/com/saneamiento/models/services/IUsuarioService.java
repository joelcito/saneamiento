package com.saneamiento.models.services;

import java.util.List;

import com.saneamiento.models.entity.Rol;
import com.saneamiento.models.entity.Usuario;
import com.saneamiento.models.entity.UsuarioRol;

public interface IUsuarioService {
	
	public List<Usuario> findAll();
	
	public Usuario findById(Long id);
	
	public UsuarioRol getPermisosUser(Usuario usuario, Rol rol);

}
