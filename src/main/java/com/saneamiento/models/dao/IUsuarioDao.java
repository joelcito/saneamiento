package com.saneamiento.models.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.saneamiento.models.entity.Usuario;

public interface IUsuarioDao extends CrudRepository<Usuario, Long> {

	//public Usuario findByUsername(String username);
	
	@Query("select u from Usuario u where u.username=?1")
	public Usuario findByUsernameNative(String username);
	
	Optional<Usuario> findByUsername(String usrename);
}
