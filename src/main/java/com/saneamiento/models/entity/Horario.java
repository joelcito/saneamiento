package com.saneamiento.models.entity;

import java.io.Serializable;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="horario")
public class Horario implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private LocalTime hora_ini;
	
	private LocalTime hora_fin;
	
	private int cantidad;	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
