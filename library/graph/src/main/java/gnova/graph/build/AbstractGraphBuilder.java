package gnova.graph.build;

import gnova.graph.structure.Edge;
import gnova.graph.structure.Node;

import java.util.Collection;
import java.util.Set;

public abstract class AbstractGraphBuilder<N extends Node, E extends Edge>
        implements GraphBuilder<N, E> {

    @Override
    public void clear() {
        clearNodesFromCache();
        clearNodesFromCache();
    }

    @Override
    public void addEdge(E edge) {
        edge.getNodeA().add(edge);
        if (!edge.getNodeA().equals(edge.getNodeB())) {
            edge.getNodeB().add(edge);
        }
        addEdgeToCache(edge);
    }

    @Override
    public void addNode(N node) {
        addNodeToCache(node);
    }

    @Override
    public void removeEdge(E edge) {
        edge.getNodeA().remove(edge);
        edge.getNodeB().remove(edge);
        removeEdgeFromCache(edge);
    }

    @Override
    public void removeEdges(Collection<E> edges) {
        for (E edge : edges) {
            removeEdge(edge);
        }
    }

    @Override
    public void removeNode(N node) {
        Collection<? extends Edge> edges = node.getEdges();
        for (Edge edge : edges) {
            removeEdge((E) edge);
        }
        removeNodeFromCache(node);
    }

    @Override
    public void removeNodes(Collection<N> nodes) {
        for (N node : nodes) {
            removeNode(node);
        }
    }

    /**
     * 创建一个id用于标识每一个图元
     * @return
     */
    protected abstract int createComponentId();

    protected abstract Set<N> getNodesFromCache();

    protected abstract void clearNodesFromCache();

    protected abstract void removeNodeFromCache(N n);

    protected abstract void addNodeToCache(N n);

    protected abstract Set<E> getEdgesFromCache();

    protected abstract void clearEdgesFromCache();

    protected abstract void removeEdgeFromCache(E e);

    protected abstract void addEdgeToCache(E e);
}
