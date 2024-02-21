package com.saneamiento.models.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.saneamiento.models.entity.Rol;


public interface IRolDao extends CrudRepository<Rol, Long> {
	
	@Modifying
    @Query("UPDATE Rol r SET r.fechaEliminacion = :fecha WHERE r.id = :id")
    int deleteUsuario(@Param("id") Long id, @Param("fecha") LocalDateTime fecha);
	
	@Query("SELECT r FROM Rol r WHERE r.fechaEliminacion is NULL ORDER BY r.id DESC")
	List<Rol> listadoRolVigentes();
	
		
}
