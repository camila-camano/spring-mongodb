package com.entregable.mongodb.repository;

import com.entregable.mongodb.model.Producto;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface ProductoRepository extends MongoRepository<Producto, String > {
    Producto findByName(String nombre);

}
