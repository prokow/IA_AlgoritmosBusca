package model;

import java.util.HashMap;
import java.util.Map;

public class Vertice {
    public String nome;                     // Nome único que identifica o vértice (ex: "a0").
    public Map<Vertice, Integer> vizinhos;  // Mapa que armazena os vértices vizinhos e o custo da aresta.
    public int heuristica;                  // Valor heurístico (custo estimado) deste vértice até o nó final.

    //Construtor da classe Vertice.
    //Pré-condição: 'nome' deve ser uma String válida e não nula.
    //Pós-condição: Um novo objeto Vertice é criado com o nome especificado e uma lista de vizinhos vazia.
    //Entrada: String 'nome' - O identificador do vértice.
    //Saída: Nenhuma.
    public Vertice(String nome){
        this.nome = nome;
        this.vizinhos = new HashMap<>();
        this.heuristica = 0;
    }


     //Adiciona uma conexão de saída (aresta direcionada) deste vértice para outro.
     //Pré-condição: 'vizinho' não pode ser nulo e 'peso' deve ser um valor de custo não negativo.
     //Pós-condição: O vértice vizinho é adicionado ao mapa de vizinhança com o seu respectivo custo.
     //Entrada: Vertice 'vizinho' - vértice de destino da conexão.
     //Saída: Nenhuma.
     public void addVizinhos(Vertice vizinho, int peso){
        this.vizinhos.put(vizinho, peso);
     }

     //Define o valor da função heurística para este vértice.
     //Pré-condição: 'valor' deve ser um valor de custo não negativo.
     //Pós-condição: O atributo 'heuristica' do vértice é atualizado.
     //Entrada: int 'valor' - valor da heurística.
     //Saída: Nenhuma.
     public void setHeuristica(int valor){
        this.heuristica = valor;
     }
}
