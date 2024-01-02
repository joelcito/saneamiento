package com.saneamiento.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.saneamiento.models.entity.Validador;

public interface IValidadorDao extends CrudRepository<Validador, Long> {
	
	@Query("SELECT v FROM Validador v WHERE v.campo = :campo AND v.formulario = :formulario")
	public Validador findByCampoByFormulario(@Param("campo") String campo, @Param("formulario") String formulario);

}
