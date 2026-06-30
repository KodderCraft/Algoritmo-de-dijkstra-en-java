package com.Utc.AppDijkstra.controller;

import com.Utc.AppDijkstra.algoritmo.Dijkstra;
import com.Utc.AppDijkstra.model.Grafo;
import com.Utc.AppDijkstra.model.Nodo;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DijkstraWebControllerTest {

    @Test
    void debeExponerElGrafoEnElModeloParaDibujarlo() {
        DijkstraWebController controller = new DijkstraWebController();
        Model model = new ExtendedModelMap();

        String vista = controller.mostrarFormulario(model);

        assertEquals("index", vista);
        assertTrue(model.containsAttribute("nodos"));
        assertTrue(model.containsAttribute("aristas"));
    }

    @Test
    void debeCalcularUnCaminoValido() {
        DijkstraWebController controller = new DijkstraWebController();
        Model model = new ExtendedModelMap();

        String vista = controller.calcular("A", "B", model);

        assertEquals("index", vista);
        assertTrue(model.containsAttribute("mostrarModalResultado"));
        assertTrue(model.containsAttribute("camino"));
        assertEquals("A -> B", model.getAttribute("camino"));
    }
}
