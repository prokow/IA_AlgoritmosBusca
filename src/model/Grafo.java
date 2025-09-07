package model;

import java.util.Map;
import java.util.HashMap;

public class Grafo {
    private Map<String, Vertice> vertices;
    private Vertice pontoInicial;
    private Vertice pontoFinal;
    private boolean orientado;


    //Descrição: Construtor da classe Grafo.
    //Pré-condição: Nenhuma.
    //Pós-condição: Um novo objeto Grafo é criado, vazio e configurado como não-orientado por padrão.
    //Entrada: Nenhuma.
    //Saída: Nenhuma.
    public Grafo(){
        this.vertices = new HashMap<>();
        this.pontoInicial = null;
        this.pontoFinal = null;
        this.orientado = false;
    }


    //Adiciona um novo vértice ao grafo ou retorna um existente se já houver um com o mesmo nome.
    //Pré-condição: 'nome' deve ser uma String válida e não nula.
    //Pós-condição: O vértice com o nome especificado existe no grafo e é retornado.
    //Entrada: String 'nome' - O identificador do vértice a ser adicionado/recuperado.
    //Saída: O objeto 'Vertice' correspondente ao nome.
    public Vertice addVertice(String nome){
        if(this.vertices.containsKey(nome)){ //Verifica se o mapa vertices ja tem o nome
            return this.vertices.get(nome);  //se sim, pega o vertice existe no mapa e retorna
        } else {
            Vertice novoVertice = new Vertice(nome); //se não, cria um novo objeto Vertice

            this.vertices.put(nome, novoVertice);    //add o novo vertice ao mapa, usando seu nome como chave
            return novoVertice;                      //e retorna o vertice criado
        }
    }



    //Adiciona uma aresta (conexão) entre dois vértices com um custo definido.
    //Lida com a criação da aresta de volta caso o grafo seja não-orientado.
    //Pré-condição: 'origem' e 'destino' são Strings válidas, 'peso' é não negativo.
    //Pós-condição: Uma ou duas conexões (ida e volta) são adicionadas entre os vértices correspondentes.
    //Entrada: String origem, String destino, int peso.
    //Saída: Nenhuma.
    public void addAresta(String origem, String destino, int peso){
        Vertice VerticeOrigem = addVertice(origem);
        Vertice VerticeDestino = addVertice(destino);

        VerticeOrigem.addVizinhos(VerticeDestino, peso);
        if(!this.orientado){
            VerticeDestino.addVizinhos(VerticeOrigem, peso);
        }
    }

    // Métodos Getters e Setters
    public Vertice getPontoInicial(){
        return pontoInicial;
    }

    public void setPontoInicial(String nome) {
        this.pontoInicial = addVertice(nome);
    }


    public Vertice getPontoFinal(){
        return pontoFinal;
    }

    public void setPontoFinal(String nome){
        this.pontoFinal = addVertice(nome);
    }

    public void setOrientado(boolean orientado){
        this.orientado = orientado;
    }

    public Vertice getVertice(String nome){
        return this.vertices.get(nome);
    }
}
