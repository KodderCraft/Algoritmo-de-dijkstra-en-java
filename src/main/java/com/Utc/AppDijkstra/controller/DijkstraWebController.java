package com.Utc.AppDijkstra.controller;

import com.Utc.AppDijkstra.algoritmo.Dijkstra;
import com.Utc.AppDijkstra.model.Arista;
import com.Utc.AppDijkstra.model.Grafo;
import com.Utc.AppDijkstra.model.Nodo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Controller
public class DijkstraWebController {

    private final Grafo grafo = construirGrafoDemo();

    @GetMapping("/")
    public String mostrarFormulario(Model model) {
        model.addAttribute("nodos", obtenerNombres(grafo));
        model.addAttribute("aristas", obtenerAristas(grafo));
        return "index";
    }

    @PostMapping("/calcular")
    public String calcular(@RequestParam String inicio, @RequestParam String fin, Model model) {
        Map<String, Nodo> nodos = new LinkedHashMap<>();
        for (Nodo nodo : grafo.getListaAdyacencia().keySet()) {
            nodos.put(nodo.getNombre(), nodo);
        }

        Nodo origen = nodos.get(inicio.toUpperCase());
        Nodo destino = nodos.get(fin.toUpperCase());

        if (origen == null || destino == null) {
            model.addAttribute("inicio", inicio);
            model.addAttribute("fin", fin);
            model.addAttribute("camino", "Nodo inválido");
            model.addAttribute("distancia", "No disponible");
            model.addAttribute("mostrarModalResultado", true);
            model.addAttribute("nodos", obtenerNombres(grafo));
            model.addAttribute("aristas", obtenerAristas(grafo));
            return "index";
        }

        Dijkstra dijkstra = new Dijkstra(grafo);
        dijkstra.calcular(origen);
        List<Nodo> camino = dijkstra.obtenerCamino(destino);

        List<String> caminoTexto = new ArrayList<>();
        for (Nodo nodo : camino) {
            caminoTexto.add(nodo.getNombre());
        }

        model.addAttribute("inicio", inicio);
        model.addAttribute("fin", fin);
        model.addAttribute("camino", String.join(" -> ", caminoTexto));
        model.addAttribute("distancia", dijkstra.getDistancia(destino));
        model.addAttribute("mostrarModalResultado", true);
        model.addAttribute("nodos", obtenerNombres(grafo));
        model.addAttribute("aristas", obtenerAristas(grafo));

        return "index";
    }

    @PostMapping("/agregar-nodo")
    public String agregarNodo(@RequestParam(required = false) String nombre, RedirectAttributes redirectAttributes) {
        String nombreFinal = nombre;
        if (nombreFinal == null || nombreFinal.isBlank()) {
            nombreFinal = generarNombreAleatorio();
        } else {
            nombreFinal = nombreFinal.trim().toUpperCase();
        }

        Nodo nodo = new Nodo(nombreFinal);
        if (!grafo.existeNodo(nodo)) {
            grafo.agregarNodo(nodo);
            redirectAttributes.addFlashAttribute("mensaje", "Nodo agregado: " + nodo.getNombre());
        } else {
            redirectAttributes.addFlashAttribute("mensaje", "El nodo ya existe");
        }
        return "redirect:/";
    }

    @PostMapping("/renombrar-nodo")
    public String renombrarNodo(@RequestParam String nombreActual,
                                @RequestParam String nuevoNombre,
                                RedirectAttributes redirectAttributes) {
        if (nuevoNombre == null || nuevoNombre.isBlank()) {
            redirectAttributes.addFlashAttribute("mensaje", "El nombre no puede estar vacío");
            return "redirect:/";
        }

        Map<String, Nodo> nodos = new LinkedHashMap<>();
        for (Nodo nodo : grafo.getListaAdyacencia().keySet()) {
            nodos.put(nodo.getNombre(), nodo);
        }

        Nodo nodo = nodos.get(nombreActual.trim().toUpperCase());
        if (nodo == null) {
            redirectAttributes.addFlashAttribute("mensaje", "Nodo no encontrado");
            return "redirect:/";
        }

        String nuevoNombreNormalizado = nuevoNombre.trim().toUpperCase();
        Nodo nodoRenombrado = new Nodo(nuevoNombreNormalizado);
        if (!grafo.existeNodo(nodoRenombrado) || nuevoNombreNormalizado.equals(nombreActual.trim().toUpperCase())) {
            nodo.setNombre(nuevoNombreNormalizado);
            redirectAttributes.addFlashAttribute("mensaje", "Nodo renombrado a: " + nuevoNombreNormalizado);
        } else {
            redirectAttributes.addFlashAttribute("mensaje", "El nombre ya existe");
        }
        return "redirect:/";
    }

    @PostMapping("/agregar-arista")
    public String agregarArista(@RequestParam String origen,
                                @RequestParam String destino,
                                @RequestParam int peso,
                                RedirectAttributes redirectAttributes) {
        if (origen == null || destino == null || origen.isBlank() || destino.isBlank()) {
            redirectAttributes.addFlashAttribute("mensaje", "Debes indicar origen y destino");
            return "redirect:/";
        }

        if (peso <= 0) {
            redirectAttributes.addFlashAttribute("mensaje", "El peso debe ser mayor que cero");
            return "redirect:/";
        }

        Map<String, Nodo> nodos = new LinkedHashMap<>();
        for (Nodo nodo : grafo.getListaAdyacencia().keySet()) {
            nodos.put(nodo.getNombre(), nodo);
        }

        Nodo nodoOrigen = nodos.get(origen.trim().toUpperCase());
        Nodo nodoDestino = nodos.get(destino.trim().toUpperCase());

        if (nodoOrigen == null || nodoDestino == null) {
            redirectAttributes.addFlashAttribute("mensaje", "Ambos nodos deben existir");
            return "redirect:/";
        }

        grafo.agregarArista(nodoOrigen, nodoDestino, peso);
        redirectAttributes.addFlashAttribute("mensaje", "Arista agregada: " + origen + " -> " + destino + " (" + peso + ")");
        return "redirect:/";
    }

    private Grafo construirGrafoDemo() {
        Grafo grafo = new Grafo();
        Map<String, Nodo> nodos = new LinkedHashMap<>();

        for (String nombre : List.of("A", "B")) {
            Nodo nodo = new Nodo(nombre);
            grafo.agregarNodo(nodo);
            nodos.put(nombre, nodo);
        }

        grafo.agregarArista(nodos.get("A"), nodos.get("B"), 4);

        return grafo;
    }

    private String generarNombreAleatorio() {
        Random random = new Random();
        String letras = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        return String.valueOf(letras.charAt(random.nextInt(letras.length()))) + random.nextInt(10);
    }

    private List<String> obtenerNombres(Grafo grafo) {
        List<String> nombres = new ArrayList<>();
        for (Nodo nodo : grafo.getListaAdyacencia().keySet()) {
            nombres.add(nodo.getNombre());
        }
        return nombres;
    }

    private List<Map<String, Object>> obtenerAristas(Grafo grafo) {
        List<Map<String, Object>> aristas = new ArrayList<>();
        for (Map.Entry<Nodo, List<Arista>> entrada : grafo.getListaAdyacencia().entrySet()) {
            for (Arista arista : entrada.getValue()) {
                Map<String, Object> dato = new LinkedHashMap<>();
                dato.put("origen", entrada.getKey().getNombre());
                dato.put("destino", arista.getDestino().getNombre());
                dato.put("peso", arista.getPeso());
                aristas.add(dato);
            }
        }
        return aristas;
    }
}
