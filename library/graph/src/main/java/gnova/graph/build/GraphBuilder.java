package gnova.graph.build;

import java.util.Collection;

import gnova.graph.structure.Edge;
import gnova.graph.structure.Graph;
import gnova.graph.structure.Node;

/**
  * 图构造器
  *
  * 图构造器用于造一张图
  *
  * @param <N> 节点的类型
  * @param <E> 边的类型
  * @author birderyu
  *
  */
public interface GraphBuilder<N extends Node, E extends Edge> {
	
	void clear();

     /**
      * 创建一个图
      *
      * @return
      */
	 Graph build();

     /**
      * 添加一条边
      *
      * @param edge
      */
	void addEdge(E edge);

     /**
      * 添加一个节点
      *
      * @param node
      */
	void addNode(N node);

	 /**
	  * 创建一条边
	  *
	  * @param nodeA
	  * @param nodeB
	  * @return
	  */
	E buildEdge(N nodeA, N nodeB);

	 /**
	  * 创建一个节点
	  *
	  * @return
	  */
	N buildNode();
	
	void removeEdge(E edge);

	void removeEdges(Collection<E> edges);
	
	void removeNode(N node);

	void removeNodes(Collection<N> nodes);
}
