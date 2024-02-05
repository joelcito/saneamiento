package com.saneamiento.models.dao;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.saneamiento.models.entity.Extranjeria;
import com.saneamiento.models.entity.Regla;
import com.saneamiento.models.entity.Solicitud;
import com.saneamiento.models.entity.Tramite;

public interface ISolicitudDao extends CrudRepository<Solicitud, Long> {
	
	//**************** SOLICITUD ****************
	@Query("SELECT so FROM Solicitud so WHERE so.UsuarioAsignado.id =:usuario_asignado_id ")
	public List<Solicitud> listadoSolicitudes(@Param("usuario_asignado_id") Long usuario_asignado_id);
	
	//**************** TABLA EXTRANJENRIA ****************
	@Query("SELECT ex FROM Extranjeria ex WHERE ex.serialExtRegistros =:serialExtRegistros")
	public Extranjeria buscaSerialExtranjero(@Param("serialExtRegistros") String serialExtRegistros);
	
	@Modifying
	@Query("INSERT INTO Extranjeria (serialExtRegistros, serialDocumentoExtRegistros, nroCedulaBolExtRegistros) VALUES (:serialExtRegistros, :serialDocumentoExtRegistros, :nroCedulaBolExtRegistros)")	
	public int saveExtranjeria(@Param("serialExtRegistros") String serialExtRegistros, @Param("serialDocumentoExtRegistros") String serialDocumentoExtRegistros, @Param("nroCedulaBolExtRegistros") String nroCedulaBolExtRegistros);
	
	
	//**************** TABLA TRAMITE ****************
	@Modifying
	@Query(value = "INSERT INTO tramite (detalle_tipo_saneo_id, solicitud_id) VALUES (:detalle_tipo_saneo_id, :solicitud_id)", nativeQuery = true)
	public int saveTramite(@Param("detalle_tipo_saneo_id") Long detalle_tipo_saneo_id, @Param("solicitud_id") Long solicitud_id);
	
	@Query("SELECT tr FROM Tramite tr WHERE tr.solicitud.id = :solicitud_id AND tr.detalleTipoSaneo.id = :detalle_tipo_saneo_id")
	public Tramite buscaByTipoSolicitudBySolicitudId(@Param("solicitud_id") Long solicitud_id, @Param("detalle_tipo_saneo_id") Long detalle_tipo_saneo_id);
	
	
	//**************** TABLA TRAMITE DETALLE ****************
	@Modifying
	@Query(value = "INSERT INTO tramite_detalle (tramite_id, pregunta, respuesta) VALUES (:tramite_id, :pregunta, :respuesta)", nativeQuery = true)
	public int saveTramiteDetalle(@Param("tramite_id") Long tramite_id, @Param("pregunta") String pregunta, @Param("respuesta") String respuesta);
		
	//***************** PARA LA ASIGNACION AUTOMATICA *****************
	@Query(value = "SELECT * FROM regla r " +
            "WHERE EXISTS (" +
            "  SELECT 1 FROM unnest(string_to_array(regexp_replace(r.horario_id, '[\\[\\]]', '', 'g'), ',')) AS elem " +
            "  WHERE CAST(elem AS integer) IN (" +
            "    SELECT sh.id FROM horario sh " +
            "    WHERE sh.hora_ini <= CAST(:horaParam AS time) AND sh.hora_fin >= CAST(:horaParam AS time)" +
            "  )" +
            ") AND r.fecha_ini BETWEEN :startDate AND :endDate AND r.estado = TRUE " +
            "ORDER BY r.asignacion ASC", nativeQuery = true)
	public List<Map<String, Object>> getReglasVigentes(@Param("horaParam") String horaParam,@Param("startDate") LocalDate startDate,@Param("endDate") LocalDate endDate);
	

	@Modifying
    @Query("UPDATE Regla r SET r.asignacion = :asignacion WHERE r.id = :id")
    int updateReglaAsignacion(@Param("asignacion") String asignacion, @Param("id") Long id);

	
}
