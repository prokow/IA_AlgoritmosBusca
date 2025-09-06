package algoritmos;

import model.Grafo;
import model.Vertice;
import java.util.*;

public class buscaProfundidade {
    private final Grafo grafo;
    private int nosVisitados;


    public buscaProfundidade(Grafo grafo) {
        this.grafo = grafo;
    }

    public void busca(){
        System.out.println("==== Inicio da execucao da Busca em Profundidade ===");
        this.nosVisitados = 0;

        Stack<auxBusca> fronteira = new Stack<>();
        Set<Vertice> visitados = new HashSet<>();

        Vertice verticeInicial = grafo.getPontoInicial();
        auxBusca noInicial = new auxBusca(verticeInicial, null, 0, verticeInicial.heuristica);
        fronteira.push(noInicial);
        this.nosVisitados = 1;

        int iteracao = 1;

        while (!fronteira.isEmpty()) {
            // --- Bloco de Impressão ---
            System.out.println("Iteração " + iteracao + ":");
            System.out.print("Pilha: ");
            for (auxBusca no : fronteira) {
                System.out.printf("(%s: dist = %.0f) ", no.vertice.nome, no.g);
            }
            System.out.println("\nMedida de desempenho: " + this.nosVisitados);
            System.out.println("-----------------");

            auxBusca noAtual = fronteira.pop();
            this.nosVisitados++;

            if (visitados.contains(noAtual.vertice)) {
                continue;
            }
            visitados.add(noAtual.vertice);

            if (noAtual.vertice == grafo.getPontoFinal()) {
                System.out.println("Fim da execução: Solução encontrada!");
                imprimirCaminho(noAtual);
                return;
            }

            // Adiciona vizinhos na pilha (a ordem pode ser invertida para um DFS mais clássico)
            for (Map.Entry<Vertice, Integer> vizinhoEntry : noAtual.vertice.vizinhos.entrySet()) {
                Vertice vizinho = vizinhoEntry.getKey();
                if (!visitados.contains(vizinho)) {
                    double custoAteVizinho = noAtual.g + vizinhoEntry.getValue();
                    auxBusca noVizinho = new auxBusca(vizinho, noAtual, custoAteVizinho, vizinho.heuristica);
                    fronteira.push(noVizinho);
                    this.nosVisitados++;
                }
            }
            iteracao++;
        }
        System.out.println("Fim da execução: Solução não encontrada.");
    }

    // Mesmo método do AEstrela, só que agora para a essa busca
    private void imprimirCaminho(auxBusca noFinal) {
        List<Vertice> caminho = new ArrayList<>();
        auxBusca noAtual = noFinal;
        while (noAtual != null) {
            caminho.add(noAtual.vertice);
            noAtual = noAtual.noAnterior;
        }
        Collections.reverse(caminho);

        System.out.println("\n--- Resumo da Execução (DFS) ---");
        System.out.println("Custo do caminho encontrado: " + noFinal.g);
        System.out.print("Caminho: ");
        for (int i = 0; i < caminho.size(); i++) {
            System.out.print(caminho.get(i).nome + (i == caminho.size() - 1 ? "" : " -> "));
        }
        System.out.println("\nMedida de desempenho final (nós visitados): " + this.nosVisitados);
    }
}

