package com.app.productos.productosapptest;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "productos")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 64)
    private String nombre;
    private float precio;

    public Producto(String nombre, int precio) {
        this.nombre=nombre;
        this.precio=precio;
    }
}
