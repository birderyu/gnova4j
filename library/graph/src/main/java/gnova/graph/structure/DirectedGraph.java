package gnova.graph.structure;

/**
 * 有向图
 *
 * @param <DN> 有向节点的类型
 * @param <DE> 有向边的类型
 * @author birderyu
 *
 */
public interface DirectedGraph<DN extends DirectedNode, DE extends DirectedEdge>
	extends Graph<DN, DE> {

}
