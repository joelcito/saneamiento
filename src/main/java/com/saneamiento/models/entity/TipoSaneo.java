package com.saneamiento.models.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tipo_saneo")
public class TipoSaneo implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="usuario_creador", length = 10)
	private String UsuarioCreador;
	
	@Column(name="usuario_modificador", length = 10)
	private String UsuarioModificador;
	
	@Column(name="usuario_eliminador", length = 10)
	private String UsuarioEliminador;
	
	private String nombre;
	
	private String descripcion;

	@OneToMany(mappedBy = "tipoSaneo", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JsonIgnore
    private List<DetalleTipoSaneo> detalles;
		
	@OneToMany(mappedBy = "tipoSaneoFormulario", cascade = CascadeType.ALL)
	@JsonIgnore
    private List<Formulario> formularios;
	
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
