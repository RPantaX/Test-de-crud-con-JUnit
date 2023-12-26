package com.app.productos.productosapptest;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest /*Le indicamos que haremos una prueba unitaria de una ENTIDAD (INSERCIONES A BASE DE DATOS)*/
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)//para que cree la tabla en la base de datos ya que con el h2 lo crea en memoria
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductoTests {
    @Autowired
    private ProductoRepositorio productoRepositorio;
    @Test /*Este será el método al que probaremos o testeemos, recordemos que siempre que teesteamos algo se hace automáticamente rollback, es decir los cambios que se hicieron se eliminan*/
    @Rollback(value = false)//ponemos el rollback false para que no elimine los cambios que haremos
    @Order(1)
    public void testGuardarProducto(){
        Producto producto = new Producto("IphoneXX",3000);
        Producto productoGuardado= productoRepositorio.save(producto);

        Assertions.assertNotNull(productoGuardado);//vamos a confirmar esta prueba unitaria siempre y cuando el producto guardado no sea nulo, esto seria en memoria pero como pusimos el replace none será en la bd normal.
    }

    @Test
    @Order(2)
    public void testBuscarPorNombre(){
        String nombre= "IphoneXX";
        Producto producto= productoRepositorio.findByNombre(nombre);
        assertThat(producto.getNombre()).isEqualTo(nombre);
    }
    @Test
    @Order(3)
    public void testBuscarPorNombreNoExistente(){
        String nombre= "CD";
        Producto producto= productoRepositorio.findByNombre(nombre);
        assertNull(producto);//comprueba si un valor no existe-voy a confirmar si el objeto que me pasaste es nulo.
    }

    @Test
    @Rollback(value = false)
    @Order(4)
    public void testActualizarProducto(){
        String nombreProducto= "CD"; //nuevo valor
        Producto producto= new Producto(nombreProducto, 2000); //nuevos valores
        producto.setId(1);
        productoRepositorio.save(producto);
        Producto productoActualizado= productoRepositorio.findByNombre(nombreProducto);
        assertThat(productoActualizado.getNombre()).isEqualTo(nombreProducto);
    }

    @Test
    @Order(5)
    public void testListarProductos(){
        List<Producto> productos= (List<Producto>) productoRepositorio.findAll();

        assertThat(productos).size().isGreaterThan(0);

    }
    @Test
    @Rollback(value = false)
    @Order(6)
    public void testEliminarProductos(){
        Integer id=20;
        boolean esExistenteAntesDeEliminar =productoRepositorio.findById(id).isPresent();
        productoRepositorio.deleteById(id);

        boolean noExisteDespuesDeEliminar =productoRepositorio.findById(id).isPresent();

        assertTrue(esExistenteAntesDeEliminar); //confirmamos si este valor es true
        assertFalse(noExisteDespuesDeEliminar); //confirmamos si este valor es false
    }


}
