package com.saneamiento.models.dao;

import java.security.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.saneamiento.models.entity.Rol;
import com.saneamiento.models.entity.Usuario;
import com.saneamiento.models.entity.UsuarioRol;

public interface IUsuarioDao extends CrudRepository<Usuario, Long> {

	//public Usuario findByUsername(String username);
	
	// ********* USUARIO ********* 
	@Query("select u from Usuario u where u.username=?1")
	public Usuario findByUsernameNative(String username);
	
	Optional<Usuario> findByUsername(String usrename);	
	
	@Modifying
    @Query("UPDATE Usuario u SET u.fechaEliminacion = :fecha WHERE u.id = :id")
    int deleteUsuario(@Param("id") Long id, @Param("fecha") LocalDateTime fecha);
	
	@Query("SELECT u FROM Usuario u WHERE u.fechaEliminacion is NULL ORDER BY u.id DESC")
	List<Usuario> listadoUsuarioVigentes();
	
	
	// ********* USUARIO ROL ********* 
	@Query("SELECT ur FROM UsuarioRol ur WHERE ur.usuario = :usuario AND ur.rol = :rol")
	public UsuarioRol getPermisosUser(@Param("usuario") Usuario usuario, @Param("rol") Rol rol);
		
	@Modifying
	@Query("INSERT INTO UsuarioRol (usuario, rol, menus) VALUES (:usuario, :rol, :menus)")	
	public int saveUsuarioRol(@Param("usuario") Usuario usuario, @Param("rol") Rol rol, @Param("menus") String menus);
	
	@Query("SELECT ur FROM UsuarioRol ur WHERE ur.usuario = :usuario")
	public List<UsuarioRol> getRolesUser(@Param("usuario") Usuario usuario);
	
	@Modifying
	@Query("UPDATE UsuarioRol ur SET ur.menus = :nuevosMenus WHERE ur.id = :usuarioRolId")
	int actualizarMenusUsuarioRol(@Param("usuarioRolId") Long usuarioRolId, @Param("nuevosMenus") String nuevosMenus);
		

}
