package gnova.graph.build.basic;

import gnova.graph.structure.basic.BasicDirectedEdge;
import gnova.graph.structure.basic.BasicDirectedNode;

import java.util.HashSet;
import java.util.Set;

public class SimpleBasicDirectedGraphBuilder
        extends BasicDirectedGraphBuilder {

    /**
     * ID分配器
     */
    private int currentId = 0;

    private final Set<BasicDirectedNode> nodes;
    private final Set<BasicDirectedEdge> edges;

    public SimpleBasicDirectedGraphBuilder() {
        nodes = new HashSet<BasicDirectedNode>();
        edges = new HashSet<BasicDirectedEdge>();
    }

    @Override
    protected int createComponentId() {
        return currentId++;
    }

    @Override
    protected Set<BasicDirectedNode> getNodesFromCache() {
        return nodes;
    }

    @Override
    protected void clearNodesFromCache() {
        nodes.clear();
    }

    @Override
    protected void removeNodeFromCache(BasicDirectedNode n) {
        nodes.remove(n);
    }

    @Override
    protected void addNodeToCache(BasicDirectedNode n) {
        nodes.add(n);
    }

    @Override
    protected Set<BasicDirectedEdge> getEdgesFromCache() {
        return edges;
    }

    @Override
    protected void clearEdgesFromCache() {
        edges.clear();
    }

    @Override
    protected void removeEdgeFromCache(BasicDirectedEdge e) {
        edges.remove(e);
    }

    @Override
    protected void addEdgeToCache(BasicDirectedEdge e) {
        edges.add(e);
    }
}
