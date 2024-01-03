package com.saneamiento.models.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.saneamiento.models.entity.Rol;
import com.saneamiento.models.entity.Usuario;
import com.saneamiento.models.entity.UsuarioRol;

public interface IUsuarioDao extends CrudRepository<Usuario, Long> {

	//public Usuario findByUsername(String username);
	
	@Query("select u from Usuario u where u.username=?1")
	public Usuario findByUsernameNative(String username);
	
	Optional<Usuario> findByUsername(String usrename);
	
	@Query("SELECT ur FROM UsuarioRol ur WHERE ur.usuario = :usuario AND ur.rol = :rol")
	public UsuarioRol getPermisosUser(@Param("usuario") Usuario usuario, @Param("rol") Rol rol);
		
}
