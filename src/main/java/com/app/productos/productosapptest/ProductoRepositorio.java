package com.app.productos.productosapptest;

import org.springframework.data.repository.CrudRepository;


public interface ProductoRepositorio extends CrudRepository<Producto, Integer> {
    Producto findByNombre(String nombre);
}
