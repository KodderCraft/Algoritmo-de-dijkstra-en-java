package com.Utc.AppDijkstra.controller;

import com.Utc.AppDijkstra.algoritmo.Dijkstra;
import com.Utc.AppDijkstra.model.Arista;
import com.Utc.AppDijkstra.model.Grafo;
import com.Utc.AppDijkstra.model.Nodo;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/dijkstra")
public class DijkstraControllerSimple {

    private Grafo grafo = new Grafo();

    @PostMapping("/crear")
    public Map<String, Object> crearGrafo() {
        grafo = new Grafo();
        return respuesta(true, "Grafo creado", null);
    }

    @PostMapping("/nodo")
    public Map<String, Object> agregarNodo(@RequestBody Map<String, String> body) {
        try {
            String nombre = body.get("nombre");
            if (nombre == null || nombre.trim().isEmpty()) {
                return respuesta(false, "El nombre del nodo no puede estar vacío", null);
            }
            Nodo nodo = new Nodo(nombre);
            if (grafo.existeNodo(nodo)) {
                return respuesta(false, "El nodo ya existe", null);
            }
            grafo.agregarNodo(nodo);
            return respuesta(true, "Nodo agregado: " + nombre, nombre);
        } catch (Exception e) {
            return respuesta(false, "Error: " + e.getMessage(), null);
        }
    }

    @PostMapping("/arista")
    public Map<String, Object> agregarArista(@RequestBody Map<String, Object> body) {
        try {
            String origen = (String) body.get("origen");
            String destino = (String) body.get("destino");
            Integer peso = ((Number) body.get("peso")).intValue();

            if (origen == null || destino == null) {
                return respuesta(false, "Origen y destino son requeridos", null);
            }
            if (peso == null || peso <= 0) {
                return respuesta(false, "El peso debe ser mayor a 0", null);
            }
            if (origen.equals(destino)) {
                return respuesta(false, "No se permite una arista de un nodo a sí mismo", null);
            }

            Nodo nodoOrigen = null;
            Nodo nodoDestino = null;

            for (Nodo n : grafo.getListaAdyacencia().keySet()) {
                if (n.getNombre().equals(origen)) nodoOrigen = n;
                if (n.getNombre().equals(destino)) nodoDestino = n;
            }

            if (nodoOrigen == null || nodoDestino == null) {
                return respuesta(false, "Uno o ambos nodos no existen", null);
            }

            grafo.agregarArista(nodoOrigen, nodoDestino, peso);
            return respuesta(true, "Arista " + origen + " -> " + destino + " agregada", null);
        } catch (Exception e) {
            return respuesta(false, "Error: " + e.getMessage(), null);
        }
    }

    @GetMapping("/info")
    public Map<String, Object> obtenerInfo() {
        List<String> nodos = new ArrayList<>();
        Map<Nodo, java.util.List<Arista>> listaAdyacencia = grafo.getListaAdyacencia();
        
        for (Nodo n : listaAdyacencia.keySet()) {
            nodos.add(n.getNombre());
        }

        List<Map<String, Object>> aristas = new ArrayList<>();
        for (Nodo n : listaAdyacencia.keySet()) {
            for (Arista arista : listaAdyacencia.get(n)) {
                Map<String, Object> ari = new HashMap<>();
                ari.put("origen", n.getNombre());
                ari.put("destino", arista.getDestino().getNombre());
                ari.put("peso", arista.getPeso());
                aristas.add(ari);
            }
        }

        Map<String, Object> info = new HashMap<>();
        info.put("nodos", nodos);
        info.put("aristas", aristas);
        info.put("cantidadNodos", nodos.size());
        info.put("cantidadAristas", aristas.size());

        return info;
    }

    @PostMapping("/calcular")
    public Map<String, Object> calcularCamino(@RequestBody Map<String, String> body) {
        try {
            String nodoOrigen = body.get("nodoOrigen");
            String nodoDestino = body.get("nodoDestino");

            if (nodoOrigen == null || nodoDestino == null) {
                return respuesta(false, "Nodos origen y destino son requeridos", null);
            }

            Nodo origen = null;
            Nodo destino = null;

            for (Nodo n : grafo.getListaAdyacencia().keySet()) {
                if (n.getNombre().equals(nodoOrigen)) origen = n;
                if (n.getNombre().equals(nodoDestino)) destino = n;
            }

            if (origen == null || destino == null) {
                return respuesta(false, "Uno o ambos nodos no existen", null);
            }

            if (grafo.getListaAdyacencia().isEmpty()) {
                return respuesta(false, "El grafo está vacío", null);
            }

            Dijkstra dijkstra = new Dijkstra(grafo);
            dijkstra.calcular(origen);
            List<Nodo> caminoNodos = dijkstra.obtenerCamino(destino);

            Map<String, Object> resultado = new HashMap<>();
            resultado.put("éxito", true);
            resultado.put("nodoOrigen", nodoOrigen);
            resultado.put("nodoDestino", nodoDestino);

            if (caminoNodos != null && !caminoNodos.isEmpty()) {
                List<String> camino = new ArrayList<>();
                for (Nodo n : caminoNodos) {
                    camino.add(n.getNombre());
                }
                Integer distancia = dijkstra.getDistancia(destino);
                resultado.put("camino", camino);
                resultado.put("distancia", distancia == Integer.MAX_VALUE ? null : distancia);
                resultado.put("caminoEncontrado", distancia != Integer.MAX_VALUE);
            } else {
                resultado.put("camino", new ArrayList<>());
                resultado.put("distancia", null);
                resultado.put("caminoEncontrado", false);
            }

            return resultado;
        } catch (Exception e) {
            return respuesta(false, "Error: " + e.getMessage(), null);
        }
    }

    @GetMapping("/distancias/{nodo}")
    public Map<String, Object> obtenerDistancias(@PathVariable String nodo) {
        try {
            Nodo nodoOrigen = null;
            for (Nodo n : grafo.getListaAdyacencia().keySet()) {
                if (n.getNombre().equals(nodo)) {
                    nodoOrigen = n;
                    break;
                }
            }

            if (nodoOrigen == null) {
                return respuesta(false, "El nodo no existe", null);
            }

            Dijkstra dijkstra = new Dijkstra(grafo);
            dijkstra.calcular(nodoOrigen);

            Map<String, Integer> distancias = new HashMap<>();
            for (Nodo n : grafo.getListaAdyacencia().keySet()) {
                Integer dist = dijkstra.getDistancia(n);
                if (dist != Integer.MAX_VALUE) {
                    distancias.put(n.getNombre(), dist);
                }
            }

            Map<String, Object> resultado = new HashMap<>();
            resultado.put("nodoOrigen", nodo);
            resultado.put("distancias", distancias);
            return resultado;
        } catch (Exception e) {
            return respuesta(false, "Error: " + e.getMessage(), null);
        }
    }

    @DeleteMapping("/limpiar")
    public Map<String, Object> limpiarGrafo() {
        grafo = new Grafo();
        return respuesta(true, "Grafo limpiado", null);
    }

    private Map<String, Object> respuesta(Boolean éxito, String mensaje, Object datos) {
        Map<String, Object> res = new HashMap<>();
        res.put("éxito", éxito);
        res.put("mensaje", mensaje);
        if (datos != null) {
            res.put("datos", datos);
        }
        return res;
    }
}
