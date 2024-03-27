package com.saneamiento.models.entity;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "solicitud_archivo")
public class SolicitudArchivo implements Serializable{


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "solicitud_id")
	private Solicitud solicitud;
	
	@Column(name="usuario_creador", length = 10)
	private String usuario_creador;
	
	@Column(name="usuario_modificador", length = 10)
	private String usuario_modificador;
	
	@Column(name="usuario_eliminador", length = 10)
	private String usuario_eliminador;
	
	private String gestion;
	
	private String sistema;
	
	private String mes;
	
	private Date fecha;
	
	private String nombre_archivo;
	
	private String ETag;
	
	private String Location;
	
	private String key;
	
	private String Bucket;
	
	private String tipo_archivo;
	
	@ManyToOne
	@JoinColumn(name = "conversacion_id")
	private SolicitudConversacion conversacion;
	
	@Column(name = "fecha_creacion", columnDefinition = "TIMESTAMP")
    private LocalDateTime fechaCreacion;
	
	@Column(name = "fecha_modificacion", columnDefinition = "TIMESTAMP")
    private LocalDateTime fechaModificacion;

	@Column(name = "fecha_eliminacion", columnDefinition = "TIMESTAMP")
    private LocalDateTime fechaEliminacion;
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
}
