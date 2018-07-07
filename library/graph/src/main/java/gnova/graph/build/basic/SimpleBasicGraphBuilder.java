package gnova.graph.build.basic;

import gnova.graph.structure.basic.BasicEdge;
import gnova.graph.structure.basic.BasicNode;

import java.util.HashSet;
import java.util.Set;

/**
 * 一个简单的图构造器
 *
 * 该构建器用于在单线程环境中构建图
 *
 * @author birderyu
 * @date 2017/4/22
 */
public class SimpleBasicGraphBuilder
        extends BasicGraphBuilder {

    /**
     * ID分配器
     */
    private int currentId = 0;

    private final Set<BasicNode> nodes;
    private final Set<BasicEdge> edges;

    public SimpleBasicGraphBuilder() {
        nodes = new HashSet<>();
        edges = new HashSet<>();
    }

    @Override
    protected int createComponentId() {
        return currentId++;
    }

    @Override
    protected Set<BasicNode> getNodesFromCache() {
        return nodes;
    }

    @Override
    protected void clearNodesFromCache() {
        nodes.clear();
    }

    @Override
    protected void removeNodeFromCache(BasicNode n) {
        nodes.remove(n);
    }

    @Override
    protected void addNodeToCache(BasicNode n) {
        nodes.add(n);
    }

    @Override
    protected Set<BasicEdge> getEdgesFromCache() {
        return edges;
    }

    @Override
    protected void clearEdgesFromCache() {
        edges.clear();
    }

    @Override
    protected void removeEdgeFromCache(BasicEdge e) {
        edges.remove(e);
    }

    @Override
    protected void addEdgeToCache(BasicEdge e) {
        edges.add(e);
    }
}
