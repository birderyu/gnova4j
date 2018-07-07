package gnova.graph.build;

import gnova.graph.structure.Edge;
import gnova.graph.structure.Graph;
import gnova.graph.structure.Graphable;
import gnova.graph.structure.Node;

/**
 * 图创建者
 *
 * 图创建者用于将一个第三方对象添加到图中
 * 
 * @author birderyu
 *
 */
public interface GraphGenerator<N extends Node, E extends Edge> {

	Graphable get(Object obj);

    /**
     * 添加一个对象到图中，返回对象对应的图元
     *
     * @param obj
     * @return
     */
	Graphable add(Object obj);

    /**
     * 从图中移除一个对象
     *
     * @param obj
     * @return
     */
	Graphable remove(Object obj);
	
	GraphBuilder<N, E> getGraphBuilder();
	
	Graph<N, E> build();
	
}
