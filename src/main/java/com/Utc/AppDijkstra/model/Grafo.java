package com.Utc.AppDijkstra.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Grafo {

    private Map<Nodo, List<Arista>> listaAdyacencia;

    public Grafo() {
        listaAdyacencia = new HashMap<>();
    }

    // Agrega un nodo al grafo
    public void agregarNodo(Nodo nodo) {
        listaAdyacencia.putIfAbsent(nodo, new ArrayList<>());
    }

    // Agrega una arista entre dos nodos
    public void agregarArista(Nodo origen, Nodo destino, int peso) {

        if (!listaAdyacencia.containsKey(origen)) {
            agregarNodo(origen);
        }

        if (!listaAdyacencia.containsKey(destino)) {
            agregarNodo(destino);
        }

        listaAdyacencia.get(origen).add(new Arista(destino, peso));
    }

    // Devuelve toda la lista de adyacencia
    public Map<Nodo, List<Arista>> getListaAdyacencia() {
        return listaAdyacencia;
    }

    // Devuelve los vecinos de un nodo
    public List<Arista> obtenerVecinos(Nodo nodo) {
        return listaAdyacencia.getOrDefault(nodo, new ArrayList<>());
    }

    // Verifica si existe un nodo
    public boolean existeNodo(Nodo nodo) {
        return listaAdyacencia.containsKey(nodo);
    }

}