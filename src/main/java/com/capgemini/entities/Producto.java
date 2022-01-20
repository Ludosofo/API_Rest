package com.capgemini.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

// Data genera los getter y setters automaticamente

@Entity
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Producto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotNull
	@NotEmpty(message = "ERROR Producto.java line 29: El campo nombre no puede estar vacio")
	@Size( min = 4, max = 80, message = "El nombre tiene que estar entre 4 y 80 caracteres")
	private String nombre;
	
	
	@NotEmpty(message = "ERROR Producto.java line 35: El campo descripcion no puede estar vacio")
	@Size( min = 4, max = 225, message = "El nombre tiene que estar entre 4 y 225 caracteres")
	private String descripcion;
	
	@Min(value=1, message = "El precio minimo es 1")
	private double precio;
	
	@Min(value=0, message = "El stock no puede ser negativo")
	private long stock;
	
	// Antes: Creabamos getter & setters & constructores & supperconstructores
	// Ahora: Nos descargamos el lombok desde pagina oficial, corremos por consola el jar y configuramos el IDE
	
	
	// Muchos productos pueden tener una presentación
	// El fetch Lazy no hace las comprobaciones todo el rato
	// Cascade para que los datos se transmitan al resto
	@ManyToOne(fetch= FetchType.LAZY, cascade = CascadeType.PERSIST)
	private Presentacion presentacion;
}


