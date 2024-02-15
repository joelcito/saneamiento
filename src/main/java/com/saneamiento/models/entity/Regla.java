package com.saneamiento.models.entity;

import java.io.Serializable;
import java.time.LocalDate;
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
@Table(name = "regla")
public class Regla implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="usuario_creador", length = 10)
	private String UsuarioCreador;
	
	@Column(name="usuario_modificador", length = 10)
	private String UsuarioModificador;
	
	@Column(name="usuario_eliminador", length = 10)
	private String UsuarioEliminador;
	
	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;
	
	@ManyToOne
	@JoinColumn(name = "tipo_saneo_id")
	private TipoSaneo tipoSaneo;
	
	private String horario_id;
	
	private String detalle_tipo_saneo_id;
	
	private LocalDate fecha_ini;
	
	private LocalDate fecha_fin;
	
	private Boolean estado;
	
	@Column(length = 2)
	private String asignacion;
	
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
