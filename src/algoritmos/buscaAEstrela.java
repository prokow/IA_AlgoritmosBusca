package algoritmos;

import model.*;

import java.util.*;


public class buscaAEstrela {
    private final Grafo grafo;      //o grafo que será percorrido(uma unica vez)
    private int maxSizeFronteira;   //Medida de desempenho: tamanho maximo da fronteira

    //Construtor da classe.
    //Pré-condição: O objeto 'grafo' fornecido não pode ser nulo.
    //Pós-condição: Uma nova instância de 'buscaAEstrela' é criada.
    //Entrada: Grafo g - O grafo no qual a busca será realizada.
    //Saída: Nenhuma.
    public buscaAEstrela(Grafo grafo) {
        this.grafo = grafo;
    }


    //Executa o algoritmo de busca A* para encontrar um caminho de menor custodo início ao fim.
    //Pré-condição: O 'grafo' da classe deve conter um ponto inicial e um ponto final válidos.
    //Pós-condição: O caminho ótimo é encontrado e impresso, ou uma mensagem de "solução não encontrada" é exibida.
    //Entrada: Nenhuma.
    //Saída: Imprime os resultados no console.
    public void buscar() {
        System.out.println("Início da execução da Busca A*");

        // Inicializa a medida de desempenho.
        this.maxSizeFronteira = 0;

        // A 'fronteira' é a lista de nós a serem visitados. A PriorityQueue garante
        // que o nó com o menor custo 'f' (g+h) esteja sempre no início.
        PriorityQueue<auxBusca> fronteira = new PriorityQueue<>();

        // O conjunto 'explorados' guarda os nós que já foram visitados para não os
        // processarmos novamente, evitando ciclos e trabalho redundante.
        Set<Vertice> explorados = new HashSet<>();

        // Pega o ponto de partida do grafo.
        Vertice verticeInicial = grafo.getPontoInicial();

        // Cria o primeiro "Nó de Busca" para o ponto inicial.
        // O nó anterior é nulo, o custo 'g' é 0, e a heurística 'h' vem do próprio vértice.
        auxBusca noInicial = new auxBusca(verticeInicial, null, 0, verticeInicial.heuristica);

        // Adiciona o nó inicial à fronteira para começar a busca.
        fronteira.add(noInicial);

        int i = 1;

        // O laço continua enquanto houver nós na fronteira para serem explorados.
        while (!fronteira.isEmpty()) {
            // --- Bloco de Impressão ---
            System.out.println("Iteração " + i + ":");
            System.out.print("Lista: ");
            List<auxBusca> listaOrdenada = new ArrayList<>(fronteira);
            Collections.sort(listaOrdenada); // Ordena para garantir a visualização correta da prioridade

            for (auxBusca no : listaOrdenada) {
                System.out.printf("(%s: %.0f + %.0f = %.0f) ", no.vertice.nome, no.g, no.h, no.f);
            }

            System.out.println("\nTamanh atual da fronteira: " + fronteira.size());
            System.out.println("-----------------");

            // Pega o nó mais promissor (com o menor 'f') da fronteira para expandir.
            // O '.poll()' remove e retorna o elemento no início da fila de prioridade.
            auxBusca noAtual = fronteira.poll();

            // Otimização: Se já encontramos um caminho melhor para este nó e já o exploramos, pulamos.
            if (explorados.contains(noAtual.vertice)) {
                continue;
            }
            // Marca o vértice atual como explorado.
            explorados.add(noAtual.vertice);

            if (noAtual.vertice == grafo.getPontoFinal()) {
                System.out.println("Fim da execução: Solução ótima encontrada!");
                imprimirCaminho(noAtual);
                return;
            }

            // Para cada vizinho do nó atual...
            for (Map.Entry<Vertice, Integer> vizinhoEntry : noAtual.vertice.vizinhos.entrySet()) {
                Vertice vizinho = vizinhoEntry.getKey();

                // ...se o vizinho ainda não foi explorado...
                if (!explorados.contains(vizinho)) {
                    // ...calcula o novo custo para chegar até ele (custo até o nó atual + custo da aresta).
                    double custoAteVizinho = noAtual.g + vizinhoEntry.getValue();

                    // Cria um novo nó de busca para este vizinho, guardando o caminho.
                    auxBusca noVizinho = new auxBusca(vizinho, noAtual, custoAteVizinho, vizinho.heuristica);

                    // Adiciona o novo nó à fronteira. A PriorityQueue o colocará na posição correta.
                    fronteira.add(noVizinho);
                }
            }

            // Após adicionar os vizinhos, verifica se a fronteira atingiu um novo tamanho máximo.
            if(fronteira.size() > this.maxSizeFronteira){
                this.maxSizeFronteira = fronteira.size();
            }
            i++;
        }
        // Esta linha só é alcançada se o laço terminar (fronteira vazia) sem encontrar o objetivo.
        System.out.println("Fim da execução: Solução não encontrada.");
    }

    //Reconstrói o caminho da busca a partir do nó final e imprime um resumo completo.
    //Pré-condição: 'noFinal' deve ser o nó que contém o vértice objetivo, encontrado pela busca.
    //Pós-condição: O caminho ótimo encontrado, seu custo e as medidas de desempenho são impressos no console.
    //Entrada: auxBusca noFinal - que será nó de busca que contém o vértice final e o histórico do caminho.
    //Saída: Imprime o Resumo completo desta busca realizada.
    private void imprimirCaminho(auxBusca noFinal) {
        // Cria uma lista para armazenar os vértices do caminho.
        List<Vertice> caminho = new ArrayList<>();
        auxBusca noAtual = noFinal;

        // Inicia um laço que "volta no tempo", seguindo a referência 'noAnterior' de cada nó.
        while (noAtual != null) {
            caminho.add(noAtual.vertice);
            noAtual = noAtual.noAnterior;
        }

        // A lista foi construída de trás para frente (Fim -> Início), então precisamos invertê-la.
        Collections.reverse(caminho);

        // Imprime o resumo final formatado.
        System.out.println("\n--- Resumo da Execução (A*) ---");
        System.out.println("Custo do caminho ótimo: " + noFinal.g);
        System.out.print("Caminho: ");
        for (int i = 0; i < caminho.size(); i++) {
            System.out.print(caminho.get(i).nome + (i == caminho.size() - 1 ? "" : " -> "));
        }
        System.out.println("\nMedida de desempenho final (Tamanho Máximo da Fronteira): " + this.maxSizeFronteira);
    }
}
