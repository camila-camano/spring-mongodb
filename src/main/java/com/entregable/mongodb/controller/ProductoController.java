package com.entregable.mongodb.controller;

import com.entregable.mongodb.model.Producto;
import com.entregable.mongodb.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/mongo")
public class ProductoController {

    @Autowired
    ProductoService service;

    @GetMapping("/getall")
    public List<Producto> findProductos(){return service.findAll();}

}
