package gnova.graph.structure.basic;

import gnova.graph.structure.AbstractGraph;

import java.io.Serializable;
import java.util.*;

public class BasicGraph
        extends AbstractGraph<BasicNode, BasicEdge> implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 4814181181180041146L;

    public BasicGraph(Set<BasicNode> nodes, Set<BasicEdge> edges) {
        super(nodes, edges);
    }

}
