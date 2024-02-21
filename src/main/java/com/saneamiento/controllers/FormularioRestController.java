package com.saneamiento.controllers;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saneamiento.models.entity.Formulario;
import com.saneamiento.models.entity.FormularioPregunta;
import com.saneamiento.models.entity.TipoSaneo;
import com.saneamiento.models.entity.Usuario;
import com.saneamiento.models.services.IFormularioService;
import com.saneamiento.models.services.ITipoSaneoService;
import com.saneamiento.models.services.IUsuarioService;

@RestController
@RequestMapping("/api/formulario")
@CrossOrigin(origins = {"http://localhost:4200"})
public class FormularioRestController {
	@Autowired
	private IFormularioService formularioService;
	
	@Autowired
	private ITipoSaneoService tipoSaneoService;
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@GetMapping("/listado")
	private List<Formulario> name() {
		return this.formularioService.findAll();
	}
	
	@PostMapping("/")
	public Formulario save(@RequestBody Map<String, Object> requestBody) {
		
		Formulario newFor = new Formulario();
		newFor.setNombre(requestBody.get("nombre").toString());
		newFor.setSigla(requestBody.get("sigla").toString());
		
		Long tipo_saneo_id = Long.parseLong(requestBody.get("tipo_saneo").toString());
		TipoSaneo tipoSaneoBuscado = this.tipoSaneoService.findById(tipo_saneo_id);
		newFor.setTipoSaneoFormulario(tipoSaneoBuscado);
		
		Instant instant = new Date().toInstant();
		LocalDateTime localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
		newFor.setFechaCreacion(localDateTime);
		newFor.setUsuarioCreador(requestBody.get("usuario").toString());			
		
		return this.formularioService.save(newFor);
		
	}
	
	@GetMapping("/listadoFormulariosByIdTipoSaneo/{tipoSaneoId}")
	public List<Formulario> getFormulariosByIdTipoSaneo(@PathVariable Long tipoSaneoId) {
		return this.formularioService.getFormulariosByIdTipoSaneo(tipoSaneoId);
	}
	
	@GetMapping("/getFormulariofindById/{id}")
	public Formulario getFormulariofindById(@PathVariable Long id) {
		return this.formularioService.findById(id);
	}
	
	
	
	//***************** PREGUNTA FORMULARIO *****************
	@GetMapping("/formulario_pregunta/{formulario_id}")
	public List<FormularioPregunta> getFormularioPregunta(@PathVariable Long formulario_id) {
		return this.formularioService.getFormularioPregunta(formulario_id);
	}
	
	@PostMapping("/formulario_pregunta/")
	public FormularioPregunta saveFormularioPregunta(@RequestBody Map<String, Object> requestBody) {
		System.out.println(requestBody);
		//Long formulario_id 						= ((Integer) requestBody.get("formulario")).longValue();
		
		
		Object formularioObject = requestBody.get("formulario");
		Long formulario_id = null;  // Inicializar con un valor predeterminado o manejar nulos según tus necesidades

		if (formularioObject instanceof Integer) {
		    formulario_id = ((Integer) formularioObject).longValue();
		} else if (formularioObject instanceof String) {
		    try {
		        formulario_id = Long.parseLong((String) formularioObject);
		    } catch (NumberFormatException e) {
		        // Manejar el caso en el que la cadena no es un número válido
		        e.printStackTrace();  // Imprimir la excepción (puedes cambiar esto según tus necesidades)
		    }
		}

		// Continuar con el resto del código, utilizando formulario_id
		
		
		Formulario formularioBuscado = this.formularioService.findById(formulario_id);
		List<FormularioPregunta> listadoPreguntas = formularioBuscado.getPreguntas();
		
		FormularioPregunta newPregunta = new FormularioPregunta();
		newPregunta.setNombre(requestBody.get("nombre").toString());
		newPregunta.setTipo(requestBody.get("tipo").toString());
		newPregunta.setRequerido((Boolean) requestBody.get("requerido"));
		newPregunta.setFormulario(formularioBuscado);
		
		listadoPreguntas.add(newPregunta);
		
		formularioBuscado.setPreguntas(listadoPreguntas);
		
		this.formularioService.save(formularioBuscado);
		
		System.out.println(formulario_id);
			
		return new FormularioPregunta();
	}
	
	@GetMapping("/formulario_pregunta/{formulario_id}/{order_data}")
	public List<FormularioPregunta> getFormularioPreguntaByTipoSaneoByTipoDato(@PathVariable Long formulario_id, @PathVariable String order_data){
		System.out.println(formulario_id);
		System.out.println(order_data);
		return this.formularioService.getFormularioPreguntaByTipoSaneoByTipoDato(formulario_id, order_data);
	}

}
