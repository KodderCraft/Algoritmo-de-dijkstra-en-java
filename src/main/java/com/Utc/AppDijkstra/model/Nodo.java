package com.Utc.AppDijkstra.model;

import java.util.Objects;

public class Nodo {

    private String nombre;

    public Nodo(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof Nodo))
            return false;

        Nodo nodo = (Nodo) o;

        return Objects.equals(nombre, nodo.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre);
    }

}