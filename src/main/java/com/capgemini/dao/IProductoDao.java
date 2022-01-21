package com.capgemini.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.capgemini.entities.Producto;

@Repository
public interface IProductoDao extends JpaRepository<Producto, Long>{
	// Crear consultas personalizadas con la relación LAZY para optimizar mejor las consultas
	// Así manejamos registros con paginación y ordenamiento
	
	// El metodo findAll parece que tiene una opción para parametros
	
	// Ahora hay que decirle como generar este metodo query
	
	// El profesor dice que aquí consultas entidades no son SQL
	// Y efectviamente se le parece pero ni de puta coña
	@Query(value= "select p from Producto p left join fetch p.presentacion")
	public List<Producto> findAll(Sort sort);
	
	// Para recuperar los registros, pero no todos sino de 10 en 10, de 20 en 20, etc...
	// Es decir para hacer un paginado tipo google
	
	@Query(value= "select p from Producto p left join fetch p.presentacion",
			countQuery = "select count(p) from Producto p left join p.presentacion" )
	public Page<Producto> findAll(Pageable pageable);
	
	/***
	 * 
	 * ESTO ES PROPIO Y NO CONFIO EN EL
	 * 
	@Query(value= "select p from Producto p left join fetch p.presentacion")
	public List<Producto> findAll();
	***/
	
	
	// Esto es como $id por fin podemos especificar las coincidencias que buscamos
	// ¿Como haremos un like con esto?
	@Query(value = "select p from Producto p left join fetch p.presentacion where p.id = :id")
	public Producto findById(long id);
	public Producto getById(long id);
}
