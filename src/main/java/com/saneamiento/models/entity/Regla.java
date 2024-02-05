package com.saneamiento.models.entity;

import java.io.Serializable;
import java.time.LocalDate;

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
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
