package gnova.graph.structure;

import java.util.Set;

public abstract class AbstractGraph<N extends Node, E extends Edge>
        implements Graph<N, E> {

    /**
     * 节点的集合
     */
    private final Set<N> nodes;

    /**
     * 边的集合
     */
    private final Set<E> edges;

    public AbstractGraph(Set<N> nodes, Set<E> edges) {
        this.nodes = nodes;
        this.edges = edges;
    }

    @Override
    public int nodeSize() {
        return nodes == null ? 0 : nodes.size();
    }

    @Override
    public int edgeSize() {
        return edges == null ? 0 : edges.size();
    }

    @Override
    public boolean isNodeEmpty() {
        if (nodes == null) {
            return true;
        }
        return nodes.isEmpty();
    }

    @Override
    public boolean isEdgeEmpty() {
        if (edges == null) {
            return true;
        }
        return edges.isEmpty();
    }

    @Override
    public Set<E> edgeSet() {
        return this.edges;
    }

    @Override
    public Set<N> nodeSet() {
        return this.nodes;
    }

}
