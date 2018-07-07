package gnova.graph.structure.basic;

import gnova.graph.structure.AbstractGraphable;
import gnova.graph.structure.Edge;
import gnova.graph.structure.Graphable;
import gnova.graph.structure.Node;

import java.util.ArrayList;
import java.util.Collection;

public class BasicNode
        extends AbstractGraphable implements Node {

    /**
     * 与该节点相关联的边
     */
    protected transient Collection<Edge> edges;

    public BasicNode(int id) {
        super(id);
        edges = buildCollection();
    }

    @Override
    public int size() {
        return edges.size();
    }

    @Override
    public boolean isEmpty() {
        return edges.isEmpty();
    }

    @Override
    public void add(Edge edge) {
        edges.add(edge);
    }

    @Override
    public void remove(Edge edge) {
        this.edges.remove(edge);
    }

    @Override
    public Edge getEdge(Node node) {
        for (Edge e : edges) {
            if ((e.getNodeA().equals(this) && e.getNodeB().equals(node)) ||
                    (e.getNodeA().equals(node) && e.getNodeB().equals(this))) {
                return e;
            }
        }
        return null;
    }

    @Override
    public Collection<Edge> getEdges(Node node) {
        Collection<Edge> edges = new ArrayList<>();
        for (Edge edge : this.edges) {
            if ((edge.getNodeA().equals(this) && edge.getNodeB().equals(node)) ||
                    (edge.getNodeA().equals(node) && edge.getNodeB().equals(this))) {
                edges.add(edge);
            }
        }
        return edges;
    }

    @Override
    public Collection<? extends Edge> getEdges() {
        return edges;
    }

    @Override
    public Collection<? extends Graphable> getRelated() {
        // 为了能查找到edge，令node的临界元件为edge
        Collection<Edge> related = new ArrayList<>(edges.size());
        related.addAll(edges);
        return related;
    }

    @Override
    public boolean relateTo(Graphable component) {
        // node只与edge相邻接
        if (component == null ||
                !(component instanceof Edge)) {
            return false;
        }

        for (Edge edge : edges) {
            if (edge == null) {
                continue;
            }
            if (edge.equals(component)) {
                return true;
            }
        }
        return false;
    }

    protected Collection<Edge> buildCollection() {
        return new ArrayList<>();
    }
}
