package com.saneamiento.models.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
@Table(name="tipo_detalle_tipo_saneo")
public class TipoDetalleTipoSaneo implements Serializable{
		
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
	@JoinColumn(name = "detalle_tipo_saneo_id")
	@JsonBackReference
	private DetalleTipoSaneo detalleTipoSaneo;
	
	private String nombre;
	
	private String nombre_campo;
	
	private String nombre_grupo;
	
	private String tipo_dato;
	
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
