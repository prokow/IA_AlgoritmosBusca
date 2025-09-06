package model;

import java.util.HashMap;
import java.util.Map;

public class Vertice {
    public String nome;
    public Map<Vertice, Integer> vizinhos;
    public int heuristica;

    public Vertice(String nome){
        this.nome = nome;
        this.vizinhos = new HashMap<>();
        this.heuristica = 0;
    }

    public void addVizinhos(Vertice vizinho, int peso){
        this.vizinhos.put(vizinho, peso);
    }

    public void setHeuristica(int valor){
        this.heuristica = valor;
    }

    @Override
    public String toString(){
        return this.nome;
    }
}
