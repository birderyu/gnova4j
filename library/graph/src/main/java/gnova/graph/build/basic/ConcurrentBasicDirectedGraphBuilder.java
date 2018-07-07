package gnova.graph.build.basic;

import gnova.graph.structure.basic.BasicDirectedEdge;
import gnova.graph.structure.basic.BasicDirectedNode;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ConcurrentBasicDirectedGraphBuilder
        extends BasicDirectedGraphBuilder {

    /**
     * ID分配器
     */
    private static AtomicInteger currentId = new AtomicInteger(0);

    private final Map<BasicDirectedNode, Boolean> nodes;
    private final Map<BasicDirectedEdge, Boolean> edges;

    public ConcurrentBasicDirectedGraphBuilder() {
        nodes = new ConcurrentHashMap<BasicDirectedNode, Boolean>();
        edges = new ConcurrentHashMap<BasicDirectedEdge, Boolean>();
    }

    @Override
    public int createComponentId()  {
        return currentId.getAndIncrement();
    }

    @Override
    protected Set<BasicDirectedNode> getNodesFromCache() {
        return nodes.keySet();
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
        nodes.put(n, Boolean.TRUE);
    }

    @Override
    protected Set<BasicDirectedEdge> getEdgesFromCache() {
        return edges.keySet();
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
        edges.put(e, Boolean.FALSE);
    }
}
