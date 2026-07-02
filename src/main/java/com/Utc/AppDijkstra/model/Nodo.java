// Declara el paquete al que pertenece esta clase.
package com.Utc.AppDijkstra.model;

import java.util.Objects;
//Importa la clase utilitaria Objects de Java.
//Se usa más abajo para comparar y generar códigos hash de forma segura,
//evitando errores comunes como NullPointerException.

//Clase publica Nodo y representa un nodo (vértice) del grafo
public class Nodo {
	//Atributo 
    private String nombre;
    
    //Constructor Parametrizado 
    public Nodo(String nombre) {
        this.nombre = nombre;
    }
    //Get and Setter de Nombre
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    //Sobrescribe (override) el método toString() heredado de la clase Object.
    @Override
    public String toString() {
        return nombre;
    }
    
    // Define cuándo dos objetos Nodo se consideran "iguales".
    // Por defecto, Java compara objetos por referencia (misma dirección
    // de memoria); aquí se redefine para comparar por su contenido (el nombre)
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof Nodo))
            return false;

        Nodo nodo = (Nodo) o;

        return Objects.equals(nombre, nodo.nombre);
    }
    // Sobrescribe hashCode(), que genera un código numérico único
    // basado en el nombre del nodo.
    // Es obligatorio sobrescribirlo junto con equals(): si dos nodos son
    // "iguales" (equals devuelve true), deben tener el mismo hashCode.
    // Esto es esencial para que la clase funcione correctamente dentro de
    // estructuras como HashMap o HashSet (por ejemplo, si usas Nodo como
    // clave en un mapa de distancias del algoritmo de Dijkstra).
    @Override
    public int hashCode() {
        return Objects.hash(nombre);
    }

}