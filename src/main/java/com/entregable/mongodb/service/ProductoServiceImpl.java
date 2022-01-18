package com.entregable.mongodb.service;

import com.entregable.mongodb.model.Producto;
import com.entregable.mongodb.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;

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

    @Override
    public void deleteAll(){
        repository.deleteAll();
    }

    @Override
    public void updateStockOf(String name, int stock){
        var query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        var update = new Update();
        update.set("stock", stock);
        template.updateFirst(query, update, Producto.class);
        }

        @Override
    public void deleteByName(String name){
        var query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        Producto p = template.findOne(query, Producto.class);
        template.remove(p);
        }

}
