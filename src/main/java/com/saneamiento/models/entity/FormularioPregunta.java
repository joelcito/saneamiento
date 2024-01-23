package com.saneamiento.models.entity;

import java.io.Serializable;

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
@Table(name = "formulario_pregunta")
public class FormularioPregunta implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nombre;
	
	private String tipo;
	
	private Boolean requerido;
	
	@ManyToOne
	@JoinColumn(name = "formulario_id")
	private Formulario formulario;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
}
