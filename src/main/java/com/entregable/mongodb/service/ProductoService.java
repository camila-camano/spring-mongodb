package com.entregable.mongodb.service;

import com.entregable.mongodb.model.Producto;

import java.util.List;

public interface ProductoService {

    Producto createProducto(Producto producto);
    Producto findByNombre(String nombre);
    List<Producto> findAll();
}
