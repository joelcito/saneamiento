package com.saneamiento.models.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "validador")
public class Validador implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
		
	@Column(length = 50)
	private String formulario;
	
	@Column(length = 50)
	private String campo;

	@Column(length = 1000)
	private String tipo_validador;
	
	private String adicional;
	
	private String estado;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
