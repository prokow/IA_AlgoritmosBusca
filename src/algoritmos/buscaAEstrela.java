package algoritmos;

import model.*;

import java.util.*;


public class buscaAEstrela {
    private final Grafo grafo;
    private int nosExpandidos; // Medida de desempenho

    public buscaAEstrela(Grafo grafo) {
        this.grafo = grafo;
    }

    public void buscar() {
        System.out.println("Início da execução da Busca A*");
        this.nosExpandidos = 0;

        PriorityQueue<auxBusca> fronteira = new PriorityQueue<>();
        Set<Vertice> explorados = new HashSet<>();

        Vertice verticeInicial = grafo.getPontoInicial();
        auxBusca noInicial = new auxBusca(verticeInicial, null, 0, verticeInicial.heuristica);
        fronteira.add(noInicial);
        this.nosExpandidos = 1; //(nó inicial)

        int iteracao = 1;

        while (!fronteira.isEmpty()) {
            // --- Bloco de Impressão ---
            System.out.println("Iteração " + iteracao + ":");
            System.out.print("Lista: ");
            List<auxBusca> listaOrdenada = new ArrayList<>(fronteira);
            Collections.sort(listaOrdenada);
            for (auxBusca no : listaOrdenada) {
                System.out.printf("(%s: %.0f + %.0f = %.0f) ", no.vertice.nome, no.g, no.h, no.f);
            }
            System.out.println("\nMedida de desempenho: " + this.nosExpandidos);
            System.out.println("-----------------");

            auxBusca noAtual = fronteira.poll();
            this.nosExpandidos++;

            if (explorados.contains(noAtual.vertice)) {
                continue;
            }
            explorados.add(noAtual.vertice);

            if (noAtual.vertice == grafo.getPontoFinal()) {
                System.out.println("Fim da execução: Solução ótima encontrada!");
                imprimirCaminho(noAtual);
                return;
            }

            for (Map.Entry<Vertice, Integer> vizinhoEntry : noAtual.vertice.vizinhos.entrySet()) {
                Vertice vizinho = vizinhoEntry.getKey();
                if (!explorados.contains(vizinho)) {
                    double custoAteVizinho = noAtual.g + vizinhoEntry.getValue();
                    auxBusca noVizinho = new auxBusca(vizinho, noAtual, custoAteVizinho, vizinho.heuristica);
                    fronteira.add(noVizinho);
                    this.nosExpandidos++;
                }
            }
            iteracao++;
        }
        System.out.println("Fim da execução: Solução não encontrada.");
    }

    // Este método é idêntico ao do DFS
    private void imprimirCaminho(auxBusca noFinal) {
        List<Vertice> caminho = new ArrayList<>();
        auxBusca noAtual = noFinal;
        while (noAtual != null) {
            caminho.add(noAtual.vertice);
            noAtual = noAtual.noAnterior;
        }
        Collections.reverse(caminho);

        System.out.println("\n--- Resumo da Execução (A*) ---");
        System.out.println("Custo do caminho ótimo: " + noFinal.g);
        System.out.print("Caminho: ");
        for (int i = 0; i < caminho.size(); i++) {
            System.out.print(caminho.get(i).nome + (i == caminho.size() - 1 ? "" : " -> "));
        }
        System.out.println("\nMedida de desempenho final (nós expandidos): " + this.nosExpandidos);
    }
}
