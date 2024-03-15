package com.saneamiento.models.entity;

import java.io.Serializable;
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

@Data
@Entity
@Table(name = "solicitud_conversacion")
public class SolicitudConversacion implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="usuario_creador", length = 10)
	private String usuario_creador;
	
	@Column(name="usuario_modificador", length = 10)
	private String usuario_modificador;
	
	@Column(name="usuario_eliminador", length = 10)
	private String usuario_eliminador;
	
	@ManyToOne
	@JoinColumn(name = "solicitud_id")
	private Solicitud solicitud;
	
	@ManyToOne
	@JoinColumn(name = "usuario_id_solicitante")
	private Usuario usuarioSolicitante;
	
	@ManyToOne
	@JoinColumn(name = "usuario_id_respuesta")
	private Usuario usuarioRespuseta;
	
	@Column(length = 2000)
	private String texto;
	
	private String estado;
	
	private String tipo;
	
	@Column(name = "fecha_creacion", columnDefinition = "TIMESTAMP")
    private LocalDateTime fechaCreacion;
	
	@Column(name = "fecha_respuesta", columnDefinition = "TIMESTAMP")
    private LocalDateTime fechaRespuesta;
	
	@Column(name = "fecha_modificacion", columnDefinition = "TIMESTAMP")
    private LocalDateTime fechaModificacion;

	@Column(name = "fecha_eliminacion", columnDefinition = "TIMESTAMP")
    private LocalDateTime fechaEliminacion;
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
