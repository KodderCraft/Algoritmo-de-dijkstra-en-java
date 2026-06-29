package com.Utc.AppDijkstra;

import com.Utc.AppDijkstra.model.Grafo;
import com.Utc.AppDijkstra.model.Nodo;

public class PruebaGrafo {

    public static void main(String[] args) {

        Grafo grafo = new Grafo();

        Nodo A = new Nodo("A");
        Nodo B = new Nodo("B");
        Nodo C = new Nodo("C");
        Nodo D = new Nodo("D");

        grafo.agregarNodo(A);
        grafo.agregarNodo(B);
        grafo.agregarNodo(C);
        grafo.agregarNodo(D);

        grafo.agregarArista(A, B, 5);
        grafo.agregarArista(A, C, 2);
        grafo.agregarArista(B, D, 3);
        grafo.agregarArista(C, D, 8);

        System.out.println("=== LISTA DE ADYACENCIA ===");

        System.out.println(grafo.getListaAdyacencia());

        System.out.println();

        System.out.println("Vecinos de A:");

        System.out.println(grafo.obtenerVecinos(A));

    }

}