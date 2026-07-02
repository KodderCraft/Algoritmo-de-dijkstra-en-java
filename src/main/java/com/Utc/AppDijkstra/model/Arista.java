package com.Utc.AppDijkstra.model;

//Clase publica Arista
public class Arista {
	
	//Atributos
    private Nodo destino;
    private int peso;
    //Constructor Parametrizado
    public Arista(Nodo destino, int peso) {
        this.destino = destino;
        this.peso = peso;
    }
    
    //Getters and setters
    public Nodo getDestino() {
        return destino;
    }
    
    public void setDestino(Nodo destino) {
        this.destino = destino;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    // Sobrescribimos el toString() para dar una representación legible del objeto.
    // Es muy útil para depurar o para imprimir las conexiones de un nodo
    // de forma clara.
    @Override
    public String toString() {
        return destino.getNombre() + " (" + peso + ")";
    }

}