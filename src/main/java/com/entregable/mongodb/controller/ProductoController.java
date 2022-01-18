package com.entregable.mongodb.controller;

import com.entregable.mongodb.model.Producto;
import com.entregable.mongodb.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
@RestController
@RequestMapping("/mongo")
public class ProductoController {

    @Autowired
    ProductoService service;

    Logger logger = LogManager.getLogger(ProductoController.class);

    @PostMapping("/post")
    public Producto createProducto(@RequestBody Producto p){
        logger.info("POST request recibido.");
        return service.createProducto(p);
    }

    @GetMapping("/getall")
    public List<Producto> findProductos(){
        logger.info("GET ALL request recibido.");
        return service.findAll();}

    @GetMapping("/get")
    public Producto findByName(@RequestParam String name) {
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


}
