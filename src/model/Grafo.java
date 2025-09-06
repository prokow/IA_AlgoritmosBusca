package model;

import java.util.Map;
import java.util.HashMap;

public class Grafo {
    private Map<String, Vertice> vertices;
    private Vertice pontoInicial;
    private Vertice pontoFinal;
    private boolean orientado;

    public Grafo(){
        this.vertices = new HashMap<>();
        this.pontoInicial = null;
        this.pontoFinal = null;
        this.orientado = false;
    }

    public Vertice addVertice(String nome){
        if(this.vertices.containsKey(nome)){ //Verifica se o mapa vertices ja tem o nome
            return this.vertices.get(nome);  //se sim, pega o vertice existe no mapa e retorna
        } else {
            Vertice novoVertice = new Vertice(nome); //se não, cria um novo objeto Vertice

            this.vertices.put(nome, novoVertice);    //add o novo vertice ao mapa, usando seu nome como chave
            return novoVertice;                      //e retorna o vertice criado
        }
    }

    public void addAresta(String origem, String destino, int peso){
        Vertice VerticeOrigem = addVertice(origem);
        Vertice VerticeDestino = addVertice(destino);

        VerticeOrigem.addVizinhos(VerticeDestino, peso);
        if(!this.orientado){
            VerticeDestino.addVizinhos(VerticeOrigem, peso);
        }
    }

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

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("---Grafo--- \n");
        sb.append("Ponto inicial: ").append(pontoInicial).append("\n");
        sb.append("Ponto final: ").append(pontoFinal).append("\n");
        sb.append("Orientado: ").append(orientado).append("\n");
        sb.append("-----------------\n");

        for(Vertice v : vertices.values()){
            sb.append("Vertice '").append(v.nome).append("' (h=").append(v.heuristica).append(") tem vizinhos:\n");
            for(Map.Entry<Vertice, Integer> vizinho : v.vizinhos.entrySet()){
                sb.append(" -> ").append(vizinho.getKey().nome).append(" (custo: ").append(vizinho.getValue()).append(")\n");
            }
        }
        return sb.toString();
    }
}
