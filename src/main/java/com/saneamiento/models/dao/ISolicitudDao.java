package com.saneamiento.models.dao;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.saneamiento.models.entity.Extranjeria;
import com.saneamiento.models.entity.Solicitud;
import com.saneamiento.models.entity.SolicitudArchivo;
import com.saneamiento.models.entity.SolicitudConversacion;
import com.saneamiento.models.entity.TemporalSolicitud;
import com.saneamiento.models.entity.Tramite;
import com.saneamiento.models.entity.Usuario;

public interface ISolicitudDao extends CrudRepository<Solicitud, Long> {
	
	//**************** SOLICITUD ****************
	@Query("SELECT so FROM Solicitud so WHERE so.UsuarioAsignado.id =:usuario_asignado_id ")
	//@Query(value = "SELECT s.id, s.descripcion, s.fecha_respuesta, s.fecha_solicitud, s.asignado_id, s.respuesta_id, s.solicitante_id, s.sistema, s.tabla_id, s.formulario_id, s.estado, s.fecha_creacion, s.fecha_eliminacion, s.fecha_modificacion, s.usuario_creador, s.usuario_eliminador, s.usuario_modificador FROM saneamiento.solicitud s WHERE s.asignado_id =:usuario_asignado_id ", nativeQuery = true)
	public List<Solicitud> listadoSolicitudesAsignados(@Param("usuario_asignado_id") Long usuario_asignado_id);

	@Query("SELECT so FROM Solicitud so WHERE so.UsuarioSolicitante.id =:usuario_solicitante_id ")
	public List<Solicitud> listadoSolicitudes(@Param("usuario_solicitante_id") Long usuario_solicitante_id);
	
	@Query(value = "SELECT t.* "
			+ "FROM saneamiento.extranjeria e INNER JOIN saneamiento.solicitud s "
			+ "  ON e.id = CAST(s.tabla_id AS BIGINT) INNER JOIN saneamiento.tramite t "
			+ "    ON s.id = t.solicitud_id "
			+ "WHERE e.serialextregistros = :serialextregistros AND t.detalle_tipo_saneo_id = :detalle_tipo_saneo_id", nativeQuery = true)
	public List<Map<String, Object>> verificaSiTieneTramatiesEnviados(@Param("serialextregistros") String serialextregistros, @Param("detalle_tipo_saneo_id") Long detalle_tipo_saneo_id);
	
	
	/*
	@Query( value =  "SELECT * "
			+ "FROM saneamiento.solicitud s "
			+ "WHERE s.asignado_id IN ( "
			+ "    SELECT u.id "
			+ "    FROM saneamiento.usuario u  "
			+ "    WHERE u.dependencia_id = :dependencia AND u.fecha_eliminacion is NULL "
			+ "  ) AND s.fecha_eliminacion IS null ORDER BY s.id DESC ", nativeQuery = true)
	*/
	@Query(value = "SELECT *  "
			+ "FROM saneamiento.solicitud s "
			+ "WHERE s.asignado_id IN ( "
			+ "    SELECT u.id "
			+ "    FROM saneamiento.usuario u  "
			+ "    WHERE u.dependencia_id = :dependencia AND u.fecha_eliminacion is NULL "
			+ "  ) OR s.solicitante_id IN ( "
			+ "  SELECT u.id "
			+ "    FROM saneamiento.usuario u  "
			+ "    WHERE u.dependencia_id = :dependencia AND u.fecha_eliminacion is NULL "
			+ "  ) "
			+ "   "
			+ "AND s.fecha_eliminacion IS NULL ORDER BY s.id DESC", nativeQuery = true)
	public List<Solicitud> listadoCasos(@Param("dependencia") String dependencia);
	
	
	
	//**************** TABLA EXTRANJENRIA ****************	
	//@Query(value = "SELECT * "
	@Query(value = "SELECT * , s.id as id_solicitud, s.estado as estado_solucitud "
				+ "FROM solicitud s INNER JOIN extranjeria e "
				+ "ON CAST(s.tabla_id AS BIGINT) = e.id INNER JOIN usuario u "
				+ "ON s.solicitante_id = u.id "
				+ "WHERE s.id = :solicitud_id", nativeQuery = true)
	public Map<String, Object> solicitudesPorIdExtranjero(@Param("solicitud_id") Long solicitud_id);
	
	@Query(value = "SELECT * "
			+ "FROM saneamiento.tramite t INNER JOIN saneamiento.tramite_detalle td "
			+ "   ON t.id = td.tramite_id "
			+ "WHERE t.solicitud_id = :solicitud_id", nativeQuery = true)
	public List<Map<String, Object>> tramitesSolicitudesByIdSolicitud(@Param("solicitud_id") Long solicitud_id);	
	
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
	
	
	//***************** PARA LA TABLA DE TEMPORALES DE LA SOLICITUD *****************
	@Modifying
	@Query(value ="INSERT INTO temporal_solicitud (campo, dato_actual, respuesta, solicitud_id, fecha_creacion, usuario_creador) VALUES (:campo, :dato_actual, :respuesta, :solicitud, :fechaCreacion, :usuario_creador)", nativeQuery = true)
	public int saveTemporalSolicitud(@Param("campo") String campo, @Param("dato_actual") String dato_actual, @Param("respuesta") String respuesta, @Param("solicitud") Long solicitud, @Param("fechaCreacion") LocalDateTime fechaCreacion, @Param("usuario_creador") String usuario_creador);
	
	//@Query("SELECT ts FROM TemporalSolicitud ts WHERE ts.campo = :campo AND ts.dato_actual = :dato_actual AND ts.respuesta = :respuesta AND ts.solicitud.id = :solicitud_id AND ts.fechaEliminacion = NULL")
	@Query("SELECT ts FROM TemporalSolicitud ts WHERE ts.campo = :campo AND ts.dato_actual = :dato_actual AND ts.respuesta = :respuesta AND ts.solicitud.id = :solicitud_id AND ts.fechaEliminacion is NULL")
	public List<TemporalSolicitud> listadoTemporalSolicitud(@Param("campo") String campo, @Param("dato_actual") String dato_actual, @Param("respuesta") String respuesta, @Param("solicitud_id") Long solicitud_id);
	
	@Modifying
	@Query("UPDATE TemporalSolicitud ts SET ts.fechaEliminacion = :fechaEliminacion, ts.UsuarioEliminador = :UsuarioEliminador WHERE (ts.solicitud.id = :solicitud_id AND ts.campo = :campo ) AND ts.respuesta != :respuesta")
	public int eliminacionLogicaTemporalSolicitud(@Param("fechaEliminacion") LocalDateTime fechaEliminacion, @Param("UsuarioEliminador") String UsuarioEliminador, @Param("solicitud_id") Long solicitud_id, @Param("campo") String campo, @Param("respuesta") String respuesta);
	
	@Modifying
	@Query("UPDATE TemporalSolicitud ts SET ts.fechaEliminacion = :fechaEliminacion, ts.UsuarioEliminador = :UsuarioEliminador WHERE ts.solicitud.id = :solicitud_id AND ts.campo = :campo AND ts.fechaEliminacion is null")
	public int eliminacionLogicaTemporalSolicitudDeseleccion(@Param("fechaEliminacion") LocalDateTime fechaEliminacion, @Param("UsuarioEliminador") String UsuarioEliminador, @Param("solicitud_id") Long solicitud_id, @Param("campo") String campo);
		
	@Query("SELECT ts FROM TemporalSolicitud ts WHERE ts.solicitud.id = :solicitud_id AND ts.fechaEliminacion is null")
	public List<TemporalSolicitud> getTemporalesByIdSolicitud(@Param("solicitud_id") Long solicitud_id);

	
	//***************** SOLICITUD ARCHIVOS *****************
	@Modifying
	@Query(value = "INSERT INTO solicitud_archivo (solicitud_id, usuario_creador, gestion, sistema, mes, fecha, nombre_archivo, eTag, location, key, bucket, fecha_creacion) "
							+ " VALUES (:solicitud, :usuario_creador, :gestion, :sistema, :mes, :fecha, :nombre_archivo, :ETag, :Location, :key, :Bucket, :fechaCreacion)", nativeQuery = true)
	public int saveSolicitudArchivo(@Param("solicitud") Long solicitud, @Param("usuario_creador") String usuario_creador, @Param("gestion") String gestion, @Param("sistema") String sistema, @Param("mes") String mes, @Param("fecha") Date fecha, @Param("nombre_archivo") String nombre_archivo, @Param("ETag") String ETag, @Param("Location") String Location, @Param("key") String key, @Param("Bucket") String Bucket, @Param("fechaCreacion") LocalDateTime fechaCreacion);
	
	@Query("SELECT sa FROM SolicitudArchivo sa WHERE sa.solicitud.id = :solicitud_id AND sa.fechaEliminacion is null")
	public List<SolicitudArchivo> getSolicitudArchivosById(@Param("solicitud_id") Long solicitud_id);
	
	
	//***************** SOLICITUD CONVERSACION *****************
	@Modifying
	@Query(value = "INSERT INTO solicitud_conversacion (usuario_creador, solicitud_id, usuario_id_respuesta, texto, estado, tipo, fecha_creacion) "
			+ " VALUES (:usuario_creador, :solicitud_id, :usuario_id_respuesta, :texto, :estado, :tipo, :fecha_creacion)" , nativeQuery = true)
	public int saveSolicitudConversacionRespuesta(@Param("usuario_creador") String usuario_creador, @Param("solicitud_id") Long solicitud_id, @Param("usuario_id_respuesta") Long usuario_id_respuesta, @Param("texto") String texto, @Param("estado") String estado, @Param("tipo") String tipo, @Param("fecha_creacion") LocalDateTime fecha_creacion);
	
	@Modifying
	@Query(value = "INSERT INTO solicitud_conversacion (usuario_creador, solicitud_id, usuario_id_solicitante, texto, estado, tipo, fecha_creacion) "
			+ " VALUES (:usuario_creador, :solicitud_id, :usuario_id_solicitante, :texto, :estado, :tipo, :fecha_creacion)" , nativeQuery = true)
	public int saveSolicitudConversacion(@Param("usuario_creador") String usuario_creador, @Param("solicitud_id") Long solicitud_id, @Param("usuario_id_solicitante") Long usuario_id_solicitante, @Param("texto") String texto, @Param("estado") String estado, @Param("tipo") String tipo, @Param("fecha_creacion") LocalDateTime fecha_creacion);
	
	@Query("SELECT sc FROM SolicitudConversacion sc WHERE sc.solicitud.id = :solicitud_id AND  sc.fechaEliminacion IS NULL ORDER BY sc.fechaCreacion DESC")
	public List<SolicitudConversacion> getSolicitudConversacionById(@Param("solicitud_id") Long solicitud_id);
	
}
