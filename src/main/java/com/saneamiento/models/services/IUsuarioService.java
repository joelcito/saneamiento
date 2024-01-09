package com.saneamiento.models.services;

import java.util.List;

import com.saneamiento.models.entity.Rol;
import com.saneamiento.models.entity.Usuario;
import com.saneamiento.models.entity.UsuarioRol;

public interface IUsuarioService {
	
	//******** USUARIO ********
	public List<Usuario> findAll();
	
	public Usuario findById(Long id);
	
	public Usuario save(Usuario usuario);
	
	public List<UsuarioRol> getRolesUser(Usuario usuario);
	
	
	//******** USUARIO ROL ********
	int actualizarMenusUsuarioRol(Long usuarioRolId, String nuevosMenus);
	
	public UsuarioRol getPermisosUser(Usuario usuario, Rol rol);
	
	public int saveUsuarioRol(Usuario usuario, Rol rol, String menus);
	
	//public UsuarioRol getUsuarioRolById(Long id);

}
