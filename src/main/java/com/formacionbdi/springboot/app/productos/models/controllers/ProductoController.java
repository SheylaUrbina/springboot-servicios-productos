package com.formacionbdi.springboot.app.productos.models.controllers;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.formacionbdi.springboot.app.productos.models.entity.Producto;
import com.formacionbdi.springboot.app.productos.models.service.IProductoService;

@RestController
public class ProductoController {
	
	@Autowired
	private Environment env;//primera forma de usar puerto -org.springframework.core.env.Environment, mediante est variable podemos obtener el puerto
	
	/*@Value("${server.port}")//segunda forma de validar el puerto que esta utilizando
	private Integer port;*/
	
	@Autowired
	private IProductoService productoService;
	
	@GetMapping("/listar")
	public List<Producto> listar(){
		return productoService.findAll().stream().map(producto -> {
			producto.setPort(Integer.parseInt(env.getProperty("local.server.port")));//primera forma
			//producto.setPort(port);//segunda forma, agregando el @value("${server.port}")
			return producto;
		}).collect(Collectors.toList());
	}
	
	@GetMapping("/ver/{id}")
	public Producto detalle(@PathVariable Long id) throws InterruptedException {
		
		/*if(id.equals(10L)) {
			throw new IllegalStateException("Producto no encontrado");
		}
		
		if(id.equals(7L)) {
			TimeUnit.SECONDS.sleep(5L);
		}*/
		
		Producto producto = productoService.findById(id);
		producto.setPort(Integer.parseInt(env.getProperty("local.server.port")));
		//producto.setPort(port);

		/*boolean ok = false;
		if (ok == false) {
			throw new Exception("no se puede cargar el producto");
		}
		
		try {
			Thread.sleep(2000L);
		}catch(InterruptedException e) {
			e.printStackTrace();
		}*/
		return producto;
	}
	
	@GetMapping("/prueba")
	public String prueba() {
		return "estamos en servicio";
	}

}
