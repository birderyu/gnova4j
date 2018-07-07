package gnova.graph.build;

import gnova.graph.structure.DirectedEdge;
import gnova.graph.structure.DirectedGraph;
import gnova.graph.structure.DirectedNode;

public interface DirectedGraphBuilder<DN extends DirectedNode, DE extends DirectedEdge>
        extends GraphBuilder<DN, DE> {

    /**
     * 创建一个有向图
     *
     * @return
     */
    @Override
    DirectedGraph build();

    @Override
    DE buildEdge(DN in, DN out);

}
