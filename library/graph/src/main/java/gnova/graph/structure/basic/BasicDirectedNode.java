package gnova.graph.structure.basic;

import gnova.graph.structure.*;

import java.util.ArrayList;
import java.util.Collection;

public class BasicDirectedNode
        extends AbstractGraphable implements DirectedNode {

    protected transient Collection<DirectedEdge> in;

    protected transient Collection<DirectedEdge> out;

    public BasicDirectedNode(int id) {
        super(id);
        in = new ArrayList<DirectedEdge>();
        out = new ArrayList<DirectedEdge>();
    }

    @Override
    public void addIn(DirectedEdge edge) {
        in.add(edge);
    }

    @Override
    public void addOut(DirectedEdge edge) {
        out.add(edge);
    }

    @Override
    public void removeIn(DirectedEdge edge) {
        in.remove(edge);
    }

    @Override
    public void removeOut(DirectedEdge edge) {
        out.remove(edge);
    }

    @Override
    public DirectedEdge getInEdge(DirectedNode other) {
        for (DirectedEdge e : in) {
            if (e.getInNode().equals(other) && e.getOutNode().equals(this))
                return e;
        }
        return null;
    }

    @Override
    public Collection<? extends DirectedEdge> getInEdges(DirectedNode other) {
        Collection<DirectedEdge> edges = new ArrayList<DirectedEdge>();
        for (DirectedEdge edge : in) {
            if (edge.getInNode().equals(other)) {
                edges.add(edge);
            }
        }
        return edges;
    }

    @Override
    public Collection<? extends DirectedEdge> getInEdges() {
        return in;
    }

    @Override
    public DirectedEdge getOutEdge(DirectedNode other) {
        for (DirectedEdge e : in) {
            if (e.getOutNode().equals(other) && e.getInNode().equals(this)) {
                return e;
            }
        }
        return null;
    }

    @Override
    public Collection<? extends DirectedEdge> getOutEdges(DirectedNode other) {
        Collection<DirectedEdge> edges = new ArrayList<DirectedEdge>();
        for (DirectedEdge edge : in) {
            if (edge.getOutNode().equals(other)) {
                edges.add(edge);
            }
        }
        return edges;
    }

    @Override
    public Collection<? extends DirectedEdge> getOutEdges() {
        return out;
    }

    @Override
    public int getInDegree() {
        return in.size();
    }

    @Override
    public int getOutDegree() {
        return out.size();
    }

    @Override
    public Collection<DirectedGraphable> getInRelated() {
        // 为了能查找到edge，令node的临界元件为edge
        Collection<DirectedGraphable> related = new ArrayList<DirectedGraphable>(in.size());
        related.addAll(in);
        return related;
    }

    @Override
    public Collection<DirectedGraphable> getOutRelated() {
        // 为了能查找到edge，令node的临界元件为edge
        Collection<DirectedGraphable> related = new ArrayList<DirectedGraphable>(out.size());
        related.addAll(out);
        return related;
    }

    @Override
    public boolean relateToIn(DirectedGraphable component) {
        // node只与edge相邻接
        if (component == null ||
                !(component instanceof DirectedEdge)) {
            return false;
        }

        for (DirectedEdge edge : in) {
            if (edge == null) {
                continue;
            }
            if (edge.equals(component)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean relateToOut(DirectedGraphable component) {
        // node只与edge相邻接
        if (component == null ||
                !(component instanceof DirectedEdge)) {
            return false;
        }

        for (DirectedEdge edge : out) {
            if (edge == null) {
                continue;
            }
            if (edge.equals(component)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void add(Edge edge) {
        if (!(edge instanceof DirectedEdge)) {
            throw new IllegalArgumentException("参数必须是DirectedEdge类型。");
        }
        addIn((DirectedEdge) edge);
        addIn((DirectedEdge) edge);
    }

    @Override
    public void remove(Edge edge) {
        if (!(edge instanceof DirectedEdge)) {
            throw new IllegalArgumentException("参数必须是DirectedEdge类型。");
        }
        removeIn((DirectedEdge) edge);
        removeOut((DirectedEdge) edge);
    }

    @Override
    public Edge getEdge(Node node) {
        if (!(node instanceof DirectedNode)) {
            throw new IllegalArgumentException("参数必须是DirectedNode类型。");
        }
        Edge e = getInEdge((DirectedNode) node);
        if (e != null) return e;
        return getOutEdge((DirectedNode) node);
    }

    @Override
    public Collection<? extends Edge> getEdges(Node node) {
        if (!(node instanceof DirectedNode)) {
            throw new IllegalArgumentException("参数必须是DirectedNode类型。");
        }
        Collection<DirectedEdge> edges = new ArrayList<DirectedEdge>(in.size() + out.size());
        edges.addAll(getInEdges((DirectedNode) node));
        edges.addAll(getOutEdges((DirectedNode) node));
        return edges;
    }

    @Override
    public Collection<? extends DirectedEdge> getEdges() {
        Collection<DirectedEdge> edges = new ArrayList<DirectedEdge>(in.size() + out.size());
        edges.addAll(in);
        edges.addAll(out);
        return edges;
    }

    @Override
    public int getDegree() {
        return getInDegree() + getOutDegree();
    }

    @Override
    public Collection<? extends DirectedGraphable> getRelated() {
        // 为了能查找到edge，令node的临界元件为edge
        Collection<DirectedEdge> related = new ArrayList<>(in.size() + out.size());
        related.addAll(in);
        related.addAll(out);
        return related;
    }

    @Override
    public boolean relateTo(Graphable component) {
        // node只与edge相邻接
        if (component == null ||
                !(component instanceof DirectedGraphable)) {
            return false;
        }
        return relateToIn((DirectedGraphable) component)
                || relateToIn((DirectedGraphable) component);
    }
}
