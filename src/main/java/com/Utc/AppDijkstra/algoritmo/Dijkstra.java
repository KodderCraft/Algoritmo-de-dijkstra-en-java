package com.Utc.AppDijkstra.algoritmo;

import com.Utc.AppDijkstra.model.Arista;
import com.Utc.AppDijkstra.model.Grafo;
import com.Utc.AppDijkstra.model.Nodo;

import java.util.*;

public class Dijkstra {

    private Grafo grafo;
    private Map<Nodo, Integer> distancias;
    private Map<Nodo, Nodo> anteriores;

    public Dijkstra(Grafo grafo) {
        this.grafo = grafo;
    }

    /**
     * Ejecuta el algoritmo de Dijkstra desde un nodo origen.
     */
    public void calcular(Nodo origen) {

        distancias = new HashMap<>();
        anteriores = new HashMap<>();

        // Inicializar distancias
        for (Nodo nodo : grafo.getListaAdyacencia().keySet()) {
            distancias.put(nodo, Integer.MAX_VALUE);
            anteriores.put(nodo, null);
        }

        distancias.put(origen, 0);

        PriorityQueue<Nodo> cola = new PriorityQueue<>(
                Comparator.comparingInt(distancias::get));

        cola.add(origen);

        while (!cola.isEmpty()) {

            Nodo actual = cola.poll();

            for (Arista arista : grafo.obtenerVecinos(actual)) {

                Nodo vecino = arista.getDestino();

                int nuevaDistancia = distancias.get(actual) + arista.getPeso();

                if (nuevaDistancia < distancias.get(vecino)) {

                    distancias.put(vecino, nuevaDistancia);
                    anteriores.put(vecino, actual);

                    // Actualizar prioridad
                    cola.remove(vecino);
                    cola.add(vecino);
                }
            }
        }
    }

    /**
     * Devuelve la distancia mínima hasta un nodo.
     */
    public int getDistancia(Nodo destino) {
        return distancias.getOrDefault(destino, Integer.MAX_VALUE);
    }

    /**
     * Devuelve todas las distancias calculadas.
     */
    public Map<Nodo, Integer> getDistancias() {
        return distancias;
    }

    /**
     * Devuelve el camino mínimo desde el origen hasta el destino.
     */
    public List<Nodo> obtenerCamino(Nodo destino) {

        List<Nodo> camino = new ArrayList<>();

        if (!distancias.containsKey(destino)
                || distancias.get(destino) == Integer.MAX_VALUE) {
            return camino;
        }

        Nodo actual = destino;

        while (actual != null) {
            camino.add(actual);
            actual = anteriores.get(actual);
        }

        Collections.reverse(camino);

        return camino;
    }

    /**
     * Devuelve el nodo anterior de cada vértice.
     */
    public Map<Nodo, Nodo> getAnteriores() {
        return anteriores;
    }

}
