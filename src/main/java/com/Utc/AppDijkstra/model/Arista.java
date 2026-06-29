package com.Utc.AppDijkstra.model;

public class Arista {

    private Nodo destino;
    private int peso;

    public Arista(Nodo destino, int peso) {
        this.destino = destino;
        this.peso = peso;
    }

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

    @Override
    public String toString() {
        return destino.getNombre() + " (" + peso + ")";
    }

}