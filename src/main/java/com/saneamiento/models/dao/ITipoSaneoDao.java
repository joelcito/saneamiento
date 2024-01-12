package com.saneamiento.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.saneamiento.models.entity.DetalleTipoSaneo;
import com.saneamiento.models.entity.TipoSaneo;


public interface ITipoSaneoDao extends CrudRepository<TipoSaneo, Long>{
	
	// ***************** DETALLE TIPO SANEO *****************
	/*
	@Query("SELECT dts FROM DetalleTipoSaneo dts WHERE dts.tipo_saneo_id = :detalle_tipo_saneo_id")
	public List<DetalleTipoSaneo> getDetalleTiposaneo(@Param("detalle_tipo_saneo_id") Long detalle_tipo_saneo_id);
	*/
	
	@Query("SELECT dts FROM DetalleTipoSaneo dts WHERE dts.tipoSaneo.id = :detalle_tipo_saneo_id")
	public List<DetalleTipoSaneo> getDetalleTiposaneo(@Param("detalle_tipo_saneo_id") Long detalle_tipo_saneo_id);


	@Modifying
	@Query("INSERT INTO DetalleTipoSaneo (nombre, tipoSaneo) VALUES (:nombre, :tipoSaneo)")
	int saveByNombreAndTipoSaneo(@Param("nombre") String nombre, @Param("tipoSaneo") TipoSaneo tipoSaneo);
		
}