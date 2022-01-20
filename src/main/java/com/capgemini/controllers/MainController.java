package com.capgemini.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Todas las peticiones (request) se hacen a la URI
// que es el endpoint y según el verbo del protocolo HTTP utilizado
// que puede ser GET, POST, PUT, DELETE, UPDATE, ETC... se delegera la peticion a un metodo u otro


// Al poner 
// Ahora no va a resolver vistas, vamos a hacer JSON para otras mierdas
@RestController
@RequestMapping(value=" /productos/")
public class MainController {

}
