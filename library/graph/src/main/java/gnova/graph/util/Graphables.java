package gnova.graph.util;

import gnova.graph.structure.Graphable;

/**
 * 网络单元的集合
 * 
 * @author birderyu
 *
 */
public interface Graphables
		extends Iterable<Graphable> {

	int size();
	
	boolean isEmpty();
	
	/**
	 * 添加一个拓扑单元
	 * 
	 * @param component
	 */
	void add(Graphable component);
	
	/**
	 * 移除一个拓扑单元
	 * 
	 * @param component
	 */
	void remove(Graphable component);

}
