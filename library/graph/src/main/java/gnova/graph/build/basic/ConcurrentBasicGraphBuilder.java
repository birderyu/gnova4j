package gnova.graph.build.basic;

import gnova.graph.structure.basic.BasicEdge;
import gnova.graph.structure.basic.BasicNode;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 支持并发的网图构造器
 *
 * @author birderyu
 * @date 2017/4/22
 */
public class ConcurrentBasicGraphBuilder
		extends BasicGraphBuilder {

	/**
	 * ID分配器
	 */
	private static AtomicInteger currentId = new AtomicInteger(0);

	private final Map<BasicNode, Boolean> nodes;
	private final Map<BasicEdge, Boolean> edges;

    public ConcurrentBasicGraphBuilder() {
        nodes = new ConcurrentHashMap<BasicNode, Boolean>();
        edges = new ConcurrentHashMap<BasicEdge, Boolean>();
    }

    @Override
    protected int createComponentId()  {
		return currentId.getAndIncrement();
	}

    @Override
    protected Set<BasicNode> getNodesFromCache() {
        return nodes.keySet();
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
        nodes.put(n, Boolean.TRUE);
    }

    @Override
    protected Set<BasicEdge> getEdgesFromCache() {
        return edges.keySet();
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
        edges.put(e, Boolean.FALSE);
    }

}
