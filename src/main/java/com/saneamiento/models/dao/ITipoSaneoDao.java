package com.saneamiento.models.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.saneamiento.models.entity.DetalleTipoSaneo;
import com.saneamiento.models.entity.DocumentoDetalleTipoSaneo;
import com.saneamiento.models.entity.TipoDetalleTipoSaneo;
import com.saneamiento.models.entity.TipoSaneo;


public interface ITipoSaneoDao extends CrudRepository<TipoSaneo, Long>{
	
	
	@Modifying
    @Query("UPDATE TipoSaneo ts SET ts.fechaEliminacion = :fecha WHERE ts.id = :id")
    int deleteTipoSaneo(@Param("id") Long id, @Param("fecha") LocalDateTime fecha);
	
	@Query("SELECT ts FROM TipoSaneo ts WHERE ts.fechaEliminacion is NULL ORDER BY ts.id DESC")
	List<TipoSaneo> listadoRolVigentes();
	
	
	
	// ***************** DETALLE TIPO SANEO *****************
	
	@Query("SELECT dts FROM DetalleTipoSaneo dts WHERE dts.id = :detalle_tipo_saneo_id")
	public DetalleTipoSaneo findByIdDetalleTipoSaneo(@Param("detalle_tipo_saneo_id") Long detalle_tipo_saneo_id);
	
	
	@Query("SELECT dts FROM DetalleTipoSaneo dts WHERE dts.tipoSaneo.id = :detalle_tipo_saneo_id")
	public List<DetalleTipoSaneo> getDetalleTiposaneo(@Param("detalle_tipo_saneo_id") Long detalle_tipo_saneo_id);


	@Modifying
	@Query("INSERT INTO DetalleTipoSaneo (nombre, tipoSaneo) VALUES (:nombre, :tipoSaneo)")
	int saveByNombreAndTipoSaneo(@Param("nombre") String nombre, @Param("tipoSaneo") TipoSaneo tipoSaneo);
	
	
	
	// ***************** DOCUMENTO DETALLE TIPO SANEO *****************
	@Query("SELECT ddts FROM DocumentoDetalleTipoSaneo ddts WHERE ddts.detalleTipoSaneo.id = :detalle_tipo_saneo_id")
	public List<DocumentoDetalleTipoSaneo> getDocumentoDetalleTipoSaneo(@Param("detalle_tipo_saneo_id") Long detalle_tipo_saneo_id);
	
	@Query("SELECT ddts FROM DocumentoDetalleTipoSaneo ddts WHERE ddts.id = :documento_detalle_tipo_saneo_id")
	public DocumentoDetalleTipoSaneo findByIdDocumentoDetalleTipoSaneo(@Param("documento_detalle_tipo_saneo_id") Long documento_detalle_tipo_saneo_id);
	
	@Modifying
	@Query("INSERT INTO DocumentoDetalleTipoSaneo (nombre, detalleTipoSaneo) VALUES (:nombre, :detalleTipoSaneo)")
	public int saveDocumentoDetalleTipoSaneo(@Param("nombre") String nombre, @Param("detalleTipoSaneo") DetalleTipoSaneo detalleTipoSaneo);
	
	@Query("SELECT ts.id AS idTipoSaneo, ts.nombre AS nombreTipoSaneo, dts.id AS idDetalleTipoSaneo, dts.nombre AS nombreDetalleTipoSaneo " +
		    "FROM TipoSaneo ts INNER JOIN DetalleTipoSaneo dts ON ts.id = dts.tipoSaneo.id WHERE dts.id = :detalleTipoSaneoId")
	public Object[] getTipoSaneoDetalle(@Param("detalleTipoSaneoId") Long detalleTipoSaneoId);

	
	// ***************** TIPO DETALLE TIPO SANEO *****************
	//@Query("SELECT tdts FROM TipoDetalleTipoSaneo tdts")
	@Query("SELECT tdts FROM TipoDetalleTipoSaneo tdts WHERE tdts.visible = true")
	public List<TipoDetalleTipoSaneo> getTiposDetallesTipoSaneo();

}