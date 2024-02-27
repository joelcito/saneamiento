package com.saneamiento.models.entity;

import java.io.Serializable;
import java.time.LocalDateTime;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
@Table(name = "solicitud")
@JsonIgnoreProperties({"usuarioCreador", "usuarioModificador", "usuarioEliminador"})
public class Solicitud implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="usuario_creador", length = 10)
	private String usuario_creador;
	
	@Column(name="usuario_modificador", length = 10)
	private String usuario_modificador;
	
	@Column(name="usuario_eliminador", length = 10)
	private String usuario_eliminador;
	
	@Column(length = 1000)
	private String descripcion;
	
	@ManyToOne
	@JoinColumn(name = "solicitante_id")
	private Usuario UsuarioSolicitante;
	
	@ManyToOne
	@JoinColumn(name = "asignado_id")
	private Usuario UsuarioAsignado;
	
	@ManyToOne
	@JoinColumn(name = "respuesta_id")
	private Usuario UsuarioRespuesta;
	
	@ManyToOne
	@JoinColumn(name = "formulario_id")
	private Formulario Formulario;
	
	private String tabla_id;
	
	private String sistema;
	
	private String codigo;
	
	@Column(length = 15)
	private String estado;
	
	@Column(name = "fecha_solicitud", columnDefinition = "TIMESTAMP")
    private LocalDateTime fechaSolicitud;
	
	@Column(name = "fecha_respuesta", columnDefinition = "TIMESTAMP")
    private LocalDateTime fechaRespuesta;
	
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
