package algoritmos;
import model.Vertice;

// Implementa "Comparable" para que a PriorityQueue do A* saiba como ordenar os nós.
public class auxBusca implements Comparable<auxBusca> {

    public final Vertice vertice;
    public final auxBusca noAnterior;
    public final double g; // Custo do início até o vértice atual (dist)
    public final double h; // Custo estimado do vértice atual até o fim (heurística)
    public final double f; // Custo total (g + h)

    public auxBusca(Vertice vertice, auxBusca noAnterior, double g, double h) {
        this.vertice = vertice;
        this.noAnterior = noAnterior;
        this.g = g; // dist
        this.h = h; // h
        this.f = g + h; // soma
    }

    @Override
    public int compareTo(auxBusca outro) {
        // Compara os nós com base no seu custo total 'f'
        return Double.compare(this.f, outro.f);
    }
}
