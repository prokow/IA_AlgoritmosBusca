package algoritmos;

import model.Grafo;
import model.Vertice;
import java.util.*;


//Implementa o algoritmo de Busca em Profundidade (DFS - Depth-First Search).
//Onde será explorado um ramo do grafo o mais fundo possível antes fazer o backtracking.
public class buscaProfundidade {
    private final Grafo grafo;      //o grafo que será percorrido(uma unica vez)
    private int nosVisitados;       //numero de nós visitado
    private int maxSizeFronteira;   //Medida de desempenho: tamanho maximo da fronteira


     //Construtor da classe.
     //Pré-condição: O objeto 'grafo' fornecido não pode ser nulo.
     //Pós-condição: Uma nova instância de 'buscaProfundidade' é criada.
     //Entrada: Grafo g - O grafo no qual a busca será realizada.
     //Saída: Nenhuma.
    public buscaProfundidade(Grafo g) {
        this.grafo = g;
    }

    //Executa o algoritmo de Busca em Profundidade para encontrar um caminho do início ao fim.
    //Pré-condição: O 'grafo' da classe deve conter um ponto inicial e um ponto final válidos.
    //Pós-condição: O primeiro caminho encontrado é impresso, ou uma mensagem de "solução não encontrada".
    //Entrada: Nenhuma.
    //Saída: Imprime os resultados no console.
    public void busca(){
        System.out.println("==== Inicio da execucao da Busca em Profundidade ===");
        this.nosVisitados = 1;
        this.maxSizeFronteira = 0;

        // A 'fronteira' aqui é uma Pilha (Stack). Isso é o CORAÇÃO do DFS.
        // A pilha segue a regra LIFO (Last-In, First-Out), ou seja, o último
        // nó adicionado será o primeiro a ser explorado. É isso que faz a busca "mergulhar".
        Stack<auxBusca> fronteira = new Stack<>();

        // O conjunto 'visitados' é crucial para o DFS, para evitar que o algoritmo
        // entre em loops infinitos caso o grafo tenha ciclos.

        Set<Vertice> visitados = new HashSet<>();
        // Pega o ponto de partida do grafo.
        Vertice verticeInicial = grafo.getPontoInicial();

        // Cria o primeiro nó de busca.
        auxBusca noInicial = new auxBusca(verticeInicial, null, 0, verticeInicial.heuristica);

        // Coloca o nó inicial no topo da pilha.
        fronteira.push(noInicial);

        // A medida de desempenho que você escolheu é "Nós Gerados". O nó inicial é o primeiro.
        int i = 1;

        // O laço continua enquanto a pilha de caminhos a explorar não estiver vazia.
        while (!fronteira.isEmpty()) {
            // --- Bloco de Impressão de cada iteração ---
            System.out.println("Iteração " + i + ":");
            System.out.print("Pilha: ");
            for (auxBusca no : fronteira) {
                // A impressão foi ajustada para o formato do DFS, mostrando apenas a distância.
                System.out.printf("(%s: dist = %.0f) ", no.vertice.nome, no.g);
            }
            System.out.println("\nMedida de desempenho (Nós Gerados): " + this.nosVisitados);
            System.out.println("Tamanho atual da Pilha: " + fronteira.size());
            System.out.println("-----------------");

            // Pega o nó do TOPO da pilha (o mais recente). É aqui que a "profundidade" acontece.
            auxBusca noAtual = fronteira.pop();
            this.nosVisitados++;

            // Se já visitamos este vértice, pulamos para o próximo para evitar ciclos.
            if (visitados.contains(noAtual.vertice)) {
                continue;
            }
            // Marca o vértice como visitado.
            visitados.add(noAtual.vertice);

            if (noAtual.vertice == grafo.getPontoFinal()) {
                System.out.println("Fim da execução: Solução encontrada!");
                imprimirCaminho(noAtual);
                return;
            }

            // Adiciona vizinhos na pilha (a ordem pode ser invertida para um DFS mais clássico)
            // Para cada vizinho do nó atual...
            for (Map.Entry<Vertice, Integer> vizinhoEntry : noAtual.vertice.vizinhos.entrySet()) {
                Vertice vizinho = vizinhoEntry.getKey();
                // ...se o vizinho ainda não foi visitado...
                if (!visitados.contains(vizinho)) {
                    // ...calcula o custo para chegar até ele...
                    double custoAteVizinho = noAtual.g + vizinhoEntry.getValue();
                    auxBusca noVizinho = new auxBusca(vizinho, noAtual, custoAteVizinho, vizinho.heuristica);

                    // ...e o COLOCA NO TOPO da pilha. Ele será o próximo a ser explorado.
                    fronteira.push(noVizinho);

                    // Incrementa a contagem de nós gerados.
                    this.nosVisitados++;
                }
            }

            if(fronteira.size() > this.maxSizeFronteira){
                this.maxSizeFronteira = fronteira.size();
            }
            i++;
        }
        System.out.println("Fim da execução: Solução não encontrada.");
    }


    //Reconstrói o caminho da busca a partir do nó final e imprime um resumo completo.
    //Pré-condição: 'noFinal' deve ser o nó que contém o vértice objetivo, encontrado pela busca.
    //Pós-condição: O caminho encontrado, seu custo e as medidas de desempenho são impressos no console.
    //Entrada: auxBusca noFinal - que será nó de busca que contém o vértice final e o histórico do caminho.
    //Saída: Imprime o Resumo completo desta busca realizada.
    private void imprimirCaminho(auxBusca noFinal) {
        List<Vertice> caminho = new ArrayList<>();
        auxBusca noAtual = noFinal;

        // O processo de reconstrução do caminho é idêntico ao do A*,
        // pois ambos usam a referência 'noAnterior'.
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
        System.out.println("\nNós Visitados: " + this.nosVisitados);
        System.out.println("Medida de desempenho (Tamanho Máximo da Fronteira): " + this.maxSizeFronteira);
    }
}

