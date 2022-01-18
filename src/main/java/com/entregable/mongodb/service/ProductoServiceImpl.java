package com.entregable.mongodb.service;

import com.entregable.mongodb.model.Producto;
import com.entregable.mongodb.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService{

    @Autowired
    private ProductoRepository repository;

    @Autowired
    private MongoTemplate template;

    @Override
    public Producto createProducto(Producto producto){
        return repository.save(producto);
    }

    @Override
    public Producto findByNombre(String nombre){
        return repository.findByName(nombre);
    }

    @Override
    public List<Producto> findAll(){return repository.findAll();}
}
