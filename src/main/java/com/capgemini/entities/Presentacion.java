package com.capgemini.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.persistence.GenerationType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Presentacion implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotNull
	@NotEmpty(message = "ERROR Producto.java line 29: El campo nombre no puede estar vacio")
	@Size( min = 4, max = 80, message = "El nombre tiene que estar entre 4 y 80 caracteres")
	private String nombre;
	
	@NotEmpty(message = "ERROR Producto.java line 29: El campo nombre no puede estar vacio")
	@Size( min = 4, max = 255, message = "El nombre tiene que estar entre 4 y 255 caracteres")
	private String descripcion;
	
	// mapped hace que la relación este hecha en base al campo presentacion en la entidad producto
	// Como es lazy no trae automaticamente la presentacion constantemente
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE, mappedBy = "presentacion")
	private List<Producto> productos;
	
}
