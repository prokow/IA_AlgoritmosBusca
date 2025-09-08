package algoritmos;
import model.Vertice;

//Classe auxiliar que funciona como um "nó de busca" ou um "estado" na árvore de busca.
public class auxBusca implements Comparable<auxBusca> {

    public final Vertice vertice;       // O vértice do grafo que este nó de busca representa.
    public final auxBusca noAnterior;   // Referência para o nó de busca que veio antes neste caminho.
    public final double g;              // Custo do início até o vértice atual (dist)
    public final double h;              // Custo estimado do vértice atual até o fim (heurística)
    public final double f;              // Custo total (g + h)


    //Descrição: Construtor para criar um novo nó de busca.
    //Pré-condição: 'vertice' não pode ser nulo. 'g' e 'h' devem ser valores não negativos.
    //Pós-condição: Um novo objeto 'auxBusca' é instanciado com todos os seus campos definidos. O custo 'f' é calculado automaticamente.
    //Entrada: Vértice do grafo associado a este nó, o nó pai no caminho da busca, o custo real para chegar até este nó, e o valor da heurística para este nó
    //Saída: Nenhuma.
    public auxBusca(Vertice vertice, auxBusca noAnterior, double g, double h) {
        this.vertice = vertice;
        this.noAnterior = noAnterior;
        this.g = g; // dist
        this.h = h; // h
        this.f = g + h; // soma
    }


    //Método de comparação obrigatório da interface Comparable. É usado pela PriorityQueue para ordenar os nós.
    // A ordenação é feita com base no custo total 'f'.
    //Pré-condição: O objeto 'outro' não pode ser nulo.
    //Pós-condição: Retorna um inteiro negativo, zero ou positivo se o custo 'f' deste nó for
    //menor, igual ou maior que o do 'outro' nó, respectivamente.
    //Entrada: auxBusca 'outro' - O outro nó de busca a ser comparado.
    //Saída: Um valor inteiro que representa o resultado da comparação.
    @Override
    public int compareTo(auxBusca outro) {
        // Compara os nós com base no seu custo total 'f'
        return Double.compare(this.f, outro.f);
    }
}
