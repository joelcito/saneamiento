package com.saneamiento.models.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

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
	
	@Column(name="usuario_creador", length = 10)
	private String UsuarioCreador;
	
	@Column(name="usuario_modificador", length = 10)
	private String UsuarioModificador;
	
	@Column(name="usuario_eliminador", length = 10)
	private String UsuarioEliminador;
		
	@Column(length = 50)
	private String formulario;
	
	@Column(length = 50)
	private String campo;

	@Column(length = 1000)
	private String tipo_validador;
	
	private String adicional;
	
	private String estado;
	
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
