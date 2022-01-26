package com.entregable.mongodb.controller;

import com.entregable.mongodb.model.Producto;
import com.entregable.mongodb.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
@RestController
@RequestMapping("/coder-house")
public class ProductoController {

    @Autowired
    ProductoService service;

    Logger logger = LogManager.getLogger(ProductoController.class);


    @Operation(summary = "Método para crear una persona", description = "Permite crear personas en Coderhouse", tags = {"coder-house"})
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Se creó a la persona"),
                    @ApiResponse(responseCode = "400", description = "Hay un error en el request", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Ocurrió un error inesperado", content = @Content)
            }
    ) //http://localhost:8081/swagger-ui/index.html#/


    @PostMapping("/post")
    public Producto createProducto(@RequestBody Producto p){
        logger.info("POST request recibido.");
        return service.createProducto(p);
    }

    @GetMapping("/mensajes/example")
    public String getMensajesString() {
        logger.info("GET Request recibido string");
        return "Ejemplo de respuesta 8082";
    }


    @GetMapping("/getall")
    public List<Producto> findProductos(){
        logger.info("GET ALL request recibido.");
        return service.findAll();}

    /*
    @GetMapping("/get")
    public Producto findByName(@RequestParam String name) {
        logger.info("GET request recibido de producto: {} ", name);
        return service.findByNombre(name);
    }
*/

    @GetMapping("/getstring")
    public String findByName(@RequestParam String name){
        logger.info("GET request recibido de producto: {} ", name);
        return service.findByNombre(name);
    }

    @PutMapping("/put")
    public Producto updateByName(@RequestParam String name, @RequestBody Producto p){
        logger.info("PUT request recibido. Actualizando producto: {}", name);
        return p;
    }

    @DeleteMapping("/deleteall")
    public void deleteAll(){
        logger.info("DELETE ALL request recibido.");
        service.deleteAll();
    }

    @DeleteMapping("/deleteone")
    public  void deleteByName(@RequestParam String name){
        logger.info("DELETE producto de nombre: {}", name);
        service.deleteByName(name);
    }

    @PutMapping("/update")
    public void updateStockOf(@RequestParam String name, @RequestParam int stock){
        logger.info("PUT recibido, producto a modificar: {} ", name);
        service.updateStockOf(name, stock);
    }

    @PostMapping("/serialize")
    public void serialize(@RequestBody Producto producto){
        logger.info("POST serializar recibido. Creando producto serializado.");
        service.createProductoSerializado(producto);
    }

    @PostMapping("/serialize-map")
    public void serializeMap(@RequestBody Producto producto){
        logger.info("POST serializando producto como mapa.");
        service.createProductoSerializadoMap(producto);
    }

}
