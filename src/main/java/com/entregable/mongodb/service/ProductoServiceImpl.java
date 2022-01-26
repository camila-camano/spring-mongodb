package com.entregable.mongodb.service;

import com.entregable.mongodb.model.Producto;
import com.entregable.mongodb.repository.ProductoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductoServiceImpl implements ProductoService{

    @Autowired
    private ProductoRepository repository;

    @Autowired
    private MongoTemplate template;

    private final ObjectMapper mapper;

    @Override
    public Producto createProducto(Producto producto){
        return repository.save(producto);
    }

    //@Override
    //public Producto findByNombre(String nombre){
      //  return repository.findByName(nombre);
    //}

    @Override
    public String findByNombre(String nombre){
        if(repository.findByName(nombre) != null){
            return "Existe" + nombre;
        }else {
            return "NO existe" + nombre;
        }

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


        // serializacion -----------------------------------------------------------------------------------------------
    @Override
    public Producto createProductoSerializado(Producto producto) {
        try {
            mapperToString(producto);
            return repository.save(producto);

        } catch (JsonProcessingException e) {
            log.error("Error convertiong message to string", e);
        }
        return producto;
    }

    void mapperToString(Producto producto) throws JsonProcessingException {
        var productoString = mapper.writeValueAsString(producto);
        log.info("Producto en formato string : {}", productoString);
    }

    @Override
    public Producto createProductoSerializadoMap(Producto producto) {
        try {
            mapperToMap(producto);
            return repository.save(producto);

        } catch (JsonProcessingException e) {
            log.error("Error en la conversión", e);
        }
        return producto;
    }

    void mapperToMap(Producto producto) throws JsonProcessingException {
        var userString = mapper.writeValueAsString(producto);
        var userMap = mapper.readValue(userString, Map.class);
        log.info("Producto en formato de Mapa : {}", userMap);
    }

}
