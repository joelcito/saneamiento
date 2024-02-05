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
@Table(name = "solicitud")
public class Solicitud implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
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
	
	@Column(name = "fecha_solicitud", columnDefinition = "TIMESTAMP")
    private LocalDateTime fechaSolicitud;
	
	@Column(name = "fecha_respuesta", columnDefinition = "TIMESTAMP")
    private LocalDateTime fechaRespuesta;
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
