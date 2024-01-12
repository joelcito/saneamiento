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
@Table(name = "rol")
public class Rol implements Serializable{
	

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private  Long id;
	
	@Column(unique=true, length=20)
	private String nombre;
	
	@Column(length = 1000)
	private String menus;
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}