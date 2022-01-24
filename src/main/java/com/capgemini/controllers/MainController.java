package com.capgemini.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.entities.Producto;
import com.capgemini.service.IProductoService;

// Todas las peticiones (request) se hacen a la URI
// que es el endpoint y según el verbo del protocolo HTTP utilizado
// que puede ser GET, POST, PUT, DELETE, UPDATE, ETC... se delegera la peticion a un metodo u otro


// Al poner 
// Ahora no va a resolver vistas, vamos a hacer JSON para otras mierdas
@RestController
@RequestMapping(value="/productos")
public class MainController {
	@Autowired
	private IProductoService productoService;
	
	// Esto es RPC (Remote Procedure Call)
	// El sigueinte metodo no es REST (Nada de REST tiene)
	// PD: No funciona porque no obtiene datos de ddbb tienes que hacer algo!!!!!
//	@GetMapping
//	public List<Producto> findAll(){
//		return productoService.findAll();
//	}
	
	// Vamos a ver REST de verdad
	// REST devuelve información sobre como ha ido un proceso
	// Tenemos que incluir esta información para que sepa como actuar
	
	@GetMapping
	@Transactional(readOnly = true)
	public ResponseEntity<List<Producto>> findAll( 	@RequestParam(required= false, name="page") Integer page,
													@RequestParam(required= false, name="size") Integer size){
		
		ResponseEntity<List<Producto>> responseEntity = null;
		
		List<Producto> productos = null;
		
		Sort sortByName = Sort.by("nombre"); // Propiedades de ordenación
				
		if( page != null && size != null) {
			// Resultados con paginación
			Pageable pageable;
			pageable = PageRequest.of(page, size, sortByName); // Pedimos el paginado
			productos = productoService.findAll(pageable).getContent();
			// ERR: Type mismatch > añadir .getContent() para devolver List<Producto>
			
		}else {
			// Sin paginación
			
			productos = productoService.findAll(sortByName);
		}
		
		if(productos.size()>0) {
			responseEntity  = new ResponseEntity<List<Producto>>(productos, HttpStatus.OK);
		}else {
			responseEntity = new ResponseEntity<List<Producto>>(HttpStatus.NO_CONTENT);
		}
		
		return responseEntity;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Producto> findById( @PathVariable(name="id") long id){
		
		ResponseEntity<Producto> responseEntity = null;
		
		Producto producto = null;
		
		producto = productoService.findById(id);
		
		if(producto!=null) {
			responseEntity = new ResponseEntity<Producto>(producto, HttpStatus.OK);
		}else {
			responseEntity = new ResponseEntity<Producto>(HttpStatus.NO_CONTENT);
		}
		return responseEntity;
	}
	
	
	// Todas etiquetas tienen una función
	// Valid = Valida contra las restricciones puestas en la entidad de Producto
	// BindingResult = Vincula resultados de la validación / Status 200, 201, 403, 404, 500
	// 
	@PostMapping
	public ResponseEntity<Map<String, Object>> guardar(@Valid @RequestBody Producto producto, BindingResult result){
		
		
		// Definimos variables iniciales que queremos obtener
		Map<String, Object> responseAsMap = new HashMap<>();
		ResponseEntity<Map<String, Object>> responseEntity = null;
		
		// Lista de errores que guardaremos
		List<String> errores = null;
		
		if(result.hasErrors()){
			errores = new ArrayList<>();
			for( ObjectError error : result.getAllErrors()) {
				errores.add(error.getDefaultMessage());
			}
			
			responseAsMap.put("errores", errores);
			responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap, HttpStatus.BAD_REQUEST);
			return responseEntity;
		}
		
		return responseEntity;
		// Tras haber pasado por aquí tenemos toda una recopilación de errores
		// Ahora hacemos la respuesta de datos
		// Control + Space y detecta cual es el return para ponerse solo

	}
	// Todo esto con el objetivo de lanzar el postman
}
